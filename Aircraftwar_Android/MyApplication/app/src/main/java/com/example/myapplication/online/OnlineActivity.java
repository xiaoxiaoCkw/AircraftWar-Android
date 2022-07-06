package com.example.myapplication.online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


public class OnlineActivity extends AppCompatActivity {

    private SharedPreferences preferences = null;
    private Handler handler = new Handler();
    private String playerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** 读取自己的用户名 */
        preferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        String myMame = preferences.getString("username", "我");

        /** 尝试去连接服务器，连不上会一直卡住 */
        new Thread(() -> {
            Log.e("========", "尝试连接服务器");
            Client.initConnection();
        }).start();

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.e("========", "检查是否连接");
            if (Client.isConnected()) {
                /** 发送自己的用户名 */
                Client.sendMessage(myMame);
                /** 接收对方的用户名 */
                playerName = Client.getMessage();

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("========", "没有连接到服务器");
                handler.post(() -> {
                    Toast.makeText(this, "未连接到服务器！！！", Toast.LENGTH_SHORT).show();
                    finish();
                });
            }
        }).start();

    }

}