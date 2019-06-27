package com.example.showprep;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.showprep.setlist.Set;
import com.example.showprep.setlist.SetList;
import com.example.showprep.setlist.Song;
import com.example.showprep.spotify.Playlist;
import com.example.showprep.spotify.SnapshotId;
import com.example.showprep.spotify.SpotifyAPI;
import com.example.showprep.spotify.SpotifySession;
import com.example.showprep.spotify.TracksPager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetlistActivity extends AppCompatActivity {
    private static final String TAG = "SetlistActivity";
    private ArrayList<Song> songs; // list of songs in a set list
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
        RecyclerView recyclerView = findViewById(R.id.setlist_recyclerview);
        SetlistAdapter adapter = new SetlistAdapter(songs, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void displaySetList() {
        setList = getIntent().getParcelableExtra("SETLIST");
        for (Set set : setList.getSets().getSets()) {
            songs.addAll(set.getSongs());
        }
    }

    public void createPlaylist(View view) {
        new PlaylistTask().execute("","","");
    }


    private String parseQuery(String artist, String track) {
       return "artist:" + artist + " track:" + track;
    }

    class PlaylistTask extends AsyncTask<String, String, String> {
        private ProgressDialog dialog = new ProgressDialog(SetlistActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String ... strings) {
            StringBuilder uris = new StringBuilder();
            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(SpotifyAPI.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            SpotifyAPI spotifyAPI = retrofit.create(SpotifyAPI.class);

            HashMap<String,String> playlistBody = new HashMap<>(); // Used for Post request body
            playlistBody.put("name", setList.getArtist().getName() + " - " + setList.getEventDate());
            playlistBody.put("description", setList.getArtist().getName() + " @ " +
                    setList.getVenue().getName() + " " + setList.getVenue().getCity().getName() +
                    " " + setList.getVenue().getCity().getStateCode() + " - " + setList.getEventDate());

            Call<Playlist> call = spotifyAPI.createPlaylist(SpotifySession.getInstance().getUserID(),
                    SpotifySession.getInstance().getToken(),"application/json",playlistBody);
            try {
                Response<Playlist> response = call.execute();
                if (response.code() == 201) {
                    Playlist spotifyPlaylist = response.body();
                    for(Song s : songs) {
                        Call<TracksPager> callTracks = spotifyAPI.spotifySearch(SpotifySession.getInstance().getToken(),
                                parseQuery(setList.getArtist().getName(), s.getName()), "track");
                        Response<TracksPager> responseTracks = callTracks.execute();
                        if(responseTracks.code() == 200) {
                            TracksPager pager = responseTracks.body();
                            if (pager != null) {
                                if (!pager.getTracks().getItems().isEmpty())
                                    uris.append(pager.getTracks().getItems().get(0).getUri()).append(",");
                            }
                            else
                                Log.d(TAG,  "NULL - Track not found");
                        }
                    }
                    Call<SnapshotId> callAddTrack = spotifyAPI.addToPlaylist(spotifyPlaylist.getId(),
                            SpotifySession.getInstance().getToken(),
                            uris.toString());
                    Response responseAddTrack = callAddTrack.execute();
                    if (responseAddTrack.code() == 201) {
                        return "Playlist created successfully.";
                    }
                }
            } catch (IOException e) {
                Toast.makeText(SetlistActivity.this, "network failure :(", Toast.LENGTH_SHORT).show();
            }
            return "Something went wrong.";
        }
        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            Toast.makeText(SetlistActivity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}

