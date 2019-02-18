package com.croteam.crobird.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.croteam.crobird.R;
import com.croteam.crobird.model.CommonClass;

import java.util.ArrayList;

public class CommonAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CommonClass> list;
    private int layoutId;

    public CommonAdapter(Context context, ArrayList<CommonClass> list, int layoutId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CommonClass getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        ImageView icon = itemView.findViewById(R.id.icon);
        TextView text = itemView.findViewById(R.id.text);
        icon.setImageResource(list.get(position).getNum1());
        text.setText(list.get(position).getField1());
        return itemView;
    }
}
