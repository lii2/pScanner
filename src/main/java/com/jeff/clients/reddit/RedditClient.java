package com.jeff.clients.reddit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeff.application.configuration.ConfigConstants;
import com.jeff.application.configuration.Converter;
import com.jeff.clients.Client;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Component
public class RedditClient implements Client {

    public static final String RUNNING_QUERY = "https://www.reddit.com/r/RobinHoodPennyStocks/search.json?q=running&restrict_sr=on&sort=new&t=day";

    public static final String HALTED_QUERY = "https://www.reddit.com/r/RobinHoodPennyStocks/search.json?q=halted&restrict_sr=on&sort=new&t=day";

    public static final String TEST_QUERY = "https://www.reddit.com/r/RobinHoodPennyStocks/search.json?q=moon&restrict_sr=on&include_over_18=on&sort=relevance&t=all";

    private HashMap<String, ChildData> newPosts;
    private RestTemplate restTemplate;
    private HttpEntity<String> entity;
    private ObjectMapper mapper;
    private List<String> oldPosts;

    public RedditClient() {
        newPosts = new HashMap<>();
        restTemplate = new RestTemplate();
        entity = createEntity();
        mapper = new ObjectMapper();
        oldPosts = Converter.getOldPosts();
    }

    public void searchPennyStockData() {
        addNewPosts(RUNNING_QUERY);
        addNewPosts(HALTED_QUERY);
    }

    private void addNewPosts(String query) {
        ResponseEntity result = restTemplate.exchange(query, HttpMethod.GET, entity, String.class);
        RedditResult redditResult = new RedditResult();

        try {
            //JSON from String to Object
            redditResult = mapper.readValue(result.getBody().toString(), RedditResult.class);
        } catch (IOException e) {
            System.out.println("IOException thrown while mapping");
            e.printStackTrace();
        }

        for (Children child : redditResult.getData().getChildren()) {
            if (!oldPosts.contains(child.getData().getTitle())) {
                newPosts.put(child.getData().getTitle(), child.getData());
            }

        }
    }

    public HashMap<String, ChildData> getNewPosts() {
        return newPosts;
    }

    private HttpEntity<String> createEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-Agent", ConfigConstants.USER_AGENT);
        return new HttpEntity<>("", httpHeaders);
    }

    public void agePosts() {
        for (String entry : newPosts.keySet()) {
            Converter.addOldPost(entry);
        }
        newPosts.clear();
        oldPosts = Converter.getOldPosts();
    }

    public void preScan() {
        searchPennyStockData();
        agePosts();
    }

}
