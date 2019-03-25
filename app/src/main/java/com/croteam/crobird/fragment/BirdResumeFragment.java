package com.croteam.crobird.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.croteam.crobird.CroDetailActivity;
import com.croteam.crobird.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BirdResumeFragment extends Fragment {

    public BirdResumeFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.tv_job)
    TextView tvJob;
    @BindView(R.id.tv_mail)
    TextView tvEmail;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_dob)
    TextView tvDob;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_bird_resume, container, false);
        ButterKnife.bind(this, root);
        initResume();
        return root;
    }

    private void initResume(){
        tvJob.setText(((CroDetailActivity)getActivity()).user.getJob());
        tvEmail.setText(((CroDetailActivity)getActivity()).user.getEmail());
        tvPhone.setText(((CroDetailActivity)getActivity()).user.getPhone());
        tvDob.setText(((CroDetailActivity)getActivity()).user.getDob());
    }



}
