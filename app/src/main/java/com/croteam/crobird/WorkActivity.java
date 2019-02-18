package com.croteam.crobird;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.croteam.crobird.adapter.BirdAdapter;
import com.croteam.crobird.adapter.ClickListener;
import com.croteam.crobird.adapter.ViewPagerAdapter;
import com.croteam.crobird.fragment.BirdFragment;
import com.croteam.crobird.fragment.CroFragment;
import com.croteam.crobird.model.User;
import com.croteam.crobird.uitls.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WorkActivity extends AppCompatActivity {

    public ViewPager viewPager;
    private TabLayout tabLayout;

    @BindView(R.id.edt_search)
    public EditText edtSearch;
    @BindView(R.id.rv)
    public RecyclerView rvBird;
    private ArrayList<User> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        ButterKnife.bind(this);
        String title = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(title);
        list = new ArrayList<>();
        initViews();
        initCro();
//        setupViewPager();
    }

    private void initViews(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        rvBird.setLayoutManager(mLayoutManager);
        rvBird.setItemAnimator(new DefaultItemAnimator());

    }

    private void initCro(){
        String[] firstNames = new String[]{"Nguyen", "Tran", "Le", "Lam", "Phan", "Pham", "Vo", "Vu", "Thai"};
        String[] middleNames = new String[]{"Van", "Hoang", "Thi", "Thu", "Thanh", "Minh", "Ngoc"};
        String[] lastNames = new String[]{"Thao", "Hieu", "Thu", "Mai", "Phuong", "Van", "Hang", "Han", "Nhu", };
        for(int i=0; i<100; i++){
            User user = new User();
            user.setName(firstNames[Utils.randomWithRange(0, firstNames.length-1)]+" "+middleNames[Utils.randomWithRange(0, middleNames.length-1)]
                    +" " +lastNames[Utils.randomWithRange(0, lastNames.length-1)]);
            user.setGender(Utils.randomWithRange(0,1)==1);
            user.setDob("01/01/1990");
            user.setPrice(Utils.randomWithRange(5, 30));
            user.setRating(Utils.randomWithRange(1, 5));
            list.add(user);
        }
        BirdAdapter adapter = new BirdAdapter(this, list, new ClickListener() {
            @Override
            public void onItemClick(View v) {

            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
        rvBird.setAdapter(adapter);

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
}
