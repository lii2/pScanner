package com.jeff.application;

import com.jeff.application.rateLimiter.DailyRateLimiter;
import com.jeff.application.timing.CalenderUtils;
import com.jeff.clients.email.EmailClient;
import com.jeff.clients.reddit.ChildData;
import com.jeff.clients.reddit.RedditClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class Scheduler {

    Logger logger = LoggerFactory.getLogger("scheduler");

    private RedditClient redditClient = new RedditClient();

    private EmailClient emailClient = new EmailClient();

    private DailyRateLimiter rateLimiter = new DailyRateLimiter(5);

    // Every minute
    @Scheduled(fixedDelay = 60000)
    public void run() {
        try {
            if (CalenderUtils.isPreMarket()) {
                redditClient.preScan();
            } else if (CalenderUtils.isRightHourAndDay()) {
                searchAndNotify();
            }
        } catch (Exception e) {
            System.out.println("Unknown error");
            logger.error("Unknown Error", e);
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
        scheduler.run();
    }

}

