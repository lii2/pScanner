package com.jeff.application.timing;

import java.util.Calendar;
import java.util.TimeZone;

public class CalenderUtils {

    private CalenderUtils() {
    }

    public static boolean isRightHourAndDay() {
        Calendar calNewYork = Calendar.getInstance();
        calNewYork.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        int hour = calNewYork.get(Calendar.HOUR_OF_DAY);
        int day = calNewYork.get(Calendar.DAY_OF_WEEK);
        int minute = calNewYork.get(Calendar.MINUTE);
        return (((hour == 9 && minute > 30) || hour > 9)
                && hour < 16) && (day != 1 && day != 7);
    }

    public static boolean isPreMarket() {
        Calendar calNewYork = Calendar.getInstance();
        calNewYork.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        int hour = calNewYork.get(Calendar.HOUR_OF_DAY);
        int minute = calNewYork.get(Calendar.MINUTE);
        int day = calNewYork.get(Calendar.DAY_OF_WEEK);
        return (hour == 9 && minute < 30) && (day != 1 && day != 7);
    }
}
