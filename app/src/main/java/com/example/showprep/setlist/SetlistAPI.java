package com.example.showprep.setlist;

import android.util.Log;

import java.lang.reflect.Array;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SetlistAPI {
    String apiVersion = "1.0";
    String BASE_URL = "https://api.setlist.fm/rest/" + apiVersion + "/";
    String API_KEY = "5e4c6fa8-f538-4d27-bd34-92a87136aace";

    @Headers({
            "Accept: application/json",
            "x-api-key: " + API_KEY
    })
    @GET("artist/{mbid}") //This is the api call. We can have input parameter {} and we use @Path to define them.
    Call<Artist> getArtist(@Path("mbid") String mbid); //Here we set what kind of object we are receiving.

    @Headers({
            "Accept: application/json",
            "x-api-key: " + API_KEY
    })
    @GET("search/artists")
    Call<SearchArtist> searchArtist(@Query("artistName") String artistName);

    @Headers({
            "Accept: application/json",
            "x-api-key: " + API_KEY
    })
    @GET("search/setlists")
    Call<SearchSetlist> searchSetlist(@Query("artistMbid") String artistMbid);
}
