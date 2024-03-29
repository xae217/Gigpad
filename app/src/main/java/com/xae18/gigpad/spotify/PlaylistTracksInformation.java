package com.xae18.gigpad.spotify;

import android.os.Parcel;
import android.os.Parcelable;

public class PlaylistTracksInformation implements Parcelable {
    private String href;
    private int total;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
        dest.writeInt(this.total);
    }

    public PlaylistTracksInformation() {
    }

    protected PlaylistTracksInformation(Parcel in) {
        this.href = in.readString();
        this.total = in.readInt();
    }

    public static final Creator<PlaylistTracksInformation> CREATOR = new Creator<PlaylistTracksInformation>() {
        public PlaylistTracksInformation createFromParcel(Parcel source) {
            return new PlaylistTracksInformation(source);
        }

        public PlaylistTracksInformation[] newArray(int size) {
            return new PlaylistTracksInformation[size];
        }
    };

    public String getHref() {
        return href;
    }

    public int getTotal() {
        return total;
    }
}
