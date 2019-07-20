package com.example.gigpad.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface TrackDoa {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Track track);

    @Delete
    void delete(Track track);

    @Query("DELETE FROM setlist WHERE id = :setlistId")
    void deleteAllFromSetlist(String setlistId);

    @Query("SELECT * FROM track WHERE id = :setlistId")
    LiveData<List<Track>> getTracks(String setlistId);
}
