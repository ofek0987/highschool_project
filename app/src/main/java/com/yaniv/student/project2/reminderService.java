package com.yaniv.student.project2;

import android.app.IntentService;
import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class reminderService extends IntentService {

    public reminderService() {
        super("reminderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.trump)
                .setContentTitle("Trump needs you")
                .setContentText("Play Trump vs. Putin");
        NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

            try {
                Thread.sleep(10800000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            notificationManager.notify(1, builder.build());


    }}
