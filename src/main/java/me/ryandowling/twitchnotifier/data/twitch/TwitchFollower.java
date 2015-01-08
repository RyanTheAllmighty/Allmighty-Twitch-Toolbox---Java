package me.ryandowling.twitchnotifier.data.twitch;

public class TwitchFollower {
    private boolean notifications;
    private TwitchUser user;

    public boolean hasEnabledNotifications() {
        return this.notifications;
    }

    public TwitchUser getUser() {
        return this.user;
    }
}
