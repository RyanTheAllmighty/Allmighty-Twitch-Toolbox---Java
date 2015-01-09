package me.ryandowling.twitchnotifier.events.managers;

import me.ryandowling.twitchnotifier.App;
import me.ryandowling.twitchnotifier.Logger;
import me.ryandowling.twitchnotifier.data.twitch.TwitchFollower;
import me.ryandowling.twitchnotifier.events.listeners.FollowerListener;

import javax.swing.SwingUtilities;
import java.util.LinkedList;
import java.util.List;

public class FollowerManager {
    private static final List<FollowerListener> listeners = new LinkedList<FollowerListener>();

    public static synchronized void addListener(FollowerListener listener) {
        listeners.add(listener);
    }

    public static synchronized void removeListener(FollowerListener listener) {
        listeners.remove(listener);
    }

    public static synchronized void newFollow(final TwitchFollower follower) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (App.NOTIFIER.addFollower(follower)) {
                    Logger.log(follower.getUser().getDisplayName() + " followed the channel!");

                    for (FollowerListener listener : listeners) {
                        listener.onNewFollow(follower);
                    }
                }
            }
        });
    }
}
