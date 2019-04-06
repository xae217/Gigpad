package com.example.showprep.setlist;
import java.util.List;

public class SetList {
    private Artist artist;
    private Venue venue;
    private Tour tour;
    private List<Set> set;
    private String info;
    private String url;
    private String id;
    private String versionId;
    private String eventDate;
    private String lastUpdated;

    public SetList(Artist artist, List<Set> set, Venue venue, Tour tour, String info, String url, String id, String versionId, String eventDate, String lastUpdated) {
        this.artist = artist;
        this.set = set;
        this.venue = venue;
        this.tour = tour;
        this.info = info;
        this.url = url;
        this.id = id;
        this.versionId = versionId;
        this.eventDate = eventDate;
        this.lastUpdated = lastUpdated;
    }

    public Artist getArtist() {
        return artist;
    }

    public List<Set> getSets() {
        return set;
    }

    public Venue getVenue() {
        return venue;
    }

    public Tour getTour() {
        return tour;
    }

    public String getInfo() {
        return info;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getVersionId() {
        return versionId;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }
}
