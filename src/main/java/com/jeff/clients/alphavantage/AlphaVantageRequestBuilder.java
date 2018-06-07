package com.jeff.clients.alphavantage;

import com.jeff.application.configuration.ConfigConstants;

public class AlphaVantageRequestBuilder {

    private static final String HOST = "https://www.alphavantage.co/query?function=";

    private String function = "SMA";

    private String symbol = "SPY";

    private String interval = "daily";

    private String timePeriod = "5";

    private AlphaVantageRequestBuilder() {
    }

    public static AlphaVantageRequestBuilder aRequest() {
        return new AlphaVantageRequestBuilder();
    }

    public AlphaVantageRequestBuilder withSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public AlphaVantageRequestBuilder withInterval(String interval) {
        this.interval = interval;
        return this;
    }

    public AlphaVantageRequestBuilder withTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
        return this;
    }

    public AlphaVantageRequestBuilder forFunction(String function) {
        this.function = function;
        return this;
    }

    public String build() {
        return HOST + function + "&symbol=" + symbol + "&interval=" + interval + "&time_period=" + timePeriod
                + "&series_type=close&apikey=" + ConfigConstants.ALPHAVANTAGE_API_KEY;
    }

}
