package me.ryandowling.allmightytwitchtoolbox.events;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Donation;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.DonationListener;
import me.ryandowling.allmightytwitchtoolbox.events.managers.DonationManager;
import me.ryandowling.allmightytwitchtoolbox.utils.Utils;
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

            FileUtils.write(Utils.getDonationGoalFile().toFile(), App.NOTIFIER.getDonationTotalFormatted() + " / " +
                    App.NOTIFIER.getDonationGoalFormatted());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
