package com.intern.musicplayertutorial.module.genre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.intern.musicplayertutorial.object.Genre;
import com.intern.musicplayertutorial.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GenreListAdapter extends RecyclerView.Adapter<GenreListAdapter.ViewHolder> {
    List<Genre> genreList;
    Context context;
    GenreListScreenFragment fragment;
    public GenreListAdapter(Context context,List<Genre> genreList,GenreListScreenFragment fragment){
        this.context = context;
        this.genreList = genreList;
        this.fragment = fragment;
    }
    @NonNull
    @Override
    public GenreListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_genre_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreListAdapter.ViewHolder holder, int position) {
        Genre genre = genreList.get(position);
        holder.tvName.setText(genre.getName());
        Glide.with(context).load(genre.getPictureURL())
                .centerCrop()
                .into(holder.ivImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               fragment.getmPresenter().getArtistsByGenreID(genre.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return genreList.size();
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
    }
}
