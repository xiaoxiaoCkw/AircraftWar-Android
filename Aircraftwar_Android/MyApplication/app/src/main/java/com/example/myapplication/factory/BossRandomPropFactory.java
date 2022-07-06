package com.example.myapplication.factory;

import java.util.Random;

/**针对BOSS产生道具工厂的简单工厂，随机产生
 */
public class BossRandomPropFactory {
    private Random propR = new Random();
    private int whichProp;
    public PropFactory createPropFactory(){
        whichProp=propR.nextInt(100);
        if(whichProp < 25){
            return new PropBloodFactory();
        }
        else if(whichProp < 50){
            return new PropBombFactory();
        }
        else if(whichProp < 75){
            return new PropImmuneFactory();
        }
        else{
            return new PropBulletFactory();
        }
    }
}
