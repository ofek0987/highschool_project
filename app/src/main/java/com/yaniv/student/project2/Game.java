package com.yaniv.student.project2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class Game extends AppCompatActivity {
    ViewGame ViewGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ViewGame = new ViewGame(this);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.gamefram);
        frameLayout.addView(ViewGame);


        ViewGame.StartMovment();

    }
}
