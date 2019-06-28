package com.example.showprep.spotify;

public class SpotifySession {
    private static final SpotifySession access = new SpotifySession();
    private String token;
    private String userID;

    public static SpotifySession getInstance() {
        return access;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = "Bearer " + token;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }


}
