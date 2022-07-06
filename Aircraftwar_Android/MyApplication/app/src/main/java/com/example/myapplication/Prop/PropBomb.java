package com.example.myapplication.Prop;
import com.example.myapplication.R;
import com.example.myapplication.aircraft.HeroAircraft;
import com.example.myapplication.application.GameView;
import com.example.myapplication.application.Subscriber;
//import com.example.myapplication.music.MusicThread;

import java.util.ArrayList;
import java.util.List;

public class PropBomb extends BaseProp{
    private List<Subscriber> subscribers = new ArrayList<>();

    public void addSubscriber(Subscriber subscriber){
        subscribers.add(subscriber);
    }

    public void removeSubscriber(Subscriber subscriber){
        subscribers.remove(subscriber);
    }

    public void remind(){
        for(Subscriber subscriber:subscribers){
            subscriber.update();
        }
    }

    public PropBomb(int locationX, int locationY, int speedX, int speedY){
        super(locationX, locationY, speedX, speedY);
    }
    @Override
    public void propFunction(HeroAircraft heroAircraft){
//        if(Game.isMusicTurnOn()){
//            new MusicThread("src/videos/bomb_explosion.wav").start();
//        }
        System.out.println("BombSupply active!");
        remind();
    }

}
