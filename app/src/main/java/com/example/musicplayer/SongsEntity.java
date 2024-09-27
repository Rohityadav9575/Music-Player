package com.example.musicplayer;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "songs_table")

public class SongsEntity {
    @PrimaryKey(autoGenerate = true)
    private  int  id;

    String name;
    String path;
    String duration;


    public SongsEntity(String name, String path, String duration) {

        this.name = name;
        this.path = path;
        this.duration = duration;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    private boolean isFavorite;

    // existing constructor...

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }


}
