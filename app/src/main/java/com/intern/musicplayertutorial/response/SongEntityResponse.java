package com.intern.musicplayertutorial.response;

import com.google.gson.annotations.SerializedName;
import com.intern.musicplayertutorial.object.SongEntity;

import java.util.List;

public class SongEntityResponse {
    @SerializedName("data")
    List<SongEntity> songList;

    public List<SongEntity> getSongList() {
        return songList;
    }
}
