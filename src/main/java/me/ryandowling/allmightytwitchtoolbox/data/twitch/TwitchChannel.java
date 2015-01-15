package me.ryandowling.allmightytwitchtoolbox.data.twitch;

public class TwitchChannel {
    private String display_name;
    private String game;
    private String logo;
    private boolean mature;
    private String status;
    private boolean partner;
    private String url;
    private String name;
    private int followers;
    private int views;

    public String getDisplay_name() {
        return this.display_name;
    }

    public String getLogo() {
        return this.logo;
    }

    public boolean isMature() {
        return this.mature;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isPartner() {
        return this.partner;
    }

    public String getURL() {
        return this.url;
    }

    public String getName() {
        return this.name;
    }

    public int getFollowers() {
        return this.followers;
    }

    public int getViews() {
        return this.views;
    }
}
