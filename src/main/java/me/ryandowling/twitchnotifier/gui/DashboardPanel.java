package me.ryandowling.twitchnotifier.gui;

import me.ryandowling.twitchnotifier.App;
import me.ryandowling.twitchnotifier.data.interfaces.Follower;
import me.ryandowling.twitchnotifier.events.listeners.FollowerListener;
import me.ryandowling.twitchnotifier.events.managers.FollowerManager;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardPanel extends JPanel implements FollowerListener {
    private JPanel mainPane;
    private JPanel buttonPane;

    private JPanel totalFollowersPanel;
    private JLabel totalFollowersLabel;
    private JLabel totalFollowers;

    private JPanel followersTallyPanel;
    private JLabel followersTallyLabel;
    private JLabel followersTally;

    private JButton saveButton;

    public DashboardPanel() {
        super();
        setLayout(new BorderLayout());

        setupPanes();
        addComponents();

        FollowerManager.addListener(this);
    }

    private void setupPanes() {
        this.mainPane = new JPanel();
        this.mainPane.setLayout(new BoxLayout(this.mainPane, BoxLayout.Y_AXIS));

        this.buttonPane = new JPanel();
        this.buttonPane.setLayout(new FlowLayout());

        setupTotalFollowersPanel();
        setupFollowersTallyPanel();
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

    private void setupFollowersTallyPanel() {
        this.followersTallyPanel = new JPanel();
        this.followersTallyPanel.setLayout(new FlowLayout());

        this.followersTallyLabel = new JLabel("Followers Tally:");
        this.followersTally = new JLabel(String.valueOf(App.NOTIFIER.getFollowersTally()));

        this.followersTallyPanel.add(this.followersTallyLabel);
        this.followersTallyPanel.add(this.followersTally);
    }

    private void setupButtonPanel() {
        this.saveButton = new JButton("Reset Follower Tally");
        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                App.NOTIFIER.resetFollowersTally();
                followersTally.setText("0");
            }
        });
    }

    private void addComponents() {
        this.mainPane.add(this.totalFollowersPanel);
        this.mainPane.add(this.followersTallyPanel);

        this.buttonPane.add(this.saveButton);

        add(this.mainPane, BorderLayout.CENTER);
        add(this.buttonPane, BorderLayout.SOUTH);
    }

    @Override
    public void onNewFollow(Follower follower) {
        this.totalFollowers.setText(String.valueOf(App.NOTIFIER.getFollowers().size()));
        this.followersTally.setText(String.valueOf(App.NOTIFIER.getFollowersTally()));
    }
}
