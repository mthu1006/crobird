package com.croteam.crobird.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Register2Fragment extends Fragment {


    public Register2Fragment() {
        // Required empty public constructor
    }

    PlacesClient placesClient;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Places.initialize(getActivity().getApplicationContext(), getActivity().getString(R.string.google_maps_key));
        placesClient = Places.createClient(getActivity());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @BindView(R.id.edt_contact)
    EditText edtContact;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.btn_next)
    Button btnNext;

    private AutocompleteSupportFragment autocompleteFragment;
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
        autocompleteFragment = (AutocompleteSupportFragment ) getChildFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setHint("Address");
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(AppConstants.TAG, "Place: " + place.getName() + ", " + place.getId());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(AppConstants.TAG, "An error occurred: " + status);
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
