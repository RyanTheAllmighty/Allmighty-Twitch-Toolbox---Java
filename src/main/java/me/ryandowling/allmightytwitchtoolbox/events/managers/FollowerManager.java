package me.ryandowling.allmightytwitchtoolbox.events.managers;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.Logger;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Follower;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.FollowerListener;

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

    public static synchronized void newFollow(final Follower follower) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (App.NOTIFIER.addFollower(follower)) {
                    Logger.log(follower.getDisplayName() + " followed the channel!");

                    for (FollowerListener listener : listeners) {
                        listener.onNewFollow(follower);
                    }
                }
            }
        });
    }

    public static synchronized void followersNumberChanged(final int newTotal) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (FollowerListener listener : listeners) {
                    listener.onFollowersNumberChanged(newTotal);
                }
            }
        });
    }
}
