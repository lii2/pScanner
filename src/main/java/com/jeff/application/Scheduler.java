package com.jeff.application;

import com.jeff.clients.email.EmailClient;
import com.jeff.clients.reddit.RedditClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.TimeZone;

@Component
public class Scheduler {

    RedditClient redditClient = new RedditClient();

    EmailClient emailClient = new EmailClient();

    static Calendar calNewYork;

    private static int notificationsSent = 0;

    // Every minute
    @Scheduled(fixedDelay = 60000)
    public void run() {
        try {
            if (isPreMarket()) {
                preScan();
            }
            if (isRightHourAndDay()) {
                searchAndNotify();
            }
        } catch (Exception e) {
            System.out.println("Unknown error");
            e.printStackTrace();
        }
    }

    private void searchAndNotify() {
        redditClient.searchPennyStockData();
        if (!redditClient.getNewPosts().isEmpty()
                && notificationsSent < 5) {
            emailClient.sendNotifications(redditClient.getNewPosts());
            notificationsSent++;
        }
        redditClient.agePosts();
    }

    private boolean isRightHourAndDay() {
        calNewYork = Calendar.getInstance();
        calNewYork.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        int hour = calNewYork.get(Calendar.HOUR_OF_DAY);
        int day = calNewYork.get(Calendar.DAY_OF_WEEK);
        int minute = calNewYork.get(Calendar.MINUTE);
        return (((hour == 9 && minute > 30) || hour > 9)
                && hour < 16) && (day != 1 && day != 7);
    }

    private void preScan() {
        redditClient.searchPennyStockData();
        redditClient.agePosts();
    }

    private boolean isPreMarket() {
        calNewYork = Calendar.getInstance();
        calNewYork.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        int hour = calNewYork.get(Calendar.HOUR_OF_DAY);
        int minute = calNewYork.get(Calendar.MINUTE);
        return (hour == 9 && minute < 30);
    }

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        scheduler.searchAndNotify();
    }

}

