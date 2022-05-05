package com.ominfo.crm_solution;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ominfo.crm_solution.alarm.createalarm.CreateAlarmViewModel;
import com.ominfo.crm_solution.alarm.data.Alarm;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.basecontrol.BaseApplication;
import com.ominfo.crm_solution.common.BackgroundLocationUpdateService;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.interfaces.Constants;
import com.ominfo.crm_solution.interfaces.ErrorCallbacks;
import com.ominfo.crm_solution.interfaces.SharedPrefKey;
import com.ominfo.crm_solution.network.ApiResponse;
import com.ominfo.crm_solution.network.DynamicAPIPath;
import com.ominfo.crm_solution.network.NetworkCheck;
import com.ominfo.crm_solution.network.ViewModelFactory;
import com.ominfo.crm_solution.ui.attendance.model.MarkAttendanceViewModel;
import com.ominfo.crm_solution.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.CustomerList;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmResponse;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmlist;
import com.ominfo.crm_solution.ui.enquiry_report.model.SaveEnquiryRequest;
import com.ominfo.crm_solution.ui.enquiry_report.model.SaveEnquiryResponse;
import com.ominfo.crm_solution.ui.enquiry_report.model.SaveEnquiryViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.SearchCustResponse;
import com.ominfo.crm_solution.ui.enquiry_report.model.SearchCustViewModel;
import com.ominfo.crm_solution.ui.login.LoginActivity;
import com.ominfo.crm_solution.ui.login.model.LoginRequest;
import com.ominfo.crm_solution.ui.login.model.LoginResponse;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.login.model.LoginViewModel;
import com.ominfo.crm_solution.ui.my_account.MyAccountFragment;
import com.ominfo.crm_solution.ui.my_account.model.ApplyLeaveRequest;
import com.ominfo.crm_solution.ui.reminders.ReminderFragment;
import com.ominfo.crm_solution.ui.reminders.adapter.AddTagAdapter;
import com.ominfo.crm_solution.ui.reminders.model.ReminderModel;
import com.ominfo.crm_solution.ui.search.SearchFragment;
import com.ominfo.crm_solution.ui.visit_report.activity.StartEndVisitActivity;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;
import com.ominfo.crm_solution.util.SharedPref;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.Part;
import retrofit2.http.Url;

