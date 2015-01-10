package me.ryandowling.twitchnotifier.gui;

import me.ryandowling.twitchnotifier.App;
import me.ryandowling.twitchnotifier.data.interfaces.Donation;
import me.ryandowling.twitchnotifier.data.interfaces.Follower;
import me.ryandowling.twitchnotifier.events.listeners.DonationListener;
import me.ryandowling.twitchnotifier.events.listeners.FollowerListener;
import me.ryandowling.twitchnotifier.events.managers.DonationManager;
import me.ryandowling.twitchnotifier.events.managers.FollowerManager;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardPanel extends JPanel implements FollowerListener, DonationListener {
    private JPanel mainPane;
    private JPanel buttonPane;

    private JPanel totalFollowersPanel;
    private JLabel totalFollowersLabel;
    private JLabel totalFollowers;

    private JPanel totalDonationsPanel;
    private JLabel totalDonationsLabel;
    private JLabel totalDonations;

    private JPanel followersTallyPanel;
    private JLabel followersTallyLabel;
    private JLabel followersTally;

    private JPanel donationsTallyPanel;
    private JLabel donationsTallyLabel;
    private JLabel donationsTally;

    private JButton resetFollowerTally;
    private JButton resetDonationTally;

    public DashboardPanel() {
        super();
        setLayout(new BorderLayout());

        setupPanes();
        addComponents();

        updateValues();

        FollowerManager.addListener(this);
        DonationManager.addListener(this);
    }

    private void setupPanes() {
        this.mainPane = new JPanel();
        this.mainPane.setLayout(new BoxLayout(this.mainPane, BoxLayout.Y_AXIS));

        this.buttonPane = new JPanel();
        this.buttonPane.setLayout(new FlowLayout());

        setupTotalFollowersPanel();
        setupTotalDonationsPanel();
        setupFollowersTallyPanel();
        setupDonationsTallyPanel();
        setupButtonPanel();
    }

    private void setupTotalFollowersPanel() {
        this.totalFollowersPanel = new JPanel();
        this.totalFollowersPanel.setLayout(new FlowLayout());

        this.totalFollowersLabel = new JLabel("Total Followers:");
        this.totalFollowers = new JLabel(String.valueOf(App.NOTIFIER.getFollowers().size()));

        this.totalFollowersPanel.add(this.totalFollowersLabel);
        this.totalFollowersPanel.add(this.totalFollowers);
    }

    private void setupTotalDonationsPanel() {
        this.totalDonationsPanel = new JPanel();
        this.totalDonationsPanel.setLayout(new FlowLayout());

        this.totalDonationsLabel = new JLabel("Total Donations:");
        this.totalDonations = new JLabel("$" + String.valueOf(App.NOTIFIER.getDonationsTotal()));

        this.totalDonationsPanel.add(this.totalDonationsLabel);
        this.totalDonationsPanel.add(this.totalDonations);
    }

    private void setupFollowersTallyPanel() {
        this.followersTallyPanel = new JPanel();
        this.followersTallyPanel.setLayout(new FlowLayout());

        this.followersTallyLabel = new JLabel("Followers Tally:");
        this.followersTally = new JLabel(String.valueOf(App.NOTIFIER.getFollowersTally()));

        this.followersTallyPanel.add(this.followersTallyLabel);
        this.followersTallyPanel.add(this.followersTally);
    }

    private void setupDonationsTallyPanel() {
        this.donationsTallyPanel = new JPanel();
        this.donationsTallyPanel.setLayout(new FlowLayout());

        this.donationsTallyLabel = new JLabel("Donations Tally:");
        this.donationsTally = new JLabel();

        this.donationsTallyPanel.add(this.donationsTallyLabel);
        this.donationsTallyPanel.add(this.donationsTally);
    }

    private void setupButtonPanel() {
        this.resetFollowerTally = new JButton("Reset Follower Tally");
        this.resetFollowerTally.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.NOTIFIER.resetFollowersTally();
                followersTally.setText("0");
            }
        });

        this.resetDonationTally = new JButton("Reset Donation Tally");
        this.resetDonationTally.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.NOTIFIER.resetDonationsTally();
                resetDonationTally.setText("$0.00");
            }
        });
    }

    private void addComponents() {
        this.mainPane.add(this.totalFollowersPanel);
        this.mainPane.add(this.totalDonationsPanel);
        this.mainPane.add(this.followersTallyPanel);
        this.mainPane.add(this.donationsTallyPanel);

        this.buttonPane.add(this.resetFollowerTally);
        this.buttonPane.add(this.resetDonationTally);

        add(this.mainPane, BorderLayout.CENTER);
        add(this.buttonPane, BorderLayout.SOUTH);
    }

    @Override
    public void onNewFollow(Follower follower) {
        updateFollowerValues();
    }

    @Override
    public void onNewDonation(Donation donation) {
        updateDonationValues();
    }

    public void updateValues() {
        updateFollowerValues();
        updateDonationValues();
    }

    public void updateFollowerValues() {
        this.totalFollowers.setText(String.valueOf(App.NOTIFIER.getFollowers().size()));
        this.followersTally.setText(String.valueOf(App.NOTIFIER.getFollowersTally()));
    }

    public void updateDonationValues() {
        this.totalDonations.setText("$" + String.format("%.2f", App.NOTIFIER.getDonationsTotal()));
        this.donationsTally.setText("$" + String.format("%.2f", App.NOTIFIER.getDonationsTally()));
    }
}
