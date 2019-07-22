package com.example.gigpad.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.gigpad.db.AppDatabase;
import com.example.gigpad.db.Setlist;

import java.lang.ref.WeakReference;

public class DeleteSetlistTask extends AsyncTask<Setlist, Void, String> {
    private WeakReference<Context> mContext;

    public DeleteSetlistTask(WeakReference<Context> context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(Setlist... s) {
        AppDatabase db = AppDatabase.getDatabase(mContext.get());
        db.setlistDao().delete(s[0]);
        return "Deleted.";
    }
}
