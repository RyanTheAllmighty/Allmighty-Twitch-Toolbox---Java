package me.ryandowling.twitchnotifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.ryandowling.twitchnotifier.data.Settings;
import me.ryandowling.twitchnotifier.data.twitch.TwitchAPIRequest;
import me.ryandowling.twitchnotifier.data.twitch.TwitchFollower;
import me.ryandowling.twitchnotifier.data.twitch.TwitchUserFollows;
import me.ryandowling.twitchnotifier.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TwitchNotifier {
    private Settings settings;

    private Server server; // The Jetty server

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private Map<String, TwitchFollower> followers = new HashMap<>();

    public TwitchNotifier() {
        loadSettings();
        startServer();
        loadFollowers();
        startCheckingForNewFollowers();
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
                    follower.addTimestamps();
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

    public List<TwitchFollower> checkForNewFollowers() {
        List<TwitchFollower> newFollowers = new ArrayList<>();

        TwitchAPIRequest request = new TwitchAPIRequest("/channels/" + this.settings.getTwitchUsername() +
                "/follows?direction=desc&limit=100");

        try {
            TwitchUserFollows followers = GSON.fromJson(request.get(), TwitchUserFollows.class);

            for (TwitchFollower follower : followers.getFollows()) {
                if (!this.followers.containsKey(follower.getUser().getName())) {
                    follower.addTimestamps();
                    long followedTimestamp = follower.getCreatedAtTimestamp() / 1000;
                    long nowTimestamp = System.currentTimeMillis() / 1000;

                    System.out.println("New Follower " + follower.getUser().getDisplayName() + " - Followed " + Utils
                            .timeConversion((int) (nowTimestamp - followedTimestamp)) + " ago!");
                    newFollowers.add(follower);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFollowers;
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
}
