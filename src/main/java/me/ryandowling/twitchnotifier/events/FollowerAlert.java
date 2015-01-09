package me.ryandowling.twitchnotifier.events;

import io.github.asyncronous.toast.Toaster;
import me.ryandowling.twitchnotifier.data.twitch.TwitchFollower;
import me.ryandowling.twitchnotifier.events.listeners.FollowerListener;
import me.ryandowling.twitchnotifier.events.managers.FollowerManager;

public class FollowerAlert implements FollowerListener {
    public FollowerAlert() {
        FollowerManager.addListener(this);
    }

    @Override
    public void onNewFollow(TwitchFollower follower) {
        Toaster.instance().pop("New Follower: " + follower.getUser().getDisplayName());
    }
}
