package com.xae18.gigpad.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xae18.gigpad.R;
import com.xae18.gigpad.db.AppDatabase;
import com.xae18.gigpad.db.SavedSetlist;
import com.xae18.gigpad.db.SetlistViewModel;
import com.xae18.gigpad.spotify.SpotifyAPI;
import com.xae18.gigpad.spotify.SpotifySession;
import com.xae18.gigpad.spotify.User;
import com.xae18.gigpad.ui.adapters.MainAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String REDIRECT_URI = "gigpad://callback";
    private static final int REQUEST_CODE = 1337;
    private RecyclerView recyclerView;
    private LinearLayout emptyView;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_main);
        spotifyAuthentication();
        MainAdapter adapter = new MainAdapter(new ArrayList<>(), this);
        fabAdd = findViewById(R.id.fab_add);
        initRecyclerView(adapter);
        setViewModel(adapter);
    }

    private void initRecyclerView(MainAdapter adapter) {
        recyclerView = findViewById(R.id.savedSetlistRecyclerView);
        emptyView = findViewById(R.id.empty_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fabAdd.getVisibility() == View.VISIBLE) {
                    fabAdd.hide();
                } else if (dy < 0 && fabAdd.getVisibility() != View.VISIBLE) {
                    fabAdd.show();
                }
            }
        });
    }

    private void setViewModel(MainAdapter adapter) {
        SetlistViewModel setlistViewModel = ViewModelProviders.of(this).get(SetlistViewModel.class);
        setlistViewModel.getmSetlists().observe(MainActivity.this, new Observer<List<SavedSetlist>>() {
            @Override
            public void onChanged(List<SavedSetlist> savedSetlists) {
                if (savedSetlists.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else {
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.GONE);
                    adapter.addItems(savedSetlists);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            switch (response.getType()) {
                case TOKEN:
                    SpotifySession.getInstance().setToken(response.getAccessToken());
                    getUserId();
                    break;
                case ERROR:
                    Log.d("Debug", response.getError());
                    Toast.makeText(getApplicationContext(), R.string.authFailed, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void getUserId() {
        Call<User> call = SpotifyAPI.getService().getUser(SpotifySession.getInstance().getToken());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user != null) {
                    SpotifySession.getInstance().setUserID(user.getId());
                    new UserTask(AppDatabase.getDatabase(MainActivity.this.getApplicationContext())).execute(user);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Main", "Failed to fetch userid");
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void spotifyAuthentication() {
        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(getString(R.string.spotifyClientId),
                        AuthenticationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"streaming", "playlist-modify-public",
                "playlist-modify-private","app-remote-control"});
        AuthenticationRequest request = builder.build();
        AuthenticationClient.openLoginActivity(MainActivity.this, REQUEST_CODE, request);
    }

    public void onSearchSetlist(View view) {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }



    private static class UserTask extends AsyncTask<User, Void, String> {
        private final AppDatabase mdb;
        public UserTask (AppDatabase db) {
            mdb = db;
        }
        @Override
        protected String doInBackground(User... params) {
            if(mdb.userDao().getUser(params[0].getId()) == null) {
                com.xae18.gigpad.db.User newUser = new com.xae18.gigpad.db.User(params[0].getId(),
                        params[0].getDisplay_name(), params[0].getImages().get(0).getUrl());
                mdb.userDao().insert(newUser);
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
        }

        @Override
        protected void onPreExecute() {}

    }
}
