package me.ryandowling.allmightytwitchtoolbox;

import fi.iki.elonen.NanoHTTPD;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Donation;
import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Follower;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.DonationListener;
import me.ryandowling.allmightytwitchtoolbox.events.listeners.FollowerListener;
import me.ryandowling.allmightytwitchtoolbox.events.managers.DonationManager;
import me.ryandowling.allmightytwitchtoolbox.events.managers.FollowerManager;
import me.ryandowling.allmightytwitchtoolbox.utils.Utils;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
                    return found(IOUtils.toString(System.class.getResource("/assets/web/html/index.html")));
                } catch (IOException e) {
                    e.printStackTrace();
                    return error();
                }
            case "/timer/1":
                try {
                    return found(IOUtils.toString(System.class.getResource("/assets/web/html/timer.html")));
                } catch (IOException e) {
                    e.printStackTrace();
                    return error();
                }
            case "/timer/2":
                try {
                    return found(IOUtils.toString(System.class.getResource("/assets/web/html/timer.html")));
                } catch (IOException e) {
                    e.printStackTrace();
                    return error();
                }
            case "/timer/3":
                try {
                    return found(IOUtils.toString(System.class.getResource("/assets/web/html/timer.html")));
                } catch (IOException e) {
                    e.printStackTrace();
                    return error();
                }
            case "/notifications":
                try {
                    return found(IOUtils.toString(System.class.getResource("/assets/web/html/notifications.html")));
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
            case "/api/viewers/total":
                json = AllmightyTwitchToolbox.GSON.toJson(App.NOTIFIER.getLatestViewerCount());
                break;
            case "/api/followers/latest":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestFollower);
                break;
            case "/api/followers/latest/username":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestFollower.getDisplayName());
                break;
            case "/api/followers/total":
                json = AllmightyTwitchToolbox.GSON.toJson(App.NOTIFIER.getFollowersTotal());
                break;
            case "/api/donations/latest":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestDonation);
                break;
            case "/api/donations/latest/id":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestDonation.getID());
                break;
            case "/api/donations/latest/username":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestDonation.getUsername());
                break;
            case "/api/donations/latest/amount":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestDonation.getPrintableAmount());
                break;
            case "/api/donations/latest/note":
                json = AllmightyTwitchToolbox.GSON.toJson(this.latestDonation.getNote());
                break;
            case "/api/timer/1/seconds":
                long seconds1 = Utils.getDateDiff(new Date(), App.NOTIFIER.getCountdownTimer(1), TimeUnit.SECONDS);

                if (seconds1 < 0) {
                    seconds1 = 0;
                }

                json = AllmightyTwitchToolbox.GSON.toJson(seconds1);
                break;
            case "/api/timer/2/seconds":
                long seconds2 = Utils.getDateDiff(new Date(), App.NOTIFIER.getCountdownTimer(2), TimeUnit.SECONDS);

                if (seconds2 < 0) {
                    seconds2 = 0;
                }

                json = AllmightyTwitchToolbox.GSON.toJson(seconds2);
                break;
            case "/api/timer/3/seconds":
                long seconds3 = Utils.getDateDiff(new Date(), App.NOTIFIER.getCountdownTimer(3), TimeUnit.SECONDS);

                if (seconds3 < 0) {
                    seconds3 = 0;
                }

                json = AllmightyTwitchToolbox.GSON.toJson(seconds3);
                break;
            case "/api/foobar/stop":
                json = "{\"done\": false}";

                if (App.NOTIFIER.getSettings().getFoobarLocation() != null && !App.NOTIFIER.getSettings()
                        .getFoobarLocation()
                        .isEmpty()) {
                    try {
                        Runtime.getRuntime().exec(App.NOTIFIER.getSettings().getFoobarLocation() + " /stop");
                        json = "{\"done\": true}";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "/api/foobar/play":
                json = "{\"done\": false}";

                if (App.NOTIFIER.getSettings().getFoobarLocation() != null && !App.NOTIFIER.getSettings()
                        .getFoobarLocation()
                        .isEmpty()) {
                    try {
                        Runtime.getRuntime().exec(App.NOTIFIER.getSettings().getFoobarLocation() + " /play");
                        json = "{\"done\": true}";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "/api/foobar/pause":
                json = "{\"done\": false}";

                if (App.NOTIFIER.getSettings().getFoobarLocation() != null && !App.NOTIFIER.getSettings()
                        .getFoobarLocation().isEmpty()) {
                    try {
                        Runtime.getRuntime().exec(App.NOTIFIER.getSettings().getFoobarLocation() + " /pause");
                        json = "{\"done\": true}";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "/api/foobar/next":
                json = "{\"done\": false}";

                if (App.NOTIFIER.getSettings().getFoobarLocation() != null && !App.NOTIFIER.getSettings()
                        .getFoobarLocation().isEmpty()) {
                    try {
                        Runtime.getRuntime().exec(App.NOTIFIER.getSettings().getFoobarLocation() + " /next");
                        json = "{\"done\": true}";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "/api/foobar/prev":
                json = "{\"done\": false}";

                if (App.NOTIFIER.getSettings().getFoobarLocation() != null && !App.NOTIFIER.getSettings()
                        .getFoobarLocation().isEmpty()) {
                    try {
                        Runtime.getRuntime().exec(App.NOTIFIER.getSettings().getFoobarLocation() + " /prev");
                        json = "{\"done\": true}";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case "/api/foobar/nextpause":
                json = "{\"done\": false}";

                if (App.NOTIFIER.getSettings().getFoobarLocation() != null && !App.NOTIFIER.getSettings()
                        .getFoobarLocation().isEmpty()) {
                    try {
                        Runtime.getRuntime().exec(App.NOTIFIER.getSettings().getFoobarLocation() + " /next");
                        Thread.sleep(100);
                        Runtime.getRuntime().exec(App.NOTIFIER.getSettings().getFoobarLocation() + " /pause");
                        json = "{\"done\": true}";
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                break;
            default:
                URL url = System.class.getResource("/assets/web" + session.getUri());

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
    public void onFollowersNumberChanged(int newTotal) {

    }

    @Override
    public void onNewDonation(Donation donation) {
        this.latestDonation = donation;
    }
}
