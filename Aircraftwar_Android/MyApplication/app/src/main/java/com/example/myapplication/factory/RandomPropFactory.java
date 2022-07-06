package com.example.myapplication.factory;

import java.util.Random;

/**产生道具工厂的简单工厂，随机产生
 * 不产生道具返回null
 */
public class RandomPropFactory {
    private Random propR = new Random();
    private float whichProp;
    private static float bloodPossibility = 0.15f;
    private static float bombPossibility = 0.1f;
    private static float bulletPossibility = 0.15f;
    private static float immunePossibility = 0.15f;

    public static void setBloodPossibility(float bloodPossibility) {
        RandomPropFactory.bloodPossibility = bloodPossibility;
    }

    public static void setBombPossibility(float bombPossibility) {
        RandomPropFactory.bombPossibility = bombPossibility;
    }

    public static void setBulletPossibility(float bulletPossibility) {
        RandomPropFactory.bulletPossibility = bulletPossibility;
    }

    public static void setImmunePossibility(float immunePossibility) {
        RandomPropFactory.immunePossibility = immunePossibility;
    }

    public PropFactory createPropFactory(){
        whichProp=propR.nextFloat();
        if(whichProp<=bloodPossibility){
            return new PropBloodFactory();
        }
        else if(whichProp <= bloodPossibility+bombPossibility){
            return new PropBombFactory();
        }
        else if(whichProp<=bloodPossibility+bombPossibility+bulletPossibility){
            return new PropBulletFactory();
        }
        else if(whichProp<=bloodPossibility+bombPossibility+bulletPossibility+immunePossibility){
            return new PropImmuneFactory();
        }
        else {
            return null;
        }
    }
}
