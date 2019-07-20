package com.example.gigpad.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gigpad.R;
import com.example.gigpad.db.Artist;
import com.example.gigpad.db.AppDatabase;
import com.example.gigpad.db.Setlist;
import com.example.gigpad.db.Track;
import com.example.gigpad.setlist.Set;
import com.example.gigpad.setlist.SetList;
import com.example.gigpad.setlist.Song;
import com.example.gigpad.spotify.Playlist;
import com.example.gigpad.spotify.SnapshotId;
import com.example.gigpad.spotify.SpotifyAPI;
import com.example.gigpad.spotify.SpotifySession;
import com.example.gigpad.spotify.TracksPager;
import com.example.gigpad.ui.adapters.SetlistAdapter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Response;

public class SetlistActivity extends AppCompatActivity {
    private static final String TAG = "SetlistActivity";
    private ArrayList<Song> songs; // list of songs in a set list
    private SetList setList;
    private com.example.gigpad.spotify.Artist artist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlist);
        this.songs = new ArrayList<>();
        initRecyclerView();
        displaySetList();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.setlist_recyclerview);
        SetlistAdapter adapter = new SetlistAdapter(songs, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void displaySetList() {
        TextView title = findViewById(R.id.setlist_title);
        setList = getIntent().getParcelableExtra("SETLIST");
        artist = getIntent().getParcelableExtra("ARTIST");
        title.setText(String.format("%s - %s", setList.getArtist().getName(), convertDate(setList.getEventDate())));
        for (Set set : setList.getSets().getSets()) {
            songs.addAll(set.getSongs());
        }
    }

    private String convertDate(String d) {
        String newDate = d;
        try {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(d);
            newDate = new SimpleDateFormat("MMM dd, yyyy").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }
    public void createPlaylist(View view) {
        new PlaylistTask().execute("","","");
    }


    private String parseQuery(String artist, String track) {
       return "artist:" + artist + " track:" + track;
    }

    private HashMap<String, String> createPlaylistRequestBody() {
        HashMap<String,String> playlistBody = new HashMap<>();
        playlistBody.put("name", setList.getArtist().getName() + " - " + setList.getEventDate());
        playlistBody.put("description", setList.getArtist().getName() + " @ " +
                setList.getVenue().getName() + " " + setList.getVenue().getCity().getName() +
                " " + setList.getVenue().getCity().getStateCode() + " - " + setList.getEventDate());
        return playlistBody;
    }

    private class PlaylistTask extends AsyncTask<String, String, String> {
        private ProgressDialog dialog = new ProgressDialog(SetlistActivity.this); // TODO: Progress Dialog is deprecated
        private Setlist newSetlist;
        private Artist newArtist;
        private ArrayList<Track> newTracks;

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage(getString(R.string.pleaseWait));
            this.dialog.show();
            this.newTracks = new ArrayList<>();
        }

        @Override
        protected String doInBackground(String ... strings) {
            //Create playlist
            Call<Playlist> call = SpotifyAPI.getService().createPlaylist(SpotifySession.getInstance().getUserID(),
                    SpotifySession.getInstance().getToken(),"application/json", createPlaylistRequestBody());
            try {
                Response<Playlist> response = call.execute();
                if (response.code() == 201) {
                    Playlist spotifyPlaylist = response.body();

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


                    if (spotifyPlaylist == null) {
                        return "Unable to create Playlist.";
                    }
                    //Add songs to playlist given the URIs
                    Call<SnapshotId> callAddTrack = SpotifyAPI.getService().addToPlaylist(spotifyPlaylist.getId(),
                            SpotifySession.getInstance().getToken(),
                            getTrackUris());
                    Response responseAddTrack = callAddTrack.execute();
                    if (responseAddTrack.code() == 201) {
                        insertSetlistToDb();
                        return getString(R.string.createdPlaylist);
                    }
                }
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), R.string.networkFailure, Toast.LENGTH_SHORT).show();
            }
            return "Something went wrong.";
        }
        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }
        private void insertSetlistToDb() {
            newSetlist.setArtistId(newArtist.getId());
            AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
            db.artistDoa().insert(newArtist);
            db.setlistDao().insert(newSetlist);
            for (Track t : newTracks) {
                db.trackDoa().insert(t);
            }
        }

        /* Map the tracks from Setlist.fm to Spotify URIs */
        private String getTrackUris() throws IOException {
            StringBuilder uris = new StringBuilder();
            for(Song s: songs) {
                Call<TracksPager> callTracks = SpotifyAPI.getService().spotifySearch(SpotifySession.getInstance().getToken(),
                        parseQuery(setList.getArtist().getName(), s.getName()), "track");
                Response<TracksPager> responseTracks = callTracks.execute();
                if(responseTracks.code() == 200) {
                    TracksPager pager = responseTracks.body();
                    if (pager != null) {
                        if (!pager.getTracks().getItems().isEmpty()) {
                            com.example.gigpad.spotify.Track spotifyTrack = pager.getTracks().getItems().get(0);
                            uris.append(spotifyTrack.getUri()).append(",");
                            //Create Track records to insert to DB
                            Track newTrack = new Track(spotifyTrack.getId(),newSetlist.getId(),
                                    spotifyTrack.getName(),
                                    spotifyTrack.getUri(),
                                    spotifyTrack.getTrack_number(),
                                    spotifyTrack.getDuration_ms());
                            newTracks.add(newTrack);
                        }
                    }
                    else
                        Log.d(TAG,  "NULL - Track not found");
                }
            }
            return uris.toString();
        }

    }
}

//TODO clean this up. Move internal class to a new file. Refactore creation of newdbrecords.