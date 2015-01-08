package me.ryandowling.twitchnotifier.events.listeners;

import me.ryandowling.twitchnotifier.data.twitch.TwitchFollower;

public interface FollowerListener {
    public void onNewFollow(final TwitchFollower follower);
}
