package me.ryandowling.allmightytwitchtoolbox.gui;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.SettingsListener;
import me.ryandowling.allmightytwitchtoolbox.events.managers.SettingsManager;

import javax.swing.JTabbedPane;

public class TabbedPanel extends JTabbedPane implements SettingsListener {
    private DashboardPanel dashboardPanel = new DashboardPanel();
    private ConsolePanel consolePanel = new ConsolePanel();
    private FollowerPanel followerPanel = new FollowerPanel();
    private ViewerCountPanel viewerCountPanel = new ViewerCountPanel();
    private DonationPanel donationPanel = new DonationPanel();
    private TimerPanel timerPanel = new TimerPanel();
    private ToolPanel toolsPanel = new ToolPanel();
    private TestPanel testPanel = new TestPanel();
    private SettingsPanel settingsPanel = new SettingsPanel();

    public TabbedPanel() {
        super(JTabbedPane.TOP);

        SettingsManager.addListener(this);

        addTab("Dashboard", this.dashboardPanel);
        addTab("Console", this.consolePanel);
        addTab("Viewers", this.viewerCountPanel);
        addTab("Followers", this.followerPanel);
        addTab("Donations", this.donationPanel);
        addTab("Timer", this.timerPanel);
        addTab("Tools", this.toolsPanel);
        addTab("Test", this.testPanel);
        addTab("Settings", this.settingsPanel);

        if (!App.NOTIFIER.getSettings().isSetup()) {
            this.setSelectedComponent(this.consolePanel);
            updateTabs(false);
        }
    }

    private void updateTabs(boolean b) {
        this.setEnabledAt(0, b);
        this.setEnabledAt(2, b);
        this.setEnabledAt(3, b);
        this.setEnabledAt(4, b);
        this.setEnabledAt(5, b);
        this.setEnabledAt(6, b);
        this.setEnabledAt(7, b);
    }

    @Override
    public void onSetupComplete() {
        App.NOTIFIER.setup(false);
        updateTabs(true);
    }

    @Override
    public void onSetupInvalidated() {
        this.setSelectedComponent(this.consolePanel);
        updateTabs(false);
    }
}
