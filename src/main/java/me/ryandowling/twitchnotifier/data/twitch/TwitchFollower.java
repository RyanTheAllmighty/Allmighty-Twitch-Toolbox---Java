package me.ryandowling.twitchnotifier.data.twitch;

import me.ryandowling.twitchnotifier.data.interfaces.Follower;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TwitchFollower implements Follower {
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
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date;

        try {
            date = df.parse(this.created_at);
            return date.getTime();
        } catch (ParseException e) {
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

    public Follower create(String username) {
        if (this.user != null) {
            return this; // Don't allow editing this if it's an existing follow
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        this.created_at = format.format(new Date());
        this.notifications = true;
        this.user = new TwitchUser().create(username);

        return this;
    }

    @Override
    public int compareTo(Object o) {
        long time1 = this.getTime();
        long time2 = ((TwitchFollower) o).getTime();

        if (time1 == time2) {
            return 0;
        }

        if (time1 > time2) {
            return -1;
        }

        return 1;
    }
}
