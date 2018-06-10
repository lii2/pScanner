package com.jeff.application.rateLimiter;

import java.util.Date;
import java.util.LinkedList;

import static java.lang.Math.toIntExact;

public class DailyRateLimiter {

    private LinkedList<Date> dates;

    private int rateLimit;

    public DailyRateLimiter(int limit) {
        this.dates = new LinkedList<Date>();
        this.rateLimit = limit;
    }

    public boolean attemptAction(Date date) {
        if (dates.size() < rateLimit) {
            dates.add(date);
            return true;
        } else {
            // At least one day
            if (daysBetween(dates.getFirst(), date) > 0) {
                System.out.println("popping");
                System.out.println(daysBetween(dates.getFirst(), date) > 0);
                dates.pop();
                dates.add(date);
                return true;
            }
        }

        return false;
    }

    private int daysBetween(Date date1, Date date2) {
        //Comparing dates
        long difference = Math.abs(date1.getTime() - date2.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);

        return toIntExact(differenceDates);
    }

    public static void main(String[] args) {
        DailyRateLimiter drl = new DailyRateLimiter(5);

        for (int x = 0; x < 6; x++) {
            Date date = new Date();

            if(drl.attemptAction(date))
                System.out.println("attempt succeeded.");
            else
                System.out.println("attempt failed.");
        }
    }
}
