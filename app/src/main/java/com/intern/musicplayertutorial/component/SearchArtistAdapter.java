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
import com.intern.musicplayertutorial.R;
import com.intern.musicplayertutorial.api.Api;
import com.intern.musicplayertutorial.api.RetrofitClient;
import com.intern.musicplayertutorial.object.Artist;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchArtistAdapter extends RecyclerView.Adapter<SearchArtistAdapter.ViewHolder> {

    private BaseViewImpl context;
    List<Artist> objectList;
    public SearchArtistAdapter(Context context, List<Artist> objectList) {
        this.context = (BaseViewImpl) context;
        this.objectList = objectList;
    }

    public List<Artist> getObjectList() {
        return objectList;
    }

    @NonNull
    @Override
    public SearchArtistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchArtistAdapter.ViewHolder holder, int position) {
        Artist artist = objectList.get(position);
        String name = artist.getName();
        String imageURL = artist.getPictureUrl();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.getmPresenter().getAlbumListFragment(artist.getId());
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
