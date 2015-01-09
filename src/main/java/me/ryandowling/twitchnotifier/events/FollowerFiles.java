package me.ryandowling.twitchnotifier.events;

import me.ryandowling.twitchnotifier.App;
import me.ryandowling.twitchnotifier.data.twitch.TwitchFollower;
import me.ryandowling.twitchnotifier.events.listeners.FollowerListener;
import me.ryandowling.twitchnotifier.events.managers.FollowerManager;
import me.ryandowling.twitchnotifier.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.IOException;

public class FollowerFiles implements FollowerListener {
    public FollowerFiles() {
        FollowerManager.addListener(this);
    }

    @Override
    public void onNewFollow(final TwitchFollower follower) {
        writeFiles();
    }

    public void writeFiles() {
        try {
            TwitchFollower latestFollower = App.NOTIFIER.getFollowers().entrySet().iterator().next().getValue();

            FileUtils.write(Utils.getLatestFollowerFile().toFile(), latestFollower.getUser().getDisplayName());
            FileUtils.write(Utils.getNumberOfFollowerFile().toFile(), "" + App.NOTIFIER.getFollowers().size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
