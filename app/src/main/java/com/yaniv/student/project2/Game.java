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
import android.widget.FrameLayout;

public class Game extends AppCompatActivity implements SensorEventListener {
    private ViewGame viewGame;
    private Handler toBackHandler;
    private Thread toBackThread;
    private toBackListenerThread toBackListenerThread;
    private SharedPreferences sharedPreferences;
    private SensorManager senSensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("Settings" , Context.MODE_PRIVATE); //getting shared Preferences
        setContentView(R.layout.activity_game);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //making sure that the screen will not spin
        senSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        toBackHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                Back();

                return false;                                                                    //thread checks if the user pressed the 'back' button
            }
        });
        toBackListenerThread = new toBackListenerThread(toBackHandler);
       toBackThread = new Thread(toBackListenerThread);
       toBackThread.start();

        viewGame = new ViewGame(this , sharedPreferences.getInt("music" , 100) ,sharedPreferences.getInt("fx" , 100) );
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.gamefram);
        frameLayout.addView(viewGame);   //adding my custom view to frame layout


        viewGame.startMovement(); //start the game

    }
    protected void onResume() {
        super.onResume();
        viewGame.setSongVol(sharedPreferences.getInt("music" , 100)); //setting music volume to the saved volume
        senSensorManager.registerListener(this, senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST); //registering the sensor manager

    }
    protected void onPause() {
        super.onPause();
        senSensorManager.unregisterListener(this); //unregistering the sensor manager
        viewGame.setSongVol(0); ////setting music volume to the 0
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor mySensor = sensorEvent.sensor;
        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            double deltax = sensorEvent.values[0];

            if (viewGame != null) {
              viewGame.Add_X_a(- deltax);  //applying the change of the sensor on the game

            }

        }
    }
        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
        public void Back()
        {
            if(viewGame.isToBack())    //if user pressed the 'back' button this function returns the user to the main activity
            {
                toBackListenerThread.toStop();
                Intent Home = new Intent(this , MainActivity.class);
                startActivity(Home);
                finish();
            }

        }


}
