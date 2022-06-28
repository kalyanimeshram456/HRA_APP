package com.ominfo.hra_app.ui.attendance;

import static com.ominfo.hra_app.interfaces.Constants.INTERVAL_M;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.common.BackgroundAttentionService;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.interfaces.Constants;
import com.ominfo.hra_app.interfaces.SharedPrefKey;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.attendance.model.GetAttendanceAtt;
import com.ominfo.hra_app.ui.attendance.model.GetAttendanceRequest;
import com.ominfo.hra_app.ui.attendance.model.GetAttendanceResponse;
import com.ominfo.hra_app.ui.attendance.model.MarkAttendanceResponse;
import com.ominfo.hra_app.ui.attendance.model.GetAttendanceViewModel;
import com.ominfo.hra_app.ui.attendance.model.UpdateAttendanceRequest;
import com.ominfo.hra_app.ui.attendance.model.UpdateAttendanceResponse;
import com.ominfo.hra_app.ui.attendance.model.UpdateAttendanceViewModel;
import com.ominfo.hra_app.ui.attendance.ripple_effect.RippleBackground;
import com.ominfo.hra_app.ui.dashboard.model.DashModel;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.visit_report.activity.AddLocationActivity;
import com.ominfo.hra_app.ui.visit_report.model.GetVisitNoViewModel;
import com.ominfo.hra_app.ui.visit_report.model.VisitNoResponse;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;
import com.ominfo.hra_app.util.SharedPref;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class StartAttendanceActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Context mContext;
    String transactionId = "";
    List<DashModel> dashboardList = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();
    @BindView(R.id.tvStartVisit)
    AppCompatTextView textViewVisit;
    @BindView(R.id.visitNo)
    AppCompatTextView textVisitNo;
    @BindView(R.id.tvCheckOutTime)
    AppCompatTextView tvCheckOutTime;
    @BindView(R.id.tvCheckInTime)
    AppCompatTextView tvCheckInTime;
    /* @BindView(R.id.startVisitButton)
     AppCompatButton mButtonStartVisit;*/
    @BindView(R.id.imgChecked)
    AppCompatImageView imgChecked;
    List<GetAttendanceAtt> getAttendanceAttList = new ArrayList<>();
    GoogleApiClient googleApiClient;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    String offlat = "", offlng = "",currlat = "", currlng = "";
    boolean mStartVisit = false;
    @Inject
    ViewModelFactory mViewModelFactory;
    private GetVisitNoViewModel getVisitNoViewModel;
    private GetAttendanceViewModel getAttendanceViewModel;
    private UpdateAttendanceViewModel updateAttendanceViewModel;
    private AppDatabase mDb;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.relRound)
    RelativeLayout relRound;
    @BindView(R.id.tvCheckInName)
    AppCompatTextView tvCheckInName;
    @BindView(R.id.tvOfcLocation)
    AppCompatTextView tvOfcLocation;
    public static AppCompatTextView tvCurrLocation;
    public String addressCurr = "Unknown", addressOff = "";
    @BindView(R.id.layBottomCheckOut)
    LinearLayoutCompat layBottomCheckOut;
    @BindView(R.id.imgCheckInBg)
    ConstraintLayout imgCheckInBg;
    public static  String result = "temp";
    String distanceInMeters = "51";
    String locationDisable = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_attendance);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mContext = this;
        getDeps().inject(this);
        ButterKnife.bind(this);
        injectAPI();
        init();

    }

    private void init() {
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        // initialise tha layout
        setToolbar();
        Glide.with(mContext)
                .load(R.drawable.attendance_bg)
                .into(new CustomTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        imgCheckInBg.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        tvCurrLocation = findViewById(R.id.tvCurrLocation);
        final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content);
        rippleBackground.startRippleAnimation(0, mContext);

        imgChecked.setVisibility(View.GONE);
        showSmallProgressBar(mProgressBarHolder);
        SharedPref.getInstance(mContext).write(SharedPrefKey.ATTENTION_LOC_LAT, "0.0");
        SharedPref.getInstance(mContext).write(SharedPrefKey.ATTENTION_LOC_LONG, "0.0");
        SharedPref.getInstance(mContext).write(SharedPrefKey.ENTERED_VISIT_LAT, "0.0");
        SharedPref.getInstance(mContext).write(SharedPrefKey.ENTERED_VISIT_LNG, "0.0");
        SharedPref.getInstance(mContext).write(SharedPrefKey.ATTENTION_LOC_TITLE, getString(R.string.scr_lbl_unavailable));
        SharedPref.getInstance(mContext).write(SharedPrefKey.LOCATION_ENTERED_TXT, getString(R.string.scr_lbl_unavailable));
        if (!isGPSEnabled(mContext)) {
            requestPermission();
        } else {
            getLocation();
        }
        prepareAllData();
        callGetAttendanceApi();

    }

    private void prepareAllData() {
        LoginTable loginTable = mDb.getDbDAO().getLoginData();
        if (loginTable != null) {
            tvCheckInName.setText("Hi " + loginTable.getName() + " ! \nPlease wait...");
        }

        String dataDate = AppUtils.getCurrentDateTime();
        String mDate = AppUtils.convertAlarmDate(dataDate);
        textViewVisit.setText(AppUtils.getCurrentTime());
        String startTime = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENDANCE_CHECKIN_TIME, "00:00:00");
        //tvCheckInTime.setText(AppUtils.convert24to12Attendance(startTime));
        //tvCheckOutTime.setText(AppUtils.getCurrentTime());
        textVisitNo.setText(mDate);
       /* String locationLat = SharedPref.getInstance(mContext).read(SharedPrefKey.ENTERED_VISIT_LAT, "0.0");
        String locationLng = SharedPref.getInstance(mContext).read(SharedPrefKey.ENTERED_VISIT_LNG, "0.0");
        String location = SharedPref.getInstance(mContext).read(SharedPrefKey.LOCATION_ENTERED_TXT, "Not Available");
        //SharedPref.getInstance(getBaseContext()).write(SharedPrefKey.ATTENTION_LOC_TITLE, location);
        result=location;//data.getStringExtra("result");
        //String str = "<b>"+result+"</b>";
        //LogUtil.printToastMSG(mContext,result);
        tvCurrLocation.setText("Current Location : "+result); //Current Location : Fetching...
        currlat = locationLat;
        currlng = locationLng;*/
    }

    @Override
    protected void onStart() {
        super.onStart();
            startLocationService();
    }

    private void doBounceAnimation(View targetView) {
        Interpolator interpolator = new Interpolator() {
            @Override
            public float getInterpolation(float v) {
                return getPowOut(v, 2);//Add getPowOut(v,3); for more up animation
            }
        };
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "translationY", 0, 25, 0);
        animator.setInterpolator(interpolator);
        //animator.setStartDelay(200);
        animator.setDuration(800);
        animator.setRepeatCount(1);
        animator.start();
    }

    private void doBounceAnimationOnce(View targetView) {
        Interpolator interpolator = new Interpolator() {
            @Override
            public float getInterpolation(float v) {
                return getPowOut(v, 1);//Add getPowOut(v,3); for more up animation
            }
        };
        ObjectAnimator animator = ObjectAnimator.ofFloat(targetView, "translationY", 0, 25, 0);
        animator.setInterpolator(interpolator);
        //animator.setStartDelay(200);
        animator.setDuration(200);
        animator.setRepeatCount(0);
        animator.start();
    }

    private float getPowOut(float elapsedTimeRate, double pow) {
        return (float) ((float) 1 - Math.pow(1 - elapsedTimeRate, pow));
    }

    private void injectAPI() {
        getAttendanceViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetAttendanceViewModel.class);
        getAttendanceViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_GET_ATTENDANCE));

        updateAttendanceViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UpdateAttendanceViewModel.class);
        updateAttendanceViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_UPDATE_ATTENDANCE));
    }

    /* Call Api For get Attendance */
    private void callGetAttendanceApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_attendance);
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                RequestBody mRequestBodyFDate = RequestBody.create(MediaType.parse("text/plain"), AppUtils.getCurrentDateInyyyymmdd());
                RequestBody mRequestBodyTDate = RequestBody.create(MediaType.parse("text/plain"), AppUtils.getCurrentDateInyyyymmdd());
                String startlocationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LAT, "0.0");
                String startlocationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LONG, "0.0");
                String mTime_ = AppUtils.getCurrentTimeIn24hr();
                RequestBody mRequestToken = RequestBody.create(MediaType.parse("text/plain"), loginTable.getToken());
                RequestBody mRequestComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                SharedPref.getInstance(getBaseContext()).write(SharedPrefKey.ATTENDANCE_CHECKIN_TIME, mTime_);
                GetAttendanceRequest markAttendanceRequest = new GetAttendanceRequest();
                markAttendanceRequest.setAction(mRequestBodyAction);
                markAttendanceRequest.setEmpId(mRequestBodyTypeEmpId);
                markAttendanceRequest.setFrm_date(mRequestBodyFDate);
                markAttendanceRequest.setTo_date(mRequestBodyTDate);
                markAttendanceRequest.setToken(mRequestToken);
                markAttendanceRequest.setCompany_ID(mRequestComId);
                getAttendanceViewModel.hitGetAttendanceApi(markAttendanceRequest);
            } else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Update Attendance */
    private void callUpdateAttendanceApi(int isUpdate) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                UpdateAttendanceRequest markAttendanceRequest = new UpdateAttendanceRequest();
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_update_attendance);
                RequestBody mRequestEmailID = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                RequestBody mRequestdate = RequestBody.create(MediaType.parse("text/plain"), AppUtils.getCurrentDateInyyyymmdd());

                String cTime = AppUtils.getCurrentTimeIn24hr();
                String clocationLat = "0.0";
                String clocationLng = "0.0";
                String dataLoc = "Unavailable";
                try{
                boolean isLocationFetched = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.NOT_FETCHED, false);
                if(!isLocationFetched){
                     clocationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LAT, "0.0");
                     clocationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LONG, "0.0");
                     dataLoc = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_TITLE, getString(R.string.scr_lbl_unavailable));
                }else{
                     clocationLat = SharedPref.getInstance(mContext).read(SharedPrefKey.ENTERED_VISIT_LAT, "0.0");
                     clocationLng = SharedPref.getInstance(mContext).read(SharedPrefKey.ENTERED_VISIT_LNG, "0.0");
                     dataLoc = result;
                }}catch (Exception e){}
                RequestBody mRequestStartTime = RequestBody.create(MediaType.parse("text/plain"), cTime);
                RequestBody mRequestBodyAddr = RequestBody.create(MediaType.parse("text/plain"), dataLoc==null?"Unavailable":dataLoc);
                RequestBody mRequestBodyStartLat = RequestBody.create(MediaType.parse("text/plain"), clocationLat==null?"0.0":clocationLat);
                RequestBody mRequestBodyStartLong = RequestBody.create(MediaType.parse("text/plain"), clocationLng==null?"0.0":clocationLng);
                String isTime = "0";
                if(isUpdate==0) {
                    String timeApi = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENDANCE_START_TIME, "10:00:00");
                    LogUtil.printLog("test_date_att",timeApi);
                    String currDate = AppUtils.getCurrentDateTime_() + " " + AppUtils.getCurrentTimeIn24hr();
                    String savedDate = AppUtils.getCurrentDateTime_() + " " + timeApi;
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    try {
                        SimpleDateFormat sdf30 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        Date date = sdf30.parse(savedDate);
                        Calendar calendar30 = Calendar.getInstance();
                        calendar30.setTime(date);
                        calendar30.add(Calendar.MINUTE, 10);
                        Date date1 = sdf.parse(sdf30.format(calendar30.getTime()));//saved + 15
                        Date date2 = sdf.parse(currDate); //curr
                        if (date2.compareTo(date1)== 1) {
                            isTime = "1";
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    RequestBody mRequestIsLate = RequestBody.create(MediaType.parse("text/plain"), isTime);
                    markAttendanceRequest.setStart_time(mRequestStartTime);
                    markAttendanceRequest.setStart_latitude(mRequestBodyStartLat);
                    markAttendanceRequest.setStart_longitude(mRequestBodyStartLong);
                    markAttendanceRequest.setOffice_start_addr(mRequestBodyAddr);
                    markAttendanceRequest.setIs_late(mRequestIsLate);
                }
                else{
                    RequestBody mRequestIsLate = RequestBody.create(MediaType.parse("text/plain"), isTime);
                    markAttendanceRequest.setEnd_time(mRequestStartTime);
                    markAttendanceRequest.setEnd_latitude(mRequestBodyStartLat);
                    markAttendanceRequest.setEnd_longitude(mRequestBodyStartLong);
                    markAttendanceRequest.setOffice_end_addr(mRequestBodyAddr);
                    markAttendanceRequest.setIs_late(mRequestIsLate);
                }
               /* LogUtil.printToastMSG(mContext,DynamicAPIPath.action_update_attendance+
                        loginTable.getEmployeeId()+AppUtils.getCurrentDateInyyyymmdd()+cTime+isTime+
                        (cTime+dataLoc==null?"Unavailable":dataLoc)+ (clocationLat==null?"0.0":clocationLat)+
                        (clocationLng==null?"0.0":clocationLng));*/
                markAttendanceRequest.setAction(mRequestBodyAction);
                markAttendanceRequest.setEmp_id(mRequestEmailID);
                markAttendanceRequest.setDate(mRequestdate);

                updateAttendanceViewModel.hitUpdateAttendanceApi(markAttendanceRequest);
            } else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    //check if gps is enabled
    public boolean isGPSEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gps_enabled;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                StartAttendanceActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                StartAttendanceActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        } else {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            List<String> providers = locationManager.getProviders(true);
            Location bestLocation = null;
            for (String provider : providers) {
                /*Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }*/
            }
            final Handler handler = new Handler();
            Location finalBestLocation = bestLocation;
            handler.postDelayed(new Runnable() {
                public void run() {
                    Location locationGPS = finalBestLocation;
                    if (locationGPS != null) {
                    } else {
                        //Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                    }
                }
            }, 1000);

        }
    }

    //request camera and storage permission
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]
                                {
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION,

                                },
                        1000);

            } else {
                OnGPS();
            }
        } else {
            OnGPS();
        }
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
                ) {
                    OnGPS();
                } else {
                    Toast.makeText(this, "somthing_went_wrong", Toast.LENGTH_SHORT).show();
                }
                break;

        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                if (!isGPSEnabled(mContext)) {
                    settingsRequest();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            //startLocationService();
                        }
                    }, 5000);
                }

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //sending GPS request
    public void settingsRequest() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
            googleApiClient.connect();
        }
        long INTERVAL = Constants.INTERVAL_ATTENDENCE; //5 min
        long FASTEST_INTERVAL = Constants.FASTEST_INTERVAL_ATTENDENCE; //2 min
        LocationRequest locationRequest = LocationRequest.create();
        //locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setSmallestDisplacement(INTERVAL_M); //higher priority
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.e("TAG", "setResultCallback: " + LocationSettingsStatusCodes.SUCCESS);
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.e("TAG", "setResultCallback: " + LocationSettingsStatusCodes.RESOLUTION_REQUIRED);

                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(StartAttendanceActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.e("TAG", "setResultCallback: " + LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE);

                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void deleteDir() {
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), Constants.FILE_NAME);
        //File oldFile = new File(myDir);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                new File(dir, children[i]).delete();
            }
        }
    }


    private void setToolbar() {
        //set toolbar title
        //toolbarTitle.setText(R.string.scr_lbl_add_new_lr);
        //initToolbar(1, mContext, R.id.imgBack, R.id.imgReport, R.id.imgReport, 0, R.id.imgCall);
    }

    public static void setTimerMillis(Context context, long millis) {
        SharedPreferences sp = context.getSharedPreferences(Constants.BANK_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putLong(Constants.BANK_LIST, millis);
        spe.apply();
    }

    public static long getTimerMillis(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.BANK_LIST, Context.MODE_PRIVATE);
        return sp.getLong(Constants.BANK_LIST, 0);
    }


    //perform click actions
    @OnClick({R.id.relRound, R.id.imgOfcLocation, R.id.imgBack,R.id.tvCurrLocation})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.relRound:
                try {
                    String[] loc = tvOfcLocation.getText().toString().split("Office Location : ");
                    if(loc.length>1){
                    addressOff = loc[1];}
                    String[] curr = tvCurrLocation.getText().toString().split("Current Location : ");
                    if(curr.length>1){
                    addressCurr = curr[1];}
                    if (addressOff.equals("Fetching...") || addressOff.equals("")) {
                        LogUtil.printToastMSG(mContext, "Please request Admin to add office location.");
                    }
                    else if(addressCurr.equals("Fetching...") || addressCurr.equals("") ||tvCurrLocation.getText().toString().equals("Tap to add your current location")){
                        LogUtil.printToastMSG(mContext, "Please wait, Location is getting fetched.");
                    } else {
                        String startlocationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LAT, "0.0");
                        String startlocationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LONG, "0.0");
                        String locationLat = SharedPref.getInstance(mContext).read(SharedPrefKey.ENTERED_VISIT_LAT, "0.0");
                        String locationLng = SharedPref.getInstance(mContext).read(SharedPrefKey.ENTERED_VISIT_LNG, "0.0");
                        currlat = locationLat;
                        currlng = locationLng;
                        if(locationDisable!=null && locationDisable.equals("0")) {
                            if(distanceInMeters == null || distanceInMeters.equals("") || distanceInMeters.equals("null") ||distanceInMeters.equals("0") || distanceInMeters.equals("0.00") )
                            {
                                LogUtil.printSnackBar(mContext, Color.RED, findViewById(android.R.id.content), "Some company configurations are pending, Please contact admin.");

                            }else {
                                boolean isLocationFetched = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.NOT_FETCHED, false);
                                if (isLocationFetched) {
                                    Double distanceMatchurr = AppUtils.meterDistanceBetweenPoints(Float.parseFloat(startlocationLat),
                                            Float.parseFloat(startlocationLng),
                                            Float.parseFloat(currlat), Float.parseFloat(currlng));
                                    Double distanceMatchOff = AppUtils.meterDistanceBetweenPoints(Float.parseFloat(currlat),
                                            Float.parseFloat(currlng),
                                            Float.parseFloat(offlat), Float.parseFloat(offlng));

                              /* LogUtil.printToastMSG(mContext, distanceInMeters+"dist bet cur and map - " + distanceMatchurr + " lat" +
                                        startlocationLat + "," + startlocationLng + "," + currlat + "," + currlng + "next" +
                                        "dist bet off and map - " + distanceMatchOff + " lat" +
                                        offlat + "," + offlng + "," + currlat + "," + currlng);*/

                                    double mDistance = Math.floor(Double.parseDouble((distanceInMeters == null || distanceInMeters.equals("")
                                            || distanceInMeters.equals("null") ? "51.00" : distanceInMeters)));

                                    if ((Math.floor(distanceMatchOff) <= mDistance)
                                            && (Math.floor(distanceMatchurr) <= 350)) {
                                        if (getAttendanceAttList != null && getAttendanceAttList.size() > 0) {
                                            callUpdateAttendanceApi(1);
                                        } else {
                                            callUpdateAttendanceApi(0);
                                        }
                                    } else {
                                        LogUtil.printSnackBar(mContext, Color.RED, findViewById(android.R.id.content), getString(R.string.msg_location_change_in_attendance)
                                                /*+ distance+"--"+Double.parseDouble((distanceInMeters==null||distanceInMeters.equals("")||distanceInMeters.equals("null")?"51":distanceInMeters))*/);
                                    }
                                } else {
                                    Double distance = AppUtils.meterDistanceBetweenPoints(Float.parseFloat(startlocationLat),
                                            Float.parseFloat(startlocationLng), Float.parseFloat(offlat), Float.parseFloat(offlng));
                                    if (Math.floor(distance) <= Math.floor(Double.parseDouble((distanceInMeters == null || distanceInMeters.equals("") || distanceInMeters.equals("null") ? "51" : distanceInMeters)))) {
                                        if (getAttendanceAttList != null && getAttendanceAttList.size() > 0) {
                                            callUpdateAttendanceApi(1);
                                        } else {
                                            callUpdateAttendanceApi(0);
                                        }
                                    } else {
                                        LogUtil.printSnackBar(mContext, Color.RED, findViewById(android.R.id.content), getString(R.string.msg_location_change_in_attendance)
                                                /*+ distance+"--"+Double.parseDouble((distanceInMeters==null||distanceInMeters.equals("")||distanceInMeters.equals("null")?"51":distanceInMeters))*/);
                                    }
                                }
                            }
                        }else{
                            if (getAttendanceAttList != null && getAttendanceAttList.size() > 0) {
                                callUpdateAttendanceApi(1);
                            } else {
                                callUpdateAttendanceApi(0);
                            }
                        }
                    }

                } catch (Exception e) {
                    LogUtil.printToastMSG(mContext, "Please wait, Location is getting fetch.");
                }
                break;
            case R.id.imgBack:
                finish();
                break;
            case R.id.tvCurrLocation:
            int LAUNCH_SECOND_ACTIVITY = 10000;
            Intent i = new Intent(this, AddLocationActivity.class);
            startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(mContext, BackgroundAttentionService.class));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10000) {
            if (resultCode == Activity.RESULT_OK) {
                String locationLat = SharedPref.getInstance(mContext).read(SharedPrefKey.ENTERED_VISIT_LAT, "0.0");
                String locationLng = SharedPref.getInstance(mContext).read(SharedPrefKey.ENTERED_VISIT_LNG, "0.0");
                String location = SharedPref.getInstance(mContext).read(SharedPrefKey.LOCATION_ENTERED_TXT, "Not Available");
                //SharedPref.getInstance(getBaseContext()).write(SharedPrefKey.ATTENTION_LOC_TITLE, location);
                result=location;//data.getStringExtra("result");
                //String str = "<b>"+result+"</b>";
                //LogUtil.printToastMSG(mContext,result);
                tvCurrLocation.setText("Current Location : "+result); //Current Location : Fetching...
                currlat = locationLat;
                currlng = locationLng;
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }


    //set date picker view
    private void openDataPicker(int val, AppCompatTextView datePickerField) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                if (val == 1) {
                    datePickerField.setText("-" + sdf.format(myCalendar.getTime()));
                } else {
                    datePickerField.setText(sdf.format(myCalendar.getTime()));
                }
            }

        };

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    /*Api response */
    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {

            case LOADING:
                showSmallProgressBar(mProgressBarHolder);
                break;

            case SUCCESS:
                dismissSmallProgressBar(mProgressBarHolder);
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_VISIT_NO)) {
                            VisitNoResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), VisitNoResponse.class);
                            if (responseModel != null && responseModel.getStatus() == 1) {
                                //textVisitNo.setText("Visit Number : "+responseModel.getNumber());
                                SharedPref.getInstance(this).write(SharedPrefKey.VISIT_NO, responseModel.getNumber());

                            } else {
                                LogUtil.printToastMSG(mContext, responseModel.getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                   try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_ATTENDANCE)) {
                            GetAttendanceResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetAttendanceResponse.class);
                            if(responseModel.getResult()!=null){
                                locationDisable = (responseModel.getResult().getDisable_location()==null
                                        || responseModel.getResult().getDisable_location().equals(""))?"0"
                                        :responseModel.getResult().getDisable_location();
                                distanceInMeters = responseModel.getResult().getDist_range()+"";
                                try {
                                    Geocoder geocoder = new Geocoder(StartAttendanceActivity.this);
                                    offlat = responseModel.getResult().getOffice_latitude()+"";
                                    offlng = responseModel.getResult().getOffice_longitude()+"";
                                    //LogUtil.printToastMSG(mContext,offlat +"-"+offlng);
                                    List<Address> addressList = geocoder.getFromLocation(Double.parseDouble(offlat),
                                            Double.parseDouble(offlng), 1);
                                    if (addressList != null && addressList.size() > 0) {
                                        Address address = addressList.get(0);
                                        LatLng latLng1 = new LatLng(address.getLatitude(), address.getLongitude());
                                        String locality = addressList.get(0).getAddressLine(0);
                                        String country = addressList.get(0).getCountryName();
                                        addressOff = locality;
                                        tvOfcLocation.setText("Office Location : " + locality);
                                    }else{
                                        tvOfcLocation.setText("Office Location : " + responseModel.getResult().getOffice_address());
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                //LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                                final Handler handler = new Handler();
                                getAttendanceAttList = responseModel.getResult().getAtt();
                                locationDisable = (responseModel.getResult().getDisable_location()==null
                                        || responseModel.getResult().getDisable_location().equals(""))?"0"
                                :responseModel.getResult().getDisable_location();
                                if(responseModel.getResult().getAtt()==null) {
                                    layBottomCheckOut.setVisibility(View.INVISIBLE);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dismissSmallProgressBar(mProgressBarHolder);
                                            doBounceAnimationOnce(tvCheckInName);
                                            tvCheckInName.setText("Check In");
                                        }
                                    }, 1600);
                                }else{
                                    String startlocationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LAT, "0.0");
                                    String startlocationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LONG, "0.0");
                                    Geocoder geocoder;
                                    List<Address> addresses;
                                    geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

                                    try {
                                        addresses = geocoder.getFromLocation(Double.parseDouble(startlocationLat), Double.parseDouble(startlocationLng), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                        //addresses = null;
                                        if((addresses != null && addresses.size()>0) || (!startlocationLat.equals("0.0") && !startlocationLng.equals("0.0"))) {
                                            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                                            String city = addresses.get(0).getLocality();
                                            String state = addresses.get(0).getAdminArea();
                                            String country = addresses.get(0).getCountryName();
                                            String postalCode = addresses.get(0).getPostalCode();
                                            String knownName = addresses.get(0).getFeatureName();
                                            tvCurrLocation.setText("Current Location : " + address);
                                            tvCurrLocation.setEnabled(false);
                                            SharedPref.getInstance(getBaseContext()).write(SharedPrefKey.NOT_FETCHED, false);
                                        }else {
                                            tvCurrLocation.setEnabled(true);
                                            SharedPref.getInstance(getBaseContext()).write(SharedPrefKey.NOT_FETCHED, true);
                                            tvCurrLocation.setPaintFlags(tvCurrLocation.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                                            tvCurrLocation.setText("Tap to add your current location");
                                             if (result != null && !result.equals("temp") && !result.equals("")) {
                                                tvCurrLocation.setText("Current Location : " + result);
                                            }
                                            if (tvCurrLocation.getText().toString() == null || tvCurrLocation.getText().toString().equals("Tap to add your current location") || tvCurrLocation.getText().toString().equals("Current Location : Fetching...")) {
                                                LogUtil.printSnackBar(getBaseContext(), R.color.white, findViewById(android.R.id.content),
                                                        "Sorry, We are unable to fetch your location! \nPlease select it manually.");}

                                        }
                                    } catch (Exception e) {
                                    }
                                    tvCheckInTime.setText(AppUtils.convert24to12Attendance(responseModel.getResult().getAtt().get(0).getStartTime()));
                                    tvCheckOutTime.setText(AppUtils.convert24to12Attendance(responseModel.getResult().getAtt().get(0).getEndTime()));
                                    layBottomCheckOut.setVisibility(View.VISIBLE);
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dismissSmallProgressBar(mProgressBarHolder);
                                            doBounceAnimationOnce(tvCheckInName);
                                            tvCheckInName.setText("Check Out");
                                        }
                                    }, 1600);
                                }
                               /* imgChecked.setVisibility(View.VISIBLE);
                                //doBounceAnimation(imgChecked);
                                final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce_out);
                                imgChecked.startAnimation(animation);
                                //SharedPref.getInstance(getBaseContext()).write(SharedPrefKey.ATTENDANCE_ID, String.valueOf(responseModel.getResult().getId()));
                                //set toggle
                                SharedPref.getInstance(mContext).write(SharedPrefKey.CHECK_IN_BUTTON, true);
                                SharedPref.getInstance(mContext).write(SharedPrefKey.CHECK_OUT_ENABLED, true);
                                SharedPref.getInstance(mContext).write(SharedPrefKey.CHECK_OUT_TIME, AppUtils.getCurrentDateTime_() + " " + "00:00:00");
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 1700);*/
                            } else {
                                locationDisable = (responseModel.getResult().getDisable_location()==null
                                        || responseModel.getResult().getDisable_location().equals(""))?"0"
                                        :responseModel.getResult().getDisable_location();
                                layBottomCheckOut.setVisibility(View.INVISIBLE);
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        dismissSmallProgressBar(mProgressBarHolder);
                                        doBounceAnimationOnce(tvCheckInName);
                                        tvCheckInName.setText("Check In");
                                    }
                                }, 1600);
                                //LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_UPDATE_ATTENDANCE)) {
                            UpdateAttendanceResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), UpdateAttendanceResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                                imgChecked.setVisibility(View.VISIBLE);
                                //doBounceAnimation(imgChecked);
                                final Animation animation = AnimationUtils.loadAnimation(this, R.anim.bounce_out);
                                imgChecked.startAnimation(animation);
                                //set toggle
                                SharedPref.getInstance(mContext).write(SharedPrefKey.CHECK_IN_BUTTON, true);
                                SharedPref.getInstance(mContext).write(SharedPrefKey.CHECK_OUT_ENABLED, false);
                                SharedPref.getInstance(mContext).write(SharedPrefKey.CHECK_OUT_TIME, AppUtils.getCurrentDateTime_() + " " + AppUtils.getCurrentTimeIn24hr());
                                stopService(new Intent(mContext, BackgroundAttentionService.class));
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 1700);
                            } else {
                                LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case ERROR:
                dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }


}