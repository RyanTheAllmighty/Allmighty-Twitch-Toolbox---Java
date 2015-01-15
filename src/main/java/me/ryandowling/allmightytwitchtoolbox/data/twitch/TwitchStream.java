package me.ryandowling.allmightytwitchtoolbox.data.twitch;

import java.util.Map;

public class TwitchStream {
    private String game;
    private int viewers;
    private Map<String, String> preview;
    private TwitchChannel channel;

    public String getGame() {
        return this.game;
    }

    public int getViewers() {
        return this.viewers;
    }

    public String getSmallPreviewURL() {
        return this.preview.get("small");
    }

    public String getMediumPreviewURL() {
        return this.preview.get("medium");
    }

    public String getLargePreviewURL() {
        return this.preview.get("large");
    }

    public TwitchChannel getChannel() {
        return this.channel;
    }
}