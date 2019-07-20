package com.example.gigpad.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

// TODO: Look into using foreign keys
@Entity(tableName = "setlist",
        foreignKeys = { @ForeignKey(entity = Artist.class,
        parentColumns = "id",
        childColumns = "artistId",
        onDelete = CASCADE)})
public class Setlist implements Parcelable {
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

    protected Setlist(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        date = in.readString();
        location = in.readString();
        userId = in.readString();
        artistId = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(date);
        dest.writeString(location);
        dest.writeString(userId);
        dest.writeString(artistId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Setlist> CREATOR = new Parcelable.Creator<Setlist>() {
        @Override
        public Setlist createFromParcel(Parcel in) {
            return new Setlist(in);
        }

        @Override
        public Setlist[] newArray(int size) {
            return new Setlist[size];
        }
    };
}