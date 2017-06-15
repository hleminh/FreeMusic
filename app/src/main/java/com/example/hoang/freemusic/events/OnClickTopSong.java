package com.example.hoang.freemusic.events;

import com.example.hoang.freemusic.databases.models.TopSongModel;

/**
 * Created by Hoang on 6/6/2017.
 */

public class OnClickTopSong {
    private TopSongModel topSongModel;

    public TopSongModel getTopSongModel() {
        return topSongModel;
    }

    public OnClickTopSong(TopSongModel topSongModel) {
        this.topSongModel = topSongModel;
    }
}
