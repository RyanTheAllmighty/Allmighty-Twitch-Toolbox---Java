package me.ryandowling.allmightytwitchtoolbox.gui;

import me.ryandowling.allmightytwitchtoolbox.App;

import javax.swing.JTabbedPane;

public class TabbedPanel extends JTabbedPane {
    private DashboardPanel dashboardPanel = new DashboardPanel();
    private ConsolePanel consolePanel = new ConsolePanel();
    private FollowerPanel followerPanel = new FollowerPanel();
    private DonationPanel donationPanel = new DonationPanel();
    private TestPanel testPanel = new TestPanel();
    private SettingsPanel settingsPanel = new SettingsPanel();

    public TabbedPanel() {
        super(JTabbedPane.TOP);

        addTab("Dashboard", this.dashboardPanel);
        addTab("Console", this.consolePanel);
        addTab("Followers", this.followerPanel);
        addTab("Donations", this.donationPanel);
        addTab("Test", this.testPanel);
        addTab("Settings", this.settingsPanel);

        if (!App.NOTIFIER.getSettings().isSetup()) {
            this.setEnabledAt(0, false);
            this.setEnabledAt(2, false);
            this.setEnabledAt(3, false);
            this.setEnabledAt(4, false);
        }
    }
}
