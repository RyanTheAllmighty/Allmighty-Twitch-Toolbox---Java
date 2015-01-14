package me.ryandowling.allmightytwitchtoolbox.gui;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.SettingsListener;
import me.ryandowling.allmightytwitchtoolbox.events.managers.SettingsManager;

import javax.swing.JTabbedPane;

public class TabbedPanel extends JTabbedPane implements SettingsListener {
    private DashboardPanel dashboardPanel = new DashboardPanel();
    private ConsolePanel consolePanel = new ConsolePanel();
    private FollowerPanel followerPanel = new FollowerPanel();
    private DonationPanel donationPanel = new DonationPanel();
    private TestPanel testPanel = new TestPanel();
    private SettingsPanel settingsPanel = new SettingsPanel();

    public TabbedPanel() {
        super(JTabbedPane.TOP);

        SettingsManager.addListener(this);

        addTab("Dashboard", this.dashboardPanel);
        addTab("Console", this.consolePanel);
        addTab("Followers", this.followerPanel);
        addTab("Donations", this.donationPanel);
        addTab("Test", this.testPanel);
        addTab("Settings", this.settingsPanel);

        if (!App.NOTIFIER.getSettings().isSetup()) {
            this.setSelectedComponent(this.consolePanel);

            this.setEnabledAt(0, false);
            this.setEnabledAt(2, false);
            this.setEnabledAt(3, false);
            this.setEnabledAt(4, false);
        }
    }

    @Override
    public void onSetupComplete() {
        App.NOTIFIER.setup(false);
        
        this.setEnabledAt(0, true);
        this.setEnabledAt(2, true);
        this.setEnabledAt(3, true);
        this.setEnabledAt(4, true);
    }

    @Override
    public void onSetupInvalidated() {
        this.setSelectedComponent(this.consolePanel);

        this.setEnabledAt(0, false);
        this.setEnabledAt(2, false);
        this.setEnabledAt(3, false);
        this.setEnabledAt(4, false);
    }
}
