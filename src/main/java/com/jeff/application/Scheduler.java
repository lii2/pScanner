package com.jeff.application;

import com.jeff.application.email.EmailClient;
import com.jeff.application.reddit.RedditClient;
import org.springframework.beans.factory.annotation.Autowired;
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

        if (isRightTime()) {
            searchAndNotify();
        }

    }

    private void searchAndNotify() {
        redditClient.searchPennyStockData();
        if (!redditClient.getNewPosts().isEmpty() && notificationsSent < 5) {
            emailClient.sendNotifications(redditClient.getNewPosts());
            notificationsSent++;
        }
        redditClient.agePosts();
    }

    private boolean isRightTime() {
        calNewYork = Calendar.getInstance();
        calNewYork.setTimeZone(TimeZone.getTimeZone("America/New_York"));
        int hour = calNewYork.get(Calendar.HOUR_OF_DAY);
        int day = calNewYork.get(Calendar.DAY_OF_WEEK);
        return (hour > 9 && hour < 16) && (day != 1 && day != 7);
    }

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        scheduler.searchAndNotify();
    }

}

