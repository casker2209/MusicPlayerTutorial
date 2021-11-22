package com.intern.musicplayertutorial.media;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.intern.musicplayertutorial.object.Song;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MediaManager implements Serializable {
    private static MediaManager instance;
    private MediaPlayer mediaPlayer;
    private MutableLiveData<Boolean> isPausing;
    private MutableLiveData<Integer> currentIndex;
    private MutableLiveData<Integer> progress;
    private MutableLiveData<Integer> duration;
    private MutableLiveData<Song> currentSong;
    private Observable<Integer> observable;
    private List<Song> songList;
    private MediaPlayerService mediaPlayerService;
    private boolean activityRunning;
    private boolean activityStopped;
    private MutableLiveData<Boolean> isPlaying;
    private MutableLiveData<Boolean> isRandom;
    private MutableLiveData<Boolean> isLooping;
    private MutableLiveData<Float> volume;
    private MutableLiveData<Boolean> isLoading;
    public static MediaManager getInstance(){
        if(instance == null){
            instance = new MediaManager();
        }
        return instance;
    }
    private MediaManager(){
        mediaPlayer = new MediaPlayer();
        volume = new MutableLiveData<>();
        volume.setValue(1.0f);
        currentIndex = new MutableLiveData<>();
        currentIndex.setValue(0);
        currentSong = new MutableLiveData<>();
        currentSong.setValue(null);
        progress = new MutableLiveData<>();
        duration = new MutableLiveData<>();
        isPausing = new MutableLiveData<>();
        isPausing.setValue(false);
        isLooping = new MutableLiveData<>();
        isLooping.setValue(false);
        isRandom = new MutableLiveData<>();
        isRandom.setValue(false);
        isLoading = new MutableLiveData<>();
        isLoading.setValue(false);
        isPlaying = new MutableLiveData<>();
        isPlaying.setValue(true);
        duration.setValue(999);
        progress.setValue(0);
        songList = new ArrayList<>();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                duration.setValue(getCurrentSong().getDuration()*1000);
            }
        });
        startUpdateTime();

    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoadingValue(Boolean isLoading) {
        this.isLoading.setValue(isLoading);
    }

    public MutableLiveData<Float> getVolume() {
        return volume;
    }

    public void setVolume(Float volume) {
        Log.d("Volume", "volume log:" + String.valueOf(volume));
        this.volume.setValue(volume);
        mediaPlayer.setVolume(volume,volume);
    }

    public boolean isActivityRunning() {
        return activityRunning;
    }

    public void setActivityRunning(boolean activityRunning) {
        this.activityRunning = activityRunning;
    }


    private void startUpdateTime() {
        observable = new Observable<Integer>() {
            @Override
            protected void subscribeActual(@NonNull Observer<? super Integer> observer) {
                while(instance!=null){
                    if(instance == null || isPausing.getValue()) continue;
                    observer.onNext(mediaPlayer.getCurrentPosition());
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if(integer!=null){
                    progress.setValue(integer);
                    }
                }, throwable -> {
                    Log.d("Media Manager", "startUpdateTime: " + throwable.getMessage());
                }, () -> {

                });
    }

    public void setService(MediaPlayerService mediaPlayerService){
        this.mediaPlayerService = mediaPlayerService;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }


    public Song getCurrentSong(){
        if(currentIndex.getValue()>0) currentSong.setValue(songList.get(getCurrentIndex().getValue()));

        return currentSong.getValue();
    }
    public MutableLiveData<Song> getCurrentSongObservable(){
        return currentSong;
    }

    public void setSongList(List<Song> songList) {
        if(!this.songList.equals(songList)) {
            Log.d("Error","Should have reset media player");
            isPausing.setValue(false);
            currentIndex.setValue(-1);
        }
        this.songList = songList;
    }

    public MutableLiveData<Boolean> getIsPausing() {
        return isPausing;
    }

    public MutableLiveData<Boolean> isPlaying() {
        Log.d("Playing or not", String.valueOf(mediaPlayer.isPlaying()));
        return isPlaying;
    }

    public void setPlaying(Boolean isPlaying) {
        this.isPlaying.setValue(isPlaying);
    }

    public MutableLiveData<Integer> getCurrentIndex() {
        return currentIndex;
    }

    public void playSong(int index){
        if(isLoading.getValue() == false) {
            if (isPausing.getValue()) {
                Log.d("pause", "Start from pause");
                mediaPlayer.start();
                isPlaying.setValue(true);
                isPausing.setValue(false);
                mediaPlayerService.showNotification();
            } else {
                if (isPlaying().getValue()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    Log.d("pause", "Start from pause");
                }
                try {
                    if (currentIndex.getValue() != index) {
                        Log.d("set index", "set index");
                        isLoading.setValue(true);
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        currentIndex.setValue(index);
                        currentSong.setValue(songList.get(index));
                        Song tempCurrentSong = getCurrentSong();
                        mediaPlayer.setDataSource(tempCurrentSong.getUri());

                        mediaPlayer.setAudioAttributes(
                                new AudioAttributes.Builder()
                                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                        .setUsage(AudioAttributes.USAGE_MEDIA)
                                        .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                                        .build()
                        );
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                isLoading.setValue(false);
                                mp.start();
                                mediaPlayerService.showNotification();
                                isPausing.setValue(false);
                                isPlaying.setValue(true);
                            }
                        });
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                if (getIsLooping().getValue()) {
                                    seek(0);
                                } else if (getIsRandom().getValue()) {
                                    playSong(randomInt());
                                } else playNextSong();
                            }
                        });
                        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                            @Override
                            public boolean onError(MediaPlayer mp, int what, int extra) {
                                return true;
                            }
                        });
                        mediaPlayer.prepareAsync();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void pauseSong(){
        if(isLoading.getValue() == false) {
            mediaPlayer.pause();
            isPausing.setValue(true);
            isPlaying.setValue(false);
            mediaPlayerService.showNotification();
        }
    }
    public void playNextSong(){
        isPausing.setValue(false);
        if(songList.size()> 1)  playSong(currentIndex.getValue() == songList.size() -1  ? 0 : currentIndex.getValue()+1);
        else {
            seek(0);
            mediaPlayer.start();
            isPlaying.setValue(true);
            mediaPlayerService.showNotification();

        }
    }

    public int randomInt(){
        int random = (int)(Math.random() * ((getSize() -1) + 1));
        return  random;
    }
    public void playPreviousSong(){
        isPausing.setValue(false);
        if(songList.size()> 1) playSong(currentIndex.getValue() == 0 ? songList.size() -1 : currentIndex.getValue() - 1);
        else {
            seek(0);
            mediaPlayer.start();
            isPlaying.setValue(true);
            mediaPlayerService.showNotification();
        }
    }
    public void release(){
        mediaPlayer.stop();
        mediaPlayer.release();
    }
    public Song getNextSong(){
        return songList.get(currentIndex.getValue() == songList.size() -1  ? 0 : currentIndex.getValue()+1);
    }
    public Song getPrevSong(){
        return songList.get(currentIndex.getValue() == 0 ? songList.size() -1 : currentIndex.getValue() - 1);
    }
    public void seek(int jumpto) {
        mediaPlayer.seekTo(jumpto);
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex.setValue(currentIndex);
    }
    public void releaseMediaManager(){
        mediaPlayer.stop();
        mediaPlayer.release();
        songList = new ArrayList<>();
        instance = null;
        observable.unsubscribeOn(Schedulers.io());
    }

    public MutableLiveData<Integer> getDuration() {
        return duration;
    }

    public MutableLiveData<Integer> getProgress() {
        return progress;
    }

    public boolean isActivityStopped() {
        return activityStopped;
    }

    public void setActivityStopped(boolean activityStopped) {
        this.activityStopped = activityStopped;
    }

    public MutableLiveData<Boolean> getIsLooping() {
        return isLooping;
    }

    public MutableLiveData<Boolean> getIsRandom() {
        return isRandom;
    }

    public void setIsLooping(boolean isLooping) {
        this.isLooping.setValue(isLooping);
        mediaPlayer.setLooping(isLooping);
    }

    public void switchLooping(){
        setIsLooping(!this.isLooping.getValue());
    }
    public void switchRandom(){
        setIsRandom(!this.isRandom.getValue());
    }
    public void setIsRandom(boolean isRandom) {
        this.isRandom.setValue(isRandom);
    }

    public int getSize(){
        return songList.size();
    }
}
