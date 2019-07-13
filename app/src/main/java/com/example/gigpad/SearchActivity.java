package com.example.gigpad;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.gigpad.setlist.Artist;
import com.example.gigpad.setlist.SearchArtist;
import com.example.gigpad.setlist.SetlistAPI;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private ArrayList<Artist> artists;
    private ArtistAdapter adapter;
    private static final String TAG = "SearchActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        artists = new ArrayList<>();
        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchArtist(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (searchView.getQuery().length() == 0) {
                    adapter.clear();
                }
                searchArtist(s);
                return true;
            }
        });

        return true;
    }

    private void searchArtist(String searchTerm) {
        Call<SearchArtist> call = SetlistAPI.getService().searchArtist(searchTerm,"relevance");
        call.enqueue(new Callback<SearchArtist>() {
            @Override
            public void onResponse(Call<SearchArtist> call, Response<SearchArtist> response) {
                if (response.code() == 200) {
                    SearchArtist searchResults = response.body();
                    if (searchResults != null) {
                        refreshResults(searchResults);
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchArtist> call, Throwable t) {
                Log.d(TAG, "Error while searching artist. Check connection.");
            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new ArtistAdapter(artists,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void refreshResults(SearchArtist searchResults) {
        int top = searchResults.getArtists().size() < 10 ? searchResults.getArtists().size() : 9;
        adapter.clear();
        for (int i = 0; i < top; i++) {
            if (searchResults.getArtists().get(i).getTmid() != null || searchResults.getArtists().size() < 3) {
                artists.add(searchResults.getArtists().get(i));
                adapter.notifyItemInserted(artists.size() - 1);
            }
        }
    }
}
