package com.example.showprep;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerView";
    private ArrayList<String> mArtistNames = new ArrayList<>();
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<String> mArtistNames, Context mContext) {
        this.mArtistNames = mArtistNames;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.artist_search_view, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG,"onBind");
        viewHolder.artistName.setText(mArtistNames.get(i));
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.d(TAG, "Clicked artist View");
            }
        });
    }

    @Override
    public int getItemCount() {
        return mArtistNames.size();
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
}
