package com.example.gigpad.setlist;

import java.util.List;

public class SearchArtist {
    private String type;
    private String itemsPerPage;
    private String page;
    private String total;
    private List<Artist> artist;

    public SearchArtist(String type, String itemsPerPage, String page, String total, List<Artist> artist) {
        this.type = type;
        this.itemsPerPage = itemsPerPage;
        this.page = page;
        this.total = total;
        this.artist = artist;
    }

    public String getType() {
        return type;
    }

    public String getItemsPerPage() {
        return itemsPerPage;
    }

    public String getPage() {
        return page;
    }

    public String getTotal() {
        return total;
    }

    public List<Artist> getArtists() {
        return artist;
    }
}
