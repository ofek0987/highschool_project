package com.yaniv.student.project2;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
   // ViewGame ViewGame;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(boardGame);
       // ViewGame = new ViewGame(this);
       // FrameLayout frameLayout = (FrameLayout)findViewById(R.id.mainfram);
       // frameLayout.addView(ViewGame);


       // ViewGame.StartMovment();
        intent = new Intent(this , Game.class);
        startActivity(intent);

    }
}
