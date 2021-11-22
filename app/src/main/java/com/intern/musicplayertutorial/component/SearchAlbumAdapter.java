package com.intern.musicplayertutorial.component;

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

import com.bumptech.glide.Glide;
import com.intern.musicplayertutorial.R;
import com.intern.musicplayertutorial.api.Api;
import com.intern.musicplayertutorial.api.RetrofitClient;
import com.intern.musicplayertutorial.object.Album;
import com.intern.musicplayertutorial.object.Song;
import com.intern.musicplayertutorial.object.SongEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchAlbumAdapter extends RecyclerView.Adapter<SearchAlbumAdapter.ViewHolder> {

    private BaseViewImpl context;
    List<Album> objectList;
    public SearchAlbumAdapter(Context context, List<Album> objectList) {
        this.context = (BaseViewImpl) context;
        this.objectList = objectList;
    }

    public List<Album> getObjectList() {
        return objectList;
    }

    @NonNull
    @Override
    public SearchAlbumAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAlbumAdapter.ViewHolder holder, int position) {

        Album album = objectList.get(position);
        String  name = album.getName();
        String imageURL = album.getPictureUrl();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.getmPresenter().getSongListFragment(album.getId(),album);
                        context.setIconified();
                        context.slideDown();

                    }
                });
        holder.tvContent.setVisibility(View.GONE);
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
