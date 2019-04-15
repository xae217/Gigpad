package com.example.showprep;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.showprep.setlist.SetList;
import com.google.gson.Gson;


import java.util.ArrayList;

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ViewHolder>{
    private static final String TAG = "ShowAdapter";
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
        ShowsAdapter.ViewHolder holder = new ShowsAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String date = mShows.get(i).getEventDate();
        String venue = mShows.get(i).getVenue().getName();
        String city = mShows.get(i).getVenue().getCity().getName();
        String state = mShows.get(i).getVenue().getCity().getStateCode();
        String showTitle = date + " - " + venue + ", " + city+ ", " + state ;
        Gson gson = new Gson();
        viewHolder.showDate.setText(showTitle);
        viewHolder.parentLayout.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, SetlistActivity.class);
            intent.putExtra("SETLIST", gson.toJson(mShows.get(i)));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mShows.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView showDate;
        RelativeLayout parentLayout;
        public ViewHolder (View itemView) {
            super(itemView);
            showDate = itemView.findViewById(R.id.show_date);
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