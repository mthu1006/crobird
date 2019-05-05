package com.croteam.crobird;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.croteam.crobird.adapter.ClickListener;
import com.croteam.crobird.adapter.TagAdapter;
import com.croteam.crobird.uitls.AppConstants;
import com.croteam.crobird.uitls.Prefs;
import com.croteam.crobird.uitls.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Register3Activity extends AppCompatActivity {

    @BindView(R.id.edt_main_job)
    AppCompatAutoCompleteTextView edtMainJob;
    @BindView(R.id.rv_tag)
    RecyclerView rvTag;
    @BindView(R.id.spn_certificate)
    Spinner spnCertificate;
    @BindView(R.id.spn_language)
    Spinner spnLanguage;
    @BindView(R.id.spn_skill_level)
    Spinner spnSkillLevel;
    @BindView(R.id.spn_payment)
    Spinner spnPayment;
    @BindView(R.id.spn_currency)
    Spinner spnCurrency;
    @BindView(R.id.edt_sub_skill)
    EditText edtSubSkill;
    @BindView(R.id.btn_next)
    Button btnNext;

    String[] certificates = new String[]{"Certificate", "Bachelor", "Master", "Post Doctor"};
    String[] languages = new String[]{"Foreign language", "English", "Japanese", "Korean", "French", "Chinese"};
    String[] levels = new String[]{"Level", "Beginner", "Elementary", "Intermediate", "Upper Intermediate"};
    String[] payments = new String[]{"Payment", "Cash", "Internet Banking", "Visa/Master card"};
    String[] currencies = new String[]{"Currency", "VND", "USD", "Won"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        initViews();

    }

    private void initViews(){

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTag.setLayoutManager(mLayoutManager);
        rvTag.setItemAnimator(new DefaultItemAnimator());
        final TagAdapter adapter = new TagAdapter(this, getResources().getStringArray(R.array.tag), new ClickListener() {
            @Override
            public void onItemClick(View v) {
                if(!Validation.checkNullOrEmpty(edtMainJob.getText().toString()))
                    edtMainJob.setText(edtMainJob.getText().toString() + " ," + v.getTag().toString());
                else edtMainJob.setText(v.getTag().toString());
            }

            @Override
            public void onItemLongClick(View v) {

            }
        });
        rvTag.setAdapter(adapter);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.tag));
        edtMainJob.setAdapter(arrayAdapter);
        ArrayAdapter<String> certificateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, certificates);
        spnCertificate.setAdapter(certificateAdapter);
        ArrayAdapter<String> languageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, languages);
        spnLanguage.setAdapter(languageAdapter);
        ArrayAdapter<String> levelAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, levels);
        spnSkillLevel.setAdapter(levelAdapter);
        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, payments);
        spnPayment.setAdapter(paymentAdapter);
        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currencies);
        spnCurrency.setAdapter(currencyAdapter);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Prefs.with(Register3Activity.this).putInt(AppConstants.PREF_KEY_REGISTER_PROGRESS, 3);
                Intent intent = new Intent(Register3Activity.this, Register4Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
