package com.example.hoang.freemusic.databases;

import com.example.hoang.freemusic.databases.models.MusicTypeModel;
import com.example.hoang.freemusic.databases.models.TopSongModel;
import com.example.hoang.freemusic.networks.music_type.MusicType;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Hoang on 6/20/2017.
 */

public class RealmHandle {
    Realm realm = Realm.getDefaultInstance();
    private static RealmHandle instance;

    public static RealmHandle getInstance() {
        if (instance == null) instance = new RealmHandle();
        return instance;
    }

    public void addMusicTypeToRealm(MusicTypeModel musicTypeModel) {
        realm.beginTransaction();
        realm.copyToRealm(musicTypeModel);
        realm.commitTransaction();
    }

    public void addTopSongToMusicType(MusicTypeModel musicTypeModel, TopSongModel topSongModel) {
        realm.beginTransaction();
        musicTypeModel.getTopSongModelList().add(topSongModel);
        realm.commitTransaction();
    }

    public List<MusicTypeModel> getListMusicTypeModel() {
        return realm.where(MusicTypeModel.class).findAll();
    }

    public List<TopSongModel> getListTopSongModel(MusicTypeModel musicTypeModel) {
        return musicTypeModel.getTopSongModelList();
    }

    public void clearTopSongList(MusicTypeModel musicTypeModel) {
        realm.beginTransaction();
        musicTypeModel.getTopSongModelList().clear();
        realm.commitTransaction();
    }

    public void setFavoriteMusicType(MusicTypeModel musicTypeModel, boolean setCheck) {
        realm.beginTransaction();
        musicTypeModel.setFavorite(setCheck);
        realm.commitTransaction();
    }

    public List<MusicTypeModel> getFavoriteMusicTypeList() {
        return realm.where(MusicTypeModel.class).equalTo("isFavorite", true).findAll();
    }
}
