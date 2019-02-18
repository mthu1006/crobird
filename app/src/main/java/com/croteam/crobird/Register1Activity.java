package com.croteam.crobird;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.croteam.crobird.uitls.AppConstants;
import com.croteam.crobird.uitls.Prefs;
import com.croteam.crobird.uitls.Utils;
import com.croteam.crobird.uitls.Validation;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Register1Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initViews();
    }

    private void initViews(){
        this.registerForContextMenu(imgProfile);
        datePickerDialog = new DatePickerDialog(
                this, this, 1990, 1, 1);

        edtDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(view);
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
            Prefs.with(this).putInt(AppConstants.PREF_KEY_REGISTER_PROGRESS, 1);
            Intent intent = new Intent(Register1Activity.this, Register2Activity.class);
            startActivity(intent);
            finish();
        }
    }


    public void takePictureFromCamera() {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, 0);//zero can be replaced with any action code
    }

    public void picImageFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);//one can be replaced with any action code
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.item_camera:
                takePictureFromCamera();
                return true;
            case R.id.item_galery:
                picImageFromGallery();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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

    public void setImageAvatar(Bitmap bm){
        imgProfile.setImageBitmap(bm);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    if(photo!=null) {
                        setImageAvatar(photo);
                    }
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    photo = Utils.uriToBitmap(selectedImage, Register1Activity.this);
                    if(photo!=null) {
                        setImageAvatar(photo);
                    }
                }
                break;
        }
    }

}
