package me.ryandowling.twitchnotifier.data;

import java.awt.Dimension;
import java.awt.Point;

public class Settings {
    private Point guiPosition;
    private Dimension guiSize;
    private int serverPort;

    public void loadDefaults() {
        this.guiPosition = new Point(0, 0);
        this.guiSize = new Dimension(600, 400);
        this.serverPort = 9001;
    }

    public Point getGuiPosition() {
        return this.guiPosition;
    }

    public Dimension getGuiSize() {
        return this.guiSize;
    }

    public void setGuiPosition(Point guiPosition) {
        this.guiPosition = guiPosition;
    }

    public void setGuiSize(Dimension guiSize) {
        this.guiSize = guiSize;
    }

    public int getServerPort() {
        return this.serverPort;
    }
}
