package com.example.hoang.freemusic.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.freemusic.R;
import com.example.hoang.freemusic.databases.models.MusicTypeModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hoang on 5/28/2017.
 */

public class MusicTypeAdapter extends RecyclerView.Adapter<MusicTypeAdapter.MusicTypeViewHolder> {

    private List<MusicTypeModel> musicTypeModelList;
    private Context context;
    private View.OnClickListener onItemClickListener;

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MusicTypeAdapter(List<MusicTypeModel> musicTypeModelList, Context context) {
        this.musicTypeModelList = musicTypeModelList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return musicTypeModelList.size();
    }

    @Override
    public MusicTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_list_music_types, parent, false);
        itemView.setOnClickListener(onItemClickListener);
        return new MusicTypeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MusicTypeViewHolder holder, int position) {
        holder.setData(musicTypeModelList.get(position));
    }


    public class MusicTypeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_music_type)
        ImageView ivMusicType;
        @BindView(R.id.tv_music_type)
        TextView tvMusicType;

        View view;

        public MusicTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            view = itemView;
        }

        public void setData(MusicTypeModel musicTypeModel) {
            if (musicTypeModel != null) {
                Picasso.with(context).load(musicTypeModel.getIdImage()).into(ivMusicType);
                tvMusicType.setText(musicTypeModel.getKey());
                view.setTag(musicTypeModel);
            }
        }
    }
}
