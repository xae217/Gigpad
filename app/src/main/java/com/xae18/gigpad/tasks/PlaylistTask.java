package com.xae18.gigpad.tasks;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.xae18.gigpad.R;
import com.xae18.gigpad.db.AppDatabase;
import com.xae18.gigpad.db.Artist;
import com.xae18.gigpad.db.Setlist;
import com.xae18.gigpad.db.Track;
import com.xae18.gigpad.setlist.SetList;
import com.xae18.gigpad.setlist.Song;
import com.xae18.gigpad.spotify.Playlist;
import com.xae18.gigpad.spotify.SnapshotId;
import com.xae18.gigpad.spotify.SpotifyAPI;
import com.xae18.gigpad.spotify.SpotifySession;
import com.xae18.gigpad.spotify.TracksPager;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import androidx.constraintlayout.widget.ConstraintLayout;
import retrofit2.Call;
import retrofit2.Response;

public class PlaylistTask extends AsyncTask<Void, Void, String> {
    private AlertDialog dialog;
    private Setlist newSetlist;
    private Artist newArtist;
    private ArrayList<Track> newTracks;
    private ArrayList<String> tracksNotFound;
    private WeakReference<Context> context;
    private ArrayList<Song> songs; // list of songs in a set list
    private SetList setList;
    private com.xae18.gigpad.spotify.Artist artist;
    private WeakReference<ConstraintLayout> parentLayout;


    public PlaylistTask(WeakReference<Context> context, ArrayList<Song> songs, SetList setList, com.xae18.gigpad.spotify.Artist artist, WeakReference<ConstraintLayout> parentLayout) {
        this.context = context;
        this.songs = songs;
        this.setList = setList;
        this.artist = artist;
        this.parentLayout = parentLayout;
    }

    @Override
    protected void onPreExecute() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context.get());
        alertBuilder.setCancelable(false); // We want user to wait for the playlist to be created.
        alertBuilder.setView(R.layout.layout_loading_dialog);
        dialog = alertBuilder.create();
        dialog.show();
        this.newTracks = new ArrayList<>();
        this.tracksNotFound = new ArrayList<>();
    }

    @Override
    protected String doInBackground(Void... params) {
        //Create playlist
        Call<Playlist> call = SpotifyAPI.getService().createPlaylist(SpotifySession.getInstance().getUserID(),
                SpotifySession.getInstance().getToken(),"application/json", createPlaylistRequestBody());
        try {
            Response<Playlist> response = call.execute();
            if (response.code() == 201) {
                Playlist spotifyPlaylist = response.body();

                if (spotifyPlaylist == null) {
                    return "Unable to create Playlist.";
                }

                newSetlist = new Setlist(spotifyPlaylist.getId(), spotifyPlaylist.getName(),
                        spotifyPlaylist.getDescription(),
                        setList.getEventDate(),
                        setList.getVenue().getCity().getName() + ", " + setList.getVenue().getCity().getStateCode(),
                        SpotifySession.getInstance().getUserID(),
                        "");

                newArtist = new Artist(artist.getId(),
                        artist.getName(),
                        artist.getImages().get(0).getUrl(),
                        artist.getUri(),
                        setList.getArtist().getMbid());

                //Add songs to playlist given the URIs
                Call<SnapshotId> callAddTrack = SpotifyAPI.getService().addToPlaylist(spotifyPlaylist.getId(),
                        SpotifySession.getInstance().getToken(),
                        getTrackUris());
                Response responseAddTrack = callAddTrack.execute();
                if (responseAddTrack.code() == 201) {
                    insertSetlistToDb();
                    return context.get().getString(R.string.createdPlaylist);
                }
            }
        } catch (IOException e) {
            Toast.makeText(context.get(), R.string.networkFailure, Toast.LENGTH_SHORT).show();
        }
        return "Something went wrong.";
    }
    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();
        if(!tracksNotFound.isEmpty()) {
            Snackbar snack = Snackbar.make(parentLayout.get(), context.get().getString(R.string.song_not_found),
                    Snackbar.LENGTH_SHORT);
            snack.show();
            snack.addCallback(new Snackbar.Callback(){
                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    Snackbar.make(parentLayout.get(), s,
                            Snackbar.LENGTH_SHORT)
                            .show();
                }
            });

        }
        else {
            Snackbar.make(parentLayout.get(), s,
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
    private void insertSetlistToDb() {
        newSetlist.setArtistId(newArtist.getId());
        AppDatabase db = AppDatabase.getDatabase(context.get());
        db.artistDoa().insert(newArtist);
        db.setlistDao().insert(newSetlist);
        for (Track t : newTracks) {
            db.trackDoa().insert(t);
        }
    }

    /* Map the tracks from Setlist.fm to Spotify URIs */
    private String getTrackUris() throws IOException {
        StringBuilder uris = new StringBuilder();
        for(int i = 0; i < songs.size(); i++) {
            Song s = songs.get(i);
            int trackNum = i;
            Call<TracksPager> callTracks = SpotifyAPI.getService().spotifySearch(SpotifySession.getInstance().getToken(),
                    parseQuery(setList.getArtist().getName(), s.getName()), "track");
            Response<TracksPager> responseTracks = callTracks.execute();
            if(responseTracks.code() == 200) {
                TracksPager pager = responseTracks.body();
                if (pager != null) {
                    if (!pager.getTracks().getItems().isEmpty()) {
                        com.xae18.gigpad.spotify.Track spotifyTrack = pager.getTracks().getItems().get(0);
                        uris.append(spotifyTrack.getUri()).append(",");
                        //Create Track records to insert to DB
                        Track newTrack = new Track(spotifyTrack.getId(),newSetlist.getId(),
                                spotifyTrack.getName(),
                                spotifyTrack.getUri(),
                                trackNum,
                                spotifyTrack.getDuration_ms());
                        newTracks.add(newTrack);
                    }
                    else {
                        tracksNotFound.add(s.getName());
                    }
                }
                else
                    tracksNotFound.add(s.getName());
            }
        }
        return uris.toString();
    }
    private HashMap<String, String> createPlaylistRequestBody() {
        HashMap<String,String> playlistBody = new HashMap<>();
        playlistBody.put("name", setList.getArtist().getName() + " - " + setList.getEventDate());
        playlistBody.put("description", setList.getArtist().getName() + " @ " +
                setList.getVenue().getName() + " " + setList.getVenue().getCity().getName() +
                " " + setList.getVenue().getCity().getStateCode() + " - " + setList.getEventDate());
        return playlistBody;
    }

    /* parse query for web call */
    private String parseQuery(String artist, String track) {
        return "artist:" + artist + " track:" + track;
    }

}