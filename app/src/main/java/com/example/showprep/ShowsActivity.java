package com.example.showprep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.example.showprep.setlist.SearchSetlist;
import com.example.showprep.setlist.SetList;
import com.example.showprep.setlist.SetlistAPI;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowsActivity extends AppCompatActivity {
    private ArrayList<SetList> showDates;
    private RecyclerView recyclerView;
    private ShowsAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDates = new ArrayList<>();
        setContentView(R.layout.activity_shows);
        Intent intent = getIntent();
        String mbid = intent.getStringExtra(ArtistAdapter.ARTIST_MBID);
        initRecyclerView();
        searchSetlists(mbid);
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.show_recyclerView);
        adapter = new ShowsAdapter(showDates,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void searchSetlists(String mbid) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(SetlistAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        SetlistAPI setlistAPI = retrofit.create(SetlistAPI.class);
        Call<SearchSetlist> call = setlistAPI.searchSetlist(mbid);
        call.enqueue(new Callback<SearchSetlist>() {
            @Override
            public void onResponse(Call<SearchSetlist> call, Response<SearchSetlist> response) {
                if (response.code() == 200) {
                    SearchSetlist searchResults = response.body();
                    TextView artistTitle = findViewById(R.id.artist_title);
                    artistTitle.setText(searchResults.getsetlists().get(0).getArtist().getName());
                    adapter.clear();
                    for (SetList s : searchResults.getsetlists()) {
                        showDates.add(s);
                        adapter.notifyItemInserted(showDates.size() - 1);
                    }
                }
                else {
                    //TODO handle error codes.
                }
            }

            @Override
            public void onFailure(Call<SearchSetlist> call, Throwable t) {
                Log.d("SHOWSACT", "ERROR!!!");
                //todo
            }
        });
    }
}
