package com.frite.creativevues.model;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class TimestampModel {
    private long seconds;
    private long nanoseconds;


    public TimestampModel(com.google.firebase.Timestamp timestamp) {
        this.nanoseconds = timestamp.getNanoseconds();
        this.seconds = timestamp.getSeconds();
    }

    public String toDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(Locale.FRANCE);


        ZonedDateTime dateTime = new Timestamp(this.seconds * 1000).toInstant()
                .atZone(ZoneId.systemDefault());

        return dateTime.format(formatter).split(",")[0];
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public long getNanoseconds() {
        return nanoseconds;
    }

    public void setNanoseconds(long nanoseconds) {
        this.nanoseconds = nanoseconds;
    }
}
