package com.intern.musicplayertutorial.module.songlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.intern.musicplayertutorial.BaseFragment;
import com.intern.musicplayertutorial.DataHolder;
import com.intern.musicplayertutorial.R;
import com.intern.musicplayertutorial.module.genre.GenreListAdapter;
import com.intern.musicplayertutorial.object.Album;
import com.intern.musicplayertutorial.object.Genre;
import com.intern.musicplayertutorial.object.Song;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
public class SongListScreenFragment extends BaseFragment<SongListScreenView,SongListScreenPresenter,Song,Album> implements View.OnClickListener, SongListScreenView {
    Unbinder unbinder;
    @BindView(R.id.screen_recycler)
    RecyclerView screenRecycler;
    @BindView(R.id.ivCover)
    ImageView albumCover;
    @BindView(R.id.tvName)
    TextView albumName;
    @BindView(R.id.tvDuration)
    TextView albumDuration;
    @BindView(R.id.tvNum)
    TextView albumNum;
    @BindView(R.id.tvPlay)
    TextView playButton;





    public SongListScreenFragment() {

    }

    public static SongListScreenFragment getInstance() {
        return new SongListScreenFragment();
    }

    @Override
    public SongListScreenPresenter getmPresenter() {
        return mPresenter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mPresenter = new SongListScreenPresenter(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_songlist_screen, container, false);
        List = DataHolder.getSongList();
        object = DataHolder.getAlbum();
        mPresenter.setSongList(List);
        unbinder = ButterKnife.bind(this,view);
        Glide.with(this).load(object.getPictureUrl()).centerCrop().into(albumCover);
        albumDuration.setText("Total duration: " + mPresenter.getTotalDuration());
        albumNum.setText("Total: "+ String.valueOf(List.size())+" song(s)");
        albumName.setText(object.getName());
        screenRecycler.setAdapter(new SongListAdapter(getContext(),List,this));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        DividerItemDecoration dividerItemDecoration;
        dividerItemDecoration = new DividerItemDecoration(screenRecycler.getContext(), layoutManager.getOrientation());
        screenRecycler.setLayoutManager(layoutManager);
        screenRecycler.addItemDecoration(dividerItemDecoration);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mPresenter.onDestroy();

    }

    @OnClick(R.id.tvPlay)
    public void onClick(View v) {
            mPresenter.startMusicSongActivity();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        DataHolder.setSongList(List);
        DataHolder.setAlbum(object);
    }
}