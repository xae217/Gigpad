package com.example.showprep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.showprep.spotify.SpotifyAPI;
import com.example.showprep.spotify.SpotifySession;
import com.example.showprep.spotify.User;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

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
                    Toast.makeText(MainActivity.this, R.string.authFailed, Toast.LENGTH_SHORT).show();
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
                SpotifySession.getInstance().setUserID(user.getId());
                Log.d("Main", SpotifySession.getInstance().getUserID());
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
}
