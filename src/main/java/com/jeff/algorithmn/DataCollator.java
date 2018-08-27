package com.jeff.algorithmn;

import com.jeff.clients.email.EmailClient;
import com.jeff.clients.email.TestQuery;
import com.jeff.clients.reddit.RedditQuery;
import com.jeff.clients.reddit.RedditClient;
import com.jeff.clients.reddit.model.RedditResponse;

import java.util.ArrayList;

public class DataCollator {

    private RedditClient redditClient;

    public DataCollator() {
        redditClient = new RedditClient();
    }

    public ArrayList<RedditResponse> getPreMarketData() {

        ArrayList<RedditResponse> responseArrayList = new ArrayList<>();

        for (RedditQuery query : RedditQuery.values()) {
            responseArrayList.add(redditClient.makeQuery(query));
        }

        return responseArrayList;
    }

    public ArrayList<RedditResponse> getNewSignals() {

        ArrayList<RedditResponse> responseArrayList = new ArrayList<>();

        for (RedditQuery query : RedditQuery.values()) {

            responseArrayList.add(redditClient.makeQuery(query));

        }
        return responseArrayList;
    }

    public ArrayList<RedditResponse> getTestSignal() {

        ArrayList<RedditResponse> responseArrayList = new ArrayList<>();

        for (TestQuery query : TestQuery.values()) {

            responseArrayList.add(redditClient.getResponse(query.getResource()));

        }
        return responseArrayList;
    }

}
