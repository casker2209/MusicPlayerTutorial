package com.intern.musicplayertutorial.module.songlist;

import com.intern.musicplayertutorial.BaseInteractor;
import com.intern.musicplayertutorial.object.Song;

import java.text.SimpleDateFormat;
import java.util.List;

public class SongListScreenInteractor extends BaseInteractor {
    public String getTotalDuration(List<Song> songList) {
        int duration = 0;
        for(Song song : songList){
            duration+=song.getDuration();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        return sdf.format(duration*1000);
    }
}
