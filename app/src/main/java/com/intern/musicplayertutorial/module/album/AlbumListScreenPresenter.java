package com.intern.musicplayertutorial.module.album;

import android.util.Log;

import com.intern.musicplayertutorial.BasePresenter;
import com.intern.musicplayertutorial.component.BaseViewImpl;
import com.intern.musicplayertutorial.object.Album;
import com.intern.musicplayertutorial.object.Song;
import com.intern.musicplayertutorial.object.SongEntity;
import com.intern.musicplayertutorial.response.SongEntityResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AlbumListScreenPresenter extends BasePresenter<AlbumListScreenView, AlbumListScreenInteractor, Song,AlbumListScreenPresenterInterface> implements AlbumListScreenPresenterInterface {

    public AlbumListScreenPresenter(AlbumListScreenView fragment) {
        super(fragment,new AlbumListScreenInteractor());

    }
    public void getSongByAlbumId(String id, Album album){
        List = new ArrayList<>();
       mInteractor.getSongEntityFromAlbumId(id)
               .doOnSubscribe(disposable -> {
                           fragment.getBaseActivity().showLoading();
                       }
               )
               .doFinally(() -> {
                   fragment.getBaseActivity().hideLoading();
               })
               .subscribe(
               response ->{ response.setDuration(30);
               List.add(response);
               },
               throwable -> {},
               () -> {
                   fragment.getBaseActivity().getmPresenter().commitSongListFragment(List,album);

               }
       );
    }
    public void onDestroy(){
        fragment = null;
        mInteractor = null;
    }
}
