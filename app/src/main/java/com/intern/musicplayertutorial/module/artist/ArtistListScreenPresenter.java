package com.intern.musicplayertutorial.module.artist;

import android.util.Log;

import com.intern.musicplayertutorial.BasePresenter;
import com.intern.musicplayertutorial.component.BaseViewImpl;
import com.intern.musicplayertutorial.module.album.AlbumListScreenPresenter;
import com.intern.musicplayertutorial.module.album.AlbumListScreenPresenterInterface;
import com.intern.musicplayertutorial.module.genre.GenreListScreenFragment;
import com.intern.musicplayertutorial.module.genre.GenreListScreenInteractor;
import com.intern.musicplayertutorial.object.Album;
import com.intern.musicplayertutorial.object.Artist;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ArtistListScreenPresenter extends BasePresenter<ArtistListScreenView,ArtistListScreenInteractor,Album,ArtistListScreenPresenterInterface> implements ArtistListScreenPresenterInterface {

    public ArtistListScreenPresenter(ArtistListScreenView fragment) {
        super(fragment,new ArtistListScreenInteractor());

    }
    public void getAlbumsByArtistID(String id){
        mInteractor.getAlbumsfromArtistId(id)
                .doOnSubscribe(disposable -> {
                    fragment.getBaseActivity().showLoading();
                        }
                )
                .doFinally(() -> {
                    fragment.getBaseActivity().hideLoading();
                })
                .subscribe(
                        response -> {
                            List = response.getAlbumList();
                        },
                        throwable -> {
                            Log.e("throwable", "throwable: " + throwable.getMessage());
                        }
                        ,()->{
                            fragment.getBaseActivity().getmPresenter().commitAlbumListFragment(List);
                        }
                );
    }
    public void onDestroy(){
        fragment = null;
        mInteractor = null;
    }
}
