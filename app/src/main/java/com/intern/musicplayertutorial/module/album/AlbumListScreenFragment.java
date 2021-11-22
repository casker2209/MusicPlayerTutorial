package com.intern.musicplayertutorial.module.album;

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
import com.intern.musicplayertutorial.module.artist.ArtistListAdapter;
import com.intern.musicplayertutorial.object.Album;
import com.intern.musicplayertutorial.object.Artist;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AlbumListScreenFragment extends BaseFragment<AlbumListScreenView,AlbumListScreenPresenterInterface,Album,Object> implements AlbumListScreenView {
    @BindView(R.id.screen_recycler)
    RecyclerView screenRecycler;


    public AlbumListScreenFragment(){}

    public static AlbumListScreenFragment getInstance() {
        return new AlbumListScreenFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPresenter = new AlbumListScreenPresenter(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_base_screen, container, false);
        List = DataHolder.getAlbumList();
        unbinder = ButterKnife.bind(this,view);
        screenRecycler.setAdapter(new AlbumListAdapter(getContext(),List,this));
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
        DataHolder.setAlbumList(List);
    }


}