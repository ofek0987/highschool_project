package com.yaniv.student.project2;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;


public class reminderService extends IntentService {

    public reminderService() {
        super("reminderService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.trump)
                .setContentTitle("This bloody putin")
                .setContentText("let's kill him");
        ;

        startForeground(1 , builder.build());
    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);


        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.trump)
                .setContentTitle("Trump needs you")
                .setContentText("Play Trump vs. Putin");
        NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

            try {
             Thread.sleep(10000);                 //10800000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            notificationManager.notify(2, builder.build());


    }}
