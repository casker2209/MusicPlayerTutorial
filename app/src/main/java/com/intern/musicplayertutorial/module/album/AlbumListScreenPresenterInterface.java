package com.intern.musicplayertutorial.module.album;

import com.intern.musicplayertutorial.BasePresenterInterface;
import com.intern.musicplayertutorial.object.Album;

public interface AlbumListScreenPresenterInterface extends BasePresenterInterface {
    void getSongByAlbumId(String id, Album album);

    void onDestroy();
}
