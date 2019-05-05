package com.croteam.crobird.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.croteam.crobird.R;
import com.croteam.crobird.SearchResultActivity;
import com.croteam.crobird.WorkActivity;
import com.croteam.crobird.adapter.CategoryAdapter;
import com.croteam.crobird.database.RealmController;
import com.croteam.crobird.model.Category;
import com.croteam.crobird.model.User;

import java.util.ArrayList;

import io.realm.RealmObject;
import io.realm.RealmResults;


public class MainFragment extends Fragment {

    private GridView gvCategories;
    private EditText edtSearch;
    private ImageButton btnFilter;
    private ArrayList<Category> list;

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

        list = new ArrayList<>();
//        initCatogories();
        initData();
        initViews();
        return root;
    }

    private void initData(){
        list = new ArrayList<>();
        RealmResults<RealmObject> results = RealmController.with(this).getAllList(Category.class);
        for(RealmObject obj: results){
            list.add((Category) obj);
        }

    }

    private void initViews(){
        CategoryAdapter adapter = new CategoryAdapter(getActivity(), list, R.layout.item_category);
        gvCategories.setAdapter(adapter);
        gvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), WorkActivity.class);
                intent.putExtra(Category.NAME, list.get(position).getName());
                startActivity(intent);
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String key = edtSearch.getText().toString();
                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                    intent.putExtra(User.SEARCH_KEY, key);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }




}
