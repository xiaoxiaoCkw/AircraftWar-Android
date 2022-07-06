package com.example.myapplication.aircraft;

import com.example.myapplication.application.GameView;
import com.example.myapplication.Prop.BaseProp;
import com.example.myapplication.application.Subscriber;
import com.example.myapplication.factory.BossRandomPropFactory;
import com.example.myapplication.factory.PropFactory;
//import com.example.myapplication.music.LoopMusicThread;

import java.util.LinkedList;
import java.util.List;


public class Boss extends BaseEnemy implements Subscriber {

//    private LoopMusicThread bossMusic = null;
    private GameView game;

    public Boss(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, int direction){
        super(locationX, locationY, speedX, speedY, hp, shootNum, power, direction);
    }

//    public void setBossMusic(LoopMusicThread bossMusic) {
//        this.bossMusic = bossMusic;
//    }

    public void setGame(GameView game) {
        this.game = game;
    }

    /**
     * boss随机掉落两个道具,一前一后
     */
    @Override
    public List<BaseProp> dropProp(){
        BossRandomPropFactory randomPropFactory = new BossRandomPropFactory();
        PropFactory propFactory1 = randomPropFactory.createPropFactory();
        PropFactory propFactory2 = randomPropFactory.createPropFactory();
        List<BaseProp> prop = new LinkedList<>();
        prop.add(propFactory1.createProp(this.getLocationX(), this.getLocationY()+20));
        prop.add(propFactory2.createProp(this.getLocationX(), this.getLocationY()));
        return prop;
    }

    @Override
    public void update(){
        this.decreaseHp(20);
        System.out.println("炸弹对boss造成了20伤害！");
        if(getHp()<=0){
//            if(bossMusic!=null) {
//                bossMusic.setStop();
//                bossMusic.setJump();
//            }
            game.setBossFlag(false);
        }
    }

}
