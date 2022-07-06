package com.example.myapplication.market;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.market.MarketItem;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<MarketItem> {
    public ItemAdapter(@NonNull Context context, int resource, @NonNull List<MarketItem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MarketItem item=getItem(position);//得到当前项的 Fruit 实例
        //为每一个子项加载设定的布局
        @SuppressLint("ViewHolder") View view= LayoutInflater.from(getContext()).inflate(R.layout.market_item,parent,false);
        //分别获取 image view 和 textview 的实例
        ImageView propImage =view.findViewById(R.id.prop_image);
        TextView propName =view.findViewById(R.id.prop_name);
        TextView propPrice=view.findViewById(R.id.prop_price);
        TextView propFunction=view.findViewById(R.id.prop_function);
        // 设置要显示的图片和文字
        propImage.setImageResource(item.getImageId());
        propName.setText(item.getName());
        propPrice.setText(item.getPrice());
        propFunction.setText(item.getFunction());
        return view;
    }

}
