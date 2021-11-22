package com.intern.musicplayertutorial.response;

import com.google.gson.annotations.SerializedName;
import com.intern.musicplayertutorial.object.Album;

import java.util.List;

public class AlbumResponse {
    @SerializedName("data")
    List<Album> albumList;

    public List<Album> getAlbumList() {
        return albumList;
    }
}
