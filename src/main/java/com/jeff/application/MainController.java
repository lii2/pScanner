package com.jeff.application;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@RestController
public class MainController {

    @RequestMapping("/")
    public String index() {
        return "personal Scanner server is running " + new Date().toString();
    }

}