package com.yaniv.student.project2;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Terms_of_Use extends AppCompatActivity {
    Intent mainIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_terms_of__use);
        mainIntent = new Intent(this , MainActivity.class);
    }

    public void back(View view) {
        startActivity(mainIntent);
    }
}
