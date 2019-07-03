package com.example.showprep.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {User.class, Setlist.class, Track.class, Artist.class}, version = 1, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract SetlistDao setlistDao();
    public abstract TrackDoa trackDoa();
    public abstract ArtistDao artistDoa();

    private static volatile LocalDatabase INSTANCE;

    public static LocalDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LocalDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocalDatabase.class, "local_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
