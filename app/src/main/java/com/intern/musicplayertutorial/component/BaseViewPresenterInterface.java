package com.intern.musicplayertutorial.component;

import android.content.Intent;

import com.intern.musicplayertutorial.object.Album;
import com.intern.musicplayertutorial.object.Artist;
import com.intern.musicplayertutorial.object.Song;

import java.util.List;

public interface BaseViewPresenterInterface {
    void onMediaBarClick();

    void reconnectMediaBar();

    void initialize();

    void commitAlbumListFragment(List<Album> albumList);

    void commitArtistListFragment(List<Artist> artistList);

    void commitSongListFragment(List<Song> songList, Album album);

    void getSongList(String newText);

    void getArtistList(String newText);

    void getAlbumList(String newText);

    void getSongListFragment(String id, Album album);

    void getAlbumListFragment(String id);

    void startSongFromSearchView(Song song);

    void unregisterReceiver();

    void sendBroadcastToMediaService(Intent intent);

    void onNextClick();

    void onPrevClick();

    void onPlayPauseClick();

    void onDestroy();

    void onSearchViewClick();
}
