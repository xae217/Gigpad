package com.example.showprep.spotify;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://developer.spotify.com/web-api/object-model/#paging-object">Paging object model</a>
 *
 * @param <T> expected object that is paged
 */
public class Pager<T> implements Parcelable  {

    private List<T> items;
    private int limit;
    private String next;
    private int offset;
    private String previous;
    private int total;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(href);
        dest.writeList(items);
        dest.writeInt(limit);
        dest.writeString(next);
        dest.writeInt(offset);
        dest.writeString(previous);
        dest.writeInt(total);
    }

    public Pager() {
    }

    protected Pager(Parcel in) {
        this.href = in.readString();
        this.items = in.readArrayList(ArrayList.class.getClassLoader());
        this.limit = in.readInt();
        this.next = in.readString();
        this.offset = in.readInt();
        this.previous = in.readString();
        this.total = in.readInt();
    }

    public static final Creator<Pager> CREATOR = new Creator<Pager>() {
        public Pager createFromParcel(Parcel source) {
            return new Pager(source);
        }

        public Pager[] newArray(int size) {
            return new Pager[size];
        }
    };

    public String href;

    public String getHref() {
        return href;
    }

    public List<T> getItems() {
        return items;
    }

    public int getLimit() {
        return limit;
    }

    public String getNext() {
        return next;
    }

    public int getOffset() {
        return offset;
    }

    public String getPrevious() {
        return previous;
    }

    public int getTotal() {
        return total;
    }
}
