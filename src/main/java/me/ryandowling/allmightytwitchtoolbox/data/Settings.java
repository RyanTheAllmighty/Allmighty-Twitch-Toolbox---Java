package me.ryandowling.allmightytwitchtoolbox.data;

import me.ryandowling.allmightytwitchtoolbox.App;

import java.awt.Dimension;
import java.awt.Point;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Settings {
    private Point guiPosition;
    private Dimension guiSize;
    private int serverPort;

    private String twitchUsername;
    private String twitchAPIToken;
    private String twitchAPIClientID;

    private String streamTipClientID;
    private String streamTipAccessToken;

    private int secondsBetweenFollowerChecks;
    private int secondsBetweenDonationChecks;
    private int secondsBetweenViewerCountChecks;

    private int numberOfPointsOnViewerChart;

    private String newFollowSoundPath;
    private String newDonationSoundPath;

    private String foobarLocation;

    private String rawNowPlayingFilePath;
    private String nowPlayingFilePath;
    private String nowPlayingFileFilePath;

    private boolean autoRunFoobarNowPlayingConverter;

    private String followerType;
    private String donationType;

    private Map<Integer, Date> countdownTimers;

    private boolean isSetup;
    private int counter;
    private String[] soundboardSounds;
    private float soundsVolume;

    public void loadDefaults() {
        this.guiPosition = new Point(0, 0);
        this.guiSize = new Dimension(600, 400);
        this.serverPort = 9001;
        this.twitchUsername = "";
        this.twitchAPIToken = "";
        this.twitchAPIClientID = "";
        this.secondsBetweenFollowerChecks = 30;
        this.secondsBetweenDonationChecks = 30;
        this.secondsBetweenViewerCountChecks = 30;
        this.numberOfPointsOnViewerChart = 20;
        this.newFollowSoundPath = "";
        this.newDonationSoundPath = "";
        this.rawNowPlayingFilePath = "";
        this.nowPlayingFilePath = "";
        this.nowPlayingFileFilePath = "";
        this.autoRunFoobarNowPlayingConverter = false;
        this.soundboardSounds = new String[9];
        this.soundsVolume = 0.0f;

        this.countdownTimers = new HashMap<>();
        this.isSetup = false;

        this.followerType = "TwitchFollower";
        this.donationType = "StreamTipTip";
    }

    public void setupSoundboardSoundDefaults() {
        if (this.soundboardSounds == null) {
            this.soundboardSounds = new String[9];
            App.NOTIFIER.saveSettings();
        }
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

    public int getSecondsBetweenViewerCountChecks() {
        return this.secondsBetweenViewerCountChecks;
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

    public void setSecondsBetweenViewerCountChecks(int secondsBetweenViewerCountChecks) {
        this.secondsBetweenViewerCountChecks = secondsBetweenViewerCountChecks;
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

    public void setupInvalid() {
        this.isSetup = false;
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

    public String getTwitchAPIClientID() {
        return twitchAPIClientID;
    }

    public void setTwitchAPIToken(String twitchAPIToken) {
        this.twitchAPIToken = twitchAPIToken;
    }

    public void setTwitchAPIClientID(String twitchAPIClientID) {
        this.twitchAPIClientID = twitchAPIClientID;
    }

    public int getNumberOfPointsOnViewerChart() {
        return this.numberOfPointsOnViewerChart;
    }

    public void setNumberOfPointsOnViewerChart(int numberOfPointsOnViewerChart) {
        this.numberOfPointsOnViewerChart = numberOfPointsOnViewerChart;
    }

    public void setCountdownTimer(int num, Date countdownTimer) {
        if (this.countdownTimers == null) {
            this.countdownTimers = new HashMap<>();
        }

        this.countdownTimers.put(num, countdownTimer);
    }

    public Date getCountdownTimer(int num) {
        if (this.countdownTimers == null) {
            return null;
        }

        return this.countdownTimers.get(num);
    }

    public boolean autoRunFoobarNowPlayingConverter() {
        return this.autoRunFoobarNowPlayingConverter;
    }

    public void setAutoRunFoobarNowPlayingConverter(boolean autoRunFoobarNowPlayingConverter) {
        this.autoRunFoobarNowPlayingConverter = autoRunFoobarNowPlayingConverter;
    }

    public Path getRawNowPlayingFile() {
        return Paths.get(this.rawNowPlayingFilePath);
    }

    public String getRawNowPlayingFilePath() {
        return this.rawNowPlayingFilePath;
    }

    public void setRawNowPlayingFile(String rawNowPlayingFile) {
        this.rawNowPlayingFilePath = rawNowPlayingFile;
    }

    public Path getNowPlayingFile() {
        return Paths.get(this.nowPlayingFilePath);
    }

    public String getNowPlayingFilePath() {
        return this.nowPlayingFilePath;
    }

    public void setNowPlayingFile(String nowPlayingFile) {
        this.nowPlayingFilePath = nowPlayingFile;
    }

    public Path getNowPlayingFileFile() {
        return Paths.get(this.nowPlayingFileFilePath);
    }

    public String getNowPlayingFileFilePath() {
        return this.nowPlayingFileFilePath;
    }

    public void setNowPlayingFileFile(String nowPlayingFileFile) {
        this.nowPlayingFileFilePath = nowPlayingFileFile;
    }

    public boolean hasSetupNowPlaying() {
        return this.getRawNowPlayingFilePath() != null && !this.getRawNowPlayingFilePath().isEmpty() &&
                this.getNowPlayingFilePath() != null && !this.getNowPlayingFilePath().isEmpty() &&
                this.getNowPlayingFileFilePath() != null && !this.getNowPlayingFileFilePath().isEmpty();
    }

    public String getFoobarLocation() {
        return this.foobarLocation;
    }

    public void setFoobarLocation(String foobarLocation) {
        this.foobarLocation = foobarLocation;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public int getCounter() {
        return this.counter;
    }

    public Path getSoundboardSound(int i) {
        if (this.soundboardSounds[i - 1] == null) {
            return null;
        }

        return Paths.get(this.soundboardSounds[i - 1]);
    }

    public void setSoundboardSound(int i, String absolutePath) {
        this.soundboardSounds[i - 1] = absolutePath;
    }

    public float getSoundsVolume() {
        return this.soundsVolume;
    }

    public void setSoundsVolume(float soundsVolume) {
        this.soundsVolume = soundsVolume;
    }
}
