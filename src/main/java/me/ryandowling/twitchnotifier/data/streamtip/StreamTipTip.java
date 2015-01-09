package me.ryandowling.twitchnotifier.data.streamtip;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class StreamTipTip implements Comparable {
    private String _id;
    private String channel;
    private String user;
    private String processor;
    private String transactionId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String currencyCode;
    private String currencySymbol;
    private long cents;
    private String note;
    private int __v;
    private boolean pending;
    private boolean reversed;
    private boolean deleted;
    private String date;
    private long date_timestamp;
    private String amount;
    private String id;

    public StreamTipTip create(String username, String amount) {
        if (this.username != null) {
            return this; // Don't allow editing this if it's an existing donation
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");

        this.username = username;
        this.amount = amount;
        this.date = format.format(new Date());
        this.date_timestamp = System.currentTimeMillis();
        this.currencySymbol = "$";

        return this;
    }

    public void addTimestamps() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sss'Z'");
        Date date;

        try {
            date = df.parse(this.date);
            this.date_timestamp = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getPrintableAmount() {
        return this.currencySymbol + this.amount;
    }

    public String getID() {
        return this._id;
    }

    public String getChannel() {
        return this.channel;
    }

    public String getUser() {
        return this.user;
    }

    public String getProcessor() {
        return this.processor;
    }

    public String getTransactionId() {
        return this.transactionId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public String getCurrencySymbol() {
        return this.currencySymbol;
    }

    public long getCents() {
        return this.cents;
    }

    public String getNote() {
        return this.note;
    }

    public int getV() {
        return this.__v;
    }

    public boolean isPending() {
        return this.pending;
    }

    public boolean isReversed() {
        return this.reversed;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public String getDate() {
        return this.date;
    }

    public long getDateTimestamp() {
        return this.date_timestamp;
    }

    public String getDateLocalTime() {
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

    public String getAmount() {
        return this.amount;
    }

    public String getID2() {
        return this.id;
    }

    @Override
    public int compareTo(Object o) {
        long time1 = this.getDateTimestamp();
        long time2 = ((StreamTipTip) o).getDateTimestamp();

        if (time1 == time2) {
            return 0;
        }

        if (time1 > time2) {
            return -1;
        }

        return 1;
    }
}
