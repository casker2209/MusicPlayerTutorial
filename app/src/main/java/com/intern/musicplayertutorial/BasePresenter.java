package com.intern.musicplayertutorial;

import androidx.fragment.app.Fragment;

import java.util.List;

public abstract class BasePresenter<F extends BaseInterfaceView,I extends BaseInteractor,O extends Object,IN extends BasePresenterInterface>  {
    protected F fragment;
    protected I mInteractor;
    protected List<O> List;

    public BasePresenter(F f,I i){
        this.fragment = f;
        this.mInteractor = i;
    }


    public abstract void onDestroy();


}
