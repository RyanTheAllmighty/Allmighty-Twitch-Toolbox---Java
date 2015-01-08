package me.ryandowling.twitchnotifier.data;

import java.awt.Dimension;
import java.awt.Point;

public class Settings {
    private Point guiPosition;
    private Dimension guiSize;

    public void loadDefaults() {
        this.guiPosition = new Point(0, 0);
        this.guiSize = new Dimension(600, 400);
    }

    public Point getGuiPosition() {
        return this.guiPosition;
    }

    public Dimension getGuiSize() {
        return this.guiSize;
    }
}
