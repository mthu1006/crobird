package com.croteam.crobird.uitls;


import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

    private static final String PRE_LOAD = "preLoad";
    private static final String PREFS_NAME = "prefs";
    private static Prefs instance;
    private final SharedPreferences sharedPreferences;

    public Prefs(Context context) {

        sharedPreferences = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static Prefs with(Context context) {

        if (instance == null) {
            instance = new Prefs(context);
        }
        return instance;
    }

    public void setPreLoad(boolean totalTime) {

        sharedPreferences
                .edit()
                .putBoolean(PRE_LOAD, totalTime)
                .apply();
    }

    public boolean getPreLoad(){
        return sharedPreferences.getBoolean(PRE_LOAD, false);
   }

   public void putString(String key, String value){
       sharedPreferences
               .edit()
               .putString(key, value)
               .apply();
   }

    public String getString(String key){
        return sharedPreferences.getString(key, null);
    }

    public String getString(String key, String defaultValue){
        return sharedPreferences.getString(key, defaultValue);
    }

    public void putBoolean(String key, boolean value){
        sharedPreferences
                .edit()
                .putBoolean(key, value)
                .apply();
    }

    public boolean getBoolean(String key){
        return sharedPreferences.getBoolean(key, false);
    }

    public void putInt(String key, int value){
        sharedPreferences
                .edit()
                .putInt(key, value)
                .apply();
    }

    public int getInt(String key){
        return sharedPreferences.getInt(key, 0);
    }

    public int getInt(String key, int defaultValue){
        return sharedPreferences.getInt(key, defaultValue);
    }

    public void putDouble(String key, double value){
        sharedPreferences
                .edit()
                .putString(key, String.valueOf(value))
                .apply();
    }

    public double getDouble(String key){
        return Double.parseDouble(sharedPreferences.getString(key, "0"));
    }

}
