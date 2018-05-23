package com.jeff.application.reddit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildData {

    private String selftext;

    private String title;

    private String URL;

    public String getSelftext() {
        return selftext;
    }

    public void setSelftext(String selftext) {
        this.selftext = selftext;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return "ChildData{" +
                "selftext='" + selftext + '\'' +
                ", title='" + title + '\'' +
                ", URL='" + URL + '\'' +
                '}';
    }
}

