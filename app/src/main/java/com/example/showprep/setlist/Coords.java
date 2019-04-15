package com.example.showprep.setlist;
import com.google.gson.annotations.SerializedName;

public class Coords {
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
}
