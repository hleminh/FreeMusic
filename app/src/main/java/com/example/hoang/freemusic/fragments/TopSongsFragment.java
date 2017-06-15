package com.example.hoang.freemusic.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hoang.freemusic.R;
import com.example.hoang.freemusic.adapters.TopSongAdapter;
import com.example.hoang.freemusic.databases.models.MusicTypeModel;
import com.example.hoang.freemusic.databases.models.TopSongModel;
import com.example.hoang.freemusic.events.OnClickMusicTypeModel;
import com.example.hoang.freemusic.events.OnClickTopSong;
import com.example.hoang.freemusic.managers.MusicManager;
import com.example.hoang.freemusic.managers.ScreenManager;
import com.example.hoang.freemusic.networks.RetrofitFactory;
import com.example.hoang.freemusic.networks.music_type.MusicType;
import com.example.hoang.freemusic.networks.top_song.EntryObject;
import com.example.hoang.freemusic.networks.top_song.Feed;
import com.example.hoang.freemusic.networks.top_song.GetTopSongsService;
import com.example.hoang.freemusic.networks.top_song.MainObject;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hoang on 5/28/2017.
 */

public class TopSongsFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    @BindView(R.id.iv_top_song)
    ImageView ivMusicType;
    @BindView(R.id.tv_music_type)
    TextView tvMusicType;
    @BindView(R.id.rv_top_songs)
    RecyclerView rvTopSongs;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private List<TopSongModel> topSongModelList;
    private TopSongAdapter topSongAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_songs, container, false);
        setupUI(view);
        return view;
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenManager.backFragment(getActivity().getSupportFragmentManager());
            }
        });
        topSongAdapter.setOnClickListener(this);
    }

    @Subscribe(sticky = true)
    public void onReceiveMusicType(OnClickMusicTypeModel onClickMusicTypeModel) {
        MusicTypeModel musicTypeModel = onClickMusicTypeModel.getMusicTypeModel();
        tvMusicType.setText(musicTypeModel.getKey());
        Picasso.with(getContext()).load(musicTypeModel.getIdImage()).into(ivMusicType);
        loadData(musicTypeModel);
    }

    private void loadData(MusicTypeModel musicTypeModel) {
        topSongModelList = new ArrayList<>();
        topSongAdapter = new TopSongAdapter(topSongModelList, getContext());
        GetTopSongsService service = RetrofitFactory.getInstance().createService(GetTopSongsService.class);
        service.getTopSongs(musicTypeModel.getId()).enqueue(new Callback<MainObject>() {
            @Override
            public void onResponse(Call<MainObject> call, Response<MainObject> response) {
                MainObject mainObject = response.body();
                Feed feed = mainObject.getFeed();
                for (EntryObject entryObject : feed.getEntry()) {
                    TopSongModel topSongModel = new TopSongModel();
                    topSongModel.setSongName(entryObject.getNameObject().getLabel());
                    topSongModel.setArtistName(entryObject.getArtistObject().getLabel());
                    topSongModel.setSmallImage(entryObject.getImageObjectList().get(0).getLabel());
                    topSongModel.setLargeImage(entryObject.getImageObjectList().get(2).getLabel());
                    System.out.println(topSongModel.toString());
                    topSongModelList.add(topSongModel);
                }
                topSongAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MainObject> call, Throwable t) {

            }
        });
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        rvTopSongs.addItemDecoration(dividerItemDecoration);
        rvTopSongs.setAdapter(topSongAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvTopSongs.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View v) {
        int position = rvTopSongs.getChildLayoutPosition(v);
        System.out.println(topSongModelList.get(position).toString());
        MusicManager.loadSearchSong(getContext(),topSongModelList.get(position));
        EventBus.getDefault().post(new OnClickTopSong(topSongModelList.get(position)));
    }
}
