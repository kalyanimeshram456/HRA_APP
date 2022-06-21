package com.ominfo.hra_app.ui.login;

import android.Manifest;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Chronometer;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.ominfo.hra_app.MainActivity;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.common.customui.OtpEditText;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.interfaces.Constants;
import com.ominfo.hra_app.interfaces.SharedPrefKey;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.login.model.AttendanceDaysTable;
import com.ominfo.hra_app.ui.login.model.GetEmailOtpViewModel;
import com.ominfo.hra_app.ui.login.model.LoginRequest;
import com.ominfo.hra_app.ui.login.model.LoginResponse;
import com.ominfo.hra_app.ui.login.model.LoginViewModel;
import com.ominfo.hra_app.ui.login.model.ResendEmailOtpViewModel;
import com.ominfo.hra_app.ui.login.model.ResetPasswordOtpViewModel;
import com.ominfo.hra_app.ui.login.model.VerifyEmailOtpViewModel;
import com.ominfo.hra_app.ui.registration.RegistrationActivity;
import com.ominfo.hra_app.ui.registration.model.VerifyOtpResponse;
import com.ominfo.hra_app.ui.registration.model.VerifyOtpViewModel;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;
import com.ominfo.hra_app.util.SharedPref;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "PushNotification";
    private static final String CHANNEL_ID = "209";
    String recentToken = "";
    @BindView(R.id.input_password)
    TextInputLayout inputPassword;

    @BindView(R.id.input_email)
    TextInputLayout inputEmail;

    @BindView(R.id.editTextPassword)
    AppCompatAutoCompleteTextView editTextPassword;

    @BindView(R.id.editTextEmail)
    AppCompatAutoCompleteTextView editTextEmail;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;
    @BindView(R.id.tvRegClick)
    AppCompatTextView tvRegClick;
    @BindView(R.id.imgDash)
    AppCompatImageView imgDash;
    boolean statusPrefix = false,statusOtp = false;
    Context mContext;
    @Inject
    ViewModelFactory mViewModelFactory;
    private LoginViewModel mLoginViewModel;
    private VerifyEmailOtpViewModel verifyEmailOtpViewModel;
    private GetEmailOtpViewModel getEmailOtpViewModel;
    private ResendEmailOtpViewModel resendEmailOtpViewModel;
    private ResetPasswordOtpViewModel resetPasswordOtpViewModel;
    private AppDatabase mDb;
    public static boolean activityVisible; // Variable that will check the
    OtpEditText edtOpt;
    AppCompatTextView tvResendOtp;
    AppCompatButton btnGetOtp;
    AppCompatButton btnRegister;
    Chronometer tvCounter;
    Dialog mDialogResetPass;
    AppCompatAutoCompleteTextView AutoComUsername,AutoComEmailId;
    TextInputLayout input_textUsername,input_textEmailId;
    String imei = "000000000000000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getDeps().inject(this);
        ButterKnife.bind(this);
        mContext = this;
        injectAPI();
        //requestPermission();
        init();
        //FirebaseApp.initializeApp();
       /* FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId("1:572524579127:android:744e808675a2f2d10472b2") // Required for Analytics.
                .setProjectId("turanthhr") // Required for Firebase Installations.
                .setApiKey("AIzaSyCyvJS8rtHthsF3l3gPfm4wSrojGsfh2vI") // Required for Auth.
                .build();
        FirebaseApp.initializeApp(this, options, "TuranthHR");*/
        createNotificationChannel();
        getToken();
        tvRegClick.setPaintFlags(tvRegClick.getPaintFlags() |  Paint.UNDERLINE_TEXT_FLAG);
    }
    private void injectAPI() {
         mLoginViewModel = ViewModelProviders.of(LoginActivity.this, mViewModelFactory).get(LoginViewModel.class);
         mLoginViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.action_login));

        verifyEmailOtpViewModel = ViewModelProviders.of(LoginActivity.this, mViewModelFactory).get(VerifyEmailOtpViewModel.class);
        verifyEmailOtpViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_verify_email_otp));

        getEmailOtpViewModel = ViewModelProviders.of(LoginActivity.this, mViewModelFactory).get(GetEmailOtpViewModel.class);
        getEmailOtpViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_get_email_otp));

        resendEmailOtpViewModel = ViewModelProviders.of(LoginActivity.this, mViewModelFactory).get(ResendEmailOtpViewModel.class);
        resendEmailOtpViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_resend_email_otp));

        resetPasswordOtpViewModel = ViewModelProviders.of(LoginActivity.this, mViewModelFactory).get(ResetPasswordOtpViewModel.class);
        resetPasswordOtpViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_forget_password_email));
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

    private void getToken() {
        //FirebaseMessaging.getInstance().deleteToken();
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                //If task is failed then
                if (!task.isSuccessful()) {
                    LogUtil.printLog(TAG, "onComplete: Failed to get the Token");
                }

                //Token
                try {
                    String token = task.getResult();
                    recentToken = token;
                    LogUtil.printLog(TAG, "onComplete: " + token);
                }catch (Exception e){}
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "firebaseNotifChannel";
            String description = "Receve Firebase notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void init(){
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        tvTitle.setText(getString(R.string.scr_lbl_login));
        setErrorMSG();
        Glide.with(this)
                .load(R.drawable.ic_turanthbiz_name)
                .into(imgDash);
        //editTextEmail.setText("KAL025");
        //editTextPassword.setText("3486");
        /*Window window = getWindow();
        View view = window.getDecorView();
        DarkStatusBar.setLightStatusBar(view,this);*/
        try{imei = AppUtils.getIMEINumber(mContext);
        LogUtil.printLog("imei_tetsing ",imei);}catch (Exception e){}
    }

    // set error if input field is blank
    private void setErrorMSG() {
        setErrorMessage(inputEmail, editTextEmail, getString(R.string.val_msg_please_enter_email));
        setErrorMessage(inputPassword, editTextPassword, getString(R.string.val_msg_please_enter_password));
    }

    //perform click actions
    @OnClick({R.id.loginButton,R.id.tvRegClick,R.id.tvResetPass})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.loginButton:
                if(isDetailsValid()) {
                    callLoginUserApi();
                }
                break;
            case R.id.tvRegClick:
                finish();
                launchScreen(mContext, RegistrationActivity.class);
                break;
            case R.id.tvResetPass:
                showResetPassDialog();
                break;
        }
    }

    /* Call Api For Login user and get user details */
    private void callLoginUserApi() {
        if (NetworkCheck.isInternetAvailable(LoginActivity.this)) {
            /*LoginRequest mLoginRequest = new LoginRequest();
            mLoginRequest.setUsername(editTextEmail.getEditableText().toString().trim()); //6b07b768-926c-49b6-ac1c-89a9d03d4c3b
            mLoginRequest.setPassword(editTextPassword.getEditableText().toString().trim());*/
            RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_login);
            RequestBody mRequestBodyTypeImage = RequestBody.create(MediaType.parse("text/plain"), editTextEmail.getEditableText().toString().trim());
            RequestBody mRequestBodyTypeImage1 = RequestBody.create(MediaType.parse("text/plain"), editTextPassword.getEditableText().toString().trim());
            RequestBody mRequestBodyTypeToken = RequestBody.create(MediaType.parse("text/plain"), recentToken);
            RequestBody mRequestBodyTypeimei_no = RequestBody.create(MediaType.parse("text/plain"), imei);

            mLoginViewModel.hitLoginApi(mRequestBodyType,mRequestBodyTypeImage,mRequestBodyTypeImage1,mRequestBodyTypeToken,mRequestBodyTypeimei_no);
        } else {
            LogUtil.printToastMSG(LoginActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /*check validations on field*/
    private boolean isDetailsValid() {
        if (TextUtils.isEmpty(editTextEmail.getText().toString().trim())) {
            setError(0,inputEmail, getString(R.string.val_msg_please_enter_email));
            return false;
        } else if (TextUtils.isEmpty(editTextPassword.getText().toString().trim())) {
            setError(0,inputPassword, getString(R.string.val_msg_please_enter_password));
            return false;
        } /*else if(!getValidUser()){
            setError(0,inputEmail, "Please Enter Valid Username.");
            return false;
        }*/
        return true;
    }

    /* Call Api For Get Otp */
    private void callGetOtpApi() {
        if (NetworkCheck.isInternetAvailable(LoginActivity.this)) {
            RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_email_otp);
            RequestBody mRequestBodyUSername = RequestBody.create(MediaType.parse("text/plain"), AutoComUsername.getText().toString().trim());
            RequestBody mRequestEmail = RequestBody.create(MediaType.parse("text/plain"), AutoComEmailId.getText().toString().trim());
            getEmailOtpViewModel.hitGetEmailOtpApi(mRequestBodyAction, mRequestBodyUSername,mRequestEmail);

        } else {
            LogUtil.printToastMSG(LoginActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Get Otp */
    private void callResetPassowrdOtpApi() {
        if (NetworkCheck.isInternetAvailable(LoginActivity.this)) {
            RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_forget_password_email);
            RequestBody mRequestBodyUSername = RequestBody.create(MediaType.parse("text/plain"), AutoComUsername.getText().toString().trim());
            resetPasswordOtpViewModel.hitResetPasswordOtpApi(mRequestBodyAction, mRequestBodyUSername);

        } else {
            LogUtil.printToastMSG(LoginActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Get Otp */
    private void callVerifyOtpApi() {
        if (NetworkCheck.isInternetAvailable(LoginActivity.this)) {
            RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_verify_email_otp);
            RequestBody mRequestOTP = RequestBody.create(MediaType.parse("text/plain"), edtOpt.getText().toString().trim());
            RequestBody mRequestBodyName = RequestBody.create(MediaType.parse("text/plain"), AutoComUsername.getText().toString().trim());
            verifyEmailOtpViewModel.hitVerifyEmailOtpApi(mRequestBodyAction, mRequestOTP,mRequestBodyName);

        } else {
            LogUtil.printToastMSG(LoginActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /*check validations on field*/
    private boolean isOTPDetailsValid() {
        input_textUsername.setErrorEnabled(false);
        input_textEmailId.setErrorEnabled(false);
        if (TextUtils.isEmpty(AutoComUsername.getText().toString().trim())) {
            setError(0, input_textUsername, "Please enter username");
            return false;
        }else if (TextUtils.isEmpty(AutoComEmailId.getText().toString().trim()) || !Patterns.EMAIL_ADDRESS.matcher(AutoComEmailId.getText().toString().trim()).matches()) {
            setError(0, input_textEmailId, getString(R.string.err_enter_email_id));
            return false;
        }
        return true;
    }

    public void showDiscardDialog() {
        Dialog mDialogDiscardOTP = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogDiscardOTP.setContentView(R.layout.dialog_deactive_account);
        mDialogDiscardOTP.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialogDiscardOTP.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialogDiscardOTP.findViewById(R.id.uploadButton);
        AppCompatButton cancelButton = mDialogDiscardOTP.findViewById(R.id.cancelButton);
        AppCompatTextView tvTitle = mDialogDiscardOTP.findViewById(R.id.tvStart);
        tvTitle.setText("Are you sure you want to discard the reset password process?");
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogResetPass.dismiss();
                mDialogDiscardOTP.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDiscardOTP.dismiss();
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDiscardOTP.dismiss();
            }
        });
        mDialogDiscardOTP.show();
    }

    public void showResetPassDialog() {
        mDialogResetPass = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogResetPass.setContentView(R.layout.dialog_reset_password);
        //mDialogResetPass.setCanceledOnTouchOutside(true);
        RelativeLayout mClose = mDialogResetPass.findViewById(R.id.layCancelNEw);
        edtOpt = mDialogResetPass.findViewById(R.id.edtOpt);
        tvResendOtp = mDialogResetPass.findViewById(R.id.tvResendOtp);
         btnGetOtp = mDialogResetPass.findViewById(R.id.btnGetOtp);
         btnRegister = mDialogResetPass.findViewById(R.id.btnSubmitOTP);
        tvCounter = mDialogResetPass.findViewById(R.id.chronometer);
        AutoComUsername  = mDialogResetPass.findViewById(R.id.AutoComUsername);
        AutoComEmailId  = mDialogResetPass.findViewById(R.id.AutoComEmailId);
        input_textUsername  = mDialogResetPass.findViewById(R.id.input_textUsername);
        input_textEmailId  = mDialogResetPass.findViewById(R.id.input_textEmailId);
        tvCounter.setText("00:00:00");
        tvCounter.setBase(SystemClock.elapsedRealtime());
        tvCounter.stop();
        setTimerMillis(mContext, 0);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mDialogResetPass.dismiss();
                showDiscardDialog();
            }
        });
        tvResendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callResendOtpApi();
            }
        });
        btnGetOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOTPDetailsValid()) {
                    callGetOtpApi();
                    btnGetOtp.setVisibility(View.GONE);
                    edtOpt.setVisibility(View.VISIBLE);
                    tvCounter.setVisibility(View.VISIBLE);
                    setTimerCounter(1);
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callResetPassowrdOtpApi();
            }
        });
        edtOpt.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if(s.length()>3) {
                    callVerifyOtpApi();
                }
            }
        });
        AutoComEmailId.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if(s.length()>5) {
                    if(!TextUtils.isEmpty(AutoComEmailId.getText().toString().trim()) &&
                            Patterns.EMAIL_ADDRESS.matcher(AutoComEmailId.getText().toString().trim()).matches()){
                        input_textEmailId.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_right_accept));
                        int color=Color.GREEN;
                        input_textEmailId.setEndIconTintList(ColorStateList.valueOf(color));
                        setError(1, input_textEmailId, "Entered email is valid.");
                    }
                    else {
                        input_textEmailId.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                        int color=Color.RED;
                        input_textEmailId.setEndIconTintList(ColorStateList.valueOf(color));
                        setError(0, input_textEmailId, getString(R.string.err_enter_email_id));
                    }
                }
                if(s.length()==0){
                    input_textEmailId.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                    int color=Color.RED;
                    input_textEmailId.setEndIconTintList(ColorStateList.valueOf(color));
                    setError(0, input_textEmailId, getString(R.string.err_enter_email_id));
                }
            }
        });
        AutoComUsername.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length()==0){
                    input_textUsername.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                    int color=Color.RED;
                    input_textUsername.setEndIconTintList(ColorStateList.valueOf(color));
                    setError(0, input_textUsername, "Please enter username.");
                }if(s.length()>0){
                    //input_textEmailId.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_right_accept));
                    int color=Color.TRANSPARENT;
                    input_textUsername.setEndIconTintList(ColorStateList.valueOf(color));
                    input_textUsername.setErrorEnabled(false);
                }
            }
        });
        mDialogResetPass.show();
    }

    private boolean getValidUser(){
        String currentString = editTextEmail.getText().toString().trim();
        if(currentString.substring(0, 2).toLowerCase().equals("hd") ||
                currentString.substring(0, 2).toLowerCase().equals("ho")
                || currentString.substring(0, 2).toLowerCase().equals("jp")){
            return true;
        }
        else {
            return false;
        }
    }
    /* Call Api For Get Otp */
    private void callResendOtpApi() {
        if (NetworkCheck.isInternetAvailable(LoginActivity.this)) {
            RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_resend_email_otp);
            RequestBody mRequestBodyUser = RequestBody.create(MediaType.parse("text/plain"), AutoComUsername.getText().toString().trim());
            RequestBody mRequestBodyEmail = RequestBody.create(MediaType.parse("text/plain"), AutoComEmailId.getText().toString().trim());

            resendEmailOtpViewModel.hitResendEmailOtpApi(mRequestBodyAction, mRequestBodyUser,mRequestBodyEmail);

        } else {
            LogUtil.printToastMSG(LoginActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }
    public static void setTimerMillis(Context context, long millis)
    {
        SharedPreferences sp = context.getSharedPreferences(Constants.BANK_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putLong(Constants.BANK_LIST, millis); spe.apply();
    }

    public static long getTimerMillis(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.BANK_LIST, Context.MODE_PRIVATE);
        return sp.getLong(Constants.BANK_LIST, 0);
    }

    private int getCurrentMiliSecondsOfChronometer(int val) {
        int stoppedMilliseconds = 0;
        String chronoText = tvCounter.getText().toString().trim();
        String array[] = chronoText.split(":");

        try {
            if (array.length == 2) {
                stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000 + Integer.parseInt(array[1]) * 1000;
            } else if (array.length == 3) {
                stoppedMilliseconds =
                        Integer.parseInt(array[0]) * 60 * 60 * 1000 + Integer.parseInt(array[1]) * 60 * 1000
                                + Integer.parseInt(array[2]) * 1000;
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return Integer.parseInt(String.format("%02d", stoppedMilliseconds));
    }
    private void startChronometer(int val) {
        long stoppedMilliseconds = getTimerMillis(mContext);
        if(val==0){
            long preV = getTimerMillis(mContext);
            tvCounter.setBase(SystemClock.elapsedRealtime() - preV);
            tvCounter.stop();
        }
        else {
            if (SystemClock.elapsedRealtime() > stoppedMilliseconds) {
                tvCounter.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
            } else {
                tvCounter.setBase(stoppedMilliseconds - SystemClock.elapsedRealtime());
            }
        }
        tvCounter.start();
    }

    public void setTimerCounter(int val){
        startChronometer(val);
        tvCounter.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer cArg) {
                long milliseconds = getCurrentMiliSecondsOfChronometer(val);//getTimerMillis(mContext);
                int seconds = (int) (milliseconds / 1000) % 60 ;
                int minutes = (int) ((milliseconds / (1000*60)) % 60);
                int hours   = (int) ((milliseconds / (1000*60*60)) % 24);

                setTimerMillis(mContext, milliseconds);
                tvCounter.setText(String.format("%02d",hours)+":"+String.format("%02d",minutes)+":"+String.format("%02d",seconds));
                if(tvCounter.getText().toString().equals("00:02:00")){
                    tvResendOtp.setVisibility(View.VISIBLE);
                    //LogUtil.printToastMSG(mContext,"test");
                }
                if(tvCounter.getText().toString().equals("00:05:00")){
                    //tvCounter.setText("00:00:00");
                    //tvCounter.setBase(SystemClock.elapsedRealtime());
                    tvCounter.stop();
                    //setTimerMillis(mContext, 1);
                    tvResendOtp.setVisibility(View.GONE);
                    LogUtil.printToastMSG(mContext,"Something went wrong, Please try again later.");
                }
            }
        });

    }

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
                    try{if (tag.equalsIgnoreCase(DynamicAPIPath.action_login)) {
                        LoginResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), LoginResponse.class);
                        if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                            finish();
                            launchScreen(mContext, MainActivity.class);
                            try {
                                mDb.getDbDAO().deleteLoginData();
                                mDb.getDbDAO().insertLoginData(responseModel.getDetails());
                                mDb.getDbDAO().deleteLocationData();
                                mDb.getDbDAO().deleteAttendanceData();
                                AttendanceDaysTable daysTable = new AttendanceDaysTable();
                                daysTable.setLoginDays(responseModel.getResult().getDayData());
                                mDb.getDbDAO().insertAttendanceData(daysTable);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            SharedPref.getInstance(this).write(SharedPrefKey.IS_LOGGED_IN, true);
                            //LogUtil.printSnackBar(mContext, Color.GREEN,findViewById(android.R.id.content),responseModel.getResult().getMessage());
                            LogUtil.printToastMSG(LoginActivity.this, responseModel.getResult().getMessage());

                        } else {
                            LogUtil.printToastMSG(LoginActivity.this, responseModel.getResult().getMessage());
                        }
                    }
                    }catch (Exception e){}
                    try{if (tag.equalsIgnoreCase(DynamicAPIPath.action_verify_email_otp)) {
                        VerifyOtpResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), VerifyOtpResponse.class);
                        if (responseModel != null && responseModel.getResult().getStatus().equals("Success")) {
                            statusOtp = true;
                            btnRegister.setVisibility(View.VISIBLE);
                            tvCounter.setText("00:00:00");
                            tvCounter.setBase(SystemClock.elapsedRealtime());
                            tvCounter.stop();
                            setTimerMillis(mContext, 0);
                            LogUtil.printToastMSG(LoginActivity.this, responseModel.getResult().getMessage());
                        }
                        else {
                            statusOtp = false;
                            LogUtil.printToastMSG(LoginActivity.this, responseModel.getResult().getMessage());
                        }
                    }
                    }catch (Exception e){}
                    try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.action_get_sms_otp) || tag.equalsIgnoreCase(DynamicAPIPath.action_resend_email_otp)
                        ) {
                            VerifyOtpResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), VerifyOtpResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                LogUtil.printToastMSG(LoginActivity.this, responseModel.getResult().getMessage());
                            }
                            else { LogUtil.printToastMSG(LoginActivity.this, responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){}
                    if (tag.equalsIgnoreCase(DynamicAPIPath.action_forget_password_email)) {
                        VerifyOtpResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), VerifyOtpResponse.class);
                        if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                            mDialogResetPass.dismiss();
                            showSuccessDialog( responseModel.getResult().getMessage(),false,LoginActivity.this);
                        }
                        else { LogUtil.printToastMSG(LoginActivity.this, responseModel.getResult().getMessage());
                        }
                    }
                }
                break;
            case ERROR:
                dismissLoader();
                LogUtil.printToastMSG(LoginActivity.this, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }


}