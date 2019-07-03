package com.example.showprep;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.showprep.db.Artist;
import com.example.showprep.db.LocalDatabase;
import com.example.showprep.db.Setlist;
import com.example.showprep.db.Track;
import com.example.showprep.setlist.Set;
import com.example.showprep.setlist.SetList;
import com.example.showprep.setlist.Song;
import com.example.showprep.spotify.Playlist;
import com.example.showprep.spotify.SnapshotId;
import com.example.showprep.spotify.SpotifyAPI;
import com.example.showprep.spotify.SpotifySession;
import com.example.showprep.spotify.TracksPager;

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
        String fDate;
        try {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(setList.getEventDate());
            fDate = new SimpleDateFormat("MMM dd, yyyy").format(date);
            title.setText(String.format("%s | %s", setList.getArtist().getName(), fDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (Set set : setList.getSets().getSets()) {
            songs.addAll(set.getSongs());
        }
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
        private ProgressDialog dialog = new ProgressDialog(SetlistActivity.this);
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
                            setList.getVenue().getCity() + ", " + setList.getVenue().getCity().getStateCode(),
                            SpotifySession.getInstance().getUserID(),
                            "");
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
            LocalDatabase db = LocalDatabase.getDatabase(getApplicationContext());
            db.setlistDao().insert(newSetlist);
            db.artistDoa().insert(newArtist);
            for (Track t : newTracks) {
                db.trackDoa().insert(t);
            }
        }

        /* Map the tracks from Setlist.fm to Spotify URIs */
        private String getTrackUris() throws IOException {
            StringBuilder uris = new StringBuilder();
            for(int i = 0; i < songs.size(); i++) {
                Song s = songs.get(i);
                Call<TracksPager> callTracks = SpotifyAPI.getService().spotifySearch(SpotifySession.getInstance().getToken(),
                        parseQuery(setList.getArtist().getName(), s.getName()), "track");
                Response<TracksPager> responseTracks = callTracks.execute();
                if(responseTracks.code() == 200) {
                    TracksPager pager = responseTracks.body();
                    if (pager != null) {
                        if (!pager.getTracks().getItems().isEmpty()) {
                            com.example.showprep.spotify.Track spotifyTrack = pager.getTracks().getItems().get(0);

                            if(i == 0) {
                                newArtist = new Artist(spotifyTrack.getArtists().get(0).getId(),
                                        spotifyTrack.getArtists().get(0).getName(),
                                        "", //TODO this does not seem to be working. We cannot get images spotifyTrack.getArtists().get(0).getImages().get(0).getUrl()
                                        spotifyTrack.getArtists().get(0).getUri(),
                                        setList.getArtist().getMbid());
                            }

                            uris.append(spotifyTrack.getUri()).append(",");
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

//Todo solve random crashes.