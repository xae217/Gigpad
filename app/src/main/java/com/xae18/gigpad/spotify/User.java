package com.xae18.gigpad.spotify;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

public class User implements Parcelable {
    private String display_name;
    private Map<String, String> external_urls;
    private Followers followers;
    private String href;
    private String id;
    private List<Image> images;
    private String type;
    private String uri;

    public User() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.display_name);
        dest.writeMap(this.external_urls);
        dest.writeParcelable(this.followers, 0);
        dest.writeString(this.href);
        dest.writeString(this.id);
        dest.writeTypedList(images);
        dest.writeString(this.type);
        dest.writeString(this.uri);
    }

    protected User(Parcel in) {
        this.display_name = in.readString();
        this.external_urls = in.readHashMap(Map.class.getClassLoader());
        this.followers = in.readParcelable(Followers.class.getClassLoader());
        this.href = in.readString();
        this.id = in.readString();
        this.images = in.createTypedArrayList(Image.CREATOR);
        this.type = in.readString();
        this.uri = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getDisplay_name() {
        return display_name;
    }

    public Map<String, String> getExternal_urls() {
        return external_urls;
    }

    public Followers getFollowers() {
        return followers;
    }

    public String getHref() {
        return href;
    }

    public String getId() {
        return id;
    }

    public List<Image> getImages() {
        return images;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }
}
