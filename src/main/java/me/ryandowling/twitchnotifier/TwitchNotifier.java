package me.ryandowling.twitchnotifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.ryandowling.twitchnotifier.data.Settings;
import me.ryandowling.twitchnotifier.data.twitch.TwitchAPIRequest;
import me.ryandowling.twitchnotifier.data.twitch.TwitchFollower;
import me.ryandowling.twitchnotifier.data.twitch.TwitchUserFollows;
import me.ryandowling.twitchnotifier.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class TwitchNotifier {
    private Settings settings;

    private Server server; // The Jetty server

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();


    private Map<String, TwitchFollower> followers = new HashMap<>();

    public TwitchNotifier() {
        loadSettings();
        startServer();
        loadFollowers();
    }

    private void loadFollowers() {
        boolean hasMoreFollowers = true;
        int offset = 0;
        TwitchUserFollows followers;

        while (hasMoreFollowers) {
            TwitchAPIRequest request = new TwitchAPIRequest("/channels/" + this.settings.getTwitchUsername() +
                    "/follows?direction=desc&limit=100&offset=" + offset);

            try {
                followers = GSON.fromJson(request.get(), TwitchUserFollows.class);

                for (TwitchFollower follower : followers.getFollows()) {
                    this.followers.put(follower.getUser().getName(), follower);
                }

                offset += 100;

                if (followers.getFollows() == null || followers.getFollows().size() < 100) {
                    hasMoreFollowers = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        saveFollowers();
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
