package com.ominfo.hra_app;

import static com.ominfo.hra_app.interfaces.Constants.INTERVAL_M;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.common.BackgroundAttentionService;
import com.ominfo.hra_app.common.BackgroundLocationUpdateService;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.interfaces.Constants;
import com.ominfo.hra_app.interfaces.SharedPrefKey;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.hra_app.ui.dashboard.model.DashModel;
import com.ominfo.hra_app.ui.employees.EmployeeFragment;
import com.ominfo.hra_app.ui.leave.LeaveFragment;
import com.ominfo.hra_app.ui.leave.fragment.EmployeeLeaveListFragment;
import com.ominfo.hra_app.ui.login.LoginActivity;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.my_account.MyAccountFragment;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.salary.SalaryFragment;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.SharedPref;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    AppCompatAutoCompleteTextView AutoComTextViewRM,tvSearchView
            ,AutoComTextViewName,AutoComTextViewMobile,AutoComTextViewPOI
            ,AutoComTextViewEnquiry;
    AppCompatEditText etDescr;

    String mRmId = "";
    private boolean status = false;
    Context mContext;
    List<DashModel> tagList = new ArrayList<>();
    GoogleApiClient googleApiClient;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    LocationManager locationManager;
    String latitude, longitude;
    double lat, lng;
    private MyReceiver myReceiver;
    boolean statusLoc = false;
    private static final int REQUEST_LOCATION = 1;
    final Calendar myCalendar = Calendar.getInstance();
    private AppDatabase mDb;
    @Inject
    ViewModelFactory mViewModelFactory;

    Dialog mDialog;
    public static BottomNavigationView bottomNavigationView;
    LoginTable loginTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //getWindow().setStatusBarColor(Color.parseColor("#00ffffff"));
        getWindow().setNavigationBarColor(Color.parseColor("#00ffffff"));
        mContext = this;
        myReceiver = new MyReceiver();
        ButterKnife.bind(this);
        getDeps().inject(this);
        injectAPI();
        initToolbar();
        Intent intent = getIntent();
        if(intent!=null){
            String noti = intent.getStringExtra("Noti");
            if(noti!=null && noti.equals("1")){
                Boolean iSLoggedIn = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.IS_LOGGED_IN, false);
                if (!iSLoggedIn){
                    finishAffinity();
                    launchScreen(mContext,LoginActivity.class);
                }else {
                    finishAffinity();
                    launchScreen(mContext, NotificationsActivity.class);
                }
            }
            else{
                Boolean iSLoggedIn = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.IS_LOGGED_IN, false);
                if (!iSLoggedIn){
                    finishAffinity();
                    launchScreen(mContext,LoginActivity.class);
                }
            }
        }
        if (!isGPSEnabled(mContext)) {
            requestPermission();
        } else {
            getLocation();
        }
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        loginTable = mDb.getDbDAO().getLoginData();

     /*   getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer, new DashboardFragment()).commit();

        ssCustomBottomNavigation = findViewById(R.id.bottomNavigationN);
        //ssCustomBottomNavigation.change
        ssCustomBottomNavigation.add(new SSCustomBottomNavigation.Model(1, R.drawable.ic_home,"Home"));
        ssCustomBottomNavigation.add(new SSCustomBottomNavigation.Model(2, R.drawable.ic_employee,"Employee"));
        ssCustomBottomNavigation.add(new SSCustomBottomNavigation.Model(3, R.drawable.ic_lev,"Leave"));
        ssCustomBottomNavigation.add(new SSCustomBottomNavigation.Model(4, R.drawable.ic_sal,"Salary"));
        ssCustomBottomNavigation.add(new SSCustomBottomNavigation.Model(5, R.drawable.ic_acc,"Profile"));
        ssCustomBottomNavigation.show(1,true);

        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer, new DashboardFragment()).commit();

        ssCustomBottomNavigation.setOnClickMenuListener(new Function1<SSCustomBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(SSCustomBottomNavigation.Model model) {
                Fragment temp = null;
                switch (model.getId()) {
                    case 1:
                        temp = new DashboardFragment();
                        break;
                    case 2:
                        temp = new EmployeeFragment();
                        break;
                    case 3:
                        if(loginTable!=null) {
                            if(loginTable.getIsadmin().equals("0")){
                                temp = new EmployeeLeaveListFragment();
                            } else{
                                temp = new LeaveFragment();
                            }
                        }
                        else{
                            temp = new LeaveFragment();
                        }
                        break;
                    case 4:
                        temp = new SalaryFragment();
                        break;
                    case 5:
                        temp = new MyAccountFragment();
                }

                getSupportFragmentManager().beginTransaction().add(R.id.framecontainer, temp).addToBackStack(null).commit();

                return null;
            }
        });
   */
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setBackground(null);

        //bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        bottomNavigationView.setSelectedItemId(R.id.home);
        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer, new DashboardFragment()).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp = null;

                switch (item.getItemId()) {
                    case R.id.home:
                        temp = new DashboardFragment();
                        break;
                    case R.id.Search:
                        temp = new EmployeeFragment();
                        break;
                    case R.id.leave:
                        if(loginTable!=null) {
                            if(loginTable.getIsadmin().equals("0")){
                                temp = new EmployeeLeaveListFragment();
                            } else{
                                temp = new LeaveFragment();
                            }
                        }
                        else{
                            temp = new LeaveFragment();
                        }
                        break;

                    case R.id.Profile:
                        temp = new SalaryFragment();
                        break;

                    case R.id.Settings:
                        temp = new MyAccountFragment();
                }

                getSupportFragmentManager().beginTransaction().add(R.id.framecontainer, temp).addToBackStack(null).commit();
                return true;
            }
        });
    }

    private void initToolbar() {
        /*Window window = getWindow();
        View view = window.getDecorView();
        DarkStatusBar.setLightStatusBar(view,this);*/
        //initToolbar(0, mContext, R.id.imgBack, R.id.imgReport, R.id.imgNotify, R.id.layBack, R.id.imgCall);
    }

    private void injectAPI() {
        }

    @Override
    protected void onResume() {
        super.onResume();

       /* ViewGroup.LayoutParams params = coordinator.getLayoutParams();
        int marginInDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 95, getResources()
                        .getDisplayMetrics());
        params.height = marginInDp;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        coordinator.setLayoutParams(params);*/
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                        lat = locationGPS.getLatitude();
                        lng = locationGPS.getLongitude();
                        latitude = String.valueOf(lat);
                        longitude = String.valueOf(lng);
                        //setCurrent();
                        //startLocationService();
                        //showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude + "\n" + "Longitude: " + longitude);
                    } else {
                        //Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
                    }
                }
            }, 1000);

        }
    }

    public void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    /**
     * Receiver for broadcasts sent by {@link }.
     */
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(BackgroundLocationUpdateService.EXTRA_LOCATION);
            if (location != null) {
                /*if(!statusLoc) {
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    setCurrent();
                    statusLoc = true;
                }*/
            }
        }
    }

    /*//starting foreground service and registering broadcast for lat long
    private void startLocationService() {
        startService(new Intent(this, BackgroundLocationUpdateService.class));
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                new IntentFilter(BackgroundLocationUpdateService.ACTION_BROADCAST));
    }*/

    //set date picker view
    private void openDataPicker(AppCompatAutoCompleteTextView datePickerField) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "";
                myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                datePickerField.setText(sdf.format(myCalendar.getTime()));
            }

        };

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void OpenTimePicker(AppCompatAutoCompleteTextView AutoComTextViewTime) {
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String am_pm = "";
                myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                myCalendar.set(Calendar.MINUTE, selectedMinute);
                if (myCalendar.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "AM";
                else if (myCalendar.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "PM";
                String strHrsToShow = (myCalendar.get(Calendar.HOUR) == 0) ? "12" : myCalendar.get(Calendar.HOUR) + "";
                //UIHelper.showLongToastInCenter(context, strHrsToShow + ":" + myCalendar.get(Calendar.MINUTE) + " " + am_pm);
                AutoComTextViewTime.setText(strHrsToShow + ":" + myCalendar.get(Calendar.MINUTE) + " " + am_pm);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }



    //set value to RM dropdown
    private void setDropdownEnquiry() {
        List<String> RMDropdown = new ArrayList<>();
        RMDropdown.add("Internet");
        RMDropdown.add("Call/Email");
        RMDropdown.add("Visit");
        RMDropdown.add("Walk-in");
        RMDropdown.add("Indiamart");
        RMDropdown.add("Justdial");
        RMDropdown.add("Reference by customers");
        RMDropdown.add("Other");
        try {
            int pos = 0;
            if (RMDropdown != null && RMDropdown.size() > 0) {
                String[] mDropdownList = new String[RMDropdown.size()];
                for (int i = 0; i < RMDropdown.size(); i++) {
                    mDropdownList[i] = String.valueOf(RMDropdown.get(i));
                   /* if (!VehiIdDropdown.equals("")) {
                        if (RMDropdown.get(i).getId().equals(VehiIdDropdown)) {
                            pos = i;
                        }
                    }*/
                }
                //AutoComTextViewVehNo.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //AutoComTextViewVehNo.setThreshold(1);
                AutoComTextViewEnquiry.setAdapter(adapter);
                //mSelectedColor = mDropdownList[pos];
                AutoComTextViewEnquiry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //mSelectedColor = mDropdownList[position];
                        AppUtils.hideKeyBoard(MainActivity.this);
                    }
                });
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /*//perform click actions
    @OnClick({R.id.btnFab, R.id.imgReminder, R.id.imgVisit, R.id.imgReceipt,R.id.imgEnquiry})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnFab:
                if (status) {
                    status = false;
                    btnFab.setImageResource(R.drawable.ic_add_dashboard);
                    layOptions.setVisibility(View.GONE);
                    ViewGroup.LayoutParams params = coordinator.getLayoutParams();
                    // Changes the height and width to the specified *pixels*
                    int marginInDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 95, getResources()
                                    .getDisplayMetrics());
                    params.height = marginInDp;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    coordinator.setLayoutParams(params);

                } else {
                    status = true;
                    btnFab.setImageResource(R.drawable.ic_close_dashboard);
                    layOptions.setVisibility(View.VISIBLE);
                    ViewGroup.LayoutParams params = coordinator.getLayoutParams();
                    // Changes the height and width to the specified *pixels*
                    int marginInDp = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 140, getResources()
                                    .getDisplayMetrics());
                    params.height = marginInDp;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    coordinator.setLayoutParams(params);
                    params.height = marginInDp;
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT;
                    coordinator.setLayoutParams(params);

                }
                break;
            case R.id.imgReminder:
                //showAddReminderDialog();
                Bundle bundle = new Bundle();
                bundle.putString("dialog", "1");
                Fragment fragment = new ReminderFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = ((MainActivity)mContext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.framecontainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.imgVisit:
                Boolean iSTimer = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.TIMER_STATUS, false);
                if (!iSTimer) {
                    Intent i = new Intent(mContext, StartEndVisitActivity.class);
                    i.putExtra(Constants.TRANSACTION_ID, "1");
                    startActivity(i);
                    //showAddVisitDialog();
                } else {
                    ((BaseActivity) mContext).errorMessage("Sorry, Please complete your ongoing visit to start new one.", new ErrorCallbacks() {
                        @Override
                        public void onOkClick() {
                            //DO something;
                        }
                    });
                }
                break;
            case R.id.imgReceipt:
                showAddReceiptDialog();
                break;
            case R.id.imgEnquiry:
                showAddEnquiryDialog();
                break;
        }
    }
*/
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
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            stopService(new Intent(mContext, BackgroundAttentionService.class));
        }catch (Exception e){}
    }
}




