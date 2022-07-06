package com.example.myapplication.application;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import com.example.myapplication.main.GameActivity;
import com.example.myapplication.aircraft.BaseEnemy;
import com.example.myapplication.factory.BaseEnemyFactory;
import com.example.myapplication.factory.EliteEnemyFactory;
import com.example.myapplication.factory.MobEnemyFactory;


import java.util.Random;

public class SimpleGameView extends GameView{
    private SurfaceHolder surfaceHolder = getHolder();
    public SimpleGameView(Context context){
        super(context);
        System.out.println("简单模式，周期600ms，加血道具概率0.15，火力道具概率0.15，免疫道具概率0.15，炸弹道具概率0.1");
    }

    /**精英机掉落
     * 按8:2概率创建普通敌机和精英敌机
     */
    @Override
    public BaseEnemy createEnemy(){
        Random propR = new Random();
        float whichProp;
        whichProp = propR.nextFloat();
        BaseEnemyFactory enemyFactory;
        if(whichProp<=0.2){
            enemyFactory = new EliteEnemyFactory();
        }else{
            enemyFactory = new MobEnemyFactory();
        }
        enemyFactory.setHp(30);
        return enemyFactory.createEnemy();
    }
    @Override
    public void paintBackground(Paint mPaint, Canvas canvas){
        //绘制滚动背景图片
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE_SIMPLE, 0, backGroundTop- GameActivity.WINDOW_HEIGHT, mPaint);
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE_SIMPLE, 0, backGroundTop,mPaint);

    }

//    @Override
//    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
//        new Thread(this).start();
//    }
//
//    @Override
//    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {
//
//    }
//
//    @Override
//    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
//
//    }

//    @Override
//    public void run() {
//        System.out.println("In Run func11111111~~~~~~~~~~~~");
//        while(!isGameOverFlag) {
//            System.out.println("In Run func22222222222~~~~~~~~~~~~");
//            synchronized (surfaceHolder) {
//                try {
//                    System.out.println("Draw on~~~~~~~~~~~~~~~~~~~");
//                    action();
//                    draw();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
