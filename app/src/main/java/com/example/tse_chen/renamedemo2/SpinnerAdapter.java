package com.example.tse_chen.renamedemo2;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Tse_Chen on 2016/3/31.
 */
public class SpinnerAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    String[] TUEItem;
    TextView textView;


    public SpinnerAdapter(MainActivity mainActivity,String[] TUEItem) {
        layoutInflater=LayoutInflater.from(mainActivity);
        this.TUEItem=TUEItem;
        System.out.println(TUEItem);
    }



    @Override
    public int getCount() {
        return TUEItem.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.spinneritem, parent,false);
        textView = (TextView) convertView.findViewById(R.id.spinnerText);
        textView.setText(TUEItem[position]);
        return convertView;
    }
}
