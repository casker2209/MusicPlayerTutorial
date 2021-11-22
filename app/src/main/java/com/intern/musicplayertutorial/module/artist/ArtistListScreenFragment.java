package com.intern.musicplayertutorial.module.artist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.intern.musicplayertutorial.BaseFragment;
import com.intern.musicplayertutorial.DataHolder;
import com.intern.musicplayertutorial.R;
import com.intern.musicplayertutorial.object.Artist;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ArtistListScreenFragment extends BaseFragment<ArtistListScreenView,ArtistListScreenPresenterInterface,Artist,Object> implements ArtistListScreenView{

    @BindView(R.id.screen_recycler)
    RecyclerView screenRecycler;


    public ArtistListScreenFragment() {

    }

    public static ArtistListScreenFragment getInstance(){
        ArtistListScreenFragment fragment = new ArtistListScreenFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPresenter = new ArtistListScreenPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_base_screen, container, false);
        unbinder = ButterKnife.bind(this,view);
        List = DataHolder.getArtistList();
        screenRecycler.setAdapter(new ArtistListAdapter(getContext(),List,this));
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
        DataHolder.setArtistList(List);
    }
}