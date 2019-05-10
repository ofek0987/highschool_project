package com.yaniv.student.project2;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {

   private Intent gameIntent , termsIntent;
    private SeekBar musicBar , fxBar;
   private int musicVol , fxVol;
    private TextView bestscoure;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent reminder = new Intent(this , reminderService.class);
        startService(reminder);
        bestscoure = findViewById(R.id.BestScoure);
        bestscoure.setText("Best Score : "  + String.valueOf( getSharedPreferences("bestS" ,Context.MODE_PRIVATE).getInt("bestS" , 0)));
        sharedPreferences = getSharedPreferences("vols" ,Context.MODE_PRIVATE);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        gameIntent = new Intent(this , Game.class);
        termsIntent = new Intent(this , Terms_of_Use.class);
        musicBar = findViewById(R.id.musicV);
        fxBar = findViewById(R.id.fxV);

             musicVol = sharedPreferences.getInt("music" , 100);
             musicBar.setProgress(musicVol);
             fxVol = sharedPreferences.getInt("fx" , 100);
             fxBar.setProgress(fxVol);



         musicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                 musicVol = i;
                 sharedPreferences.edit().putInt("music",musicVol).commit();
                 seekBar.setProgress(musicVol);
             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {

             }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {

             }
         });

         fxBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                 fxVol = i;
                 sharedPreferences.edit().putInt("fx",fxVol).commit();
                 seekBar.setProgress(fxVol);
             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {

             }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {

             }
         });



    }




    public void startGame(View view) {

        startActivity(gameIntent);
        finish();
    }


    public void openTeams(View view) {
        startActivity(termsIntent);
        finish();

    }
}
