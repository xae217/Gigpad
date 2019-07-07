package com.example.showprep;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        holder.setlistName.setText(savedSetlist.getSetlist().getName());
    }

    @Override
    public int getItemCount() {
        return savedSetlists.size();
    }
    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView setlistName;
        private RelativeLayout  partentLayout;
        RecyclerViewHolder(View view) {
            super(view);
            setlistName = itemView.findViewById(R.id.saved_setlist_title);
            partentLayout = itemView.findViewById(R.id.saved_setlist_parent_layout);
        }
    }

    public void addItems(List<SavedSetlist> s) {
        this.savedSetlists = s;
        notifyDataSetChanged();
    }
}
