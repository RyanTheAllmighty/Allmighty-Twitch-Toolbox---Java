package me.ryandowling.twitchnotifier.data.interfaces;

public interface Donation extends Comparable<Donation> {
    public boolean isTest = false;

    public String getID();

    public String getPrintableAmount();

    public String getUsername();

    public String getNote();

    public float getAmount();

    public long getTime();

    public String getTimeLocal();

    public boolean isTest();

    public Donation create(String username, String amount, String note);
}
