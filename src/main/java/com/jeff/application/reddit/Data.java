package com.jeff.application.reddit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

    private String after;

    private int dist;

    @JsonIgnore
    private String facets;

    private String modhash;

    private Children[] children;

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public String getFacets() {
        return facets;
    }

    public void setFacets(String facets) {
        this.facets = facets;
    }

    public String getModhash() {
        return modhash;
    }

    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    public Children[] getChildren() {
        return children;
    }

    public void setChildren(Children[] children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "Data{" +
                "after='" + after + '\'' +
                ", dist=" + dist +
                ", facets='" + facets + '\'' +
                ", modhash='" + modhash + '\'' +
                ", children=" + Arrays.toString(children) +
                '}';
    }
}
