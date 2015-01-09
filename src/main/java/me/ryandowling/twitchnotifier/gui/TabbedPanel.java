package me.ryandowling.twitchnotifier.gui;

import me.ryandowling.twitchnotifier.App;

import javax.swing.JTabbedPane;

public class TabbedPanel extends JTabbedPane {
    private ConsolePanel consolePanel = new ConsolePanel();
    private FollowerPanel followerPanel = new FollowerPanel();
    private DonationPanel donationPanel = new DonationPanel();
    private TestPanel testPanel = new TestPanel();
    private SettingsPanel settingsPanel = new SettingsPanel();

    public TabbedPanel() {
        super(JTabbedPane.TOP);

        addTab("Console", this.consolePanel);
        addTab("Followers", this.followerPanel);
        addTab("Donations", this.donationPanel);
        addTab("Test", this.testPanel);
        addTab("Settings", this.settingsPanel);

        if (!App.NOTIFIER.getSettings().isSetup()) {
            this.setEnabledAt(1, false);
            this.setEnabledAt(2, false);
            this.setEnabledAt(3, false);
        }
    }
}
