package com.yaniv.student.project2;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity  {
   // ViewGame ViewGame;
    Intent gameIntent , termsIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        gameIntent = new Intent(this , Game.class);
        termsIntent = new Intent(this , Terms_of_Use.class);



    }




    public void startGame(View view) {
        startActivity(gameIntent);
    }


    public void openTeams(View view) {
        startActivity(termsIntent);

    }
}
