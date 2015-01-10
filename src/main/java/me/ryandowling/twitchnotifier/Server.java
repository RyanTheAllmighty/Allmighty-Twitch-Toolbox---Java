package me.ryandowling.twitchnotifier;

import fi.iki.elonen.NanoHTTPD;
import me.ryandowling.twitchnotifier.utils.Utils;
import org.apache.commons.io.FileUtils;

import java.io.FileInputStream;
import java.io.IOException;

public class Server extends NanoHTTPD {
    public Server(int port) {
        super(port);
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
                json = TwitchNotifier.GSON.toJson(App.NOTIFIER.getFollowers());
                break;
            case "/followers/latest":
                json = TwitchNotifier.GSON.toJson(App.NOTIFIER.getLatestFollower());
                break;
            case "/followers/latest/text":
                json = TwitchNotifier.GSON.toJson(App.NOTIFIER.getLatestFollower().getDisplayName());
                break;
            case "/followers/total":
                json = TwitchNotifier.GSON.toJson(App.NOTIFIER.getFollowersTotal());
                break;
            case "/donations/latest":
                json = TwitchNotifier.GSON.toJson(App.NOTIFIER.getLatestDonation());
                break;
            case "/donations/latest/text":
                json = TwitchNotifier.GSON.toJson(App.NOTIFIER.getLatestDonation().getID());
                break;
            case "/donations/latest/username":
                json = TwitchNotifier.GSON.toJson(App.NOTIFIER.getLatestDonation().getUsername());
                break;
            case "/donations/latest/amount":
                json = TwitchNotifier.GSON.toJson(App.NOTIFIER.getLatestDonation().getPrintableAmount());
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
}
