package com.example.showprep;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.showprep.setlist.Set;
import com.example.showprep.setlist.SetList;
import com.example.showprep.setlist.Song;
import com.example.showprep.spotify.SpotifyAPI;
import com.example.showprep.spotify.SpotifyAccess;
import com.example.showprep.spotify.Track;
import com.example.showprep.spotify.TracksPager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

    public void createPlaylist(View view) {
//        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        okBuilder.addInterceptor(logging);
        ArrayList<Track> playlist = getTracks();
        //TODO create playlist. We need user ID, maybe store it somwhere so we only fetch it once.
    }

    private ArrayList<Track> getTracks() {
        ArrayList<Track> playlist = new ArrayList<>();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(SpotifyAPI.BASE_URL)
//                .client(okBuilder.build())
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();
        SpotifyAPI spotifyAPI = retrofit.create(SpotifyAPI.class);
        Log.d(TAG, SpotifyAccess.getInstance().getToken() );

        for (Song s: songs) {
            Call<TracksPager> call = spotifyAPI.spotifySearch(SpotifyAccess.getInstance().getToken(),
                    parseQuery(setList.getArtist().getName(), s.getName()),
                    "track");
            call.enqueue(new Callback<TracksPager>() {
                @Override
                public void onResponse(Call<TracksPager> call, Response<TracksPager> response) {
                    if (response.code() == 200) {
                        TracksPager pager = response.body();
                        if (pager != null) {
                            playlist.add(pager.getTracks().getItems().get(0));
                            Log.d(TAG,  pager.getTracks().getItems().get(0).getName());
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
        return playlist;
    }
    private String parseQuery(String artist, String track) {
       return "artist:" + artist + " track:" + track;
    }
}
