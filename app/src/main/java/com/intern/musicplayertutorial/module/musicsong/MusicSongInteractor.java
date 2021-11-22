package com.intern.musicplayertutorial.module.musicsong;

import android.content.Intent;

import com.intern.musicplayertutorial.media.MediaManager;
import com.intern.musicplayertutorial.media.MediaPlayerService;
import com.intern.musicplayertutorial.object.Song;

public class MusicSongInteractor {
    private MediaManager mediaManager;
    public MusicSongInteractor(){
        this.mediaManager = MediaManager.getInstance();
    }


    public MediaManager getMediaManager() {
        return mediaManager;
    }
    public Song getCurrentSong(){
        return mediaManager.getCurrentSong();
    }
    public void releaseMediaManager(){
        mediaManager = null;
    }
}
