package com.yaniv.student.project2;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class Game extends AppCompatActivity implements SensorEventListener {
    ViewGame viewGame;
    Boolean run = true;
    private SensorManager senSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        viewGame = new ViewGame(this);
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

    public void StopOrRun(View view) {
        if(run)
        {
            viewGame.StopMovment();
        }
        else {
            viewGame.ContinueMovment();
        }
        run = !run;

    }
}
