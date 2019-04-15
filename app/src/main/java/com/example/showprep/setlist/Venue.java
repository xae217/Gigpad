package com.example.showprep.setlist;

public class Venue {
    private City city;
    private String url;
    private String id;
    private String name;

    public Venue(City city, String url, String id, String name) {
        this.city = city;
        this.url = url;
        this.id = id;
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
