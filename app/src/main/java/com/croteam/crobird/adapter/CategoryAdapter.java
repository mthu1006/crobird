package com.croteam.crobird.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.croteam.crobird.R;
import com.croteam.crobird.model.Category;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Category> list;
    private int layoutId;

    public CategoryAdapter(Context context, ArrayList<Category> list, int layoutId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Category getItem(int position) {
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
//        icon.setImageResource(list.get(position).get());
        text.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getImg()).into(icon);
        return itemView;
    }
}
