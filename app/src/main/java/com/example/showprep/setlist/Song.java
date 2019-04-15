package com.example.showprep.setlist;

public class Song {
    private String name;
    private Artist with;
    private Artist cover;
    private String info;
    private String tape;


    public Song(String name, Artist with, Artist cover, String info, String tape) {
        this.name = name;
        this.with = with;
        this.cover = cover;
        this.info = info;
        this.tape = tape;
    }

    public String getName() {
        return name;
    }

    public Artist getWith() {
        return with;
    }

    public Artist getCover() {
        return cover;
    }

    public String getInfo() {
        return info;
    }

    public String getTape() {
        return tape;
    }
}
