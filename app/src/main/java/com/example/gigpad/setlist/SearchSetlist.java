package com.example.gigpad.setlist;

import java.util.List;

public class SearchSetlist {
    private String type;
    private String itemsPerPage;
    private String page;
    private String total;
    private List<SetList> setlist;

    public SearchSetlist(String type, String itemsPerPage, String page, String total, List<SetList> setlist) {
        this.type = type;
        this.itemsPerPage = itemsPerPage;
        this.page = page;
        this.total = total;
        this.setlist = setlist;
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

    public List<SetList> getsetlists() {
        return setlist;
    }
}
