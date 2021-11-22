package com.intern.musicplayertutorial.object;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Song implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("duration")
    private int duration;
    @SerializedName("artist")
    private Artist artist;
    @SerializedName("album")
    private Album album;
    @SerializedName("preview")
    private String uri;

    public Artist getArtist() {
        return artist;
    }

    public String getUri() {
        return uri;
    }

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getId() {
        return id;
    }

    public Album getAlbum() {
        return album;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Song(String id, String title, int duration, Artist artist, Album album, String uri) {
        this.id = id;
        this.title = title;
        this.duration = duration;
        this.artist = artist;
        this.album = album;
        this.uri = uri;
    }

    public Song(String id, String title, String url, int duration, Artist artist) {
        this.id = id;
        this.title = title;
        this.uri = url;
        this.duration = duration;
        this.artist = artist;
    }
    public Song(String id,String title,String url,int duration){
        this.id = id;
        this.title = title;
        this.uri = url;
        this.duration = duration;
    }
}
