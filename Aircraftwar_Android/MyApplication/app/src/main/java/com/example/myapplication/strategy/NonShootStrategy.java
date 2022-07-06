package com.example.myapplication.strategy;

import com.example.myapplication.aircraft.AbstractAircraft;
import com.example.myapplication.bullet.BaseBullet;

import java.util.ArrayList;
import java.util.List;
public class NonShootStrategy implements ShootStrategy{
    public List<BaseBullet> shoot(AbstractAircraft aircraft){
        return new ArrayList<>();
    }
}
