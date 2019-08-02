package com.xae18.gigpad.spotify;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class SpotifyAPI {
    public interface SpotifyService {

        @GET("me")
        Call<User> getUser(@Header("Authorization") String auth);

        @GET("search")
        Call<TracksPager> spotifySearch(@Header("Authorization") String auth,
                                        @Query("q") String q,
                                        @Query("type") String type);

        @GET("search")
        Call<ArtistsPager> spotifySearchArtist(@Header("Authorization") String auth,
                                               @Query("q") String q,
                                               @Query("type") String type);

        @POST("users/{userid}/playlists")
        Call<Playlist> createPlaylist(@Path("userid") String userid,
                                      @Header("Authorization") String auth,
                                      @Header("Content-Type") String type,
                                      @Body HashMap<String, String> body);

        @POST("playlists/{playlist_id}/tracks")
        Call<SnapshotId> addToPlaylist(@Path("playlist_id") String playlistId,
                                       @Header("Authorization") String auth,
                                       @Query("uris") String queryParameters);
    }

    private static final String BASE_URL = "https://api.spotify.com/v1/";
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build();

    private static final SpotifyService SPOTIFY_SERVICE = retrofit.create(SpotifyService.class);

    public static SpotifyService getService() {
        return SPOTIFY_SERVICE;
    }
}