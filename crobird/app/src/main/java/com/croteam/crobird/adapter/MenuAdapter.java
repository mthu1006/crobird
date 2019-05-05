package com.croteam.crobird.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.croteam.crobird.R;
import com.croteam.crobird.model.CommonClass;

import java.util.ArrayList;


public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private ArrayList<CommonClass> list;
    private Context context;
    private ClickListener clickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView img;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_name);
            img = (ImageView) view.findViewById(R.id.icon);
        }
    }


    public MenuAdapter(Context context, ArrayList<CommonClass> list, ClickListener clickListener) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final CommonClass menu = list.get(position);

        holder.name.setText(menu.getField1());
        holder.img.setImageResource(menu.getNum1());

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
