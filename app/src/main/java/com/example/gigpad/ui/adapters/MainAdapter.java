package com.example.gigpad.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gigpad.R;
import com.example.gigpad.db.SavedSetlist;
import com.example.gigpad.tasks.DeleteSetlistTask;
import com.example.gigpad.tasks.DownloadImageTask;
import com.example.gigpad.ui.SavedSetlistActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecyclerViewHolder> {
    private List<SavedSetlist> mSavedSetlists;
    private Context mContext;

    public MainAdapter(List<SavedSetlist> mSavedSetlists, Context mContext) {
        this.mSavedSetlists = mSavedSetlists;
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
        SavedSetlist savedSetlist = mSavedSetlists.get(position);
        holder.setlistName.setText(savedSetlist.getArtist().get(0).getName());
        holder.setlistDate.setText(savedSetlist.getSetlist().getDate());
        holder.setlistLocation.setText(savedSetlist.getSetlist().getLocation());
        String numSongs = savedSetlist.getTrack().size() + " song";
        if (savedSetlist.getTrack().size() > 1) numSongs += "s";
        holder.setlistLength.setText(numSongs);
        new DownloadImageTask(new WeakReference<>(holder.artistImage)).execute(savedSetlist.getArtist().get(0).getImage());

        holder.parentLayout.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, SavedSetlistActivity.class);
            intent.putExtra("SAVED_SETLIST", mSavedSetlists.get(position));
            mContext.startActivity(intent);
        });

        holder.btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(mContext, view);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_delete:
                                new DeleteSetlistTask(new WeakReference<>(mContext))
                                        .execute(mSavedSetlists.get(holder.getAdapterPosition()).getSetlist());
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.ss_more_menu, popup.getMenu());
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSavedSetlists.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView setlistName;
        private TextView setlistLocation;
        private TextView setlistLength;
        private TextView setlistDate;
        private RelativeLayout parentLayout;
        private ImageView artistImage;
        private ImageButton btnMenu;
        RecyclerViewHolder(View view) {
            super(view);
            setlistName = itemView.findViewById(R.id.saved_setlist_title);
            setlistLocation = itemView.findViewById(R.id.saved_setlist_location);
            setlistLength = itemView.findViewById(R.id.saved_setlist_length);
            parentLayout = itemView.findViewById(R.id.saved_setlist_parent_layout);
            artistImage = itemView.findViewById(R.id.saved_artistImage);
            setlistDate = itemView.findViewById(R.id.saved_setlist_date);
            btnMenu = itemView.findViewById(R.id.ss_menu_button);
        }
    }

    public void addItems(List<SavedSetlist> s) {
        this.mSavedSetlists = s;
        notifyDataSetChanged();
    }
}
