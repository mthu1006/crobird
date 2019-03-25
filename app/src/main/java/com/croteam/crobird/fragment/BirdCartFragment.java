package com.croteam.crobird.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.croteam.crobird.MainActivity;
import com.croteam.crobird.R;
import com.croteam.crobird.adapter.BirdAdapter;
import com.croteam.crobird.adapter.ClickListener;
import com.croteam.crobird.uitls.AppConstants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BirdCartFragment extends Fragment {


    public BirdCartFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    public BirdAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_bird_cart, container, false);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    private void initViews(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter = new BirdAdapter(getActivity(), MainActivity.cartList, new ClickListener() {
            @Override
            public void onItemClick(View v) {

            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
        rv.setAdapter(adapter);
        Log.d(AppConstants.TAG, "MainActivity.cartList size "+MainActivity.cartList.size());
        if(MainActivity.cartList.isEmpty()){
            tvEmpty.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }else {
            tvEmpty.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(AppConstants.TAG, "MainActivity.cartList size onResume "+MainActivity.cartList.size());
        if(MainActivity.cartList.isEmpty()){
            tvEmpty.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }else {
            tvEmpty.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            if(adapter!=null)
                adapter.notifyDataSetChanged();
        }

    }
}
