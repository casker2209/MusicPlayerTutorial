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

import com.intern.musicplayertutorial.BaseFragmentAdapter;
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

public class SongListAdapter extends BaseFragmentAdapter<SongListScreenFragment,Song> {
    public SongListAdapter(Context context, List<Song> list, SongListScreenFragment fragment) {
        super(context, list, fragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_music_card;
    }

    @Override
    protected BaseHolder getHolder(View view) {
        return new SongHolder(view);
    }
    public class SongHolder extends SongListAdapter.BaseHolder{
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
        public SongHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void initData(int position) {
            Song song = list.get(position);
            tvDuration.setText(new SimpleDateFormat("mm:ss").format(song.getDuration()*1000));
            tvTitle.setText(song.getTitle());
            tvIndex.setText(String.valueOf(position+1));
            tvArtist.setText(song.getArtist().getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.getmPresenter().startSongAtPosition(position);
                    fragment.getBaseActivity().getmPresenter().reconnectMediaBar();
                }
            });
        }
    }


}
