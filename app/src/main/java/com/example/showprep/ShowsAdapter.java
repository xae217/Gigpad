package com.example.showprep;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.showprep.setlist.SetList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ViewHolder>{
    private ArrayList<SetList> mShows;
    private Context mContext;

    public ShowsAdapter(ArrayList<SetList> mShows, Context mContext) {
        this.mShows = mShows;
        this.mContext = mContext;
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
            Intent intent = new Intent(mContext, SetlistActivity.class);
            intent.putExtra("SETLIST", mShows.get(i));
            mContext.startActivity(intent);
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
}
