package com.croteam.crobird.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.croteam.crobird.R;
import com.croteam.crobird.RegisterActivity;
import com.croteam.crobird.uitls.AppConstants;
import com.croteam.crobird.uitls.AppTransaction;
import com.croteam.crobird.uitls.Prefs;
import com.croteam.crobird.uitls.Validation;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Register2Fragment extends Fragment {


    public Register2Fragment() {
        // Required empty public constructor
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register2, container, false);
        ButterKnife.bind(this, view);

        initViews();
        return view;
    }

    private void initViews(){
        autocompleteFragment = (PlaceAutocompleteFragment) getActivity().getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
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

        edtPhone.setText(Prefs.with(getActivity()).getString(AppConstants.PHONE_NUMBER));

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
            Prefs.with(getActivity()).putInt(AppConstants.PREF_KEY_REGISTER_PROGRESS, 2);
            ((RegisterActivity)getActivity()).user.setEmail(mail);
            ((RegisterActivity)getActivity()).user.setPhone(phone);
            String str = ((RegisterActivity)getActivity()).user.toJSONObject().toString();
            Prefs.with(getActivity()).putString(AppConstants.PREF_KEY_USER, str);
            ((RegisterActivity)getActivity()).stepView.go(2, true);
            AppTransaction.replaceFragmentWithAnimation(getActivity().getSupportFragmentManager(), new Register3Fragment(), R.id.content);
        }
    }

}
