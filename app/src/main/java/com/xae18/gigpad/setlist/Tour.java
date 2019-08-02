package com.xae18.gigpad.setlist;

import android.os.Parcel;
import android.os.Parcelable;

public class Tour implements Parcelable {
    private String name;

    public Tour(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    protected Tour(Parcel in) {
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Tour> CREATOR = new Parcelable.Creator<Tour>() {
        @Override
        public Tour createFromParcel(Parcel in) {
            return new Tour(in);
        }

        @Override
        public Tour[] newArray(int size) {
            return new Tour[size];
        }
    };
}