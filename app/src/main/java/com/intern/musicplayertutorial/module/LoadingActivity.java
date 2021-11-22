package com.intern.musicplayertutorial.module;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.intern.musicplayertutorial.DataHolder;
import com.intern.musicplayertutorial.api.Api;
import com.intern.musicplayertutorial.component.BaseViewImpl;
import com.intern.musicplayertutorial.object.Genre;
import com.intern.musicplayertutorial.R;
import com.intern.musicplayertutorial.api.RetrofitClient;

import java.io.Serializable;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;

public class LoadingActivity extends AppCompatActivity {
    List<Genre> genreList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        genreList = new ArrayList<>();
        Api api = RetrofitClient.getInstance().getMyApi();
        api.getAllGenres().observeOn(AndroidSchedulers.mainThread())
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
                        Toast.makeText(this,"There is a problem with internet requests",Toast.LENGTH_SHORT).show();
                    }
                    else if(throwable instanceof UnknownHostException){
                        UnknownHostException exception = (UnknownHostException) throwable;
                        Toast.makeText(this,"Unknown Host error: "+exception.getCause(),Toast.LENGTH_SHORT).show();
                    }
                    finish();
                },() ->{
                    Intent intent = new Intent(this, BaseViewImpl.class);
                    DataHolder.setGenreList(genreList);
                    startActivity(intent);
                    finish();
                });
    }
}