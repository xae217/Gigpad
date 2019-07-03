package com.example.showprep.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "track", primaryKeys = {"id","setlistId"})
public class Track {
    @NonNull
    private String id;
    @NonNull
    private String setlistId;
    private String name;
    private String uri;
    private int trackNum;
    private long duration;

    public Track(@NonNull String id, String setlistId, String name, String uri, int trackNum, long duration) {
        this.id = id;
        this.setlistId = setlistId;
        this.name = name;
        this.uri = uri;
        this.trackNum = trackNum;
        this.duration = duration;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getSetlistId() {
        return setlistId;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public int getTrackNum() {
        return trackNum;
    }

    public long getDuration() {
        return duration;
    }
}
