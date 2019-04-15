package com.example.showprep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.showprep.setlist.Set;
import com.example.showprep.setlist.SetList;
import com.example.showprep.setlist.SetlistAPI;
import com.example.showprep.setlist.Song;
import com.google.gson.Gson;

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
        Log.d("SHOW", "IN INIT");
        recyclerView = findViewById(R.id.setlist_recyclerview);
        adapter = new SetlistAdapter(songs,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void displaySetList() {
        Gson gson = new Gson();
        String strObj = getIntent().getStringExtra("SETLIST");
        Log.d(TAG, strObj);
        this.setList = gson.fromJson(strObj, SetList.class);
        Log.d(TAG, setList.getArtist().getName());
        Log.d(TAG, String.valueOf(setList.getSets().size()));
//        for (Set set : setList.getSets()) {
//            for (Song s : set.getSongs()) {
//                this.songs.add(s);
//                adapter.notifyItemInserted(this.songs.size() - 1);
//            }
//        }
    }
}
