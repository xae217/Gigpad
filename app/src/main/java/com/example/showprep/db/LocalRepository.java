package com.example.showprep.db;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class LocalRepository {
    private UserDao userDao;
    private SetlistDao setlistDao;
    private TrackDoa trackDoa;
    private ArtistDao artistDoa;

    LocalRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        setlistDao = db.setlistDao();
        userDao = db.userDao();
        trackDoa = db.trackDoa();
        artistDoa = db.artistDoa();
}

    LiveData<List<Setlist>> getAllSetlist() {
        return setlistDao.getAllSetlists();
    }


    public void insert (Setlist setlist) {
        new insertAsyncTask(setlistDao).execute(setlist);
    }

    private static class insertAsyncTask extends AsyncTask<Setlist, Void, Void> {

        private SetlistDao mAsyncTaskDao;

        insertAsyncTask(SetlistDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Setlist... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
