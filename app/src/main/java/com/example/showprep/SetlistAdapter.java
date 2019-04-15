package com.example.showprep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.showprep.setlist.SetList;
import com.example.showprep.setlist.Song;

import java.util.ArrayList;

public class SetlistAdapter extends RecyclerView.Adapter<SetlistAdapter.ViewHolder>{
    private static final String TAG = "SetlistAdapter";
    private ArrayList<Song> mSong;
    private Context mContext;

    public SetlistAdapter(ArrayList<Song> mSong, Context mContext) {
        this.mSong = mSong;
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
        String songTitle = mSong.get(i).getName() ;
        viewHolder.songTitle.setText(songTitle);
    }

    @Override
    public int getItemCount() {
        return mSong.size();
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
        final int size = mSong.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mSong.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }
}