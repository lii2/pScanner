package com.jeff.clients.reddit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeff.application.configuration.ConfigConstants;
import com.jeff.application.configuration.Converter;
import com.jeff.clients.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Component
public class RedditClient implements Client {

    Logger logger = LoggerFactory.getLogger("scheduler");

    private HashMap<String, ChildData> newPosts;
    private ObjectMapper mapper;
    private List<String> oldPosts;

    public RedditClient() {
        newPosts = new HashMap<>();
        mapper = new ObjectMapper();
        oldPosts = Converter.getOldPosts();
    }

    public void searchPennyStockData() {
        addNewPosts(RedditQueries.RUNNING_QUERY);
        addNewPosts(RedditQueries.HALTED_QUERY);
    }

    private void addNewPosts(String query) {
        HttpEntity<String> entity = createEntity();
        RestTemplate restTemplate = new RestTemplate();


        try {
            //JSON from String to Object
            ResponseEntity result = restTemplate.exchange(query, HttpMethod.GET, entity, String.class);
            RedditResult redditResult = mapper.readValue(result.getBody().toString(), RedditResult.class);

            for (Children child : redditResult.getData().getChildren()) {
                if (!oldPosts.contains(child.getData().getTitle())) {
                    newPosts.put(child.getData().getTitle(), child.getData());
                }
            }

        } catch (IOException e) {
            System.out.println("IOException thrown while mapping");
            e.printStackTrace();
        } catch (HttpServerErrorException e) {
            Date date = new Date();
            System.out.println("HttpServerErrorException: " + query + " " + date.toString());
            logger.error("HttpServerErrorException: " + query + " " + date.toString(), e);
            e.printStackTrace();
        }

    }

    public String runQuery(String query) {
        HttpEntity<String> entity = createEntity();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity result = restTemplate.exchange(query, HttpMethod.GET, entity, String.class);

        return result.getBody().toString();
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
