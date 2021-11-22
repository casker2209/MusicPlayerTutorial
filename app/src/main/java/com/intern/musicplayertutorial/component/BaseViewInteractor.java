package com.intern.musicplayertutorial.component;

import com.intern.musicplayertutorial.BaseInteractor;
import com.intern.musicplayertutorial.api.Api;
import com.intern.musicplayertutorial.api.RetrofitClient;
import com.intern.musicplayertutorial.media.MediaManager;
import com.intern.musicplayertutorial.object.Album;
import com.intern.musicplayertutorial.object.Song;
import com.intern.musicplayertutorial.object.SongEntity;
import com.intern.musicplayertutorial.response.AlbumListResponse;
import com.intern.musicplayertutorial.response.AlbumResponse;
import com.intern.musicplayertutorial.response.ArtistResponse;
import com.intern.musicplayertutorial.response.SongEntityResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class BaseViewInteractor extends BaseInteractor {
    private MediaManager mediaManager;
    public BaseViewInteractor(){
        super();
        this.mediaManager =  MediaManager.getInstance();
    }
    public void reconnectMediaManager(){
        this.mediaManager = MediaManager.getInstance();
    }
    public MediaManager getMediaManager() {
        return mediaManager;
    }
    public Song getCurrentSong(){
        return mediaManager.getCurrentSong();
    }
    public void releaseMediaManager(){
        mediaManager = null;
    }

    Observable<ResponseBody> getSongFromQuery(String query){
       return getApi().getSongbyQuery(query).observeOn(AndroidSchedulers.mainThread())
               .subscribeOn(Schedulers.io());
   }
   Observable<AlbumResponse> getAlbumfromQuery(String query){
       return getApi().getAlbumbyQuery(query).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
   }
   Observable<ArtistResponse> getArtistfromQuery(String query){
       return getApi().getArtistbyQuery(query).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
   }
    public Observable<Song> getSongFromAlbumId(String id){
        return getApi().getSongbyAlbumID(id).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<SongEntityResponse, ObservableSource<Song>>() {
                    @Override
                    public Observable<Song> apply(SongEntityResponse songEntityResponse) throws Throwable {
                        List<SongEntity> songEntityList = songEntityResponse.getSongList();
                        List<Observable<Song>> observableList = new ArrayList<>();
                        for(SongEntity songEntity: songEntityList){
                            observableList.add(getApi().getSongbyEntityId(songEntity.getId()).observeOn(AndroidSchedulers.mainThread())
                                    .subscribeOn(Schedulers.io()));
                        }
                        return Observable.mergeDelayError(observableList).observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io());
                    }
                });
    }
    Observable<SongEntityResponse> getSongfromArtistId(String id){
       return getApi().getSongbyAlbumID(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    Observable<AlbumResponse> getAlbumfromArtist(String id){
       return getApi().getAlbumbyArtistID(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
