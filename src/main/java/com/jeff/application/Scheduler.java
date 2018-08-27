package com.jeff.application;

import com.jeff.clients.email.EmailClient;
import com.jeff.clients.reddit.model.ChildData;
import com.jeff.clients.reddit.RedditClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class Scheduler {

    private RedditClient redditClient = new RedditClient();

    private EmailClient emailClient = new EmailClient();

    // Get rid of old posts from overnight
    @Scheduled(cron = "0 0-29 9-10 ? * MON-FRI")
    public void prescan() {
        try {
            redditClient.preScan();
        } catch (Exception e) {
            Date date = new Date();
            System.out.println("Unknown error: " + date.toString());
        }
    }


    // Every minute in the first thirty minutes
    @Scheduled(cron = "0 30-59 9-10 ? * MON-FRI")
    public void firstThirtyMinutes() {
        try {
            searchAndNotify();

        } catch (Exception e) {
            Date date = new Date();
            System.out.println("Unknown error: " + date.toString());
        }
    }

    // Every minute during normal hourse other than the first 30 minutes
    @Scheduled(cron = "0 * 10-16 ? * MON-FRI")
    public void normalHours() {
        try {
            searchAndNotify();
        } catch (Exception e) {
            Date date = new Date();
            System.out.println("Unknown error: " + date.toString());
        }
    }

    private void searchAndNotify() {

        int numberOfSends = 0;

        redditClient.searchPennyStockData();
        if (!redditClient.getNewPosts().isEmpty()) {
            System.out.println(new Date().toString() + " new posts found: ");
            for (Map.Entry<String, ChildData> entry : redditClient.getNewPosts().entrySet()) {
                System.out.println(entry.getKey());
                if (numberOfSends < 5) {
                    emailClient.sendNotification(entry);
                    numberOfSends++;
                }
            }
        }
        redditClient.agePosts();
    }


}

