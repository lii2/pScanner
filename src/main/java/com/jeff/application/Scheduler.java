package com.jeff.application;

import com.jeff.algorithmn.DataCollator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Scheduler {

    @Autowired
    private DataCollator dataCollator;

    // Get rid of old posts from overnight
    @Scheduled(cron = "* 0-29 9-10 * * MON-FRI")
    public void preMarketCleaning() {
        try {
           dataCollator.getPreMarketData();
        } catch (Exception e) {
            Date date = new Date();
            System.out.println("Unknown error: " + date.toString());
        }
    }


    // Every minute in the first thirty minutes
    @Scheduled(cron = "0 30-59 9-10 * * MON-FRI")
    public void firstThirtyMinutes() {
        try {
            dataCollator.searchAndNotify();

        } catch (Exception e) {
            Date date = new Date();
            System.out.println("Unknown error: " + date.toString());
        }
    }

    // Every minute during normal hours other than the first 30 minutes
    @Scheduled(cron = "* * 10-16 * * MON-FRI")
    public void normalHours() {
        try {
            dataCollator.searchAndNotify();
        } catch (Exception e) {
            Date date = new Date();
            System.out.println("Unknown error: " + date.toString());
        }
    }

}

