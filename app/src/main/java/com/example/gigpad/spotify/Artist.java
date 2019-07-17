package com.example.gigpad.spotify;

import android.os.Parcel;

import java.util.List;

public class Artist extends ArtistSimple {
    private Followers followers;
    private List<String> genres;
    private List<Image> images;
    private Integer popularity;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.followers, flags);
        dest.writeStringList(this.genres);
        dest.writeTypedList(images);
        dest.writeValue(this.popularity);
    }

    public Artist() {
    }

    protected Artist(Parcel in) {
        super(in);
        this.followers = in.readParcelable(Followers.class.getClassLoader());
        this.genres = in.createStringArrayList();
        this.images = in.createTypedArrayList(Image.CREATOR);
        this.popularity = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        public Artist createFromParcel(Parcel source) {
            return new Artist(source);
        }

        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public Followers getFollowers() {
        return followers;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<Image> getImages() {
        return images;
    }

    public Integer getPopularity() {
        return popularity;
    }
}