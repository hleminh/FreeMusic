package com.example.hoang.freemusic.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.freemusic.R;
import com.example.hoang.freemusic.databases.models.TopSongModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Hoang on 5/30/2017.
 */

public class TopSongAdapter extends RecyclerView.Adapter<TopSongAdapter.TopSongHolder> {

    private List<TopSongModel> topSongModelList;
    private Context context;
    private View.OnClickListener onClickListener;

    public TopSongAdapter(List<TopSongModel> topSongModelList, Context context) {
        this.topSongModelList = topSongModelList;
        this.context = context;
    }

    @Override
    public TopSongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_list_top_songs, parent, false);
        view.setOnClickListener(onClickListener);
        return new TopSongHolder(view);
    }

    @Override
    public void onBindViewHolder(TopSongHolder holder, int position) {
        holder.setData(topSongModelList.get(position));
    }

    @Override
    public int getItemCount() {
        return topSongModelList.size();
    }

    public class TopSongHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_top_song_name)
        TextView topSongName;
        @BindView(R.id.tv_top_song_artist)
        TextView topSongArtist;
        @BindView(R.id.iv_top_song_item)
        ImageView topSongImage;

        View view;

        public TopSongHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
        }

        public void setData(TopSongModel topSongModel) {
            topSongName.setText(topSongModel.getSongName());
            topSongArtist.setText(topSongModel.getArtistName());
            Picasso.with(context).load(topSongModel.getSmallImage()).transform(new CropCircleTransformation()).into(topSongImage);
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
