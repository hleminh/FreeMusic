package com.example.hoang.freemusic.events;

import com.example.hoang.freemusic.databases.models.MusicTypeModel;

/**
 * Created by Hoang on 5/30/2017.
 */

public class OnClickMusicTypeModel {
    private MusicTypeModel musicTypeModel;

    public OnClickMusicTypeModel(MusicTypeModel musicTypeModel) {
        this.musicTypeModel = musicTypeModel;
    }

    public MusicTypeModel getMusicTypeModel() {
        return musicTypeModel;
    }

    public void setMusicTypeModel(MusicTypeModel musicTypeModel) {
        this.musicTypeModel = musicTypeModel;
    }
}
