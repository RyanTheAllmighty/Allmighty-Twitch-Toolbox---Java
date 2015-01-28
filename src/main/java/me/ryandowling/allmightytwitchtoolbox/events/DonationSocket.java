package me.ryandowling.allmightytwitchtoolbox.events;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Donation;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.DonationListener;
import me.ryandowling.allmightytwitchtoolbox.events.managers.DonationManager;

import java.util.HashMap;
import java.util.Map;

public class DonationSocket implements DonationListener {
    public DonationSocket() {
        DonationManager.addListener(this);
    }

    @Override
    public void onNewDonation(final Donation donation) {
        Map<String, String> data = new HashMap<>();

        data.put("username", donation.getUsername());
        data.put("printableAmount", donation.getPrintableAmount());

        App.NOTIFIER.sendSocketMessage("newdonation", data);
    }
}
