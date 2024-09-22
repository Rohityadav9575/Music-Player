package com.example.musicplayer;

public class MusicFilesModel {
    private String path;
    private String title;
    private String duration;
    private String artists;
    private String  album;


    public MusicFilesModel(String path, String title, String duration, String artists, String album) {
        this.path = path;
        this.title = title;
        this.duration = duration;
        this.artists = artists;
        this.album = album;
    }

    public MusicFilesModel() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }
}
