package com.jeff.clients.reddit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildData {

    private String selftext;

    private String title;

    private String URL;

    private int num_comments;

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

    public int getNum_comments() {
        return num_comments;
    }

    public void setNum_comments(int num_comments) {
        this.num_comments = num_comments;
    }


    @Override
    public String toString() {
        return "ChildData{" +
                "selftext='" + selftext + '\'' +
                ", title='" + title + '\'' +
                ", URL='" + URL + '\'' +
                ", num_comments=" + num_comments +
                '}';
    }
}

