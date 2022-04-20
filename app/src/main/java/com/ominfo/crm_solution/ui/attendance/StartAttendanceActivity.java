package com.ominfo.crm_solution.ui.attendance;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

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
import com.ominfo.crm_solution.common.BackgroundLocationUpdateService;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.interfaces.Constants;
import com.ominfo.crm_solution.interfaces.SharedPrefKey;
import com.ominfo.crm_solution.network.ApiResponse;
import com.ominfo.crm_solution.network.DynamicAPIPath;
import com.ominfo.crm_solution.network.NetworkCheck;
import com.ominfo.crm_solution.network.ViewModelFactory;
import com.ominfo.crm_solution.ui.attendance.ripple_effect.RippleBackground;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.visit_report.activity.AddLocationActivity;
import com.ominfo.crm_solution.ui.visit_report.activity.UploadVisitActivity;
import com.ominfo.crm_solution.ui.visit_report.model.AddVisitRequest;
import com.ominfo.crm_solution.ui.visit_report.model.AddVisitResponse;
import com.ominfo.crm_solution.ui.visit_report.model.AddVisitViewModel;
import com.ominfo.crm_solution.ui.visit_report.model.GetVisitNoViewModel;
import com.ominfo.crm_solution.ui.visit_report.model.VisitNoResponse;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;
import com.ominfo.crm_solution.util.RealPathUtils;
import com.ominfo.crm_solution.util.SharedPref;
import com.ominfo.crm_solution.util.Util;

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

