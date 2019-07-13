package com.example.showprep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.showprep.db.SavedSetlist;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SavedSetlistAdapter extends RecyclerView.Adapter<SavedSetlistAdapter.RecyclerViewHolder> {
    private List<SavedSetlist> savedSetlists;
    private Context context;

    public SavedSetlistAdapter(List<SavedSetlist> savedSetlists, Context context) {
        this.savedSetlists = savedSetlists;
        this.context = context;
    }

    @NonNull
    @Override
    public SavedSetlistAdapter.RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_setlist_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SavedSetlistAdapter.RecyclerViewHolder holder, int position) {
        SavedSetlist savedSetlist = savedSetlists.get(position);
        holder.setlistName.setText(savedSetlist.getArtist().get(0).getName());
        holder.setlistDate.setText(savedSetlist.getSetlist().getDate());
        holder.setlistLocation.setText(savedSetlist.getSetlist().getLocation());
        holder.setlistLength.setText(savedSetlist.getTrack().size() + " tracks");
        new DownloadImageTask(holder.artistImage).execute(savedSetlist.getArtist().get(0).getImage());
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
        private RelativeLayout  partentLayout;
        private ImageView artistImage;
        RecyclerViewHolder(View view) {
            super(view);
            setlistName = itemView.findViewById(R.id.saved_setlist_title);
            setlistLocation = itemView.findViewById(R.id.saved_setlist_location);
            setlistLength = itemView.findViewById(R.id.saved_setlist_length);
            partentLayout = itemView.findViewById(R.id.saved_setlist_parent_layout);
            artistImage = itemView.findViewById(R.id.saved_artistImage);
            setlistDate = itemView.findViewById(R.id.saved_setlist_date);
        }
    }

    public void addItems(List<SavedSetlist> s) {
        this.savedSetlists = s;
        notifyDataSetChanged();
    }
}
