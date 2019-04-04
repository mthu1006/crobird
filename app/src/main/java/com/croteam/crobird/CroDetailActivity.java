package com.croteam.crobird;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.croteam.crobird.adapter.ViewPagerAdapter;
import com.croteam.crobird.database.UserHelper;
import com.croteam.crobird.fragment.BirdRatingFragment;
import com.croteam.crobird.fragment.BirdResumeFragment;
import com.croteam.crobird.model.BirdCart;
import com.croteam.crobird.model.User;
import com.croteam.crobird.uitls.AppConstants;
import com.croteam.crobird.uitls.Validation;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CroDetailActivity extends AppCompatActivity {

    @BindView(R.id.backdrop)
    ImageView imgBackdrop;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.btn_add)
    FloatingActionButton btnAdd;

    public ViewPager viewPager;
    private TabLayout tabLayout;
    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cro_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        initUser();
        setupViewPager();
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BirdResumeFragment(), "Résume");
//        adapter.addFragment(new BirdFragment(), "Bird");
        adapter.addFragment(new BirdRatingFragment(), "Rating");
        viewPager.setAdapter(adapter);

        int[] icons = new int[]{R.drawable.ic_me, android.R.drawable.star_off};
        String[] titles = new String[]{"Résume", "Rating"};
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

    private void initUser(){
        final Calendar calendar = Calendar.getInstance();
        Log.d(AppConstants.TAG, "getIntent().getStringExtra(User.ID) "+getIntent().getStringExtra(User.ID));
        user = UserHelper.with(this).getUserById(getIntent().getStringExtra(User.ID));
        collapsingToolbarLayout.setTitle(user.getName());
        if(!Validation.checkNullOrEmpty(user.getImg()))
            Glide.with(this).load(user.getImg()).dontAnimate().dontTransform().into(imgBackdrop);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BirdCart birdCart = new BirdCart(BirdCart.class.getSimpleName()+calendar.getTime(), MainActivity.user.getId(), user.getId(), calendar.getTime());
                if(!MainActivity.cartList.contains(birdCart)) {
                    MainActivity.cartList.add(birdCart);
                    Log.d(AppConstants.TAG, "MainActivity.cartList size "+MainActivity.cartList.size());
                    Snackbar snackbar = Snackbar
                            .make(btnAdd, user.getName() + " is added to cart!", Snackbar.LENGTH_LONG);

                    snackbar.setActionTextColor(Color.GREEN);
                    snackbar.show();
                }else {
                    Snackbar.make(btnAdd, user.getName() + "has already added to cart", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

}
