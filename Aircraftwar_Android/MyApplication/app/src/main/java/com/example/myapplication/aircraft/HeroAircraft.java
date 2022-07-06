package com.example.myapplication.aircraft;

import com.example.myapplication.main.MainActivity;
import com.example.myapplication.application.ImageManager;
//import com.example.myapplication.application.Main;

import com.example.myapplication.strategy.StraightShoot;


/**
 * 英雄飞机，游戏玩家操控
 * @author hitsz
 */
public class HeroAircraft extends AbstractAircraft {

    /**单例模式创建英雄机*/

    private volatile static HeroAircraft heroAircraft;

    /**判断英雄机是否免疫伤害*/
    private boolean immune = false;

    /**
     * @param locationX 英雄机位置x坐标
     * @param locationY 英雄机位置y坐标
     * @param speedX 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param speedY 英雄机射出的子弹的基准速度（英雄机无特定速度）
     * @param hp    初始生命值
     * @param shootNum 英雄机射射击子弹的数量
     * @param power 英雄机射出的子弹的力量
     * @param direction    子弹方向
     */
    private HeroAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, int direction) {
        super(locationX, locationY, speedX, speedY, hp, shootNum, power, direction);
    }

    public boolean isImmune() {
        return immune;
    }

    public void setImmune(boolean immune) {
        this.immune = immune;
    }

    /**双重检查锁定实现单例的创建*/
    public static HeroAircraft getHeroAircraft(){
        if(heroAircraft == null){
            synchronized (HeroAircraft.class){
                if(heroAircraft == null){
                    heroAircraft = new HeroAircraft(MainActivity.WINDOW_WIDTH / 2,
                            MainActivity.WINDOW_HEIGHT - ImageManager.HERO_IMAGE.getHeight() ,
                            0, 0, 1500, 1, 30, -1);
                    heroAircraft.setShootStrategy(new StraightShoot("HeroAircraft"));
                }
            }
        }
        return heroAircraft;
    }

    /**
     * 实现加血功能
     */
    public void increaseHp(int increase){
        if (increase+hp>=maxHp){
            hp=maxHp;
        }
        else{
            hp=hp+increase;
        }
    }

    @Override
    public void forward() {
        // 英雄机由鼠标控制，不通过forward函数移动
    }


}
