package com.example.hoang.freemusic.databases.models;

/**
 * Created by Hoang on 5/28/2017.
 */

public class MusicTypeModel {
    private String id;
    private String key;
    private int idImage;

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

    @Override
    public String toString() {
        return "MusicTypeModel{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                ", idImage=" + idImage +
                '}';
    }
}
