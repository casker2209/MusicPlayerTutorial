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


}
