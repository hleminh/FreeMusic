package com.example.hoang.freemusic.managers;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hoang.freemusic.R;
import com.example.hoang.freemusic.databases.models.TopSongModel;
import com.example.hoang.freemusic.events.LoadUIPlayer;
import com.example.hoang.freemusic.networks.RetrofitFactory;
import com.example.hoang.freemusic.networks.search_song.DocObject;
import com.example.hoang.freemusic.networks.search_song.GetSearchSongService;
import com.example.hoang.freemusic.networks.search_song.MainObject;
import com.example.hoang.freemusic.services.MusicNotification;
import com.example.hoang.freemusic.utils.FuzzyMatch;
import com.example.hoang.freemusic.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hybridmediaplayer.HybridMediaPlayer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hoang on 5/30/2017.
 */

public class MusicManager {
    public static HybridMediaPlayer mediaPlayer;
    private static boolean isPrepared;
    private static boolean keepUpdate = true;

    public static void loadSearchSong(final Context context, final TopSongModel topSongModel) {
        GetSearchSongService getSearchSongService = RetrofitFactory.getInstance().createService(GetSearchSongService.class);
        final String dataSong = topSongModel.getSongName() + " " + topSongModel.getArtistName();
        String requestData = "{\"q\":\"" + dataSong + "\",\"sort\":\"hot\", \"start\":\"0\", \"length\":\"10\"}";
        getSearchSongService.getSearchSong(requestData).enqueue(new Callback<MainObject>() {
            @Override
            public void onResponse(Call<MainObject> call, Response<MainObject> response) {
                if (response.code() == 200) {
                    if (response.body().getDocs().size() != 0) {
                        System.out.println(response.body().getDocs().size());
                        List<Integer> ratioList = new ArrayList<Integer>();
                        for (DocObject docObject : response.body().getDocs()) {
                            int ratio = FuzzyMatch.getRatio(dataSong, docObject.getTitle() + " " + docObject.getArtist(), false);
                            ratioList.add(ratio);
                            System.out.println("onRatio: " + ratio);
                        }

                        for (int i = 0; i < ratioList.size(); i++) {
                            if (ratioList.get(i) == Collections.max(ratioList)) {
                                topSongModel.setLinkSource(response.body().getDocs().get(i).getSource().getLinkSource());
                                System.out.println(topSongModel.getLinkSource());
                                playMusic(context, topSongModel);
                                break;
                            }
                        }
                    } else
                        Toast.makeText(context, "Not Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MainObject> call, Throwable t) {
                Toast.makeText(context, "No connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public static void playMusic(final Context context, final TopSongModel topSongModel) {
        isPrepared = false;
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = HybridMediaPlayer.getInstance(context);
        mediaPlayer.setDataSource(topSongModel.getLinkSource());
        mediaPlayer.prepare();

        mediaPlayer.setOnPreparedListener(new HybridMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(HybridMediaPlayer hybridMediaPlayer) {
                isPrepared = true;
                hybridMediaPlayer.play();
                EventBus.getDefault().post(new LoadUIPlayer(topSongModel));
                MusicNotification.setupNotification(context, topSongModel);
            }
        });

    }


    public static void playOrPause() {
        if (isPrepared) {
            if (mediaPlayer.isPlaying()) mediaPlayer.pause();
            else mediaPlayer.play();
            MusicNotification.updateNotification(mediaPlayer.isPlaying());
        }

    }

    public static void updateSongRealtime(final FloatingActionButton fab, final SeekBar sb, final SeekBar seekBar2, final TextView tvCurrent, final TextView tvDuration) {
        final Handler handler = new Handler();
        Runnable autoUpdate = new Runnable() {
            @Override
            public void run() {
                System.out.println(keepUpdate);
                handler.postDelayed(this, 100);
                if (keepUpdate) {
                    if (mediaPlayer.isPlaying()) {
                        fab.setImageResource(R.drawable.ic_pause_black_24dp);
                    } else {
                        fab.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    }
                    sb.setMax(mediaPlayer.getDuration());
                    sb.setProgress(mediaPlayer.getCurrentPosition());
                    if (seekBar2 != null) {
                        seekBar2.setMax(mediaPlayer.getDuration());
                        seekBar2.setProgress(mediaPlayer.getCurrentPosition());
                    }

                    if (tvCurrent != null) {
                        tvCurrent.setText(Utils.convertTime(mediaPlayer.getCurrentPosition()));
                        tvDuration.setText(Utils.convertTime(mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition()));
                    }
                }
            }
        };
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar2 != null) {
                    seekBar2.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                keepUpdate = false;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                keepUpdate = true;
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        if (seekBar2 != null) {
            seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    sb.setProgress(progress);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    keepUpdate = false;
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    keepUpdate = true;
                    mediaPlayer.seekTo(seekBar.getProgress());
                }
            });
        }
        autoUpdate.run();
    }
}
