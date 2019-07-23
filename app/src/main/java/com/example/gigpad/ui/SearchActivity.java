package com.example.gigpad.ui;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.gigpad.R;
import com.example.gigpad.setlist.Artist;
import com.example.gigpad.setlist.SearchArtist;
import com.example.gigpad.setlist.SetlistAPI;
import com.example.gigpad.ui.adapters.SearchAdapter;

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
    private SearchAdapter adapter;
    private LinearLayout emptyView;
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
        MenuItem menuItem =  menu.findItem(R.id.search_menu_item);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getString(R.string.artist_name));
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
//                    emptyView.setVisibility(View.VISIBLE);
                }
                searchArtist(s);
                return true;
            }
        });

        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                emptyView.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                emptyView.setVisibility(View.VISIBLE);
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
                        refreshResults(searchResults, searchTerm);
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
        emptyView = findViewById(R.id.search_empty_view);
        adapter = new SearchAdapter(artists,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void refreshResults(SearchArtist searchResults, String searchTerm) {
        int top = searchResults.getArtists().size() < 10 ? searchResults.getArtists().size() : 9;
        adapter.clear();
        for (int i = 0; i < top; i++) {
            if (searchResults.getArtists().get(i).getTmid() != null ||
                    searchResults.getArtists().size() < 3 ||
                    searchResults.getArtists().get(i).getName().equalsIgnoreCase(searchTerm)) {
                artists.add(searchResults.getArtists().get(i));
                adapter.notifyItemInserted(artists.size() - 1);
            }
        }
    }
}
