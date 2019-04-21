package com.example.showprep.setlist;

import android.os.Parcel;
import android.os.Parcelable;

public class Song implements Parcelable {
    private String name;
    private Artist with;
    private Artist cover;
    private String info;
    private String tape;


    public Song(String name, Artist with, Artist cover, String info, String tape) {
        this.name = name;
        this.with = with;
        this.cover = cover;
        this.info = info;
        this.tape = tape;
    }

    public String getName() {
        return name;
    }

    public Artist getWith() {
        return with;
    }

    public Artist getCover() {
        return cover;
    }

    public String getInfo() {
        return info;
    }

    public String getTape() {
        return tape;
    }

    protected Song(Parcel in) {
        name = in.readString();
        with = (Artist) in.readValue(Artist.class.getClassLoader());
        cover = (Artist) in.readValue(Artist.class.getClassLoader());
        info = in.readString();
        tape = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeValue(with);
        dest.writeValue(cover);
        dest.writeString(info);
        dest.writeString(tape);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}