package com.croteam.crobird.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.croteam.crobird.R;
import com.croteam.crobird.model.User;
import com.croteam.crobird.uitls.Validation;

import java.util.ArrayList;


public class BirdAdapter extends RecyclerView.Adapter<BirdAdapter.MyViewHolder> {

    private ArrayList<User> list;
    private Context context;
    private ClickListener clickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, rating, price, job;
        public RatingBar ratingBar;
        public RelativeLayout viewBackground, viewForeground;
        public ImageView img;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_name);
            job = (TextView) view.findViewById(R.id.tv_job);
            rating = (TextView) view.findViewById(R.id.tv_rating);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            price = (TextView) view.findViewById(R.id.tv_price);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
            img = (ImageView) view.findViewById(R.id.img_profile);
        }
    }


    public BirdAdapter(Context context, ArrayList<User> list, ClickListener clickListener) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bird, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final User user = list.get(position);

        holder.name.setText(user.getName());
        holder.job.setText(user.getJob());
        holder.ratingBar.setRating(user.getRating());
        holder.price.setText("$"+user.getPrice()+" USD/Hour");
//        holder.img.setImageResource(menu.getNum1());
        if(!Validation.checkNullOrEmpty(user.getImg()))
            Glide.with(context).load(user.getImg()).into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(position);
                clickListener.onItemClick(v);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.setTag(position);
                clickListener.onItemLongClick(v);
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
