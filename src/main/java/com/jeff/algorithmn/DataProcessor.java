package com.jeff.algorithmn;

import com.jeff.application.configuration.Converter;
import com.jeff.clients.ApiResponse;
import com.jeff.clients.email.EmailClient;
import com.jeff.clients.reddit.model.ChildData;
import com.jeff.clients.reddit.model.Children;
import com.jeff.clients.reddit.model.RedditResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DataProcessor {

    private List<String> oldPosts;

    private EmailClient emailClient;

    private static final int MAX_NUMBER_OF_SENDS = 5;

    private static int CURRENT_NUMBER_OF_SENDS = 0;

    public DataProcessor() {
        oldPosts = Converter.getOldPosts();
        emailClient = new EmailClient();
    }

    public void sendEmails(ArrayList<RedditResponse> apiResponseList) {
        for (RedditResponse redditResponse : apiResponseList) {
            for (Children child : redditResponse.getData().getChildren()) {
                if (!oldPosts.contains(child.getData().getTitle())) {
                    if (CURRENT_NUMBER_OF_SENDS < MAX_NUMBER_OF_SENDS) {
                        emailClient.sendAll(child.getData().getTitle(), child.getData().getURL());
                    } else {
                        System.out.println("MAXIMUM NUMBER OF SENDS PER SESSION EXCEEDED: " + new Date().toString());
                        System.out.println("Did not send: " + child.getData().getTitle());
                    }
                    Converter.addOldPost(child.getData().getTitle());
                }
            }
        }

    }

    public void addToOldPosts(ArrayList<RedditResponse> apiResponseList) {

        for (RedditResponse redditResponse : apiResponseList) {
            for (Children child : redditResponse.getData().getChildren()) {
                Converter.addOldPost(child.getData().getTitle());
            }
        }
    }

}
