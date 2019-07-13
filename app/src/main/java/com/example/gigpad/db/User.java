package com.example.gigpad.db;

import androidx.annotation.NonNull;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String image;

    public User(@NonNull String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
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
}
