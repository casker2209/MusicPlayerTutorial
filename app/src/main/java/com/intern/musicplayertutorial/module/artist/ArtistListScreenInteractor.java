package com.intern.musicplayertutorial.module.artist;

import com.intern.musicplayertutorial.BaseInteractor;
import com.intern.musicplayertutorial.response.AlbumResponse;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ArtistListScreenInteractor extends BaseInteractor{
    public Observable<AlbumResponse> getAlbumsfromArtistId(String id){
        return getApi().getAlbumbyArtistID(id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
