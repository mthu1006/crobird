package com.croteam.crobird;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.croteam.crobird.adapter.ViewPagerAdapter;
import com.croteam.crobird.fragment.MainFragment;
import com.croteam.crobird.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity {

    public ViewPager viewPager;
    private TabLayout tabLayout;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        setupViewPager();
    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainFragment(), "Dashboard");
//        adapter.addFragment(new BirdFragment(), "Bird");
        adapter.addFragment(new SettingFragment(), "Setting");
        viewPager.setAdapter(adapter);

        int[] icons = new int[]{R.drawable.ic_cro, R.drawable.ic_me};
        String[] titles = new String[]{"Cro", "Setting"};
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
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Double touch back button to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
