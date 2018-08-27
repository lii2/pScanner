package com.jeff.application;

import com.jeff.algorithmn.DataCollator;
import com.jeff.algorithmn.DataProcessor;
import com.jeff.clients.email.EmailClient;
import com.jeff.clients.reddit.RedditClient;
import com.jeff.clients.reddit.RedditQuery;
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
    public String test(@RequestParam(name="token") String name) {

        if(!name.equalsIgnoreCase(TEST_TOKEN))
            return "Access denied";

        DataCollator dataCollator = new DataCollator();

        DataProcessor dataProcessor = new DataProcessor();

        dataProcessor.addToOldPosts(dataCollator.getTestSignal());

        dataProcessor.sendEmails(dataCollator.getTestSignal());

        return "test email sent " + new Date().toString();
    }

}