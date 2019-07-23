package com.xae18.gigpad.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xae18.gigpad.R;
import com.xae18.gigpad.setlist.Artist;
import com.xae18.gigpad.ui.ShowsActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    public static final String ARTIST_MBID = "ARTIST_MBID";
    private ArrayList<Artist> mArtists;
    private Context mContext;

    public SearchAdapter(ArrayList<Artist> mArtists, Context mContext) {
        this.mArtists = mArtists;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.artist_search_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.artistName.setText(mArtists.get(i).getName());
        viewHolder.parentLayout.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, ShowsActivity.class);
            intent.putExtra(ARTIST_MBID, mArtists.get(i).getMbid() );
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mArtists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView artistName;
        RelativeLayout parentLayout;
        public ViewHolder (View itemView) {
            super(itemView);
            artistName = itemView.findViewById(R.id.artist_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
    public void clear() {
        final int size = mArtists.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mArtists.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }
}
