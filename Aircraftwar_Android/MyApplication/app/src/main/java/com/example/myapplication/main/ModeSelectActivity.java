package com.example.myapplication.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.application.GameView;
import com.example.myapplication.application.InternetGameView;

public class ModeSelectActivity extends AppCompatActivity {
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;
    public static boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        WINDOW_WIDTH = dm.widthPixels;
        WINDOW_HEIGHT = dm.heightPixels;

        Button singleButton = findViewById(R.id.singleButton);

        singleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ModeSelectActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        Button multiButton = findViewById(R.id.multiButton);

        multiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(ModeSelectActivity.this, GameActivity.class);
//                startActivity(intent);
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(4000);
                            flag = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };

                Toast.makeText(ModeSelectActivity.this,"等待连接",Toast.LENGTH_SHORT).show();
//                new Thread(r).start();
//                while (!flag){
//
//                }
//                GameView gameView = new InternetGameView(ModeSelectActivity.this);
//                setContentView(gameView);

            }
        });
    }



}