package com.intern.musicplayertutorial.module.album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.intern.musicplayertutorial.BaseFragmentAdapter;
import com.intern.musicplayertutorial.R;
import com.intern.musicplayertutorial.object.Album;
import com.intern.musicplayertutorial.api.Api;
import com.intern.musicplayertutorial.api.RetrofitClient;
import com.intern.musicplayertutorial.object.SongEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumListAdapter extends BaseFragmentAdapter<AlbumListScreenFragment,Album> {
    public AlbumListAdapter(Context context, List<Album> list, AlbumListScreenFragment fragment) {
        super(context, list, fragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_genre_card;
    }

    @Override
    protected BaseHolder getHolder(View view) {
        return new AlbumAdapter(view);
    }

    public class AlbumAdapter extends AlbumListAdapter.BaseHolder {
        @BindView(R.id.genre_image)
        ImageView ivImage;
        @BindView(R.id.genre_name)
        TextView tvName;

        public AlbumAdapter(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void initData(int position) {
            Album album = list.get(position);
            tvName.setText(album.getName());
            Glide.with(context).load(album.getPictureUrl())
                    .centerCrop()
                    .into(ivImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.getPresenter().getSongByAlbumId(album.getId(), album);
                }
            });
        }

    /*public AlbumListAdapter(Context context, List<Album> albumList,
                            AlbumListScreenFragment fragment){
        this.context = context;
        this.albumList = albumList;
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public AlbumListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_genre_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumListAdapter.ViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.tvName.setText(album.getName());
        Glide.with(context).load(album.getPictureUrl())
                .centerCrop()
                .into(holder.ivImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.getPresenter().getSongByAlbumId(album.getId(),album);
            }
        });
    }



    @Override
    public int getItemCount() {
        return albumList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.genre_image)
        ImageView ivImage;
        @BindView(R.id.genre_name)
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }*/
    }
}
