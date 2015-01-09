package me.ryandowling.twitchnotifier.gui;

import me.ryandowling.twitchnotifier.data.streamtip.StreamTipTip;
import me.ryandowling.twitchnotifier.data.twitch.TwitchFollower;
import me.ryandowling.twitchnotifier.events.managers.DonationManager;
import me.ryandowling.twitchnotifier.events.managers.FollowerManager;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestPanel extends JPanel {
    private JPanel buttonPanel;

    private JPanel newFollowerPanel;
    private JTextField newFollowerName;
    private JButton testNewFollower;

    private JPanel newDonationPanel;
    private JTextField newDonationName;
    private JTextField newDonationAmount;
    private JButton testNewDonation;

    public TestPanel() {
        super();
        setupPanel();
        setupButtons();
        addComponents();
    }

    private void setupPanel() {
        setLayout(new BorderLayout());
    }

    private void setupButtons() {
        this.buttonPanel = new JPanel();
        this.buttonPanel.setLayout(new BoxLayout(this.buttonPanel, BoxLayout.Y_AXIS));

        this.newFollowerPanel = new JPanel();
        this.newFollowerPanel.setLayout(new FlowLayout());

        this.newFollowerName = new JTextField(16);

        this.testNewFollower = new JButton("Test New Follower");
        this.testNewFollower.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.testNewFollower.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!newFollowerName.getText().isEmpty()) {
                    FollowerManager.newFollow(new TwitchFollower().create(newFollowerName.getText()));
                }
            }
        });

        this.newDonationPanel = new JPanel();
        this.newDonationPanel.setLayout(new FlowLayout());

        this.newDonationName = new JTextField(16);
        this.newDonationAmount = new JTextField(16);

        this.testNewDonation = new JButton("Test New Donation");
        this.testNewDonation.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.testNewDonation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!newDonationName.getText().isEmpty()) {
                    DonationManager.newDonation(new StreamTipTip().create(newDonationName.getText(),
                            newDonationAmount.getText()));
                }
            }
        });
    }

    private void addComponents() {
        this.newFollowerPanel.add(this.newFollowerName);
        this.newFollowerPanel.add(this.testNewFollower);

        this.newDonationPanel.add(this.newDonationName);
        this.newDonationPanel.add(this.newDonationAmount);
        this.newDonationPanel.add(this.testNewDonation);

        add(this.buttonPanel, BorderLayout.CENTER);

        buttonPanel.add(this.newFollowerPanel);
        buttonPanel.add(this.newDonationPanel);
    }
}
