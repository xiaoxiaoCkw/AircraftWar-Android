package com.example.myapplication.strategy;

import com.example.myapplication.aircraft.AbstractAircraft;
import com.example.myapplication.bullet.BaseBullet;

import java.util.List;

public interface ShootStrategy {
    public List<BaseBullet> shoot(AbstractAircraft aircraft);
}
