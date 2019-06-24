package com.example.showprep.spotify;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {
    private Integer width;
    private Integer height;
    private String url;


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

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public String getUrl() {
        return url;
    }
}
