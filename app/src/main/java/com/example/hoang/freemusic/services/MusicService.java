package com.example.hoang.freemusic.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.example.hoang.freemusic.R;
import com.example.hoang.freemusic.managers.MusicManager;

/**
 * Created by Hoang on 6/13/2017.
 */

public class MusicService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (MusicManager.mediaPlayer.isPlaying()) {
                MusicManager.mediaPlayer.pause();
                MusicNotification.remoteViews.setImageViewResource(R.id.iv_play, R.drawable.ic_play_arrow_black_24dp);
            } else {
                MusicManager.mediaPlayer.play();
                MusicNotification.remoteViews.setImageViewResource(R.id.iv_play, R.drawable.ic_pause_black_24dp);
            }
            MusicNotification.manager.notify(MusicNotification.NOTIFICATION_ID, MusicNotification.builder.build());
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