public class StartAttendanceActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Context mContext;
    String transactionId = "";
    List<DashModel> dashboardList = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();
    @BindView(R.id.tvStartVisit)
    AppCompatTextView textViewVisit;
    @BindView(R.id.visitNo)
    AppCompatTextView textVisitNo;
   /* @BindView(R.id.startVisitButton)
    AppCompatButton mButtonStartVisit;*/
    @BindView(R.id.imgChecked)
   AppCompatImageView imgChecked;
    private MyReceiver myReceiver;
    GoogleApiClient googleApiClient;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    String lat="",lng="";
    boolean mStartVisit = false;
    @Inject
    ViewModelFactory mViewModelFactory;
    private GetVisitNoViewModel getVisitNoViewModel;
    private AddVisitViewModel addVisitViewModel;
    private AppDatabase mDb;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.relRound)
    RelativeLayout relRound;
    @BindView(R.id.tvCheckInName)
    AppCompatTextView tvCheckInName;
    @BindView(R.id.tvOfcLocation)
    AppCompatTextView tvOfcLocation;
    @BindView(R.id.tvCurrLocation)
    AppCompatTextView tvCurrLocation;
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

        final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation(0,mContext);
        final Handler handler=new Handler();
        imgChecked.setVisibility(View.GONE);
        showSmallProgressBar(mProgressBarHolder);
        LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                tvCheckInName.setText("Hi "+loginTable.getName()+" ! \nPlease wait...");
            }

       /* ObjectAnimator scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                relRound,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f));
        scaleDown.setDuration(310);

        scaleDown.setRepeatCount(ObjectAnimator.INFINITE);
        scaleDown.setRepeatMode(ObjectAnimator.REVERSE);

        scaleDown.start();*/
        if (!isGPSEnabled(mContext)) {
            requestPermission();
        } else {
            getLocation();
        }
        myReceiver = new MyReceiver();
        Intent intent = getIntent();
        if (intent != null) {
            transactionId = intent.getStringExtra(Constants.TRANSACTION_ID);
            Boolean iSTimer = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.CHECK_IN, false);
            if (iSTimer){
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
                    }
                },1600);
            }
        }

        setToolbar();
        dashboardList.add(new DashModel("Sales Credit","₹13245647",getDrawable(R.drawable.ic_om_sales_credit)));
        dashboardList.add(new DashModel("Receipt","₹13245647",getDrawable(R.drawable.ic_om_receipt)));
        dashboardList.add(new DashModel("Top Customer","₹13245647",getDrawable(R.drawable.ic_om_rating)));
        dashboardList.add(new DashModel("Total Quotation Amount","₹13245647",getDrawable(R.drawable.ic_om_total_quotation)));
        dashboardList.add(new DashModel("Dispatch Pending","₹13245647",getDrawable(R.drawable.ic_om_dispatch_pending)));
        dashboardList.add(new DashModel("Enquiry Report","₹13245647",getDrawable(R.drawable.ic_om_enquiry_report)));
        dashboardList.add(new DashModel("Visit Report","₹13245647",getDrawable(R.drawable.ic_om_visit_report)));
        dashboardList.add(new DashModel("Products","₹13245647",getDrawable(R.drawable.ic_om_product)));
        dashboardList.add(new DashModel("Sales Credit","₹13245647",getDrawable(R.drawable.ic_om_sales_credit)));
        dashboardList.add(new DashModel("Receipt","₹13245647",getDrawable(R.drawable.ic_om_receipt)));
        dashboardList.add(new DashModel("Top Customer","₹13245647",getDrawable(R.drawable.ic_om_rating)));
        //setAdapterForDashboardList();
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

        addVisitViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AddVisitViewModel.class);
        addVisitViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_ADD_VISIT));
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

    /* Call Api For Add visit */
    private void callAddVisitApi() {
        if (NetworkCheck.isInternetAvailable(this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                String mLocDesc = "",mLocTitle = "";
                String startlocationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.START_VISIT_LAT, "0.0");
                String startlocationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.START_VISIT_LNG, "0.0");

                Geocoder geocoder = new Geocoder(StartAttendanceActivity.this);

                try {
                    List<Address> addressList = geocoder.getFromLocation(Double.parseDouble(startlocationLat),
                            Double.parseDouble(startlocationLng), 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        LatLng latLng1 = new LatLng(address.getLatitude(), address.getLongitude());
                        String locality = addressList.get(0).getAddressLine(0);
                        String country = addressList.get(0).getCountryName();
                        mLocTitle = addressList.get(0).getFeatureName();
                        if (!locality.isEmpty() && !country.isEmpty())
                            mLocDesc = locality + "  " + country;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                AddVisitRequest request = new AddVisitRequest();
                request.setCompanyID(loginTable.getCompanyId());
                request.setStartLocationAddress(mLocDesc);
                request.setStartLocationName(mLocTitle);
                request.setStartLocationLatitude(startlocationLat);
                request.setStartLocationLongitute(startlocationLng);
                try {
                    String[] visitNo = textVisitNo.getText().toString().split("Visit Number : ");
                    request.setVisitNo(visitNo[1]);
                }catch (Exception e){
                    request.setVisitNo("");
                }
                String mD = AppUtils.getStartVisitDate()
                , mT = AppUtils.getStartVisitTime();
                request.setVisitDate(mD);
                request.setVisitTime(mT);
                addVisitViewModel.hitAddVisitApi(request);
            }
            else {
                LogUtil.printToastMSG(this, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(this, getString(R.string.err_msg_connection_was_refused));
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

    /**
     * Receiver for broadcasts sent by {@link }.
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(BackgroundAttentionService.EXTRA_LOCATION);
            tvCurrLocation.setText("yo");
            if (location != null) {
                     lat = String.valueOf(location.getLatitude());
                     lng = String.valueOf(location.getLongitude());
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(mContext, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();
                    tvCurrLocation.setText("Current Location : "+address);
                } catch (IOException e) {
                    e.printStackTrace();
                }


               /* String isVisit = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.VISIT_ON, "0");
                if(isVisit.equals("1")) {
                    SharedPref.getInstance(mContext).write(SharedPrefKey.VISIT_ON, "0");
                    //LogUtil.printToastMSG(mContext,"clicked started visit"+lat+","+lng);
                    SharedPref.getInstance(mContext).write(SharedPrefKey.START_VISIT_LAT, lat);
                    SharedPref.getInstance(mContext).write(SharedPrefKey.START_VISIT_LNG, lng);
                    callAddVisitApi();
                }
                if(isVisit.equals("2")) {
                    SharedPref.getInstance(mContext).write(SharedPrefKey.VISIT_ON, "0");
                    //LogUtil.printToastMSG(mContext,"clicked end Visit"+lat+","+lng);
                    SharedPref.getInstance(mContext).write(SharedPrefKey.END_VISIT_LAT, lat);
                    SharedPref.getInstance(mContext).write(SharedPrefKey.END_VISIT_LNG, lng);
                }*/
            }
        }
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


    //starting foreground service and registering broadcast for lat long
    private void startLocationService() {
        startService(new Intent(this, BackgroundAttentionService.class));
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(BackgroundAttentionService.ACTION_BROADCAST));
    }

    //perform click actions
    @OnClick({R.id.relRound,R.id.imgOfcLocation})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
               case R.id.relRound:
                   imgChecked.setVisibility(View.VISIBLE);
                   //doBounceAnimation(imgChecked);
                   final Animation animation = AnimationUtils.loadAnimation(this,R.anim.bounce_out);
                   imgChecked.startAnimation(animation);
                   Boolean iSTimer = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.CHECK_IN, false);
                   if(iSTimer) {
                       SharedPref.getInstance(mContext).write(SharedPrefKey.CHECK_IN, false);
                       stopService(new Intent(mContext, BackgroundAttentionService.class));
                   }
                   else{
                       SharedPref.getInstance(mContext).write(SharedPrefKey.CHECK_IN, true);
                   }
                   final Handler handler=new Handler();
                   handler.postDelayed(new Runnable() {
                       @Override
                       public void run() {
                          finish();
                       }
                   },1700);
                   break;
            case R.id.imgOfcLocation:
            int LAUNCH_SECOND_ACTIVITY = 1000;
            Intent i = new Intent(this, AddLocationActivity.class);
            startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
            break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if(resultCode == Activity.RESULT_OK){
                 String result=data.getStringExtra("result");
                //String str = "<b>"+result+"</b>";
                tvOfcLocation.setText("Office Location : "+result);
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_ADD_VISIT)) {
                            AddVisitResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), AddVisitResponse.class);
                            if (responseModel != null && responseModel.getStatus()==1) {
                                //LogUtil.printToastMSG(mContext, responseModel.getMessage());
                                 } else {
                                LogUtil.printToastMSG(mContext, responseModel.getMessage());
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