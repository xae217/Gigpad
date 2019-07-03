package com.example.showprep.db;

import androidx.annotation.NonNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "artist")
public class Artist {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String image;
    private String uri;
    private String mbid;

    public Artist(@NonNull String id, String name, String image, String uri, String mbid) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.uri = uri;
        this.mbid = mbid;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getUri() {
        return uri;
    }

    public String getMbid() {
        return mbid;
    }
}
