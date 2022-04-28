package com.ominfo.crm_solution.basecontrol;

import static com.ominfo.crm_solution.ui.attendance.StartAttendanceActivity.tvCurrLocation;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ominfo.crm_solution.MainActivity;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.alarm.get_count.DeleteReminderViewModel;
import com.ominfo.crm_solution.alarm.get_count.GetCountViewModel;
import com.ominfo.crm_solution.common.BackgroundAttentionService;
import com.ominfo.crm_solution.common.CounterClass;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.deps.DaggerDeps;
import com.ominfo.crm_solution.deps.Deps;
import com.ominfo.crm_solution.dialog.ViewDialog;
import com.ominfo.crm_solution.interfaces.DialogCallbacks;
import com.ominfo.crm_solution.interfaces.ErrorCallbacks;
import com.ominfo.crm_solution.interfaces.ServiceCallBackInterface;
import com.ominfo.crm_solution.interfaces.SharedPrefKey;
import com.ominfo.crm_solution.network.ApiResponse;
import com.ominfo.crm_solution.network.DynamicAPIPath;
import com.ominfo.crm_solution.network.NetworkCheck;
import com.ominfo.crm_solution.network.NetworkModule;
import com.ominfo.crm_solution.network.ViewModelFactory;
import com.ominfo.crm_solution.ui.attendance.StartAttendanceActivity;
import com.ominfo.crm_solution.ui.attendance.model.LocationPerHourRequest;
import com.ominfo.crm_solution.ui.attendance.model.LocationPerHourResponse;
import com.ominfo.crm_solution.ui.attendance.model.LocationPerHourTable;
import com.ominfo.crm_solution.ui.attendance.model.LocationPerHourViewModel;
import com.ominfo.crm_solution.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.my_account.model.ApplyLeaveRequest;
import com.ominfo.crm_solution.ui.notifications.NotificationsActivity;
import com.ominfo.crm_solution.ui.notifications.model.DeleteNotificationViewModel;
import com.ominfo.crm_solution.ui.notifications.model.NotificationResponse;
import com.ominfo.crm_solution.ui.notifications.model.NotificationViewModel;
import com.ominfo.crm_solution.ui.sales_credit.fragment.SalesCreditFragment;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.CustomAnimationUtil;
import com.ominfo.crm_solution.util.DialogUtils;
import com.ominfo.crm_solution.util.LogUtil;
import com.ominfo.crm_solution.util.SharedPref;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/* base activity for all activity
* please use this naming convention
*
 public static final int SOME_CONSTANT = 42;
    public int publicField;
    private static MyClass sSingleton;
    int mPackagePrivate;
    private int mPrivate;
    protected int mProtected;
    boolean isBoolean;
    boolean hasBoolean;
    View mMyView;
*
*
* */

