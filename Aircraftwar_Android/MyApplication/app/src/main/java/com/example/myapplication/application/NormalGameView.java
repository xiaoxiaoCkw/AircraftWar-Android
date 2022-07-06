package com.example.myapplication.application;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;

import com.example.myapplication.main.GameActivity;
import com.example.myapplication.R;
import com.example.myapplication.aircraft.BaseEnemy;
import com.example.myapplication.aircraft.Boss;
import com.example.myapplication.factory.*;
//import com.example.myapplication.music.LoopMusicThread;
//import com.example.myapplication.music.MusicThread;


import java.util.Random;

public class NormalGameView extends GameView{
//    private LoopMusicThread bossMusic = null;
    /**
     * 敌机属性
     */
    private float elitePossibility = 0.4f;
    private int hp = 60;
    private int power = 30;
    private int lastScore = 0;
    public NormalGameView(Context context){
        super(context);
        //画面中最多出现6架精英机和普通敌机
        super.setEnemyMaxNumber(6);
        super.setCycleDuration(130);
        //设置道具掉落概率
        RandomPropFactory.setBloodPossibility(0.1f);
        RandomPropFactory.setBulletPossibility(0.1f);
        RandomPropFactory.setImmunePossibility(0.1f);
        RandomPropFactory.setBombPossibility(0.05f);
        System.out.println("普通模式，周期500ms，精英机掉落加血道具概率0.1，火力道具概率0.1，炸弹道具概率0.05，免疫道具概率0.1");
    }
    /**
     * 按6:4概率创建普通敌机和精英敌机,随时间精英机概率增加
     */
    @Override
    public BaseEnemy createEnemy(){
        Random propR = new Random();
        float whichProp;
        BaseEnemyFactory enemyFactory;
        whichProp=propR.nextFloat();
        //每过了50个周期提升一次精英机概率
        if(whichProp<=elitePossibility+timeCycleCount/50*0.01f){
            enemyFactory = new EliteEnemyFactory();
        }else{
            enemyFactory = new MobEnemyFactory();
        }
        enemyFactory.setHp(hp+timeCycleCount/50);
        enemyFactory.setPower(power+timeCycleCount/50);
        if(timeCycleCount%50==0 && timeCycleCount!=0){
            System.out.printf("难度提升！精英机出现概率：%.2f,除了boss的敌机血量：%d,敌机子弹攻击力：%d\n",elitePossibility+timeCycleCount/50*0.01f,hp+timeCycleCount/50,power+timeCycleCount/50);
        }
        return enemyFactory.createEnemy();
    }

    @Override
    public void createBossAction(){
        int score = super.getScore();
        if(!bossFlag &&( (score%300==0 && score != 0)||score-lastScore>=300)){
            lastScore = score;
            BaseEnemyFactory enemyFactory = new BossFactory();
            enemyFactory.setPower(power+timeCycleCount/50);
            enemyFactory.setHp(200);
            BaseEnemy boss = enemyFactory.createEnemy();
            ((Boss)boss).setGame(this);
            getEnemyAircrafts().add(boss);
            System.out.println("boss机产生");
            bossFlag = true;
            if(GameView.isMusicTurnOn()){
                boss_music_on();
            }
        }
    }

//    @Override
//    public void closeBossMusic(){
//        if(GameView.isMusicTurnOn()){
//            boss_bgm.release();
//            boss_bgm = null;
//        }
//    }

    @Override
    public void boss_music_on(){
//        Runnable bgm_task = ()->{
            System.out.println("Music on");
            this.boss_bgm = MediaPlayer.create(this.getContext(), R.raw.bgm_boss);
            boss_bgm.setLooping(true);
            boss_bgm.start();
//        };
//
//        new Thread(bgm_task).run();

    }
    @Override
    public void paintBackground(Paint mPaint, Canvas canvas){
        //绘制滚动背景图片
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE_NORMAL, 0, backGroundTop- GameActivity.WINDOW_HEIGHT, mPaint);
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE_NORMAL, 0, backGroundTop,mPaint);
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
//
//    @Override
//    public void run() {
//        while (true){
//            draw();
//        }
//    }
}
