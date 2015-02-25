package me.ryandowling.allmightytwitchtoolbox.events;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Follower;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.FollowerListener;
import me.ryandowling.allmightytwitchtoolbox.events.managers.FollowerManager;
import me.ryandowling.allmightytwitchtoolbox.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.IOException;

public class FollowerFiles implements FollowerListener {
    public FollowerFiles() {
        FollowerManager.addListener(this);
    }

    @Override
    public void onNewFollow(final Follower follower) {
        try {
            FileUtils.write(Utils.getLatestFollowerFile().toFile(), App.NOTIFIER.getLatestFollower().getDisplayName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFiles() {
        try {
            FileUtils.write(Utils.getLatestFollowerFile().toFile(), App.NOTIFIER.getLatestFollower().getDisplayName());

            FileUtils.write(Utils.getNumberOfFollowersFile().toFile(), "" + App.NOTIFIER.getFollowersTotalFormatted());

            FileUtils.write(Utils.getFollowersTallyFile().toFile(), "" + App.NOTIFIER.getFollowersTallyFormatted());

            FileUtils.write(Utils.getFollowerGoalFile().toFile(), App.NOTIFIER.getFollowersTotalFormatted() + " / " +
                    App.NOTIFIER.getFollowerGoalFormatted());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFollowersNumberChanged(final int newTotal) {
        try {
            FileUtils.write(Utils.getNumberOfFollowersFile().toFile(), "" + App.NOTIFIER.getFollowersTotalFormatted());

            FileUtils.write(Utils.getFollowersTallyFile().toFile(), "" + App.NOTIFIER.getFollowersTallyFormatted());

            FileUtils.write(Utils.getFollowerGoalFile().toFile(), App.NOTIFIER.getFollowersTotalFormatted() + " / " +
                    App.NOTIFIER.getFollowerGoalFormatted());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
