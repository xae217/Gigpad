package com.xae18.gigpad.setlist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Set implements Parcelable {
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

    protected Set(Parcel in) {
        name = in.readString();
        encore = in.readString();
        if (in.readByte() == 0x01) {
            song = new ArrayList<Song>();
            in.readList(song, Song.class.getClassLoader());
        } else {
            song = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(encore);
        if (song == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(song);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Set> CREATOR = new Parcelable.Creator<Set>() {
        @Override
        public Set createFromParcel(Parcel in) {
            return new Set(in);
        }

        @Override
        public Set[] newArray(int size) {
            return new Set[size];
        }
    };
}
