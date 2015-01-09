package me.ryandowling.twitchnotifier.events.managers;

import me.ryandowling.twitchnotifier.App;
import me.ryandowling.twitchnotifier.Logger;
import me.ryandowling.twitchnotifier.data.streamtip.StreamTipTip;
import me.ryandowling.twitchnotifier.events.listeners.DonationListener;
import me.ryandowling.twitchnotifier.events.listeners.FollowerListener;

import javax.swing.SwingUtilities;
import java.util.LinkedList;
import java.util.List;

public class DonationManager {
    private static final List<DonationListener> listeners = new LinkedList<DonationListener>();

    public static synchronized void addListener(DonationListener listener) {
        listeners.add(listener);
    }

    public static synchronized void removeListener(DonationListener listener) {
        listeners.remove(listener);
    }

    public static synchronized void newDonation(final StreamTipTip donation) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (App.NOTIFIER.addDonation(donation)) {
                    Logger.log(donation.getUsername() + " donated " + donation.getPrintableAmount() + "!");

                    for (DonationListener listener : listeners) {
                        listener.onNewDonation(donation);
                    }
                }
            }
        });
    }
}
