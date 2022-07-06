package com.example.myapplication.aircraft;

import com.example.myapplication.main.MainActivity;
import com.example.myapplication.Prop.BaseProp;
//import com.example.myapplication.application.Main;
import com.example.myapplication.application.Subscriber;

import java.util.List;

/**
 * 普通敌机
 * 不可射击
 *
 * @author hitsz
 */
public class MobEnemy extends BaseEnemy implements Subscriber {

    public MobEnemy(int locationX, int locationY, int speedX, int speedY, int hp, int shootNum, int power, int direction) {
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
    public List<BaseProp> dropProp(){return null;}

    @Override
    public void update(){
        this.decreaseHp(this.getHp());
    }
}
