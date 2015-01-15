package me.ryandowling.allmightytwitchtoolbox.events.listeners;

public interface ViewerCountListener {
    public void onViewerCountDataAdded(final int viewerCount);
    public void onViewerCountChanged(final int viewerCount);
}
