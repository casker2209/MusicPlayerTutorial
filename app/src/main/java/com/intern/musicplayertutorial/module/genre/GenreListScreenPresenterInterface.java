package com.intern.musicplayertutorial.module.genre;

import com.intern.musicplayertutorial.BasePresenterInterface;

public interface GenreListScreenPresenterInterface extends BasePresenterInterface {
    void onDestroy();
    void getArtistsByGenreID(String id);
}
