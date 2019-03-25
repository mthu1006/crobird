package com.croteam.crobird;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.croteam.crobird.adapter.BirdAdapter;
import com.croteam.crobird.adapter.ClickListener;
import com.croteam.crobird.database.UserHelper;
import com.croteam.crobird.model.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class SearchResultActivity extends AppCompatActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.tv_empty)
    TextView tvEmpty;
    ArrayList<User> list;
    BirdAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ButterKnife.bind(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(mLayoutManager);
        rv.setItemAnimator(new DefaultItemAnimator());
        list = new ArrayList<>();
        adapter = new BirdAdapter(this, list, new ClickListener() {
            @Override
            public void onItemClick(View v) {

            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
        rv.setAdapter(adapter);
        String key = getIntent().getStringExtra(User.SEARCH_KEY);
        performSearch(key);
    }

    private void performSearch(String key){
        list.clear();
        RealmResults<User> results = UserHelper.with(this).searchUser(key);
        if(!results.isEmpty()) {
            for (User obj : results) {
                list.add(obj);
            }
            adapter.notifyDataSetChanged();
            rv.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
        }else {
            tvEmpty.setText("No data with " +'"'+key+'"');
            rv.setVisibility(View.GONE);
            tvEmpty.setVisibility(View.VISIBLE);
        }
    }
}
