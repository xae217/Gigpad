package com.example.gigpad.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * from user WHERE id = :userId")
    User getUser(String userId);
}
