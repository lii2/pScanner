package com.jeff.application;

import com.jeff.algorithm.DataCollator;
import com.jeff.algorithm.DataProcessor;
import com.jeff.clients.email.EmailClient;
import com.jeff.clients.reddit.model.Children;
import com.jeff.clients.reddit.model.RedditResponse;
import com.jeff.database.CoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class Scheduler {

    @Autowired
    private DataCollator dataCollator;

    @Autowired
    private DataProcessor dataProcessor;

    @Autowired
    private EmailClient emailClient;

    // Get rid of old hits from overnight
    @Scheduled(cron = "0 0-29 9-10 * * MON-FRI")
    public void preMarketCleaning() {
        try {
            CoreRepository.addAllToOldPosts(dataCollator.getPreMarketData());
        } catch (Exception e) {
            Date date = new Date();
            System.out.println("Unknown error: " + date.toString());
        }
    }

    // Every  ten seconds in the first thirty minutes
    @Scheduled(cron = "*/10 30-59 9-10 * * MON-FRI")
    public void firstThirtyMinutes() {
        try {
            ArrayList<RedditResponse> responseArrayList = dataProcessor.process(dataCollator.getNewSignals());
            for (RedditResponse redditResponse : responseArrayList) {
                for (Children child : redditResponse.getData().getChildren()) {
                    emailClient.sendAll(child.getData().getTitle(), child.getData().getURL());
                }
            }

        } catch (Exception e) {
            Date date = new Date();
            System.out.println("Unknown error: " + date.toString());
        }
        System.out.println(" first 30 loop completed");
    }

    // Every minute during normal hours other than the first 30 minutes
    @Scheduled(cron = "* * 10-16 * * MON-FRI")
    public void normalHours() {
        try {
            ArrayList<RedditResponse> responseArrayList = dataProcessor.process(dataCollator.getNewSignals());
            for (RedditResponse redditResponse : responseArrayList) {
                for (Children child : redditResponse.getData().getChildren()) {
                    emailClient.sendAll(child.getData().getTitle(), child.getData().getURL());
                }
            }
        } catch (Exception e) {
            Date date = new Date();
            System.out.println("Unknown error: " + date.toString());
        }
    }


}

