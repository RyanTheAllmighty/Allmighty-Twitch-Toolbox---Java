package me.ryandowling.twitchnotifier.gui;

import me.ryandowling.twitchnotifier.data.twitch.TwitchFollower;
import me.ryandowling.twitchnotifier.events.managers.FollowerManager;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestPanel extends JPanel {
    private JButton testNewFollower;
    private JPanel buttonPanel;

    public TestPanel() {
        super();

        setLayout(new BorderLayout());

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        testNewFollower = new JButton("Test New Follower");
        testNewFollower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FollowerManager.newFollow(new TwitchFollower().create("Test User"));
            }
        });

        buttonPanel.add(this.testNewFollower);

        add(buttonPanel, BorderLayout.CENTER);
    }
}
