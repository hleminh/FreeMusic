package com.example.hoang.freemusic.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.hoang.freemusic.R;
import com.example.hoang.freemusic.databases.models.TopSongModel;
import com.example.hoang.freemusic.events.OnClickMiniPlayer;
import com.example.hoang.freemusic.managers.MusicManager;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.picasso.transformations.BlurTransformation;

/**
 * Created by Hoang on 6/11/2017.
 */

public class MainPlayerFragment extends Fragment {
    @BindView(R.id.tv_song)
    TextView tvSong;
    @BindView(R.id.tv_artist)
    TextView tvArtist;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_download)
    ImageView ivDownload;
    @BindView(R.id.iv_blur)
    ImageView ivBlur;
    @BindView(R.id.iv_main)
    ImageView ivMain;
    @BindView(R.id.tv_current_time)
    TextView tvCurrentTime;
    @BindView(R.id.tv_duration)
    TextView tvDuration;
    @BindView(R.id.sb_main_1)
    SeekBar sbMain1;
    @BindView(R.id.sb_main_2)
    SeekBar sbMain2;
    @BindView(R.id.fab_main)
    FloatingActionButton fabMain;
    @BindView(R.id.iv_prev)
    ImageView ivPrev;
    @BindView(R.id.iv_next)
    ImageView ivNext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_player, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Subscribe(sticky = true)
    public void onReceiveTopSong(OnClickMiniPlayer onClickMiniPlayer) {
        TopSongModel topSongModel = onClickMiniPlayer.getTopSongModel();
        updateUIMainPlayer(topSongModel);
    }

    public void updateUIMainPlayer(TopSongModel topSongModel){
        sbMain1.setPadding(0,0,0,0);
        sbMain2.setPadding(0,0,0,0);
        tvSong.setText(topSongModel.getSongName());
        tvArtist.setText(topSongModel.getArtistName());
        Picasso.with(getContext()).load(topSongModel.getLargeImage()).transform(new BlurTransformation(getContext(),10)).into(ivBlur);
        Picasso.with(getContext()).load(topSongModel.getLargeImage()).into(ivMain);
        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MusicManager.playOrPause();
            }
        });
        MusicManager.updateSongRealtime(fabMain, sbMain1, sbMain2, tvCurrentTime, tvDuration);
    }
}
