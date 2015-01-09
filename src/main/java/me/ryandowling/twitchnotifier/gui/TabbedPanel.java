package me.ryandowling.twitchnotifier.gui;

import javax.swing.JTabbedPane;

public class TabbedPanel extends JTabbedPane {
    public TabbedPanel() {
        super(JTabbedPane.TOP);

        addTab("Console", new ConsolePanel());
        addTab("Followers", new FollowerPanel());
        addTab("Test", new TestPanel());
        addTab("Settings", new SettingsPanel());
    }
}
