package com.xae18.gigpad.spotify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PlaylistFollowPrivacy implements Parcelable {
    @SerializedName("public")
    private Boolean is_public;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.is_public);
    }

    public PlaylistFollowPrivacy() {
    }

    protected PlaylistFollowPrivacy(Parcel in) {
        this.is_public = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<PlaylistFollowPrivacy> CREATOR = new Creator<PlaylistFollowPrivacy>() {
        public PlaylistFollowPrivacy createFromParcel(Parcel source) {
            return new PlaylistFollowPrivacy(source);
        }

        public PlaylistFollowPrivacy[] newArray(int size) {
            return new PlaylistFollowPrivacy[size];
        }
    };

    public Boolean getIs_public() {
        return is_public;
    }
}
