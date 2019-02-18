package com.croteam.crobird.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.croteam.crobird.R;
import com.croteam.crobird.WorkActivity;
import com.croteam.crobird.adapter.CommonAdapter;
import com.croteam.crobird.model.CommonClass;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    private GridView gvCategories;
    private EditText edtSearch;
    private ImageButton btnFilter;
    private ArrayList<CommonClass> categories;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        gvCategories = root.findViewById(R.id.gv_categories);
        edtSearch = root.findViewById(R.id.edt_search);

        categories = new ArrayList<>();
        initCatogories();
        initViews();
        return root;
    }

    private void initViews(){
        CommonAdapter adapter = new CommonAdapter(getActivity(), categories, R.layout.item_category);
        gvCategories.setAdapter(adapter);
        gvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WorkActivity.class);
                intent.putExtra("name", categories.get(position).getField1());
                startActivity(intent);
            }
        });
    }

    private void initCatogories(){
        CommonClass c1 = new CommonClass("Engineering", R.drawable.ic_engineer);
        categories.add(c1);
        CommonClass c2 = new CommonClass("Cleansing", R.drawable.ic_engineer);
        categories.add(c2);
        CommonClass c3 = new CommonClass("Teaching", R.drawable.ic_engineer);
        categories.add(c3);
        CommonClass c4 = new CommonClass("Saling", R.drawable.ic_engineer);
        categories.add(c4);
        CommonClass c5 = new CommonClass("Marketing", R.drawable.ic_engineer);
        categories.add(c5);
        CommonClass c6 = new CommonClass("Publishing", R.drawable.ic_engineer);
        categories.add(c6);
        CommonClass c7 = new CommonClass("Farming", R.drawable.ic_engineer);
        categories.add(c7);
        CommonClass c8 = new CommonClass("Computing", R.drawable.ic_engineer);
        categories.add(c8);
        CommonClass c9 = new CommonClass("Healthy caring", R.drawable.ic_engineer);
        categories.add(c9);
    }

}
