package com.croteam.crobird;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

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

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.realm.internal.Util;

public class RegisterActivity extends AppCompatActivity {

    String[] descriptionData = {"Details", "Status", "Photo", "Confirm"};

    private Bitmap photo;
    Register1Fragment register1Fragment;
    Register4Fragment register4Fragment;

    public StepView stepView;

    public User user;

    private static final int FILE_SELECT_CODE = 2;

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
        register4Fragment = new Register4Fragment();
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
        else if(step == 4)AppTransaction.replaceFragmentWithAnimation(getSupportFragmentManager(), register4Fragment, R.id.content);
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

    public void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
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
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = imageReturnedIntent.getData();
                    Log.d(AppConstants.TAG, "File Uri: " + uri.toString());
                    // Get the path
                    String path = null;
                    try {
                        path = Utils.getPath(this, uri);
                        Log.d(AppConstants.TAG, "File Path: " + path);
                        register4Fragment.setFilePath(path);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
    }

}
