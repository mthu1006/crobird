package com.croteam.crobird.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.croteam.crobird.MainActivity;
import com.croteam.crobird.R;
import com.croteam.crobird.uitls.AppConstants;
import com.croteam.crobird.uitls.AppTransaction;
import com.croteam.crobird.uitls.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Register4Fragment extends Fragment {

    public Register4Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.btn_choose_file)
    Button btnChooseFile;
    @BindView(R.id.edt_file_path)
    EditText edtFilePath;
    @BindView(R.id.edt_title)
    EditText edtTitle;
    @BindView(R.id.edt_intro)
    EditText edtIntro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register4, container, false);
        ButterKnife.bind(this, view);

        initViews();
        return view;
    }

    private void initViews(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.with(getActivity()).putInt(AppConstants.PREF_KEY_REGISTER_PROGRESS, 4);
                AppTransaction.replaceActivityWithAnimation(getActivity(), MainActivity.class);
            }
        });
    }

}
