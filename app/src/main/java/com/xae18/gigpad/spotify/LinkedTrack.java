package com.xae18.gigpad.spotify;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

public class LinkedTrack implements Parcelable {
    private Map<String, String> external_urls;
    private String href;
    private String id;
    private String type;
    private String uri;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(this.external_urls);
        dest.writeString(this.href);
        dest.writeString(this.id);
        dest.writeString(this.type);
        dest.writeString(this.uri);
    }

    public LinkedTrack() {
    }

    protected LinkedTrack(Parcel in) {
        this.external_urls = in.readHashMap(ClassLoader.getSystemClassLoader());
        this.href = in.readString();
        this.id = in.readString();
        this.type = in.readString();
        this.uri = in.readString();
    }

    public static final Creator<LinkedTrack> CREATOR = new Creator<LinkedTrack>() {
        public LinkedTrack createFromParcel(Parcel source) {
            return new LinkedTrack(source);
        }

        public LinkedTrack[] newArray(int size) {
            return new LinkedTrack[size];
        }
    };

    public Map<String, String> getExternal_urls() {
        return external_urls;
    }

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }
}
