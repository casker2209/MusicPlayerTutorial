package com.intern.musicplayertutorial.module.musicsong;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.intern.musicplayertutorial.ConstantsUtil;
import com.intern.musicplayertutorial.media.MediaPlayerService;
import com.intern.musicplayertutorial.R;
import com.intern.musicplayertutorial.object.Song;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MusicSongActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, MusicSongActivityInterface {
    @BindView(R.id.music_image)
    ShapeableImageView imageView;
    @BindView(R.id.MusicBar)
    SeekBar seekBar;
    @BindView(R.id.volume_seekbar)
    SeekBar volumeBar;
    @BindView(R.id.action_next)
    ImageButton NextButton;
    @BindView(R.id.action_play_pause)
    ImageButton PlayPauseButton;
    @BindView(R.id.action_prev)
    ImageButton PrevButton;
    @BindView(R.id.action_loop)
    ImageView LoopButton;
    @BindView(R.id.action_random)
    ImageView RandomButton;
    @BindView(R.id.tvProgress)
    TextView tvProgress;
    @BindView(R.id.tvDuration)
    TextView tvDuration;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvArtist)
    TextView tvArtist;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    Song song;
    MusicSongPresenterInterface musicSongPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initPresenter();
        ButterKnife.bind(this);
        musicSongPresenter.getMusicSongInteractor().getMediaManager().setActivityRunning(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        musicSongPresenter.unregisterReceiver();
        if(musicSongPresenter.getMusicSongInteractor().getMediaManager()!=null) musicSongPresenter.getMusicSongInteractor().getMediaManager().setActivityRunning(false);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(musicSongPresenter.getMusicSongInteractor().getMediaManager()!=null)
        musicSongPresenter.getMusicSongInteractor().getMediaManager().setActivityStopped(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        musicSongPresenter.getMusicSongInteractor().getMediaManager().setActivityStopped(false);
    }
    @Override
    public void spinning(boolean isPause){
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE); //Repeat animation indefinitely
        anim.setDuration(2000);
        if(!isPause){
            imageView.startAnimation(anim);
        }
        else{
            anim.cancel();
            imageView.clearAnimation();
        }
    }
    @Override
    public void initView(Song song){
        tvArtist.setText(song.getArtist().getName());
        tvTitle.setText(song.getTitle());
        Glide.with(this).load(song.getAlbum().getPictureUrl()).into(imageView);

        seekBar.setOnSeekBarChangeListener(this);
        volumeBar.setMax(100);
        volumeBar.setOnSeekBarChangeListener(this);
        NextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicSongPresenter.onButtonNext();
            }
        });
        PrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicSongPresenter.onButtonPrev();
            }
        });
        LoopButton = findViewById(R.id.action_loop);
        LoopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicSongPresenter.onLoopButtonClicked();
            }
        });
        RandomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicSongPresenter.onRandomButtonClicked();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    public void setPlayPauseButton(boolean isPause){
        PlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicSongPresenter.onButtonPlayPause(isPause);
            }
        });
    }
    @Override
    public void initPresenter(){
        musicSongPresenter = new MusicSongPresenter(this);
    }

    @Override
    public void onPlayPause(boolean isPause){
        if(isPause) PlayPauseButton.setImageResource(R.drawable.icon_round_play);
        else PlayPauseButton.setImageResource(R.drawable.icon_round_pause);

    }
    @Override
    public void setProgressText(int progress){
        tvProgress.setText(convertMs(progress));
        seekBar.setProgress(progress);
    }
    @Override
    public String convertMs(int ms){
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        return sdf.format(ms);
    }
    @Override
    public void setDurationText(Integer integer) {
        tvDuration.setText(convertMs(integer));
        seekBar.setMax(integer);
    }
    @Override
    public void setRandomColor(Boolean bool){
        if(bool) {
            RandomButton.setColorFilter(getResources().getColor(R.color.colorPrimary));
        }
        else RandomButton.setColorFilter(getResources().getColor(R.color.colorLightBlack));
    }
    @Override
    public void setLoopColor(Boolean bool){
        if(bool) {
            LoopButton.setColorFilter(getResources().getColor(R.color.colorPrimary));
        }
        else LoopButton.setColorFilter(getResources().getColor(R.color.colorLightBlack));
    }
    @Override
    public void setEnabled(boolean isLoading){
        PlayPauseButton.setEnabled(!isLoading);
        NextButton.setEnabled(!isLoading);
        PrevButton.setEnabled(!isLoading);
        if(isLoading) seekBar.setOnSeekBarChangeListener(null);
        else seekBar.setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
    @Override
    public void onStopTrackingTouch(SeekBar sb) {
        if(sb.equals(volumeBar))                 musicSongPresenter.onVolumeChanged(sb.getProgress());
        else if(sb.equals(seekBar))              musicSongPresenter.onSeekBarChanged(sb.getProgress());
    }

    @Override
    public void setEnabledPlayPause(boolean enable){
        PlayPauseButton.setClickable(enable);
        if(!enable) PlayPauseButton.setOnClickListener(null);
    }

    @Override
    public void setVolumeBar(int progress) {
        volumeBar.setProgress(progress);
    }

    @Override
    public void register(BroadcastReceiver receiver, IntentFilter intentFilter) {
        registerReceiver(receiver,intentFilter);
    }

    @Override
    public void unregister(BroadcastReceiver receiver) {
        unregisterReceiver(receiver);
    }

}