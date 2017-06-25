package com.example.hoang.freemusic.databases.models;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Hoang on 5/28/2017.
 */

public class MusicTypeModel extends RealmObject{
    private String id;
    private String key;
    private int idImage;
    private RealmList<TopSongModel> topSongModelList = new RealmList<>();
    private boolean isFavorite;

    public RealmList<TopSongModel> getTopSongModelList() {
        return topSongModelList;
    }

    public void setTopSongModelList(RealmList<TopSongModel> topSongModelList) {
        this.topSongModelList = topSongModelList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "MusicTypeModel{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", idImage=" + idImage +
                '}';
    }
}
