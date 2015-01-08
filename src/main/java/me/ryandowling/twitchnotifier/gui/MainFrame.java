package me.ryandowling.twitchnotifier.gui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    public MainFrame() {
        setSize(new Dimension(600, 400));
        setTitle("Twitch Notifier by RyanTheAllmighty");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                exit();
            }
        });
    }

    private void exit() {
        System.out.println("Exiting the application!");
        System.exit(0);
    }
}
