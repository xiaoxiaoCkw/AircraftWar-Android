package com.example.myapplication.SQLite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.main.User;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "user.db";
    private static int DB_VERSION = 1;
    private Context context;
    private String SQL = "create table user("//创建数据库语句
            + "id integer primary key autoincrement,"   //主键
            + "username text,"  //字符串型
            + "score integer,"  //整型
            + "password text)";

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void add(SQLiteDatabase sqLiteDatabase, User user){
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("score", user.getSocre());
        contentValues.put("password", user.getPassword());
        sqLiteDatabase.insert("user", null, contentValues);
    }

    @SuppressLint("Range")
    public String getPasswordByUsername(SQLiteDatabase sqLiteDatabase, String username){
        String password = null;
        Cursor cursor = sqLiteDatabase.query("user", null, "username=?", new String[]{username+""}, null, null, null);
        //判断是否有数据
        if (cursor.getCount() != 0){
            //循环获取
            while (cursor.moveToNext()){
                password = cursor.getString(cursor.getColumnIndex("password"));
                break;
            }
        }
        return password;
    }
}
