package me.ryandowling.allmightytwitchtoolbox.events.managers;

import me.ryandowling.allmightytwitchtoolbox.events.listeners.ViewerCountListener;

import javax.swing.SwingUtilities;
import java.util.LinkedList;
import java.util.List;

public class ViewerCountManager {
    private static final List<ViewerCountListener> listeners = new LinkedList<ViewerCountListener>();

    public static synchronized void addListener(ViewerCountListener listener) {
        listeners.add(listener);
    }

    public static synchronized void removeListener(ViewerCountListener listener) {
        listeners.remove(listener);
    }

    public static synchronized void viewerCountChanged(final int viewerCount) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (ViewerCountListener listener : listeners) {
                    listener.onViewerCountChanged(viewerCount);
                }
            }
        });
    }

    public static synchronized void viewerCountDataAdded(final int viewerCount) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (ViewerCountListener listener : listeners) {
                    listener.onViewerCountDataAdded(viewerCount);
                }
            }
        });
    }
}
