package com.example.hoang.freemusic.networks.music_type;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Hoang on 5/23/2017.
 */

public interface MusicTypesService {
    @GET("https://itunes.apple.com/WebObjects/MZStoreServices.woa/ws/genres")
    Call<List<MediaType>> getMusicTypes();
}
