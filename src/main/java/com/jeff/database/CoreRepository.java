package com.jeff.database;

import com.jeff.application.configuration.Converter;
import com.jeff.clients.reddit.model.Children;
import com.jeff.clients.reddit.model.RedditResponse;

import java.util.ArrayList;

public class CoreRepository {
    public static void addAllToOldPosts(ArrayList<RedditResponse> apiResponseList) {
        for (RedditResponse redditResponse : apiResponseList) {
            for (Children child : redditResponse.getData().getChildren()) {
                Converter.addOldPost(child.getData().getTitle());
            }
        }
    }

    public static void addToOldPost(Children child){
        Converter.addOldPost(child.getData().getTitle());
    }
}
