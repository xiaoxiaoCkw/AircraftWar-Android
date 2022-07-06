package com.example.myapplication.factory;

import com.example.myapplication.Prop.BaseProp;
import com.example.myapplication.Prop.PropImmune;

public class PropImmuneFactory implements PropFactory{
    @Override
    public BaseProp createProp(int locationX, int locationY){return new PropImmune(locationX,locationY,0,5);}
}
