package me.ryandowling.twitchnotifier.data.streamtip;

import me.ryandowling.twitchnotifier.App;
import me.ryandowling.twitchnotifier.data.APIRequest;

public class StreamTipAPIRequest extends APIRequest {
    public StreamTipAPIRequest(String path) {
        super("https://streamtip.com/api", path);
    }

    @Override
    protected void setRequestProperties() {
        super.setRequestProperties();

        this.connection.setRequestProperty("Authorization", App.NOTIFIER.getSettings().getStreamTipClientID() + " " +
                App.NOTIFIER.getSettings().getStreamTipAccessToken());
    }
}