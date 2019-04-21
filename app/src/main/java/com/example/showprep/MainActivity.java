package com.example.showprep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;

import okhttp3.OkHttpClient;
import retrofit2.Call;


public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "07450964b36e46d6ab07178b94916e4a";
    private static final String REDIRECT_URI = "showprep://callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    private static final int REQUEST_CODE = 1337;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private String mAccessToken;
    private String mAccessCode;
    private Call mCall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.d("Debug", "Back");
        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                case TOKEN:
                    mAccessToken = response.getAccessToken();
                    connected();
                    break;
                case ERROR:
                    //TODO: Handle Error
                    response.getError();
                    break;
                default:
                    //TODO: Handle other cases
            }
        }
    }

    private void connected() {
        Log.d("main", "connected");

    }

    @Override
    protected void onStop() {
        super.onStop();
        //TODO: handle onStop
        //Should we log out/clear tokens?
        //AuthenticationClient#clearCookies
    }

    public void onGetUserProfileClicked(View view) {
        spotifyAuthentication();
        if (mAccessToken == null) {
            Log.d("Main", "NULL ACCESS");
            return;
        }

//        final Request request = new Request.Builder()
//                .url("https://api.spotify.com/v1/me")
//                .addHeader("Authorization","Bearer " + mAccessToken)
//                .build();
//
//        mCall = mOkHttpClient.newCall(request);
//
//        mCall.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                setResponse("Failed to fetch data: " + e);
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                try {
//                    final JSONObject jsonObject = new JSONObject(response.body().string());
//                    setResponse(jsonObject.toString(3));
//                } catch (JSONException e) {
//                    setResponse("Failed to parse data: " + e);
//                }
//            }
//        });
//
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void spotifyAuthentication() {
        AuthenticationRequest.Builder builder =
                new AuthenticationRequest.Builder(CLIENT_ID, AuthenticationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming", "app-remote-control"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);
    }
}
