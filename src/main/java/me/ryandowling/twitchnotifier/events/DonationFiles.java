package me.ryandowling.twitchnotifier.events;

import me.ryandowling.twitchnotifier.App;
import me.ryandowling.twitchnotifier.data.interfaces.Donation;
import me.ryandowling.twitchnotifier.events.listeners.DonationListener;
import me.ryandowling.twitchnotifier.events.managers.DonationManager;
import me.ryandowling.twitchnotifier.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.IOException;

public class DonationFiles implements DonationListener {
    public DonationFiles() {
        DonationManager.addListener(this);
    }

    @Override
    public void onNewDonation(final Donation tip) {
        writeFiles();
    }

    public void writeFiles() {
        try {
            Donation tip = App.NOTIFIER.getLatestDonation();

            FileUtils.write(Utils.getLatestDonationFile().toFile(), tip.getUsername() + ": " + tip.getPrintableAmount
                    ());
            FileUtils.write(Utils.getDonationsTallyFile().toFile(), "" + App.NOTIFIER.getDonationsTally());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
