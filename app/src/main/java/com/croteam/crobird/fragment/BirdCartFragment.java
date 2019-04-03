package com.croteam.crobird.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.croteam.crobird.CroDetailActivity;
import com.croteam.crobird.MainActivity;
import com.croteam.crobird.R;
import com.croteam.crobird.adapter.BirdCartAdapter;
import com.croteam.crobird.adapter.ClickListener;
import com.croteam.crobird.component.BirdCartItemTouchHelper;
import com.croteam.crobird.model.User;
import com.croteam.crobird.uitls.AppConstants;
import com.croteam.crobird.uitls.AppDialogManager;
import com.croteam.crobird.uitls.DialogAcceptClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BirdCartFragment extends Fragment  implements BirdCartItemTouchHelper.RecyclerItemTouchHelperListener {


    public BirdCartFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    @BindView(R.id.btn_send_message)
    Button btnSendMessage;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.fab_filter)
    FloatingActionButton fabFilter;

    public BirdCartAdapter adapter;

    private Dialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_bird_cart, container, false);
        ButterKnife.bind(this, root);
        initDialogFilter();
        initViews();
        return root;
    }

    private void initViews(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        adapter = new BirdCartAdapter(getActivity(), MainActivity.cartList, new ClickListener() {
            @Override
            public void onItemClick(View v) {
                Intent intent = new Intent(getActivity(), CroDetailActivity.class);
                intent.putExtra(User.ID, MainActivity.cartList.get((int) v.getTag()).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v) {

            }
        }, new BirdCartAdapter.BirdCartClick() {
            @Override
            public void onSendMessageClick(int position) {

            }

            @Override
            public void onCheckedChange(int position, boolean isChecked) {

            }
        });
        rv.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new BirdCartItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rv);
        Log.d(AppConstants.TAG, "MainActivity.cartList size "+MainActivity.cartList.size());
        if(MainActivity.cartList.isEmpty()){
            tvEmpty.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }else {
            tvEmpty.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private void initDialogFilter(){
        dialog = AppDialogManager.onCreateCustomDialog(getActivity(), R.layout.dialog_bird_cart_fiter, new DialogAcceptClickListener() {
            @Override
            public void onAcceptClick(View v) {

            }

            @Override
            public void onCloseClick(View v) {

            }
        });
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

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
//        String name = MainActivity.cartList.get(position).getName();
        MainActivity.cartList.remove(position);
        adapter.notifyDataSetChanged();
        Snackbar.make(rv, "Removed from cart", Snackbar.LENGTH_LONG).show();
    }
}
