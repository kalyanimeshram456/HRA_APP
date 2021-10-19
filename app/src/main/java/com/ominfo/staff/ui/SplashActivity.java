package com.ominfo.staff.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ominfo.staff.R;
import com.ominfo.staff.interfaces.Constants;
import com.ominfo.staff.interfaces.SharedPrefKey;
import com.ominfo.staff.ui.dashboard.DashbooardActivity;
import com.ominfo.staff.ui.login.LoginActivity;
import com.ominfo.staff.util.SharedPref;


public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setTimeStamp();
    }

    /*wait for few second than launch new screen*/
    private void setTimeStamp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    //get login status
                    Boolean iSLoggedIn = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.IS_LOGGED_IN, false);
                    if (iSLoggedIn){
                        launchScreen(DashbooardActivity.class);
                    }else {
                        launchScreen(LoginActivity.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, Constants.SPLAHS_TIME_OUT);
    }

    /*navigate to new screen*/
    private void launchScreen(Class activity) {
        startActivity(new Intent(SplashActivity.this, activity));
        finish();
    }
}