package me.ryandowling.allmightytwitchtoolbox.gui;

import io.github.asyncronous.toast.Toaster;
import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.events.managers.SettingsManager;
import me.ryandowling.allmightytwitchtoolbox.exceptions.SettingsException;
import me.ryandowling.allmightytwitchtoolbox.gui.settings.ApplicationSettingsPanel;
import me.ryandowling.allmightytwitchtoolbox.gui.settings.FoobarSettingsPanel;
import me.ryandowling.allmightytwitchtoolbox.gui.settings.SoundSettingsPanel;
import me.ryandowling.allmightytwitchtoolbox.utils.Utils;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel extends JPanel {
    private JPanel mainPane;
    private JPanel buttonPane;

    private JTabbedPane settingsPanel;
    private ApplicationSettingsPanel applicationSettingsPanel;
    private FoobarSettingsPanel foobarSettingsPanel;
    private SoundSettingsPanel soundSettingsPanel;

    private JButton openServerButton;
    private JButton saveButton;

    public SettingsPanel() {
        super();
        setLayout(new BorderLayout());

        setupPanes();
        setupTabs();
        addComponents();
    }

    private void setupPanes() {
        this.mainPane = new JPanel();
        this.mainPane.setLayout(new BoxLayout(this.mainPane, BoxLayout.Y_AXIS));

        this.buttonPane = new JPanel();
        this.buttonPane.setLayout(new FlowLayout());

        setupButtonPanel();
    }

    private void setupTabs() {
        this.applicationSettingsPanel = new ApplicationSettingsPanel();
        this.foobarSettingsPanel = new FoobarSettingsPanel();
        this.soundSettingsPanel = new SoundSettingsPanel();

        this.settingsPanel = new JTabbedPane();

        this.settingsPanel.addTab("Application", this.applicationSettingsPanel);
        this.settingsPanel.addTab("Foobar", this.foobarSettingsPanel);
        this.settingsPanel.addTab("Sound", this.soundSettingsPanel);
    }

    private void setupButtonPanel() {
        this.saveButton = new JButton("Save");
        this.saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSettings();
            }
        });

        this.openServerButton = new JButton("Open Server");
        this.openServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utils.openLink("http://localhost:" + App.NOTIFIER.getSettings().getServerPort());
            }
        });
    }

    private void addComponents() {
        this.buttonPane.add(this.saveButton);
        this.buttonPane.add(this.openServerButton);

        add(this.settingsPanel, BorderLayout.CENTER);
        add(this.buttonPane, BorderLayout.SOUTH);
    }

    private void saveSettings() {
        try {
            soundSettingsPanel.saveSettings();
            foobarSettingsPanel.saveSettings();

            if (applicationSettingsPanel.saveSettings()) {
                JOptionPane.showMessageDialog(this, "The app must be restarted! Please rerun the application after " +
                        "clicking OK!", "Restart Required", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }

            App.NOTIFIER.getSettings().setupFinished();
            App.NOTIFIER.saveSettings();
            SettingsManager.setupComplete();
            Toaster.instance().pop("Settings saved!");
        } catch (SettingsException e) {
        }
    }
}
