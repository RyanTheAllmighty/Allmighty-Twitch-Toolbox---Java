package me.ryandowling.twitchnotifier.data.twitch;

public class TwitchUser {
    private long _id;
    private String name;
    private String created_at;
    private String updated_at;
    private String display_name;
    private String type;

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
