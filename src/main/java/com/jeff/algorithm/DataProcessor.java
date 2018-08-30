package com.jeff.algorithm;

import com.jeff.application.configuration.Converter;
import com.jeff.clients.email.EmailClient;
import com.jeff.clients.reddit.model.Children;
import com.jeff.clients.reddit.model.RedditResponse;
import com.jeff.database.CoreRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataProcessor {

    private List<String> oldPosts;

    private static final int MAX_NUMBER_OF_SENDS = 5;

    private static int CURRENT_NUMBER_OF_SENDS = 0;

    public DataProcessor() {
        oldPosts = Converter.getOldPosts();
    }

    public ArrayList<RedditResponse> process(ArrayList<RedditResponse> apiResponseList) {

        ArrayList<RedditResponse> responseArrayList = new ArrayList<>();

        for (RedditResponse redditResponse : apiResponseList) {
            for (Children child : redditResponse.getData().getChildren()) {
                if (!oldPosts.contains(child.getData().getTitle())) {
                    if (CURRENT_NUMBER_OF_SENDS < MAX_NUMBER_OF_SENDS) {
                        responseArrayList.add(redditResponse);
                    } else {
                        System.out.println("MAXIMUM NUMBER OF SENDS PER SESSION EXCEEDED: " + new Date().toString());
                        System.out.println("Did not send: " + child.getData().getTitle());
                        System.out.println("DataProcessor.process(ArrayList<RedditResponse> apiResponseList)");
                    }
                    CoreRepository.addToOldPost(child);
                }
            }
        }
        return responseArrayList;
    }

}
