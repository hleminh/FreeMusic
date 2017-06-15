package com.example.hoang.freemusic.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.hoang.freemusic.R;
import com.example.hoang.freemusic.activities.MainActivity;
import com.example.hoang.freemusic.databases.models.TopSongModel;

/**
 * Created by Hoang on 6/13/2017.
 */

public class MusicNotification {
    public static NotificationCompat.Builder builder;
    public static NotificationManager manager;
    public static int NOTIFICATION_ID = 1;
    public static RemoteViews remoteViews;

    public static void setupNotification(Context context, TopSongModel topSongModel) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        remoteViews.setTextViewText(R.id.tv_song, topSongModel.getSongName());
        remoteViews.setTextViewText(R.id.tv_artist, topSongModel.getArtistName());
        remoteViews.setImageViewResource(R.id.iv_play, R.drawable.ic_pause_black_24dp);
        setOnPlayPauseNotification(context);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context).setOngoing(true).setSmallIcon(R.drawable.ic_music_note_black_24dp).setCustomBigContentView(remoteViews).setContentIntent(pendingIntent);
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    public static void setOnPlayPauseNotification(Context context) {
        Intent intent = new Intent(context, MusicService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.iv_play, pendingIntent);
    }

    public static void updateNotification(boolean isPlaying){
        if (isPlaying){
            remoteViews.setImageViewResource(R.id.iv_play,R.drawable.ic_pause_black_24dp);
        }else remoteViews.setImageViewResource(R.id.iv_play, R.drawable.ic_play_arrow_black_24dp);
        manager.notify(NOTIFICATION_ID, builder.build());
    }
}
