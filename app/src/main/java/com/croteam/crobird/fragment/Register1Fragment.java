package com.croteam.crobird.fragment;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.croteam.crobird.R;
import com.croteam.crobird.RegisterActivity;
import com.croteam.crobird.model.User;
import com.croteam.crobird.uitls.AppConstants;
import com.croteam.crobird.uitls.AppTransaction;
import com.croteam.crobird.uitls.Prefs;
import com.croteam.crobird.uitls.Validation;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class Register1Fragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.img_profile)
    ImageView imgProfile;
    @BindView(R.id.edt_name)
    EditText edtName;
    @BindView(R.id.edt_dob)
    EditText edtDOB;
    @BindView(R.id.rg_gender)
    RadioGroup rgGender;
    @BindView(R.id.btn_next)
    Button btnNext;

    private DatePickerDialog datePickerDialog;
    private Bitmap photo;


    public Register1Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_register1, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews(){
        this.registerForContextMenu(imgProfile);
        datePickerDialog = new DatePickerDialog(
                getActivity(), this, 1990, 1, 1);

        edtDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().openContextMenu(view);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validateData();
            }
        });
    }

    private void validateData(){
        String name = edtName.getText().toString();
        String dob = edtDOB.getText().toString();
        if(Validation.checkNullOrEmpty(name)){
            edtName.setError("You must enter name!");
        }else if(Validation.checkNullOrEmpty(dob)){
            edtDOB.setError("You must enter date of birth!");
        }else {
            updateInfo(name, dob);
        }
    }

    private void updateInfo(String name, String dob){
//        Map mParent = new HashMap();
//        mParent.put(User.PHONE, name);
//        mParent.put(User.DOB, dob);
//        mParent.put(User.STEP, 2);
//        ((RegisterActivity)getActivity()).db.push().setValue(mParent);
        ((RegisterActivity)getActivity()).user.setName(name);
        ((RegisterActivity)getActivity()).user.setDob(dob);
        String str = ((RegisterActivity)getActivity()).user.toJSONObject().toString();
        Prefs.with(getActivity()).putString(AppConstants.PREF_KEY_USER, str);
        Prefs.with(getActivity()).putInt(AppConstants.PREF_KEY_REGISTER_PROGRESS, 1);
        ((RegisterActivity)getActivity()).stepView.go(1, true);
        AppTransaction.replaceFragmentWithAnimation(getActivity().getSupportFragmentManager(), new Register2Fragment(), R.id.content);
    }

    public void setImageAvatar(Bitmap bm){
        imgProfile.setImageBitmap(bm);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        i1+=1;
        String m = String.valueOf(i1);
        if(i1<10) m = "0"+m;
        String d = String.valueOf(i2);
        if(i2<10) d = "0"+d;
        edtDOB.setText(d+"-"+m+"-"+i);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.item_camera:
                ((RegisterActivity)getActivity()).takePictureFromCamera();
                return true;
            case R.id.item_galery:
                ((RegisterActivity)getActivity()).picImageFromGallery();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


}
