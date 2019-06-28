package com.example.showprep.setlist;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class SetlistAPI {
    public interface SetlistService {
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

    private static final String BASE_URL = "https://api.setlist.fm/rest/1.0/";

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SetlistAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();

    private static final SetlistService SETLIST_SERVICE = retrofit.create(SetlistService.class);

    public static SetlistService getService() {
        return SETLIST_SERVICE;
    }
}

