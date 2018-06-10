package com.jeff.application;

import com.jeff.application.rateLimiter.DailyRateLimiter;
import com.jeff.application.timing.CalenderUtils;
import com.jeff.clients.email.EmailClient;
import com.jeff.clients.reddit.ChildData;
import com.jeff.clients.reddit.RedditClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class Scheduler {

    private RedditClient redditClient = new RedditClient();

    private EmailClient emailClient = new EmailClient();

    private DailyRateLimiter rateLimiter = new DailyRateLimiter(5);

    // Every minute
    @Scheduled(fixedDelay = 60000)
    public void run() {
        try {
            if (CalenderUtils.isPreMarket()) {
                redditClient.preScan();
            }
            if (CalenderUtils.isRightHourAndDay()) {
                searchAndNotify();
            }
        } catch (Exception e) {
            System.out.println("Unknown error");
            e.printStackTrace();
        }
    }

    private void searchAndNotify() {
        redditClient.searchPennyStockData();
        if (!redditClient.getNewPosts().isEmpty())
            for (Map.Entry<String, ChildData> entry : redditClient.getNewPosts().entrySet()) {
                if (rateLimiter.attemptAction(new Date())) {
                    emailClient.sendNotification(entry);
                }
            }
        redditClient.agePosts();
    }


    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        scheduler.searchAndNotify();
    }

}

