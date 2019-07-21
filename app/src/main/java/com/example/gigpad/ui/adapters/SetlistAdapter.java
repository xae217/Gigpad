package com.example.gigpad.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gigpad.R;
import com.example.gigpad.setlist.Song;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SetlistAdapter extends RecyclerView.Adapter<SetlistAdapter.ViewHolder>{
    private static final String TAG = "SetlistAdapter";
    private ArrayList<Song> mSongs;
    private Context mContext;

    public SetlistAdapter(ArrayList<Song> mSongs, Context mContext) {
        this.mSongs = mSongs;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SetlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.setlist_search_view, viewGroup, false);
        SetlistAdapter.ViewHolder holder = new SetlistAdapter.ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String songTitle = mSongs.get(i).getName();
        viewHolder.songTitle.setText(String.format("%2d. %s", i + 1, songTitle));
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView songTitle;
        RelativeLayout parentLayout;
        public ViewHolder (View itemView) {
            super(itemView);
            songTitle = itemView.findViewById(R.id.song_title);
            parentLayout = itemView.findViewById(R.id.song_parent_layout);
        }
    }

    public void clear() {
        final int size = mSongs.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mSongs.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }
}
