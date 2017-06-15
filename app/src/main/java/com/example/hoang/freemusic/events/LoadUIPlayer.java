package com.example.hoang.freemusic.events;

import com.example.hoang.freemusic.databases.models.TopSongModel;

/**
 * Created by Hoang on 6/11/2017.
 */

public class LoadUIPlayer {
    private TopSongModel topSongModel;

    public LoadUIPlayer(TopSongModel topSongModel) {
        this.topSongModel = topSongModel;
    }

    public TopSongModel getTopSongModel() {
        return topSongModel;
    }
}
