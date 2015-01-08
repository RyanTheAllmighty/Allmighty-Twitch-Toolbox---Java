package me.ryandowling.twitchnotifier.data.twitch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TwitchFollower implements Comparable {
    private String created_at;
    private long created_at_timestamp;
    private boolean notifications;
    private TwitchUser user;

    public String getCreatedAt() {
        return this.created_at;
    }

    public long getCreatedAtTimestamp() {
        return this.created_at_timestamp;
    }

    public boolean hasEnabledNotifications() {
        return this.notifications;
    }

    public TwitchUser getUser() {
        return this.user;
    }

    public void addTimestamps() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        Date date;

        try {
            date = df.parse(this.created_at);
            this.created_at_timestamp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        user.addTimestamps();
    }

    @Override
    public int compareTo(Object o) {
        long time1 = this.getCreatedAtTimestamp();
        long time2 = ((TwitchFollower) o).getCreatedAtTimestamp();

        if (time1 == time2) {
            return 0;
        }

        if (time1 > time2) {
            return -1;
        }

        return 1;
    }
}
