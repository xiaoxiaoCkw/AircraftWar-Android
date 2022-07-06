package com.example.myapplication.aircraft;

import com.example.myapplication.basic.AbstractFlyingObject;
import com.example.myapplication.bullet.BaseBullet;
import com.example.myapplication.strategy.NonShootStrategy;
import com.example.myapplication.strategy.ShootStrategy;

import java.util.List;

public class AbstractAircraft extends AbstractFlyingObject {
    /**
     * 生命值
     */
    protected int maxHp;
    protected int hp;
    /**
     * 子弹射击
     */
    protected int shootNum;
    protected int power;
    protected int direction;
    protected ShootStrategy strategy = new NonShootStrategy();

    public AbstractAircraft(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, int direction) {
        super(locationX, locationY, speedX, speedY);
        this.hp = hp;
        this.maxHp = hp;
        this.shootNum = shootNum;
        this.power = power;
        this.direction = direction;
    }

    public void decreaseHp(int decrease){
        hp -= decrease;
        if(hp <= 0){
            hp=0;
            vanish();
        }
    }

    public int getHp() {
        return hp;
    }

    public void setShootNum(int num){
        this.shootNum = num;
    }

    public int getShootNum() {
        return shootNum;
    }

    public int getPower() {
        return power;
    }

    public int getDirection() {
        return direction;
    }

    /**
     * 切换策略
     */
    public void setShootStrategy(ShootStrategy strategy){
        this.strategy = strategy;
    }

    /**
     * 飞机射击方法，通过策略实施
     */
    public List<BaseBullet> executeShoot(){
        return strategy.shoot(this);
    }
}
