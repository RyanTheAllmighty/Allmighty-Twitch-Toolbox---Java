package me.ryandowling.twitchnotifier.data.interfaces;

public interface Follower extends Comparable<Follower> {
    public boolean isTest = false;

    public String getUsername();

    public String getDisplayName();

    public long getTime();

    public String getTimeLocal();

    public boolean isTest();

    public Follower create(String username);
}
