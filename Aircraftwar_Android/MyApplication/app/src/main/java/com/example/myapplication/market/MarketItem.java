package com.example.myapplication.market;

public class MarketItem {
    private String name;
    private String price;
    private String function;
    private int imageId;

    public MarketItem(String name, String price, String function, int id){
        this.name = name;
        this.function = function;
        this.price = price;
        this.imageId = id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getFunction() {
        return function;
    }

    public int getImageId() {
        return imageId;
    }
}
