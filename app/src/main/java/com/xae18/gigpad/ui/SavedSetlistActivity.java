package com.xae18.gigpad.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.xae18.gigpad.R;
import com.xae18.gigpad.db.SavedSetlist;
import com.xae18.gigpad.db.Track;
import com.xae18.gigpad.tasks.DownloadImageTask;
import com.xae18.gigpad.ui.adapters.SavedSetlistAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SavedSetlistActivity extends AppCompatActivity {

    private SavedSetlist mSavedSetlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_setlist);
        mSavedSetlist = getIntent().getParcelableExtra("SAVED_SETLIST");
        initRecyclerView();
        displayHeader();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.saved_setlist_recyclerview);
        SavedSetlistAdapter adapter = new SavedSetlistAdapter((ArrayList<Track>) mSavedSetlist.getTrack(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void displayHeader() {
        TextView title =  findViewById(R.id.ss_title);
        TextView date = findViewById(R.id.ss_date);
        title.setText(mSavedSetlist.getArtist().get(0).getName());
        date.setText((mSavedSetlist.getSetlist().getDate()));
        new DownloadImageTask(new WeakReference<>(findViewById(R.id.ss_artistImage))).execute(mSavedSetlist.getArtist().get(0).getImage());
    }
}
