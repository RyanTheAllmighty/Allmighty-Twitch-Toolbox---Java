package me.ryandowling.allmightytwitchtoolbox;

import fi.iki.elonen.NanoHTTPD;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Donation;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Follower;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.DonationListener;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.FollowerListener;
import me.ryandowling.allmightytwitchtoolbox.events.managers.DonationManager;
import me.ryandowling.allmightytwitchtoolbox.events.managers.FollowerManager;
import me.ryandowling.allmightytwitchtoolbox.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.FileInputStream;
import java.io.IOException;

public class Server extends NanoHTTPD implements FollowerListener, DonationListener {
    private Follower latestFollower;
    private Donation latestDonation;

    public Server(int port) {
        super(port);

        this.latestFollower = App.NOTIFIER.getLatestFollower();
        this.latestDonation = App.NOTIFIER.getLatestDonation();

        FollowerManager.addListener(this);
        DonationManager.addListener(this);
    }

    @Override
    public Response serve(IHTTPSession session) {
        String json;

        switch (session.getUri()) {
            case "/":
                try {
                    return found(FileUtils.readFileToString(Utils.getNotificationsHTMLFile().toFile()));
                } catch (IOException e) {
                    e.printStackTrace();
                    return error();
                }
            case "/image":
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(Utils.getNotificationsImageFile().toFile());
                    return new NanoHTTPD.Response(Response.Status.OK, "image/png", fis);
                } catch (IOException e) {
                    e.printStackTrace();
                    return error();
                }
            case "/followers/all":
                json = AllmightyTwitchToolbox.GSON.toJson(App.NOTIFIER.getFollowers());
                break;
            case "/followers/latest":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestFollower);
                break;
            case "/followers/latest/text":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestFollower.getDisplayName());
                break;
            case "/followers/total":
                json = AllmightyTwitchToolbox.GSON.toJson(App.NOTIFIER.getFollowersTotal());
                break;
            case "/donations/latest":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestDonation);
                break;
            case "/donations/latest/text":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestDonation.getID());
                break;
            case "/donations/latest/username":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestDonation.getUsername());
                break;
            case "/donations/latest/amount":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestDonation.getPrintableAmount());
                break;
            default:
                return notFound();
        }

        return new NanoHTTPD.Response(Response.Status.OK, "application/json", json);
    }

    private Response found(String response) {
        return new NanoHTTPD.Response(response);
    }

    private Response notFound() {
        return new NanoHTTPD.Response(Response.Status.NOT_FOUND, "text/html", "404 Not Found");
    }

    private Response error() {
        return new NanoHTTPD.Response(Response.Status.INTERNAL_ERROR, "text/html", "500 Internal Error");
    }

    @Override
    public void onNewFollow(Follower follower) {
        this.latestFollower = follower;
    }

    @Override
    public void onNewDonation(Donation donation) {
        this.latestDonation = donation;
    }
}
