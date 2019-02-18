package com.croteam.crobird.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.croteam.crobird.R;
import com.croteam.crobird.adapter.ClickListener;
import com.croteam.crobird.adapter.MenuAdapter;
import com.croteam.crobird.model.CommonClass;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.realm.Realm;

public class SettingFragment extends Fragment implements ClickListener {


    public SettingFragment() {
        // Required empty public constructor
    }

    private TextView tvName, tvPhone, tvEmail;
    private CircleImageView imgProfile;
    private RecyclerView recyclerView;
    private LinearLayout lnHeaderLogin, lnInfo;

    public ProgressDialog loadingDialog;
    private Realm realm;
    private ArrayList<CommonClass> menuList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        recyclerView = (RecyclerView)root.findViewById(R.id.rv_menu);
        lnHeaderLogin = (LinearLayout) root.findViewById(R.id.ln_header_login);
        lnInfo = (LinearLayout) root.findViewById(R.id.ln_info);
        tvName = (TextView)root.findViewById(R.id.tv_name);
        tvPhone = (TextView)root.findViewById(R.id.tv_phone);
        tvEmail = (TextView)root.findViewById(R.id.tv_email);
        imgProfile = (CircleImageView) root.findViewById(R.id.img_profile);
//        realm = RealmController.with(getActivity()).getRealm();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        loadingDialog = new ProgressDialog(getActivity());
        loadingDialog.setTitle("Đang đồng bộ, vui lòng đợi...");
        loadingDialog.setCanceledOnTouchOutside(false);
        initSettingList();
        initView();
        return root;
    }

    private void initSettingList(){
        menuList = new ArrayList<>();
        CommonClass c1 = new CommonClass("Change password", android.R.drawable.ic_media_play);
        menuList.add(c1);
        CommonClass c2 = new CommonClass("My budget", android.R.drawable.ic_media_play);
        menuList.add(c2);
        CommonClass c3 = new CommonClass("My resume", android.R.drawable.ic_media_play);
        menuList.add(c3);
        CommonClass c4 = new CommonClass("My tasks", android.R.drawable.ic_media_play);
        menuList.add(c4);
    }

    private void initView(){
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        MenuAdapter adapter =  new MenuAdapter(getActivity(), menuList, this);
        recyclerView.setAdapter(adapter);

//        lnInfo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AppTransaction.replaceActivityWithAnimation(getActivity(), AboutActivity.class);
//            }
//        });
//
//        imgProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), ProfileActivity.class);
//                startActivity(intent);
//            }
//        });

    }


    @Override
    public void onItemClick(View v) {

    }

    @Override
    public void onItemLongClick(View v) {

    }
}
