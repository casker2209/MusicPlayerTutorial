package com.intern.musicplayertutorial.module.musicsong;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.widget.SeekBar;

import androidx.lifecycle.LifecycleOwner;

import com.intern.musicplayertutorial.object.Song;

public interface MusicSongActivityInterface extends LifecycleOwner {
    void spinning(boolean isPause);

    void initView(Song song);

    void setPlayPauseButton(boolean isPause);

    void initPresenter();

    void onPlayPause(boolean isPause);

    void setProgressText(int progress);

    String convertMs(int ms);

    void setDurationText(Integer integer);

    void setRandomColor(Boolean bool);

    void setLoopColor(Boolean bool);

    void setEnabled(boolean isLoading);

    void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser);

    void onStartTrackingTouch(SeekBar seekBar);

    void onStopTrackingTouch(SeekBar sb);

    void setEnabledPlayPause(boolean enable);

    void finish();

    void setVolumeBar(int progress);

    void register(BroadcastReceiver receiver,IntentFilter intentFilter);

    void unregister(BroadcastReceiver receiver);

}
