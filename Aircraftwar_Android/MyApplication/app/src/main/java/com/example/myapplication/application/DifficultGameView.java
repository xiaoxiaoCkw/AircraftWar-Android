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


import java.util.Random;

public class DifficultGameView extends GameView{
    /**
     * 对boss出现次数计数
     */
    private int bossCounts = 0;
//    private LoopMusicThread bossMusic;
    private int lastScore = 0;
    /**
     * 敌机属性
     */
    private float elitePossibility = 0.5f;
    private int hp = 60;
    private int power = 35;

    public DifficultGameView(Context context){
        super(context);
        //画面中最多出现8架精英机和普通敌机
        super.setEnemyMaxNumber(8);
        super.setCycleDuration(110);
        //设置道具掉落概率
        RandomPropFactory.setBloodPossibility(0.07f);
        RandomPropFactory.setBulletPossibility(0.07f);
        RandomPropFactory.setImmunePossibility(0.07f);
        RandomPropFactory.setBombPossibility(0.05f);
        System.out.println("困难模式，周期400ms，精英机掉落加血道具概率0.07，火力道具概率0.07，炸弹道具概率0.05，免疫道具概率0.07");
    }
    /**
     * 按5:5概率创建普通敌机和精英敌机,随时间概率增加
     */
    @Override
    public BaseEnemy createEnemy(){
        Random propR = new Random();
        float whichProp;
        BaseEnemyFactory enemyFactory;
        whichProp=propR.nextFloat();
        //每过了50个周期提升一次精英机概率
        if(whichProp<=elitePossibility+timeCycleCount/50*0.02f){
            enemyFactory = new EliteEnemyFactory();
        }else{
            enemyFactory = new MobEnemyFactory();
        }
        enemyFactory.setHp(hp+2*timeCycleCount/50);
        enemyFactory.setPower(power+2*timeCycleCount/50);
        if(timeCycleCount%50==0 && timeCycleCount!=0){
            System.out.printf("难度提升！精英机出现概率：%.2f,除了boss的敌机血量：%d,敌机子弹攻击力：%d\n",elitePossibility+timeCycleCount/50*0.02f,hp+2*timeCycleCount/50,power+2*timeCycleCount/50);
        }
        return enemyFactory.createEnemy();
    }
    /**
     * 创建boss机，随出现次数增加而增大血量
     */
    @Override
    public void createBossAction(){
        int score = super.getScore();
        int bossHp = 150;
        if(!bossFlag && ((score%200==0 && score != 0)||score-lastScore>=200)){
            lastScore = score;
            bossCounts+=1;
            BaseEnemyFactory enemyFactory = new BossFactory();
            enemyFactory.setHp(bossHp+bossCounts*50);
            enemyFactory.setPower(power+2*timeCycleCount/50);
            BaseEnemy boss = enemyFactory.createEnemy();
            System.out.printf("第%d次出现boss，boss血量为%d\n",bossCounts,boss.getHp());
            getEnemyAircrafts().add(boss);
            ((Boss)boss).setGame(this);
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
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE_DIFFICULT, 0, super.getBackGroundTop() - GameActivity.WINDOW_HEIGHT, mPaint);
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE_DIFFICULT, 0, super.getBackGroundTop(),mPaint);

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
