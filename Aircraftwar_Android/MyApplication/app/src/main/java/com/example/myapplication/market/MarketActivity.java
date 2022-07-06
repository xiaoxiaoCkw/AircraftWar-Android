package com.example.myapplication.market;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class MarketActivity extends AppCompatActivity {
    ListView marketView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);
        //道具列表
        marketView = findViewById(R.id.list_item);
        List<MarketItem> items = new ArrayList<>();
        MarketItem item1 = new MarketItem("加血","1积分","加30hp",R.drawable.prop_blood);
        items.add(item1);
        MarketItem item2 = new MarketItem("免疫","2积分","免疫5s",R.drawable.prop_immune);
        items.add(item2);
        ItemAdapter adapter=new ItemAdapter(MarketActivity.this,R.layout.market_item,items);
        marketView.setAdapter(adapter);
    }
}
