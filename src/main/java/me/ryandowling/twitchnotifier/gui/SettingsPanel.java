package me.ryandowling.twitchnotifier.gui;

import io.github.asyncronous.toast.Toaster;
import me.ryandowling.twitchnotifier.App;
import me.ryandowling.twitchnotifier.utils.SoundPlayer;
import me.ryandowling.twitchnotifier.utils.Utils;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SettingsPanel extends JPanel {
    private JPanel mainPane;
    private JPanel buttonPane;

    private JPanel twitchUsernamePanel;
    private JLabel twitchUsernameLabel;
    private JTextField twitchUsernameTextField;

    private JPanel timeBetweenFollowerChecksPanel;
    private JLabel timeBetweenFollowerChecksLabel;
    private JSpinner timeBetweenFollowerChecksSpinner;

    private JPanel serverPortPanel;
    private JLabel serverPortLabel;
    private JTextField serverPortTextField;

    private JPanel newFollowerSoundPanel;
    private JLabel newFollowerSoundLabel;
    private JTextField newFollowerSoundTextField;
    private JFileChooser newFollowerSoundChooser;
    private JButton newFollowerSoundChooserButton;
    private JButton newFollowerSoundTestButton;

    private JButton saveButton;

    public SettingsPanel() {
        super();
        setLayout(new BorderLayout());

        setupPanes();
        addComponents();
    }

    private void setupPanes() {
        this.mainPane = new JPanel();
        this.mainPane.setLayout(new BoxLayout(this.mainPane, BoxLayout.Y_AXIS));

        this.buttonPane = new JPanel();
        this.buttonPane.setLayout(new FlowLayout());

        setupTwitchUsernamePanel();
        setupTimeBetweenFollowerChecksPanel();
        setupServerPortPanel();
        setupFollowerSoundPanel();
        setupButtonPanel();
    }

    private void setupTwitchUsernamePanel() {
        this.twitchUsernamePanel = new JPanel();
        this.twitchUsernamePanel.setLayout(new FlowLayout());

        this.twitchUsernameLabel = new JLabel("Twitch Username:");
        this.twitchUsernameTextField = new JTextField(App.NOTIFIER.getSettings().getTwitchUsername(), 16);

        this.twitchUsernamePanel.add(this.twitchUsernameLabel);
        this.twitchUsernamePanel.add(this.twitchUsernameTextField);
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

    private void setupServerPortPanel() {
        this.serverPortPanel = new JPanel();
        this.serverPortPanel.setLayout(new FlowLayout());

        this.serverPortLabel = new JLabel("Server Port:");

        this.serverPortTextField = new JTextField("" + App.NOTIFIER.getSettings().getServerPort(), 16);

        this.serverPortPanel.add(this.serverPortLabel);
        this.serverPortPanel.add(this.serverPortTextField);
    }

    private void setupFollowerSoundPanel() {
        this.newFollowerSoundPanel = new JPanel();
        this.newFollowerSoundPanel.setLayout(new FlowLayout());

        this.newFollowerSoundLabel = new JLabel("New Follower Sound:");

        this.newFollowerSoundTextField = new JTextField(App.NOTIFIER.getSettings().getNewFollowSoundPath(), 16);
        this.newFollowerSoundTextField.setEnabled(false);

        this.newFollowerSoundChooser = new JFileChooser();
        this.newFollowerSoundChooser.setMultiSelectionEnabled(false);
        this.newFollowerSoundChooser.addChoosableFileFilter(Utils.getWavFileFilter());
        this.newFollowerSoundChooser.setFileFilter(Utils.getWavFileFilter());

        this.newFollowerSoundChooserButton = new JButton("Browse");
        this.newFollowerSoundChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newFollowerSoundChooser.showOpenDialog(SettingsPanel.this);
                if (newFollowerSoundChooser.getSelectedFile() != null) {
                    newFollowerSoundTextField.setText(newFollowerSoundChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        this.newFollowerSoundTestButton = new JButton("Test");
        this.newFollowerSoundTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!newFollowerSoundTextField.getText().isEmpty()) {
                    Path path = Paths.get(newFollowerSoundTextField.getText());
                    if (Files.exists(path) && Files.isRegularFile(path)) {
                        SoundPlayer.playSound(path);
                    }
                }
            }
        });

        this.newFollowerSoundPanel.add(this.newFollowerSoundLabel);
        this.newFollowerSoundPanel.add(this.newFollowerSoundTextField);
        this.newFollowerSoundPanel.add(this.newFollowerSoundChooserButton);
        this.newFollowerSoundPanel.add(this.newFollowerSoundTestButton);
    }

    private void setupButtonPanel() {
        this.saveButton = new JButton("Save");
        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSettings();
            }
        });
    }

    private void addComponents() {
        this.mainPane.add(this.twitchUsernamePanel);
        this.mainPane.add(this.timeBetweenFollowerChecksPanel);
        this.mainPane.add(this.serverPortPanel);
        this.mainPane.add(this.newFollowerSoundPanel);

        this.buttonPane.add(this.saveButton);

        add(this.mainPane, BorderLayout.CENTER);
        add(this.buttonPane, BorderLayout.SOUTH);
    }

    private void saveSettings() {
        boolean restartApp = false;

        if (this.twitchUsernameLabel.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Twitch username cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ((int) this.timeBetweenFollowerChecksSpinner.getValue() < 10 || (int) this
                .timeBetweenFollowerChecksSpinner.getValue() > 60) {
            JOptionPane.showMessageDialog(this, "Time between follower checks must be between 10 and 60 seconds!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (this.newFollowerSoundTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "New follower sound must be set!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!App.NOTIFIER.getSettings().getTwitchUsername().equalsIgnoreCase(this.twitchUsernameTextField.getText())) {
            restartApp = true;
        }

        App.NOTIFIER.getSettings().setTwitchUsername(this.twitchUsernameTextField.getText());
        App.NOTIFIER.getSettings().setSecondsBetweenFollowerChecks((int) this.timeBetweenFollowerChecksSpinner
                .getValue());
        App.NOTIFIER.getSettings().setServerPort(Integer.parseInt(this.serverPortTextField.getText().replaceAll
                ("[^0-9]", "")));

        if (this.newFollowerSoundChooser.getSelectedFile() != null) {
            App.NOTIFIER.getSettings().setNewFollowSound(this.newFollowerSoundChooser.getSelectedFile()
                    .getAbsolutePath());
        }

        App.NOTIFIER.getSettings().setupFinished();
        App.NOTIFIER.saveSettings();
        Toaster.instance().pop("Settings saved!");

        if (restartApp) {
            JOptionPane.showMessageDialog(this, "The app must be restarted! Please rerun the application after " +
                    "clicking OK!", "Restart Required", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }
}
