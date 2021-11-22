package com.intern.musicplayertutorial.response;

import com.google.gson.annotations.SerializedName;
import com.intern.musicplayertutorial.object.Song;

import java.util.List;

public class SongListResponse{
    @SerializedName("data")
    List<Song> songList;
    public List<Song> getSongList() {

        return songList;
    }
}
