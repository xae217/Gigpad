package com.example.showprep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.showprep.setlist.Set;
import com.example.showprep.setlist.SetList;
import com.example.showprep.setlist.Song;

import java.util.ArrayList;

public class SetlistActivity extends AppCompatActivity {
    private static final String TAG = "SetlistActivity";
    private ArrayList<Song> songs;
    private RecyclerView recyclerView;
    private SetlistAdapter adapter;
    private SetList setList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlist);
        this.songs = new ArrayList<>();
        initRecyclerView();
        displaySetList();
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.setlist_recyclerview);
        adapter = new SetlistAdapter(songs,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void displaySetList() {
        setList = getIntent().getParcelableExtra("SETLIST");
        for (Set set : setList.getSets().getSets()) {
            for (Song song : set.getSongs()) {
                songs.add(song);
            }
        }
    }
}
