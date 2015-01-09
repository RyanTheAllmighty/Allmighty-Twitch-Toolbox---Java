package me.ryandowling.twitchnotifier.events.listeners;

import me.ryandowling.twitchnotifier.data.streamtip.StreamTipTip;

public interface DonationListener {
    public void onNewDonation(final StreamTipTip donation);
}
