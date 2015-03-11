package me.ryandowling.allmightytwitchtoolbox.data.twitch;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TwitchUser {
    private long _id;
    private String name;
    private String created_at;
    private String updated_at;

    private String display_name;
    private String type;

    public TwitchUser create(String username) {
        if (this.name != null) {
            return this; // Don't allow editing this if it's an existing user
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));

        this._id = 0;
        this.name = username.toLowerCase();
        this.created_at = format.format(new Date());
        this.updated_at = format.format(new Date());

        this.display_name = username;
        this.type = "user";

        return this;
    }

    public long getID() {
        return this._id;
    }

    public String getName() {
        return this.name;
    }

    public String getCreatedAt() {
        return this.created_at;
    }

    public String getUpdatedAt() {
        return this.updated_at;
    }

    public String getDisplayName() {
        return this.display_name;
    }

    public String getType() {
        return this.type;
    }
}
