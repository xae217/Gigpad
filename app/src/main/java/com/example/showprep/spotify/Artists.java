package com.example.showprep.spotify;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Artists implements Parcelable {
    private List<Artist> artists;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(artists);
    }

    public Artists() {
    }

    protected Artists(Parcel in) {
        this.artists = in.createTypedArrayList(Artist.CREATOR);
    }

    public static final Parcelable.Creator<Artists> CREATOR = new Parcelable.Creator<Artists>() {
        public Artists createFromParcel(Parcel source) {
            return new Artists(source);
        }

        public Artists[] newArray(int size) {
            return new Artists[size];
        }
    };

    public List<Artist> getArtists() {
        return artists;
    }
}