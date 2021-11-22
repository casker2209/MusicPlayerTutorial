package com.intern.musicplayertutorial.module.artist;

import com.intern.musicplayertutorial.BasePresenterInterface;

public interface ArtistListScreenPresenterInterface extends BasePresenterInterface {
    void getAlbumsByArtistID(String id);

    void onDestroy();
}
