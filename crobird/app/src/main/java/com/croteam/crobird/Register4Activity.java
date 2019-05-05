package com.croteam.crobird;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.croteam.crobird.uitls.AppConstants;
import com.croteam.crobird.uitls.Prefs;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Register4Activity extends AppCompatActivity {


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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register4);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initViews();
    }

    private void initViews(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs.with(Register4Activity.this).putInt(AppConstants.PREF_KEY_REGISTER_PROGRESS, 4);
                Intent intent = new Intent(Register4Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
