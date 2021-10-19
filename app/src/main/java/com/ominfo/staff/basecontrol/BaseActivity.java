package com.ominfo.staff.basecontrol;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ominfo.staff.R;
import com.ominfo.staff.database.AppDatabase;
import com.ominfo.staff.deps.DaggerDeps;
import com.ominfo.staff.deps.Deps;
import com.ominfo.staff.dialog.ViewDialog;
import com.ominfo.staff.interfaces.ServiceCallBackInterface;
import com.ominfo.staff.interfaces.SharedPrefKey;
import com.ominfo.staff.network.DynamicAPIPath;
import com.ominfo.staff.network.NetworkAPIServices;
import com.ominfo.staff.network.NetworkCheck;
import com.ominfo.staff.network.NetworkModule;
import com.ominfo.staff.network.NetworkURLs;
import com.ominfo.staff.network.RetroNetworkModule;
import com.ominfo.staff.network.ViewModelFactory;
import com.ominfo.staff.ui.dashboard.adapter.CallManagerAdapter;
import com.ominfo.staff.ui.driver_hisab.model.DriverHisabModel;
import com.ominfo.staff.ui.login.LoginActivity;
import com.ominfo.staff.ui.lr_number.AddLrActivity;
import com.ominfo.staff.ui.lr_number.model.UploadVehicleRecordRequest;
import com.ominfo.staff.ui.lr_number.model.UploadVehicleRecordRespoonse;
import com.ominfo.staff.ui.lr_number.model.VehicleDetailsResultTable;
import com.ominfo.staff.ui.notifications.NotificationsActivity;
import com.ominfo.staff.util.CustomAnimationUtil;
import com.ominfo.staff.util.LogUtil;
import com.ominfo.staff.util.SharedPref;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

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
    Location location;
    public static Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDeps = DaggerDeps.builder().networkModule(new NetworkModule()).build();
        mDeps.inject(this);

    }


    public void setToolbar(AppCompatActivity appCompatActivity, Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
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

    /*
     * call this method to display full screen progress loader
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
        /*try {
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


    public void showSuccessDialog(String msg,boolean status) {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_weight_submitted);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatTextView mTextViewTitle = mDialog.findViewById(R.id.tv_dialogTitle);
        AppCompatButton button = mDialog.findViewById(R.id.okayButton);
        mTextViewTitle.setText(msg);
        if(status){
            button.setVisibility(View.GONE);        }
        else {
            button.setVisibility(View.VISIBLE);
        }
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

    public void initToolbar(int val,Context mContext,int backId,int complaintId,int NotifyId,int logoutId,int callId) {
        AppCompatImageView imgBack = findViewById(backId);
        AppCompatImageView imgComplaint = findViewById(complaintId);
        AppCompatImageView imgNotify = findViewById(NotifyId);
        AppCompatImageView imgLogout = findViewById(logoutId);
        AppCompatImageView imgCall = findViewById(callId);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showCallManagerDialog(mContext);
            }
        });
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
        });
        try {
            imgLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishAffinity();
                    SharedPref.getInstance(mContext).write(SharedPrefKey.IS_LOGGED_IN, false);
                    launchScreen(mContext, LoginActivity.class);
                }
            });
        }catch (Exception e){}

    }


        public void setCallDialor(String number){
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:"+number));
            startActivity(intent);
        }

    /*private void setAdapterForPuranaHisabList(RecyclerView recyclerView,Context mContext,Dialog mDialog ) {
        List<DriverHisabModel> driverHisabModelList =new ArrayList<>();
        driverHisabModelList.add(new DriverHisabModel(getString(R.string.scr_lbl_general_manager),"1"));
        driverHisabModelList.add(new DriverHisabModel(getString(R.string.scr_lbl_dept_manager),"0"));
        driverHisabModelList.add(new DriverHisabModel(getString(R.string.scr_lbl_asst_manager),"0"));

        if (driverHisabModelList.size() > 0) {
            mCallManagerAdapter = new CallManagerAdapter(mContext, driverHisabModelList, new CallManagerAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(String mDataTicket) {
                    mDialog.dismiss();
                    setCallDialor(mDataTicket);
                }
            });
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            recyclerView.setAdapter(mCallManagerAdapter);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.GONE);
        }
    }
*/

    @Override
    public void onPause() {
        super.onPause();
        //unregisterReceiver(receiver);
    }




}
