package com.yaniv.student.project2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class Game extends AppCompatActivity implements SensorEventListener {
    ViewGame viewGame;
    Handler toBackHandler;
    Thread toBackThread;
    toBackListenerThread toBackListenerThread;
    SharedPreferences sharedPreferences;
    private SensorManager senSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("vols" , Context.MODE_PRIVATE);
        setContentView(R.layout.activity_game);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        toBackHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                isToBack();

                return false;
            }
        });
        toBackListenerThread = new toBackListenerThread(toBackHandler);
       toBackThread = new Thread(toBackListenerThread);
       toBackThread.start();

        viewGame = new ViewGame(this , sharedPreferences.getInt("music" , 100) ,sharedPreferences.getInt("fx" , 100) );
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.gamefram);
        frameLayout.addView(viewGame);


        viewGame.StartMovment();

    }
    protected void onResume() {
        super.onResume();

        senSensorManager.registerListener(this, senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);

    }
    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            double deltax = sensorEvent.values[0];

            if (viewGame != null) {
              viewGame.Add_X_a(- deltax);

            }

        }
    }
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
        public void isToBack()
        {
            if(viewGame.isToBack())
            {
                toBackListenerThread.toStop();
                Intent Home = new Intent(this , MainActivity.class);
                startActivity(Home);
            }
        }


}
