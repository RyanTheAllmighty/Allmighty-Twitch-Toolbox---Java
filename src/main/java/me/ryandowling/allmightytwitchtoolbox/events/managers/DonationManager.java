package me.ryandowling.allmightytwitchtoolbox.events.managers;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.Logger;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Donation;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.DonationListener;

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

    public static synchronized void newDonation(final Donation donation) {
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
