package com.yaniv.student.project2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    Circle circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(boardGame);
        circle = new Circle(this);
        FrameLayout frameLayout = (FrameLayout)findViewById(R.id.mainfram);
        frameLayout.addView(circle);


        circle.StartMovment();

    }
}
