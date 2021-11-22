package com.intern.musicplayertutorial.module.genre;

import android.util.Log;

import com.intern.musicplayertutorial.BasePresenter;
import com.intern.musicplayertutorial.component.BaseViewImpl;
import com.intern.musicplayertutorial.object.Artist;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GenreListScreenPresenter extends BasePresenter<GenreListScreenView,GenreListScreenInteractor,Artist,GenreListScreenPresenterInterface> implements GenreListScreenPresenterInterface  {

    public GenreListScreenPresenter(GenreListScreenView fragment) {
        super(fragment,new GenreListScreenInteractor());

    }
    public void getArtistsByGenreID(String id){
        mInteractor.getArtistFromGenreId(id)
                .doOnSubscribe(disposable -> {
                    fragment.getBaseActivity().showLoading();
                        }
                )
                .doFinally(() -> {
                    fragment.getBaseActivity().hideLoading();
                })
                .subscribe(
                        artistResponse -> {
                            List = artistResponse.getArtistList();
                        },
                        throwable -> {
                            Log.e("throwable", "throwable: " + throwable.getMessage());
                        }
                        ,()->{
                            fragment.getBaseActivity().getmPresenter().commitArtistListFragment(List);
                        }
                );
    }
    public void onDestroy(){
        fragment = null;
        mInteractor = null;
    }
}
