package com.example.myapplication.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.application.DifficultGameView;
//import com.example.myapplication.application.Game;
import com.example.myapplication.application.GameView;
import com.example.myapplication.application.InternetGameView;
import com.example.myapplication.application.NormalGameView;
import com.example.myapplication.application.SimpleGameView;

import java.io.BufferedReader;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;
    public static String whichMode;
    public static GameView gameView = null;
    public static final Object lock = new Object();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_select);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        WINDOW_WIDTH = dm.widthPixels;
        WINDOW_HEIGHT = dm.heightPixels;
        Button easy = findViewById(R.id.easy);
        Button normal = findViewById(R.id.normal);
        Button hard = findViewById(R.id.hard);
        //给各个按钮添加点击事件
        easy.setOnClickListener(this);
        normal.setOnClickListener(this);
        hard.setOnClickListener(this);
        //setContent用于添加界面，就是画不同难度的界面
        //后续添加Easy， Hard，Normal
//        setContentView(new GameView(this));
        getSupportActionBar().hide();
    }
    @Override
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.easy:
                gameView = new SimpleGameView(this);
                setContentView(gameView);
                whichMode = "simple";
                break;
            case R.id.normal:
                gameView = new NormalGameView(this);
                setContentView(gameView);
                Log.i("test","ss");
                whichMode = "normal";
                break;
            case R.id.hard:
                gameView = new DifficultGameView(this);
                setContentView(gameView);
                whichMode = "hard";
                break;
            default:
                gameView = new GameView(this);
                setContentView(gameView);
                whichMode = "error";
                break;
        }
    }

}