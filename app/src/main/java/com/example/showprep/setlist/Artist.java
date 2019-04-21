package com.example.showprep.setlist;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {
    private String mbid;
    private String tmid;
    private String name;
    private String sortName;
    private String disambiguation;
    private String url;

    public Artist(String mbid, String tmid, String name, String sortName, String disambiguation, String url) {
        this.mbid = mbid;
        this.tmid = tmid;
        this.name = name;
        this.sortName = sortName;
        this.disambiguation = disambiguation;
        this.url = url;
    }

    public String getMbid() {
        return mbid;
    }

    public String getTmid() {
        return tmid;
    }

    public String getName() {
        return name;
    }

    public String getSortName() {
        return sortName;
    }

    public String getDisambiguation() {
        return disambiguation;
    }

    public String getUrl() {
        return url;
    }

    protected Artist(Parcel in) {
        mbid = in.readString();
        tmid = in.readString();
        name = in.readString();
        sortName = in.readString();
        disambiguation = in.readString();
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mbid);
        dest.writeString(tmid);
        dest.writeString(name);
        dest.writeString(sortName);
        dest.writeString(disambiguation);
        dest.writeString(url);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Artist> CREATOR = new Parcelable.Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };
}