package me.ryandowling.allmightytwitchtoolbox.gui;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.utils.SoundPlayer;
import me.ryandowling.allmightytwitchtoolbox.utils.Utils;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;

public class SoundboardPanel extends JPanel {
    private JPanel mainPane;

    private JLabel[] labels = new JLabel[9];

    public SoundboardPanel() {
        super();

        setLayout(new BorderLayout());

        setupPanes();
        setupGrid();
        addComponents();
    }

    private void setupPanes() {
        this.mainPane = new JPanel();
        this.mainPane.setLayout(new GridLayout(3, 3));
    }

    private void setupGrid() {
        for (int i = 1; i <= 9; i++) {
            final int num = i - 1;
            JPanel panel = new JPanel();
            panel.setBorder(BorderFactory.createRaisedBevelBorder());
            panel.setLayout(new BorderLayout());

            JPanel playPane = new JPanel();
            playPane.setLayout(new BorderLayout());

            labels[num] = new JLabel("No Sound");

            Path path = App.NOTIFIER.getSettings().getSoundboardSound(num + 1);
            if (path != null) {
                labels[num] = new JLabel(path.toFile().getName().substring(0, path.toFile().getName().length() - 4));
            }
            labels[num].setHorizontalAlignment(SwingConstants.CENTER);
            playPane.add(labels[num], BorderLayout.CENTER);

            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout());

            JButton editButton = new JButton("Edit");
            editButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setMultiSelectionEnabled(false);
                    chooser.addChoosableFileFilter(Utils.getWavFileFilter());
                    chooser.setFileFilter(Utils.getWavFileFilter());
                    chooser.showOpenDialog(SoundboardPanel.this);
                    if (chooser.getSelectedFile() != null) {
                        File file = chooser.getSelectedFile();
                        labels[num].setText(file.getName().substring(0, file.getName().length() - 4));
                        App.NOTIFIER.getSettings().setSoundboardSound(num + 1, file.getAbsolutePath());
                        App.NOTIFIER.saveSettings();
                    }
                }
            });

            JButton playButton = new JButton("Play");
            playButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Path path = App.NOTIFIER.getSettings().getSoundboardSound(num + 1);
                    if (path != null) {
                        SoundPlayer.playSound(path, App.NOTIFIER.getSettings().getSoundsVolume());
                    }
                }
            });

            buttonPane.add(editButton);
            buttonPane.add(playButton);

            panel.add(playPane, BorderLayout.CENTER);
            panel.add(buttonPane, BorderLayout.SOUTH);
            this.mainPane.add(panel);
        }
    }

    private void addComponents() {
        add(this.mainPane, BorderLayout.CENTER);
    }
}
