package com.example.gigpad.setlist;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Coords implements Parcelable {
    @SerializedName("long")
    private String longitude;
    private String lat;

    public Coords(String longitude, String lat) {
        this.longitude = longitude;
        this.lat = lat;
    }

    public String getLonguitude() {
        return longitude;
    }

    public String getLat() {
        return lat;
    }

    protected Coords(Parcel in) {
        longitude = in.readString();
        lat = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(longitude);
        dest.writeString(lat);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Coords> CREATOR = new Parcelable.Creator<Coords>() {
        @Override
        public Coords createFromParcel(Parcel in) {
            return new Coords(in);
        }

        @Override
        public Coords[] newArray(int size) {
            return new Coords[size];
        }
    };
}