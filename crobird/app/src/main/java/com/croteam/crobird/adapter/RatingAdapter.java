package com.croteam.crobird.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.croteam.crobird.R;
import com.croteam.crobird.model.Review;

import java.util.ArrayList;

public class RatingAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Review> list;

    public RatingAdapter(Context context, ArrayList<Review> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Review getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bird_rating, parent, false);
        TextView tvName = itemView.findViewById(R.id.tv_name);
        TextView tvRating = itemView.findViewById(R.id.tv_rating);
        TextView tvContent = itemView.findViewById(R.id.tv_content);
        RatingBar ratingBar = itemView.findViewById(R.id.ratingBar);
        tvName.setText(list.get(position).getUsername());
        tvRating.setText(String.valueOf(list.get(position).getRating()));
        tvContent.setText(list.get(position).getContent());
        ratingBar.setRating(list.get(position).getRating());
        return itemView;
    }
}
