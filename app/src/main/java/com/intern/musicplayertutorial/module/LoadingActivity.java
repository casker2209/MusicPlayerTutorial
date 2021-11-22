package com.intern.musicplayertutorial.module;

import android.app.Activity;
import android.content.Context;
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

public class LoadingActivity extends AppCompatActivity implements LoadingView{
    LoadingPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        presenter = new LoadingPresenterImpl(this);
        presenter.getGenre();
        getActivity();
    }


    @Override
    public Context getActivity() {
        return this;
    }

    @Override
    public void startNewActivity() {
        Intent intent = new Intent(this, BaseViewImpl.class);
        startActivity(intent);
        finish();
    }
}