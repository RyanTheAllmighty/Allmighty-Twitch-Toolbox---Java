package me.ryandowling.allmightytwitchtoolbox;

import me.ryandowling.allmightytwitchtoolbox.gui.MainFrame;
import me.ryandowling.allmightytwitchtoolbox.gui.SplashScreen;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class App {
    public static final AllmightyTwitchToolbox NOTIFIER = new AllmightyTwitchToolbox();

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SplashScreen ss = new SplashScreen();

        NOTIFIER.setup();

        ss.close();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}
