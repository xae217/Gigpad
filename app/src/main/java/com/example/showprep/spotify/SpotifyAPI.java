package com.example.showprep.spotify;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpotifyAPI {
    String BASE_URL = "https://api.spotify.com/v1/";


    @GET("me")
    Call<User> getUser(@Header("Authorization") String auth);

    @GET("search")
    Call<TracksPager> spotifySearch(@Header("Authorization") String auth,
                                    @Query("q") String q,
                                    @Query("type") String type);

    @POST("users/{userid}/playlists")
    Call<Playlist> createPlaylist(@Path("userid") String userid,
                                  @Header("Authorization") String auth,
                                  @Header("Content-Type") String type,
                                  @Body HashMap<String,String> body);

    @POST("playlists/{playlist_id}/tracks")
    Call<SnapshotId> addToPlaylist(@Path("playlist_id") String playlistId,
                                   @Header("Authorization") String auth,
                                   @Query("uris") String queryParameters);
}
