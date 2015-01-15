package me.ryandowling.allmightytwitchtoolbox.events;

import me.ryandowling.allmightytwitchtoolbox.App;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.ViewerCountListener;
import me.ryandowling.allmightytwitchtoolbox.events.managers.ViewerCountManager;
import me.ryandowling.allmightytwitchtoolbox.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.IOException;

public class ViewerCountFiles implements ViewerCountListener {
    public ViewerCountFiles() {
        ViewerCountManager.addListener(this);
    }

    public void writeFiles() {
        try {
            FileUtils.write(Utils.getViewerCountFile().toFile(), "" + App.NOTIFIER.getLatestViewerCount());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewerCountChanged(final int viewerCount) {
        writeFiles();
    }
}
