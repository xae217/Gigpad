package com.example.showprep.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface SetlistDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Setlist setlist);

    @Query("DELETE FROM setlist")
    void deleteAll();

    @Query("SELECT * from setlist WHERE userID = :userId")
    LiveData<List<Setlist>> getAllSetlists(String userId);

    @Query("SELECT * from setlist WHERE id = :setlistId")
    LiveData<Setlist> getSetlist(String setlistId);
}
