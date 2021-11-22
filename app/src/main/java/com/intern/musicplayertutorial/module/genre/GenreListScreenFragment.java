package com.intern.musicplayertutorial.module.genre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.intern.musicplayertutorial.BaseFragment;
import com.intern.musicplayertutorial.DataHolder;
import com.intern.musicplayertutorial.R;
import com.intern.musicplayertutorial.object.Genre;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class GenreListScreenFragment extends BaseFragment<GenreListScreenView,GenreListScreenPresenterInterface,Genre,Object> implements GenreListScreenView{
    @BindView(R.id.screen_recycler)
    RecyclerView screenRecycler;
    Unbinder unbinder;

    public GenreListScreenFragment() {

    }

    public static GenreListScreenFragment newInstance(){
        GenreListScreenFragment fragment = new GenreListScreenFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new GenreListScreenPresenter(this);
    }

    public GenreListScreenPresenterInterface getmPresenter() {
        return mPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_base_screen, container, false);
        List = DataHolder.getGenreList();
        unbinder = ButterKnife.bind(this,view);
        screenRecycler.setAdapter(new GenreScreenAdapter(getContext(),List,this));
        screenRecycler.setLayoutManager(new GridLayoutManager(getContext(),2));


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mPresenter.onDestroy();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DataHolder.setGenreList(List);
    }
}