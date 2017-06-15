package com.example.hoang.freemusic.networks.search_song;

import com.example.hoang.freemusic.networks.top_song.*;
import com.example.hoang.freemusic.networks.top_song.MainObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Hoang on 5/30/2017.
 */

public interface GetSearchSongService {
    @GET("http://api.mp3.zing.vn/api/mobile/search/song?")
    Call<com.example.hoang.freemusic.networks.search_song.MainObject> getSearchSong(@Query("requestdata") String request);
}
