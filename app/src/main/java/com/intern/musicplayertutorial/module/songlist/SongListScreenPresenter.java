package com.intern.musicplayertutorial.module.songlist;

import android.content.Intent;
import android.util.Log;

import com.intern.musicplayertutorial.BasePresenter;
import com.intern.musicplayertutorial.ConstantsUtil;
import com.intern.musicplayertutorial.component.BaseViewImpl;
import com.intern.musicplayertutorial.media.MediaPlayerService;
import com.intern.musicplayertutorial.module.genre.GenreListScreenFragment;
import com.intern.musicplayertutorial.module.genre.GenreListScreenInteractor;
import com.intern.musicplayertutorial.module.musicsong.MusicSongActivity;
import com.intern.musicplayertutorial.object.Artist;
import com.intern.musicplayertutorial.object.Song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SongListScreenPresenter extends BasePresenter<SongListScreenFragment,SongListScreenInteractor,Song,SongListScreenPresenterInterface> implements SongListScreenPresenterInterface {
    List<Song> songList;
    public SongListScreenPresenter(SongListScreenFragment fragment) {
        super(fragment,new SongListScreenInteractor());
    }
    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public void startMusicSongActivity(){

        Intent intent = new Intent(fragment.getActivity(), MediaPlayerService.class);
        intent.putExtra("Song",(Serializable) songList);
        intent.putExtra("index",0);
        intent.setAction(ConstantsUtil.ACTION.PLAY);
        fragment.getBaseActivity().startService(intent);

        Intent activityIntent = new Intent(fragment.getActivity(), MusicSongActivity.class);
        activityIntent.putExtra("Song", songList.get(0));
        fragment.getBaseActivity().startActivity(activityIntent);
        fragment.getBaseActivity().getmPresenter().reconnectMediaBar();

    }
    public void startSongAtPosition(int position){
        Intent intent = new Intent(fragment.getActivity(), MediaPlayerService.class);
        List<Song> passingSongList = new ArrayList<>();
        passingSongList.add(songList.get(position));
        intent.putExtra("Song",(Serializable) passingSongList);
        intent.putExtra("index",0);
        intent.setAction(ConstantsUtil.ACTION.PLAY);
        fragment.getBaseActivity().startService(intent);
        Intent activityIntent = new Intent(fragment.getActivity(), MusicSongActivity.class);
        activityIntent.putExtra("Song", passingSongList.get(0));
        fragment.getBaseActivity().startActivity(activityIntent);
    }



    public String getTotalDuration() {
        return mInteractor.getTotalDuration(songList);
    }
    public void onDestroy(){
        fragment = null;
        mInteractor = null;
    }
}