public class BaseActivity extends AppCompatActivity implements ServiceCallBackInterface, LifecycleObserver {
    private Deps mDeps;
    private Window mWindow;
    private CustomAnimationUtil customAnimationUtil;
    private ViewDialog viewDialog;
    private boolean mBound = false;
    @Inject
    ViewModelFactory mViewModelFactory;
    private NotificationViewModel notificationViewModel;
    private LocationPerHourViewModel locationPerHourViewModel;
    Location location;
    public Context context;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;
    boolean isNotify = false;
    private AppDatabase mDb;
    public static AppCompatTextView imgNotifyCount;
    private MyReceiver myReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDeps = DaggerDeps.builder().networkModule(new NetworkModule(this)).build();
        mDeps.inject(this);
        context = this;
        injectAPI();
        mDb =BaseApplication.getInstance(this).getAppDatabase();
        myReceiver = new MyReceiver();

    }


    public void setToolbar(AppCompatActivity appCompatActivity, Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    private void injectAPI() {
        notificationViewModel = ViewModelProviders.of(BaseActivity.this, mViewModelFactory).get(NotificationViewModel.class);
        notificationViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_NOTIFICATION));

        locationPerHourViewModel = ViewModelProviders.of(BaseActivity.this, mViewModelFactory).get(LocationPerHourViewModel.class);
        locationPerHourViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_LOCATION_PER_HOUR));
    }

    /**
     * Receiver for broadcasts sent by {@link }.
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(BackgroundAttentionService.EXTRA_LOCATION);
            tvCurrLocation.setText("Current Location : Fetching.....");
            if (location != null) {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    tvCurrLocation.setText("Current Location : " + address);
                    SharedPref.getInstance(getBaseContext()).write(SharedPrefKey.ATTENTION_LOC_LAT, String.valueOf(location.getLatitude()));
                    SharedPref.getInstance(getBaseContext()).write(SharedPrefKey.ATTENTION_LOC_LONG, String.valueOf(location.getLongitude()));
                    SharedPref.getInstance(getBaseContext()).write(SharedPrefKey.ATTENTION_LOC_TITLE, address);
                    Boolean iSTimer = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.CHECK_OUT_ENABLED, false);
                    if(iSTimer) {
                        LoginTable loginTable = mDb.getDbDAO().getLoginData();
                        if (loginTable != null) {
                            RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_location_per_hour);
                            RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                            RequestBody mRequestBodyDate = RequestBody.create(MediaType.parse("text/plain"), AppUtils.getCurrentDateInyyyymmdd());
                            RequestBody mRequestBodyLat = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(location.getLatitude()));
                            RequestBody mRequestBodyLong = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(location.getLongitude()));
                            RequestBody mRequestBodyStartTime = RequestBody.create(MediaType.parse("text/plain"), AppUtils.getCurrentTimeIn24hr());
                            Random rnd = new Random();
                            int number = rnd.nextInt(99999);
                            // this will convert any number sequence into 6 character.
                            String geneatedId = String.format("%05d", number);
                            RequestBody mReqToken = RequestBody.create(MediaType.parse("text/plain"), geneatedId);
                            LocationPerHourTable locationPerHourTable = new LocationPerHourTable();
                            locationPerHourTable.setAction(DynamicAPIPath.action_location_per_hour);
                            locationPerHourTable.setEmpId(loginTable.getEmployeeId());
                            locationPerHourTable.setDate(AppUtils.getCurrentDateInyyyymmdd());
                            locationPerHourTable.setLatitude(String.valueOf(location.getLatitude()));
                            locationPerHourTable.setLongitude(String.valueOf(location.getLongitude()));
                            locationPerHourTable.setStartTime(AppUtils.getCurrentTimeIn24hr());
                            locationPerHourTable.setRequestedToken(geneatedId);
                            mDb.getDbDAO().insertLocationPerHour(locationPerHourTable);
                            //int count = mDb.getDbDAO().getCountLocation();
                            //LogUtil.printToastMSG(context, String.valueOf(count));
                            if (NetworkCheck.isInternetAvailable(context)) {
                                LocationPerHourRequest locationPerHourRequest = new LocationPerHourRequest();
                                locationPerHourRequest.setAction(mRequestBodyAction);
                                locationPerHourRequest.setEmpId(mRequestBodyTypeEmpId);
                                locationPerHourRequest.setDate(mRequestBodyDate);
                                locationPerHourRequest.setLatitude(mRequestBodyLat);
                                locationPerHourRequest.setLongitude(mRequestBodyLong);
                                locationPerHourRequest.setStartTime(mRequestBodyStartTime);
                                locationPerHourRequest.setRequestedToken(mReqToken);
                                locationPerHourViewModel.hitLocationPerHourApi(locationPerHourRequest);
                            } else {
                                LogUtil.printToastMSG(context, "Something is wrong.");
                            }
                        } else {
                            LogUtil.printToastMSG(context, getString(R.string.err_msg_connection_was_refused));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    // Self explanatory method
    public boolean checkForInternet() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }
    //starting foreground service and registering broadcast for lat long
    public void startLocationService() {
        startService(new Intent(this, BackgroundAttentionService.class));
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(BackgroundAttentionService.ACTION_BROADCAST));
    }
    void loadData(){
        // do sth
    }

    void updateUI(){
        // No internet connection, update the ui and warn the user
    }

    public void setFontInCollapseToolbar(CollapsingToolbarLayout mCollapsingToolbarLayout) {
        /*Typeface typeface = ResourcesCompat.getFont(this, R.font.sf_pro_display_bold);
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(typeface);
        mCollapsingToolbarLayout.setExpandedTitleTypeface(typeface);*/
    }

    public Deps getDeps() {
        return mDeps;
    }

    /**
     * show error message according to error type
     */
    /**
     * @param view     view that will be animated
     * @param duration for how long in ms will it shake
     * @param offset   start offset of the animation
     * @return returns the same view with animation properties
     */
    public static View makeMeShake(View view, int duration, int offset) {
        Animation anim = new TranslateAnimation(-offset, offset, 0, 0);
        anim.setDuration(duration);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(5);
        view.startAnimation(anim);
        return view;
    }

    /* Call Api For Location per hour */
    private void callApplyLeaveApi() {

    }
    /*  openContactSupportEmail(this, getString(R.string.app_name),
                       "sendbits@gmail.com", "");*/
    /*send email*/
    public static void openContactSupportEmail(Context context, String subject, String email, String body) {
        /*Create an ACTION_SEND Intent*/
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        /*This will be the actual content you wish you share.*/
        String shareBody = "Here is the share content body";
        /*The type of the content is text, obviously.*/
        intent.setType("text/plain");
        /*Applying information Subject and Body.*/
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, body);
        /*Fire!*/
        context.startActivity(Intent.createChooser(intent, "Share via"));
    }

    /**
     * show error when edit text is empty
     *
     * @param textInputLayout
     * @param error
     */
    public void showError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        textInputLayout.requestFocus();
        makeMeShake(textInputLayout, 20, 5);
    }


    // Monitors the state of the connection to the service.
    public ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBound = true;
            isServiceActive(true, service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            isServiceActive(false, null);

        }
    };

    public void moveFragment(Context mContext,Fragment fragmentMove){
        Fragment fragment = fragmentMove;
        FragmentManager fragmentManager = ((MainActivity)mContext).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framecontainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void isServiceActive(boolean is, IBinder service) {
    }

    @Override
    public void isServiceActiveCall(boolean is, IBinder service) {
//        if (BaseApplication.getInstance().isApplicationBackgruond || mBound) {
//            unbindService(mServiceConnection);
//            mBound = false;
//        }
    }


    public void setErrorMessage(TextInputLayout inputLayouts, AppCompatEditText editText, String errorMsg) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (editText.getEditableText().toString().trim().isEmpty()) {
                    inputLayouts.setError(errorMsg);
                    editText.requestFocus();
                    makeMeShake(editText, 20, 5);
                } else {
                    inputLayouts.setErrorEnabled(false);
                }
            }
        });
    }


  /*   * call this method to display full screen progress loader
     * */
    public void showProgressLoader(String message) {
        try {
            if (viewDialog == null) {
                viewDialog = new ViewDialog(this);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                viewDialog.showDialog(message);
            }

        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    public void showSmallProgressBar(FrameLayout mProgressBarHolder) {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        mProgressBarHolder.setAnimation(inAnimation);
        mProgressBarHolder.setVisibility(View.VISIBLE);
    }

    public void dismissSmallProgressBar(FrameLayout mProgressBarHolder) {
        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        mProgressBarHolder.setAnimation(outAnimation);
        mProgressBarHolder.setVisibility(View.GONE);
    }

    /*
     * call this method to dismiss progress loader
     * */
    public void dismissLoader() {
        try {
            if (viewDialog != null) {
                viewDialog.hideDialog();
            }
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
       /* try {
            if (!this.isFinishing() && mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    /*launch screen*/
    public void launchScreenClear(Context mContext, Class mActivity){
        Intent intent=new Intent(mContext,mActivity);
        startActivity(intent);
        finish();
    }

    /*launch screen*/
    public void launchScreen(Context mContext, Class mActivity){
        Intent intent=new Intent(mContext,mActivity);
        startActivity(intent);
    }


    /*set error in input field if invalid*/
    public void setError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setError(error);
        textInputLayout.requestFocus();
        if (customAnimationUtil == null) {
            customAnimationUtil = new CustomAnimationUtil(this);
        }
        //customAnimationUtil.showErrorEditTextAnimation(textInputLayout, R.anim.shake);
    }

    public Window getmWindow() {
        return mWindow;
    }


    public void setErrorMessageWithNoInputLayout(final AppCompatEditText editText, final String errorMsg) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isValidateFieldForNoInputLayout(editText, errorMsg);
//                if (editText.getText().toString().trim().isEmpty()) {
//                    inputLayouts.setError(errorMsg);
//                    requestFocus(editText);
//                } else {
//                    inputLayouts.setErrorEnabled(false);
//                }
            }
        });
    }
    public boolean isValidateFieldForNoInputLayout(AppCompatEditText editText, String errorMsg) {
        return true;
    }

    public void transperentStatusBar(Context context) {
        //make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // edited here
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }



    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void appInResumeState() {
//        Toast.makeText(this,"In Foreground",Toast.LENGTH_LONG).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void appInPauseState() {
//        Toast.makeText(this,"In Background",Toast.LENGTH_LONG).show();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void appInStopState() {
        try {
            if (BaseApplication.getInstance().isApplicationBackgruond || mBound) {
                unbindService(mServiceConnection);
                mBound = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * foreground service code
     */
    @Override
    protected void onStart() {
        super.onStart();
    }


    public void showSuccessDialog(String msg,boolean status,Activity activity) {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_weight_submitted);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatTextView mTextViewTitle = mDialog.findViewById(R.id.tv_dialogTitle);
        AppCompatButton button = mDialog.findViewById(R.id.okayButton);
        mTextViewTitle.setText(msg);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
                if(!status) {
                    activity.finish();
                }
            }
        }, 1100);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                finish();
            }
        });
        //AppCompatButton appCompatButton = mDialog.findViewById(R.id.btn_done);
        //LinearLayoutCompat appCompatLayout = mDialog.findViewById(R.id.layPopup);
        /*appCompatButton.setVisibility(View.VISIBLE);
        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(mEditTextNote.getText().toString()))
                {
                    LogUtil.printToastMSG(mContext,getString(R.string.val_msg_please_enter_note));
                }
                else {
                    callUpdateMarkApi(mEditTextNote.getText().toString());
                    mDialog.dismiss();
                }
            }
        });*/
        mDialog.show();
    }

    public void showUploadDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_upload_total_hisab);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.uploadButton);
        AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                //showSuccessDialog(getString(R.string.msg_kharcha_uploaded));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 700);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    public void initToolbar(int val,Context mContext,int backId,int complaintId,int NotifyId,AppCompatTextView NotifyCount,int logoutId,int callId) {
        AppCompatImageView imgBack = findViewById(backId);
        AppCompatImageView imgComplaint = findViewById(complaintId);
        AppCompatImageView imgNotify = findViewById(NotifyId);
        AppCompatImageView imgCall = findViewById(callId);
        imgNotifyCount = NotifyCount;
        //imgNotifyCount.invalidate();// = null;
        //LogUtil.printToastMSG(mContext,"counttt");
        if(val!=6){
        setNotifyCount();}
        /*if(logoutId!=0) {
            LinearLayoutCompat imgLogout = findViewById(logoutId);
            imgLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //LogUtil.printToastMSG(mContext,"clicked");
                    Fragment fragment = new DashboardFragment();
                    FragmentManager fragmentManager = ((MainActivity)mContext).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framecontainer, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }*/

        try {
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(val==5){
                        Fragment fragment = new DashboardFragment();
                        FragmentManager fragmentManager = ((MainActivity)mContext).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.add(R.id.framecontainer, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                    else if(val==6){
                        showVisitCloseAlertDialog(mContext);
                    }
                    else {
                    finish();}
                }
            });
        }catch (Exception e){e.printStackTrace();}
        try {
            imgCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //showCallManagerDialog(mContext);
                }
            });
        }catch (Exception e){e.printStackTrace();}
        try{
        imgComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (val == 1) {
                    finish();
                    //launchScreen(mContext, ComplaintsActivity.class);
                } else {
                    //launchScreen(mContext, ComplaintsActivity.class);
                }
            }
        });
        }catch (Exception e){e.printStackTrace();}
        try{
        imgNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (val == 1) {
                    finish();
                    launchScreen(mContext, NotificationsActivity.class);
                } else {
                    launchScreen(mContext, NotificationsActivity.class);
                }
            }
        });}catch (Exception e){e.printStackTrace();}
        try {
           /* imgLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishAffinity();
                    SharedPref.getInstance(mContext).write(SharedPrefKey.IS_LOGGED_IN, false);
                    launchScreen(mContext, LoginActivity.class);
                }
            });*/
        }catch (Exception e){}

    }


        public void setCallDialor(String number){
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+number));
            startActivity(intent);
        }


    @Override
    public void onPause() {
        super.onPause();
        //unregisterReceiver(receiver);
    }

    public void showVisitCloseAlertDialog(Context mContext) {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_logout);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatTextView tvTitle = mDialog.findViewById(R.id.tvStart);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.uploadButton);
        AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);
        tvTitle.setText("Do you really want to discard this visit ?");
        okayButton.setText("Discard");
        cancelButton.setText("Keep");
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                String visit = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.VISIT_NO, "");
                SharedPref.getInstance(mContext).write(SharedPrefKey.VISIT_NO,"");
                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    public void setNotifyCount(){
        imgNotifyCount.setVisibility(View.INVISIBLE);
        isNotify=true;
        callNotificationApi();
    }
    /* Call Api Notification */
    private void callNotificationApi() {
        if (NetworkCheck.isInternetAvailable(BaseActivity.this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_notification);
                RequestBody mRequestBodyTypeCompId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                notificationViewModel.hitNotificationApi(mRequestBodyType,mRequestBodyTypeCompId
                        ,mRequestBodyTypeEmployee);
            }
        } else {
            LogUtil.printToastMSG(BaseActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }
    /**
     * show error message according to error type
     */
    public void errorMessage(String errorMSG, ErrorCallbacks errorCallbacks) {
        try {
            if (errorMSG != null && !errorMSG.isEmpty()) {
                DialogUtils.showErrorDialog(this,
                        "", errorMSG, false, new DialogCallbacks() {
                            @Override
                            public void onPositiveClick() {
                                errorCallbacks.onOkClick();
                            }

                            @Override
                            public void onNegativeClick() {

                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*Api response */
    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {

            case LOADING:
                if(!isNotify) {
                    //showProgressLoader(getString(R.string.scr_message_please_wait));
                }
                break;

            case SUCCESS:
                //dismissLoader();
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_NOTIFICATION)) {
                            NotificationResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), NotificationResponse.class);
                            if (responseModel != null/* && responseModel.getResult().getStatus().equals("success")*/) {
                                try {
                                    isNotify = false;
                                    if(responseModel.getResult().getNotifdata().size()>0){
                                        imgNotifyCount.setVisibility(View.VISIBLE);
                                    }
                                    else{
                                        imgNotifyCount.setVisibility(View.INVISIBLE);
                                    }
                                    SharedPref.getInstance(this).write(SharedPrefKey.IS_NOTIFY_COUNT, String.valueOf(responseModel.getResult().getNotifdata().size()));
                                    imgNotifyCount.setText(String.valueOf(responseModel.getResult().getNotifdata().size()));
                                }catch (Exception e){
                                    LogUtil.printToastMSG(this,e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}
                    try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_LOCATION_PER_HOUR)) {
                            LocationPerHourResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), LocationPerHourResponse.class);
                            if (responseModel != null/* && responseModel.getResult().getStatus().equals("success")*/) {
                               // try {
                                    LogUtil.printToastMSG(this,responseModel.getResult().getMessage());
                                    mDb.getDbDAO().deleteLocationById(responseModel.getResult().getRequestedToken());
                                    //int count = mDb.getDbDAO().getCountLocation();
                                    //LogUtil.printToastMSG(context,"after "+String.valueOf(count));
                               /* }catch (Exception e){
                                    LogUtil.printToastMSG(this,responseModel.getResult().getMessage());
                                    e.printStackTrace();
                                }*/
                            }
                       }
                    }catch (Exception e){
                        LogUtil.printToastMSG(this,"issue");
                       // e.printStackTrace();
                    }
                }
                break;
            case ERROR:
                //dismissLoader();
                //LogUtil.printToastMSG(DashbooardActivity.this, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }

}
