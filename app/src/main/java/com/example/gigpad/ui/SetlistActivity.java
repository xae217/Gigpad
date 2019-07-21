package com.example.gigpad.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.gigpad.R;
import com.example.gigpad.setlist.Set;
import com.example.gigpad.setlist.SetList;
import com.example.gigpad.setlist.Song;
import com.example.gigpad.tasks.PlaylistTask;
import com.example.gigpad.ui.adapters.SetlistAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SetlistActivity extends AppCompatActivity {
    private static final String TAG = "SetlistActivity";
    private ArrayList<Song> songs; // list of songs in a set list
    private SetList setList;
    private com.example.gigpad.spotify.Artist artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlist);
        this.songs = new ArrayList<>();
        initRecyclerView();
        displaySetList();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.setlist_recyclerview);
        SetlistAdapter adapter = new SetlistAdapter(songs, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void displaySetList() {
        TextView title = findViewById(R.id.setlist_title);
        setList = getIntent().getParcelableExtra("SETLIST");
        artist = getIntent().getParcelableExtra("ARTIST");
        setList.setEventDate(convertDate(setList.getEventDate())); // Change date format
        title.setText(String.format("%s - %s", setList.getArtist().getName(), setList.getEventDate()));
        for (Set set : setList.getSets().getSets()) {
            for(Song s : set.getSongs()) {
                if (!s.getName().isEmpty()) {
                    songs.add(s);
                }
            }
        }
    }

    private String convertDate(String d) {
        String newDate = d;
        try {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(d);
            newDate = new SimpleDateFormat("MMM dd, yyyy").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    /* on button Click */
    public void createPlaylist(View view) {
        ConstraintLayout l = findViewById(R.id.setlist_layout);
        new PlaylistTask(this, songs, setList, artist, l).execute("","","");
    }
}
