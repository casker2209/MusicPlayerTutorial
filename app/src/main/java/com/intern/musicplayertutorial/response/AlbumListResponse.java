package com.intern.musicplayertutorial.response;

import com.google.gson.annotations.SerializedName;
import com.intern.musicplayertutorial.object.Album;

import java.util.List;

public class AlbumListResponse {
    @SerializedName("data")
    List<Album> albumList;

    public List<Album> getAlbumList() {
        return albumList;
    }
}