public class MainActivity extends BaseActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    BottomNavigationView bottomNavigationView;
    @BindView(R.id.layOptions)
    LinearLayoutCompat layOptions;
    AppCompatAutoCompleteTextView AutoComTextViewRM,tvSearchView
            ,AutoComTextViewName,AutoComTextViewMobile,AutoComTextViewPOI
            ,AutoComTextViewEnquiry;
    AppCompatEditText etDescr;
    @BindView(R.id.btnFab)
    FloatingActionButton btnFab;
    String mRmId = "";
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinator;
    AddTagAdapter addTagAdapter;
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
    private CreateAlarmViewModel createAlarmViewModel;
    @Inject
    ViewModelFactory mViewModelFactory;
    private GetRmViewModel getRmViewModel;
    private SearchCustViewModel searchCustViewModel;
    private SaveEnquiryViewModel saveEnquiryViewModel;

    GetRmlist selectedRM = new GetRmlist();
    Dialog mDialog;
    List<GetRmlist> RMDropdown = new ArrayList<>();
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;

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
        createAlarmViewModel = ViewModelProviders.of(this).get(CreateAlarmViewModel.class);
        initToolbar();
        if (!isGPSEnabled(mContext)) {
            requestPermission();
        } else {
            getLocation();
        }
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        btnFab.setImageResource(R.drawable.ic_add_dashboard);
        layOptions.setVisibility(View.GONE);
        ViewGroup.LayoutParams params = coordinator.getLayoutParams();
        int marginInDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 95, getResources()
                        .getDisplayMetrics());
        params.height = marginInDp;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        coordinator.setLayoutParams(params);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomnavigationbar);

        bottomNavigationView.setBackground(null);

        bottomNavigationView.getMenu().getItem(2).setEnabled(false);

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
                        temp = new SearchFragment();
                        break;

                    case R.id.Profile:
                        temp = new ReminderFragment();
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
        Window window = getWindow();
        View view = window.getDecorView();
        DarkStatusBar.setLightStatusBar(view,this);
        //initToolbar(0, mContext, R.id.imgBack, R.id.imgReport, R.id.imgNotify, R.id.layBack, R.id.imgCall);
    }

    private void injectAPI() {
        getRmViewModel = ViewModelProviders.of(MainActivity.this, mViewModelFactory).get(GetRmViewModel.class);
        getRmViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_RM));

        searchCustViewModel = ViewModelProviders.of(MainActivity.this, mViewModelFactory).get(SearchCustViewModel.class);
        searchCustViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_SEARCH_CUST));

        saveEnquiryViewModel = ViewModelProviders.of(MainActivity.this, mViewModelFactory).get(SaveEnquiryViewModel.class);
        saveEnquiryViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_SAVE_ENQUIRY));
 }

    @Override
    protected void onResume() {
        super.onResume();

        btnFab.setImageResource(R.drawable.ic_add_dashboard);
        layOptions.setVisibility(View.GONE);
        ViewGroup.LayoutParams params = coordinator.getLayoutParams();
        int marginInDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 95, getResources()
                        .getDisplayMetrics());
        params.height = marginInDp;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        coordinator.setLayoutParams(params);
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



    private void setAddTagList(RecyclerView rvImages) {
        tagList.removeAll(tagList);
        tagList.add(new DashModel("", "1", null));
        if (tagList.size() > 0) {
            rvImages.setVisibility(View.VISIBLE);
        } else {
            rvImages.setVisibility(View.GONE);
        }
        addTagAdapter = new AddTagAdapter(mContext, tagList, new AddTagAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(List<DashModel> mDataTicket) {
                tagList = mDataTicket;
                addTagAdapter.updateList(tagList, 0);
                if (tagList.size() > 3) {
                    int marginInDp40 = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 90, getResources()
                                    .getDisplayMetrics());
                    rvImages.setMinimumHeight(marginInDp40);
                    //setMargins(rvImages, 0, marginInDp40, 0, 0);
                }
            }
        });
        rvImages.setHasFixedSize(true);
        rvImages.setLayoutManager(new GridLayoutManager(mContext, 3));
        rvImages.setItemAnimator(new DefaultItemAnimator());
        rvImages.setAdapter(addTagAdapter);
        final boolean[] check = {false};

    }

    private void scheduleAlarm(String hour,String min,String desr,String date,String time) {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);
        String _24Hour= AppUtils.convert12to24(hour);
        String _24Min = AppUtils.convert12to24(min);
        Alarm alarm = new Alarm(
                alarmId,
                Integer.parseInt(_24Hour),
                Integer.parseInt(_24Min),
                desr,
                System.currentTimeMillis(),
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                date,
                time,
                "0",
                "",
                ""
        );

        createAlarmViewModel.insert(alarm);

        alarm.schedule(mContext);
    }


    //show Add Reminder popup
    public void showAddVisitDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_add_visit);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton addVisitButton = mDialog.findViewById(R.id.addVisitButton);

        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);

        addVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                Intent i = new Intent(mContext, StartEndVisitActivity.class);
                i.putExtra(Constants.TRANSACTION_ID, "1");
                startActivity(i);
                //((Activity) mContext).overridePendingTransition(0, 0);
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


    //show Add Receipt popup
    public void showAddReceiptDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_add_receipt);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton addReceiptButton = mDialog.findViewById(R.id.addReceiptButton);
        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);

        addReceiptButton.setOnClickListener(new View.OnClickListener() {
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

    //show Add Enquiry popup
    public void showAddEnquiryDialog() {
        mRmId = null;
        selectedRM = null;
        mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_add_enquiry);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton addReceiptButton = mDialog.findViewById(R.id.addReceiptButton);
        TextInputLayout input_textMobile = mDialog.findViewById(R.id.input_textMobile);
        TextInputLayout input_textName = mDialog.findViewById(R.id.input_textName);
        TextInputLayout input_textPOI = mDialog.findViewById(R.id.input_textPOI);
        TextInputLayout input_textSOE = mDialog.findViewById(R.id.input_textSOE);
        TextInputLayout input_textRM = mDialog.findViewById(R.id.input_textRM);

        AutoComTextViewRM = mDialog.findViewById(R.id.AutoComTextViewRM);
        tvSearchView = mDialog.findViewById(R.id.tvSearchView);
        tvSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        tvSearchView.setRawInputType(InputType.TYPE_CLASS_TEXT);
        AutoComTextViewName = mDialog.findViewById(R.id.AutoComTextViewName);
        AutoComTextViewMobile = mDialog.findViewById(R.id.AutoComTextViewMobile);
        AutoComTextViewPOI = mDialog.findViewById(R.id.AutoComTextViewPOI);
        AppCompatImageView imgSearchCust = mDialog.findViewById(R.id.imgSearchCust);
        AutoComTextViewEnquiry = mDialog.findViewById(R.id.AutoComTextViewEnquiry);
        etDescr = mDialog.findViewById(R.id.etDescr);
        setDropdownEnquiry();
        callRMApi();
        addReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEnqDetailsValid(input_textMobile,input_textName,input_textPOI,
                        input_textSOE,input_textRM)) {
                    callSaveEnquiryApi();
                }
            }
        });

        imgSearchCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mDialog.dismiss();
                if(tvSearchView.getText().toString().trim()!=null && !tvSearchView.getText().toString().trim().equals("")) {
                    callSearchCustApi();
                }
                else {
                    //LogUtil.printToastMSG(mContext,"Search filed is empty.");
                    callSearchCustApi();
                }
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

    /*check validations on field*/
    private boolean isEnqDetailsValid(TextInputLayout input_textMobile,
                                      TextInputLayout input_textName
    ,TextInputLayout input_textPOI ,TextInputLayout input_textSOE ,TextInputLayout input_textRM) {
        if (TextUtils.isEmpty(AutoComTextViewMobile.getText().toString().trim())) {
            setError(input_textMobile, getString(R.string.val_msg_please_enter_mobile));
            return false;
        }  else if (TextUtils.isEmpty(AutoComTextViewName.getText().toString().trim())) {
            setError(input_textName, getString(R.string.val_msg_please_enter_name));
            return false;
        }  else if (TextUtils.isEmpty(AutoComTextViewPOI.getText().toString().trim())) {
            setError(input_textPOI, getString(R.string.val_msg_please_enter_poi));
            return false;
        } else if (TextUtils.isEmpty(AutoComTextViewEnquiry.getText().toString().trim())) {
            setError(input_textSOE, getString(R.string.val_msg_please_enter_soi));
            return false;
        } else if (TextUtils.isEmpty(AutoComTextViewRM.getText().toString().trim())) {
            setError(input_textRM, getString(R.string.scr_lbl_pls_select_rm));
            return false;
        }
        return true;
    }

    /* Call Api For RM */
    private void callRMApi() {
        if (NetworkCheck.isInternetAvailable(MainActivity.this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_rm);
                RequestBody mRequestBodyTypeImage = RequestBody.create(MediaType.parse("text/plain"), "0");//loginTable.getEmployeeId());
                RequestBody mRequestBodyTypeImage1 = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                getRmViewModel.hitGetRmApi(mRequestBodyType, mRequestBodyTypeImage, mRequestBodyTypeImage1);
            }
            else {
                LogUtil.printToastMSG(MainActivity.this, "Something is wrong.");
            }
            } else {
            LogUtil.printToastMSG(MainActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Search cust */
    private void callSearchCustApi() {
        if (NetworkCheck.isInternetAvailable(MainActivity.this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyTypeAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_search_cust);
                RequestBody mRequestBodyTypeEmpID = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());//loginTable.getEmployeeId());//loginTable.getEmployeeId());
                RequestBody mRequestBodyTypeCompID = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeString = RequestBody.create(MediaType.parse("text/plain"), tvSearchView.getText().toString().trim());
                searchCustViewModel.hitSearchCustApi(mRequestBodyTypeAction, mRequestBodyTypeEmpID, mRequestBodyTypeCompID,
                        mRequestBodyTypeString);
            }
            else {
                LogUtil.printToastMSG(MainActivity.this, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(MainActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Search cust */
    private void callSaveEnquiryApi() {
        if (NetworkCheck.isInternetAvailable(MainActivity.this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {

                RequestBody mRequestBodyTypeAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_save_enquiry);
                RequestBody mRequestBodyTypeEnquiry = RequestBody.create(MediaType.parse("text/plain"), "0");//loginTable.getEmployeeId());//loginTable.getEmployeeId());
                RequestBody mRequestBodyTypeCustID = RequestBody.create(MediaType.parse("text/plain"), "0");//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeCustName = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewName.getText().toString().trim());
                RequestBody mRequestBodyTypeCustMobile = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewMobile.getText().toString().trim());
                RequestBody mRequestBodyTypeProduct = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewPOI.getText().toString().trim());
                RequestBody mRequestBodyTypeRm = RequestBody.create(MediaType.parse("text/plain"), selectedRM.getEmpId());
                RequestBody mRequestBodyTypeDescription = RequestBody.create(MediaType.parse("text/plain"), etDescr.getText().toString().trim());
                RequestBody mRequestBodyTypeSource = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewEnquiry.getText().toString().trim());
                RequestBody mRequestBodyTypeCompID = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpID = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());

                SaveEnquiryRequest saveEnquiryRequest =new SaveEnquiryRequest();
                saveEnquiryRequest.setRequestBodyTypeAction(mRequestBodyTypeAction);
                saveEnquiryRequest.setRequestBodyTypeEnquiry(mRequestBodyTypeEnquiry);
                saveEnquiryRequest.setRequestBodyTypeCustID(mRequestBodyTypeCustID);
                saveEnquiryRequest.setRequestBodyTypeCustName(mRequestBodyTypeCustName);
                saveEnquiryRequest.setRequestBodyTypeCustMobile(mRequestBodyTypeCustMobile);
                saveEnquiryRequest.setRequestBodyTypeProduct(mRequestBodyTypeProduct);
                saveEnquiryRequest.setRequestBodyTypeRm(mRequestBodyTypeRm);
                saveEnquiryRequest.setRequestBodyTypeDescription(mRequestBodyTypeDescription);
                saveEnquiryRequest.setRequestBodyTypeSource(mRequestBodyTypeSource);
                saveEnquiryRequest.setRequestBodyTypeCompID(mRequestBodyTypeCompID);
                saveEnquiryRequest.setRequestBodyTypeEmpID(mRequestBodyTypeEmpID);

                saveEnquiryViewModel.hitSaveEnquiryApi(saveEnquiryRequest);
            }
            else {
                LogUtil.printToastMSG(MainActivity.this, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(MainActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_RM)) {
                            GetRmResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetRmResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                RMDropdown.removeAll(RMDropdown);
                                RMDropdown = responseModel.getResult().getRmlist();
                                setDropdownRM();
                            } else {
                                LogUtil.printToastMSG(MainActivity.this, responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_SEARCH_CUST)) {
                            SearchCustResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), SearchCustResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                if(responseModel.getResult().getCustomerlist()!=null && responseModel.getResult().getCustomerlist().size()>0) {
                                    setDropdownSearch(responseModel.getResult().getCustomerlist(), tvSearchView);
                                    tvSearchView.showDropDown();
                                }
                                else{
                                    LogUtil.printToastMSG(mContext, "No data available.");
                                }

                            } else {
                                LogUtil.printToastMSG(MainActivity.this, responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_SAVE_ENQUIRY)) {
                            SaveEnquiryResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), SaveEnquiryResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                //LogUtil.printToastMSG(MainActivity.this, responseModel.getResult().getMessage());
                                mDialog.dismiss();
                                showSuccessDialog("Enquiry saved successfully!",true,MainActivity.this);
                                setRateUsCounter(mContext);
                            } else {
                                LogUtil.printToastMSG(MainActivity.this, responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}
                }
                break;
            case ERROR:
                dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(MainActivity.this, getString(R.string.err_msg_connection_was_refused));
                break;
        }
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

    //set value to RM dropdown
    private void setDropdownRM() {
        try {
            int pos = 0;
            if (RMDropdown != null && RMDropdown.size() > 0) {
                String[] mDropdownList = new String[RMDropdown.size()];
                for (int i = 0; i < RMDropdown.size(); i++) {
                    mDropdownList[i] = String.valueOf(RMDropdown.get(i).getEmpUsername()+" : "+
                            RMDropdown.get(i).getEmpName());
                    if (mRmId!=null && !mRmId.equals("")) {
                        if (mRmId.equals(RMDropdown.get(i).getEmpId())) {
                            pos = i;
                            AutoComTextViewRM.setText(mDropdownList[pos]);
                            selectedRM = RMDropdown.get(i);
                        }
                    }
                }
                //AutoComTextViewVehNo.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //AutoComTextViewVehNo.setThreshold(1);
                AutoComTextViewRM.setAdapter(adapter);
                //mSelectedColor = mDropdownList[pos];
                AutoComTextViewRM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //mSelectedColor = mDropdownList[position];
                        AppUtils.hideKeyBoard(MainActivity.this);
                        selectedRM = RMDropdown.get(position);
                    }

                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //set value to Search dropdown
    private void setDropdownSearch(List<CustomerList> RMDropdown, AppCompatAutoCompleteTextView AutoComTextView) {
        try {
            int pos = 0;
            if (RMDropdown != null && RMDropdown.size() > 0) {
                String[] mDropdownList = new String[RMDropdown.size()];
                for (int i = 0; i < RMDropdown.size(); i++) {
                    mDropdownList[i] = String.valueOf(RMDropdown.get(i).getCustomerName()+" ~ "+RMDropdown.get(i).getCustomerMobile());
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
                AutoComTextView.setAdapter(adapter);
                AutoComTextView.setScrollContainer(true);
                //mSelectedColor = mDropdownList[pos];
                AutoComTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for(int i=0;i<RMDropdown.size();i++){
                            if((RMDropdown.get(i).getCustomerName()+" ~ "+RMDropdown.get(i).getCustomerMobile())
                            .equals(mDropdownList[position])){
                                mRmId = RMDropdown.get(i).getRm();
                                setDropdownRM();
                            }
                        }
                        AppUtils.hideKeyBoard(MainActivity.this);
                        String[] separated = mDropdownList[position].split(" ~ ");
                        try {
                            if (separated.length > 1) {
                                AutoComTextViewName.setText(separated[0]);
                                AutoComTextViewMobile.setText(separated[1]);
                            }
                        }catch (Exception e){e.printStackTrace();}
                        //setDropdownRM();
                    }
                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //perform click actions
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

}




