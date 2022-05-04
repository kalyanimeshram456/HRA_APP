package com.ominfo.crm_solution.alarm.service;

import static androidx.core.app.NotificationCompat.DEFAULT_SOUND;
import static androidx.core.app.NotificationCompat.DEFAULT_VIBRATE;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.DATETIME;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.ID;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.TITLE;
import static com.ominfo.crm_solution.basecontrol.BaseApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.alarm.activity.RingActivity;

public class AlarmService extends Service {
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    @Override
    public void onCreate() {
        super.onCreate();

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm);
        mediaPlayer.setLooping(true);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, RingActivity.class);
        String idValue = "",DateTimeValue = "";

        String CHANNEL_ID = "channel_Alarm";
        String CHANNEL_NAME = "channel_Alarm";

        NotificationCompat.Builder builder = null;
        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            channel.setLightColor(Color.GREEN);
            channel.setShowBadge(true);
            channel.setDescription("");
            notificationManager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
            builder.setChannelId(CHANNEL_ID);
            builder.setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE);
        } else {
            builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        }

        String alarmTitle = "Alarm";
        try{
            alarmTitle = String.format("Reminder for %s", intent.getStringExtra(TITLE));
        }catch (Exception e){
            e.printStackTrace();}
        if(intent!=null) {
            try {
                idValue = intent.getStringExtra(TITLE);
                DateTimeValue = intent.getStringExtra(DATETIME);
            }catch (Exception e){e.printStackTrace();}
        }
        notificationIntent.putExtra(ID,idValue);
        notificationIntent.putExtra(TITLE,idValue);
        notificationIntent.putExtra(DATETIME,DateTimeValue);

        PendingIntent pendingIntent;// = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);

        }else {
            pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        }
        builder.setContentTitle(alarmTitle);
        builder.setContentText("Tap to perform action.");
        Uri notificationSound = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_NOTIFICATION);
        //builder.setSound(notificationSound);
        builder.setNotificationSilent();
        builder.setAutoCancel(true);
        builder.setSmallIcon(R.drawable.ic_om_reminder);
        builder.setContentIntent(pendingIntent);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE) //Important for heads-up notification
                .setPriority(Notification.PRIORITY_MAX); //Important for heads-up
        Notification notification = builder.build();

        mediaPlayer.start();

        long[] pattern = { 0, 100, 1000 };
        vibrator.vibrate(pattern, 0);
        //notificationManager.notify(001, builder.build());
        startForeground(1, notification);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
         startActivity(notificationIntent);
        Intent intent1= new Intent(this,RingActivity.class);
       /* intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent1);*/
        /*String alarmTitle = "Alarm";
        try{
           String.format("%s Alarm", intent.getStringExtra(TITLE));
        }catch (Exception e){
            e.printStackTrace();}
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(alarmTitle)
                .setContentText("Ring Ring .. Ring Ring")
                .setSmallIcon(R.drawable.ic_boy)
                .setContentIntent(pendingIntent)
                .setChannelId(CHANNEL_ID)
                .build();

        mediaPlayer.start();

        long[] pattern = { 0, 100, 1000 };
        vibrator.vibrate(pattern, 0);

        startForeground(1, notification);*/

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mediaPlayer.stop();
        vibrator.cancel();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
