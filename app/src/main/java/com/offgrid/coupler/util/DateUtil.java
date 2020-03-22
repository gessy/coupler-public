package com.offgrid.coupler.util;


import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class DateUtil {

    public static String formatMessageDate(Date target) {
        long days = DAYS.convert(new Date().getTime() - target.getTime(), MILLISECONDS);

        SimpleDateFormat format = new SimpleDateFormat();

        if (days > 150) {
            format.applyPattern("MMM dd yyyy");
        } else if (days >= 7 && days < 150) {
            format.applyPattern("MMM dd");
        } else if (days >= 2 && days < 7) {
            format.applyPattern("E");
        } else {
            format.applyPattern("E");
            String day = format.format(target);
            if (day.equals(format.format(new Date()))) {
                format.applyPattern("HH:mm");
            } else {
                return "Yesterday";
            }
        }

        return format.format(target);
    }


    public static String formatUserActivityDate(Date target) {
        long diff = new Date().getTime() - target.getTime();
        long days = DAYS.convert(diff, MILLISECONDS);

        SimpleDateFormat dateFormat = new SimpleDateFormat();
        String time = new SimpleDateFormat("HH:mm").format(target);

        if (days > 150) {
            dateFormat.applyPattern("MMM dd yyyy");
        } else if (days >= 7 && days < 150) {
            dateFormat.applyPattern("MMM dd");
        } else if (days >= 2 && days < 7) {
            dateFormat.applyPattern("E");
        } else {
            dateFormat.applyPattern("E");
            String day = dateFormat.format(target);
            if (day.equals(dateFormat.format(new Date()))) {
                long seconds = SECONDS.convert(diff, MILLISECONDS);
                return seconds < 7 ?  "online" : "last seen at " + time;
            } else {
                return  "last seen yesterday at " + time;
            }
        }

        return "last seen " + dateFormat.format(target) + " at " + time;
    }

}
