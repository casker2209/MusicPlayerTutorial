package com.intern.musicplayertutorial.module.artist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.intern.musicplayertutorial.BaseAdapter;
import com.intern.musicplayertutorial.BaseFragmentAdapter;
import com.intern.musicplayertutorial.object.Artist;
import com.intern.musicplayertutorial.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArtistListAdapter extends BaseFragmentAdapter<ArtistListScreenFragment,Artist> {
    public ArtistListAdapter(Context context, List<Artist> list, ArtistListScreenFragment fragment) {
        super(context, list, fragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_genre_card;
    }

    @Override
    protected ArtistListAdapter.BaseHolder getHolder(View view) {
        return new ArtistHolder(view);
    }

    public class ArtistHolder extends ArtistListAdapter.BaseHolder{
        @BindView(R.id.genre_image)
        ImageView ivImage;
        @BindView(R.id.genre_name)
        TextView tvName;
        public ArtistHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        protected void initData(int position) {
            Artist artist = list.get(position);
            tvName.setText(artist.getName());
            Glide.with(context).load(artist.getPictureUrl())
                    .centerCrop()
                    .into(ivImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.getPresenter().getAlbumsByArtistID(artist.getId());

                }
            });
        }
    }



    /*List<Artist> artistList;
    Context context;
    ArtistListScreenFragment fragment;
    public ArtistListAdapter(Context context, List<Artist> artistList,ArtistListScreenFragment fragment){
        this.context = context;
        this.artistList = artistList;
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public ArtistListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_genre_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistListAdapter.ViewHolder holder, int position) {
        Artist artist = artistList.get(position);
        holder.tvName.setText(artist.getName());
        Glide.with(context).load(artist.getPictureUrl())
                .centerCrop()
                .into(holder.ivImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.getPresenter().getAlbumsByArtistID(artist.getId());

            }
        });

    }

    @Override
    public int getItemCount() {
        return artistList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.genre_image)
        ImageView ivImage;
        @BindView(R.id.genre_name)
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }*/
}
