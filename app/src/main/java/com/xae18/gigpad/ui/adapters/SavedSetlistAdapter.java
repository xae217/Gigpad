package com.xae18.gigpad.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xae18.gigpad.R;
import com.xae18.gigpad.db.Track;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SavedSetlistAdapter extends RecyclerView.Adapter<SavedSetlistAdapter.ViewHolder> {
    private ArrayList<Track> mTracks;
    private Context mContext;

    public SavedSetlistAdapter(ArrayList<Track> mTracks, Context context) {
        this.mTracks = mTracks;
        this.mContext = context;
    }

    @NonNull
    @Override
    public SavedSetlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_saved_setlist_track, viewGroup, false);
        return new SavedSetlistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedSetlistAdapter.ViewHolder holder, int i) {
        String songTitle = mTracks.get(i).getName();
        holder.songNum.setText(String.format("%d.", i + 1));
        holder.songTitle.setText(songTitle);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView songTitle;
        TextView songNum;
        RelativeLayout parentLayout;
        public ViewHolder (View itemView) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.saved_song_title);
            songNum = itemView.findViewById(R.id.saved_song_num);
            parentLayout = itemView.findViewById(R.id.saved_song_parent_layout);
        }
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }
}
