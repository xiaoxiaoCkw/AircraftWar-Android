package com.example.myapplication.application;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;

import com.example.myapplication.main.ModeSelectActivity;
import com.example.myapplication.R;
import com.example.myapplication.main.User;
import com.example.myapplication.aircraft.BaseEnemy;
import com.example.myapplication.aircraft.Boss;
import com.example.myapplication.factory.BaseEnemyFactory;
import com.example.myapplication.factory.BossFactory;
import com.example.myapplication.factory.EliteEnemyFactory;
import com.example.myapplication.factory.MobEnemyFactory;
import com.example.myapplication.factory.RandomPropFactory;

import java.util.Random;

public class InternetGameView extends GameView{
    /**
     * 敌机属性
     */
    private User otherUser = new User("1","1",0);
    private float elitePossibility = 0.4f;
    private int hp = 60;
    private int power = 30;
    private int lastScore = 0;
    private int decreaseHp = 30;
    public InternetGameView(Context context){
        super(context);
        isInternet = true;
        //画面中最多出现6架精英机和普通敌机
        super.setEnemyMaxNumber(6);
        super.setCycleDuration(130);
        //设置道具掉落概率
        RandomPropFactory.setBloodPossibility(0.1f);
        RandomPropFactory.setBulletPossibility(0.1f);
        RandomPropFactory.setImmunePossibility(0.1f);
        RandomPropFactory.setBombPossibility(0.05f);
        System.out.println("对战模式，周期500ms，精英机掉落加血道具概率0.1，火力道具概率0.1，炸弹道具概率0.05，免疫道具概率0.1");
        Runnable updateOtherUserHp = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (otherUser.getLife()>0){
                    int[] delayTime = {500,1000,2000,3000};
                    Random r = new Random();
                    float rand = r.nextFloat();
                    int index;
                    if(rand<0.2){
                        index = 0;
                    }else if(rand<0.7){
                        index = 1;
                    }else if(rand <0.8){
                        index = 2;
                    }else {
                        index = 3;
                    }
                    try {
                        Thread.sleep(delayTime[index]);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    otherUser.setLife(otherUser.getLife()-decreaseHp);
                }
            }
        };

        Runnable updateOtherUserScore = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while (otherUser.getLife()>0){
                    int[] delayTime = {3000,1000,2000};
                    Random r = new Random();
                    float rand = r.nextFloat();
                    int index;
                    if(rand<0.4){
                        index = 0;
                    }else if(rand<0.6){
                        index = 1;
                    }else{
                        index = 2;
                    }
                    try {
                        Thread.sleep(delayTime[index]);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(rand<0.85){
                        otherUser.setSocre(otherUser.getSocre()+10);
                    }else if(rand<0.95){
                        otherUser.setSocre(otherUser.getSocre()+20);
                    }else{
                        otherUser.setSocre(otherUser.getSocre()+40);
                    }
                }
            }
        };
        new Thread(updateOtherUserHp).start();
        new Thread(updateOtherUserScore).start();

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
        decreaseHp = decreaseHp+timeCycleCount/50;
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
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE_INTERNET, 0, backGroundTop- ModeSelectActivity.WINDOW_HEIGHT, mPaint);
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE_INTERNET, 0, backGroundTop,mPaint);
    }

    @Override
    public void paintOtherUser(Paint mPaint, Canvas canvas){
        int x = 500;
        int y = 60;
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(80);
        mPaint.setTypeface(Typeface.SANS_SERIF);
        mPaint.setFakeBoldText(true);
//        mPaint.setFont(new Font("SansSerif", Font.BOLD, 22));
        canvas.drawText("OtherUser:",x,y,mPaint);
        y = y + 80;
        canvas.drawText("SCORE:" + otherUser.getSocre(), x, y, mPaint);
        y = y + 80;
        canvas.drawText("LIFE:" + otherUser.getLife(), x, y, mPaint);

    }


//    public void getOtherUser(){
//        ObjectInputStream ois = null;
//        try {
//            ois = new ObjectInputStream(socket.getInputStream());
//            otherUser = (User)ois.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }


//    }

//    public void updateMyUser(){
//        currentUser.setLife(getHeroAircraft().getHp());
//        currentUser.setSocre(getScore());
//        ObjectOutputStream oos = null;
//        try {
//            oos = new ObjectOutputStream(socket.getOutputStream());
//            oos.writeObject(currentUser);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public boolean otherUserOver(){
        if(otherUser.getLife() <= 0){
            return true;
        }else{
            return false;
        }
    }


}
