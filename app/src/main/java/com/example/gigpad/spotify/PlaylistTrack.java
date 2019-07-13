package com.example.gigpad.spotify;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaylistTrack implements Parcelable {
    private String added_at;
    private User added_by;
    private Track track;
    private Boolean is_local;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.added_at);
        dest.writeParcelable(this.added_by, flags);
        dest.writeParcelable(this.track, 0);
        dest.writeValue(this.is_local);
    }

    public PlaylistTrack() {
    }

    protected PlaylistTrack(Parcel in) {
        this.added_at = in.readString();
        this.added_by = in.readParcelable(User.class.getClassLoader());
        this.track = in.readParcelable(Track.class.getClassLoader());
        this.is_local = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<PlaylistTrack> CREATOR = new Creator<PlaylistTrack>() {
        public PlaylistTrack createFromParcel(Parcel source) {
            return new PlaylistTrack(source);
        }

        public PlaylistTrack[] newArray(int size) {
            return new PlaylistTrack[size];
        }
    };

    public String getAdded_at() {
        return added_at;
    }

    public User getAdded_by() {
        return added_by;
    }

    public Track getTrack() {
        return track;
    }

    public Boolean getIs_local() {
        return is_local;
    }
}
