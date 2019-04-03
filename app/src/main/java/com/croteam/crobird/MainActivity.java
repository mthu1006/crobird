package com.croteam.crobird;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.croteam.crobird.adapter.ViewPagerAdapter;
import com.croteam.crobird.database.RealmController;
import com.croteam.crobird.database.UserHelper;
import com.croteam.crobird.fragment.BirdCartFragment;
import com.croteam.crobird.fragment.MainFragment;
import com.croteam.crobird.fragment.SettingFragment;
import com.croteam.crobird.model.Category;
import com.croteam.crobird.model.CommonClass;
import com.croteam.crobird.model.Rating;
import com.croteam.crobird.model.User;
import com.croteam.crobird.uitls.AppConstants;
import com.croteam.crobird.uitls.Prefs;
import com.croteam.crobird.uitls.Utils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.internal.Util;

public class MainActivity extends AppCompatActivity {

    public ViewPager viewPager;
    private TabLayout tabLayout;
    boolean doubleBackToExitPressedOnce = false;
    public User user;
    FirebaseFirestore db;

    public static ArrayList<User> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        db = FirebaseFirestore.getInstance();
        cartList = new ArrayList<>();
        user = UserHelper.with(this).getUserByPhone(Prefs.with(this).getString(AppConstants.PHONE_NUMBER));
        initCatogories();
        initCro();
        setupViewPager();

    }

    private void initCatogories(){
        if(!RealmController.with(this).getRealm().isInTransaction()) RealmController.with(this).getRealm().beginTransaction();
        String[] names = {"Engineering", "Cleansing", "Teaching", "Saling", "Marketing", "Publishing", "Farming", "Computing", "Healthy caring"};
        for(int i=0; i<names.length; i++){
            Category category = new Category(String.valueOf(i), names[i], "https://image.flaticon.com/icons/png/512/190/190684.png");
            CommonClass c9 = new CommonClass(names[i], R.drawable.ic_engineer);
            RealmController.with(this).getRealm().copyToRealmOrUpdate(category);
        }
        RealmController.with(this).getRealm().commitTransaction();
    }

    private void initCro(){
        if(!RealmController.with(this).getRealm().isInTransaction()) RealmController.with(this).getRealm().beginTransaction();
        String[] names = {"Engineering", "Cleansing", "Teaching", "Saling", "Marketing", "Publishing", "Farming", "Computing", "Healthy caring"};
        String[] firstNames = new String[]{"Nguyen", "Tran", "Le", "Lam", "Phan", "Pham", "Vo", "Vu", "Thai"};
        String[] middleNames = new String[]{"Van", "Hoang", "Thi", "Thu", "Thanh", "Minh", "Ngoc"};
        String[] lastNames = new String[]{"Thao", "Hieu", "Thu", "Mai", "Phuong", "Van", "Hang", "Han", "Nhu", };
        String[] comments = new String[]{"Hard working", "Great!", "Not bad", "Not good", "Very good", "Very bad", "Terrible"};
        ArrayList<String> avatars = new ArrayList<>();
        avatars.add("https://i.pinimg.com/originals/72/eb/b6/72ebb6e15a76ac319e7275d8cb2d6626.jpg");
        avatars.add("http://anhbiafb.com/uploads/images/thumb/50-avatar-ve-tho-bay-mau-sieu-de-thuong-va-cuc-cute-luon-1502290505-20.jpg");
        avatars.add("https://fiverr-res.cloudinary.com/images/t_main1,q_auto,f_auto/gigs/117796916/original/63535411336d226c2a23e0caea4c8e2337fc6cae/draw-a-cute-and-nice-avatar-or-portrait-for-you.png");
        avatars.add("https://i.pinimg.com/originals/51/43/08/5143089ed7e98f7f173b5c26d85ed3e5.jpg");
        avatars.add("https://foreverwithsandara.files.wordpress.com/2015/11/dara-instagram10.jpg?w=665&h=665");
        avatars.add("https://i.pinimg.com/originals/32/b5/73/32b573203d9c069ca10bfe6b749eda00.jpg");
        avatars.add("https://66.media.tumblr.com/2826fe9f812d5947be9ab1b6d0cd344d/tumblr_owt7vrYWYG1wvpa5ho1_400.jpg");
        avatars.add("https://img.heypik.com/png-vector/20190122/psd-girl-white-cute-cartoon-fresh-korean-couple-avatar-character-heypik-8EU44R3.jpg?x-oss-process=image/quality,q_70/watermark,image_c2h1aXlpbl9uZXcucG5n,g_center");
        avatars.add("https://png.pngtree.com/element_origin_min_pic/16/10/30/7e08b169602a6051b66c166fadd9f355.jpg");
        avatars.add("https://media.tenor.com/images/493390d2782257f57bd23b91538affb3/raw");
        avatars.add("https://images.pexels.com/photos/1073567/pexels-photo-1073567.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        avatars.add("https://otofun.org/wp-content/uploads/2018/12/4-cung-hoang-dao-trai-qua-thang-cuoi-nam-hanh-phuc-yen-binh.png?w=1400");
        float[] ratings = new float[]{4.5f, 4f, 3.5f, 2f, 5f, 1f, 0.5f };
        for(int i=0; i<100; i++){
            User user = new User();
            user.setId(String.valueOf(i));
            user.setName(firstNames[Utils.randomWithRange(0, firstNames.length-1)]+" "+middleNames[Utils.randomWithRange(0, middleNames.length-1)]
                    +" " +lastNames[Utils.randomWithRange(0, lastNames.length-1)]);
            user.setGender(Utils.randomWithRange(0,1)==1);
            user.setPhone("09"+Utils.randomWithRange(10000000, 99999999));
            user.setEmail(user.getName().toLowerCase().replace(" ","")+"@gmail.com");
            user.setDob("01/01/1990");
            user.setJob(names[Utils.randomWithRange(0, names.length-1)]);
            user.setPrice(Utils.randomWithRange(5, 30));
            user.setImg(avatars.get(Utils.randomWithRange(0, avatars.size()-1)));
            LatLng latLng = Utils.getLocation(user.getLat(), user.getLng(), 2);
            user.setLat(latLng.latitude);
            user.setLng(latLng.longitude);
            user.setAddress(Utils.getAddressFromLatlng(this, latLng.latitude, latLng.longitude));
            int count = Utils.randomWithRange(5, 20);
            float rate = 0;
            for(int r = 0; r<count; r++) {
                Rating rating = new Rating();
                rating.setId(String.valueOf(r)+String.valueOf(i));
                rating.setBirdId(String.valueOf(i));
                rating.setUsername(firstNames[Utils.randomWithRange(0, firstNames.length-1)]+" "+middleNames[Utils.randomWithRange(0, middleNames.length-1)]
                        +" " +lastNames[Utils.randomWithRange(0, lastNames.length-1)]);
                rating.setUserId(String.valueOf(r));
                int random = Utils.randomWithRange(0, comments.length-1);
                rating.setContent(comments[random]);
                rating.setRating((float)Math.round(ratings[random]));
                rate+=ratings[random];
                RealmController.with(this).getRealm().copyToRealmOrUpdate(rating);
            }
            user.setRating(rate/count);
            String key = user.getName()+"-"+user.getPhone()+"-"+user.getJob();
            user.setSearchKey(key.toLowerCase());
            RealmController.with(this).getRealm().copyToRealmOrUpdate(user);
            HashMap map = user.toHashMap();

            db.collection(User.class.getSimpleName())
                    .add(map)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(AppConstants.TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(AppConstants.TAG, "Error adding document", e);
                        }
                    });

//            list.add(user);
        }
        RealmController.with(this).getRealm().commitTransaction();

    }

    private void setupViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        final BirdCartFragment birdCartFragment = new BirdCartFragment();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MainFragment(), "Dashboard");
//        adapter.addFragment(new BirdFragment(), "Bird");
        adapter.addFragment(birdCartFragment, "Cart");
        adapter.addFragment(new SettingFragment(), "Setting");
        viewPager.setAdapter(adapter);

        int[] icons = new int[]{R.drawable.ic_cro, R.drawable.ic_cart, R.drawable.ic_me};
        String[] titles = new String[]{"Cro", "Cart", "Setting"};
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
