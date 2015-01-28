package me.ryandowling.allmightytwitchtoolbox;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
import me.ryandowling.allmightytwitchtoolbox.events.FollowerAlert;
import me.ryandowling.allmightytwitchtoolbox.events.FollowerFiles;
import me.ryandowling.allmightytwitchtoolbox.events.ViewerCountFiles;
import me.ryandowling.allmightytwitchtoolbox.events.managers.DonationManager;
import me.ryandowling.allmightytwitchtoolbox.events.managers.FollowerManager;
import me.ryandowling.allmightytwitchtoolbox.events.managers.ViewerCountManager;
import me.ryandowling.allmightytwitchtoolbox.gui.Console;
import me.ryandowling.allmightytwitchtoolbox.utils.Utils;
import org.apache.commons.io.FileUtils;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
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

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(8);

    private Map<String, Follower> followers = new HashMap<>();
    private Map<String, Donation> donations = new HashMap<>();
    private Map<Date, Integer> viewerCount = new TreeMap<>();

    // All the notificators
    private ViewerCountFiles viewerCountFiles;

    private FollowerFiles followerFiles;
    private FollowerAlert followerAlert;

    private DonationFiles donationFiles;
    private DonationAlert donationAlert;

    private Console console;

    private int followersTally = 0;
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
            loadFollowers();
            startCheckingForNewFollowers();

            loadDonations();
            startCheckingForNewDonations();

            checkViewerCount();
            startCheckingViewerCount();

            loadNotifiers();

            startServer();

            this.hasSetup = true;
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
                "/follows?direction=desc&limit=100");

        try {
            TwitchUserFollows followers = GSON.fromJson(request.get(), TwitchUserFollows.class);

            for (TwitchFollower follower : followers.getFollows()) {
                FollowerManager.newFollow(follower);
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
                DonationManager.newDonation(tip);
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

    public void stopServer() {
        if (this.server != null && this.server.isAlive()) {
            this.server.stop();
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
        if (this.viewerCount.size() == 0) {
            return 0;
        }

        return this.viewerCount.entrySet().iterator().next().getValue();
    }

    public String getLatestViewerCountFormatted() {
        DecimalFormat df = new DecimalFormat("###,###");

        if (this.viewerCount.size() == 0) {
            return df.format(0);
        }

        return df.format(this.viewerCount.entrySet().iterator().next().getValue());
    }

    public boolean addFollower(Follower follower) {
        boolean isNew = !this.followers.containsKey(follower.getUsername());

        this.followers.put(follower.getUsername(), follower);

        if (isNew) {
            this.followersTally++;
        }

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
        return this.followersTally;
    }

    public String getFollowersTallyFormatted() {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(this.followersTally);
    }

    public void resetFollowersTally() {
        this.followersTally = 0;
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
        return this.followers.size();
    }

    public String getFollowersTotalFormatted() {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(this.followers.size());
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

    public void setCountdownTimer(Date countdownTimer) {
        this.settings.setCountdownTimer(countdownTimer);
        this.saveSettings();
    }

    public Date getCountdownTimer() {
        if (this.settings.getCountdownTimer() == null) {
            return new Date();
        }
        
        return this.settings.getCountdownTimer();
    }
}
