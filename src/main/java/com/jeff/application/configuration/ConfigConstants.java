package com.jeff.application.configuration;

public class ConfigConstants {

    public static final String USER_AGENT = Converter.getProperty("user.agent");

    public static final String EMAIL = Converter.getProperty("username");

    public static final String PASSWORD = Converter.getProperty("password");

    public static final String ALPHAVANTAGE_API_KEY = Converter.getProperty("alphavantage.key");

}
