package me.ryandowling.allmightytwitchtoolbox.events.listeners;

import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Donation;

public interface DonationListener {
    public void onNewDonation(final Donation donation);
}
