package com.xae18.gigpad.spotify;

import android.os.Parcel;
import android.os.Parcelable;


public class Playlist extends PlaylistBase {
    private String description;
    private Followers followers;
    private Pager<PlaylistTrack> tracks;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.description);
        dest.writeParcelable(this.followers, 0);
        dest.writeParcelable(this.tracks, 0);
    }

    public Playlist() {
    }

    protected Playlist(Parcel in) {
        super(in);
        this.description = in.readString();
        this.followers = in.readParcelable(Followers.class.getClassLoader());
        this.tracks = in.readParcelable(Pager.class.getClassLoader());
    }

    public static final Parcelable.Creator<Playlist> CREATOR = new Parcelable.Creator<Playlist>() {
        public Playlist createFromParcel(Parcel source) {
            return new Playlist(source);
        }

        public Playlist[] newArray(int size) {
            return new Playlist[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public Followers getFollowers() {
        return followers;
    }

    public Pager<PlaylistTrack> getTracks() {
        return tracks;
    }
}