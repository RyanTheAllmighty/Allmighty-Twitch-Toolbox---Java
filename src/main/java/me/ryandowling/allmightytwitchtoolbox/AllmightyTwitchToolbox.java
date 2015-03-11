package me.ryandowling.allmightytwitchtoolbox;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tulskiy.keymaster.common.HotKey;
import com.tulskiy.keymaster.common.HotKeyListener;
import com.tulskiy.keymaster.common.Provider;
import me.ryandowling.allmightytwitchtoolbox.data.ChartData;
import me.ryandowling.allmightytwitchtoolbox.data.Settings;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Donation;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Follower;
import me.ryandowling.allmightytwitchtoolbox.data.streamtip.StreamTipAPIRequest;
import me.ryandowling.allmightytwitchtoolbox.data.streamtip.StreamTipTip;
import me.ryandowling.allmightytwitchtoolbox.data.streamtip.StreamTipTips;
import me.ryandowling.allmightytwitchtoolbox.data.twitch.TwitchAPIRequest;
import me.ryandowling.allmightytwitchtoolbox.data.twitch.TwitchFollower;
import me.ryandowling.allmightytwitchtoolbox.data.twitch.TwitchUserFollows;
import me.ryandowling.allmightytwitchtoolbox.events.DonationAlert;
import me.ryandowling.allmightytwitchtoolbox.events.DonationFiles;
import me.ryandowling.allmightytwitchtoolbox.events.DonationSocket;
import me.ryandowling.allmightytwitchtoolbox.events.FollowerAlert;
import me.ryandowling.allmightytwitchtoolbox.events.FollowerFiles;
import me.ryandowling.allmightytwitchtoolbox.events.FollowerSocket;
import me.ryandowling.allmightytwitchtoolbox.events.ViewerCountFiles;
import me.ryandowling.allmightytwitchtoolbox.events.managers.DonationManager;
import me.ryandowling.allmightytwitchtoolbox.events.managers.FollowerManager;
import me.ryandowling.allmightytwitchtoolbox.events.managers.ViewerCountManager;
import me.ryandowling.allmightytwitchtoolbox.gui.Console;
import me.ryandowling.allmightytwitchtoolbox.utils.SoundPlayer;
import me.ryandowling.allmightytwitchtoolbox.utils.Utils;
import org.apache.commons.io.FileUtils;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

// @todo Clean up this massive mess of messiness
public class AllmightyTwitchToolbox {
    private Settings settings;

    private final Provider HOT_KEY_PROVIDER = Provider.getCurrentProvider(true);

    private Server server; // The Jetty server
    private SocketIOServer socketIOServer; // The Socket IO server

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(8);

    private Map<String, Follower> followers = new HashMap<>();
    private int totalFollowers = 0;

    private Map<String, Donation> donations = new HashMap<>();
    private Map<Date, Integer> viewerCount = new TreeMap<>();

    private int latestViewerCount = 0;

    // All the notificators
    private ViewerCountFiles viewerCountFiles;

    private FollowerSocket followerSocket;
    private FollowerFiles followerFiles;
    private FollowerAlert followerAlert;

    private DonationSocket donationSocket;
    private DonationFiles donationFiles;
    private DonationAlert donationAlert;

    private Console console;

    private int followersStart = 0;
    private float donationsTally = 0.00f;

    private int followerGoal = 0;
    private float donationGoal = 0.00f;
    private int[] viewerCountChartData;
    private boolean hasSetup = false;

    private Date countdownTimer = new Date();

    public AllmightyTwitchToolbox() {
        loadSettings();
        checkForServerResources();
    }

    private void checkForServerResources() {
        if (!Files.exists(Utils.getNotificationsImageFile())) {
            try {
                URL inputUrl = System.class.getResource("/assets/web/image/notifications.png");
                FileUtils.copyURLToFile(inputUrl, Utils.getNotificationsImageFile().toFile());
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to copy notifications.png to disk! Exiting!");
                System.exit(1);
            }
        }
    }

    public void setup() {
        this.setup(true);
    }

