package me.ryandowling.allmightytwitchtoolbox.data.twitch;

import java.util.List;

public class TwitchUserFollows {
    private List<TwitchFollower> follows;
    private int _total;

    public List<TwitchFollower> getFollows() {
        return this.follows;
    }

    public int getTotalFollowers() {
        return this._total;
    }
}
