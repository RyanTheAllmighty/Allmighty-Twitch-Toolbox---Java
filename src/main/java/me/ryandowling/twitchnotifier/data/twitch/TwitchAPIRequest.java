package me.ryandowling.twitchnotifier.data.twitch;

import me.ryandowling.twitchnotifier.data.APIRequest;

public class TwitchAPIRequest extends APIRequest {
    public TwitchAPIRequest(String path) {
        super("https://api.twitch.tv/kraken", path);
    }

    @Override
    protected void setRequestProperties() {
        super.setRequestProperties();

        this.connection.setRequestProperty("Accept", "application/vnd.twitchtv.v3+json");
    }
}