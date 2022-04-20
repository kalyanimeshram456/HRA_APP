package com.ominfo.crm_solution.ui.visit_report.activity;

import android.Manifest;
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
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
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
import com.ominfo.crm_solution.common.BackgroundLocationUpdateService;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.interfaces.Constants;
import com.ominfo.crm_solution.interfaces.SharedPrefKey;
import com.ominfo.crm_solution.network.ApiResponse;
import com.ominfo.crm_solution.network.DynamicAPIPath;
import com.ominfo.crm_solution.network.NetworkCheck;
import com.ominfo.crm_solution.network.ViewModelFactory;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
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

public class StartEndVisitActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Context mContext;
    String transactionId = "";
    List<DashModel> dashboardList = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();
    @BindView(R.id.tvStartVisit)
    AppCompatTextView textViewVisit;
    @BindView(R.id.visitNo)
    AppCompatTextView textVisitNo;
    @BindView(R.id.startVisitButton)
    AppCompatButton mButtonStartVisit;
    @BindView(R.id.tvTime)
    Chronometer tvCounter;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_visit);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mContext = this;
        getDeps().inject(this);
        ButterKnife.bind(this);
        getDeps().inject(this);
        injectAPI();
        init();

    }

    private void init(){
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        myReceiver = new MyReceiver();
        Intent intent = getIntent();
        if (intent != null) {
            transactionId = intent.getStringExtra(Constants.TRANSACTION_ID);
            Boolean iSTimer = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.TIMER_STATUS, false);
            if (iSTimer){
                if (transactionId.equals("1")) { //start
                    tvCounter.setText("00:00:00");
                    tvCounter.setBase(SystemClock.elapsedRealtime());
                    tvCounter.stop();
                    setTimerMillis(mContext, 0);
                    //setTimerCounter(1);
                    textViewVisit.setText(R.string.scr_lbl_start_visit);
                    mButtonStartVisit.setText(R.string.scr_lbl_start_visit);
                    callVisitNoApi();
                } else { //end
                    setTimerCounter(0);
                    textViewVisit.setText(R.string.scr_lbl_end_visit);
                    mButtonStartVisit.setText(R.string.scr_lbl_end_visit);
                    String visit = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.VISIT_NO, "");
                    textVisitNo.setText("Visit Number : "+visit);
                }
             }
            else {
                if (transactionId.equals("1")) {
                    callVisitNoApi();
                }
                else {
                    String visit = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.VISIT_NO, "");
                    textVisitNo.setText("Visit Number : "+visit);
                }
                setTimerMillis(mContext,0);
                tvCounter.setBase(SystemClock.elapsedRealtime());
                tvCounter.stop();
            }
        }
        if (!isGPSEnabled(mContext)) {
            requestPermission();
        } else {
            getLocation();
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

                Geocoder geocoder = new Geocoder(StartEndVisitActivity.this);

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
                StartEndVisitActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                StartEndVisitActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION);
        } else {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            List<String> providers = locationManager.getProviders(true);
            Location bestLocation = null;
            for (String provider : providers) {
                Location l = locationManager.getLastKnownLocation(provider);
                if (l == null) {
                    continue;
                }
                if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                    // Found best last known location: %s", l);
                    bestLocation = l;
                }
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
        long INTERVAL = Constants.INTERVAL; //5 min
        long FASTEST_INTERVAL = Constants.FASTEST_INTERVAL; //2 min
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
                            status.startResolutionForResult(StartEndVisitActivity.this, REQUEST_CHECK_SETTINGS);
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
            Location location = intent.getParcelableExtra(BackgroundLocationUpdateService.EXTRA_LOCATION);
            if (location != null) {
                     lat = String.valueOf(location.getLatitude());
                     lng = String.valueOf(location.getLongitude());
                String isVisit = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.VISIT_ON, "0");
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
                }
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
                /*Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);*/
                //calendar.set(Calendar.MILLISECOND, getCurrentMiliSecondsOfChronometer(val));
                long milliseconds = getCurrentMiliSecondsOfChronometer(val);//getTimerMillis(mContext);
                int seconds = (int) (milliseconds / 1000) % 60 ;
                int minutes = (int) ((milliseconds / (1000*60)) % 60);
                int hours   = (int) ((milliseconds / (1000*60*60)) % 24);

                setTimerMillis(mContext, milliseconds);
                tvCounter.setText(String.format("%02d",hours)+":"+String.format("%02d",minutes)+":"+String.format("%02d",seconds));
            }
        });

    }

    //starting foreground service and registering broadcast for lat long
    private void startLocationService() {
        startService(new Intent(this, BackgroundLocationUpdateService.class));
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(BackgroundLocationUpdateService.ACTION_BROADCAST));
    }

    //perform click actions
    @OnClick({R.id.startVisitButton/*,R.id.toDate*/})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.startVisitButton:
                //1
                if(transactionId.equals("1")) { //start
                    SharedPref.getInstance(this).write(SharedPrefKey.TIMER_STATUS, true);
                    setTimerCounter(1);
                    showSuccessDialog("Visit Started Successfully!",false,StartEndVisitActivity.this);
                    startLocationService();
                    SharedPref.getInstance(mContext).write(SharedPrefKey.VISIT_ON, "1");
                }
                //end
                else {
                    SharedPref.getInstance(this).write(SharedPrefKey.TIMER_STATUS, false);
                    finish();
                    Intent i = new Intent(mContext, UploadVisitActivity.class);
                    i.putExtra(Constants.TRANSACTION_ID, "1");
                    startActivity(i);
                    ((Activity) mContext).overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_bottom);
                    //overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_bottom);
                    stopService(new Intent(this, BackgroundLocationUpdateService.class));
                    SharedPref.getInstance(mContext).write(SharedPrefKey.VISIT_ON, "2");
                }
                break;
          /*  case R.id.toDate:
                openDataPicker(1,toDate);
                break;*/
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
                ((BaseActivity) mContext).showSmallProgressBar(mProgressBarHolder);
                break;

            case SUCCESS:
                ((BaseActivity) mContext).dismissSmallProgressBar(mProgressBarHolder);
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_VISIT_NO)) {
                            VisitNoResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), VisitNoResponse.class);
                            if (responseModel != null && responseModel.getStatus()==1) {
                                textVisitNo.setText("Visit Number : "+responseModel.getNumber());
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
                ((BaseActivity) mContext).dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }


}