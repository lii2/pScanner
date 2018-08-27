package com.jeff.clients.reddit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeff.application.configuration.ConfigConstants;
import com.jeff.clients.Client;
import com.jeff.clients.reddit.model.RedditResponse;
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

    private ObjectMapper mapper;

    public RedditClient() {

        mapper = new ObjectMapper();

    }

    public RedditResponse makeQuery(RedditQuery query) {
        return getResponse(query.getResource());
    }

    private RedditResponse getResponse(String query) {
        HttpEntity<String> entity = createEntity();
        RestTemplate restTemplate = new RestTemplate();
        RedditResponse redditResponse = null;

        try {
            //JSON from String to Object
            ResponseEntity result = restTemplate.exchange(query, HttpMethod.GET, entity, String.class);
            redditResponse = mapper.readValue(result.getBody().toString(), RedditResponse.class);

        } catch (IOException e) {
            System.out.println("IOException thrown while mapping");
            e.printStackTrace();
        } catch (HttpServerErrorException e) {
            Date date = new Date();
            System.out.println("HttpServerErrorException: " + query + " " + date.toString());
            logger.error("HttpServerErrorException: " + query + " " + date.toString(), e);
            e.printStackTrace();
        }

        return redditResponse;
    }

    public String runQuery(String query) {
        HttpEntity<String> entity = createEntity();
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity result = restTemplate.exchange(query, HttpMethod.GET, entity, String.class);

        return result.getBody().toString();
    }

    private HttpEntity<String> createEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("User-Agent", ConfigConstants.USER_AGENT);
        return new HttpEntity<>("", httpHeaders);
    }


}
