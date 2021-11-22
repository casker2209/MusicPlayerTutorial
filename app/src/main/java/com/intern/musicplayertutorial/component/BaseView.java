package com.intern.musicplayertutorial.component;

import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.intern.musicplayertutorial.BaseFragment;
import com.intern.musicplayertutorial.object.Album;
import com.intern.musicplayertutorial.object.Artist;
import com.intern.musicplayertutorial.object.Genre;
import com.intern.musicplayertutorial.object.Song;

import java.util.List;

public interface BaseView extends LifecycleOwner {
    AlertDialog.Builder getDialogProgressBar();

    void showLoading();

    void hideLoading();

    BaseViewPresenterInterface getmPresenter();

    void onBackPressed();

    void initSearchView();

    void setIconified();

    void slideUp();

    void slideDown();

    void showhideMediaBar(boolean show);

    void initMediaBar(Song song);

    void setPlayPauseIcon(boolean isPlay);

    Intent registerReceiver(BroadcastReceiver receiver, IntentFilter intentFilter);

    void unregisterReceiver(BroadcastReceiver receiver);

    void startActivity(Intent intent);

    void commitFragment(int id, BaseFragment fragment, String tag);

    List<Genre> getExtra(String text);

    ComponentName startService(Intent intent);

    List<Song> getSongList();
    List<Album> getAlbumList();
    List<Artist> getArtistList();

    void updateSongLayout(Boolean bool);
    void updateArtistLayout(Boolean bool);
    void updateAlbumLayout(Boolean bool);

    void updateSongAdapter();
    void updateArtistAdapter();
    void updateAlbumAdapter();
}
