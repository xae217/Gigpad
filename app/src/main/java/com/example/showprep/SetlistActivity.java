package com.example.showprep;

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
import com.example.showprep.spotify.SpotifyAPI;
import com.example.showprep.spotify.SpotifySession;
import com.example.showprep.spotify.Track;
import com.example.showprep.spotify.TracksPager;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetlistActivity extends AppCompatActivity {
    private static final String TAG = "SetlistActivity";
    private ArrayList<Song> songs; // list of songs in a set list
    private RecyclerView recyclerView;
    private SetlistAdapter adapter;
    private SetList setList;
    private ArrayList<Track> playlist; // list of track objects from Spotify

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlist);
        this.songs = new ArrayList<>();
        this.playlist = new ArrayList<>();
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

    public void createPlaylist(View view) {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okBuilder.addInterceptor(logging);
        getTracks();
        Log.d(TAG, "size: " + this.playlist.size()); //TODO this is printing 0 because of async call

        HashMap<String,String> playlistBody = new HashMap<>(); // Used for Post request body
        playlistBody.put("name", "Test Playlist 1");
        playlistBody.put("description", "This is a test playlist");

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(SpotifyAPI.BASE_URL)
                .client(okBuilder.build())
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        SpotifyAPI spotifyAPI = retrofit.create(SpotifyAPI.class);
//        Log.d(TAG, SpotifySession.getInstance().getUserID());
        Call<Playlist> call = spotifyAPI.createPlaylist(SpotifySession.getInstance().getUserID(), SpotifySession.getInstance().getToken(),"application/json",playlistBody);
        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                if (response.code() == 201) {
                    Playlist spotifyPlaylist = response.body();
                    Toast.makeText(getApplicationContext(),"Created Playlist",Toast.LENGTH_SHORT).show();
                    Log.d(TAG,spotifyPlaylist.getId());
                }
                else{
                    Log.d(TAG, "Failed to create playlist " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable throwable) {
                Log.d(TAG, "Failed call ");
            }
        });
    }

    private void getTracks() {
        this.playlist.clear();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(SpotifyAPI.BASE_URL)
//                .client(okBuilder.build())
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        SpotifyAPI spotifyAPI = retrofit.create(SpotifyAPI.class);

        for (Song s: songs) {
            Call<TracksPager> call = spotifyAPI.spotifySearch(SpotifySession.getInstance().getToken(),
                    parseQuery(setList.getArtist().getName(), s.getName()),
                    "track");
            call.enqueue(new Callback<TracksPager>() {
                @Override
                public void onResponse(Call<TracksPager> call, Response<TracksPager> response) {
                    if (response.code() == 200) {
                        TracksPager pager = response.body();
                        if (pager != null) {
                            playlist.add(pager.getTracks().getItems().get(0));
                        }
                        else
                            Log.d(TAG,  "NULL");
                    }
                    else {
                        Log.d(TAG, "Incorrect response code!!!");
                        //TODO handle error codes.
                    }
                }

                @Override
                public void onFailure(Call<TracksPager> call, Throwable throwable) {
                    Log.d(TAG, "ERROR!!!");
                    //TODO
                }
            });
        }
    }
    private String parseQuery(String artist, String track) {
       return "artist:" + artist + " track:" + track;
    }
}
