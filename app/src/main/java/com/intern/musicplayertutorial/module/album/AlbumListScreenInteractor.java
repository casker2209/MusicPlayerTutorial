package com.intern.musicplayertutorial.module.album;

import com.intern.musicplayertutorial.BaseInteractor;
import com.intern.musicplayertutorial.object.Song;
import com.intern.musicplayertutorial.object.SongEntity;
import com.intern.musicplayertutorial.response.SongEntityResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlbumListScreenInteractor extends BaseInteractor {

    public Observable<Song> getSongEntityFromAlbumId(String id){
        return getApi().getSongbyAlbumID(id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<SongEntityResponse, ObservableSource<Song>>() {
                    @Override
                    public Observable<Song> apply(SongEntityResponse songEntityResponse) throws Throwable {
                        List<SongEntity> songEntityList = songEntityResponse.getSongList();
                        List<Observable<Song>> observableList = new ArrayList<>();
                        for(SongEntity songEntity: songEntityList){
                            observableList.add(getApi().getSongbyEntityId(songEntity.getId()).observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io()));
                        }
                        return Observable.mergeDelayError(observableList).observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io());
                    }
                });
    }

    public Observable<Song> getSongFromEntity(List<SongEntity> songEntityList){
        List<Observable<Song>> observableList = new ArrayList<>();
        for(SongEntity songEntity: songEntityList){
            observableList.add(getApi().getSongbyEntityId(songEntity.getId()).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io()));
        }
        return Observable.mergeDelayError(observableList).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
    }


}
