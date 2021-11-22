package com.intern.musicplayertutorial.media;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LifecycleService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.intern.musicplayertutorial.ConstantsUtil;
import com.intern.musicplayertutorial.R;
import com.intern.musicplayertutorial.component.BaseViewImpl;
import com.intern.musicplayertutorial.module.musicsong.MusicSongActivity;
import com.intern.musicplayertutorial.object.Song;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.BiFunction;
import kotlin.jvm.functions.Function2;

public class MediaPlayerService extends LifecycleService {
    List<Song> songList;;
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            handleIntent(intent);
        }
    };
    private PendingIntent createPendingIntent(String actionName) {
        return PendingIntent.getBroadcast(this,0,new Intent(actionName),PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void handleIntent(Intent intent){
         if(intent.getAction().equals(ConstantsUtil.ACTION.CLOSE)){

             mediaManager.releaseMediaManager();


             Intent screenIntent = new Intent(getApplicationContext(), BaseViewImpl.class);
             screenIntent.setAction(ConstantsUtil.ACTION.CLOSE);
             sendBroadcast(screenIntent);

             Intent activityIntent = new Intent(getApplicationContext(), MusicSongActivity.class);
             activityIntent.setAction(ConstantsUtil.ACTION.CLOSE);
             sendBroadcast(activityIntent);


            stopForeground(true);
            stopSelf();




        }
        else if(intent.getAction().equals(ConstantsUtil.ACTION.NEXT)){
            playNextSong();
        }
        else if(intent.getAction().equals(ConstantsUtil.ACTION.PREV)){
            playPrevSong();
        }
        else if(intent.getAction().equals(ConstantsUtil.ACTION.SEEK)){
            int jumpto = intent.getIntExtra("Jump to",mediaManager.getMediaPlayer().getCurrentPosition());
            Log.d("Jump position", String.valueOf(jumpto));
            mediaManager.seek(jumpto);
        }
         else if(intent.getAction().equals(ConstantsUtil.ACTION.SEEK_VOLUME)){
             float jumpto = intent.getFloatExtra("Jump to",mediaManager.getVolume().getValue());
             Log.d("Jump position", String.valueOf(jumpto));
             mediaManager.setVolume(jumpto);
         }
        else if(intent.getAction().equals(ConstantsUtil.ACTION.PLAY)){
             Intent screenIntent = new Intent(getApplicationContext(), BaseViewImpl.class);
             screenIntent.setAction(ConstantsUtil.ACTION.PLAY);
             sendBroadcast(screenIntent);

            int index = intent.getIntExtra("index",mediaManager.getCurrentIndex().getValue());
            List<Song> getSongList = (ArrayList<Song>) intent.getSerializableExtra("Song");
            if(getSongList != null) {
                songList = getSongList;
                mediaManager.setSongList(songList);
            }
            mediaManager.playSong(index);
            showNotification();




             Log.d("artist picture url","_:" + mediaManager.getCurrentSong().getArtist().getPictureUrl());
        }
        else if(intent.getAction().equals(ConstantsUtil.ACTION.PAUSE)){
            mediaManager.pauseSong();
            showNotification();
        }
         else if(intent.getAction().equals(ConstantsUtil.ACTION.VOLUME_CHANGE)){
             mediaManager.setVolume(intent.getFloatExtra("volume change",mediaManager.getVolume().getValue()));
             showNotification();
         }
        else if(intent.getAction().equals(ConstantsUtil.ACTION.START_ACTIVITY)){
             if(!mediaManager.isActivityRunning()){
             Song song = mediaManager.getCurrentSong();
             Intent activityIntent = new Intent(this,MusicSongActivity.class);
            activityIntent.putExtra("Song",song);
            startActivity(activityIntent);
            Log.i("State","From Running");
             }
             else if(mediaManager.isActivityStopped()){
                 Song song = mediaManager.getCurrentSong();
                 Intent activityIntent = new Intent(this,MusicSongActivity.class);
                 activityIntent.putExtra("Song",song);
                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                 startActivity(activityIntent);
                 Log.i("State","From Pausing");

             }
         }
    }
    @SuppressLint("RestrictedApi")
    public void showNotification() {
        NotificationCompat.Action pauseAction = new NotificationCompat.Action(R.drawable.icon_pause,"pause",createPendingIntent(ConstantsUtil.ACTION.PAUSE));
        NotificationCompat.Action playAction = new NotificationCompat.Action(R.drawable.icon_start,"start",createPendingIntent(ConstantsUtil.ACTION.PLAY));
        NotificationCompat.Action cancelAction = new NotificationCompat.Action(
                R.drawable.icon_cancel,"cancel",
                createPendingIntent(ConstantsUtil.ACTION.CLOSE));
        NotificationCompat.Action nextAction = new NotificationCompat.Action(
                R.drawable.icon_next,"next",createPendingIntent(ConstantsUtil.ACTION.NEXT)
        );
        NotificationCompat.Action prevAction = new NotificationCompat.Action(
                R.drawable.icon_prev,"prev",createPendingIntent(ConstantsUtil.ACTION.PREV)
        );
        PendingIntent activityIntent =  PendingIntent.getBroadcast(this,0,new Intent(ConstantsUtil.ACTION.START_ACTIVITY),PendingIntent.FLAG_UPDATE_CURRENT);

        //
        final Bitmap[] bitmap = {BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.icon_music_note_notif)};


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"Media Player Channel")
                //don't know why color of notification doesn't change
                //maybe because android version < oreo
                .setSmallIcon(R.drawable.icon_music_note)
                .addAction(nextAction)
                .addAction(prevAction)
                .addAction(cancelAction)
                .setContentIntent(activityIntent)
                .setContentText(mediaManager.getCurrentSong().getArtist().getName())
                .setContentTitle(mediaManager.getCurrentSong().getTitle())
                .setOngoing(false)
                .setAutoCancel(true);

        Glide.with(getApplicationContext())
                .asBitmap()
                .load(mediaManager.getCurrentSong().getArtist().getPictureUrl())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        builder.setLargeIcon(resource);
                        return false;
                    }
                })
                .submit();
        mediaManager.getIsLoading().observe(this,aBoolean -> {
            if(aBoolean){
                    builder.mActions.remove(pauseAction);
                    builder.mActions.remove(playAction);
                    builder.setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(1,0)
                    .setShowCancelButton(true)
                    .setCancelButtonIntent(createPendingIntent(ConstantsUtil.ACTION.CLOSE)).setMediaSession(new MediaSessionCompat(this,"empty Media Session for style").getSessionToken()));
                    }
                else{
                mediaManager.getIsPausing().observe(this,bBoolean -> {
                    if(builder.mActions.size()>=4) builder.mActions.remove(3);
                            if(!bBoolean) builder.addAction(pauseAction);
                            else builder.addAction(playAction);
                            builder.setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                                    .setShowActionsInCompactView(1,3,0)
                                    .setShowCancelButton(true)
                                    .setCancelButtonIntent(createPendingIntent(ConstantsUtil.ACTION.CLOSE)).setMediaSession(new MediaSessionCompat(this,"empty Media Session for style").getSessionToken()));
                        }
                );
            }
        });



        mediaManager.getCurrentSongObservable().observe(this,song -> {
            builder.setContentText(song.getArtist().getName())
                    .setContentTitle(song.getTitle());
            Glide.with(getApplicationContext())
                    .asBitmap()
                    .load(song.getArtist().getPictureUrl())
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            builder.setLargeIcon(resource);
                            Notification notification = builder.build();
                            startForeground(1000,notification);
                            return false;
                        }
                    })
                    .submit();
        });



    }

    private MediaManager mediaManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        super.onBind(intent);
        return null;
    }
    public int randomInt(){
        int random = (int)(Math.random() * ((mediaManager.getSize() -1) + 1));
        return  random;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        mediaManager = MediaManager.getInstance();


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantsUtil.ACTION.PLAY);
        intentFilter.addAction(ConstantsUtil.ACTION.PAUSE);
        intentFilter.addAction(ConstantsUtil.ACTION.CLOSE);
        intentFilter.addAction(ConstantsUtil.ACTION.PREV);
        intentFilter.addAction(ConstantsUtil.ACTION.NEXT);
        intentFilter.addAction(ConstantsUtil.ACTION.SEEK);
        intentFilter.addAction(ConstantsUtil.ACTION.SEEK_VOLUME);
        intentFilter.addAction(ConstantsUtil.ACTION.START_ACTIVITY);
        registerReceiver(receiver,intentFilter);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(songList == null) {
            songList = (List<Song>) intent.getSerializableExtra("Song");
            mediaManager.setSongList(songList);
            mediaManager.setService(this);
        }
            handleIntent(intent);
            showNotification();


        return super.onStartCommand(intent, flags, startId);
    }

    public void playNextSong(){
        mediaManager.playNextSong();
        showNotification();
    }
    public void playPrevSong(){
        mediaManager.playPreviousSong();
        showNotification();

    }

}
