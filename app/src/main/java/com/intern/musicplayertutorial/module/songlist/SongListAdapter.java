package com.intern.musicplayertutorial.module.songlist;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intern.musicplayertutorial.component.BaseViewImpl;
import com.intern.musicplayertutorial.media.MediaPlayerService;
import com.intern.musicplayertutorial.module.musicsong.MusicSongActivity;
import com.intern.musicplayertutorial.R;
import com.intern.musicplayertutorial.object.Song;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.ViewHolder> {
    Context context;
    List<Song> songList;
    SongListScreenView fragment;
    public SongListAdapter(Context context, List<Song> songList,SongListScreenView fragment){
        this.context =context;
        this.songList = songList;
        this.fragment =fragment;
    }


    @NonNull
    @Override
    public SongListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music_card,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongListAdapter.ViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.tvDuration.setText(new SimpleDateFormat("mm:ss").format(song.getDuration()*1000));
        holder.tvTitle.setText(song.getTitle());
        holder.tvIndex.setText(String.valueOf(position+1));
        holder.tvArtist.setText(song.getArtist().getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               fragment.getmPresenter().startSongAtPosition(position);
               fragment.getBaseActivity().getmPresenter().reconnectMediaBar();
            }
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.song_title)
        TextView tvTitle;
        @BindView(R.id.song_artist)
        TextView tvArtist;
        @BindView(R.id.song_duration)
        TextView tvDuration;
        @BindView(R.id.song_index)
        TextView tvIndex;
        @BindView(R.id.song_icon)
        ImageView ivIcon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
