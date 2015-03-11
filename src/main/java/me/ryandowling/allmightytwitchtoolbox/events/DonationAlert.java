package me.ryandowling.allmightytwitchtoolbox.events;

import io.github.asyncronous.toast.Toaster;
import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Donation;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.DonationListener;
import me.ryandowling.allmightytwitchtoolbox.events.managers.DonationManager;
import me.ryandowling.allmightytwitchtoolbox.utils.SoundPlayer;

public class DonationAlert implements DonationListener {
    SoundPlayer soundPlayer = new SoundPlayer();

    public DonationAlert() {
        DonationManager.addListener(this);
    }

    @Override
    public void onNewDonation(final Donation tip) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Toaster.instance().pop("New Donation: " + tip.getUsername() + " donated " + tip.getPrintableAmount());
                SoundPlayer.playSound(App.NOTIFIER.getSettings().getNewDonationSound(), App.NOTIFIER.getSettings().getNotificationVolume());
            }
        });
        thread.run();
    }
}
