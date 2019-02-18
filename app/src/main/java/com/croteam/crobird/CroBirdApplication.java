package com.croteam.crobird;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.croteam.crobird.uitls.AppConstants;
import com.google.firebase.FirebaseApp;

import io.realm.Realm;

public class CroBirdApplication extends Application {

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        try {
            Realm.init(this);
        }catch (Exception e){
            Log.e(AppConstants.TAG, e.toString());
        }
    }
}
