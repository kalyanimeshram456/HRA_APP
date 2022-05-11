package com.ominfo.hra_app.ui.registration;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.ominfo.hra_app.MainActivity;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.interfaces.SharedPrefKey;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.login.LoginActivity;
import com.ominfo.hra_app.ui.login.model.AttendanceDaysTable;
import com.ominfo.hra_app.ui.login.model.LoginRequest;
import com.ominfo.hra_app.ui.login.model.LoginResponse;
import com.ominfo.hra_app.ui.login.model.LoginViewModel;
import com.ominfo.hra_app.util.LogUtil;
import com.ominfo.hra_app.util.SharedPref;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RegistrationActivity extends BaseActivity {
    private static final String TAG = "PushNotification";
    private static final String CHANNEL_ID = "109";
    String recentToken = "";

    Context mContext;
    @Inject
    ViewModelFactory mViewModelFactory;
    private LoginViewModel mLoginViewModel;

    private AppDatabase mDb;
    public static boolean activityVisible; // Variable that will check the
    //final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    //IntentFilter intentFilter;
    //MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getDeps().inject(this);
        ButterKnife.bind(this);
        mContext = this;
        injectAPI();
        init();
    }
    private void injectAPI() {
         mLoginViewModel = ViewModelProviders.of(RegistrationActivity.this, mViewModelFactory).get(LoginViewModel.class);
         mLoginViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, "Login"));
    }

    /*
     * ACCESS_FINE_LOCATION permission result
     * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED
                        &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED
                ) {
                    //BaseApplication.getInstance().mService.requestLocationUpdates();
                } else {
                    //Toast.makeText(mContext, getString(R.string.somthing_went_wrong), Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }


    //request camera and storage permission
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mContext.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || mContext.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]
                                {
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                },
                        1000);

            } else {
                // reqPermissionCode();
            }
        } else {
            //reqPermissionCode();
        }
    }


    //show subscription popup
    public void showSubscriptionDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_subscription_details);
        mDialog.setCanceledOnTouchOutside(true);
        RelativeLayout mClose = mDialog.findViewById(R.id.imgCancel);
        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    //show Succes register popup
    public void showThanksForRegisterDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_thanks_for_registering);
        mDialog.setCanceledOnTouchOutside(true);
        RelativeLayout mClose = mDialog.findViewById(R.id.imgCancel);
        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                launchScreen(mContext, LoginActivity.class);
            }
        });
        mDialog.show();
    }

    private void init(){
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        //editTextEmail.setText("hd001");
        //editTextPassword.setText("2437");
        setErrorMSG();
       /* Window window = getWindow();
        View view = window.getDecorView();
        DarkStatusBar.setLightStatusBar(view,this);*/
    }

    // set error if input field is blank
    private void setErrorMSG() {
        /*setErrorMessage(inputEmail, editTextEmail, getString(R.string.val_msg_please_enter_email));
        setErrorMessage(inputPassword, editTextPassword, getString(R.string.val_msg_please_enter_password));*/
    }

    //perform click actions
    @OnClick({R.id.imgInfo,R.id.btnRegister})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgInfo:
                showSubscriptionDialog();
               /* if(isDetailsValid()) {
                    callLoginUserApi();
                }*/
                //launchScreen(mContext, MainActivity.class);
                break;
            case R.id.btnRegister:
                showThanksForRegisterDialog();
                break;


        }
    }

    /* Call Api For Login user and get user details */
    private void callLoginUserApi() {
        if (NetworkCheck.isInternetAvailable(RegistrationActivity.this)) {
           /* LoginRequest mLoginRequest = new LoginRequest();
            mLoginRequest.setUsername(editTextEmail.getEditableText().toString().trim()); //6b07b768-926c-49b6-ac1c-89a9d03d4c3b
            mLoginRequest.setPassword(editTextPassword.getEditableText().toString().trim());
            Gson gson = new Gson();
            String bodyInStringFormat = gson.toJson(mLoginRequest);
            RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), "login");
            RequestBody mRequestBodyTypeImage = RequestBody.create(MediaType.parse("text/plain"), editTextEmail.getEditableText().toString().trim());
            RequestBody mRequestBodyTypeImage1 = RequestBody.create(MediaType.parse("text/plain"), editTextPassword.getEditableText().toString().trim());
            RequestBody mRequestBodyTypeToken = RequestBody.create(MediaType.parse("text/plain"), recentToken);
            mLoginViewModel.hitLoginApi(mRequestBodyType,mRequestBodyTypeImage,mRequestBodyTypeImage1,mRequestBodyTypeToken);
       */ } else {
            LogUtil.printToastMSG(RegistrationActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /*check validations on field*/
    private boolean isDetailsValid() {
       /* if (TextUtils.isEmpty(editTextEmail.getText().toString().trim())) {
            setError(inputEmail, getString(R.string.val_msg_please_enter_email));
            return false;
        } else if (TextUtils.isEmpty(editTextPassword.getText().toString().trim())) {
            setError(inputPassword, getString(R.string.val_msg_please_enter_password));
            return false;
        } else if(!getValidUser()){
            setError(inputEmail, "Please Enter Valid Username.");
            return false;
        }*/
        return true;
    }

    private boolean getValidUser(){
       /* String currentString = editTextEmail.getText().toString().trim();
        if(currentString.substring(0, 2).toLowerCase().equals("hd") ||
                currentString.substring(0, 2).toLowerCase().equals("ho")
                || currentString.substring(0, 2).toLowerCase().equals("jp")){
            return true;
        }
        else {
            return false;
        }*/
        return false; }

    /*Api response */
    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {

            case LOADING:
                showProgressLoader(getString(R.string.scr_message_please_wait));
                break;

            case SUCCESS:
                dismissLoader();
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    if (tag.equalsIgnoreCase("Login")) {
                        LoginResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), LoginResponse.class);
                        if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                            finish();
                            launchScreen(mContext, MainActivity.class);
                            LogUtil.printToastMSG(RegistrationActivity.this, responseModel.getResult().getMessage());
                            SharedPref.getInstance(this).write(SharedPrefKey.IS_LOGGED_IN, true);
                            mDb.getDbDAO().insertLoginData(responseModel.getDetails());
                            AttendanceDaysTable daysTable = new AttendanceDaysTable();
                            daysTable.setLoginDays(responseModel.getResult().getDayData());
                            mDb.getDbDAO().insertAttendanceData(daysTable);
                        } else {
                            LogUtil.printToastMSG(RegistrationActivity.this, responseModel.getResult().getMessage());
                        }
                    }
                }
                break;
            case ERROR:
                dismissLoader();
                LogUtil.printToastMSG(RegistrationActivity.this, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }


}