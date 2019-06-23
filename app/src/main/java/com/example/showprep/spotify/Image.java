package com.example.showprep.spotify;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#image-object">Image object model</a>
 */
public class Image implements Parcelable {
    public Integer width;
    public Integer height;
    public String url;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.width);
        dest.writeValue(this.height);
        dest.writeString(this.url);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.width = (Integer) in.readValue(Integer.class.getClassLoader());
        this.height = (Integer) in.readValue(Integer.class.getClassLoader());
        this.url = in.readString();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}
