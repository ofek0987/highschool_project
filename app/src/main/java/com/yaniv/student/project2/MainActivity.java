package com.yaniv.student.project2;
import android.content.Intent;
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
        //setContentView(boardGame);
       // ViewGame = new ViewGame(this);
       // FrameLayout frameLayout = (FrameLayout)findViewById(R.id.mainfram);
       // frameLayout.addView(ViewGame);


       // ViewGame.StartMovment();
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
