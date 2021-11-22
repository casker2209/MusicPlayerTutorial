package com.intern.musicplayertutorial.component;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.intern.musicplayertutorial.ConstantsUtil;
import com.intern.musicplayertutorial.media.MediaPlayerService;
import com.intern.musicplayertutorial.module.musicsong.MusicSongActivity;
import com.intern.musicplayertutorial.R;
import com.intern.musicplayertutorial.api.Api;
import com.intern.musicplayertutorial.api.RetrofitClient;
import com.intern.musicplayertutorial.object.Song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchSongAdapter extends RecyclerView.Adapter<SearchSongAdapter.ViewHolder> {
    private BaseViewImpl context;
    List<Song> objectList;
    public SearchSongAdapter(Context context, List<Song> objectList) {
        this.context = (BaseViewImpl) context;
        this.objectList = objectList;
    }

    public List<Song> getSongList() {
        return objectList;
    }

    @NonNull
    @Override
    public SearchSongAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSongAdapter.ViewHolder holder, int position) {
        Song song = objectList.get(position);
        String name = song.getTitle();
        String content = song.getArtist().getName();
        String imageURL = song.getArtist().getPictureUrl();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.getmPresenter().startSongFromSearchView(song);

                    }
                });
        holder.tvContent.setText(content);
        holder.tvName.setText(name);
        Glide.with(context).load(imageURL).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvContent)
        TextView tvContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
