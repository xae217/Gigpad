package com.example.showprep.setlist;

public class Artist {
    private String mbid;
    private String tmid;
    private String name;
    private String sortName;
    private String disambiguation;
    private String url;

    public Artist(String mbid, String tmid, String name, String sortName, String disambiguation, String url) {
        this.mbid = mbid;
        this.tmid = tmid;
        this.name = name;
        this.sortName = sortName;
        this.disambiguation = disambiguation;
        this.url = url;
    }

    public String getMbid() {
        return mbid;
    }

    public String getTmid() {
        return tmid;
    }

    public String getName() {
        return name;
    }

    public String getSortName() {
        return sortName;
    }

    public String getDisambiguation() {
        return disambiguation;
    }

    public String getUrl() {
        return url;
    }
}
