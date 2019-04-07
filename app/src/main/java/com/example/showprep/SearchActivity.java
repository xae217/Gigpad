package com.example.showprep;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    private ArrayList<Artist> artists;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
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
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
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
//        //TODO: For debugging ------>
//        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
//        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
//        okBuilder.addInterceptor(logging);
//
//        // <--------------------------

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(SetlistAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        SetlistAPI setlistAPI = retrofit.create(SetlistAPI.class);
        Call<SearchArtist> call = setlistAPI.searchArtist(searchTerm);
        call.enqueue(new Callback<SearchArtist>() {
            @Override
            public void onResponse(Call<SearchArtist> call, Response<SearchArtist> response) {

                if (response.code() == 200) {
                    SearchArtist searchResults = response.body();
                    refreshResults(searchResults);
                }
                else {
                    //TODO handle error codes.
                }
            }

            @Override
            public void onFailure(Call<SearchArtist> call, Throwable t) {
                Log.d(TAG, "ERROR!!!");
                //todo
            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new RecyclerViewAdapter(artists,this);
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
