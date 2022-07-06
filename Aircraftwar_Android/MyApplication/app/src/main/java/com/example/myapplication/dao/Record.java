package com.example.myapplication.dao;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * 记录数据类
 * 记录每局游戏结束后的分数、时间、用户名、日期
 */
public class Record implements Serializable,Comparable<Record> {
    private String name;
    private int score,hour,minute;
    private String date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Record(String name, int score){
        this.name = name;
        this.score = score;
        date = LocalDate.now().toString();
        hour = LocalTime.now().getHour();
        minute = LocalTime.now().getMinute();
    }

    public String getName(){
        return this.name;
    }

    public String getHour(){
        return String.valueOf(this.hour);
    }

    public String getScore() {
        return String.valueOf(score);
    }

    public String getMinute() {
        return String.valueOf(minute);
    }

    public String getDate() {
        String minuteString = String.valueOf(minute);
        if(minute<10){
            minuteString = "0"+minuteString;
        }
        return date+" "+hour+":"+minuteString;
    }


   @Override
    public int compareTo(Record o){
        return o.score-this.score;
   }
}
