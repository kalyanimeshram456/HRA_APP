package com.ominfo.hra_app.ui.dashboard.fragment;

import static com.ominfo.hra_app.zcustomcalendar.CustomCalendar.NEXT;
import static com.ominfo.hra_app.zcustomcalendar.CustomCalendar.PREVIOUS;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.ominfo.hra_app.MainActivity;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.basecontrol.BaseFragment;
import com.ominfo.hra_app.common.BackgroundAttentionService;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.interfaces.Constants;
import com.ominfo.hra_app.interfaces.SharedPrefKey;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.splash_slider.WelcomeActivity;
import com.ominfo.hra_app.ui.attendance.StartAttendanceActivity;
import com.ominfo.hra_app.ui.attendance.model.AttendanceList;
import com.ominfo.hra_app.ui.attendance.ripple_effect.RippleBackground;
import com.ominfo.hra_app.ui.dashboard.adapter.AttendanceDetailsAdapter;
import com.ominfo.hra_app.ui.dashboard.adapter.TodayBirthDayAdapter;
import com.ominfo.hra_app.ui.dashboard.model.AddHolidayRequest;
import com.ominfo.hra_app.ui.dashboard.model.AddHolidayResponse;
import com.ominfo.hra_app.ui.dashboard.model.AddHolidayViewModel;
import com.ominfo.hra_app.ui.dashboard.model.AttendanceDetailsData;
import com.ominfo.hra_app.ui.dashboard.model.AttendanceDetailsResponse;
import com.ominfo.hra_app.ui.dashboard.model.AttendanceDetailsViewModel;
import com.ominfo.hra_app.ui.dashboard.model.BirthDayDobdatum;
import com.ominfo.hra_app.ui.dashboard.model.CalenderHolidayLeave;
import com.ominfo.hra_app.ui.dashboard.model.CalenderHolidayResponse;
import com.ominfo.hra_app.ui.dashboard.model.CalenderHolidaysListViewModel;
import com.ominfo.hra_app.ui.dashboard.model.DashModel;
import com.ominfo.hra_app.ui.dashboard.model.DashboardRequest;
import com.ominfo.hra_app.ui.dashboard.model.EditHolidayViewModel;
import com.ominfo.hra_app.ui.dashboard.model.GetBirthDayListViewModel;
import com.ominfo.hra_app.ui.dashboard.model.GetBirthDayResponse;
import com.ominfo.hra_app.ui.employees.model.ChangePasswordViewModel;
import com.ominfo.hra_app.ui.employees.model.EditEmployeeResponse;
import com.ominfo.hra_app.ui.employees.model.EmployeeList;
import com.ominfo.hra_app.ui.employees.model.EmployeeListRequest;
import com.ominfo.hra_app.ui.employees.model.EmployeeListResponse;
import com.ominfo.hra_app.ui.employees.model.EmployeeListViewModel;
import com.ominfo.hra_app.ui.employees.model.SingleEmployeeListViewModel;
import com.ominfo.hra_app.ui.leave.model.LeaveCountResponse;
import com.ominfo.hra_app.ui.leave.model.LeaveCountViewModel;
import com.ominfo.hra_app.ui.login.LoginActivity;
import com.ominfo.hra_app.ui.login.model.AttendanceDaysTable;
import com.ominfo.hra_app.ui.login.model.DayData;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.login.model.LogoutResponse;
import com.ominfo.hra_app.ui.login.model.LogoutViewModel;
import com.ominfo.hra_app.ui.my_account.model.GetProfileImageViewModel;
import com.ominfo.hra_app.ui.my_account.model.ProfileImageResponse;
import com.ominfo.hra_app.ui.my_account.model.WorkTimingList;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;
import com.ominfo.hra_app.util.SharedPref;

