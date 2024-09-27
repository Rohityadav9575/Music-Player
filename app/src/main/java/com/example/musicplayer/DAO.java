package com.example.musicplayer;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    void insert(SongsEntity songsEntity);

    @Delete
    void delete(SongsEntity songsEntity);

    @Query("SELECT * FROM songs_table")
    LiveData<List<SongsEntity>> getAllSongs();

    @Query("SELECT * FROM songs_table WHERE name = :songName LIMIT 1")
    SongsEntity getSongByName(String songName);

}
