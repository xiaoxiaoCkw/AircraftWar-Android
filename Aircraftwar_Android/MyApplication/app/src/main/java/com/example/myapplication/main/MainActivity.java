package com.example.myapplication.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.myapplication.R;
import com.example.myapplication.application.GameView;
import com.example.myapplication.login.LoginActivity;

public class MainActivity extends AppCompatActivity{
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        DisplayMetrics dm = getResources().getDisplayMetrics();
        WINDOW_WIDTH = dm.widthPixels;
        WINDOW_HEIGHT = dm.heightPixels;
        Button start = findViewById(R.id.start);
        getSupportActionBar().hide();
    }

    public void startGame(View view){
        //界面切换
        Intent intent = new Intent(this, ModeSelectActivity.class);
        startActivity(intent);
    }

    public void RegisterGame(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onCheckboxClicked(View view){
        boolean checked = ((CheckBox) view).isChecked();

        if(checked){
            GameView.setMusic();
        }
        else{
            GameView.closeMusic();
        }
    }

}