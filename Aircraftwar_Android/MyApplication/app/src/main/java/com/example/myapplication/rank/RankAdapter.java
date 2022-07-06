package com.example.myapplication.rank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.dao.Record;

import java.util.List;

public class RankAdapter extends ArrayAdapter<Record> {
    public RankAdapter(@NonNull Context context, int resource, @NonNull List<Record> objects){
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        Record record = getItem(position);
        @SuppressLint("ViewHolder") View view= LayoutInflater.from(getContext()).inflate(R.layout.rank_list,parent,false);
        TextView name = view.findViewById(R.id.name);
        TextView rank = view.findViewById(R.id.rank);
        TextView score = view.findViewById(R.id.score);
        TextView time = view.findViewById(R.id.time);
        name.setText(record.getName());
        rank.setText(String.valueOf(position+1));
        score.setText(record.getScore());
        time.setText(record.getDate());
        return view;
    }



}
