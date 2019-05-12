package com.yaniv.student.project2;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


public class reminderService extends IntentService {
    PendingIntent main;

    public reminderService() {
        super("reminderService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
       main  = PendingIntent.getActivity(this, 0,new Intent(this , MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);



        NotificationManager manager =
                (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {           // support for android oreo or higher (channel required for oreo or higher )

            NotificationChannel channel = new NotificationChannel( "1", "Trump", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }





        NotificationCompat.Builder builder = new NotificationCompat.Builder(this , "1") //setting Notification
                .setSmallIcon(R.drawable.trump)
                .setContentTitle("This bloody putin")
                .setContentText("let's kill him")
                .setContentIntent(main);








        startForeground(1 , builder.build()); //show Notification otherwize android will kill my service
    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);


        return START_STICKY; //service will restart if it will be killed
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this ,"1" )  //setting Notification
                .setSmallIcon(R.drawable.trump)
                .setContentTitle("Trump needs you")
                .setContentText("Play Trump vs. Putin")
                .setContentIntent(main);
        NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

            try {
             Thread.sleep(10800000);                 //Notification will appear after 3 hours;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            notificationManager.notify(2, builder.build());


    }}
