package me.ryandowling.allmightytwitchtoolbox.gui.settings;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.exceptions.SettingsException;
import me.ryandowling.allmightytwitchtoolbox.utils.SoundPlayer;
import me.ryandowling.allmightytwitchtoolbox.utils.Utils;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SoundSettingsPanel extends JPanel {
    private JPanel mainPane;

    private JPanel newFollowerSoundPanel;
    private JLabel newFollowerSoundLabel;
    private JTextField newFollowerSoundTextField;
    private JFileChooser newFollowerSoundChooser;
    private JButton newFollowerSoundChooserButton;
    private JButton newFollowerSoundTestButton;

    private JPanel newDonationSoundPanel;
    private JLabel newDonationSoundLabel;
    private JTextField newDonationSoundTextField;
    private JFileChooser newDonationSoundChooser;
    private JButton newDonationSoundChooserButton;
    private JButton newDonationSoundTestButton;

    private JPanel notificationVolumePanel;
    private JLabel notificationVolumeLabel;
    private JTextField notificationVolumeTextField;

    private JPanel soundVolumePanel;
    private JLabel soundVolumeLabel;
    private JTextField soundVolumeTextField;

    public SoundSettingsPanel() {
        super();
        setLayout(new BorderLayout());

        setupPanes();
        addComponents();
    }

    private void setupPanes() {
        this.mainPane = new JPanel();
        this.mainPane.setLayout(new BoxLayout(this.mainPane, BoxLayout.Y_AXIS));

        setupFollowerSoundPanel();
        setupDonationSoundPanel();

        setupNotificationVolumePanel();
        setupSoundVolumePanel();
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
                newFollowerSoundChooser.showOpenDialog(SoundSettingsPanel.this);
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
                        SoundPlayer.playSound(path, App.NOTIFIER.getSettings().getNotificationVolume());
                    }
                }
            }
        });

        this.newFollowerSoundPanel.add(this.newFollowerSoundLabel);
        this.newFollowerSoundPanel.add(this.newFollowerSoundTextField);
        this.newFollowerSoundPanel.add(this.newFollowerSoundChooserButton);
        this.newFollowerSoundPanel.add(this.newFollowerSoundTestButton);
    }

    private void setupDonationSoundPanel() {
        this.newDonationSoundPanel = new JPanel();
        this.newDonationSoundPanel.setLayout(new FlowLayout());

        this.newDonationSoundLabel = new JLabel("New Donation Sound:");

        this.newDonationSoundTextField = new JTextField(App.NOTIFIER.getSettings().getNewDonationSoundPath(), 16);
        this.newDonationSoundTextField.setEnabled(false);

        this.newDonationSoundChooser = new JFileChooser();
        this.newDonationSoundChooser.setMultiSelectionEnabled(false);
        this.newDonationSoundChooser.addChoosableFileFilter(Utils.getWavFileFilter());
        this.newDonationSoundChooser.setFileFilter(Utils.getWavFileFilter());

        this.newDonationSoundChooserButton = new JButton("Browse");
        this.newDonationSoundChooserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newDonationSoundChooser.showOpenDialog(SoundSettingsPanel.this);
                if (newDonationSoundChooser.getSelectedFile() != null) {
                    newDonationSoundTextField.setText(newDonationSoundChooser.getSelectedFile().getAbsolutePath());
                }
            }
        });

        this.newDonationSoundTestButton = new JButton("Test");
        this.newDonationSoundTestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!newDonationSoundTextField.getText().isEmpty()) {
                    Path path = Paths.get(newDonationSoundTextField.getText());
                    if (Files.exists(path) && Files.isRegularFile(path)) {
                        SoundPlayer.playSound(path, App.NOTIFIER.getSettings().getNotificationVolume());
                    }
                }
            }
        });

        this.newDonationSoundPanel.add(this.newDonationSoundLabel);
        this.newDonationSoundPanel.add(this.newDonationSoundTextField);
        this.newDonationSoundPanel.add(this.newDonationSoundChooserButton);
        this.newDonationSoundPanel.add(this.newDonationSoundTestButton);
    }

    private void setupNotificationVolumePanel() {
        this.notificationVolumePanel = new JPanel();
        this.notificationVolumePanel.setLayout(new FlowLayout());

        this.notificationVolumeLabel = new JLabel("Notification Volume (-100 to 0):");
        this.notificationVolumeTextField = new JTextField(App.NOTIFIER.getSettings().getNotificationVolume() + "", 16);

        this.notificationVolumePanel.add(this.notificationVolumeLabel);
        this.notificationVolumePanel.add(this.notificationVolumeTextField);
    }

    private void setupSoundVolumePanel() {
        this.soundVolumePanel = new JPanel();
        this.soundVolumePanel.setLayout(new FlowLayout());

        this.soundVolumeLabel = new JLabel("Sound Volume (-100 to 0):");
        this.soundVolumeTextField = new JTextField(App.NOTIFIER.getSettings().getSoundsVolume() + "", 16);

        this.soundVolumePanel.add(this.soundVolumeLabel);
        this.soundVolumePanel.add(this.soundVolumeTextField);
    }

    private void addComponents() {
        this.mainPane.add(this.newFollowerSoundPanel);
        this.mainPane.add(this.newDonationSoundPanel);

        this.mainPane.add(this.notificationVolumePanel);
        this.mainPane.add(this.soundVolumePanel);

        add(this.mainPane, BorderLayout.CENTER);
    }

    public void saveSettings() throws SettingsException {
        if (this.newFollowerSoundTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "New follower sound must be set!", "Error", JOptionPane.ERROR_MESSAGE);
            throw new SettingsException();
        }

        if (this.newDonationSoundTextField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "New donation sound must be set!", "Error", JOptionPane.ERROR_MESSAGE);
            throw new SettingsException();
        }

        try {
            float value = Float.parseFloat(this.notificationVolumeTextField.getText());
            if (value < -100 || value > 0) {
                throw new NumberFormatException("Notification volume must be a number between -100 and 0!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Notification volume must be a number between -100 and 0!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new SettingsException();
        }

        try {
            float value = Float.parseFloat(this.soundVolumeTextField.getText());
            if (value < -100 || value > 0) {
                throw new NumberFormatException("Sound volume must be a number between -100 and 0!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Sound volume must be a number between -100 and 0!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new SettingsException();
        }

        if (this.newFollowerSoundChooser.getSelectedFile() != null) {
            App.NOTIFIER.getSettings().setNewFollowSound(this.newFollowerSoundChooser.getSelectedFile()
                    .getAbsolutePath());
        }

        if (this.newDonationSoundChooser.getSelectedFile() != null) {
            App.NOTIFIER.getSettings().setNewDonationSound(this.newDonationSoundChooser.getSelectedFile()
                    .getAbsolutePath());
        }

        try {
            App.NOTIFIER.getSettings().setNotificationVolume(Float.parseFloat(this.notificationVolumeTextField.getText()));
        } catch (NumberFormatException ignored) {
        }

        try {
            App.NOTIFIER.getSettings().setSoundsVolume(Float.parseFloat(this.soundVolumeTextField.getText()));
        } catch (NumberFormatException ignored) {
        }
    }
}
