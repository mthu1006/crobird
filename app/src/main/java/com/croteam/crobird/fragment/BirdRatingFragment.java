package com.croteam.crobird.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.croteam.crobird.CroDetailActivity;
import com.croteam.crobird.R;
import com.croteam.crobird.adapter.RatingAdapter;
import com.croteam.crobird.database.RealmController;
import com.croteam.crobird.model.Rating;
import com.croteam.crobird.model.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class BirdRatingFragment extends Fragment {

    public BirdRatingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @BindView(R.id.lv)
    ListView lv;
    @BindView(R.id.tv_rating)
    TextView tvRating;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.tv_rating_counts)
    TextView tvRatingcounts;

    ArrayList<Rating> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_bird_rating, container, false);
        ButterKnife.bind(this, root);
        initRating();
        return root;
    }

    private void initRating(){
        list = new ArrayList<>();
        User user = ((CroDetailActivity)getActivity()).user;
        RealmResults<Object> results = RealmController.with(this).queryObjects(Rating.class, Rating.BIRD_ID, user.getId());
        for(Object obj: results){
            list.add((Rating)obj);
        }
        tvRating.setText(String.valueOf(user.getRating()).substring(0, 3));
        ratingBar.setRating(user.getRating());
        tvRatingcounts.setText(String.valueOf(results.size()) + " reviews");

        RatingAdapter adapter = new RatingAdapter(getActivity(), list);
        lv.setAdapter(adapter);

    }

}
