package com.example.showprep.setlist;

import android.os.Parcel;
import android.os.Parcelable;

public class City implements Parcelable {
    private String id;
    private String name;
    private String stateCode;
    private String state;
    private Coords coords;
    private Country country;

    public City(String id, String name, String stateCode, String state) {
        this.id = id;
        this.name = name;
        this.stateCode = stateCode;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getState() {
        return state;
    }

    protected City(Parcel in) {
        id = in.readString();
        name = in.readString();
        stateCode = in.readString();
        state = in.readString();
        coords = (Coords) in.readValue(Coords.class.getClassLoader());
        country = (Country) in.readValue(Country.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(stateCode);
        dest.writeString(state);
        dest.writeValue(coords);
        dest.writeValue(country);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<City> CREATOR = new Parcelable.Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };
}