    public void setup(boolean checkAPIToken) {
        if (checkAPIToken && this.settings.isSetup()) {
            checkTwitchAPIToken();
        }

        // If things are still setup, then Twitch API token is good

        if (this.settings.isSetup() && !this.hasSetup) {
            this.settings.setupSoundboardSoundDefaults();

            loadFollowers();
            startCheckingForNewFollowers();

            loadDonations();
            startCheckingForNewDonations();

            checkViewerCount();
            startCheckingViewerCount();

            loadNotifiers();

            startServer();

            startSocketIOServer();

            setupSoundboardHotkeys();

            executor.submit(new Runnable() {
                @Override
                public void run() {
                    sendInitialCountdownTimers();
                }
            });

            this.hasSetup = true;
        }
    }

    private void setupSoundboardHotkeys() {
        int[] keyEvents = {
                KeyEvent.VK_NUMPAD1,
                KeyEvent.VK_NUMPAD2,
                KeyEvent.VK_NUMPAD3,
                KeyEvent.VK_NUMPAD4,
                KeyEvent.VK_NUMPAD5,
                KeyEvent.VK_NUMPAD6,
                KeyEvent.VK_NUMPAD7,
                KeyEvent.VK_NUMPAD8,
                KeyEvent.VK_NUMPAD9
        };

        for (int i = 1; i <= 9; i++) {
            final int num = i - 1;

            getHotKeyProvider().register(KeyStroke.getKeyStroke(keyEvents[num], KeyEvent
                    .CTRL_DOWN_MASK), new HotKeyListener() {
                @Override
                public void onHotKey(HotKey hotKey) {
                    Path path = App.NOTIFIER.getSettings().getSoundboardSound(num + 1);
                    if (path != null) {
                        SoundPlayer.playSound(path);
                    }
                }
            });
        }
    }

    private void checkTwitchAPIToken() {
        if (!Utils.isTwitchAPITokenValid(this.settings.getTwitchAPIToken())) {
            this.settings.setupInvalid();
            this.saveSettings();

            JOptionPane.showMessageDialog(null, "Twitch API Token is invalid!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadNotifiers() {
        viewerCountFiles = new ViewerCountFiles();
        viewerCountFiles.writeFiles();

        followerSocket = new FollowerSocket();
        donationSocket = new DonationSocket();

        followerFiles = new FollowerFiles();
        followerFiles.writeFiles();

        donationFiles = new DonationFiles();
        donationFiles.writeFiles();

        followerAlert = new FollowerAlert();
        donationAlert = new DonationAlert();
    }

    private void startCheckingForNewFollowers() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                checkForNewFollowers();
            }
        };

