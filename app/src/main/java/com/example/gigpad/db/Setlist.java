package com.example.gigpad.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "setlist")
/* TODO: Look into using foreign keys
@Entity(tableName = "setlist",
        foreignKeys = {@ForeignKey(entity = Track.class,
        parentColumns = "id",
        childColumns = "setlistId",
        onDelete = CASCADE), @ForeignKey(entity = Artist.class,
        parentColumns = "artistId",
        childColumns = "id",
        onDelete = CASCADE)}) */
public class Setlist {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String description;
    private String date;
    private String location;
    private String userId;
    private String artistId;

    public Setlist(@NonNull String id, String name, String description, String date, String location, String userId, String artistId) {
        this.id = id;
        this.name = name;
        this.description =  description;
        this.date = date;
        this.location = location;
        this.userId = userId;
        this.artistId = artistId;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getUserId() {
        return userId;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getDescription() {
        return description;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }
}
