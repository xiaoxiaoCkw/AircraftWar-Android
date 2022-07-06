package com.example.myapplication.factory;

import com.example.myapplication.Prop.BaseProp;
import com.example.myapplication.Prop.PropBlood;

/**加血道具工厂*/
public class PropBloodFactory implements PropFactory{
    @Override
    public BaseProp createProp(int locationX, int locationY){
        return new PropBlood(locationX, locationY, 0, 10);
    }
}
