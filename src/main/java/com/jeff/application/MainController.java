package com.jeff.application;

import com.jeff.algorithm.DataCollator;
import com.jeff.algorithm.DataProcessor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

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