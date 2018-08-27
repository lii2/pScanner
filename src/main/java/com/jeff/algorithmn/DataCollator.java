package com.jeff.algorithmn;

import com.jeff.clients.email.EmailClient;
import com.jeff.clients.reddit.RedditClient;
import com.jeff.clients.reddit.model.ChildData;

import java.util.Date;
import java.util.Map;

public class DataCollator {

    private RedditClient redditClient;

    private EmailClient emailClient;

    public DataCollator(){
        redditClient = new RedditClient();
        emailClient = new EmailClient();
    }

    public void getPreMarketData(){
        redditClient.preScan();
    }

    public void searchAndNotify() {

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
