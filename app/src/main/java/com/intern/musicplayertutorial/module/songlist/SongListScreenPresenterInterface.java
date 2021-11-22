package com.intern.musicplayertutorial.module.songlist;

import com.intern.musicplayertutorial.BasePresenterInterface;
import com.intern.musicplayertutorial.module.genre.GenreListScreenPresenter;
import com.intern.musicplayertutorial.object.Song;

import java.util.List;

public interface SongListScreenPresenterInterface extends BasePresenterInterface {
    void setSongList(List<Song> songList);
    void startSongAtPosition(int position);
}
