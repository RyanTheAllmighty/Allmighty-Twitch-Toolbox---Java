package me.ryandowling.twitchnotifier.data;

import java.awt.Dimension;
import java.awt.Point;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Settings {
    private Point guiPosition;
    private Dimension guiSize;
    private int serverPort;

    private String twitchUsername;

    private int secondsBetweenFollowerChecks;

    private String newFollowSoundPath;

    private boolean isSetup;

    public void loadDefaults() {
        this.guiPosition = new Point(0, 0);
        this.guiSize = new Dimension(600, 400);
        this.serverPort = 9001;
        this.twitchUsername = "";
        this.secondsBetweenFollowerChecks = 30;
        this.newFollowSoundPath = "";
        this.isSetup = false;
    }

    public Point getGuiPosition() {
        return this.guiPosition;
    }

    public Dimension getGuiSize() {
        return this.guiSize;
    }

    public void setGuiPosition(Point guiPosition) {
        this.guiPosition = guiPosition;
    }

    public void setGuiSize(Dimension guiSize) {
        this.guiSize = guiSize;
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public String getTwitchUsername() {
        return this.twitchUsername;
    }

    public int getSecondsBetweenFollowerChecks() {
        return this.secondsBetweenFollowerChecks;
    }

    public Path getNewFollowSound() {
        return Paths.get(this.newFollowSoundPath);
    }

    public String getNewFollowSoundPath() {
        return this.newFollowSoundPath;
    }

    public void setNewFollowSound(String path) {
        this.newFollowSoundPath = path;
    }

    public void setTwitchUsername(String twitchUsername) {
        this.twitchUsername = twitchUsername;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public void setSecondsBetweenFollowerChecks(int secondsBetweenFollowerChecks) {
        this.secondsBetweenFollowerChecks = secondsBetweenFollowerChecks;
    }

    public void setNewFollowSoundPath(String newFollowSoundPath) {
        this.newFollowSoundPath = newFollowSoundPath;
    }

    public boolean isSetup() {
        return this.isSetup;
    }

    public void setupFinished() {
        this.isSetup = true;
    }
}
