package com.yaniv.student.project2;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {

   private Intent gameIntent , termsIntent , reminder;
    private SeekBar musicBar , fxBar;
   private int musicVol , fxVol;
    private TextView bestscoure;
    private Dialog d;



    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("Settings" ,Context.MODE_PRIVATE);
        setContentView(R.layout.activity_main);

        d= new Dialog(this);
        d.setContentView(R.layout.dialog);
        d.setTitle("Settings");
        d.setCancelable(true);
        ((CheckBox)d.findViewById(R.id.remindCheck)).setChecked(sharedPreferences.getBoolean("isToRemind" , true));
        musicBar = d.findViewById(R.id.musicSeek);
        fxBar = d.findViewById(R.id.FXseek);
        reminder = new Intent(this , reminderService.class);

        if(sharedPreferences.getBoolean("isToRemind" , true)) {
            startService(reminder); //starts the reminder service
        }

        bestscoure = findViewById(R.id.BestScoure);
        bestscoure.setText("Best Score : "  + String.valueOf( getSharedPreferences("bestS" ,Context.MODE_PRIVATE).getInt("bestS" , 0))); //getting the saved value for the higher score

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  //making sure that the screen will not spin
        gameIntent = new Intent(this , Game.class);
        termsIntent = new Intent(this , Terms_of_Use.class);
        musicVol = sharedPreferences.getInt("music" , 100);
        musicBar.setProgress(musicVol);
        fxVol = sharedPreferences.getInt("fx" , 100);
        fxBar.setProgress(fxVol);



    }




    public void startGame(View view) {

        startActivity(gameIntent); //starts the game
        finish();
    }


    public void openTeams(View view) {
        startActivity(termsIntent); //open the 'terms of use' activity
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu); //makes my menu appear
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.Exit) {
            finish();
        }

        if(item.getItemId() == R.id.Settings){


            musicBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
             @Override
             public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                 musicVol = i;
                 sharedPreferences.edit().putInt("music",musicVol).apply(); //saving the value for the volume for the music that the user entered
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
                 sharedPreferences.edit().putInt("fx",fxVol).apply();  //saving the value for the volume of the fx that the user entered
                 seekBar.setProgress(fxVol);
             }

             @Override
             public void onStartTrackingTouch(SeekBar seekBar) {

             }

             @Override
             public void onStopTrackingTouch(SeekBar seekBar) {

             }
         });
            d.show(); //show the dialog


        }



        return true;
    }

    public void clickedCheck(View view) {
        sharedPreferences.edit().putBoolean("isToRemind" , ((CheckBox) view).isChecked()).apply();
        if(((CheckBox) view).isChecked()) {

            startService(reminder); //start\stop the service and save whether the service will be started according to the user wish(using Check Box)
        }
        else {
            stopService(reminder);
        }
    }
}
