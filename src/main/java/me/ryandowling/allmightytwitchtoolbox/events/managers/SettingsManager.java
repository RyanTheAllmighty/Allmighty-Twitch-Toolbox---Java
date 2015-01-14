package me.ryandowling.allmightytwitchtoolbox.events.managers;

import me.ryandowling.allmightytwitchtoolbox.events.listeners.SettingsListener;

import javax.swing.SwingUtilities;
import java.util.LinkedList;
import java.util.List;

public class SettingsManager {
    private static final List<SettingsListener> listeners = new LinkedList<SettingsListener>();

    public static synchronized void addListener(SettingsListener listener) {
        listeners.add(listener);
    }

    public static synchronized void removeListener(SettingsListener listener) {
        listeners.remove(listener);
    }

    public static synchronized void setupComplete() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (SettingsListener listener : listeners) {
                    listener.onSetupComplete();
                }
            }
        });
    }

    public static synchronized void setupInvalidated() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (SettingsListener listener : listeners) {
                    listener.onSetupInvalidated();
                }
            }
        });
    }
}
