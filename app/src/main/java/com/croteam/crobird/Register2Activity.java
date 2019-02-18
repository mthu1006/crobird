package com.croteam.crobird;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.croteam.crobird.uitls.AppConstants;
import com.croteam.crobird.uitls.Prefs;
import com.croteam.crobird.uitls.Validation;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Register2Activity extends AppCompatActivity {

    @BindView(R.id.edt_contact)
    EditText edtContact;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.btn_next)
    Button btnNext;

    private PlaceAutocompleteFragment autocompleteFragment;
    private EditText autocompleteEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initViews();
    }

    private void initViews(){
        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setHint("Address");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

            }

            @Override
            public void onError(Status status) {
                Log.d(AppConstants.TAG, "An error occurred: " + status);
            }
        });

        autocompleteEdt = ((EditText)autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input));

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private void validateData(){
        String contact = edtContact.getText().toString();
        String mail = edtEmail.getText().toString();
        String phone = edtPhone.getText().toString();
        if(Validation.checkNullOrEmpty(contact)){
            edtContact.setError("You must enter contact!");
        }else if(Validation.checkNullOrEmpty(mail)){
            edtEmail.setError("You must enter email!");
        }else if(Validation.checkNullOrEmpty(phone)){
            edtPhone.setError("You must enter phone number!");
        }else {
            Prefs.with(this).putInt(AppConstants.PREF_KEY_REGISTER_PROGRESS, 2);
            Intent intent = new Intent(Register2Activity.this, Register3Activity.class);
            startActivity(intent);
            finish();
        }
    }
}
