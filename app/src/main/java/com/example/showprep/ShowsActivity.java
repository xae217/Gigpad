package com.example.showprep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.showprep.setlist.SearchSetlist;
import com.example.showprep.setlist.SetList;
import com.example.showprep.setlist.SetlistAPI;
import com.example.showprep.spotify.ArtistsPager;
import com.example.showprep.spotify.SpotifyAPI;
import com.example.showprep.spotify.SpotifySession;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowsActivity extends AppCompatActivity {
    private ArrayList<SetList> showDates;
    private ShowsAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDates = new ArrayList<>();
        setContentView(R.layout.activity_shows);
        Intent intent = getIntent();
        String artistMbid = intent.getStringExtra(ArtistAdapter.ARTIST_MBID);
        initRecyclerView();
        searchSetlists(artistMbid);
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.show_recyclerView);
        adapter = new ShowsAdapter(showDates,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void searchSetlists(String artistMbid) {
        Call<SearchSetlist> call = SetlistAPI.getService().searchSetlist(artistMbid);
        call.enqueue(new Callback<SearchSetlist>() {
            @Override
            public void onResponse(Call<SearchSetlist> call, Response<SearchSetlist> response) {
                if (response.code() == 200) {
                    SearchSetlist searchResults = response.body();
                    if (searchResults != null) {
                        displayHeader(searchResults.getsetlists().get(0).getArtist().getName());
                        adapter.clear();
                        for (SetList s : searchResults.getsetlists()) {
                            showDates.add(s);
                            adapter.notifyItemInserted(showDates.size() - 1);
                        }
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), R.string.failedSetlistSearch, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SearchSetlist> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.networkFailure, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* Display Artist Name and image */
    private void displayHeader(String artistName) {
        TextView artistTitle = findViewById(R.id.artist_title);
        artistTitle.setText(artistName);
        Call<ArtistsPager> call = SpotifyAPI.getService().spotifySearchArtist(SpotifySession.getInstance().getToken(),
                "artist:" + artistName, "artist");
        call.enqueue(new Callback<ArtistsPager>() {
            @Override
            public void onResponse(Call<ArtistsPager> call, Response<ArtistsPager> response) {
                if (response.code() == 200) {
                    ArtistsPager artistsPager = response.body();
                    if (artistsPager.getArtists() != null) {
                        adapter.setArtist(artistsPager.getArtists().getItems().get(0));
                        String imgUrl = artistsPager.getArtists().getItems().get(0).getImages().get(0).getUrl();
                        new DownloadImageTask(findViewById(R.id.artistImage)).execute(imgUrl);
                    }

                }
            }

            @Override
            public void onFailure(Call<ArtistsPager> call, Throwable t) {
                Log.e("error", "Failed image");
            }
        });
    }


}
