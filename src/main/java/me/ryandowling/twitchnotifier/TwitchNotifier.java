package me.ryandowling.twitchnotifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.ryandowling.twitchnotifier.data.Settings;
import me.ryandowling.twitchnotifier.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.file.Files;

public class TwitchNotifier {
    private Settings settings;

    private Server server; // The Jetty server

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public TwitchNotifier() {
        loadSettings();
        startServer();
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
