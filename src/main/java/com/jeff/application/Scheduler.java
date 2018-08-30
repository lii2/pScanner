package com.jeff.application;

import com.jeff.algorithm.DataCollator;
import com.jeff.algorithm.DataProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class Scheduler {

    @Autowired
    private DataCollator dataCollator;

    @Autowired
    private DataProcessor dataProcessor;

    // Get rid of old hits from overnight
    @Scheduled(cron = "0 0-29 9-10 * * MON-FRI")
    public void preMarketCleaning() {
        try {
            dataProcessor.addToOldPosts(dataCollator.getPreMarketData());
        } catch (Exception e) {
            Date date = new Date();
            System.out.println("Unknown error: " + date.toString());
        }
    }

    // Every  ten seconds in the first thirty minutes
    @Scheduled(cron = "*/10 30-59 9-10 * * MON-FRI")
    public void firstThirtyMinutes() {
        try {
            dataProcessor.sendEmails(dataCollator.getNewSignals());
        } catch (Exception e) {
            Date date = new Date();
            System.out.println("Unknown error: " + date.toString());
        }
    }

    // Every minute during normal hours other than the first 30 minutes
    @Scheduled(cron = "* * 10-16 * * MON-FRI")
    public void normalHours() {
        try {
            dataProcessor.sendEmails(dataCollator.getNewSignals());
        } catch (Exception e) {
            Date date = new Date();
            System.out.println("Unknown error: " + date.toString());
        }
    }

}

