package com.xae18.gigpad.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xae18.gigpad.R;
import com.xae18.gigpad.setlist.SetList;
import com.xae18.gigpad.spotify.Artist;
import com.xae18.gigpad.ui.SetlistActivity;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ViewHolder>{
    private ArrayList<SetList> mShows;
    private Context mContext;
    private Artist artist;

    public ShowsAdapter(ArrayList<SetList> mShows, Context mContext) {
        this.mShows = mShows;
        this.mContext = mContext;
        this.artist = new Artist();
    }

    @NonNull
    @Override
    public ShowsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.show_search_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String dateString = mShows.get(i).getEventDate();
        String venue = mShows.get(i).getVenue().getName();
        String city = mShows.get(i).getVenue().getCity().getName();
        String state = mShows.get(i).getVenue().getCity().getStateCode();
        String showTitle = venue + "\n" + city + ", " + state ;
        String fDate;
        try {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateString);
            fDate = new SimpleDateFormat("MMM dd\nyyyy").format(date);
            viewHolder.showDate.setText(fDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.showLocation.setText(showTitle);
        viewHolder.parentLayout.setOnClickListener(view -> {
            if(mShows.get(i).getSets().getSets().isEmpty()) {
                Snackbar.make(viewHolder.parentLayout, mContext.getString(R.string.empty_setlist),
                        Snackbar.LENGTH_LONG)
                        .show();
            }
            else {
                Intent intent = new Intent(mContext, SetlistActivity.class);
                intent.putExtra("SETLIST", mShows.get(i));
                intent.putExtra("ARTIST", this.artist);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mShows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView showDate;
        TextView showLocation;
        RelativeLayout parentLayout;
        public ViewHolder (View itemView) {
            super(itemView);
            showDate = itemView.findViewById(R.id.show_date);
            showLocation =itemView.findViewById(R.id.show_location);
            parentLayout = itemView.findViewById(R.id.show_parent_layout);
        }
    }
    public void clear() {
        final int size = mShows.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mShows.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }
}
