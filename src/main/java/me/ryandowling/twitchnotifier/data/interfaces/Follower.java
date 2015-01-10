package me.ryandowling.twitchnotifier.data.interfaces;

public interface Follower extends Comparable<Follower> {
    public String getUsername();

    public String getDisplayName();

    public long getTime();

    public String getTimeLocal();

    public Follower create(String username);
}
