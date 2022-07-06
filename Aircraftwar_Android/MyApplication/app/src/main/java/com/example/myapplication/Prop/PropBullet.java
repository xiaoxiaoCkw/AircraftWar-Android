package com.example.myapplication.Prop;
import com.example.myapplication.aircraft.HeroAircraft;
import com.example.myapplication.strategy.ScatteringShoot;
import com.example.myapplication.strategy.StraightShoot;

public class PropBullet extends BaseProp{
    private static Integer bombCount = 0;
    private static boolean jump = false;
    public PropBullet(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }

    public static void setJump(){
        jump = true;
    }

    @Override
    public void propFunction(HeroAircraft heroAircraft){
        synchronized (bombCount){
            bombCount+=1;
        }
        System.out.printf("火力道具叠加%d个,一共持续%d秒\n",bombCount,bombCount*5);
        //新建火力线程
        Runnable r = ()->{
            heroAircraft.setShootNum(3);
            heroAircraft.setShootStrategy(new ScatteringShoot("HeroAircraft"));
            while (PropBullet.bombCount>0){
                try {
                    Thread.sleep(5000);
                    synchronized (bombCount){
                        bombCount-=1;
                    }
                    if(jump){
                        break;
                    }
                    System.out.printf("火力道具已经消耗掉1个，目前%d个\n",bombCount);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            heroAircraft.setShootNum(1);
            heroAircraft.setShootStrategy(new StraightShoot("HeroAircraft"));
        };
        if(bombCount==1){
            new Thread(r).start();
        }
    }
}
