package me.ryandowling.allmightytwitchtoolbox.data.twitch;

import me.ryandowling.allmightytwitchtoolbox.data.interfaces.Follower;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TwitchFollower implements Follower {
    private boolean isTest;
    private String created_at;
    private boolean notifications;
    private TwitchUser user;

    public TwitchUser getUser() {
        return this.user;
    }

    public String getUsername() {
        return this.user.getName();
    }

    public String getDisplayName() {
        return this.user.getDisplayName();
    }

    public long getTime() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        try {
            return format.parse(this.created_at).getTime();
        } catch (ParseException | NumberFormatException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public String getTimeLocal() {
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            originalFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

            Date date = originalFormat.parse(this.created_at);

            DateFormat localFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            localFormat.setTimeZone(TimeZone.getDefault());

            return localFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return this.created_at;
    }

    public boolean isTest() {
        return this.isTest;
    }

    public Follower create(String username) {
        if (this.user != null) {
            return this; // Don't allow editing this if it's an existing follow
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        this.created_at = format.format(new Date());
        this.notifications = true;
        this.user = new TwitchUser().create(username);
        this.isTest = true;

        return this;
    }

    @Override
    public int compareTo(Follower o) {
        long time1 = this.getTime();
        long time2 = o.getTime();

        if (time1 == time2) {
            return 0;
        }

        if (time1 > time2) {
            return -1;
        }

        return 1;
    }
}
