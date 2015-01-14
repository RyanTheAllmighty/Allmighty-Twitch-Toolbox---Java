package me.ryandowling.allmightytwitchtoolbox;

import me.ryandowling.allmightytwitchtoolbox.gui.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class App {
    public static final AllmightyTwitchToolbox NOTIFIER = new AllmightyTwitchToolbox();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                NOTIFIER.setup();
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                        UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                new MainFrame();
            }
        });
    }
}
