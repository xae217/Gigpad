package com.xae18.gigpad.db;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class SavedSetlist implements Parcelable {
    @Embedded
    private Setlist setlist;
    @Relation(parentColumn = "artistId", entityColumn = "id")
    private List<Artist> artist;
    @Relation(parentColumn = "id", entityColumn = "setlistId")
    private List<Track> track;

    public SavedSetlist(Setlist setlist, List<Track> track, List<Artist>artist) {
        this.setlist = setlist;
        this.track = track;
        this.artist = artist;
    }

    public Setlist getSetlist() {
        return setlist;
    }

    public List<Artist> getArtist() {
        return artist;
    }

    public List<Track> getTrack() {
        Collections.sort(this.track);
        return this.track;
    }

    protected SavedSetlist(Parcel in) {
        setlist = (Setlist) in.readValue(Setlist.class.getClassLoader());
        if (in.readByte() == 0x01) {
            artist = new ArrayList<Artist>();
            in.readList(artist, Artist.class.getClassLoader());
        } else {
            artist = null;
        }
        if (in.readByte() == 0x01) {
            track = new ArrayList<Track>();
            in.readList(track, Track.class.getClassLoader());
        } else {
            track = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(setlist);
        if (artist == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(artist);
        }
        if (track == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(track);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SavedSetlist> CREATOR = new Parcelable.Creator<SavedSetlist>() {
        @Override
        public SavedSetlist createFromParcel(Parcel in) {
            return new SavedSetlist(in);
        }

        @Override
        public SavedSetlist[] newArray(int size) {
            return new SavedSetlist[size];
        }
    };
}
