package com.example.gigpad.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.gigpad.db.AppDatabase;
import com.example.gigpad.db.Setlist;

public class DeleteSetlistTask extends AsyncTask<Setlist, Void, String> {
    private Context mContext; //TODO fix leak

    public DeleteSetlistTask(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(Setlist... s) {
        AppDatabase db = AppDatabase.getDatabase(mContext);
        db.setlistDao().delete(s[0]);
        return "Deleted.";
    }
}