import com.ominfo.hra_app.zcustomcalendar.CustomCalendar;
import com.ominfo.hra_app.zcustomcalendar.OnCalenderHolidaySelectedListener;
import com.ominfo.hra_app.zcustomcalendar.OnDateSelectedListener;
import com.ominfo.hra_app.zcustomcalendar.OnNavigationButtonClickedListener;
import com.ominfo.hra_app.zcustomcalendar.Property;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends BaseFragment {

    private Context mContext;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    @BindView(R.id.rvTodaysBirthdayList)
    RecyclerView rvTodaysBirthdayList;
    @BindView(R.id.rvUpcomingBirthDayList)
    RecyclerView rvUpcomingBirthDayList;
    @BindView(R.id.imgCall)
    AppCompatImageView imgLogout;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
    @BindView(R.id.relAddHoliday)
    RelativeLayout relAddHoliday;
    @BindView(R.id.tvTodaysTitle)
    AppCompatTextView tvTodaysTitle;
    @BindView(R.id.tvAttendance)
    AppCompatTextView tvAttendanceTitle;
    @BindView(R.id.imgProfileDash)
    AppCompatTextView imgProfileDash;
    @BindView(R.id.tvAttendanceValue)
    AppCompatTextView tvAttendanceValue;
    @BindView(R.id.tvLeave)
    AppCompatTextView tvLeaveTitle;
    @BindView(R.id.tvLeaveValue)
    AppCompatTextView tvLeaveValue;
    @BindView(R.id.tvAdmin)
    AppCompatTextView tvAdminTitle;
    @BindView(R.id.tvNameTitle)
    AppCompatTextView tvNameTitle;
    @BindView(R.id.tvUpcomingTitle)
    AppCompatTextView tvUpcomingTitle;
    @BindView(R.id.custom_calendar)
    CustomCalendar custom_calendar;
    @BindView(R.id.imgBirthPro)
    CircleImageView imgBirthPro;
    @BindView(R.id.progress_barBirth)
    ProgressBar progressBar;
    String stringAttendance = "";
    final Calendar myCalendar = Calendar.getInstance();
    public static List<CalenderHolidayLeave> calenderHolidayLeave = new ArrayList<>();
    private AppDatabase mDb;
    TodayBirthDayAdapter todayBirthDayAdapter;
    AttendanceDetailsAdapter attendanceDetailsAdapter;
    List<BirthDayDobdatum> birthDayDobdatumList = new ArrayList<>();
    List<BirthDayDobdatum> upcomingBirthDayDobdatumList = new ArrayList<>();
    Boolean enabledPopup = false;
    @Inject
    ViewModelFactory mViewModelFactory;
    private GetProfileImageViewModel getProfileImageViewModel;
    private EditHolidayViewModel editHolidayViewModel;
    private SingleEmployeeListViewModel employeeListViewModel;
    private AttendanceDetailsViewModel attendanceDetailsViewModel;
    public static boolean activityVisible; // Variable that will check the
    final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    IntentFilter intentFilter;
    //MyReceiver receiver;
    private GetBirthDayListViewModel getBirthDayListViewModel;
    private CalenderHolidaysListViewModel calenderHolidaysListViewModel;
    //private AppDatabase mDb;
    String mUrl = "", mFinalURL = "";
    boolean mSearchStatus = false, mScrolledStatus = false;
    int type = 0;
    private boolean loading = true;
    private String mUserKey = "";
    private boolean swipeStatus = false;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.rippleEffect)
    RippleBackground rippleEffect;
    @BindView(R.id.add_attendance)
    FloatingActionButton add_attendance;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    Dialog mDialogLogout,dialogAddHoliday,mDialogEditHoliday;
    @BindView(R.id.imgDashBg)
    LinearLayoutCompat imgDashBg;
    Snackbar snackbar;
    private LeaveCountViewModel leaveCountViewModel;
    private AddHolidayViewModel addHolidayViewModel;
    AppCompatTextView tvDateValueFrom;
    AppCompatAutoCompleteTextView AutoComHolidayName;
    AppCompatEditText etDescr;
    String mFromDate = "",mToDate = "";
    HashMap<Object, Property> descHashMap = new HashMap<>();
    HashMap<Integer, Object> dateHashmap = new HashMap<>();
    Calendar calendar = Calendar.getInstance();
    LoginTable loginTable;
    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_dashbooard, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((BaseActivity) mContext).getDeps().inject(this);
        injectAPI();
        init();
        /*int count = mDb.getDbDAO().getCountLocation();
        LogUtil.printToastMSG(mContext,"after "+String.valueOf(count));*/
        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        Boolean iSTimer = SharedPref.getInstance(mContext).read(SharedPrefKey.IS_NOTIFY, false);
        if (iSTimer) {
             /*SharedPref.getInstance(mContext).write(SharedPrefKey.IS_NOTIFY, false);
             launchScreen(mContext, NotificationsActivity.class);*/
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setToolbar();
        mFromDate = AppUtils.startMonth(); mToDate = AppUtils.endMonth();
        callProfileImageApi();
        callLeaveCountApi();
        callCalenderHolidaysApi();
        callDashboardApi();
        callEmployeeListApi();
        try{
            mContext.stopService(new Intent(mContext, BackgroundAttentionService.class));
        }catch (Exception e){}
    }

    private void injectAPI() {
        getBirthDayListViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(GetBirthDayListViewModel.class);
        getBirthDayListViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_BIRTH_DAY_LIST));

        calenderHolidaysListViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(CalenderHolidaysListViewModel.class);
        calenderHolidaysListViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_CALENDER_HOLIDAY));

        getProfileImageViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetProfileImageViewModel.class);
        getProfileImageViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_GET_PROFILE));

        leaveCountViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LeaveCountViewModel.class);
        leaveCountViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_LEAVE_COUNT));

        addHolidayViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AddHolidayViewModel.class);
        addHolidayViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_ADD_HOLIDAY));

        editHolidayViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EditHolidayViewModel.class);
        editHolidayViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_EDIT_HOLIDAY));

        employeeListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SingleEmployeeListViewModel.class);
        employeeListViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_SINLE_EMPLOYEES_LIST));

        attendanceDetailsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AttendanceDetailsViewModel.class);
        attendanceDetailsViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_ATTENDANCE_DETAILS));
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    //perform click actions
    @OnClick({R.id.cardSale, R.id.add_attendance, R.id.relAddHoliday})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add_attendance:
                Intent iAtt = new Intent(mContext, StartAttendanceActivity.class);
                iAtt.putExtra(Constants.TRANSACTION_ID, "0");
                startActivity(iAtt);
                break;
            case R.id.cardSale:
                /*Fragment fragment = new SaleFragment();
                moveFromFragment(fragment, mContext);*/
                break;
              case R.id.relAddHoliday:
                showAddHolidayDialog();
                break;
        }
    }

    private void init() {
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        //requestPermission();
        setToolbar();
        Glide.with(mContext)
                .load(R.drawable.img_bg_bashboard)
                .into(new CustomTarget<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        imgDashBg.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
        /*Glide.with(mContext)
                .load(R.drawable.img_bg_bashboard)
                .into(imgDashBg);*/
        setAdapterForBirthDayList();
        setAdapterForUpcomingBirthDayList();
        loginTable = mDb.getDbDAO().getLoginData();
        if(loginTable!=null){
            if(!loginTable.getIsadmin().equals("0")) {
                tvAttendanceTitle.setText("Today's Attendance");
                tvAttendanceValue.setText("0 / 0");
                tvLeaveTitle.setText("Today's Late Marks");
                tvLeaveValue.setText("0");
            }
            else{
                tvAttendanceTitle.setText("Attendance - monthly");
                tvAttendanceValue.setText("0 / 0");
                tvLeaveTitle.setText("Late mark - monthly");
                tvLeaveValue.setText("0 / 0");
            }
            tvNameTitle.setText(loginTable.getName());
            tvAdminTitle.setText(loginTable.getDesignation());
            //LogUtil.printLog("testimage","https://ominfo.in/o_hr/"+loginTable.getProfilePicture());
            AppUtils.loadImageURL(mContext,"https://ominfo.in/o_hr/"+loginTable.getProfilePicture(),
                    imgBirthPro, progressBar);
            if(loginTable.getIsadmin().equals("0")){
                relAddHoliday.setVisibility(View.INVISIBLE);
                rippleEffect.stopRippleAnimation();
                add_attendance.setVisibility(View.VISIBLE);
                rippleEffect.setVisibility(View.VISIBLE);
                callEmployeeListApi();
            }
            else{
                rippleEffect.stopRippleAnimation();
                add_attendance.setVisibility(View.GONE);
                rippleEffect.setVisibility(View.GONE);
                relAddHoliday.setVisibility(View.VISIBLE);
            }
        }
        //set ripple effect
        //rippleEffect.setBackgroundColor(mContext.getResources().getColor(R.color.deep_yellow));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkCheck.isInternetAvailable(mContext)) {
                    swipeStatus = true;
                    setToolbar();
                    if(loginTable.getIsadmin().equals("0")){
                        rippleEffect.stopRippleAnimation();
                        add_attendance.setVisibility(View.VISIBLE);
                        rippleEffect.setVisibility(View.VISIBLE);
                        callEmployeeListApi();
                    }
                    else{
                        rippleEffect.stopRippleAnimation();
                        add_attendance.setVisibility(View.GONE);
                        rippleEffect.setVisibility(View.GONE);
                    }
                    //mFromDate = AppUtils.startMonth(); mToDate = AppUtils.endMonth();
                    callCalenderHolidaysApi();
                    callDashboardApi();
                    callLeaveCountApi();
                } else {
                    swipeStatus = false;
                    LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });

        try {
            mSwipeRefreshLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        mSwipeRefreshLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    } else {
                        mSwipeRefreshLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }

                    Rect rect = new Rect();
                    mSwipeRefreshLayout.getDrawingRect(rect);
                    mSwipeRefreshLayout.setProgressViewOffset(false, 0, rect.centerY() - (mSwipeRefreshLayout.getProgressCircleDiameter()));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        setCalenderData();
    }

    private void setCalenderData() {
        // Initialize description hashmap


        // Initialize default property
        Property defaultProperty = new Property();

        // Initialize default resource
        defaultProperty.layoutResource = R.layout.default_view;
        // Initialize and assign variable
        defaultProperty.dateTextViewResource = R.id.text_view;

        // Put object and property
        descHashMap.put("default", defaultProperty);

        // for current date
        Property currentProperty = new Property();
        currentProperty.layoutResource = R.layout.current_view;
        currentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("current", currentProperty);

        // for present date
        Property presentProperty = new Property();
        presentProperty.layoutResource = R.layout.present_view;
        presentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("present", presentProperty);

        // For absent
        Property absentProperty = new Property();
        absentProperty.layoutResource = R.layout.absent_view;
        absentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("absent", absentProperty);

        // set desc hashmap on custom calendar
        custom_calendar.invalidate();
        custom_calendar.setMapDescToProp(descHashMap);

        // Initialize date hashmap

        // initialize calendar

        // Put values
        dateHashmap.put(calendar.get(Calendar.DAY_OF_MONTH), "current");
        try {
            if (calenderHolidayLeave != null && calenderHolidayLeave.size() > 0) {
                for (int i = 0; i < calenderHolidayLeave.size(); i++) {
                    String[] str = calenderHolidayLeave.get(i).getDate().split("-");
                    dateHashmap.put(Integer.valueOf(str[2]), "absent");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       /* dateHashmap.put(1,"present");
        dateHashmap.put(2,"absent");
        dateHashmap.put(3,"present");
        dateHashmap.put(4,"absent");
        dateHashmap.put(20,"present");
        dateHashmap.put(30,"absent");*/

        // set date
        // custom_calendar.setNextButtonColor(R.color.colorAccent);
        custom_calendar.setDate(calendar, dateHashmap);
        custom_calendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
                // get string date
                try {
                    String mon = String.valueOf(selectedDate.get(Calendar.MONTH) + 1).length() == 1 ?
                            "0" + String.valueOf(selectedDate.get(Calendar.MONTH) + 1) :
                            String.valueOf(selectedDate.get(Calendar.MONTH) + 1);
                    String day = String.valueOf(selectedDate.get(Calendar.DAY_OF_MONTH)).length() == 1 ?
                            "0" + String.valueOf(selectedDate.get(Calendar.DAY_OF_MONTH)) :
                            String.valueOf(selectedDate.get(Calendar.DAY_OF_MONTH));
                    String sDate = selectedDate.get(Calendar.YEAR)
                            + "-" + mon
                            + "-" + day;

                    // display date in toast
                    Boolean event = false;
                    for (int i = 0; i < calenderHolidayLeave.size(); i++) {
                        if (sDate.equals(calenderHolidayLeave.get(i).getDate())) {
                            enabledPopup = true;
                            showEditHolidayDialog(calenderHolidayLeave.get(i).getRecordId(),
                                    calenderHolidayLeave.get(i).getDate(), calenderHolidayLeave.get(i).getName() + "("
                                            + calenderHolidayLeave.get(i).getDescription() + ")");
                            //LogUtil.printToastMSG(mContext, calenderHolidayLeave.get(i).getName());
                            event = true;
                            break;
                        }
                    }
                    if (!event) {
                        if (loginTable.getIsadmin().equals("1")) {
                            callAttendanceDetailsApi(sDate);
                            stringAttendance = AppUtils.dateConvertYYYYToDD(sDate);
                        }
                        else {
                            LogUtil.printSnackBar(mContext, Color.YELLOW, ((MainActivity) mContext).findViewById(android.R.id.content), "No events for " + AppUtils.convertDobDate(sDate));
                        }
                    }
                }catch (Exception e){
                    LogUtil.printToastMSG(mContext, "Something went wrong!");
                }
            }
        });
        custom_calendar.setOnCalenderListener(new OnCalenderHolidaySelectedListener() {
            @Override
            public void onDateSelected(Calendar selectedDate) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String test=  sdf.format(selectedDate.getTime());
                mFromDate = AppUtils.startHolidayMonth(selectedDate);
                mToDate = AppUtils.endHolidayMonth(selectedDate);
                calendar = selectedDate;
                //LogUtil.printToastMSG(mContext,test+"k "+mFromDate+"jn"+mToDate);
                callCalenderHolidaysApi();
                callDashboardApi();
            }
        });
    }

    private void setAttendanceFloatingButtons(DayData loginDays) {
        rippleEffect.stopRippleAnimation();
        add_attendance.setVisibility(View.VISIBLE);
        rippleEffect.setVisibility(View.VISIBLE);
        Boolean iSTimer = SharedPref.getInstance(mContext).read(SharedPrefKey.CHECK_IN_BUTTON, false);
        //if(!iSTimer){
        List<AttendanceList> attendanceListList = new ArrayList<>();
        attendanceListList.add(new AttendanceList(loginDays.getMonDay(), loginDays.getMonWorking() == null ? "No" : loginDays.getMonWorking(), loginDays.getMonStartTime(), loginDays.getMonEndTime()));
        attendanceListList.add(new AttendanceList(loginDays.getTueDay(), loginDays.getTueWorking() == null ? "No" : loginDays.getTueWorking(), loginDays.getTueStartTime(), loginDays.getTueEndTime()));
        attendanceListList.add(new AttendanceList(loginDays.getWedDay(), loginDays.getWedWorking() == null ? "No" : loginDays.getWedWorking(), loginDays.getWedStartTime(), loginDays.getWedEndTime()));
        attendanceListList.add(new AttendanceList(loginDays.getThrusDay(), loginDays.getThrusWorking() == null ? "No" : loginDays.getThrusWorking(), loginDays.getThrusStartTime(), loginDays.getThrusEndTime()));
        attendanceListList.add(new AttendanceList(loginDays.getFriDay(), loginDays.getFriWorking() == null ? "No" : loginDays.getFriWorking(), loginDays.getFriStartTime(), loginDays.getFriEndTime()));
        attendanceListList.add(new AttendanceList(loginDays.getSatDay(), loginDays.getSatWorking() == null ? "No" : loginDays.getSatWorking(), loginDays.getSatStartTime(), loginDays.getSatStartTime()));
        attendanceListList.add(new AttendanceList(loginDays.getSunDay(), loginDays.getSunWorking() == null ? "No" : loginDays.getSunWorking(), loginDays.getSunStartTime(), loginDays.getSunEndTime()));
        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());
        SharedPref.getInstance(mContext).write(SharedPrefKey.ATTENDANCE_START_TIME, "00:00:00");
        //LogUtil.printToastMSG(mContext,"att_before_Test-"+weekDay);
        for (int i = 0; i < attendanceListList.size(); i++) {
            if (weekDay.equals(attendanceListList.get(i).getMonDay()) && (attendanceListList.get(i).getMonWorking().equals("Yes")||attendanceListList.get(i).getMonWorking().equals("yes"))) {
                //LogUtil.printToastMSG(mContext,"att_Test"+weekDay);
                SharedPref.getInstance(mContext).write(SharedPrefKey.ATTENDANCE_START_TIME, attendanceListList.get(i).getMonStartTime());
                String startDate = AppUtils.getCurrentDateTime_() + " " + AppUtils.getCurrentTimeIn24hr(),
                        endDate = (attendanceListList.get(i).getMonStartTime() == null ||
                                attendanceListList.get(i).getMonStartTime().equals("00:00:00") )? AppUtils.getCurrentDateTime_() + " " + "10:00:00" : AppUtils.getCurrentDateTime_() + " " + attendanceListList.get(i).getMonStartTime(),
                        attEndDate = (attendanceListList.get(i).getMonEndTime() == null ||
                                attendanceListList.get(i).getMonEndTime().equals("00:00:00") )? AppUtils.getCurrentDateTime_() + " " + "19:00:00" : AppUtils.getCurrentDateTime_() + " " + attendanceListList.get(i).getMonEndTime();
                String Min30LaterTime = "", Min60LaterTime = "";
                try {
                    SimpleDateFormat sdf30 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String currentDateandTime = sdf30.format(new Date());
                    Date date = sdf30.parse(endDate);
                    Calendar calendar30 = Calendar.getInstance();
                    calendar30.setTime(date);
                    calendar30.add(Calendar.MINUTE, 30);
                    Min30LaterTime = sdf30.format(calendar30.getTime());
                    //LogUtil.printToastMSG(mContext,Min30LaterTime);
                    SimpleDateFormat sdf60 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    //String currentDateandTime = sdf30.format(new Date());
                    Date date60 = sdf60.parse(endDate);
                    Calendar calendar60 = Calendar.getInstance();
                    calendar60.setTime(date60);
                    calendar60.add(Calendar.HOUR, 1);
                    Min60LaterTime = sdf60.format(calendar60.getTime());
                    //LogUtil.printToastMSG(mContext,Min60LaterTime);
                } catch (Exception e) {
                }
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                add_attendance.setVisibility(View.VISIBLE);
                rippleEffect.setVisibility(View.VISIBLE);
                try {
                    Date date1 = sdf.parse(startDate);
                    LogUtil.printLog("att_start ", startDate);
                    Date date2 = sdf.parse(endDate);
                    LogUtil.printLog("att_end ", endDate);
                    Date date3 = sdf.parse(Min30LaterTime);
                    LogUtil.printLog("att_30 ", Min30LaterTime);
                    Date date4 = sdf.parse(Min60LaterTime);
                    LogUtil.printLog("att_60 ", Min60LaterTime);
                    Date date5 = sdf.parse(attEndDate);
                    LogUtil.printLog("att_realend ", attEndDate);
                    Boolean iSActiveChill = SharedPref.getInstance(mContext).read(SharedPrefKey.CHECK_IN_BUTTON, false);
                    String iSCheckInDoneChill = SharedPref.getInstance(mContext).read(SharedPrefKey.CHECK_OUT_TIME, AppUtils.getCurrentDateTime_() + " " + "00:00:00");
                    Date dateEnd = sdf.parse(iSCheckInDoneChill);

                    //String[] valll = iSCheckInDoneChill.split(" ");
                    //if (iSActiveChill) {
                        // LogUtil.printToastMSG(mContext,iSActiveChill+"-"+iSCheckInDoneChill);
                        /*if (date1.compareTo(date2) == -1) {
                            // Outputs -1 as date1 is before date2
                            rippleEffect.stopRippleAnimation();
                            add_attendance.setVisibility(View.VISIBLE);
                            rippleEffect.setVisibility(View.VISIBLE);
                        } else {
                            // LogUtil.printToastMSG(mContext,iSActiveChill+"-"+iSCheckInDoneChill);
                            rippleEffect.startRippleAnimation(2, mContext);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                add_attendance.setForeground(mContext.getDrawable(R.drawable.attention_gradient_blue));
                            }
                        }*/
                   // }
                   // if (!iSActiveChill) {
                        if ((date1.compareTo(date2) == -1) || (dateEnd.compareTo(date2) == 1) || (date1.compareTo(date5) == 1) || iSActiveChill){
                            // Outputs -1 as date1 is before date2
                            rippleEffect.stopRippleAnimation();
                            add_attendance.setVisibility(View.VISIBLE);
                            rippleEffect.setVisibility(View.VISIBLE);
                            //rippleEffect.startRippleAnimation(2, mContext);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                add_attendance.setForeground(mContext.getDrawable(R.drawable.attention_gradient_blue));
                            }
                        } else if (date1.compareTo(date2) == 1 && date1.compareTo(date3) == -1) {
                            // Outputs -1 as date3 is before date2
                            // Outputs 0 as the dates are now equal
                            rippleEffect.startRippleAnimation(2, mContext);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                add_attendance.setForeground(mContext.getDrawable(R.drawable.attention_gradient_blue));
                            }
                        } else if (date1.compareTo(date3) == 1 && date1.compareTo(date4) == -1) {
                            // Outputs 1 as date3 is after date1
                            rippleEffect.stopRippleAnimation();
                            rippleEffect.startRippleAnimation(1, mContext);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                add_attendance.setForeground(mContext.getDrawable(R.drawable.attention_gradient_yellow));
                            }
                        } else if (date1.compareTo(date4) == 1 && date1.compareTo(date5) == -1) {
                            // Outputs 1 as date4 is after date3
                            rippleEffect.stopRippleAnimation();
                            rippleEffect.startRippleAnimation(3, mContext);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                add_attendance.setForeground(mContext.getDrawable(R.drawable.attention_gradient_red));
                            }
                        } else if (date1.compareTo(date5) == 1) {
                            SharedPref.getInstance(mContext).write(SharedPrefKey.CHECK_IN_BUTTON, false);

                            // Outputs 1 as date4 is after date3
                            /*rippleEffect.stopRippleAnimation();
                            add_attendance.setVisibility(View.VISIBLE);
                            rippleEffect.setVisibility(View.VISIBLE);
                            try {
                                //((BaseActivity) mContext).stopService(new Intent(mContext, BackgroundAttentionService.class));
                            } catch (Exception e) {
                            }*/
                        }
                  //  }


                } catch (ParseException e) {
                    e.printStackTrace();
                    // Exception handling goes here
                    LogUtil.printLog("att_iiss", e.getMessage());
                }
                   /* boolean b = false;

                    try {
                        if (dfDate.parse(startDate).before(dfDate.parse(endDate))) {
                            b = true;  // If start date is before end date.
                        } else if (dfDate.parse(startDate).equals(dfDate.parse(endDate))) {
                            b = true;  // If two dates are equal.

                        } else {
                            b = false; // If start date is after the end date.
                            rippleEffect.stopRippleAnimation();
                            rippleEffect.startRippleAnimation(1,mContext);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                add_attendance.setForeground(mContext.getDrawable(R.drawable.attention_gradient_yellow));
                            }
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/

                //return b;
                // }
            }
        }
    }

    private void setAdapterForBirthDayList() {
        if (birthDayDobdatumList != null && birthDayDobdatumList.size() > 0) {
            todayBirthDayAdapter = new TodayBirthDayAdapter(mContext, birthDayDobdatumList, new TodayBirthDayAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(DashModel mData) {

                }
            });
            rvTodaysBirthdayList.setHasFixedSize(true);
            rvTodaysBirthdayList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            rvTodaysBirthdayList.setAdapter(todayBirthDayAdapter);
            rvTodaysBirthdayList.setVisibility(View.VISIBLE);
            tvTodaysTitle.setText(getString(R.string.scr_lbl_today_s_birthday));
        } else {
            rvTodaysBirthdayList.setVisibility(View.GONE);
            tvTodaysTitle.setText("No birthdays today.");
        }
    }

    private void setAdapterForUpcomingBirthDayList() {
        if (upcomingBirthDayDobdatumList != null && upcomingBirthDayDobdatumList.size() > 0) {
            todayBirthDayAdapter = new TodayBirthDayAdapter(mContext, upcomingBirthDayDobdatumList, new TodayBirthDayAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(DashModel mData) {

                }
            });
            rvUpcomingBirthDayList.setHasFixedSize(true);
            rvUpcomingBirthDayList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            rvUpcomingBirthDayList.setAdapter(todayBirthDayAdapter);
            rvUpcomingBirthDayList.setVisibility(View.VISIBLE);
            tvUpcomingTitle.setText(getString(R.string.scr_lbl_upcoming_birthdays));

        } else {
            rvUpcomingBirthDayList.setVisibility(View.GONE);
            tvUpcomingTitle.setText("No Upcoming birthdays this month.");
        }
    }

    private void setToolbar() {
        ((BaseActivity) mContext).initToolbar(0, mContext, R.id.imgBack, R.id.imgReport, R.id.imgNotify, tvNotifyCount, 0, R.id.imgCall);
        imgLogout.setVisibility(View.INVISIBLE);
        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog(mContext);
            }
        });
        imgNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity) mContext).launchScreen(mContext, NotificationsActivity.class);
                ;
            }
        });
    }


    /* Call Api For employee list */
    private void callEmployeeListApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                if(loginTable.getIsadmin().equals("0")) {
                    RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_single_employee_list);
                    RequestBody mRequestComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                    RequestBody mRequestEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());

                    EmployeeListRequest request = new EmployeeListRequest();
                    request.setAction(mRequestAction);
                    request.setCompanyId(mRequestComId);
                    request.setEmployee(mRequestEmployee);

                    employeeListViewModel.hitSingleEmployeeList(request);
                }
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api Leave */
    private void callLeaveCountApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_leave_count);
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                RequestBody mRequestComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                RequestBody mRequestMon = RequestBody.create(MediaType.parse("text/plain"), AppUtils.getLeaveCountDate());

                leaveCountViewModel.hitLeaveCountApi(mRequestBodyType,mRequestBodyTypeEmployee,
                        mRequestComId,mRequestMon);
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }


    /* Call Api For Logout */
    private void callAddHolidayApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_add_holiday);
                RequestBody mRequestComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                RequestBody mRequestDate = RequestBody.create(MediaType.parse("text/plain"), AppUtils.changeToSlashToDash(tvDateValueFrom.getText().toString()));
                RequestBody mRequestName = RequestBody.create(MediaType.parse("text/plain"), AutoComHolidayName.getText().toString());
                RequestBody mRequestDescription = RequestBody.create(MediaType.parse("text/plain"), etDescr.getText().toString());

                AddHolidayRequest addHolidayRequest = new AddHolidayRequest();
                addHolidayRequest.setAction(mRequestBodyAction);
                addHolidayRequest.setCompany_id(mRequestComId);
                addHolidayRequest.setDate(mRequestDate);
                addHolidayRequest.setName(mRequestName);
                addHolidayRequest.setDescription(mRequestDescription);
                addHolidayViewModel.hitAddHolidayApi(addHolidayRequest);
            } else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For edit holiday */
    private void callEditHolidayApi(String id) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_update_active_holiday);
                RequestBody mRequestId = RequestBody.create(MediaType.parse("text/plain"), id);

                editHolidayViewModel.hitEditHolidayApi(mRequestBodyAction,mRequestId);
            } else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    public void showLogoutDialog(Context mContext) {
        mDialogLogout = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogLogout.setContentView(R.layout.dialog_logout);
        mDialogLogout.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialogLogout.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialogLogout.findViewById(R.id.uploadButton);
        AppCompatButton cancelButton = mDialogLogout.findViewById(R.id.cancelButton);

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogLogout.dismiss();
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogLogout.dismiss();
            }
        });
        mDialogLogout.show();
    }

    public void showEditHolidayDialog(String id,String date,String name) {
        mDialogEditHoliday = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogEditHoliday.setContentView(R.layout.dialog_edit_holiday);
        mDialogEditHoliday.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialogEditHoliday.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialogEditHoliday.findViewById(R.id.uploadButton);
        AppCompatButton cancelButton = mDialogEditHoliday.findViewById(R.id.cancelButton);
        AppCompatTextView tvStart = mDialogEditHoliday.findViewById(R.id.tvStart);
        AppCompatTextView tvDate = mDialogEditHoliday.findViewById(R.id.tvDate);
        AppCompatTextView tvName = mDialogEditHoliday.findViewById(R.id.tvName);
        tvDate.setText("Date : "+AppUtils.convertDobDate(date));
        tvName.setText("Title : "+name);
        tvStart.setText("You want to cancel this holiday ?");
        if(loginTable!=null){
            if(loginTable.getIsadmin().equals("0")){
                tvStart.setText("Holiday Details");
                okayButton.setVisibility(View.GONE);
                cancelButton.setText("Okay");
            }}
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enabledPopup = false;
                callEditHolidayApi(id);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogEditHoliday.dismiss();
                enabledPopup = false;
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogEditHoliday.dismiss();
                enabledPopup = false;
            }
        });

        mDialogEditHoliday.show();

    }
    public void showAddHolidayDialog() {
        dialogAddHoliday = new Dialog(mContext, R.style.ThemeDialogCustom);
        dialogAddHoliday.setContentView(R.layout.dialog_add_holiday);
        dialogAddHoliday.setCanceledOnTouchOutside(true);
        RelativeLayout mClose = dialogAddHoliday.findViewById(R.id.layCancel);
        AppCompatButton okayButton = dialogAddHoliday.findViewById(R.id.submitButton);
        //AppCompatButton cancelButton = dialogAddHoliday.findViewById(R.id.cancelButton);
        tvDateValueFrom = dialogAddHoliday.findViewById(R.id.tvDateValueFrom);
        AutoComHolidayName = dialogAddHoliday.findViewById(R.id.AutoComHolidayName);
        etDescr = dialogAddHoliday.findViewById(R.id.etDescr);
        RelativeLayout layFromDate = dialogAddHoliday.findViewById(R.id.layFromDate);
        layFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDataPicker();
            }
        });
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callAddHolidayApi();
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddHoliday.dismiss();
            }
        });
        dialogAddHoliday.show();
    }

    public void showAttendanceDetailsDialog(List<AttendanceDetailsData> data) {
        Dialog dialogAttendance = new Dialog(mContext, R.style.ThemeDialogCustom);
        dialogAttendance.setContentView(R.layout.dialog_attendance_details);
        dialogAttendance.setCanceledOnTouchOutside(true);
        RelativeLayout mClose = dialogAttendance.findViewById(R.id.layCancel);
        RecyclerView rvSalesList = dialogAttendance.findViewById(R.id.rvSalesList);
        AppCompatTextView tvAttDate  = dialogAttendance.findViewById(R.id.tvAttDate);
        AppCompatImageView iv_emptyLayimage = dialogAttendance.findViewById(R.id.iv_emptyLayimage);
        LinearLayoutCompat empty_layoutActivity = dialogAttendance.findViewById(R.id.empty_layoutActivity);
        setAdapterForAttendanceDetailsList(iv_emptyLayimage,empty_layoutActivity,rvSalesList,data);
        tvAttDate.setText(" (Date : "+stringAttendance+")");
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAttendance.dismiss();
            }
        });
        dialogAttendance.show();
    }

    private void setAdapterForAttendanceDetailsList( AppCompatImageView iv_emptyLayimage,LinearLayoutCompat empty_layoutActivity ,RecyclerView recyclerView,List<AttendanceDetailsData> mList) {
        if (mList != null && mList.size() > 0) {
            attendanceDetailsAdapter = new AttendanceDetailsAdapter(mContext, mList, new AttendanceDetailsAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(AttendanceDetailsData mData) {

                }
            });
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            recyclerView.setAdapter(attendanceDetailsAdapter);
            recyclerView.setVisibility(View.VISIBLE);
            empty_layoutActivity.setVisibility(View.GONE);
        } else {
            recyclerView.setVisibility(View.GONE);
            empty_layoutActivity.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(R.drawable.img_bg_search)
                    .into(iv_emptyLayimage);
        }
    }


    //set date picker view
    private void openDataPicker() {
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
                tvDateValueFrom.setText(sdf.format(myCalendar.getTime()));

            }

        };

        new DatePickerDialog(mContext, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }
    /* Call Api For RM */
    private void callDashboardApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                String[] mon=mFromDate.split("-");
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_birth_day);
                RequestBody mRequestMon = RequestBody.create(MediaType.parse("text/plain"), mon[1].length()==1?"0"+mon[1]:mon[1]);
                RequestBody mRequestComID = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());

                getBirthDayListViewModel.hitGetBirthDayListApi(mRequestAction, mRequestMon,mRequestComID);
            } else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For calender holidays */
    private void callCalenderHolidaysApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_company_holiday);
                RequestBody mRequestComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                RequestBody mRequestFrom = RequestBody.create(MediaType.parse("text/plain"), mFromDate);
                RequestBody mRequestTo = RequestBody.create(MediaType.parse("text/plain"), mToDate);

                calenderHolidaysListViewModel.hitCalenderHolidaysListApi(mRequestAction, mRequestComId,
                        mRequestFrom, mRequestTo);
            } else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }
    /* Call Api For Attendance Details */
    private void callAttendanceDetailsApi(String date) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                snackbar = LogUtil.printSnackBarTest(mContext,R.color.white,((MainActivity) mContext).findViewById(android.R.id.content),"Attendance records are getting ready...");
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_attendance_record_bydate);
                RequestBody mRequestDate = RequestBody.create(MediaType.parse("text/plain"), date);
                RequestBody mRequestEmp = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                RequestBody mRequestCom = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());

                attendanceDetailsViewModel.hitAttendanceDetailsApi(mRequestAction, mRequestDate,
                        mRequestEmp, mRequestCom);
            } else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Profile Image */
    private void callProfileImageApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_profile_img);
                RequestBody mRequestBodyTypeCompId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());

                getProfileImageViewModel.executeGetProfileImageAPI(mRequestBodyType, mRequestBodyTypeCompId,
                        mRequestBodyTypeEmployee);
            } else {
                progressBar.setVisibility(View.GONE);
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            progressBar.setVisibility(View.GONE);
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
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

    private String checkIFNull(String checkString) {
        if (checkString != null && !checkString.equals("") && !checkString.equals("null")) {
        } else {
            checkString = "0";
        }
        return checkString;
    }

    private String iSNull(String checkString, int status) {
        String returnVal = "";
        try {
            if (checkString != null && !checkString.equals("") && !checkString.equals("null")) {
                returnVal = checkString;
            } else {
                returnVal = "0";
            }
        } catch (Exception e) {
        }
        return returnVal;
    }

    private void setDefaultCalender(){
        try{calenderHolidayLeave=new ArrayList<>();
            descHashMap = new HashMap<>();dateHashmap = new HashMap<>();}catch (Exception e){}
            dateHashmap.put(1, "default");
        custom_calendar.setDate(calendar, dateHashmap);
        TextView current_date = custom_calendar.getMonthYearTextView();
        String calMon = AppUtils.convertMonthToIntMMM(current_date.getText().toString());
        String[] strDate = AppUtils.getCurrentDateInyyyymmdd().split("-");
        if (Integer.parseInt(calMon)==Integer.parseInt(strDate[1])) {
            String day = AppUtils.getDayToday(AppUtils.getCurrentDateTime_());
            dateHashmap.put(Integer.parseInt(day.equals("") && day == null ? "0" : day), "current");
            custom_calendar.setDate(calendar, dateHashmap);
        }
    }

    /*Api response */
    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {

            case LOADING:
                ((BaseActivity) getActivity()).showSmallProgressBar(mProgressBarHolder);
                //((BaseActivity)getActivity()).showProgressLoader(getString(R.string.scr_message_please_wait));
                break;

            case SUCCESS:
                ((BaseActivity) getActivity()).dismissSmallProgressBar(mProgressBarHolder);
                swipeStatus = false;
                mSwipeRefreshLayout.setRefreshing(false);
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_BIRTH_DAY_LIST)) {
                            GetBirthDayResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetBirthDayResponse.class);
                            if (responseModel != null /*&& responseModel.getResult().getStatus().equals("success")*/) {
                                try {
                                    birthDayDobdatumList.clear();
                                    upcomingBirthDayDobdatumList.clear();
                                } catch (Exception e) {
                                }
                                if (responseModel.getResult().getDobdata() != null && responseModel.getResult().getDobdata().size() > 0) {
                                    String today = AppUtils.getCurrentDateBirthDay(); //getCurrentDateInyyyymmdd
                                    for (int i = 0; i < responseModel.getResult().getDobdata().size(); i++) {
                                        if(responseModel.getResult().getDobdata().get(i).getDob().trim()!=null
                                        && !responseModel.getResult().getDobdata().get(i).getDob().trim().equals("")) {
                                            String[] str = responseModel.getResult().getDobdata().get(i).getDob().trim().split("-");
                                            if (today.equals(str[1] + "-" + str[2])) {
                                                birthDayDobdatumList.add(responseModel.getResult().getDobdata().get(i));
                                            } else {
                                                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
                                                Date date1 = sdf.parse(today);
                                                Date date2 = sdf.parse(str[1] + "-" + str[2]);
                                                if(date2.compareTo(date1) == 1) {
                                                    upcomingBirthDayDobdatumList.add(responseModel.getResult().getDobdata().get(i));
                                                }
                                            }
                                        }
                                    }
                                }
                                setAdapterForBirthDayList();//"2022-06-10"
                                setAdapterForUpcomingBirthDayList();

                            } else {
                                LogUtil.printToastMSG(getActivity(), responseModel.getResult().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        progressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_PROFILE)) {
                            ProfileImageResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ProfileImageResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                //isUpload = false;
                                AppUtils.loadImageURL(mContext, responseModel.getResult().getProfileurl(), imgBirthPro, progressBar);
                                tvAdminTitle.setText(responseModel.getResult().getEmp_position());
                                tvNameTitle.setText(responseModel.getResult().getEmp_username());
                                try {
                                    String[] str = responseModel.getResult().getEmp_name().split(" ");
                                    imgProfileDash.setText("Hello " + str[0] + "!");
                                }catch (Exception e){}
                            } else {
                                progressBar.setVisibility(View.GONE);
                                //LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        progressBar.setVisibility(View.GONE);
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_CALENDER_HOLIDAY)) {
                            CalenderHolidayResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), CalenderHolidayResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                try{calenderHolidayLeave=new ArrayList<>();
                                    descHashMap = new HashMap<>();dateHashmap = new HashMap<>();}catch (Exception e){}
                                if (responseModel.getResult().getLeave()!=null &&
                                        responseModel.getResult().getLeave().size()>0) {
                                    calenderHolidayLeave = responseModel.getResult().getLeave();
                                    try{
                                            String[] strTest = calenderHolidayLeave.get(0).getDate().split("-");
                                            String[] strDate = AppUtils.getCurrentDateInyyyymmdd().split("-");
                                            if(Integer.parseInt(strTest[1])==Integer.parseInt(strDate[1])){
                                                dateHashmap.put(Integer.parseInt(strDate[2]), "current");
                                            }}catch(Exception e){}
                                            for (int i = 0; i < calenderHolidayLeave.size(); i++) {
                                                String[] str = calenderHolidayLeave.get(i).getDate().split("-");
                                                dateHashmap.put(Integer.valueOf(str[2]), "absent");
                                            }
                                            // set date
                                            // custom_calendar.setNextButtonColor(R.color.colorAccent);
                                            custom_calendar.setDate(calendar, dateHashmap);
                                }
                                else{
                                    setDefaultCalender();}
                               // LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                            } else {
                                setDefaultCalender();
                                LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        progressBar.setVisibility(View.GONE);
                        setDefaultCalender();
                        e.printStackTrace();
                    }
                    try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_LEAVE_COUNT)) {
                            LeaveCountResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), LeaveCountResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                //LoginTable loginTable = mDb.getDbDAO().getLoginData();
                                if(loginTable!=null){
                                    if(loginTable.getIsadmin().equals("1")) { //admin
                                        try {
                                            tvAttendanceTitle.setText("Today's Attendance");
                                            tvAttendanceValue.setText(responseModel.getResult().getTotal_active() + " / " + responseModel.getResult().getActive_employees());
                                            tvLeaveTitle.setText("Today's Late Marks");
                                            tvLeaveValue.setText(""+responseModel.getResult().getLate_mark());
                                        }catch (Exception e){}
                                    }
                                    else{
                                        try{
                                            int totA = Integer.parseInt(responseModel.getResult().getCurent_month_days());
                                            int absA = Integer.parseInt(responseModel.getResult().getTotal_absent_late());

                                            tvAttendanceTitle.setText("Attendance - monthly");
                                        tvAttendanceValue.setText(absA+" / "+totA);
                                        tvLeaveTitle.setText("Late mark - monthly");
                                        tvLeaveValue.setText(""+responseModel.getResult().getLate_mark()+" / "+totA);
                                        }catch (Exception e){}
                                    }
                                }
                            }
                        }
                    }catch (Exception e){e.printStackTrace();
                        LogUtil.printToastMSG(mContext,loginTable.getIsadmin()+e.getMessage());}
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_ADD_HOLIDAY)) {
                            AddHolidayResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), AddHolidayResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                dialogAddHoliday.dismiss();
                                showSuccessDialogFragment(mContext,responseModel.getResult().getMessage(),
                                        true,dialogAddHoliday);
                                //mFromDate = AppUtils.startMonth(); mToDate = AppUtils.endMonth();
                                callCalenderHolidaysApi();
                                callDashboardApi();
                            }
                            else{
                                showSuccessDialogFragment(mContext,responseModel.getResult().getMessage(),
                                        false,dialogAddHoliday);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_EDIT_HOLIDAY)) {
                            EditEmployeeResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), EditEmployeeResponse.class);
                            enabledPopup = false;
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                mDialogEditHoliday.dismiss();
                                showSuccessDialogFragment(mContext,responseModel.getResult().getMessage(),
                                        true,null);
                                //mFromDate = AppUtils.startMonth(); mToDate = AppUtils.endMonth();
                                callCalenderHolidaysApi();
                                callDashboardApi();
                            }
                            else{
                                showSuccessDialogFragment(mContext,responseModel.getResult().getMessage(),
                                        false,null);
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}
                    try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_ATTENDANCE_DETAILS)) {
                           try{  snackbar.dismiss();
                           }catch (Exception e){}
                            AttendanceDetailsResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), AttendanceDetailsResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                showAttendanceDetailsDialog(responseModel.getResult().getData());
                            }
                            else{
                               LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_SINLE_EMPLOYEES_LIST)) {
                            EmployeeListResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), EmployeeListResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                mDb.getDbDAO().deleteAttendanceData();
                                EmployeeList employeeListResData = responseModel.getResult().getList().get(0);
                                AttendanceDaysTable daysTable = new AttendanceDaysTable();
                                DayData dayData = new DayData();
                                dayData.setMonDay("Monday");dayData.setMonWorking(employeeListResData.getMonWorking()==null?"No":employeeListResData.getMonWorking());
                                dayData.setMonStartTime(employeeListResData.getMonStartTime()==null?"10:00:00":employeeListResData.getMonStartTime());
                                dayData.setMonEndTime(employeeListResData.getMonEndTime()==null?"19:00:00":employeeListResData.getMonEndTime());
                                dayData.setTueDay("Tuesday");dayData.setTueWorking(employeeListResData.getTueWorking()==null?"No":employeeListResData.getTueWorking());
                                dayData.setTueStartTime(employeeListResData.getTueStartTime()==null?"10:00:00":employeeListResData.getTueStartTime());
                                dayData.setTueEndTime(employeeListResData.getTueEndTime()==null?"19:00:00":employeeListResData.getTueEndTime());
                                dayData.setWedDay("Wednesday");dayData.setWedWorking(employeeListResData.getWedWorking()==null?"No":employeeListResData.getWedWorking());
                                dayData.setWedStartTime(employeeListResData.getWedStartTime()==null?"10:00:00":employeeListResData.getWedStartTime());
                                dayData.setWedEndTime(employeeListResData.getWedEndTime()==null?"19:00:00":employeeListResData.getWedEndTime());
                                dayData.setThrusDay("Thursday");dayData.setThrusWorking(employeeListResData.getThrusWorking()==null?"No":employeeListResData.getThrusWorking());
                                dayData.setThrusStartTime(employeeListResData.getThrusStartTime()==null?"10:00:00":employeeListResData.getThrusStartTime());
                                dayData.setThrusEndTime(employeeListResData.getThrusEndTime()==null?"19:00:00":employeeListResData.getThrusEndTime());
                                dayData.setFriDay("Friday");dayData.setFriWorking(employeeListResData.getFriWorking()==null?"No":employeeListResData.getFriWorking());
                                dayData.setFriStartTime(employeeListResData.getFriStartTime()==null?"10:00:00":employeeListResData.getFriStartTime());
                                dayData.setFriEndTime(employeeListResData.getFriEndTime()==null?"19:00:00":employeeListResData.getFriStartTime());
                                dayData.setSatDay("Saturday");dayData.setSatWorking(employeeListResData.getSatWorking()==null?"No":employeeListResData.getSatWorking());
                                dayData.setSatStartTime(employeeListResData.getSatStartTime()==null?"10:00:00":employeeListResData.getSatStartTime());
                                dayData.setSatEndTime(employeeListResData.getSatEndTime()==null?"19:00:00":employeeListResData.getSatEndTime());
                                dayData.setSunDay("Sunday");dayData.setSunWorking(employeeListResData.getSunWorking()==null?"No":employeeListResData.getSunWorking());
                                dayData.setSunStartTime(employeeListResData.getSunStartTime()==null?"10:00:00":employeeListResData.getSunStartTime());
                                dayData.setSunEndTime(employeeListResData.getSunEndTime()==null?"19:00:00":employeeListResData.getSunEndTime());
                                daysTable.setLoginDays(dayData);
                                mDb.getDbDAO().insertAttendanceData(daysTable);
                                //AttendanceDaysTable loginAttendance = mDb.getDbDAO().getTestAttendanceData(); //check
                                if (dayData != null) {
                                    setAttendanceFloatingButtons(dayData);
                                }
                               }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case ERROR:
                ((BaseActivity) getActivity()).dismissSmallProgressBar(mProgressBarHolder);
                swipeStatus = false;
                mSwipeRefreshLayout.setRefreshing(false);
                //((BaseActivity)getActivity()).dismissLoader();
                LogUtil.printToastMSG(getActivity(), getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }
}