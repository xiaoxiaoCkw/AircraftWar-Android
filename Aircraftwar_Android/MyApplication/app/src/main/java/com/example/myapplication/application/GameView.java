package com.example.myapplication.application;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.myapplication.main.GameActivity;
import com.example.myapplication.main.Lose;
import com.example.myapplication.main.MainActivity;
import com.example.myapplication.main.ModeSelectActivity;
import com.example.myapplication.main.Win;
import com.example.myapplication.rank.RankActivity;
import com.example.myapplication.aircraft.*;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.myapplication.R;
import com.example.myapplication.basic.AbstractFlyingObject;
import com.example.myapplication.bullet.BaseBullet;
import com.example.myapplication.bullet.EnemyBullet;
import com.example.myapplication.factory.BaseEnemyFactory;
import com.example.myapplication.factory.EliteEnemyFactory;
import com.example.myapplication.factory.MobEnemyFactory;
import com.example.myapplication.Prop.BaseProp;
import com.example.myapplication.Prop.PropBomb;
import com.example.myapplication.Prop.PropBullet;
import com.example.myapplication.Prop.PropImmune;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable {
    private SurfaceHolder surfaceHolder;
    private Canvas canvas;
    private Bitmap bitmap;
    private Paint mPaint;
    protected int backGroundTop = 0;
    private ImageManager imageManager;
    private Context context;


    protected MediaPlayer bgm = MediaPlayer.create(this.getContext(), R.raw.bgm);
    protected MediaPlayer boss_bgm = MediaPlayer.create(this.getContext(), R.raw.bgm_boss);;
    /**??????boss??????????????????*/
    protected boolean bossFlag = false;
    protected boolean isGameOverFlag;

    protected int timeCycleCount = 0;



    /**
     * Scheduled ??????????????????????????????
     */
//    private final ScheduledExecutorService executorService;
    private final ScheduledExecutorService scheduledExecutorService;

    /**
     * ????????????(ms)?????????????????????
     */
    private int timeInterval = 10;

    private final HeroAircraft heroAircraft;
    private final List<BaseEnemy> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<BaseProp> props;

    private int enemyMaxNumber = 4;

    private boolean gameOverFlag = false;
    private int score = 0;
    private int time = 0;
    /**
     * ?????????ms)
     * ?????????????????????????????????????????????
     * ?????????????????????????????????????????????
     */
    private int cycleDuration = 150;
    private int cycleTime = 0;
    private int heroAircraftShootCycle = 150;
    private int heroCycleTime = 0;

    public boolean isInternet = false;
    /**
     * ????????????????????????
     */
    private static boolean musicTurnOn = false;
//    private LoopMusicThread backGroundMusic;


    public GameView(Context context) {
        super(context);
        this.context = context;
        imageManager = new ImageManager(context);
        //????????????
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.bg);
        //???????????????????????????
        bitmap = Bitmap.createScaledBitmap(bitmap, GameActivity.WINDOW_WIDTH>0?GameActivity.WINDOW_WIDTH: ModeSelectActivity.WINDOW_WIDTH, GameActivity.WINDOW_HEIGHT>0?GameActivity.WINDOW_HEIGHT:ModeSelectActivity.WINDOW_HEIGHT, true);
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        heroAircraft = HeroAircraft.getHeroAircraft();
        mPaint = new Paint();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        if(musicTurnOn){
            bgm_music_on();
        }

        /**
         * Scheduled ????????????????????????????????????
         * ??????alibaba code guide??????????????? ThreadFactory ????????????????????????
         * apache ??????????????? org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
//        this.executorService = new ScheduledThreadPoolExecutor(1,
//                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());
//        this.scheduledExecutorService = Executors.newScheduledThreadPool(1, new BasicThreadFactory.DefaultThreadBuilder().namingPattern("game-action-%d").daemon(true).build());
        this.scheduledExecutorService = new ScheduledThreadPoolExecutor(1);

        //???????????????????????????
        new HeroController(this, heroAircraft);
    }

    public static boolean isMusicTurnOn() {
        return musicTurnOn;
    }

    public int getScore() {
        return score;
    }
    //??????????????????
    public static void setMusic() {
        musicTurnOn = true;
    }
    //??????????????????
    public static void closeMusic(){
        musicTurnOn = false;
    }
    //?????????boss???????????????????????????
    public void setEnemyMaxNumber(int enemyMaxNumber) {
        this.enemyMaxNumber = enemyMaxNumber;
    }
    //????????????????????????
    public void setCycleDuration(int cycleDuration) {
        this.cycleDuration = cycleDuration;
    }

    public void setBossFlag(boolean bossFlag) {
        this.bossFlag = bossFlag;
    }

    public List<BaseEnemy> getEnemyAircrafts() {
        return enemyAircrafts;
    }

    public void closeBossMusic(){
            boss_bgm.release();
            boss_bgm = null;
    }

    public HeroAircraft getHeroAircraft() {
        return heroAircraft;
    }

    /**
     * ????????????????????????????????????????????????
     */
    //???Simple???????????????????????????((
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
    };


    /**
     * ???????????????????????????????????????
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public final void action() throws InterruptedException{

        // ???????????????????????????????????????????????????????????????????????????
        Runnable task = () -> {

            time += timeInterval;
            //??????????????????

            // ?????????????????????????????????
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                System.out.println(timeCycleCount);
                // ???????????????
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    enemyAircrafts.add(createEnemy());
                }
                // ??????????????????
                enemyShootAction();
            }

            if(heroTimeCountAndNewCycleJudge()){
                heroShootAction();
            }
            //Boss???????????????
            createBossAction();

            // ????????????
            bulletsMoveAction();

            // ????????????
            aircraftsMoveAction();

            // ????????????
            propsMoveAction();

            // ????????????
            crashCheckAction();

            // ?????????
            postProcessAction();

            //????????????
            getOtherUser();

            //????????????
            updateMyUser();

            //????????????????????????
//            repaint();
            draw();
            // ??????????????????
            if (heroAircraft.getHp() <= 0 || otherUserOver()) {
                // ????????????
                //????????????????????????????????????????????????????????????
                if(musicTurnOn){
                    bgm.release();
                    bgm = null;
                    music_on(R.raw.game_over);
                    if(bossFlag){
                        closeBossMusic();
//                        boss_bgm.release();
//                        boss_bgm = null;
                    }
                }
                scheduledExecutorService.shutdown();
                closeSocket();
                gameOverFlag = true;
                PropBullet.setJump();
                PropImmune.setJump();
                System.out.println("Game Over!");
                if(!isInternet){
                    Intent intent = new Intent(context,RankActivity.class);
                    context.startActivity(intent);
                }else if(otherUserOver()){
                    Intent intent = new Intent(context, Win.class);
                    context.startActivity(intent);
                }else{
                    Intent intent = new Intent(context, Lose.class);
                    context.startActivity(intent);
                }
            }

        };



        /**
         * ?????????????????????????????????
         * ??????????????????????????????????????????????????????????????????????????????????????????
         */
