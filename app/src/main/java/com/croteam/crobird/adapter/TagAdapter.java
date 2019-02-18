package com.croteam.crobird.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.croteam.crobird.R;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.MyViewHolder> {

    private String[] list;
    private Context context;
    private ClickListener clickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, address, phone;
        public ImageView img, status;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tv_name);
        }
    }


    public TagAdapter(Context context, String[] list, ClickListener clickListener) {
        this.list = list;
        this.context = context;
        this.clickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tag, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(list[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setTag(list[position]);
                clickListener.onItemClick(v);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                v.setTag(list[position]);
                clickListener.onItemLongClick(v);
                return true;
            }
        });

    }

//    private String getStoreName(String storeCode){
//        for (int i = 0; i< ((MainActivity)context).brandList.size(); i++)
//            if(((MainActivity)context).brandList.get(i).getId().equals(storeCode))
//                return ((MainActivity)context).brandList.get(i).getBrandName();
//        return null;
//    }

    @Override
    public int getItemCount() {
        return list.length;
    }
}
