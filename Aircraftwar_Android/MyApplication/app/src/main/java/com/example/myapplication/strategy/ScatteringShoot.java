package com.example.myapplication.strategy;

import com.example.myapplication.aircraft.AbstractAircraft;
import com.example.myapplication.bullet.BaseBullet;
import com.example.myapplication.bullet.EnemyBullet;
import com.example.myapplication.bullet.HeroBullet;

import java.util.LinkedList;
import java.util.List;
public class ScatteringShoot implements ShootStrategy{
    private String aircraftType;

    public ScatteringShoot(String type){
        this.aircraftType = type;
    }

    /**
     * 散射策略
     * @return List<BaseBullet>
     */
    @Override
    public List<BaseBullet> shoot(AbstractAircraft aircraft){
        List<BaseBullet> res = new LinkedList<>();
        int x = aircraft.getLocationX();
        int y = aircraft.getLocationY() + aircraft.getDirection()*2;
        int bulletSpeedX = 5;
        int bulletSpeedY = aircraft.getSpeedY() + aircraft.getDirection()*10;
        int shootNum = aircraft.getShootNum();
        BaseBullet abstractBullet;
        for(int i=0; i<aircraft.getShootNum(); i++){
            // 子弹发射位置相对飞机位置向前偏移
            // 多个子弹横向分散
            if(i < shootNum/2){
                abstractBullet = createBullet(x + (i*2 - shootNum + 1)*10, y, -bulletSpeedX, bulletSpeedY, aircraft.getPower());
            }
            else if((shootNum & 1) ==1 && (i==shootNum/2)){
                //奇数个子弹时最中间的子弹直射
                abstractBullet = createBullet(x + (i*2 - shootNum + 1)*10, y, 0, bulletSpeedY, aircraft.getPower());
            }
            else {
                abstractBullet = createBullet(x + (i*2 - shootNum + 1)*10, y, bulletSpeedX, bulletSpeedY, aircraft.getPower());
            }
            res.add(abstractBullet);
        }
        return res;
    }

    private  BaseBullet createBullet(int x, int y, int speedX, int speedY, int power){
        if(aircraftType=="HeroAircraft"){
            return new HeroBullet(x, y, speedX, speedY, power);
        }
        else{
            return new EnemyBullet(x, y, speedX, speedY, power);
        }
    }
}
