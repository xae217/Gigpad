package com.example.gigpad.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "artist")
public class Artist implements Parcelable {
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

    protected Artist(Parcel in) {
        id = in.readString();
        name = in.readString();
        image = in.readString();
        uri = in.readString();
        mbid = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(uri);
        dest.writeString(mbid);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}