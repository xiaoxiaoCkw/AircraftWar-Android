package com.example.myapplication.Prop;
import com.example.myapplication.basic.AbstractFlyingObject;
import com.example.myapplication.aircraft.HeroAircraft;

public abstract class BaseProp extends AbstractFlyingObject{
    public BaseProp(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }
    public abstract void propFunction(HeroAircraft heroAircraft);
}
