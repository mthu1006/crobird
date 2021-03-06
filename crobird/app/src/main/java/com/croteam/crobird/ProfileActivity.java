package com.croteam.crobird;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.croteam.crobird.model.User;
import com.croteam.crobird.uitls.AppConstants;
import com.croteam.crobird.uitls.Prefs;
import com.croteam.crobird.uitls.Utils;
import com.croteam.crobird.uitls.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.backdrop)
    ImageView imgBackdrop;
//    @BindView(R.id.tv_name)
//    TextView tvName;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;
//    @BindView(R.id.img_toolbar)
//    ImageView imgToolbar;
//    @BindView(R.id.tv_title)
//    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        initUser();
    }

    private void initUser(){
        String str = Prefs.with(this).getString(AppConstants.PREF_KEY_USER);
        try {
            JSONObject obj = new JSONObject(str);
            collapsingToolbarLayout.setTitle(obj.getString(User.NAME));
//            tvEmail.setText(obj.getString(User.EMAIL));
//            tvName.setText(obj.getString(User.NAME));
//            tvTitle.setText(obj.getString(User.NAME));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String photo = Prefs.with(this).getString(AppConstants.PREF_KEY_USER_PHOTO);
        if(!Validation.checkNullOrEmpty(photo)){
            imgBackdrop.setImageBitmap(Utils.base64ToBitmap(photo));
//            imgToolbar.setImageBitmap(Utils.base64ToBitmap(photo));
        }

    }
}
