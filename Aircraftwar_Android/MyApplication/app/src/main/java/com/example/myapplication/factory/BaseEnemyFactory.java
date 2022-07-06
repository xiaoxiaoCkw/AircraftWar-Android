package com.example.myapplication.factory;

import com.example.myapplication.aircraft.BaseEnemy;

public abstract class BaseEnemyFactory {
    protected int hp = 100;
    protected int power = 30;

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void setPower(int power) {
        this.power = power;
    }


    public abstract  BaseEnemy createEnemy();

}
