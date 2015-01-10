package me.ryandowling.twitchnotifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.ryandowling.twitchnotifier.data.Settings;
import me.ryandowling.twitchnotifier.data.interfaces.Donation;
import me.ryandowling.twitchnotifier.data.interfaces.Follower;
import me.ryandowling.twitchnotifier.data.streamtip.StreamTipAPIRequest;
import me.ryandowling.twitchnotifier.data.streamtip.StreamTipTip;
import me.ryandowling.twitchnotifier.data.streamtip.StreamTipTips;
import me.ryandowling.twitchnotifier.data.twitch.TwitchAPIRequest;
import me.ryandowling.twitchnotifier.data.twitch.TwitchFollower;
import me.ryandowling.twitchnotifier.data.twitch.TwitchUserFollows;
import me.ryandowling.twitchnotifier.events.DonationAlert;
import me.ryandowling.twitchnotifier.events.DonationFiles;
import me.ryandowling.twitchnotifier.events.FollowerAlert;
import me.ryandowling.twitchnotifier.events.FollowerFiles;
import me.ryandowling.twitchnotifier.events.managers.DonationManager;
import me.ryandowling.twitchnotifier.events.managers.FollowerManager;
import me.ryandowling.twitchnotifier.gui.Console;
import me.ryandowling.twitchnotifier.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TwitchNotifier {
    private Settings settings;

    private Server server; // The Jetty server

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private Map<String, Follower> followers = new HashMap<>();
    private Map<String, Donation> donations = new HashMap<>();

    // All the notificators
    private FollowerFiles followerFiles;
    private FollowerAlert followerAlert;

    private DonationFiles donationFiles;
    private DonationAlert donationAlert;

    private Console console;

    public TwitchNotifier() {
        loadSettings();
        checkForServerResources();
    }

    private void checkForServerResources() {
        if (!Files.exists(Utils.getFollowersHTMLFile())) {
            try {
                URL inputUrl = System.class.getResource("/assets/html/followers.html");
                FileUtils.copyURLToFile(inputUrl, Utils.getFollowersHTMLFile().toFile());
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to copy followers.html to disk! Exiting!");
                System.exit(1);
            }
        }

        if (!Files.exists(Utils.getFollowersImageFile())) {
            try {
                URL inputUrl = System.class.getResource("/assets/image/followers.png");
                FileUtils.copyURLToFile(inputUrl, Utils.getFollowersImageFile().toFile());
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to copy followers.png to disk! Exiting!");
                System.exit(1);
            }
        }
    }

    public void setup() {
        if (this.settings.isSetup()) {
            loadFollowers();
            startCheckingForNewFollowers();

            loadDonations();
            startCheckingForNewDonations();

            loadNotifiers();

            startServer();
        }
    }

    private void loadNotifiers() {
        followerFiles = new FollowerFiles();
        followerFiles.writeFiles();

        followerAlert = new FollowerAlert();

        donationFiles = new DonationFiles();
        donationFiles.writeFiles();

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

    private void loadFollowers() {
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

        this.followers = Utils.sortMapByValue(this.followers);

        saveFollowers();
    }

    private void loadDonations() {
        boolean loadedFromFile = false;
        int offset = 0;
        StreamTipTips donations;

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

        StreamTipAPIRequest request = new StreamTipAPIRequest("/tips");

        try {
            donations = GSON.fromJson(request.get(), StreamTipTips.class);

            for (StreamTipTip tip : donations.getTips()) {
                if (!this.donations.containsKey(tip.getID())) {
                    this.donations.put(tip.getID(), tip);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.donations = Utils.sortMapByValue(this.donations);

        saveFollowers();
    }

    public void checkForNewFollowers() {
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
        StreamTipAPIRequest request = new StreamTipAPIRequest("/tips");

        try {
            StreamTipTips donations = GSON.fromJson(request.get(), StreamTipTips.class);

            for (StreamTipTip tip : donations.getTips()) {
                DonationManager.newDonation(tip);
            }

            this.followers = Utils.sortMapByValue(this.followers);
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

    public boolean addFollower(Follower follower) {
        boolean isNew = !this.followers.containsKey(follower.getUsername());

        this.followers.put(follower.getUsername(), follower);
        this.followers = Utils.sortMapByValue(this.followers);

        return isNew;
    }

    public boolean addDonation(Donation donation) {
        boolean isNew = !this.donations.containsKey(donation.getID());

        this.donations.put(donation.getID(), donation);
        this.donations = Utils.sortMapByValue(this.donations);

        return isNew;
    }

    public Follower getLatestFollower() {
        return this.followers.entrySet().iterator().next().getValue();
    }

    public Donation getLatestDonation() {
        return this.donations.entrySet().iterator().next().getValue();
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public Console getConsole() {
        return this.console;
    }
}
