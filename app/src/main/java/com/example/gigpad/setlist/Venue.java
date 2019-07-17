package com.example.gigpad.setlist;

import android.os.Parcel;
import android.os.Parcelable;

public class Venue implements Parcelable {
    private City city;
    private String url;
    private String id;
    private String name;

    public Venue(City city, String url, String id, String name) {
        this.city = city;
        this.url = url;
        this.id = id;
        this.name = name;
    }

    public City getCity() {
        return city;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    protected Venue(Parcel in) {
        city = (City) in.readValue(City.class.getClassLoader());
        url = in.readString();
        id = in.readString();
        name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(city);
        dest.writeString(url);
        dest.writeString(id);
        dest.writeString(name);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Venue> CREATOR = new Parcelable.Creator<Venue>() {
        @Override
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }

        @Override
        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };
}