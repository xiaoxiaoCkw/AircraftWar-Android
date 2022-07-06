package com.example.myapplication.Prop;

import com.example.myapplication.aircraft.HeroAircraft;
/**
 * 免疫道具，免疫5秒敌机子弹伤害
 */
public class PropImmune extends BaseProp{
    private static Integer immuneCount = 0;
    private static boolean jump = false;
    public PropImmune(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }

    public static void setJump(){
        jump = true;
    }
    @Override
    public void propFunction(HeroAircraft heroAircraft){
        synchronized (immuneCount){
            immuneCount+=1;
        }
        System.out.printf("免疫道具叠加%d个,一共持续%d秒\n",immuneCount,immuneCount*5);
        Runnable r = ()->{
            heroAircraft.setImmune(true);
            while (immuneCount>0){
                try {
                    Thread.sleep(5000);
                    synchronized (immuneCount){
                        immuneCount-=1;
                    }
                    if (jump){
                        break;
                    }
                    System.out.printf("免疫道具已经消耗掉1个，目前%d个\n",immuneCount);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            heroAircraft.setImmune(false);
        };
        if(immuneCount==1){
            new Thread(r).start();
        }
    }
}
