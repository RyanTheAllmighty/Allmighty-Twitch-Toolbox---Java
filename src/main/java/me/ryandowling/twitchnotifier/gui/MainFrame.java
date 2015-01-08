package me.ryandowling.twitchnotifier.gui;

import me.ryandowling.twitchnotifier.App;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Twitch Notifier by RyanTheAllmighty");
        setSize(App.NOTIFIER.getSettings().getGuiSize());
        setLocation(App.NOTIFIER.getSettings().getGuiPosition());
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        this.setLayout(new BorderLayout());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                exit();
            }
        });

        setVisible(true);
    }

    private void exit() {
        System.out.println("Exiting the application!");
        App.NOTIFIER.saveSettings();
        System.exit(0);
    }
}
