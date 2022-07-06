package com.example.myapplication.aircraft;


import com.example.myapplication.main.MainActivity;
import com.example.myapplication.Prop.BaseProp;
//import com.example.myapplication.application.Main;

import com.example.myapplication.application.Subscriber;
import com.example.myapplication.factory.PropFactory;
import com.example.myapplication.factory.RandomPropFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * 精英敌机
 * 可以射击
 *
 * @author hitsz
 */

public class EliteEnemy extends BaseEnemy implements Subscriber {

    public EliteEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, int direction) {
        super(locationX, locationY, speedX, speedY, hp, shootNum, power, direction);
    }


    @Override
    public void forward() {
        super.forward();
        // 判定 y 轴向下飞行出界
        if (locationY >= MainActivity.WINDOW_HEIGHT ) {
            vanish();
        }
    }

    @Override
    public List<BaseProp> dropProp(){
        RandomPropFactory randomPropFactory = new RandomPropFactory();
        PropFactory propFactory = randomPropFactory.createPropFactory();
        if(propFactory == null){
            return null;
        }
        List<BaseProp> prop = new LinkedList<>();
        prop.add(propFactory.createProp(this.getLocationX(), this.getLocationY()));
        return prop;
    }

    @Override
    public void update(){
        this.decreaseHp(this.getHp());
    }

}
