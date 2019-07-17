package com.example.gigpad.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gigpad.R;
import com.example.gigpad.db.SavedSetlist;
import com.example.gigpad.tasks.DownloadImageTask;
import com.example.gigpad.ui.SavedSetlistActivity;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecyclerViewHolder> {
    private List<SavedSetlist> savedSetlists;
    private Context mContext;

    public MainAdapter(List<SavedSetlist> savedSetlists, Context mContext) {
        this.savedSetlists = savedSetlists;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MainAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_setlist_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.RecyclerViewHolder holder, int position) {
        SavedSetlist savedSetlist = savedSetlists.get(position);
        holder.setlistName.setText(savedSetlist.getArtist().get(0).getName());
        holder.setlistDate.setText(savedSetlist.getSetlist().getDate());
        holder.setlistLocation.setText(savedSetlist.getSetlist().getLocation());
        String numSongs = savedSetlist.getTrack().size() + " song";
        if (savedSetlist.getTrack().size() > 1) numSongs += "s";
        holder.setlistLength.setText(numSongs);
        new DownloadImageTask(holder.artistImage).execute(savedSetlist.getArtist().get(0).getImage());

        holder.parentLayout.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, SavedSetlistActivity.class);
            intent.putExtra("SAVED_SETLIST", savedSetlists.get(position));
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return savedSetlists.size();
    }
    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView setlistName;
        private TextView setlistLocation;
        private TextView setlistLength;
        private TextView setlistDate;
        private RelativeLayout parentLayout;
        private ImageView artistImage;
        RecyclerViewHolder(View view) {
            super(view);
            setlistName = itemView.findViewById(R.id.saved_setlist_title);
            setlistLocation = itemView.findViewById(R.id.saved_setlist_location);
            setlistLength = itemView.findViewById(R.id.saved_setlist_length);
            parentLayout = itemView.findViewById(R.id.saved_setlist_parent_layout);
            artistImage = itemView.findViewById(R.id.saved_artistImage);
            setlistDate = itemView.findViewById(R.id.saved_setlist_date);
        }
    }

    public void addItems(List<SavedSetlist> s) {
        this.savedSetlists = s;
        notifyDataSetChanged();
    }
}
