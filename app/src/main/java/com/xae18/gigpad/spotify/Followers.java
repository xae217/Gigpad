package com.xae18.gigpad.spotify;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#followers-object">Followers</a>
 */
public class Followers implements Parcelable {
    private String href;
    private Integer total;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.href);
        dest.writeInt(this.total);
    }

    public Followers() {
    }

    protected Followers(Parcel in) {
        this.href = in.readString();
        this.total = in.readInt();
    }

    public static final Creator<Followers> CREATOR = new Creator<Followers>() {
        public Followers createFromParcel(Parcel source) {
            return new Followers(source);
        }

        public Followers[] newArray(int size) {
            return new Followers[size];
        }
    };

    public String getHref() {
        return href;
    }

    public Integer getTotal() {
        return total;
    }
}
