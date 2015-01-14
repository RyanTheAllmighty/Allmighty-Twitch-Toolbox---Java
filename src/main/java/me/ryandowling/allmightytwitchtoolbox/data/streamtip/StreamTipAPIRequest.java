package me.ryandowling.allmightytwitchtoolbox.data.streamtip;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.data.APIRequest;

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