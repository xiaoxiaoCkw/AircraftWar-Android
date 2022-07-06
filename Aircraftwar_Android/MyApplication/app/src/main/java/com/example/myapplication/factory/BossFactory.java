package com.example.myapplication.factory;

import com.example.myapplication.main.MainActivity;
import com.example.myapplication.aircraft.Boss;
import com.example.myapplication.aircraft.BaseEnemy;
import com.example.myapplication.application.ImageManager;
//import com.example.myapplication.application.Main;
import com.example.myapplication.strategy.ScatteringShoot;

/**Boss敌机工厂*/
public class BossFactory extends BaseEnemyFactory{

    @Override
    public BaseEnemy createEnemy(){
        BaseEnemy boss = new Boss((int) ( Math.random() * (MainActivity.WINDOW_WIDTH - ImageManager.BOSS_IMAGE.getWidth()))*1,
                (int) (ImageManager.BOSS_IMAGE.getHeight()/2)*1,
                5,
                0,
                 hp,
                3,
                power,
                1);
        boss.setShootStrategy(new ScatteringShoot("Enmey"));
        return boss;
    };
}
