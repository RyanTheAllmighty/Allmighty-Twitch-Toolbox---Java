package me.ryandowling.allmightytwitchtoolbox.events.listeners;

import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Follower;

public interface FollowerListener {
    public void onNewFollow(final Follower follower);
}
