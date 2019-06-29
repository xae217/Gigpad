package com.example.showprep;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.example.showprep.setlist.Artist;
import com.example.showprep.setlist.SearchArtist;
import com.example.showprep.setlist.SetlistAPI;

import java.util.ArrayList;

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
                searchArtist(s);
                return true;
            }
        });

        return true;
    }

    private void searchArtist(String searchTerm) {
        Call<SearchArtist> call = SetlistAPI.getService().searchArtist(searchTerm);
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
            artists.add(searchResults.getArtists().get(i));
            adapter.notifyItemInserted(artists.size() - 1);
        }
    }
}
