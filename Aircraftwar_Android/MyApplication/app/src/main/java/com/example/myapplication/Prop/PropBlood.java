package com.example.myapplication.Prop;
import com.example.myapplication.aircraft.HeroAircraft;

public class PropBlood extends BaseProp{
    public PropBlood(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }

    @Override
    public void propFunction(HeroAircraft heroAircraft){
        System.out.println("BloodSupply active!");
        heroAircraft.increaseHp(30);
    }
}