        this.executor.scheduleAtFixedRate(runnable, this.settings.getSecondsBetweenFollowerChecks(), this.settings
                .getSecondsBetweenFollowerChecks(), TimeUnit.SECONDS);
    }

    private void startCheckingForNewDonations() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                checkForNewDonations();
            }
        };

        this.executor.scheduleAtFixedRate(runnable, this.settings.getSecondsBetweenDonationChecks(), this.settings
                .getSecondsBetweenDonationChecks(), TimeUnit.SECONDS);
    }

    private void startCheckingViewerCount() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                checkViewerCount();
            }
        };

        this.executor.scheduleAtFixedRate(runnable, this.settings.getSecondsBetweenViewerCountChecks(), this.settings
                .getSecondsBetweenViewerCountChecks(), TimeUnit.SECONDS);
    }

    private void loadFollowers() {
        switch (this.settings.getFollowerType()) {
            case "TwitchFollower":
                loadTwitchFollowers();
                break;
            default:
                System.err.println("No follower type of " + this.settings.getFollowerType() + " found!");
                System.exit(1);
        }

        this.followers = Utils.sortMapByValue(this.followers);

        saveFollowers();
    }

    private void loadTwitchFollowers() {
        boolean loadedFromFile = false;
        boolean hasMoreFollowers = true;
        int offset = 0;
        TwitchUserFollows followers;

        if (Files.exists(Utils.getFollowersFile())) {
            Type listType = new TypeToken<HashMap<String, TwitchFollower>>() {
            }.getType();

            try {
                this.followers = GSON.fromJson(FileUtils.readFileToString(Utils.getFollowersFile().toFile()), listType);
                loadedFromFile = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        int limit = (loadedFromFile ? 20 : 100);

        while (hasMoreFollowers) {
            TwitchAPIRequest request = new TwitchAPIRequest("/channels/" + this.settings.getTwitchUsername() +
                    "/follows?direction=desc&limit=" + limit + "&offset=" + offset);

            try {
                followers = GSON.fromJson(request.get(), TwitchUserFollows.class);

                this.totalFollowers = followers.getTotalFollowers();
                this.followersStart = followers.getTotalFollowers();

                int added = 0;

                for (TwitchFollower follower : followers.getFollows()) {
                    if (!this.followers.containsKey(follower.getUser().getName())) {
                        added++;
                        this.followers.put(follower.getUser().getName(), follower);
                    }
                }

                offset += limit;

                if (added == 0) {
                    hasMoreFollowers = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadDonations() {
        switch (this.settings.getDonationType()) {
            case "StreamTipTip":
                loadStreamTipDonations();
                break;
            default:
                System.err.println("No donation type of " + this.settings.getDonationType() + " found!");
                System.exit(1);
        }

        this.donations = Utils.sortMapByValue(this.donations);

        saveDonations();
    }

    private void loadStreamTipDonations() {
        StreamTipTips donations;
        boolean loadedFromFile = false;

        if (Files.exists(Utils.getDonationsFile())) {
            Type listType = new TypeToken<HashMap<String, StreamTipTip>>() {
            }.getType();

            try {
                this.donations = GSON.fromJson(FileUtils.readFileToString(Utils.getDonationsFile().toFile()), listType);
                loadedFromFile = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        boolean hasMoreDonations = true;

        int limit = (loadedFromFile ? 10 : 100);
        int offset = 0;

        while (hasMoreDonations) {
            StreamTipAPIRequest request = new StreamTipAPIRequest("/tips", limit, offset);
            int added = 0;

            try {
                donations = GSON.fromJson(request.get(), StreamTipTips.class);

                for (StreamTipTip tip : donations.getTips()) {
                    if (!this.donations.containsKey(tip.getID())) {
                        added++;
                        this.donations.put(tip.getID(), tip);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            offset += limit;

            if (added == 0) {
                hasMoreDonations = false;
            }
        }
    }

    public void checkForNewFollowers() {
        switch (this.settings.getFollowerType()) {
            case "TwitchFollower":
                checkForNewTwitchFollowers();
                break;
        }
    }

    private void checkForNewTwitchFollowers() {
        TwitchAPIRequest request = new TwitchAPIRequest("/channels/" + this.settings.getTwitchUsername() +
                "/follows?direction=desc&limit=20");

        try {
            TwitchUserFollows followers = GSON.fromJson(request.get(), TwitchUserFollows.class);

            if (this.totalFollowers != followers.getTotalFollowers()) {
                FollowerManager.followersNumberChanged(followers.getTotalFollowers());
            }

            this.totalFollowers = followers.getTotalFollowers();

            for (TwitchFollower follower : followers.getFollows()) {
                if (!this.followers.containsKey(follower.getUsername())) {
                    FollowerManager.newFollow(follower);
                }
            }

            this.followers = Utils.sortMapByValue(this.followers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkForNewDonations() {
        switch (this.settings.getDonationType()) {
            case "StreamTipTip":
                checkForNewStreamTipDonations();
                break;
        }
    }

    private void checkForNewStreamTipDonations() {
        StreamTipAPIRequest request = new StreamTipAPIRequest("/tips", 5, 0);

        try {
            StreamTipTips donations = GSON.fromJson(request.get(), StreamTipTips.class);

            for (StreamTipTip tip : donations.getTips()) {
                if (!this.donations.containsKey(tip.getID())) {
                    DonationManager.newDonation(tip);
                }
            }

            this.donations = Utils.sortMapByValue(this.donations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkViewerCount() {
        TwitchAPIRequest request = new TwitchAPIRequest("/streams/" + this.settings.getTwitchUsername());

        try {
            TwitchStreamResponse stream = GSON.fromJson(request.get(), TwitchStreamResponse.class);

            int lastViewers = this.getLatestViewerCount();
            int nowViewers = (stream.isLive() ? stream.getStream().getViewers() : 0);

            this.viewerCount.put(new Date(), nowViewers);
            this.latestViewerCount = nowViewers;

            ViewerCountManager.viewerCountDataAdded(nowViewers);

            if (lastViewers != nowViewers) {
                ViewerCountManager.viewerCountChanged(nowViewers);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFollowers() {
        try {
            FileUtils.write(Utils.getFollowersFile().toFile(), GSON.toJson(this.followers));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveDonations() {
        try {
            FileUtils.write(Utils.getDonationsFile().toFile(), GSON.toJson(this.donations));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSettings() {
        if (!Files.exists(Utils.getSettingsFile())) {
            this.settings = new Settings();
            this.settings.loadDefaults();
            this.saveSettings();

            return;
        }

        try {
            this.settings = GSON.fromJson(FileUtils.readFileToString(Utils.getSettingsFile().toFile()), Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveSettings() {
        try {
            FileUtils.write(Utils.getSettingsFile().toFile(), GSON.toJson(this.settings));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Settings getSettings() {
        return this.settings;
    }

    public void startServer() {
        if (this.server != null && this.server.isAlive()) {
            this.server.stop();
        }

        this.server = new Server(this.settings.getServerPort());

        try {
            this.server.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Cannot start the server! Exiting!");
            System.exit(1);
        }
    }

    private void startSocketIOServer() {
        Configuration config = new Configuration();
        config.setHostname("localhost");
        config.setPort(9002);

        socketIOServer = new SocketIOServer(config);

        socketIOServer.start();
    }

    public void sendSocketMessage(String eventName, Object data) {
        this.socketIOServer.getBroadcastOperations().sendEvent(eventName, data);
    }

    public void stopServer() {
        if (this.server != null && this.server.isAlive()) {
            this.server.stop();
        }
    }

    public void stopSocketIOServer() {
        if (this.socketIOServer != null) {
            this.socketIOServer.stop();
        }
    }

    public Map<String, Follower> getFollowers() {
        return this.followers;
    }

    public Map<String, Donation> getDonations() {
        return this.donations;
    }

    public Map<Date, Integer> getViewerCount() {
        return this.viewerCount;
    }

    public int getLatestViewerCount() {
        return this.latestViewerCount;
    }

    public String getLatestViewerCountFormatted() {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(this.latestViewerCount);
    }

    public boolean addFollower(Follower follower) {
        boolean isNew = !this.followers.containsKey(follower.getUsername());

        this.followers.put(follower.getUsername(), follower);

        return isNew;
    }

    public boolean addDonation(Donation donation) {
        boolean isNew = !this.donations.containsKey(donation.getID());

        this.donations.put(donation.getID(), donation);

        if (isNew) {
            this.donationsTally = +donation.getAmount();
        }

        return isNew;
    }

    public Follower getLatestFollower() {
        if (this.followers.size() == 0) {
            return null;
        }

        return this.followers.entrySet().iterator().next().getValue();
    }

    public Donation getLatestDonation() {
        if (this.donations.size() == 0) {
            return null;
        }

        return this.donations.entrySet().iterator().next().getValue();
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public Console getConsole() {
        return this.console;
    }

    public int getFollowersTally() {
        return this.totalFollowers - this.followersStart;
    }

    public String getFollowersTallyFormatted() {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(this.totalFollowers - this.followersStart);
    }

    public void resetFollowersTally() {
        this.followersStart = this.totalFollowers;
    }

    public float getDonationsTally() {
        DecimalFormat df = new DecimalFormat("#0.00");

        return Float.parseFloat(df.format(this.donationsTally));
    }

    public void resetDonationsTally() {
        this.donationsTally = 0.0f;
    }

    public float getDonationsTotal() {
        float total = 0.0f;

        for (Map.Entry<String, Donation> entry : this.donations.entrySet()) {
            total += entry.getValue().getAmount();
        }

        DecimalFormat df = new DecimalFormat("#0.00");

        return Float.parseFloat(df.format(total));
    }

    public int getFollowersTotal() {
        return this.totalFollowers;
    }

    public String getFollowersTotalFormatted() {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(this.totalFollowers);
    }

    public String getDonationsTallyFormatted() {
        return "$" + String.format("%.2f", this.donationsTally);
    }

    public String getDonationTotalFormatted() {
        return "$" + String.format("%.2f", this.getDonationsTotal());
    }

    public int getFollowerGoal() {
        return this.followerGoal;
    }

    public String getFollowerGoalFormatted() {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(this.followerGoal);
    }

    public float getDonationGoal() {
        return this.donationGoal;
    }

    public String getDonationGoalFormatted() {
        return "$" + String.format("%.2f", this.donationGoal);
    }

    public void setDonationGoal(float donationGoal) {
        this.donationGoal = donationGoal;
    }

    public void setFollowerGoal(int followerGoal) {
        this.followerGoal = followerGoal;
    }

    public Provider getHotKeyProvider() {
        return this.HOT_KEY_PROVIDER;
    }

    public ChartData getViewerCountChartData() {
        List<Date> xValues = new ArrayList<>();
        List<Integer> yValues = new ArrayList<>();

        int i = 0;
        int total = this.viewerCount.size();
        for (Map.Entry<Date, Integer> entry : this.viewerCount.entrySet()) {
            yValues.add(i, entry.getValue());
            xValues.add(i, entry.getKey());

            i++;
        }

        if (xValues.size() == 0) {
            xValues.add(new Date());
        }

        if (yValues.size() == 0) {
            yValues.add(0);
        }

        if (xValues.size() > App.NOTIFIER.getSettings().getNumberOfPointsOnViewerChart()) {
            xValues = xValues.subList(xValues.size() - App.NOTIFIER.getSettings().getNumberOfPointsOnViewerChart(),
                    xValues.size());
        }

        if (yValues.size() > App.NOTIFIER.getSettings().getNumberOfPointsOnViewerChart()) {
            yValues = yValues.subList(yValues.size() - App.NOTIFIER.getSettings().getNumberOfPointsOnViewerChart(),
                    yValues.size());
        }

        return new ChartData(xValues, yValues);
    }

    public void sendInitialCountdownTimers() {
        int tries = 0;

        do {
            if (this.socketIOServer.getAllClients().size() != 0) {
                this.sendSocketMessage("timerchanged1", Utils.getDateDiff(new Date(), this.settings.getCountdownTimer
                        (1), TimeUnit.SECONDS));

                this.sendSocketMessage("timerchanged2", Utils.getDateDiff(new Date(), this.settings.getCountdownTimer
                        (2), TimeUnit.SECONDS));
                this.sendSocketMessage("timerchanged3", Utils.getDateDiff(new Date(), this.settings.getCountdownTimer
                        (3), TimeUnit.SECONDS));

                break;
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }

            tries++;
        } while (tries < 10);
    }

    public void setCountdownTimer(int num, Date countdownTimer) {
        this.settings.setCountdownTimer(num, countdownTimer);
        this.sendSocketMessage("timerchanged" + num, Utils.getDateDiff(new Date(), countdownTimer, TimeUnit.SECONDS));
        this.saveSettings();
    }

    public Date getCountdownTimer(int num) {
        if (this.settings.getCountdownTimer(num) == null) {
            return new Date();
        }

        return this.settings.getCountdownTimer(num);
    }

    public void setCounter(int counter) {
        this.settings.setCounter(counter);

        try {
            FileUtils.write(Utils.getCounterFile().toFile(), "" + counter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        System.out.println("Exiting the application!");
        saveSettings();
        stopServer();
        stopSocketIOServer();
        System.exit(0);
    }
}
