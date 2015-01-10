package me.ryandowling.twitchnotifier.data.streamtip;

import me.ryandowling.twitchnotifier.data.interfaces.Donation;
import me.ryandowling.twitchnotifier.utils.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class StreamTipTip implements Donation {
    private String username;
    private String currencySymbol;
    private String date;
    private String note;
    private String amount;
    private String id;

    public String getID() {
        return this.id;
    }

    public String getPrintableAmount() {
        return this.currencySymbol + this.amount;
    }

    public String getUsername() {
        return this.username;
    }

    public String getNote() {
        return this.note;
    }

    public float getAmount() {
        return Float.parseFloat(this.amount);
    }

    public long getTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        Date date;

        try {
            date = df.parse(this.date);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public String getTimeLocal() {
        try {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
            originalFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

            Date date = originalFormat.parse(this.date);

            DateFormat localFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            localFormat.setTimeZone(TimeZone.getDefault());

            return localFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return this.date;
    }

    public Donation create(String username, String amount) {
        if (this.username != null) {
            return this; // Don't allow editing this if it's an existing donation
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");

        this.id = Utils.getRandomHexString(24);
        this.username = username;
        this.amount = amount;
        this.date = format.format(new Date());
        this.currencySymbol = "$";
        this.note = "Test Donation";

        return this;
    }

    @Override
    public int compareTo(Donation o) {
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
