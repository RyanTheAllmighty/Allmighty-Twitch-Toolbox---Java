package me.ryandowling.allmightytwitchtoolbox.gui;

import me.ryandowling.allmightytwitchtoolbox.utils.Utils;

import javax.swing.JWindow;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class SplashScreen extends JWindow {
    private static final BufferedImage img = Utils.getImage("SplashScreen");

    public SplashScreen() {
        this.setLayout(null);
        this.setSize(img.getWidth(), img.getHeight());
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(false);

        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), null);
    }

    /**
     * Closes and disposes of the splash screen
     */
    public void close() {
        this.setVisible(false);
        this.dispose();
    }
}