package com.ominfo.crm_solution.ui.attendance;

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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;

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
import com.google.gson.Gson;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.basecontrol.BaseApplication;
import com.ominfo.crm_solution.common.BackgroundAttentionService;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.interfaces.Constants;
import com.ominfo.crm_solution.interfaces.SharedPrefKey;
import com.ominfo.crm_solution.network.ApiResponse;
import com.ominfo.crm_solution.network.DynamicAPIPath;
import com.ominfo.crm_solution.network.NetworkCheck;
import com.ominfo.crm_solution.network.ViewModelFactory;
import com.ominfo.crm_solution.ui.attendance.model.MarkAttendanceRequest;
import com.ominfo.crm_solution.ui.attendance.model.MarkAttendanceResponse;
import com.ominfo.crm_solution.ui.attendance.model.MarkAttendanceViewModel;
import com.ominfo.crm_solution.ui.attendance.model.UpdateAttendanceRequest;
import com.ominfo.crm_solution.ui.attendance.model.UpdateAttendanceResponse;
import com.ominfo.crm_solution.ui.attendance.model.UpdateAttendanceViewModel;
import com.ominfo.crm_solution.ui.attendance.ripple_effect.RippleBackground;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.visit_report.activity.AddLocationActivity;
import com.ominfo.crm_solution.ui.visit_report.model.AddVisitRequest;
import com.ominfo.crm_solution.ui.visit_report.model.AddVisitResponse;
import com.ominfo.crm_solution.ui.visit_report.model.AddVisitViewModel;
import com.ominfo.crm_solution.ui.visit_report.model.GetVisitNoViewModel;
import com.ominfo.crm_solution.ui.visit_report.model.VisitNoResponse;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;
import com.ominfo.crm_solution.util.SharedPref;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    GoogleApiClient googleApiClient;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    String lat="",lng="";
    boolean mStartVisit = false;
    @Inject
    ViewModelFactory mViewModelFactory;
    private GetVisitNoViewModel getVisitNoViewModel;
    private MarkAttendanceViewModel markAttendanceViewModel;
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

    @BindView(R.id.layBottomCheckOut)
    LinearLayoutCompat layBottomCheckOut;

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

    private void init(){
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        // initialise tha layout
        setToolbar();
        tvCurrLocation = findViewById(R.id.tvCurrLocation);
        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation(0,mContext);
        final Handler handler=new Handler();
        imgChecked.setVisibility(View.GONE);
        showSmallProgressBar(mProgressBarHolder);
        if (!isGPSEnabled(mContext)) {
            requestPermission();
        } else {
            getLocation();
        }
        Intent intent = getIntent();
        if (intent != null) {
            transactionId = intent.getStringExtra(Constants.TRANSACTION_ID);
            Boolean iSTimer = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.CHECK_IN_BUTTON, false);
            if (iSTimer){
                String startlocationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LAT, "0.0");
                String startlocationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LONG, "0.0");
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(Double.parseDouble(startlocationLat), Double.parseDouble(startlocationLng), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    tvCurrLocation.setText("Current Location : " + address);
                }catch (Exception e){}
                layBottomCheckOut.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismissSmallProgressBar(mProgressBarHolder);
                        doBounceAnimationOnce(tvCheckInName);
                        tvCheckInName.setText("Check Out");
                    }
                },1600);
             }
            else {
                layBottomCheckOut.setVisibility(View.INVISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismissSmallProgressBar(mProgressBarHolder);
                        doBounceAnimationOnce(tvCheckInName);
                        tvCheckInName.setText("Check In");
                        String address = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_TITLE, "Unavailable");

                    }
                },1600);
            }
        }
        prepareAllData();
    }

    private void prepareAllData(){
        LoginTable loginTable = mDb.getDbDAO().getLoginData();
        if(loginTable!=null) {
            tvCheckInName.setText("Hi "+loginTable.getName()+" ! \nPlease wait...");
        }
        Geocoder geocoder = new Geocoder(StartAttendanceActivity.this);

        try {
            List<Address> addressList = geocoder.getFromLocation(Double.parseDouble(loginTable.getBranchLatitude()),
                    Double.parseDouble(loginTable.getBranchLongitute()), 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = addressList.get(0);
                LatLng latLng1 = new LatLng(address.getLatitude(), address.getLongitude());
                String locality = addressList.get(0).getAddressLine(0);
                String country = addressList.get(0).getCountryName();
                //mLocTitle = addressList.get(0).getFeatureName();
                /*if (!locality.isEmpty() && !country.isEmpty())
                    mLocDesc = locality + "  " + country;*/
                tvOfcLocation.setText("Office Location : "+locality);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        String dataDate = AppUtils.getCurrentDateTime();
        String mDate = AppUtils.convertAlarmDate(dataDate);
        textViewVisit.setText(AppUtils.getCurrentTime());
        String startTime = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENDANCE_CHECKIN_TIME, "00:00:00");
        tvCheckInTime.setText(AppUtils.convert24to12Attendance(startTime));
        tvCheckOutTime.setText(AppUtils.getCurrentTime());
        textVisitNo.setText(mDate);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Boolean iSTimer = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.CHECK_OUT_ENABLED, false);
        if(!iSTimer) {
            startLocationService();
        }
    }

    private void doBounceAnimation(View targetView) {
        Interpolator interpolator = new Interpolator() {
            @Override
            public float getInterpolation(float v) {
                return getPowOut(v,2);//Add getPowOut(v,3); for more up animation
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
                return getPowOut(v,1);//Add getPowOut(v,3); for more up animation
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
        getVisitNoViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetVisitNoViewModel.class);
        getVisitNoViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_VISIT_NO));

        markAttendanceViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MarkAttendanceViewModel.class);
        markAttendanceViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_MARK_ATTENDANCE));

        updateAttendanceViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UpdateAttendanceViewModel.class);
        updateAttendanceViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_UPDATE_ATTENDANCE));
    }

    /* Call Api For visit no */
    private void callVisitNoApi() {
        if (NetworkCheck.isInternetAvailable(this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            /*if(loginTable!=null) {
                RequestBody mRequestBodyTypeAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_visit_no);
                RequestBody mRequestBodyTypeCompID = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());//loginTable.getCompanyId());
            */    getVisitNoViewModel.hitGetVisitNoApi();
            /*}
            else {
                LogUtil.printToastMSG(this, "Something is wrong.");
            }*/
        } else {
            LogUtil.printToastMSG(this, getString(R.string.err_msg_connection_was_refused));
        }
    }
    /* Call Api For Mark Attendance */
    private void callMarkAttendanceApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_mark_attendance);
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                RequestBody mRequestBodyDate = RequestBody.create(MediaType.parse("text/plain"), AppUtils.getCurrentDateInyyyymmdd());
                String startlocationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LAT, "0.0");
                String startlocationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LONG, "0.0");
                String mTime_ = AppUtils.getCurrentTimeIn24hr();
                RequestBody mRequestBodyStartTime = RequestBody.create(MediaType.parse("text/plain"), mTime_);
                RequestBody mRequestBodyStartLat = RequestBody.create(MediaType.parse("text/plain"), startlocationLat);
                RequestBody mRequestBodyStartLong = RequestBody.create(MediaType.parse("text/plain"), startlocationLng);
                SharedPref.getInstance(getBaseContext()).write(SharedPrefKey.ATTENDANCE_CHECKIN_TIME,mTime_);
                MarkAttendanceRequest markAttendanceRequest = new MarkAttendanceRequest();
                markAttendanceRequest.setAction(mRequestBodyAction);
                markAttendanceRequest.setEmpId(mRequestBodyTypeEmpId);
                markAttendanceRequest.setDate(mRequestBodyDate);
                markAttendanceRequest.setStartTime(mRequestBodyStartTime);
                markAttendanceRequest.setStartLatitude(mRequestBodyStartLat);
                markAttendanceRequest.setStartLongitude(mRequestBodyStartLong);
                markAttendanceViewModel.hitMarkAttendanceApi(markAttendanceRequest);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Update Attendance */
    private void callUpdateAttendanceApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                String id = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENDANCE_ID, "0");
                String startTime = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENDANCE_CHECKIN_TIME, "00:00:00");
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_update_attendance);
                RequestBody mRequestBodyStartTime = RequestBody.create(MediaType.parse("text/plain"),startTime);
                RequestBody mRequestBodyEndTime = RequestBody.create(MediaType.parse("text/plain"), AppUtils.getCurrentTimeIn24hr());
                RequestBody mRequestBodyId = RequestBody.create(MediaType.parse("text/plain"), id);
                String startlocationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LAT, "0.0");
                String startlocationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LONG, "0.0");
                RequestBody mRequestBodyEndLat = RequestBody.create(MediaType.parse("text/plain"), startlocationLat);
                RequestBody mRequestBodyEndLong = RequestBody.create(MediaType.parse("text/plain"), startlocationLng);

                UpdateAttendanceRequest markAttendanceRequest = new UpdateAttendanceRequest();
                markAttendanceRequest.setAction(mRequestBodyAction);
                markAttendanceRequest.setStartTime(mRequestBodyStartTime);
                markAttendanceRequest.setEndTime(mRequestBodyEndTime);
                markAttendanceRequest.setId(mRequestBodyId);
                markAttendanceRequest.setEndLatitude(mRequestBodyEndLat);
                markAttendanceRequest.setEndLongitude(mRequestBodyEndLong);
                updateAttendanceViewModel.hitUpdateAttendanceApi(markAttendanceRequest);
            }
            else {
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
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
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


    private void deleteDir(){
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), Constants.FILE_NAME);
        //File oldFile = new File(myDir);
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
        }
    }


    private void setToolbar() {
        //set toolbar title
        //toolbarTitle.setText(R.string.scr_lbl_add_new_lr);
       //initToolbar(1, mContext, R.id.imgBack, R.id.imgReport, R.id.imgReport, 0, R.id.imgCall);
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


    //perform click actions
    @OnClick({R.id.relRound,R.id.imgOfcLocation,R.id.layBack})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
               case R.id.relRound:
                   Boolean iSTimer = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.CHECK_IN_BUTTON, false);
                   if(iSTimer) {
                       callUpdateAttendanceApi();
                   }
                   else{
                       callMarkAttendanceApi();
                   }
                   break;
            case R.id.layBack:
                finish();
                break;
            case R.id.imgOfcLocation:
            int LAUNCH_SECOND_ACTIVITY = 1000;
            Intent i = new Intent(this, AddLocationActivity.class);
            startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
            break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Boolean iSTimer = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.CHECK_OUT_ENABLED, false);
        if(!iSTimer){
            stopService(new Intent(mContext, BackgroundAttentionService.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if(resultCode == Activity.RESULT_OK){
                 String result=data.getStringExtra("result");
                //String str = "<b>"+result+"</b>";

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }


    //set date picker view
    private void openDataPicker(int val , AppCompatTextView datePickerField) {
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
                if(val==1){
                datePickerField.setText("-"+sdf.format(myCalendar.getTime()));
                }
                else {
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
                            if (responseModel != null && responseModel.getStatus()==1) {
                                //textVisitNo.setText("Visit Number : "+responseModel.getNumber());
                                SharedPref.getInstance(this).write(SharedPrefKey.VISIT_NO, responseModel.getNumber());

                            } else {
                                LogUtil.printToastMSG(mContext, responseModel.getMessage());
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_MARK_ATTENDANCE)) {
                            MarkAttendanceResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), MarkAttendanceResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                                imgChecked.setVisibility(View.VISIBLE);
                                //doBounceAnimation(imgChecked);
                                final Animation animation = AnimationUtils.loadAnimation(this,R.anim.bounce_out);
                                imgChecked.startAnimation(animation);
                                SharedPref.getInstance(getBaseContext()).write(SharedPrefKey.ATTENDANCE_ID, String.valueOf(responseModel.getResult().getId()));
                               //set toggle
                                SharedPref.getInstance(mContext).write(SharedPrefKey.CHECK_IN_BUTTON, true);
                                SharedPref.getInstance(mContext).write(SharedPrefKey.CHECK_OUT_ENABLED, true);
                                SharedPref.getInstance(mContext).write(SharedPrefKey.CHECK_OUT_TIME, AppUtils.getCurrentDateTime_()+" "+"00:00:00");
                                final Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                },1700);
                            } else {
                                LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_UPDATE_ATTENDANCE)) {
                            UpdateAttendanceResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), UpdateAttendanceResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                                imgChecked.setVisibility(View.VISIBLE);
                                //doBounceAnimation(imgChecked);
                                final Animation animation = AnimationUtils.loadAnimation(this,R.anim.bounce_out);
                                imgChecked.startAnimation(animation);
                                //set toggle
                                SharedPref.getInstance(mContext).write(SharedPrefKey.CHECK_IN_BUTTON, false);
                                SharedPref.getInstance(mContext).write(SharedPrefKey.CHECK_OUT_ENABLED, false);
                                SharedPref.getInstance(mContext).write(SharedPrefKey.CHECK_OUT_TIME, AppUtils.getCurrentDateTime_()+" "+AppUtils.getCurrentTimeIn24hr());
                                stopService(new Intent(mContext, BackgroundAttentionService.class));

                                final Handler handler=new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                },1700);
                            } else {
                                LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}
                }
                break;
            case ERROR:
                dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }


}