package com.xae18.gigpad.db;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SetlistViewModel extends AndroidViewModel {
    private final LiveData<List<SavedSetlist>> mSetlists;
    private AppDatabase appDatabase;

    public SetlistViewModel (Application application) {
        super(application);
        appDatabase = AppDatabase.getDatabase(application);
        mSetlists = appDatabase.setlistDao().getSavedSetlists();
    }

    public LiveData<List<SavedSetlist>> getmSetlists() {
        return mSetlists;
    }

}
