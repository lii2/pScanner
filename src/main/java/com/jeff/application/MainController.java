package com.jeff.application;

import com.jeff.clients.email.EmailClient;
import com.jeff.clients.reddit.RedditClient;
import com.jeff.clients.reddit.RedditQueries;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

import static com.jeff.application.configuration.ConfigConstants.TEST_EMAIL;
import static com.jeff.application.configuration.ConfigConstants.TEST_TOKEN;

@RestController
public class MainController {

    @RequestMapping("/")
    public String index() {
        return "personal Scanner server is running " + new Date().toString();
    }

    @RequestMapping("/test")
    public String test(@RequestParam(name="token", required=true) String name) {

        if(!name.equalsIgnoreCase(TEST_TOKEN))
            return "Access denied";

        RedditClient redditClient = new RedditClient();

        String redditResult = redditClient.runQuery(RedditQueries.TEST_QUERY);

        EmailClient emailClient = new EmailClient();

        String date = new Date().toString();

        emailClient.send(TEST_EMAIL, "test email " + date, redditResult);

        return "test email sent " + date;
    }

}