package com.xae18.gigpad.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "track",
        primaryKeys =  {"id","setlistId"},
        foreignKeys = {@ForeignKey(entity = Setlist.class,
        parentColumns = "id",
        childColumns = "setlistId",
        onDelete = CASCADE)},
        indices = {@Index(value = {"setlistId", "id"})})
public class Track implements Parcelable, Comparable<Track>{
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

    protected Track(Parcel in) {
        id = in.readString();
        setlistId = in.readString();
        name = in.readString();
        uri = in.readString();
        trackNum = in.readInt();
        duration = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(setlistId);
        dest.writeString(name);
        dest.writeString(uri);
        dest.writeInt(trackNum);
        dest.writeLong(duration);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Track> CREATOR = new Parcelable.Creator<Track>() {
        @Override
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        @Override
        public Track[] newArray(int size) {
            return new Track[size];
        }
    };

    @Override
    public int compareTo(Track t) {
        return this.trackNum - t.trackNum;
    }
}