package com.ominfo.hra_app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.ominfo.hra_app.MainActivity;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.interfaces.Constants;
import com.ominfo.hra_app.interfaces.SharedPrefKey;
import com.ominfo.hra_app.ui.login.LoginActivity;
import com.ominfo.hra_app.ui.registration.RegistrationActivity;
import com.ominfo.hra_app.util.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    @BindView(R.id.tv_header)
    AppCompatImageView imgheader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        ButterKnife.bind(this);
        setTimeStamp();
        Glide.with(this)
                .load(R.drawable.ic_turanthbiz_name)
                .into(imgheader);
        Window window =getWindow();
        View view = window.getDecorView();
        BaseActivity.DarkStatusBar.setLightStatusBar(view,this);
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
                        launchScreen(MainActivity.class);
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