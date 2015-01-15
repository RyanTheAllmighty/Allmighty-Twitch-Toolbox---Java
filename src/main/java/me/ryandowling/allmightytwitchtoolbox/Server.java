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
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

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
                    return found(IOUtils.toString(System.class.getResource("/assets/html/index.html")));
                } catch (IOException e) {
                    e.printStackTrace();
                    return error();
                }
            case "/notifications":
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
            case "/followers/latest":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestFollower);
                break;
            case "/followers/latest/username":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestFollower.getDisplayName());
                break;
            case "/followers/total":
                json = AllmightyTwitchToolbox.GSON.toJson(App.NOTIFIER.getFollowersTotal());
                break;
            case "/donations/latest":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestDonation);
                break;
            case "/donations/latest/id":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestDonation.getID());
                break;
            case "/donations/latest/username":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestDonation.getUsername());
                break;
            case "/donations/latest/amount":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestDonation.getPrintableAmount());
                break;
            case "/donations/latest/note":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestDonation.getNote());
                break;
            default:
                URL url = System.class.getResource("/assets/" + session.getUri());

                if (url != null) {
                    try {
                        String type = "text/html";

                        if (session.getUri().substring(0, 4).equalsIgnoreCase("/js/")) {
                            type = "text/javascript";
                        } else if (session.getUri().substring(0, 5).equalsIgnoreCase("/css/")) {
                            type = "text/css";
                        }

                        return found(IOUtils.toString(url), type);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return error();
                    }
                }

                return notFound();
        }

        return new NanoHTTPD.Response(Response.Status.OK, "application/json", json);
    }

    private Response found(String response) {
        return new NanoHTTPD.Response(response);
    }

    private Response found(String response, String type) {
        return new NanoHTTPD.Response(Response.Status.OK, type, response);
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
