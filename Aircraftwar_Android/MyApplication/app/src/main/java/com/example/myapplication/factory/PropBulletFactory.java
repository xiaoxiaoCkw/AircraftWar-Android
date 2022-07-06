package com.example.myapplication.factory;

import com.example.myapplication.Prop.BaseProp;
import com.example.myapplication.Prop.PropBullet;

/**火力道具工厂*/
public class PropBulletFactory implements PropFactory{
    @Override
    public BaseProp createProp(int locationX, int locationY){
        return new PropBullet(locationX, locationY, 0, 10);
    }
}
