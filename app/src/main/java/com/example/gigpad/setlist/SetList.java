package com.example.gigpad.setlist;
import android.os.Parcel;
import android.os.Parcelable;

public class SetList implements Parcelable {
    private Artist artist;
    private Venue venue;
    private Tour tour;
    private Sets sets;
    private String info;
    private String url;
    private String id;
    private String versionId;
    private String eventDate;
    private String lastUpdated;

    public SetList(Artist artist, Sets sets, Venue venue, Tour tour, String info, String url, String id, String versionId, String eventDate, String lastUpdated) {
        this.artist = artist;
        this.sets = sets;
        this.venue = venue;
        this.tour = tour;
        this.info = info;
        this.url = url;
        this.id = id;
        this.versionId = versionId;
        this.eventDate = eventDate;
        this.lastUpdated = lastUpdated;
    }

    public Artist getArtist() {
        return artist;
    }

    public Sets getSets() {
        return sets;
    }

    public Venue getVenue() {
        return venue;
    }

    public Tour getTour() {
        return tour;
    }

    public String getInfo() {
        return info;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

    public String getVersionId() {
        return versionId;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }


    protected SetList(Parcel in) {
        artist = (Artist) in.readValue(Artist.class.getClassLoader());
        venue = (Venue) in.readValue(Venue.class.getClassLoader());
        tour = (Tour) in.readValue(Tour.class.getClassLoader());
        sets = (Sets) in.readValue(Sets.class.getClassLoader());
        info = in.readString();
        url = in.readString();
        id = in.readString();
        versionId = in.readString();
        eventDate = in.readString();
        lastUpdated = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(artist);
        dest.writeValue(venue);
        dest.writeValue(tour);
        dest.writeValue(sets);
        dest.writeString(info);
        dest.writeString(url);
        dest.writeString(id);
        dest.writeString(versionId);
        dest.writeString(eventDate);
        dest.writeString(lastUpdated);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<SetList> CREATOR = new Parcelable.Creator<SetList>() {
        @Override
        public SetList createFromParcel(Parcel in) {
            return new SetList(in);
        }

        @Override
        public SetList[] newArray(int size) {
            return new SetList[size];
        }
    };

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}
