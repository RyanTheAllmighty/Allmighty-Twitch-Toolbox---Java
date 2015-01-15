package me.ryandowling.allmightytwitchtoolbox.events.adapters;

import me.ryandowling.allmightytwitchtoolbox.events.listeners.ViewerCountListener;

public abstract class ViewerCountAdapter implements ViewerCountListener {
    public void onViewerCountDataAdded(final int viewerCount) {
    }

    public void onViewerCountChanged(final int viewerCount) {
    }
}
