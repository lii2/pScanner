package com.jeff.clients.email;

public enum TestQuery {
    TEST_QUERY("https://www.reddit.com/r/RobinHoodPennyStocks/top/.json?limit=1");

    String url;

    TestQuery(String url) {
        this.url = url;
    }

    public String getResource() {
        return url;
    }
}
