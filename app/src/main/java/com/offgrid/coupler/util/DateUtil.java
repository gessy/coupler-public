package com.offgrid.coupler.util;


import java.text.SimpleDateFormat;
import java.util.Date;

import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class DateUtil {

    public static String formatDate(Date target) {
        long days = DAYS.convert(new Date().getTime() - target.getTime(), MILLISECONDS);
        String pattern;
        if (days < 1) {
            pattern = "HH:mm";
        } else if (days < 7) {
            pattern = "E";
        } else if (days < 150) {
            pattern = "MMM dd";
        } else {
            pattern = "MMM dd yyyy";
        }
        return new SimpleDateFormat(pattern).format(target);
    }

}
