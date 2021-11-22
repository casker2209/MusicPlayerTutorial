package com.intern.musicplayertutorial.object;

import com.google.gson.annotations.SerializedName;

public class SongEntity {
    @SerializedName("id")
    String id;
    @SerializedName("title")
    String title;
    @SerializedName("preview")
    String url;
    @SerializedName("duration")
    int duration;
    @SerializedName("artist")
    Artist artist;

    public String getId() {
        return id;
    }

    public int getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }


    public String getUrl() {
        return url;
    }

    public Artist getArtist() {
        return artist;
    }

}
