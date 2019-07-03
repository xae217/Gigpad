package com.example.showprep;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.showprep.db.LocalDatabase;
import com.example.showprep.spotify.SpotifyAPI;
import com.example.showprep.spotify.SpotifySession;
import com.example.showprep.spotify.User;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private static final String REDIRECT_URI = "showprep://callback";
    private static final int REQUEST_CODE = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spotifyAuthentication();
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
                    new UserTask(LocalDatabase.getDatabase(MainActivity.this.getApplicationContext())).execute(user);
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
        private final LocalDatabase mdb;
        public UserTask (LocalDatabase db) {
            mdb = db;
        }
        @Override
        protected String doInBackground(User... params) {
            if(mdb.userDao().getUser(params[0].getId()) == null) {
                com.example.showprep.db.User newUser = new com.example.showprep.db.User(params[0].getId(),
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
