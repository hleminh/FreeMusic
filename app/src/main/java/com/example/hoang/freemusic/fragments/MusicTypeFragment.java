package com.example.hoang.freemusic.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hoang.freemusic.R;
import com.example.hoang.freemusic.adapters.MusicTypeAdapter;
import com.example.hoang.freemusic.databases.RealmHandle;
import com.example.hoang.freemusic.databases.models.MusicTypeModel;
import com.example.hoang.freemusic.events.OnClickMusicTypeModel;
import com.example.hoang.freemusic.managers.ScreenManager;
import com.example.hoang.freemusic.networks.music_type.MainObject;
import com.example.hoang.freemusic.networks.music_type.MediaType;
import com.example.hoang.freemusic.networks.music_type.MusicType;
import com.example.hoang.freemusic.networks.music_type.MusicTypesService;
import com.example.hoang.freemusic.networks.RetrofitFactory;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MusicTypeFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.rv_music_types)
    RecyclerView rvMusicType;

    private List<MusicType> musicTypeList;
    private List<MusicTypeModel> musicTypeModelList;
    private MusicTypeAdapter musicTypeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music_type, container, false);

        if (RealmHandle.getInstance().getListMusicTypeModel().size() == 0) {
            loadData();
        }
        else{
            musicTypeModelList = new ArrayList<>();
            musicTypeModelList.addAll(RealmHandle.getInstance().getListMusicTypeModel());
            System.out.println("Realm: " + musicTypeModelList.size());
            musicTypeAdapter = new MusicTypeAdapter(musicTypeModelList, getContext());
            musicTypeAdapter.notifyDataSetChanged();
        }
        setupUI(view);
        return view;
    }

    private void loadData() {
        musicTypeModelList = new ArrayList<>();
        musicTypeList = new ArrayList<>();
        musicTypeAdapter = new MusicTypeAdapter(musicTypeModelList, getContext());
        RetrofitFactory.getInstance().createService(MusicTypesService.class).getMusicTypes().enqueue(new Callback<MainObject>() {
            @Override
            public void onResponse(Call<MainObject> call, Response<MainObject> response) {
                if (response.code() == 200) {
                    for (MusicType musicType : response.body().getMediaType().getSubgenres().getAllSubgenres()) {
                        MusicTypeModel musicTypeModel = new MusicTypeModel();
                        musicTypeModel.setIdImage(getResources().getIdentifier("genre_x2_" + musicType.getId(), "raw", getActivity().getPackageName()));
                        musicTypeModel.setId(musicType.getId());
                        musicTypeModel.setKey(musicType.getName());
                        musicTypeList.add(musicType);
                        musicTypeModelList.add(musicTypeModel);
                        RealmHandle.getInstance().addMusicTypeToRealm(musicTypeModel);
                    }
                }
                musicTypeAdapter.notifyDataSetChanged();
                System.out.println("Realm: " + RealmHandle.getInstance().getListMusicTypeModel().size());
            }

            @Override
            public void onFailure(Call<MainObject> call, Throwable t) {
                Toast.makeText(getContext(), "No connection", Toast.LENGTH_SHORT).show();
            }
        });
//        MusicTypeModel musicTypeModel = new MusicTypeModel();
//        musicTypeModel.setId("");
//        musicTypeModel.setKey("All");
//        musicTypeModel.setIdImage(getResources().getIdentifier("genre_x2_" + musicTypeModel.getId(), "raw", getActivity().getPackageName()));
//        musicTypeModelList.add(musicTypeModel);
//        musicTypeAdapter.notifyDataSetChanged();
    }

    private void setupUI(View view) {
        ButterKnife.bind(this, view);
        rvMusicType.setAdapter(musicTypeAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (position % 3 == 0) ? 2 : 1;
            }
        });

        rvMusicType.setLayoutManager(gridLayoutManager);

        musicTypeAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        System.out.println(v.getTag().toString());
        ScreenManager.openFragment(getActivity().getSupportFragmentManager(), new TopSongsFragment(), R.id.rl_frame, true, false);

        EventBus.getDefault().postSticky(new OnClickMusicTypeModel((MusicTypeModel) v.getTag()));
    }
}
