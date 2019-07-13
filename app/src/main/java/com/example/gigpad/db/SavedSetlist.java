package com.example.gigpad.db;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class SavedSetlist {
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
        return track;
    }
}
