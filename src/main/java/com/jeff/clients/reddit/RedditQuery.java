package com.jeff.clients.reddit;

public enum RedditQuery {
    RUNNING_QUERY("https://www.reddit.com/r/RobinHoodPennyStocks/search.json?q=running&restrict_sr=on&sort=new&t=day"),
    HALTED_QUERY("https://www.reddit.com/r/RobinHoodPennyStocks/search.json?q=halted&restrict_sr=on&sort=new&t=day"),
    TEST_QUERY("https://www.reddit.com/r/RobinHoodPennyStocks/top/.json?limit=1");

    String url;

    RedditQuery(String url){
        this.url = url;
    }

    public String getResource(){
        return url;
    }

}
