package com.intern.musicplayertutorial.module.genre;

import com.intern.musicplayertutorial.BaseInteractor;
import com.intern.musicplayertutorial.response.ArtistResponse;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GenreListScreenInteractor extends BaseInteractor {
    public Observable<ArtistResponse> getArtistFromGenreId(String id){
        return api.getArtistbyGenreID(id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }
}
