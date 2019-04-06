package com.example.showprep.setlist;

public class Song {
    private String name;
    private String with;
    private String cover;
    private String info;
    private String tape;


    public Song(String name, String with, String cover, String info, String tape) {
        this.name = name;
        this.with = with;
        this.cover = cover;
        this.info = info;
        this.tape = tape;
    }

    public String getName() {
        return name;
    }

    public String getWith() {
        return with;
    }

    public String getCover() {
        return cover;
    }

    public String getInfo() {
        return info;
    }

    public String getTape() {
        return tape;
    }
}
