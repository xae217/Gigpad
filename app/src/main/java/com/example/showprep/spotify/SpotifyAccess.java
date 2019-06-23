package com.example.showprep.spotify;

public class SpotifyAccess {
    private String token;
    public String getToken() {return token;}
    //TODO maybe store user id here so we don't have to call it multiple times.
    public void setToken(String token) {this.token = "Bearer " + token;}

    private static final SpotifyAccess access = new SpotifyAccess();
    public static SpotifyAccess getInstance() {return access;}
}
