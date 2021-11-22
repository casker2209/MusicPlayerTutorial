package com.intern.musicplayertutorial.response;

import com.google.gson.annotations.SerializedName;
import com.intern.musicplayertutorial.object.Artist;

import java.util.List;

public class ArtistListResponse {
    @SerializedName("data")
    List<Artist> artistList;

    public List<Artist> getArtistList() {
        return artistList;
    }
}
