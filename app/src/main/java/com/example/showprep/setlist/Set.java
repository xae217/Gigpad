package com.example.showprep.setlist;

import java.util.List;

public class Set {
    private String name;
    private String encore;
    private List<Song> song;

    public Set(String name, String encore, List<Song> song) {
        this.name = name;
        this.encore = encore;
        this.song = song;
    }

    public String getName() {
        return name;
    }

    public String getEncore() {
        return encore;
    }

    public List<Song> getSongs() {
        return song;
    }
}
