package me.ryandowling.allmightytwitchtoolbox.events;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Follower;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.FollowerListener;
import me.ryandowling.allmightytwitchtoolbox.events.managers.FollowerManager;

import java.util.HashMap;
import java.util.Map;

public class FollowerSocket implements FollowerListener {
    public FollowerSocket() {
        FollowerManager.addListener(this);
    }

    @Override
    public void onNewFollow(final Follower follower) {
        Map<String, String> data = new HashMap<>();

        data.put("displayName", follower.getDisplayName());

        App.NOTIFIER.sendSocketMessage("newfollower", data);
    }
}
