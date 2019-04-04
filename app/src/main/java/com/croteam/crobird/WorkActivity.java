package com.croteam.crobird;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.croteam.crobird.adapter.BirdAdapter;
import com.croteam.crobird.adapter.ClickListener;
import com.croteam.crobird.adapter.ViewPagerAdapter;
import com.croteam.crobird.component.RecyclerItemTouchHelper;
import com.croteam.crobird.database.RealmController;
import com.croteam.crobird.database.UserHelper;
import com.croteam.crobird.fragment.BirdFragment;
import com.croteam.crobird.fragment.CroFragment;
import com.croteam.crobird.model.BirdCart;
import com.croteam.crobird.model.Category;
import com.croteam.crobird.model.User;
import com.croteam.crobird.uitls.AppConstants;
import com.croteam.crobird.uitls.Validation;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class WorkActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    public ViewPager viewPager;
    private TabLayout tabLayout;

    @BindView(R.id.edt_search)
    public EditText edtSearch;
    @BindView(R.id.rv)
    public RecyclerView rvBird;
    private ArrayList<User> list;
    private ArrayList<User> listSearch;
    private String categoryName;
    private BirdAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        ButterKnife.bind(this);
        categoryName = getIntent().getStringExtra(Category.NAME);
        getSupportActionBar().setTitle(categoryName);
        initViews();
        initData();
//        setupViewPager();
    }

    private void performSearch(String key){
        listSearch.clear();
        if(!Validation.checkNullOrEmpty(key)) {
            RealmResults<User> results = UserHelper.with(this).searchUserByCategory(categoryName, key);
            for (User obj : results) {
                listSearch.add(obj);
            }
            adapter.notifyDataSetChanged();
        }else {
            listSearch.addAll(list);
            adapter.notifyDataSetChanged();
        }

    }

    private void initViews(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        rvBird.setLayoutManager(mLayoutManager);
        rvBird.setItemAnimator(new DefaultItemAnimator());

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.d(AppConstants.TAG, "search");
                    performSearch(edtSearch.getText().toString());
                    return true;
                }
                return false;
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Validation.checkNullOrEmpty(edtSearch.getText().toString())){
                    listSearch.clear();
                    listSearch.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initData(){
        list = new ArrayList<>();
        listSearch = new ArrayList<>();
        Log.d(AppConstants.TAG, "categoryName " + categoryName);
        RealmResults<Object> results = RealmController.with(this).queryObjects(User.class, User.JOB, categoryName);
        Log.d(AppConstants.TAG, "results length " + results.size());
        for(Object obj: results){
            list.add((User)obj);
        }
        listSearch.clear();
        listSearch.addAll(list);

        adapter = new BirdAdapter(this, listSearch, new ClickListener() {
            @Override
            public void onItemClick(View v) {
                Intent intent = new Intent(WorkActivity.this, CroDetailActivity.class);
                intent.putExtra(User.ID, list.get((int)v.getTag()).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
        rvBird.setAdapter(adapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rvBird);
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CroFragment(), "Cro");
//        adapter.addFragment(new BirdFragment(), "Bird");
        adapter.addFragment(new BirdFragment(), "Bird");
        viewPager.setAdapter(adapter);

        int[] icons = new int[]{R.drawable.ic_cro, R.drawable.ic_bird};
        String[] titles = new String[]{"Cro", "Bird"};
        for (int i = 0; i < icons.length; i++) {
            RelativeLayout tabOne = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.item_tab, null);
            TextView tv = (TextView) tabOne.findViewById(R.id.tabTitle);
            ImageView img = (ImageView) tabOne.findViewById(R.id.imgTab);
            if (i == 0) {
                tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                img.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            }
            tv.setText(titles[i]);
            img.setImageResource(icons[i]);
            tabLayout.getTabAt(i).setCustomView(tabOne);

        }

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {

                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        View tabView = tab.getCustomView();
                        TextView tab_label = (TextView) tabView.findViewById(R.id.tabTitle);
                        ImageView tab_icon = (ImageView) tabView.findViewById(R.id.imgTab);

                        tab_label.setTextColor(getResources().getColor(R.color.colorPrimary));
                        tab_icon.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);

                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                        super.onTabUnselected(tab);
                        View tabView = tab.getCustomView();
                        TextView tab_label = (TextView) tabView.findViewById(R.id.tabTitle);
                        ImageView tab_icon = (ImageView) tabView.findViewById(R.id.imgTab);

                        tab_label.setTextColor(getResources().getColor(android.R.color.darker_gray));
                        tab_icon.setColorFilter(getResources().getColor(android.R.color.transparent), PorterDuff.Mode.DST_OVER);
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                        super.onTabReselected(tab);
                    }
                }
        );
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        final Calendar calendar = Calendar.getInstance();
        BirdCart birdCart = new BirdCart(BirdCart.class.getSimpleName()+calendar.getTime(), MainActivity.user.getId(), listSearch.get(position).getId(), calendar.getTime());
        String name = listSearch.get(viewHolder.getAdapterPosition()).getName();
        if(!MainActivity.cartList.contains(birdCart)) {
            MainActivity.cartList.add(birdCart);
            Log.d(AppConstants.TAG, "MainActivity.cartList size "+MainActivity.cartList.size());
            Snackbar snackbar = Snackbar
                    .make(rvBird, name + " is added to cart!", Snackbar.LENGTH_LONG);

            snackbar.setActionTextColor(Color.GREEN);
            snackbar.show();
            listSearch.remove(position);
            adapter.notifyDataSetChanged();
        }else {
            Snackbar.make(rvBird, name + "has already added to cart", Snackbar.LENGTH_LONG).show();
        }
    }
}
