package com.xae18.gigpad.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface ArtistDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Artist artist);

    @Delete
    void delete(Artist artist);

    @Query("DELETE FROM artist WHERE id = :artistId")
    void deleteArtist(String artistId);

    @Query("SELECT * FROM artist WHERE id = :artistId")
    LiveData<Artist> getArtist(String artistId);

}
