package com.ominfo.staff.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPref class is used for saving data into sharedPreference
 */
public class SharedPref {
    private static SharedPref mInstance = null;
    private static SharedPreferences mSharedPref;
    private static final String PREF_NAME = "";
    private Context mContext;

    // use getApplicationContext() to call this method
    public static SharedPref getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPref(context);
        }
        return mInstance;
    }

    private SharedPref(Context context) {
        mContext = context;
        mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
    }


    public static String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

}
