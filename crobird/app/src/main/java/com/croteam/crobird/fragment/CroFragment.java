package com.croteam.crobird.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.croteam.crobird.R;
import com.croteam.crobird.adapter.BirdAdapter;
import com.croteam.crobird.adapter.ClickListener;
import com.croteam.crobird.model.User;
import com.croteam.crobird.uitls.Utils;

import java.util.ArrayList;

public class CroFragment extends Fragment {

    public CroFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private EditText edtSearch;
    private RecyclerView rvBird;
    private ArrayList<User> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cro, container, false);
        edtSearch = root.findViewById(R.id.edt_search);
        rvBird = root.findViewById(R.id.rv);
        list = new ArrayList<>();
        initViews();
        initCro();
        return root;
    }

    private void initViews(){
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
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
        BirdAdapter adapter = new BirdAdapter(getActivity(), list, new ClickListener() {
            @Override
            public void onItemClick(View v) {

            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
        rvBird.setAdapter(adapter);

    }

}
