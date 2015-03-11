package me.ryandowling.allmightytwitchtoolbox.gui.settings;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.exceptions.SettingsException;
import me.ryandowling.allmightytwitchtoolbox.utils.Utils;
import org.apache.commons.io.FileUtils;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ApplicationSettingsPanel extends JPanel {
    private JPanel mainPane;

    private JPanel twitchUsernamePanel;
    private JLabel twitchUsernameLabel;
    private JTextField twitchUsernameTextField;

    private JPanel twitchAPITokenPanel;
    private JLabel twitchAPITokenLabel;
    private JTextField twitchAPITokenTextField;
    private JButton twitchAPITokenButton;

    private JPanel twitchAPIClientIDPanel;
    private JLabel twitchAPIClientIDLabel;
    private JTextField twitchAPIClientIDTextField;
    private JButton twitchAPIClientIDButton;

    private JPanel streamTipClientIDPanel;
    private JLabel streamTipClientIDLabel;
    private JTextField streamTipClientIDTextField;

    private JPanel streamTipAccessTokenPanel;
    private JLabel streamTipAccessTokenLabel;
    private JTextField streamTipAccessTokenTextField;

    private JPanel timeBetweenFollowerChecksPanel;
    private JLabel timeBetweenFollowerChecksLabel;
    private JSpinner timeBetweenFollowerChecksSpinner;

    private JPanel timeBetweenDonationChecksPanel;
    private JLabel timeBetweenDonationChecksLabel;
    private JSpinner timeBetweenDonationChecksSpinner;

    private JPanel timeBetweenViewerCountChecksPanel;
    private JLabel timeBetweenViewerCountChecksLabel;
    private JSpinner timeBetweenViewerCountChecksSpinner;

    private JPanel numberOfPointsOnViewerChartPanel;
    private JLabel numberOfPointsOnViewerChartLabel;
    private JSpinner numberOfPointsOnViewerChartSpinner;

    private JPanel serverPortPanel;
    private JLabel serverPortLabel;
    private JTextField serverPortTextField;

    public ApplicationSettingsPanel() {
        super();
        setLayout(new BorderLayout());

        setupPanes();
        addComponents();
    }

    private void setupPanes() {
        this.mainPane = new JPanel();
        this.mainPane.setLayout(new BoxLayout(this.mainPane, BoxLayout.Y_AXIS));

        setupTwitchUsernamePanel();
        setupTwitchAPITokenPanel();
        setupTwitchAPIClientIDPanel();
        setupStreamTipClientIDPanel();
        setupStreamTipAccessTokenPanel();
        setupTimeBetweenFollowerChecksPanel();
        setupTimeBetweenDonationChecksPanel();
        setupTimeBetweenViewerCountChecksPanel();
        setupNumberOfPointsOnViewerChartPanel();
        setupServerPortPanel();
    }

    private void setupTwitchUsernamePanel() {
        this.twitchUsernamePanel = new JPanel();
        this.twitchUsernamePanel.setLayout(new FlowLayout());

        this.twitchUsernameLabel = new JLabel("Twitch Username:");
        this.twitchUsernameTextField = new JTextField(App.NOTIFIER.getSettings().getTwitchUsername(), 16);

        this.twitchUsernamePanel.add(this.twitchUsernameLabel);
        this.twitchUsernamePanel.add(this.twitchUsernameTextField);
    }

    private void setupTwitchAPITokenPanel() {
        this.twitchAPITokenPanel = new JPanel();
        this.twitchAPITokenPanel.setLayout(new FlowLayout());

        this.twitchAPITokenLabel = new JLabel("Twitch API Token:");

        this.twitchAPITokenTextField = new JTextField(App.NOTIFIER.getSettings().getTwitchAPIToken(), 16);

        this.twitchAPITokenButton = new JButton("?");
        this.twitchAPITokenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utils.openLink("http://www.ryandowling.me/twitch-api-token-generator/");
            }
        });

        this.twitchAPITokenPanel.add(this.twitchAPITokenLabel);
        this.twitchAPITokenPanel.add(this.twitchAPITokenTextField);
        this.twitchAPITokenPanel.add(this.twitchAPITokenButton);
    }

    private void setupTwitchAPIClientIDPanel() {
        this.twitchAPIClientIDPanel = new JPanel();
        this.twitchAPIClientIDPanel.setLayout(new FlowLayout());

        this.twitchAPIClientIDLabel = new JLabel("Twitch API ClientID:");

        this.twitchAPIClientIDTextField = new JTextField(App.NOTIFIER.getSettings().getTwitchAPIClientID(), 16);

        this.twitchAPIClientIDButton = new JButton("?");
        this.twitchAPIClientIDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utils.openLink("http://www.ryandowling.me/twitch-api-token-generator/");
            }
        });

        this.twitchAPIClientIDPanel.add(this.twitchAPIClientIDLabel);
        this.twitchAPIClientIDPanel.add(this.twitchAPIClientIDTextField);
        this.twitchAPIClientIDPanel.add(this.twitchAPIClientIDButton);
    }

    private void setupStreamTipClientIDPanel() {
        this.streamTipClientIDPanel = new JPanel();
        this.streamTipClientIDPanel.setLayout(new FlowLayout());

        this.streamTipClientIDLabel = new JLabel("Stream Tip Client ID:");
        this.streamTipClientIDTextField = new JTextField(App.NOTIFIER.getSettings().getStreamTipClientID(), 16);

        this.streamTipClientIDPanel.add(this.streamTipClientIDLabel);
        this.streamTipClientIDPanel.add(this.streamTipClientIDTextField);
    }

    private void setupStreamTipAccessTokenPanel() {
        this.streamTipAccessTokenPanel = new JPanel();
        this.streamTipAccessTokenPanel.setLayout(new FlowLayout());

        this.streamTipAccessTokenLabel = new JLabel("Stream Tip Access Token:");
        this.streamTipAccessTokenTextField = new JTextField(App.NOTIFIER.getSettings().getStreamTipAccessToken(), 16);

        this.streamTipAccessTokenPanel.add(this.streamTipAccessTokenLabel);
        this.streamTipAccessTokenPanel.add(this.streamTipAccessTokenTextField);
    }

    private void setupTimeBetweenFollowerChecksPanel() {
        this.timeBetweenFollowerChecksPanel = new JPanel();
        this.timeBetweenFollowerChecksPanel.setLayout(new FlowLayout());

        this.timeBetweenFollowerChecksLabel = new JLabel("Seconds Between New Follower Checks:");

        this.timeBetweenFollowerChecksSpinner = new JSpinner();
        this.timeBetweenFollowerChecksSpinner.setModel(new SpinnerNumberModel(App.NOTIFIER.getSettings()
                .getSecondsBetweenFollowerChecks(), 10, 60, 5));

        this.timeBetweenFollowerChecksPanel.add(this.timeBetweenFollowerChecksLabel);
        this.timeBetweenFollowerChecksPanel.add(this.timeBetweenFollowerChecksSpinner);
    }

    private void setupTimeBetweenDonationChecksPanel() {
        this.timeBetweenDonationChecksPanel = new JPanel();
        this.timeBetweenDonationChecksPanel.setLayout(new FlowLayout());

        this.timeBetweenDonationChecksLabel = new JLabel("Seconds Between New Donation Checks:");

        this.timeBetweenDonationChecksSpinner = new JSpinner();
        this.timeBetweenDonationChecksSpinner.setModel(new SpinnerNumberModel(App.NOTIFIER.getSettings()
                .getSecondsBetweenDonationChecks(), 10, 60, 5));

        this.timeBetweenDonationChecksPanel.add(this.timeBetweenDonationChecksLabel);
        this.timeBetweenDonationChecksPanel.add(this.timeBetweenDonationChecksSpinner);
    }

    private void setupTimeBetweenViewerCountChecksPanel() {
        this.timeBetweenViewerCountChecksPanel = new JPanel();
        this.timeBetweenViewerCountChecksPanel.setLayout(new FlowLayout());

        this.timeBetweenViewerCountChecksLabel = new JLabel("Seconds Between Viewer Count Checks:");

        this.timeBetweenViewerCountChecksSpinner = new JSpinner();
        this.timeBetweenViewerCountChecksSpinner.setModel(new SpinnerNumberModel(App.NOTIFIER.getSettings()
                .getSecondsBetweenViewerCountChecks(), 10, 60, 5));

        this.timeBetweenViewerCountChecksPanel.add(this.timeBetweenViewerCountChecksLabel);
        this.timeBetweenViewerCountChecksPanel.add(this.timeBetweenViewerCountChecksSpinner);
    }

    private void setupNumberOfPointsOnViewerChartPanel() {
        this.numberOfPointsOnViewerChartPanel = new JPanel();
        this.numberOfPointsOnViewerChartPanel.setLayout(new FlowLayout());

        this.numberOfPointsOnViewerChartLabel = new JLabel("Number Of Points On Viewer Count Chart:");

        this.numberOfPointsOnViewerChartSpinner = new JSpinner();
        this.numberOfPointsOnViewerChartSpinner.setModel(new SpinnerNumberModel(App.NOTIFIER.getSettings()
                .getNumberOfPointsOnViewerChart(), 5, 100, 1));

        this.numberOfPointsOnViewerChartPanel.add(this.numberOfPointsOnViewerChartLabel);
        this.numberOfPointsOnViewerChartPanel.add(this.numberOfPointsOnViewerChartSpinner);
    }

    private void setupServerPortPanel() {
        this.serverPortPanel = new JPanel();
        this.serverPortPanel.setLayout(new FlowLayout());

        this.serverPortLabel = new JLabel("Server Port:");

        this.serverPortTextField = new JTextField("" + App.NOTIFIER.getSettings().getServerPort(), 16);

        this.serverPortPanel.add(this.serverPortLabel);
        this.serverPortPanel.add(this.serverPortTextField);
    }

    private void addComponents() {
        this.mainPane.add(this.twitchUsernamePanel);
        this.mainPane.add(this.twitchAPITokenPanel);
        this.mainPane.add(this.twitchAPIClientIDPanel);
        this.mainPane.add(this.streamTipClientIDPanel);
        this.mainPane.add(this.streamTipAccessTokenPanel);
        this.mainPane.add(this.timeBetweenFollowerChecksPanel);
        this.mainPane.add(this.timeBetweenDonationChecksPanel);
        this.mainPane.add(this.timeBetweenViewerCountChecksPanel);
        this.mainPane.add(this.numberOfPointsOnViewerChartPanel);
        this.mainPane.add(this.serverPortPanel);

        add(this.mainPane, BorderLayout.CENTER);
    }

    public boolean saveSettings() throws SettingsException {
        boolean restartApp = false;

        if (this.twitchUsernameTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Twitch username cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            throw new SettingsException();
        }

        if (this.twitchAPITokenTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Twitch API Token cannot be empty!", "Error", JOptionPane
                    .ERROR_MESSAGE);
            throw new SettingsException();
        }

        if (!Utils.isTwitchAPITokenValid(this.twitchAPITokenTextField.getText())) {
            JOptionPane.showMessageDialog(this, "Twitch API Token is invalid!", "Error", JOptionPane.ERROR_MESSAGE);
            throw new SettingsException();
        }

        if (this.streamTipClientIDTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Stream Tip client ID cannot be empty!", "Error", JOptionPane
                    .ERROR_MESSAGE);
            throw new SettingsException();
        }

        if (this.streamTipAccessTokenTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Stream Tip access token cannot be empty!", "Error", JOptionPane
                    .ERROR_MESSAGE);
            throw new SettingsException();
        }

        if ((int) this.timeBetweenFollowerChecksSpinner.getValue() < 10 || (int) this
                .timeBetweenFollowerChecksSpinner.getValue() > 60) {
            JOptionPane.showMessageDialog(this, "Time between follower checks must be between 10 and 60 seconds!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new SettingsException();
        }

        if ((int) this.timeBetweenDonationChecksSpinner.getValue() < 10 || (int) this
                .timeBetweenDonationChecksSpinner.getValue() > 60) {
            JOptionPane.showMessageDialog(this, "Time between donation checks must be between 10 and 60 seconds!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new SettingsException();
        }

        if ((int) this.timeBetweenViewerCountChecksSpinner.getValue() < 10 || (int) this
                .timeBetweenViewerCountChecksSpinner.getValue() > 60) {
            JOptionPane.showMessageDialog(this, "Time between viewer count checks must be between 10 and 60 " +
                    "seconds!", "Error", JOptionPane.ERROR_MESSAGE);
            throw new SettingsException();
        }

        if ((int) this.numberOfPointsOnViewerChartSpinner.getValue() < 5 || (int) this
                .numberOfPointsOnViewerChartSpinner.getValue() > 100) {
            JOptionPane.showMessageDialog(this, "Number of points on viewer count chart must be between 5 and 100!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            throw new SettingsException();
        }

        if (!App.NOTIFIER.getSettings().getTwitchUsername().equalsIgnoreCase(this.twitchUsernameTextField.getText())) {
            try {
                FileUtils.forceDeleteOnExit(Utils.getFollowersFile().toFile());
                FileUtils.forceDeleteOnExit(Utils.getDonationsFile().toFile());
                FileUtils.forceDeleteOnExit(Utils.getTxtDir().toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }

            restartApp = true;
        }

        if (App.NOTIFIER.getSettings().getServerPort() != Integer.parseInt(this.serverPortTextField.getText())) {
            restartApp = true;
        }

        App.NOTIFIER.getSettings().setTwitchUsername(this.twitchUsernameTextField.getText());
        App.NOTIFIER.getSettings().setTwitchAPIToken(this.twitchAPITokenTextField.getText());
        App.NOTIFIER.getSettings().setTwitchAPIClientID(this.twitchAPIClientIDTextField.getText());
        App.NOTIFIER.getSettings().setStreamTipClientID(this.streamTipClientIDTextField.getText());
        App.NOTIFIER.getSettings().setStreamTipAccessToken(this.streamTipAccessTokenTextField.getText());
        App.NOTIFIER.getSettings().setSecondsBetweenFollowerChecks((int) this.timeBetweenFollowerChecksSpinner
                .getValue());
        App.NOTIFIER.getSettings().setSecondsBetweenDonationChecks((int) this.timeBetweenDonationChecksSpinner
                .getValue());
        App.NOTIFIER.getSettings().setSecondsBetweenViewerCountChecks((int) this.timeBetweenViewerCountChecksSpinner
                .getValue());
        App.NOTIFIER.getSettings().setNumberOfPointsOnViewerChart((int) this.numberOfPointsOnViewerChartSpinner
                .getValue());
        App.NOTIFIER.getSettings().setServerPort(Integer.parseInt(this.serverPortTextField.getText().replaceAll
                ("[^0-9]", "")));

        return restartApp;
    }
}
