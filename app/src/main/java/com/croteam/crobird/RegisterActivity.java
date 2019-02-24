package com.croteam.crobird;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import com.croteam.crobird.fragment.Register1Fragment;
import com.croteam.crobird.fragment.Register2Fragment;
import com.croteam.crobird.fragment.Register3Fragment;
import com.croteam.crobird.fragment.Register4Fragment;
import com.croteam.crobird.model.User;
import com.croteam.crobird.uitls.AppConstants;
import com.croteam.crobird.uitls.AppTransaction;
import com.croteam.crobird.uitls.Prefs;
import com.croteam.crobird.uitls.Utils;
import com.shuhart.stepview.StepView;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    String[] descriptionData = {"Details", "Status", "Photo", "Confirm"};

    private Bitmap photo;
    Register1Fragment register1Fragment;

    public StepView stepView;

    public User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().hide();
        user = new User();
        initStepView();
    }

    private void initStepView(){
        register1Fragment = new Register1Fragment();
        stepView = findViewById(R.id.step_view);
        stepView.getState().steps(new ArrayList<String>() {{
            add("");
            add("");
            add("");
            add("");
        }}).commit();
        final int step = Prefs.with(this).getInt(AppConstants.PREF_KEY_REGISTER_PROGRESS);
        stepView.go(step-1, true);
        if(step==1) AppTransaction.replaceFragmentWithAnimation(getSupportFragmentManager(), register1Fragment, R.id.content);
        else if(step == 2)AppTransaction.replaceFragmentWithAnimation(getSupportFragmentManager(), new Register2Fragment(), R.id.content);
        else if(step == 3)AppTransaction.replaceFragmentWithAnimation(getSupportFragmentManager(), new Register3Fragment(), R.id.content);
        else if(step == 4)AppTransaction.replaceFragmentWithAnimation(getSupportFragmentManager(), new Register4Fragment(), R.id.content);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {
                    photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    if(photo!=null) {
                        register1Fragment.setImageAvatar(photo);
                    }
                }

                break;
            case 1:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = imageReturnedIntent.getData();
                    photo = Utils.uriToBitmap(selectedImage, RegisterActivity.this);
                    if(photo!=null) {
                        register1Fragment.setImageAvatar(photo);
                    }
                }
                break;
        }
    }


}
