package com.intern.musicplayertutorial.module;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.intern.musicplayertutorial.DataHolder;
import com.intern.musicplayertutorial.component.BaseViewImpl;
import com.intern.musicplayertutorial.object.Genre;

import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;

public class LoadingPresenterImpl implements LoadingPresenter {
    LoadingInteractor loadingInteractor;
    List<Genre> genreList;
    LoadingView loadingView;
    public LoadingPresenterImpl(LoadingView loadingView) {
        this.loadingInteractor = new LoadingInteractor();
        this.loadingView = loadingView;
    }
    public void getGenre(){
        DataHolder.init(loadingView.getActivity().getApplicationContext());
        loadingInteractor.getApi().getAllGenres().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(response ->{
                    Log.d("Tag",response.toString());
                    genreList = response.getGenreList();
                },throwable -> {
                    Log.e("Throwable","Throwable: "+throwable.getMessage());
                    if(throwable instanceof HttpException){
                        HttpException exception = (HttpException) throwable;
                        Log.e("Error code","Code: "+ exception.code());
                        //if code = ...
                        Toast.makeText(loadingView.getActivity(),"There is a problem with internet requests",Toast.LENGTH_SHORT).show();
                    }
                    else if(throwable instanceof UnknownHostException){
                        UnknownHostException exception = (UnknownHostException) throwable;
                        Toast.makeText(loadingView.getActivity(),"Unknown Host error: "+exception.getCause(),Toast.LENGTH_SHORT).show();
                    }
                    loadingView.getActivity();
                },() ->{
                    DataHolder.setGenreList(genreList);
                    loadingView.startNewActivity();
                });
    }
}

