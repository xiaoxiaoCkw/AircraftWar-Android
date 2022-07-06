package com.example.myapplication.application;

import android.view.MotionEvent;
import android.view.View;

import com.example.myapplication.main.MainActivity;
import com.example.myapplication.aircraft.HeroAircraft;

//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;

/**
 * 英雄机控制类
 * 监听鼠标，控制英雄机的移动
 *
 * @author hitsz
 */
public class HeroController implements View.OnTouchListener{
    private GameView game;
    private HeroAircraft heroAircraft;


    public HeroController(GameView game, HeroAircraft heroAircraft){
        this.game = game;
        this.heroAircraft = heroAircraft;
        game.setOnTouchListener(this);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event){
        int eventAction = event.getAction();
        if(eventAction == MotionEvent.ACTION_MOVE){
            int x = (int) event.getRawX();
            int y = (int) event.getRawY();
            if ( x<0 || x> MainActivity.WINDOW_WIDTH || y<0 || y> MainActivity.WINDOW_HEIGHT){
                    // 防止超出边界
                    return false;
                }
            heroAircraft.setLocation(x, y);
            return true;
        }
        return false;
    }
}
