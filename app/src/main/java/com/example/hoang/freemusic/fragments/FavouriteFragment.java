package com.example.hoang.freemusic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hoang.freemusic.R;
import com.example.hoang.freemusic.adapters.MusicTypeAdapter;
import com.example.hoang.freemusic.databases.RealmHandle;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {
    @BindView(R.id.rv_fav)
    RecyclerView rvFav;
    private MusicTypeAdapter musicTypeAdapter;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this, view);
        musicTypeAdapter = new MusicTypeAdapter(RealmHandle.getInstance().getFavoriteMusicTypeList(), getContext());
        rvFav.setAdapter(musicTypeAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        rvFav.setLayoutManager(gridLayoutManager);
        return view;
    }

}
