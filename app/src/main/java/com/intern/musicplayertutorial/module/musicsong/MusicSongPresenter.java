package com.intern.musicplayertutorial.module.musicsong;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.intern.musicplayertutorial.ConstantsUtil;
import com.intern.musicplayertutorial.media.MediaPlayerService;
import com.intern.musicplayertutorial.object.Song;

import java.util.Timer;
import java.util.TimerTask;

public class MusicSongPresenter implements MusicSongPresenterInterface {
    private MusicSongActivityInterface musicSongActivity;
    private MusicSongInteractor musicSongInteractor;
    private BroadcastReceiver broadcastReceiver;

    public MusicSongPresenter(MusicSongActivityInterface musicSongActivity){
        this.musicSongActivity = musicSongActivity;
        this.musicSongInteractor = new MusicSongInteractor();
        this.broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(ConstantsUtil.ACTION.CLOSE.equals(intent.getAction())){
                    musicSongActivity.finish();
                    musicSongInteractor.releaseMediaManager();
                }

            }
        };
        musicSongInteractor.getMediaManager().getProgress().observe(musicSongActivity,integer -> {
            musicSongActivity.setProgressText(integer);
            System.out.println(integer);
        });
        musicSongInteractor.getMediaManager().getVolume().observe(musicSongActivity,aFloat -> {
            musicSongActivity.setVolumeBar((int) (aFloat*100));
        });
        musicSongInteractor.getMediaManager().getIsPausing().observe(musicSongActivity,aBoolean -> {
            musicSongActivity.onPlayPause(aBoolean);
            musicSongActivity.setPlayPauseButton(aBoolean);
            musicSongActivity.setEnabledPlayPause(true);


            if(!musicSongInteractor.getMediaManager().getIsLoading().getValue()) musicSongActivity.spinning(aBoolean);
        });
        musicSongInteractor.getMediaManager().getCurrentSongObservable().observe(musicSongActivity,song -> {
            musicSongActivity.initView(song);
            musicSongActivity.setDurationText(song.getDuration()*1000);
        });
        musicSongInteractor.getMediaManager().getIsRandom().observe(musicSongActivity,aBoolean -> {
            musicSongActivity.setRandomColor(aBoolean);
        });
        musicSongInteractor.getMediaManager().getIsLooping().observe(musicSongActivity,aBoolean -> {
            musicSongActivity.setLoopColor(aBoolean);
        });
        musicSongInteractor.getMediaManager().getIsLoading().observe(musicSongActivity,aBoolean -> {
            musicSongActivity.spinning(aBoolean);
            musicSongActivity.setEnabled(aBoolean);
        });
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantsUtil.ACTION.CLOSE);
        intentFilter.addAction(ConstantsUtil.ACTION.PLAY);
        intentFilter.addAction(ConstantsUtil.ACTION.PAUSE);
        intentFilter.addAction(ConstantsUtil.ACTION.UPDATE_STATUS);
        musicSongActivity.register(this.broadcastReceiver,intentFilter);
    }


    @Override
    public void unregisterReceiver(){
        musicSongActivity.unregister(this.broadcastReceiver);
    }

    @Override
    public MusicSongActivityInterface getMusicSongActivity() {
        return musicSongActivity;
    }

    @Override
    public MusicSongInteractor getMusicSongInteractor() {
        return musicSongInteractor;
    }

    @Override
    public Song getCurrentSong(){
        return musicSongInteractor.getCurrentSong();
    }

    @Override
    public void onButtonNext() {
        Intent intent = new Intent(getContextFromPresenter(), MediaPlayerService.class);
        intent.setAction(ConstantsUtil.ACTION.NEXT);
        sendBroadcastToMediaService(intent);
    }
    @Override
    public Context getContextFromPresenter(){
        return (Context) musicSongActivity;
    }
    @Override
    public void sendBroadcastToMediaService(Intent intent){
        startService(intent);
    }

    @Override
    public void onButtonPrev() {
        Intent intent = new Intent(getContextFromPresenter(),MediaPlayerService.class);
        intent.setAction(ConstantsUtil.ACTION.NEXT);
        sendBroadcastToMediaService(intent);

    }
    @Override
    public void onButtonPlayPause(boolean isPausing){
                    musicSongActivity.onPlayPause(isPausing);
                    musicSongActivity.setEnabledPlayPause(false);
                    Intent intent = new Intent(getContextFromPresenter(),MediaPlayerService.class);
                    if(isPausing){
                        intent.setAction(ConstantsUtil.ACTION.PLAY);
                    }
                    else intent.setAction(ConstantsUtil.ACTION.PAUSE);
                    sendBroadcastToMediaService(intent);

    }

    @Override
    public boolean isPlaying(){
        if(musicSongInteractor.getMediaManager() == null) return false;
        return musicSongInteractor.getMediaManager().getMediaPlayer().isPlaying();
    }
    @Override
    public int getProgress(){
        return musicSongInteractor.getMediaManager().getMediaPlayer().getCurrentPosition();
    }

    @Override
    public void onLoopButtonClicked() {
        musicSongInteractor.getMediaManager().switchLooping();
    }

    @Override
    public void onRandomButtonClicked() {
        musicSongInteractor.getMediaManager().switchRandom();
    }


    @Override
    public void onVolumeChanged(int progress) {
        Intent intent = new Intent((Context) musicSongActivity, MediaPlayerService.class);
        intent.putExtra("Jump to",(float)progress/100);
        intent.setAction(ConstantsUtil.ACTION.SEEK_VOLUME);
        sendBroadcastToMediaService(intent);
    }
    @Override
    public void onSeekBarChanged(int progress){
        Intent intent = new Intent((Context) musicSongActivity, MediaPlayerService.class);
        intent.putExtra("Jump to",progress);
        intent.setAction(ConstantsUtil.ACTION.SEEK);
        sendBroadcastToMediaService(intent);
    }

    @Override
    public void startService(Intent intent) {
        ((Context) musicSongActivity).startService(intent);
    }
}
