package com.example.gigpad.spotify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public abstract class PlaylistBase implements Parcelable {
    private Boolean collaborative;
    private Map<String, String> external_urls;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private User owner;
    @SerializedName("private")
    private Boolean is_public;
    private String snapshot_id;
    private String type;
    private String uri;

    protected PlaylistBase() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.collaborative);
        dest.writeMap(this.external_urls);
        dest.writeValue(this.href);
        dest.writeValue(this.id);
        dest.writeTypedList(this.images);
        dest.writeValue(this.name);
        dest.writeParcelable(owner, flags);
        dest.writeValue(is_public);
        dest.writeValue(snapshot_id);
        dest.writeValue(type);
        dest.writeValue(uri);
    }

    protected PlaylistBase(Parcel in) {
        this.collaborative = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.external_urls = in.readHashMap(Map.class.getClassLoader());
        this.href = (String) in.readValue(String.class.getClassLoader());
        this.id = (String) in.readValue(String.class.getClassLoader());
        this.images = in.createTypedArrayList(Image.CREATOR);
        this.name = (String) in.readValue(String.class.getClassLoader());
        this.owner = in.readParcelable(User.class.getClassLoader());
        this.is_public = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.snapshot_id = (String) in.readValue(String.class.getClassLoader());
        this.type = (String) in.readValue(String.class.getClassLoader());
        this.uri = (String) in.readValue(String.class.getClassLoader());
    }

    public Boolean getCollaborative() {
        return collaborative;
    }

    public Map<String, String> getExternal_urls() {
        return external_urls;
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

    public String getName() {
        return name;
    }

    public User getOwner() {
        return owner;
    }

    public Boolean getIs_public() {
        return is_public;
    }

    public String getSnapshot_id() {
        return snapshot_id;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }
}
