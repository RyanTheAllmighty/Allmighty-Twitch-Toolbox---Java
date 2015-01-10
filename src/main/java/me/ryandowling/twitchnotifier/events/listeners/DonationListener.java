package me.ryandowling.twitchnotifier.events.listeners;

import me.ryandowling.twitchnotifier.data.interfaces.Donation;

public interface DonationListener {
    public void onNewDonation(final Donation donation);
}
