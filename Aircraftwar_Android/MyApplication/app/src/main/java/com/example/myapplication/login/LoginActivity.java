package com.example.myapplication.login;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.main.ModeSelectActivity;
import com.example.myapplication.R;
import com.example.myapplication.SQLite.MyDatabaseHelper;
import com.example.myapplication.main.User;

import java.net.Socket;

//登录界面
public class LoginActivity extends AppCompatActivity {
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;

    private MyDatabaseHelper dbHelper;

    //设置为static方便对战的时候使用
    public static Socket socket = new Socket();

    //登录后存储用户
    public static User currentUser = null;
    public static String userNameOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_login);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        WINDOW_WIDTH = dm.widthPixels;
        WINDOW_HEIGHT = dm.heightPixels;
        PwdEdit pasEditText;
        EditText userNameEdit;

        pasEditText = findViewById(R.id.ed_pas);
        userNameEdit = findViewById(R.id.userName);
        pasEditText.setEditTextHint(getResources().getString(R.string.ed_hint_pas));

        //好像没有用，为啥呢
//        String pas = pasEditText.getText();
//        String userName = userNameEdit.getText().toString();
        getSupportActionBar().hide();

        Button register_button = findViewById(R.id.registerButton);

        //数据库
        dbHelper = new MyDatabaseHelper(this, "newUser.db", null, 1);

        //实现用户注册
        register_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                //获取输入，trim()去掉空格和制表符
                userNameOutput = userNameEdit.getText().toString().trim();
                String password = pasEditText.getText().toString().trim();
                //检查有没有获取到
                System.out.println("pas:" + password);
                System.out.println("userName:" + userNameOutput);

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if(!TextUtils.isEmpty(userNameOutput)&&!TextUtils.isEmpty(password)) {
                    User user = new User(userNameOutput, password, 0);
                    dbHelper.add(db, user);
                    Toast.makeText(LoginActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    currentUser = user;
                    Intent intent = new Intent(LoginActivity.this, ModeSelectActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this,"信息不全，注册失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入，trim()去掉空格和制表符
                userNameOutput = userNameEdit.getText().toString().trim();
                String password = pasEditText.getText().toString().trim();
                //看看有没有获取到
                System.out.println("pas:" + password);
                System.out.println("userName:" + userNameOutput);

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if(!TextUtils.isEmpty(userNameOutput)&&!TextUtils.isEmpty(password)) {
                    //获取密码
                    String pwd = dbHelper.getPasswordByUsername(db, userNameOutput);
                    if(password.equals(pwd)){
                        Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        currentUser = new User(userNameOutput,password,0);
                        Intent intent = new Intent(LoginActivity.this, ModeSelectActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "密码错误，登录失败", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(LoginActivity.this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
