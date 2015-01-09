package me.ryandowling.twitchnotifier.events;

import io.github.asyncronous.toast.Toaster;
import me.ryandowling.twitchnotifier.App;
import me.ryandowling.twitchnotifier.data.twitch.TwitchFollower;
import me.ryandowling.twitchnotifier.events.listeners.FollowerListener;
import me.ryandowling.twitchnotifier.events.managers.FollowerManager;
import me.ryandowling.twitchnotifier.utils.SoundPlayer;

public class FollowerAlert implements FollowerListener {
    SoundPlayer soundPlayer = new SoundPlayer();

    public FollowerAlert() {
        FollowerManager.addListener(this);
    }

    @Override
    public void onNewFollow(final TwitchFollower follower) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Toaster.instance().pop("New Follower: " + follower.getUser().getDisplayName());
                SoundPlayer.playSound(App.NOTIFIER.getSettings().getNewFollowSound());
            }
        });
        thread.run();
    }
}
