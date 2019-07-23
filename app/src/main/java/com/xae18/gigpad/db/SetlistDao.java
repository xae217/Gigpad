package com.xae18.gigpad.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;


@Dao
public interface SetlistDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Setlist setlist);

    @Delete
    void delete(Setlist setlist);

    @Query("DELETE FROM setlist")
    void deleteAll();

    @Query("SELECT * from setlist")
    LiveData<List<Setlist>> getAllSetlists();

    @Query("SELECT * from setlist WHERE id = :setlistId")
    LiveData<Setlist> getSetlist(String setlistId);

    @Transaction
    @Query("SELECT * FROM setlist")
    LiveData<List<SavedSetlist>> getSavedSetlists();
}
