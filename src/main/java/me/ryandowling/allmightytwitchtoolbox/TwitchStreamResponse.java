package me.ryandowling.allmightytwitchtoolbox;

import me.ryandowling.allmightytwitchtoolbox.data.twitch.TwitchStream;

public class TwitchStreamResponse {
    private TwitchStream stream;

    public TwitchStream getStream() {
        return this.stream;
    }

    public boolean isLive() {
        return this.stream != null;
    }
}
