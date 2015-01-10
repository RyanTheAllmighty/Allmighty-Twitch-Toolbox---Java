package me.ryandowling.twitchnotifier.events.listeners;

import me.ryandowling.twitchnotifier.data.interfaces.Follower;

public interface FollowerListener {
    public void onNewFollow(final Follower follower);
}
