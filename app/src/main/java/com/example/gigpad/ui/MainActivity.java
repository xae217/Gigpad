package com.example.gigpad.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.gigpad.R;
import com.example.gigpad.db.AppDatabase;
import com.example.gigpad.db.SavedSetlist;
import com.example.gigpad.db.SetlistViewModel;
import com.example.gigpad.spotify.SpotifyAPI;
import com.example.gigpad.spotify.SpotifySession;
import com.example.gigpad.spotify.User;
import com.example.gigpad.ui.adapters.MainAdapter;
import com.facebook.stetho.Stetho;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import java.util.ArrayList;
import java.util.List;

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
    private SetlistViewModel setlistViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES);
        setContentView(R.layout.activity_main);
        spotifyAuthentication();
        recyclerView = findViewById(R.id.savedSetlistRecyclerView);
        MainAdapter adapter = new MainAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Stetho.initializeWithDefaults(this); //TODO for DEBUG only. Remove gradel dependency
        setlistViewModel = ViewModelProviders.of(this).get(SetlistViewModel.class);
        setlistViewModel.getmSetlists().observe(MainActivity.this, new Observer<List<SavedSetlist>>() {
            @Override
            public void onChanged(List<SavedSetlist> savedSetlists) {
                adapter.addItems(savedSetlists);
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
                com.example.gigpad.db.User newUser = new com.example.gigpad.db.User(params[0].getId(),
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
