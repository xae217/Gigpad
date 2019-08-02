package com.xae18.gigpad.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.xae18.gigpad.db.AppDatabase;
import com.xae18.gigpad.db.Setlist;

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
