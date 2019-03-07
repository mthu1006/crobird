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
import com.croteam.crobird.model.Category;
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

        String[] names = {"Engineering", "Cleansing", "Teaching", "Saling", "Marketing", "Publishing", "Farming", "Computing", "Healthy caring"};
        for(int i=0; i<names.length; i++){
            Category category = new Category(String.valueOf(i), names[i], "data:image/png;base64,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");
            CommonClass c9 = new CommonClass(names[i], R.drawable.ic_engineer);
            categories.add(c9);
        }
    }

}
