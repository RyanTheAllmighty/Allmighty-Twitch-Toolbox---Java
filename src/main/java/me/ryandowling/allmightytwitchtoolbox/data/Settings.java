package me.ryandowling.allmightytwitchtoolbox.data;

import java.awt.Dimension;
import java.awt.Point;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Settings {
    private Point guiPosition;
    private Dimension guiSize;
    private int serverPort;

    private String twitchUsername;
    private String twitchAPIToken;

    private String streamTipClientID;
    private String streamTipAccessToken;

    private int secondsBetweenFollowerChecks;
    private int secondsBetweenDonationChecks;

    private String newFollowSoundPath;
    private String newDonationSoundPath;

    private String followerType;
    private String donationType;

    private boolean isSetup;

    public void loadDefaults() {
        this.guiPosition = new Point(0, 0);
        this.guiSize = new Dimension(600, 400);
        this.serverPort = 9001;
        this.twitchUsername = "";
        this.twitchAPIToken = "";
        this.secondsBetweenFollowerChecks = 30;
        this.secondsBetweenDonationChecks = 30;
        this.newFollowSoundPath = "";
        this.newDonationSoundPath = "";
        this.isSetup = false;

        this.followerType = "TwitchFollower";
        this.donationType = "StreamTipTip";
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

    public int getSecondsBetweenDonationChecks() {
        return this.secondsBetweenDonationChecks;
    }

    public Path getNewFollowSound() {
        return Paths.get(this.newFollowSoundPath);
    }

    public Path getNewDonationSound() {
        return Paths.get(this.newDonationSoundPath);
    }

    public String getNewFollowSoundPath() {
        return this.newFollowSoundPath;
    }

    public String getNewDonationSoundPath() {
        return this.newDonationSoundPath;
    }

    public void setNewFollowSound(String path) {
        this.newFollowSoundPath = path;
    }

    public void setNewDonationSound(String path) {
        this.newDonationSoundPath = path;
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

    public void setSecondsBetweenDonationChecks(int secondsBetweenDonationChecks) {
        this.secondsBetweenDonationChecks = secondsBetweenDonationChecks;
    }

    public void setNewFollowSoundPath(String newFollowSoundPath) {
        this.newFollowSoundPath = newFollowSoundPath;
    }

    public void setNewDonationSoundPath(String newDonationSoundPath) {
        this.newDonationSoundPath = newDonationSoundPath;
    }

    public boolean isSetup() {
        return this.isSetup;
    }

    public void setupFinished() {
        this.isSetup = true;
    }

    public String getStreamTipClientID() {
        return this.streamTipClientID;
    }

    public void setStreamTipClientID(String streamTipClientID) {
        this.streamTipClientID = streamTipClientID;
    }

    public String getStreamTipAccessToken() {
        return this.streamTipAccessToken;
    }

    public void setStreamTipAccessToken(String streamTipAccessToken) {
        this.streamTipAccessToken = streamTipAccessToken;
    }

    public String getFollowerType() {
        return this.followerType;
    }

    public String getDonationType() {
        return this.donationType;
    }

    public String getTwitchAPIToken() {
        return this.twitchAPIToken;
    }

    public void setTwitchAPIToken(String twitchAPIToken) {
        this.twitchAPIToken = twitchAPIToken;
    }
}
