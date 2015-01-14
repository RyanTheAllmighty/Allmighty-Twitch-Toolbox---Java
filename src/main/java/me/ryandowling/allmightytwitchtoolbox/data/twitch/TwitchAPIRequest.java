package me.ryandowling.allmightytwitchtoolbox.data.twitch;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.data.APIRequest;

public class TwitchAPIRequest extends APIRequest {
    private String apiToken;

    public TwitchAPIRequest(String path) {
        super("https://api.twitch.tv/kraken", path);

        this.apiToken = App.NOTIFIER.getSettings().getTwitchAPIToken();
    }

    public TwitchAPIRequest(String path, String accessToken) {
        super("https://api.twitch.tv/kraken", path);

        this.apiToken = accessToken;
    }

    @Override
    protected void setRequestProperties() {
        super.setRequestProperties();

        this.connection.setRequestProperty("Accept", "application/vnd.twitchtv.v3+json");
        this.connection.setRequestProperty("Authorization", "OAuth " + this.apiToken);
    }
}