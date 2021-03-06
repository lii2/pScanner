package com.jeff.clients.reddit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jeff.clients.ApiResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RedditResponse implements ApiResponse
{

    private Data data;

    private String kind;

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }

    public String getKind ()
    {
        return kind;
    }

    public void setKind (String kind)
    {
        this.kind = kind;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", kind = "+kind+"]";
    }
}

