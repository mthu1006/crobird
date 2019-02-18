package com.croteam.crobird;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.croteam.crobird.uitls.AppConstants;
import com.croteam.crobird.uitls.AppDialogManager;
import com.croteam.crobird.uitls.DialogAcceptClickListener;
import com.croteam.crobird.uitls.Prefs;
import com.croteam.crobird.uitls.Utils;
import com.croteam.crobird.uitls.Validation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OtpListener;
import com.mukesh.OtpView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {

    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private Button btnResendOTP;
    private OtpView otpView;
    private TextView dialogTitle;
    private Dialog dialogOTP;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private Timer timer;
    private String mVerificationId, code;
    private PhoneAuthProvider mPhoneAuthProvider;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String URL = "";
    private int STATE_CODE_SENT = 0;
    private int STATE_VERIFIED_SUCCESS = 1;
    private int STATE_VERIFIED_FAILED = 2;
    private int STATE_LOGIN_SUCCESS = 3;
    private int STATE_LOGIN_FAILED = 4;
    private int STATE_RESEND_OTP = 5;
    private Dialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        initViews();
        initFirebaseAuth();
        initOTPDialog();
        checkIfUserLoggedIn();

    }

    private void checkIfUserLoggedIn(){
        if(!Validation.checkNullOrEmpty(Prefs.with(this).getString(AppConstants.PHONE_NUMBER))){
            int progress = Prefs.with(LoginActivity.this).getInt(AppConstants.PREF_KEY_REGISTER_PROGRESS);
            Intent intent = null;
            if(progress <= 4){
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }else{
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        }
    }

    private void initViews(){
        mLoadingDialog = AppDialogManager.createLoadingDialog(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doLoginPhone();
            }
        });
    }

    private void initOTPDialog(){
        dialogOTP = AppDialogManager.onCreateCustomDialog(LoginActivity.this, R.layout.dialog_otp, new DialogAcceptClickListener() {
            @Override
            public void onAcceptClick(View v) {

            }

            @Override
            public void onCloseClick(View v) {

            }
        });
        dialogOTP.setCancelable(false);
        dialogOTP.setCanceledOnTouchOutside(false);
        dialogTitle = (TextView) dialogOTP.findViewById(R.id.tv_title);
        otpView = (OtpView) dialogOTP.findViewById(R.id.otp_view);
        btnResendOTP = (Button) dialogOTP.findViewById(R.id.btn_accept);
        otpView.setListener(new OtpListener() {
            @Override
            public void onOtpEntered(String otp) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otp);
                signInWithPhoneAuthCredential(credential);

            }
        });
    }

    private void initFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("vi");
        mPhoneAuthProvider = PhoneAuthProvider.getInstance();
        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d("TAG", "onVerificationCompleted:" + credential);
                code = credential.getSmsCode();
                changeState(STATE_VERIFIED_SUCCESS);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Log.w(AppConstants.TAG, "onVerificationFailed", e);
                changeState(STATE_LOGIN_FAILED);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                }
                else if (e instanceof FirebaseTooManyRequestsException) {
                    Snackbar snackbar = Snackbar
                            .make(btnLogin, "Verification Failed !! Too many request. Try after some time. ", Snackbar.LENGTH_LONG);

                    snackbar.show();
                }

            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("TAG", "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                changeState(STATE_CODE_SENT);
            }

            @Override
            public void onCodeAutoRetrievalTimeOut(String verificationId){
                Log.d("TAG", "onCodeAutoRetrievalTimeOut:" + verificationId);
            }
        };
    }

    private void changeState(int state){
        if (state == STATE_CODE_SENT){
            dialogOTP.show();
            btnResendOTP.setEnabled(false);
            starttimer();
        }else if(state == STATE_VERIFIED_SUCCESS){
            otpView.setOTP(code);
            if(!dialogOTP.isShowing()) dialogOTP.show();
        }else if(state == STATE_VERIFIED_FAILED){
            dialogTitle.setTextColor(Color.RED);
            dialogTitle.setText("Sai mã đang nhập!");
            otpView.setOTP("      ");
        }else if(state == STATE_LOGIN_SUCCESS){
            dialogTitle.setTextColor(getResources().getColor(R.color.colorPrimary));
            dialogTitle.setText("Đăng ký thành công");
            timer.cancel();
            timer.purge();
            btnResendOTP.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            btnResendOTP.setText("Đăng nhập");
            btnResendOTP.setEnabled(true);
            btnResendOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    edtPhone.setEnabled(false);
                    getUserInfor(edtPhone.getText().toString());

                }
            });
            btnResendOTP.performClick();
        }else if(state == STATE_LOGIN_FAILED){
            Snackbar.make(btnLogin, "Có lỗi xảy ra, vui lòng đăng nhập bằng cách khác!", Snackbar.LENGTH_LONG).show();
        }else if(state == STATE_RESEND_OTP){
            String num = formatPhoneNumber(edtPhone.getText().toString());
            if(!Validation.checkNullOrEmpty(num))
                resendVerificationCode(num, mResendToken);
            btnResendOTP.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            btnResendOTP.setEnabled(false);
            starttimer();
        }
    }

    private void getUserInfor(final String phone) {
        Prefs.with(this).putString(AppConstants.PHONE_NUMBER, phone);
        Prefs.with(this).putInt(AppConstants.PREF_KEY_REGISTER_PROGRESS, 1);
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
//        Map<String, Object> user = new HashMap<>();
//        user.put(User.PHONE, phone);
//        user.put(User.NAME, "");
//        user.put(User.EMAIL, "");
//        db.collection(User.TABLE_NAME)
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Prefs.with(LoginActivity.this).putBoolean(AppConstants.IS_LOGGED_KEY, true);
//                        Prefs.with(LoginActivity.this).putString(AppConstants.PHONE_NUMBER, edt_phone.getText().toString());
//
//                        finish();
//                        Log.d(AppConstants.TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w(AppConstants.TAG, "Error adding document", e);
//                    }
//                });

    }

    private String formatPhoneNumber(String phoneNumber){
        Phonenumber.PhoneNumber vnNumberProto = null;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.createInstance(this);
        try {
            vnNumberProto = phoneUtil.parse(phoneNumber, "VN");
        } catch (NumberParseException e) {
            Log.e(AppConstants.TAG,"NumberParseException was thrown: " + e.toString());
        }
        return phoneUtil.format(vnNumberProto, PhoneNumberUtil.PhoneNumberFormat.E164);

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mLoadingDialog.show();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mLoadingDialog.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            changeState(STATE_LOGIN_SUCCESS);
//                            Snackbar snackbar = Snackbar
//                                    .make(btn_login, "Successfully Verified", Snackbar.LENGTH_LONG);
//
//                            snackbar.show();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                changeState(STATE_VERIFIED_FAILED);
                            }
                        }
                    }
                });
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        mPhoneAuthProvider.verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                120,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

    }

    public void starttimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {

            int second = 60;

            @Override
            public void run() {
                if (second <= 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnResendOTP.setText("RESEND CODE");
                            btnResendOTP.setBackgroundColor(Color.parseColor("#4ABD73"));
                            btnResendOTP.setEnabled(true);
                            btnResendOTP.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    changeState(STATE_RESEND_OTP);
                                }
                            });
                            timer.cancel();
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            btnResendOTP.setText("00:" + second--);
                        }
                    });
                }

            }
        }, 0, 1000);
    }

    private void resendVerificationCode(String phoneNumber,
                                        PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }

    private void doLoginPhone(){
        String phoneNumber = edtPhone.getText().toString();
        if(!Validation.checkNullOrEmpty(phoneNumber)){
            if(Utils.isNetworkOnline(this)) {
                startPhoneNumberVerification(formatPhoneNumber(phoneNumber));
            }else Snackbar.make(btnLogin, "Vui lòng kết nối internet và thử lại!", Snackbar.LENGTH_SHORT).show();
        }else Snackbar.make(btnLogin, "Bạn chưa nhập số điện thoại!", Snackbar.LENGTH_SHORT).show();

    }



}

