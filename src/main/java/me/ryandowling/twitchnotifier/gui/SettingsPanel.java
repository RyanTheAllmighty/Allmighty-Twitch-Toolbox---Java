package me.ryandowling.twitchnotifier.gui;

import io.github.asyncronous.toast.Toaster;
import me.ryandowling.twitchnotifier.App;
import me.ryandowling.twitchnotifier.utils.SoundPlayer;
import me.ryandowling.twitchnotifier.utils.Utils;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SettingsPanel extends JPanel {
    private JPanel mainPane;
    private JPanel buttonPane;

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

        this.newFollowerSoundPanel = new JPanel();
        this.newFollowerSoundPanel.setLayout(new FlowLayout());

        this.newFollowerSoundLabel = new JLabel("New Follower Sound:");
        this.newFollowerSoundTextField = new JTextField(App.NOTIFIER.getSettings().getNewFollowSoundPath());
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

        this.mainPane.add(this.newFollowerSoundPanel);

        this.buttonPane = new JPanel();
        this.buttonPane.setLayout(new FlowLayout());

        this.saveButton = new JButton("Save");
        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSettings();
            }
        });

        this.buttonPane.add(this.saveButton);
    }

    private void addComponents() {
        add(this.mainPane, BorderLayout.CENTER);
        add(this.buttonPane, BorderLayout.SOUTH);
    }

    private void saveSettings() {
        if (this.newFollowerSoundChooser.getSelectedFile() != null) {
            App.NOTIFIER.getSettings().setNewFollowSound(this.newFollowerSoundChooser.getSelectedFile()
                    .getAbsolutePath());
        }

        App.NOTIFIER.saveSettings();
        Toaster.instance().pop("Settings saved!");
    }
}
