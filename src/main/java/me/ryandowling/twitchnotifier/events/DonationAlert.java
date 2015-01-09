package me.ryandowling.twitchnotifier.events;

import io.github.asyncronous.toast.Toaster;
import me.ryandowling.twitchnotifier.App;
import me.ryandowling.twitchnotifier.data.streamtip.StreamTipTip;
import me.ryandowling.twitchnotifier.events.listeners.DonationListener;
import me.ryandowling.twitchnotifier.events.managers.DonationManager;
import me.ryandowling.twitchnotifier.utils.SoundPlayer;

public class DonationAlert implements DonationListener {
    SoundPlayer soundPlayer = new SoundPlayer();

    public DonationAlert() {
        DonationManager.addListener(this);
    }

    @Override
    public void onNewDonation(final StreamTipTip tip) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Toaster.instance().pop("New Donation: " + tip.getUsername() + " donated " + tip.getPrintableAmount());
                SoundPlayer.playSound(App.NOTIFIER.getSettings().getNewDonationSound());
            }
        });
        thread.run();
    }
}
