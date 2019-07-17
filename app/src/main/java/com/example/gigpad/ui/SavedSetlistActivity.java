package com.example.gigpad.ui;

import android.os.Bundle;

import com.example.gigpad.R;
import com.example.gigpad.db.SavedSetlist;
import com.example.gigpad.db.Track;
import com.example.gigpad.ui.adapters.SavedSetlistAdapter;

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

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.saved_setlist_recyclerview);
        SavedSetlistAdapter adapter = new SavedSetlistAdapter((ArrayList<Track>) mSavedSetlist.getTrack(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
