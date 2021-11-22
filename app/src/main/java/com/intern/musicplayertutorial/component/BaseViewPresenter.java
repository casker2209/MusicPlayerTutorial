package com.intern.musicplayertutorial.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentTransaction;

import com.intern.musicplayertutorial.ConstantsUtil;
import com.intern.musicplayertutorial.DataHolder;
import com.intern.musicplayertutorial.R;
import com.intern.musicplayertutorial.media.MediaManager;
import com.intern.musicplayertutorial.media.MediaPlayerService;
import com.intern.musicplayertutorial.module.album.AlbumListScreenFragment;
import com.intern.musicplayertutorial.module.album.AlbumListScreenView;
import com.intern.musicplayertutorial.module.artist.ArtistListScreenFragment;
import com.intern.musicplayertutorial.module.genre.GenreListScreenFragment;
import com.intern.musicplayertutorial.module.musicsong.MusicSongActivity;
import com.intern.musicplayertutorial.module.songlist.SongListScreenFragment;
import com.intern.musicplayertutorial.object.Album;
import com.intern.musicplayertutorial.object.Artist;
import com.intern.musicplayertutorial.object.Genre;
import com.intern.musicplayertutorial.object.Song;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BaseViewPresenter implements BaseViewPresenterInterface {
    BaseView mView;
    BaseViewInteractor mInteractor;
    BroadcastReceiver broadcastReceiver;
    boolean isPlay = false;
    boolean isShowSearchView = false;
    public BaseViewPresenter(BaseViewImpl baseView){
        this.mView = baseView;
        this.mInteractor = new BaseViewInteractor();
        this.broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(ConstantsUtil.ACTION.CLOSE.equals(intent.getAction())){
                    mView.showhideMediaBar(false);

                }
                else if(ConstantsUtil.ACTION.PLAY.equals(intent.getAction())){


                }
                else if(ConstantsUtil.ACTION.UPDATE_STATUS.equals(intent.getAction())){

                }
            }
        };
        mInteractor.getMediaManager().getIsPausing()
                .observe(mView,aBoolean -> {
            if(mInteractor.getMediaManager().getSize() >0) {
                isPlay = !aBoolean;
                mView.setPlayPauseIcon(isPlay);
            }
        });
        mInteractor.getMediaManager().getCurrentSongObservable().observe(mView,song -> {
            if(mInteractor.getMediaManager().getSize() >0) {
                if(song!=null) mView.initMediaBar(song);
                mView.showhideMediaBar(true);
            }
            else{
                mView.showhideMediaBar(false);
            }
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantsUtil.ACTION.CLOSE);
        intentFilter.addAction(ConstantsUtil.ACTION.PLAY);
        intentFilter.addAction(ConstantsUtil.ACTION.PAUSE);
        intentFilter.addAction(ConstantsUtil.ACTION.UPDATE_STATUS);
        mView.registerReceiver(this.broadcastReceiver,intentFilter);
    }

    @Override
    public void onMediaBarClick(){
        MediaManager mediaManager = mInteractor.getMediaManager();
        if(!mediaManager.isActivityRunning()){
            Song song = mediaManager.getCurrentSong();
            Intent activityIntent = new Intent((Context) mView,MusicSongActivity.class);
            activityIntent.putExtra("Song",song);
            mView.startActivity(activityIntent);
            Log.i("State","From Running");
        }
        else if(mediaManager.isActivityStopped()){
            Song song = mediaManager.getCurrentSong();
            Intent activityIntent = new Intent((Context) mView,MusicSongActivity.class);
            activityIntent.putExtra("Song",song);
            activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            mView.startActivity(activityIntent);
            Log.i("State","From Pausing");

        }

    }
    @Override
    public void reconnectMediaBar(){
        mInteractor.reconnectMediaManager();
        mInteractor.getMediaManager().getIsPausing().observe(mView,aBoolean -> {
            if(mInteractor.getMediaManager().getSize() >0) {
                isPlay = !aBoolean;
                mView.setPlayPauseIcon(isPlay);
            }
        });
        mInteractor.getMediaManager().getCurrentSongObservable().observe(mView,song -> {
            if(mInteractor.getMediaManager().getSize() >0) {
                if(song!=null) mView.initMediaBar(song);
                mView.showhideMediaBar(true);
            }
            else{
                mView.showhideMediaBar(false);
            }
        });
    }
    @Override
    public void initialize(){
        GenreListScreenFragment genreFragment = GenreListScreenFragment.newInstance();
        mView.commitFragment(R.id.fragment_container,genreFragment,"genre list");
    }
    @Override
    public void commitAlbumListFragment(List<Album> albumList){
        DataHolder.setAlbumList(albumList);
        AlbumListScreenFragment fragment = AlbumListScreenFragment.getInstance();
        mView.commitFragment(R.id.fragment_container,fragment,"album list");
    }
    @Override
    public void commitArtistListFragment(List<Artist> artistList){
        DataHolder.setArtistList(artistList);
        ArtistListScreenFragment fragment = ArtistListScreenFragment.getInstance();
        mView.commitFragment(R.id.fragment_container,fragment,"artist list");

    }

    @Override
    public void commitSongListFragment(List<Song> songList, Album album) {
        DataHolder.setSongList(songList);
        DataHolder.setAlbum(album);
        SongListScreenFragment fragment = SongListScreenFragment.getInstance();
        mView.commitFragment(R.id.fragment_container,fragment,"song list");

    }

    @Override
    public void getSongList(String newText) {
        List<Song> tempList = new ArrayList<>();
        mInteractor.getSongFromQuery(newText).subscribe(response -> {
            JSONObject biggestObject = new JSONObject(response.string());
            JSONArray arrayObject = biggestObject.getJSONArray("data");
            for(int i = 0;i<arrayObject.length();i++){
                JSONObject bigObject = arrayObject.getJSONObject(i);
                JSONObject artistObject = bigObject.getJSONObject("artist");
                JSONObject albumObject = bigObject.getJSONObject("album");
                Song song = new Song(bigObject.getString("id"),
                        bigObject.getString("title"),
                        30,
                        new Artist(artistObject.getString("id"),artistObject.getString("name"),artistObject.getString("picture")),
                        new Album(albumObject.getString("id"),albumObject.getString("title"),albumObject.getString("cover")),
                        bigObject.getString("preview"));
                tempList.add(song);
            }

        },throwable -> {
            Log.e("Throwable song",throwable.getMessage());

        },()->{
            mView.updateSongLayout(tempList.size()>0);
            List<Song> SongList = mView.getSongList();
            SongList.clear();
            for(Song song: tempList){
                SongList.add(song);
            }
            mView.updateSongAdapter();
        });
    }
    @Override
    public void getArtistList(String newText){
        final List<Artist>[] tempList = new List[]{new ArrayList<>()};
        mInteractor.getArtistfromQuery(newText).subscribe(response -> {
            tempList[0] = response.getArtistList();
        },throwable -> {
            Log.e("Throwable artist",throwable.getMessage());
        },()->{
            Log.d("List size Artist", String.valueOf(tempList[0].size()));
            mView.updateArtistLayout(tempList[0].size()>0);

            List<Artist> ArtistList = mView.getArtistList();
            ArtistList.clear();
            for(Artist artist: tempList[0]){
                ArtistList.add(artist);
            }
            mView.updateArtistAdapter();
        });
    }
    @Override
    public void getAlbumList(String newText){
        final ArrayList<Album>[] tempList = new ArrayList[]{new ArrayList<>()};
        mInteractor.getAlbumfromQuery(newText)
                .subscribe(response -> {
                            tempList[0] = (ArrayList<Album>) response.getAlbumList();
                        },throwable -> {
                            Log.e("Throwable album",throwable.getMessage());
                        },()->{

                    mView.updateAlbumLayout(tempList[0].size()>0);
                    List<Album> AlbumList = mView.getAlbumList();

                    AlbumList.clear();
                            for(Album album: tempList[0]){
                                AlbumList.add(album);
                            }
                            mView.updateArtistAdapter();
                            Log.d("List size Album", String.valueOf(tempList[0].size()));

                        }
                );
    }
    @Override
    public void getSongListFragment(String id, Album album){
        List<Song> songList = new ArrayList<>();
        mInteractor.getSongFromAlbumId(id).doOnSubscribe(disposable -> {mView.showLoading();})
                .doFinally(() ->{mView.hideLoading();}).subscribe(
                  response -> {
                      response.setDuration(30);
                      songList.add(response);
                  },
                throwable -> {},
                () -> {
                      commitSongListFragment(songList,album);
                }
        );
    }
    @Override
    public void getAlbumListFragment(String id){
        final List<Album>[] albumList = new List[]{new ArrayList<>()};
        mInteractor.getAlbumfromArtist(id).doOnSubscribe(disposable -> {mView.showLoading();})
                .doFinally(() ->{mView.hideLoading();})
                .subscribe(albumResponse -> {
                            albumList[0] = albumResponse.getAlbumList();},
                        throwable -> {},
                        () -> {
                            commitAlbumListFragment(albumList[0]);
                        }
                );
    }
    @Override
    public void startSongFromSearchView(Song song){
        Intent intent = new Intent((Context) mView, MediaPlayerService.class);
        List<Song> passingSongList = new ArrayList<>();
        passingSongList.add(song);
        intent.putExtra("Song",(Serializable) passingSongList);
        intent.putExtra("index",0);
        intent.setAction(ConstantsUtil.ACTION.PLAY);
        mView.startService(intent);
        Intent activityIntent = new Intent((Context) mView, MusicSongActivity.class);
        activityIntent.putExtra("Song", passingSongList.get(0));
        mView.startActivity(activityIntent);

    }

    @Override
    public void unregisterReceiver() {
        mView.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void sendBroadcastToMediaService(Intent intent){
        mView.startService(intent);
    }

    @Override
    public void onNextClick() {
        Intent intent = new Intent((Context) mView, MediaPlayerService.class);
        intent.setAction(ConstantsUtil.ACTION.NEXT);
        sendBroadcastToMediaService(intent);
    }
    @Override
    public void onPrevClick() {
        Intent intent = new Intent((Context) mView,MediaPlayerService.class);
        intent.setAction(ConstantsUtil.ACTION.NEXT);
        sendBroadcastToMediaService(intent);
    }
    @Override
    public void onPlayPauseClick() {
        Intent intent = new Intent((Context) mView,MediaPlayerService.class);
        if(isPlay){
            intent.setAction(ConstantsUtil.ACTION.PAUSE);
        }
        else intent.setAction(ConstantsUtil.ACTION.PLAY);
        sendBroadcastToMediaService(intent);
    }
    @Override
    public void onDestroy(){
        mView = null;
        mInteractor = null;
    }

    @Override
    public void onSearchViewClick() {
        if(!isShowSearchView) {
            mView.slideUp();
            isShowSearchView = true;
        }
        else{
            mView.slideDown();
            isShowSearchView = false;
        }
    }
}