//        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
//        if(musicTurnOn){
//            backGroundMusic = new LoopMusicThread("src/videos/bgm.wav");
//            backGroundMusic.start();
//        }
        scheduledExecutorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);
//            Thread thread = new Thread(task);
//            thread.start();
//
//            Thread.sleep(timeInterval);

    }

    //***********************
    //      Action ?????????
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // ?????????????????????
            timeCycleCount+=1;
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private boolean heroTimeCountAndNewCycleJudge() {
        heroCycleTime += timeInterval;
        if (heroCycleTime >= heroAircraftShootCycle && heroCycleTime - timeInterval < heroCycleTime) {
            // ?????????????????????
            heroCycleTime %= heroAircraftShootCycle;
            return true;
        } else {
            return false;
        }
    }

    private void enemyShootAction() {
        // TODO ????????????
        for(BaseEnemy enemy : enemyAircrafts){
            List<BaseBullet> bullets = enemy.executeShoot();
            if(bullets.size() != 0){
                enemyBullets.addAll(bullets);
            }
        }

    }
    // ????????????
    private void heroShootAction(){
        heroBullets.addAll(heroAircraft.executeShoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (BaseEnemy enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }

    private void propsMoveAction(){
        for (BaseProp prop : props){
            prop.forward();
        }
    }


    /**
     * ???????????????
     * 1. ??????????????????
     * 2. ????????????/????????????
     * 3. ??????????????????
     */
    private void crashCheckAction() {
        // TODO ????????????????????????
        for (BaseBullet enemybullet : enemyBullets) {
            if (enemybullet.notValid()) {
                continue;
            }
            if(heroAircraft.crash(enemybullet) && !heroAircraft.isImmune()){
                // ?????????????????????????????????
                // ??????????????????????????????
                heroAircraft.decreaseHp(enemybullet.getPower());
                enemybullet.vanish();
            }else if(heroAircraft.crash(enemybullet) && heroAircraft.isImmune()){
                enemybullet.vanish();
            }
        }
        // ????????????????????????
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (BaseEnemy enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // ????????????????????????????????????????????????
                    // ???????????????????????????????????????????????????
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // ??????????????????????????????
                    // ???????????????????????????
//                    if(musicTurnOn){
//                        new MusicThread("src/videos/bullet_hit.wav").start();
//                    }
                    if(musicTurnOn){
                        music_on(R.raw.bullet_hit);
                    }
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                    if (enemyAircraft.notValid()) {
                        if (enemyAircraft instanceof Boss){
                            System.out.println("Boss Flag = False");
                            bossFlag = false;
                            if(musicTurnOn){
                                System.out.println("Turn off boss bgm");
                                closeBossMusic();
//                                boss_bgm.release();
//                                boss_bgm = null;
                            }

                        }
                        List<BaseProp> prop = enemyAircraft.dropProp();
                        if(prop != null){
                            props.addAll(prop);
                        }
                    }
                }
                // ????????? ??? ?????? ??????????????????
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(heroAircraft.getHp());
                }
            }
        }

        // Todo: ?????????????????????????????????
        for (BaseProp prop : props){
            if (heroAircraft.crash(prop)){
//                if(musicTurnOn){
//                    new MusicThread("src/videos/get_supply.wav").start();
//                }
                if(musicTurnOn){
                    music_on(R.raw.get_supply);
                }
                if(prop instanceof PropBomb){
                    if(musicTurnOn){
                        music_on(R.raw.bomb_explosion);
                    }
                    for(BaseEnemy enemyAircraft:enemyAircrafts){
                        ((PropBomb) prop).addSubscriber(enemyAircraft);
                    }
                    for(BaseBullet enemyBullet:enemyBullets){
                        ((PropBomb) prop).addSubscriber((EnemyBullet)enemyBullet);
                    }
                }
                prop.propFunction(heroAircraft);
                prop.vanish();
            }
        }

    }

    /**
     * Boss????????????
     * ????????????????????????????????????boss????????????boss
     */
    public void createBossAction(){};

    public void getOtherUser(){};

    public void updateMyUser(){};

    public boolean otherUserOver(){
        return false;
    }

    public void closeSocket(){};

    /**
     * ????????????
     * 1. ?????????????????????
     * 2. ?????????????????????
     * 3. ?????????????????????
     * 4. ???????????????????????????
     * <p>
     * ????????????????????????????????????????????????
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        for(BaseEnemy enemy:enemyAircrafts){
            if(enemy.getHp()==0){
                score+=10;
            }
        }
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }





    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        new Thread(this).start();
        isGameOverFlag = false;
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        isGameOverFlag = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void run(){
        try {
            action();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
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
    }
    //***********************
    //      Paint ?????????
    //***********************

    public void paintBackground(Paint mPaint, Canvas canvas){
        //????????????????????????
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE, 0, backGroundTop-GameActivity.WINDOW_HEIGHT, mPaint);
        canvas.drawBitmap(ImageManager.BACKGROUND_IMAGE, 0, backGroundTop,mPaint);
    }

    public void draw(){
        //??????????????????
        canvas = surfaceHolder.lockCanvas();
        //?????????????????????

        if(surfaceHolder == null || canvas == null){
            return;
        }

        paintBackground(mPaint, canvas);
        backGroundTop+=5;
        if(backGroundTop >= MainActivity.WINDOW_HEIGHT){
            backGroundTop = 0;
        }
        // ?????????????????????????????????
        // ????????????????????????????????????
        paintImageWithPositionRevised(mPaint, enemyBullets);
        paintImageWithPositionRevised(mPaint, heroBullets);
        paintImageWithPositionRevised(mPaint, props);
        paintImageWithPositionRevised(mPaint, enemyAircrafts);

        //???????????????
        canvas.drawBitmap(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2, heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, mPaint);
        //????????????
        paintScoreAndLife(mPaint);

        paintOtherUser(mPaint,canvas);

        //??????
        surfaceHolder.unlockCanvasAndPost(canvas);

    }

    private void paintImageWithPositionRevised(Paint mPaint, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (int i = 0; i<objects.size();  i++) {
            Bitmap image = objects.get(i).getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            canvas.drawBitmap(image, objects.get(i).getLocationX() - image.getWidth()/2, objects.get(i).getLocationY() - image.getHeight() / 2, mPaint);

        }
    }

    private void paintScoreAndLife(Paint mPaint) {
        int x = 10;
        int y = 60;
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(80);
        mPaint.setTypeface(Typeface.SANS_SERIF);
        mPaint.setFakeBoldText(true);
//        mPaint.setFont(new Font("SansSerif", Font.BOLD, 22));
        canvas.drawText("SCORE:" + this.score, x, y, mPaint);
        y = y + 80;
        canvas.drawText("LIFE:" + this.heroAircraft.getHp(), x, y, mPaint);
    }

    public void paintOtherUser(Paint mPaint,Canvas canvas){};
    public int getBackGroundTop() {
        return backGroundTop;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return true;
    }

    public void bgm_music_on(){
        Runnable bgm_task = ()->{
            System.out.println("Music on");
            this.bgm = MediaPlayer.create(this.getContext(), R.raw.bgm);
            bgm.setLooping(true);
            bgm.start();
        };

        new Thread(bgm_task).run();
    }

    public void boss_music_on(){
    }

    public void music_on(int resid){
        MediaPlayer mp = MediaPlayer.create(this.getContext(),resid);
        mp.start();
        mp.setOnCompletionListener(mediaPlayer -> mp.release());
    }
}
