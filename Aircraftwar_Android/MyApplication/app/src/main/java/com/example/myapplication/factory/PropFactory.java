package com.example.myapplication.factory;

import com.example.myapplication.Prop.BaseProp;

public interface PropFactory {
    public BaseProp createProp(int locationX, int locationY);
}
