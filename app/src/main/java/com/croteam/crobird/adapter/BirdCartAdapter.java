package com.croteam.crobird.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.croteam.crobird.MainActivity;
import com.croteam.crobird.R;
import com.croteam.crobird.database.UserHelper;
import com.croteam.crobird.model.BirdCart;
import com.croteam.crobird.model.User;
import com.croteam.crobird.uitls.Validation;

import java.util.ArrayList;


public class BirdCartAdapter extends RecyclerView.Adapter<BirdCartAdapter.MyViewHolder> {

    private ArrayList<BirdCart> list;
    private Context context;
    private ClickListener clickListener;
    private BirdCartClick birdCartClick;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, rating, price, job;
        public RatingBar ratingBar;
        public RelativeLayout viewBackground, viewForeground;
        public ImageView img, sendMessage;
        public CheckBox cb;


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
            sendMessage = (ImageView) view.findViewById(R.id.img_message);
            cb = view.findViewById(R.id.checkBox);
        }
    }


    public BirdCartAdapter(Context context, ArrayList<BirdCart> list, ClickListener clickListener, BirdCartClick birdCartClick) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
        this.birdCartClick = birdCartClick;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bird_cart, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        final BirdCart birdCart = list.get(position);
        User user = UserHelper.with((MainActivity)context).getUserById(birdCart.getBirđId());
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
        holder.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birdCartClick.onSendMessageClick(position);
            }
        });
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                birdCartClick.onCheckedChange(position, isChecked);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface BirdCartClick{
        void onSendMessageClick(int position);
        void onCheckedChange(int position, boolean isChecked);
    }
}
