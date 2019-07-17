package com.example.gigpad.setlist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Sets implements Parcelable {
    private List<Set> set;


    public Sets(List<Set> set) {
        this.set = set;

    }

    public List<Set> getSets() {
        return this.set;
    }


    protected Sets(Parcel in) {
        if (in.readByte() == 0x01) {
            set = new ArrayList<Set>();
            in.readList(set, Set.class.getClassLoader());
        } else {
            set = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (set == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(set);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Sets> CREATOR = new Parcelable.Creator<Sets>() {
        @Override
        public Sets createFromParcel(Parcel in) {
            return new Sets(in);
        }

        @Override
        public Sets[] newArray(int size) {
            return new Sets[size];
        }
    };
}