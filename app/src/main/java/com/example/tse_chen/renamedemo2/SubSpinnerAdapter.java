package com.example.tse_chen.renamedemo2;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Tse_Chen on 2016/3/31.
 */
public class SubSpinnerAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    String[] TUESubItem;
    TextView textView;


    public SubSpinnerAdapter(Context onItemSelectedListener, String[] tueSubItem) {
        layoutInflater = LayoutInflater.from(onItemSelectedListener);
        this.TUESubItem = tueSubItem;
    }


    @Override
    public int getCount() {
        return TUESubItem.length;
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
        convertView = layoutInflater.inflate(R.layout.spinneritem,parent,false);
        textView = (TextView) convertView.findViewById(R.id.spinnerText);
        textView.setText(TUESubItem[position]);
        return convertView;
    }
}
