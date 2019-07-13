package com.example.gigpad.spotify;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Map;

public class TrackSimple implements Parcelable {
    private List<Artist> artists;
    private List<String> available_markets;
    private Boolean is_playable;
    private LinkedTrack linked_from;
    private int disc_number;
    private long duration_ms;
    private Boolean explicit;
    private Map<String, String> external_urls;
    private String href;
    private String id;
    private String name;
    private String preview_url;
    private int track_number;
    private String type;
    private String uri;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(artists);
        dest.writeStringList(this.available_markets);
        dest.writeValue(this.is_playable);
        dest.writeParcelable(this.linked_from, 0);
        dest.writeInt(this.disc_number);
        dest.writeLong(this.duration_ms);
        dest.writeValue(this.explicit);
        dest.writeMap(this.external_urls);
        dest.writeString(this.href);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.preview_url);
        dest.writeInt(this.track_number);
        dest.writeString(this.type);
        dest.writeString(this.uri);
    }

    public TrackSimple() {
    }

    protected TrackSimple(Parcel in) {
        this.artists = in.createTypedArrayList(Artist.CREATOR);
        this.available_markets = in.createStringArrayList();
        this.is_playable = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.linked_from = in.readParcelable(LinkedTrack.class.getClassLoader());
        this.disc_number = in.readInt();
        this.duration_ms = in.readLong();
        this.explicit = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.external_urls = in.readHashMap(Map.class.getClassLoader());
        this.href = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.preview_url = in.readString();
        this.track_number = in.readInt();
        this.type = in.readString();
        this.uri = in.readString();
    }

    public static final Creator<TrackSimple> CREATOR = new Creator<TrackSimple>() {
        public TrackSimple createFromParcel(Parcel source) {
            return new TrackSimple(source);
        }

        public TrackSimple[] newArray(int size) {
            return new TrackSimple[size];
        }
    };

    public List<Artist> getArtists() {
        return artists;
    }

    public List<String> getAvailable_markets() {
        return available_markets;
    }

    public Boolean getIs_playable() {
        return is_playable;
    }

    public LinkedTrack getLinked_from() {
        return linked_from;
    }

    public int getDisc_number() {
        return disc_number;
    }

    public long getDuration_ms() {
        return duration_ms;
    }

    public Boolean getExplicit() {
        return explicit;
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

    public String getName() {
        return name;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public int getTrack_number() {
        return track_number;
    }

    public String getType() {
        return type;
    }

    public String getUri() {
        return uri;
    }
}
