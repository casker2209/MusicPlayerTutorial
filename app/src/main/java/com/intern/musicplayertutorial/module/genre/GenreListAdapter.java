package com.intern.musicplayertutorial.module.genre;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.intern.musicplayertutorial.BaseFragmentAdapter;
import com.intern.musicplayertutorial.R;
import com.intern.musicplayertutorial.object.Genre;

import java.util.List;

import butterknife.BindView;

public class GenreListAdapter extends BaseFragmentAdapter<GenreListScreenFragment,Genre> {
    public GenreListAdapter(Context context, List<Genre> list, GenreListScreenFragment fragment) {
        super(context, list,fragment);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_genre_card;
    }

    @Override
    protected BaseHolder getHolder(View view) {
        return new GenreHolder(view);
    }
    public class GenreHolder extends GenreListAdapter.BaseHolder {
        @BindView(R.id.genre_image)
        ImageView ivImage;
        @BindView(R.id.genre_name)
        TextView tvName;
        public GenreHolder(@NonNull View itemView) {
            super(itemView);
        }


        @Override
        protected void initData(int position) {
            Genre genre = list.get(position);
            tvName.setText(genre.getName());
            Glide.with(context).load(genre.getPictureURL())
                    .centerCrop()
                    .into(ivImage);
           itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragment.getmPresenter().getArtistsByGenreID(genre.getId());
                }
            });

        }
    }
}
