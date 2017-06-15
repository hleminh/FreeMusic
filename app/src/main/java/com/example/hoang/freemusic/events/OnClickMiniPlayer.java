package com.example.hoang.freemusic.events;

import com.example.hoang.freemusic.databases.models.TopSongModel;

/**
 * Created by Hoang on 6/11/2017.
 */

public class OnClickMiniPlayer {
    private TopSongModel topSongModel;

    public OnClickMiniPlayer(TopSongModel topSongModel) {
        this.topSongModel = topSongModel;
    }

    public TopSongModel getTopSongModel() {
        return topSongModel;
    }
}
