package com.example.showprep.spotify;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface SpotifyAPI {
    String BASE_URL = "https://api.spotify.com/v1/";
    String API_KEY = SpotifyAccess.getInstance().getToken();

    @Headers({
            "Accept: application/json"
    })
    @GET("me") //This is the api call. We can have input parameter {} and we use @Path to define them.
    Call<User> getUser(@Header("Authorization") String auth); //Here we set what kind of object we are receiving.

    @GET("search")
    Call<TracksPager> spotifySearch(@Header("Authorization") String auth,
                                     @Query("q") String q,
                                     @Query("type") String type);
}
