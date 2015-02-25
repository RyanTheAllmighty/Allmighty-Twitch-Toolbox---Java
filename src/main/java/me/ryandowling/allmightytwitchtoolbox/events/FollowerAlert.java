package me.ryandowling.allmightytwitchtoolbox.events;

import io.github.asyncronous.toast.Toaster;
import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Follower;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.FollowerListener;
import me.ryandowling.allmightytwitchtoolbox.events.managers.FollowerManager;
import me.ryandowling.allmightytwitchtoolbox.utils.SoundPlayer;

public class FollowerAlert implements FollowerListener {
    public FollowerAlert() {
        FollowerManager.addListener(this);
    }

    @Override
    public void onNewFollow(final Follower follower) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Toaster.instance().pop("New Follower: " + follower.getDisplayName());
                SoundPlayer.playSound(App.NOTIFIER.getSettings().getNewFollowSound());
            }
        });
        thread.run();
    }

    @Override
    public void onFollowersNumberChanged(final int newTotal) {

    }
}
