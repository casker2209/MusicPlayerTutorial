package com.intern.musicplayertutorial.module.musicsong;

import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.intern.musicplayertutorial.object.Song;

public interface MusicSongPresenterInterface {
    void unregisterReceiver();

    MusicSongActivityInterface getMusicSongActivity();

    MusicSongInteractor getMusicSongInteractor();

    Song getCurrentSong();

    void onButtonNext();

    Context getContextFromPresenter();

    void sendBroadcastToMediaService(Intent intent);

    void onButtonPrev();

    void onButtonPlayPause(boolean isPausing);

    boolean isPlaying();

    int getProgress();

    void onLoopButtonClicked();

    void onRandomButtonClicked();

    void onVolumeChanged(int progress);

    void onSeekBarChanged(int progress);

    void startService(Intent intent);

    String convertMs(int ms);
}
