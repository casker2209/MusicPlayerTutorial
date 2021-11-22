package com.intern.musicplayertutorial.component;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.intern.musicplayertutorial.BaseFragment;
import com.intern.musicplayertutorial.ConstantsUtil;
import com.intern.musicplayertutorial.R;
import com.intern.musicplayertutorial.object.Album;
import com.intern.musicplayertutorial.object.Artist;
import com.intern.musicplayertutorial.object.Genre;
import com.intern.musicplayertutorial.object.Song;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class BaseViewImpl extends AppCompatActivity implements BaseView {
    BaseViewPresenterInterface mPresenter;
    AlertDialog.Builder builder;
    AlertDialog progressDialog;
    @BindView(R.id.toolbar)
    SearchView searchView;
    @BindView(R.id.artistLayout)
    LinearLayout artistLayout;
    @BindView(R.id.albumLayout)
    LinearLayout albumLayout;
    @BindView(R.id.songLayout)
    LinearLayout songLayout;
    @BindView(R.id.mediaBar)
    RelativeLayout mediaBar;
    @BindView(R.id.resultView)
    ScrollView resultView;
    @BindView(R.id.artistRecycler)
    RecyclerView artistRecycler;
    @BindView(R.id.albumRecycler)
    RecyclerView albumRecycler;
    @BindView(R.id.songRecycler)
    RecyclerView songRecycler;
    @BindView(R.id.media_image)
    ImageView ivMediaImage;
    @BindView(R.id.media_playpause)
    ImageView ivPlayPause;
    @BindView(R.id.media_prev)
    ImageView ivPrev;
    @BindView(R.id.media_next)
    ImageView ivNext;
    @BindView(R.id.media_title)
    TextView tvMediaTitle;
    @BindView(R.id.media_artist)
    TextView tvMediaArtist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_view_impl);
        mPresenter = new BaseViewPresenter(this);
        mPresenter.initialize();
        ButterKnife.bind(this);
        progressDialog = getDialogProgressBar().create();
        initSearchView();
    }

    @Override
    public AlertDialog.Builder getDialogProgressBar() {
        if (builder == null) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Loading...");
            final ProgressBar progressBar = new ProgressBar(this);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            progressBar.setPadding(0,20,0,20);
            builder.setView(progressBar);
        }
        return builder;
    }

    @Override
    public void showLoading(){
        progressDialog.show();
    }
    @Override
    public void hideLoading(){
        progressDialog.cancel();
    }

    @Override
    public BaseViewPresenterInterface getmPresenter() {
        return mPresenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        builder=null;
        mPresenter.unregisterReceiver();
        mPresenter.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 1) super.onBackPressed();
        else this.finishAffinity();
    }

    @Override
    public void initSearchView(){

        mediaBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onMediaBarClick();
            }
        });


        songRecycler.setAdapter(new SearchSongAdapter(this, new ArrayList<>()));
        artistRecycler.setAdapter(new SearchArtistAdapter(this, new ArrayList<>()));
        albumRecycler.setAdapter(new SearchAlbumAdapter(this, new ArrayList<>()));

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(songRecycler.getContext(),
                layoutManager1.getOrientation());
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(artistRecycler.getContext(),
                layoutManager2.getOrientation());
        LinearLayoutManager layoutManager3 = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration3 = new DividerItemDecoration(albumRecycler.getContext(),
                layoutManager3.getOrientation());
        songRecycler.setLayoutManager(layoutManager1);
        artistRecycler.setLayoutManager(layoutManager2);
        albumRecycler.setLayoutManager(layoutManager3);
        songRecycler.addItemDecoration(dividerItemDecoration1);
        artistRecycler.addItemDecoration(dividerItemDecoration2);
        albumRecycler.addItemDecoration(dividerItemDecoration3);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()>2){
                    mPresenter.getAlbumList(newText);
                    mPresenter.getSongList(newText);
                    mPresenter.getArtistList(newText);
                }
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                slideDown();
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideUp();
            }
        });
    }
    @Override
    public void setIconified(){
        searchView.setQuery("",false);
        searchView.setIconified(true);
    }
    @Override
    public void slideUp(){
        resultView.getLayoutParams().height = -1; //-1 = match_parent
    }
    @Override
    public void slideDown(){
        resultView.getLayoutParams().height = 0;
    }
    @Override
    public void showhideMediaBar(boolean show){
        if(show) mediaBar.setVisibility(View.VISIBLE);
        else mediaBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void initMediaBar(Song song) {
       Glide.with(this).load(song.getAlbum().getPictureUrl()).into(ivMediaImage);
        tvMediaTitle.setText(song.getTitle());
        tvMediaArtist.setText(song.getArtist().getName());


        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onNextClick();
            }
        });
        ivPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onPlayPauseClick();
            }
        });
        ivPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.onPrevClick();
            }
        });
    }

    @Override
    public void setPlayPauseIcon(boolean isPlay){
        if(isPlay) ivPlayPause.setBackgroundResource(R.drawable.icon_pause);
        else ivPlayPause.setBackgroundResource(R.drawable.icon_start);
    }

    @Override
    public void commitFragment(int id, BaseFragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(tag);
        transaction.commit();
    }

    @Override
    public List<Genre> getExtra(String text) {
        return (ArrayList<Genre>) getIntent().getSerializableExtra(text);
    }

    @Override
    public List<Song> getSongList() {
        return ((SearchSongAdapter) songRecycler.getAdapter()).getSongList();
    }

    @Override
    public List<Album> getAlbumList() {
        return ((SearchAlbumAdapter) albumRecycler.getAdapter()).getObjectList();
    }

    @Override
    public List<Artist> getArtistList() {
        return ((SearchArtistAdapter) artistRecycler.getAdapter()).getObjectList();
    }

    @Override
    public void updateSongLayout(Boolean bool) {
        songLayout.setVisibility(bool ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void updateArtistLayout(Boolean bool) {
        artistLayout.setVisibility(bool ? View.VISIBLE : View.INVISIBLE);

    }

    @Override
    public void updateAlbumLayout(Boolean bool) {
        albumLayout.setVisibility(bool ? View.VISIBLE : View.INVISIBLE);

    }

    @Override
    public void updateSongAdapter() {
        songRecycler.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void updateArtistAdapter() {
        artistRecycler.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void updateAlbumAdapter() {
        artistRecycler.getAdapter().notifyDataSetChanged();

    }
}