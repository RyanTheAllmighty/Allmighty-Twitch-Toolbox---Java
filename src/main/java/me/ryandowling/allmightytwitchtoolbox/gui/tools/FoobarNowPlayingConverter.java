package me.ryandowling.allmightytwitchtoolbox.gui.tools;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.utils.FileWatcher;
import me.ryandowling.allmightytwitchtoolbox.utils.Utils;
import org.apache.commons.io.FileUtils;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class FoobarNowPlayingConverter extends JPanel implements ActionListener {
    private JLabel label;
    private JLabel turnedOn;
    private JButton toggleStateButton;
    private boolean running = false;

    private Timer timer;

    public FoobarNowPlayingConverter() {
        super();

        setLayout(new FlowLayout());

        setupComponents();
        addComponents();
    }

    private void setupComponents() {
        this.label = new JLabel("foobar Now Playing Converter: ");

        this.turnedOn = new JLabel("Off");

        this.toggleStateButton = new JButton("Turn On");
        this.toggleStateButton.addActionListener(this);
    }

    private void addComponents() {
        add(this.label);
        add(this.turnedOn);
        add(this.toggleStateButton);
    }

    private void setupTimer() {
        TimerTask task = new FileWatcher(App.NOTIFIER.getSettings().getRawNowPlayingFile().toFile()) {
            @Override
            protected void onChange(File file) {
                System.out.println("AAAAA");
                updateFiles();
            }
        };

        timer = new Timer();
        timer.schedule(task, new Date(), 100);
    }

    private void cancelTimer() {
        this.timer.cancel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.toggleStateButton) {
            toggleState();
        }
    }

    private void toggleState() {
        if (!this.running && !App.NOTIFIER.getSettings().hasSetupNowPlaying()) {
            JOptionPane.showMessageDialog(this, "You must setup the now playing file options in the Settings " +
                    "panel first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.running = !this.running;
        this.turnedOn.setText((this.running ? "On" : "Off"));
        this.toggleStateButton.setText("Turn " + (this.running ? "Off" : "On"));

        if (this.running) {
            this.setupTimer();
        } else {
            this.cancelTimer();
        }
    }

    private void updateFiles() {
        try {
            String nowPlaying = FileUtils.readFileToString(App.NOTIFIER.getSettings().getRawNowPlayingFile().toFile());

            // How to display the file then ||| followed by Absolute path to file "C:/music/file.mp3", example below
            // Artist Name - Song Name|||C:/music/file.mp3
            // foobar: http://skipyrich.com/wiki/Foobar2000:Now_Playing_Simple - %artist% - %title%|||%path%
            String[] parts = nowPlaying.split("\\|\\|\\|");

            if (parts.length == 2) {
                FileUtils.write(App.NOTIFIER.getSettings().getNowPlayingFile().toFile(), parts[0]);
                FileUtils.write(App.NOTIFIER.getSettings().getNowPlayingFileFile().toFile(), parts[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
