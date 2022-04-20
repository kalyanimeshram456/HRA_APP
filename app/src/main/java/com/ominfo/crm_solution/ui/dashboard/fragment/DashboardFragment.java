package com.ominfo.crm_solution.ui.dashboard.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.basecontrol.BaseApplication;
import com.ominfo.crm_solution.basecontrol.BaseFragment;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.interfaces.Constants;
import com.ominfo.crm_solution.interfaces.SharedPrefKey;
import com.ominfo.crm_solution.network.ApiResponse;
import com.ominfo.crm_solution.network.DynamicAPIPath;
import com.ominfo.crm_solution.network.NetworkCheck;
import com.ominfo.crm_solution.network.ViewModelFactory;
import com.ominfo.crm_solution.ui.attendance.StartAttendanceActivity;
import com.ominfo.crm_solution.ui.attendance.ripple_effect.RippleBackground;
import com.ominfo.crm_solution.ui.dashboard.adapter.CrmAdapter;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.dashboard.model.Dashboard;
import com.ominfo.crm_solution.ui.dashboard.model.DashboardRequest;
import com.ominfo.crm_solution.ui.dashboard.model.GetDashboardResponse;
import com.ominfo.crm_solution.ui.dashboard.model.GetDashboardViewModel;
import com.ominfo.crm_solution.ui.dashboard.model.HighlightModel;
import com.ominfo.crm_solution.ui.enquiry_report.EnquiryReportFragment;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.lost_apportunity.LostApportunityFragment;
import com.ominfo.crm_solution.ui.my_account.MyAccountFragment;
import com.ominfo.crm_solution.ui.my_account.model.ChangePasswordViewModel;
import com.ominfo.crm_solution.ui.my_account.model.ChangeProfileImageViewModel;
import com.ominfo.crm_solution.ui.my_account.model.GetProfileImageViewModel;
import com.ominfo.crm_solution.ui.my_account.model.ProfileImageResponse;
import com.ominfo.crm_solution.ui.my_account.model.ProfileViewModel;
import com.ominfo.crm_solution.ui.notifications.NotificationsActivity;
import com.ominfo.crm_solution.ui.quotation_amount.QuotationFragment;
import com.ominfo.crm_solution.ui.sale.SaleFragment;
import com.ominfo.crm_solution.ui.visit_report.activity.StartEndVisitActivity;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;
import com.ominfo.crm_solution.util.SharedPref;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;

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

     @BindView(R.id.layVisitTimer)
     LinearLayoutCompat layVisitTimer;
    @BindView(R.id.imgSearchLr)
    AppCompatImageView imgSearchLr;
    @BindView(R.id.tvVisitNoValue)
    AppCompatTextView tvVisitNoValue;
    @BindView(R.id.tvName)
    AppCompatTextView tvName;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    @BindView(R.id.tvSale)
    AppCompatTextView tvSale;
    @BindView(R.id.tvQuotationCount)
    AppCompatTextView tvQuotationCount;
    @BindView(R.id.tvLostApp)
    AppCompatTextView tvLostApp;
    @BindView(R.id.tvEnquiryCount)
    AppCompatTextView tvEnquiryCount;
    @BindView(R.id.tvHighlight)
    AppCompatAutoCompleteTextView tvHighlight;
    @BindView(R.id.rvDashboardList)
    RecyclerView rvDashboardList;
    @BindView(R.id.imgProfileDash)
    CircleImageView imgProfileDash;
    @BindView(R.id.chronometer)
    Chronometer tvCounter;
    @BindView(R.id.imgBack)
    AppCompatImageView imgBack;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    final Calendar myCalendar = Calendar.getInstance();
    private AppDatabase mDb;
    CrmAdapter mCrmAdapter;
    List<DashModel> dashboardList = new ArrayList<>();
    @Inject
    ViewModelFactory mViewModelFactory;
    private GetProfileImageViewModel getProfileImageViewModel;
    public static boolean activityVisible; // Variable that will check the
    final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    IntentFilter intentFilter;
    //MyReceiver receiver;
    private GetDashboardViewModel getDashboardViewModel;
    //private GetVehicleViewModel mGetVehicleViewModel;
    //private AppDatabase mDb;
    String mUrl = "", mFinalURL = "";
    boolean mSearchStatus = false, mScrolledStatus = false;
    int type = 0;
    private boolean loading = true;
    private String mUserKey = "";

    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.rippleEffect)
    RippleBackground rippleEffect;
    @BindView(R.id.add_attendance)
    FloatingActionButton add_attendance;

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
        ((BaseActivity)mContext).getDeps().inject(this);
        injectAPI();
        init();
        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        setTimer();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((BaseActivity)mContext).initToolbar(0,mContext,R.id.imgBack,R.id.imgReport,R.id.imgNotify,tvNotifyCount,0,R.id.imgCall);
        setTimer();
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.status_bar_color));

    }

    private void injectAPI() {
        getDashboardViewModel = ViewModelProviders.of(getActivity(), mViewModelFactory).get(GetDashboardViewModel.class);
        getDashboardViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_DASHBOARD));

         getProfileImageViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetProfileImageViewModel.class);
        getProfileImageViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_PROFILE));
  }

    //set value to Search dropdown
    private void setDropdownHighlight() {
        tvHighlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvHighlight.showDropDown();
            }
        });
        List<HighlightModel> highlightModelList = new ArrayList<>();
        highlightModelList.add(new HighlightModel("Today's Hightlights",
                AppUtils.getDashCurrentDateTime_(), AppUtils.getDashCurrentDateTime_()));
        highlightModelList.add(new HighlightModel("Yesterday's Hightlights",
                AppUtils.getDashYesterdaysDate(),AppUtils.getDashYesterdaysDate()));
        highlightModelList.add(new HighlightModel("This Week's Hightlights",
                AppUtils.getStartWeek(),AppUtils.getEndWeek()));
        highlightModelList.add(new HighlightModel("Last Week's Hightlights"
                ,AppUtils.getStartLastWeek(), AppUtils.getEndLastWeek()));
        highlightModelList.add(new HighlightModel("This Month's Hightlights",
                AppUtils.startMonth(), AppUtils.endMonth()));
        highlightModelList.add(new HighlightModel("Last Month's Hightlights",
                AppUtils.startLastMonth(), AppUtils.endLastMonth()));
        highlightModelList.add(new HighlightModel("This Year's Hightlights",
                AppUtils.startYear(), AppUtils.endYear()));
        try {
            int pos = 0;
            if (highlightModelList != null && highlightModelList.size() > 0) {
                String[] mDropdownList = new String[highlightModelList.size()];
                for (int i = 0; i < highlightModelList.size(); i++) {
                    mDropdownList[i] = String.valueOf(highlightModelList.get(i).getHighlight());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mContext,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //tvHighlight.setThreshold(1);
                tvHighlight.setAdapter(adapter);
                tvHighlight.setHint(mDropdownList[pos]);
                tvHighlight.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                          for(int i=0;i<highlightModelList.size();i++){
                              if(highlightModelList.get(i).getHighlight().equals(mDropdownList[position])){
                                 /* LogUtil.printToastMSG(mContext,highlightModelList.get(i).getDateFrom()
                                  +"  "+highlightModelList.get(i).getDateTo());*/
                                  callDashboardApi(highlightModelList.get(i).getDateFrom()
                                          ,highlightModelList.get(i).getDateTo());
                              }
                          }
                    }
                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setTimer(){
        Boolean iSTimer = SharedPref.getInstance(mContext).read(SharedPrefKey.TIMER_STATUS, false);
        if (iSTimer){
            layVisitTimer.setVisibility(View.VISIBLE);
            String visit = SharedPref.getInstance(mContext).read(SharedPrefKey.VISIT_NO, "");
            tvVisitNoValue.setText(visit);
            setTimerCounter(0);
        } else {
            layVisitTimer.setVisibility(View.GONE);
            setTimerMillis(mContext,0);
            tvCounter.setBase(SystemClock.elapsedRealtime());
            tvCounter.stop();
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    //perform click actions
    @OnClick({R.id.cardSale,R.id.cardQuotation,R.id.cardEnquiry,R.id.layVisitTimer,R.id.cardLostOpportunity
    ,R.id.add_attendance,R.id.imgProfileDash})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add_attendance:
                Intent iAtt = new Intent(mContext, StartAttendanceActivity.class);
                iAtt.putExtra(Constants.TRANSACTION_ID, "0");
                startActivity(iAtt);
                break;
            case R.id.cardSale:
                Fragment fragment = new SaleFragment();
                moveFromFragment(fragment,mContext);
                break;
            case R.id.imgProfileDash:
               /* Fragment fragmentAcc = new MyAccountFragment();
                moveFromFragment(fragmentAcc,mContext);*/
                break;
            case R.id.cardLostOpportunity:
                Fragment fragmentLost = new LostApportunityFragment();
                moveFromFragment(fragmentLost,mContext);
                break;
            case R.id.cardQuotation:
                Fragment fragmentQuo = new QuotationFragment();
                moveFromFragment(fragmentQuo,mContext);
                break;
            case R.id.cardEnquiry:
                Fragment fragmentEnq = new EnquiryReportFragment();
                moveFromFragment(fragmentEnq,mContext);
                break;
            case R.id.layVisitTimer:
                Intent i = new Intent(mContext, StartEndVisitActivity.class);
                i.putExtra(Constants.TRANSACTION_ID, "0");
                startActivity(i);
                break;
        }
    }

    private void init(){
        mDb =BaseApplication.getInstance(mContext).getAppDatabase();
        //requestPermission();
        setToolbar();
        //set ripple effect
        //rippleEffect.setBackgroundColor(mContext.getResources().getColor(R.color.deep_yellow));
        rippleEffect.startRippleAnimation(2,mContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            add_attendance.setForeground(mContext.getDrawable(R.drawable.attention_gradient_blue));
        }
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rippleEffect.stopRippleAnimation();
                rippleEffect.startRippleAnimation(1,mContext);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    add_attendance.setForeground(mContext.getDrawable(R.drawable.attention_gradient_yellow));
                }
            }
        },10000);
        final Handler handlerRed=new Handler();
        handlerRed.postDelayed(new Runnable() {
            @Override
            public void run() {
                rippleEffect.stopRippleAnimation();
                rippleEffect.startRippleAnimation(3,mContext);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    add_attendance.setForeground(mContext.getDrawable(R.drawable.attention_gradient_red));
                }
            }
        },16000);
        setDropdownHighlight();
        dashboardList.removeAll(dashboardList);
        dashboardList.add(new DashModel("Sales Credit","₹0",mContext.getDrawable(R.drawable.ic_om_sales_credit)));
        dashboardList.add(new DashModel("Receipt","₹0",mContext.getDrawable(R.drawable.ic_om_receipt)));
        dashboardList.add(new DashModel("Top Customer","Top Customer",mContext.getDrawable(R.drawable.ic_om_rating)));
        dashboardList.add(new DashModel("Total Quotation Amount","₹0",mContext.getDrawable(R.drawable.ic_om_total_quotation)));
        dashboardList.add(new DashModel("Dispatch Pending","0",mContext.getDrawable(R.drawable.ic_om_dispatch_pending)));
        dashboardList.add(new DashModel("Enquiry Report","0",mContext.getDrawable(R.drawable.ic_om_enquiry_report)));
        dashboardList.add(new DashModel("Visit Report","0",mContext.getDrawable(R.drawable.ic_om_visit_report)));
        dashboardList.add(new DashModel("Products","",mContext.getDrawable(R.drawable.ic_om_product)));
        setAdapterForDashboardList();
        callProfileImageApi();
        callDashboardApi(AppUtils.getDashCurrentDateTime_(), AppUtils.getDashCurrentDateTime_());
        try {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                tvName.setText(loginTable.getName());
            }
            else{
                LogUtil.printToastMSG(mContext,"Something went wrong ,Please re-login.");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setToolbar(){
        ((BaseActivity)mContext).initToolbar(0,mContext,R.id.imgBack,R.id.imgReport,R.id.imgNotify,tvNotifyCount,0,R.id.imgCall);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new DashboardFragment();
                ((BaseActivity)mContext).moveFragment(mContext,fragment);
            }
        });
        imgNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity)mContext).launchScreen(mContext, NotificationsActivity.class);;
            }
        });
    }

    /* Call Api For RM */
    private void callDashboardApi(String startDate,String endDate) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                DashboardRequest dashboardRequest = new DashboardRequest();
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_dashboard);
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"),String.valueOf(loginTable.getEmployeeId()));
                RequestBody mRequestBodyTypeCompId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(loginTable.getCompanyId()));
                RequestBody mRequestBodyTypeStart = RequestBody.create(MediaType.parse("text/plain"), startDate);
                RequestBody mRequestBodyTypeEnd = RequestBody.create(MediaType.parse("text/plain"), endDate);
                LogUtil.printLog("httpdashboard_data",String.valueOf(loginTable.getEmployeeId())+" "+ String.valueOf(loginTable.getCompanyId())
                +" "+startDate+" "+endDate);
                dashboardRequest.setAction(mRequestBodyType);
                dashboardRequest.setEmployee(mRequestBodyTypeEmployee);
                dashboardRequest.setCompanyId(mRequestBodyTypeCompId);
                dashboardRequest.setStartDate(mRequestBodyTypeStart);
                dashboardRequest.setEndDate(mRequestBodyTypeEnd);
                getDashboardViewModel.hitGetDashboardApi(dashboardRequest);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }


    private void setAdapterForDashboardList() {
        if (dashboardList.size() > 0) {
            rvDashboardList.setVisibility(View.VISIBLE);
        } else {
            rvDashboardList.setVisibility(View.GONE);
        }
        mCrmAdapter = new CrmAdapter(mContext, dashboardList, new CrmAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(DashModel mDataTicket) {

            }
        });
        rvDashboardList.setHasFixedSize(true);
        rvDashboardList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvDashboardList.setAdapter(mCrmAdapter);
    }

    /* Call Api For Profile Image */
    private void callProfileImageApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_profile_img);
                RequestBody mRequestBodyTypeCompId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());

                getProfileImageViewModel.executeGetProfileImageAPI(mRequestBodyType,mRequestBodyTypeCompId,
                        mRequestBodyTypeEmployee);
            }
            else {
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
    private void checkIFNull(String checkString,AppCompatTextView textView) {
        if (checkString != null && !checkString.equals("") && !checkString.equals("null")) {
            textView.setText(checkString);
        } else {
            textView.setText("0");
        }
    }
    private String iSNull(String checkString,int status){
        String returnVal = "";
        try {
            if (status == 1) {
                returnVal = "0";
            }
            if (checkString != null && !checkString.equals("") && !checkString.equals("null")) {
                return checkString;
            }
        }catch (Exception e){}
        return returnVal;
    }

        /*Api response */
        private void consumeResponse(ApiResponse apiResponse, String tag) {
            switch (apiResponse.status) {

                case LOADING:
                    ((BaseActivity)getActivity()).showSmallProgressBar(mProgressBarHolder);
                    //((BaseActivity)getActivity()).showProgressLoader(getString(R.string.scr_message_please_wait));
                    break;

                case SUCCESS:
                    ((BaseActivity)getActivity()).dismissSmallProgressBar(mProgressBarHolder);
                    //((BaseActivity)getActivity()).dismissLoader();
                    if (!apiResponse.data.isJsonNull()) {
                        LogUtil.printLog(tag, apiResponse.data.toString());
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_DASHBOARD)) {
                            GetDashboardResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetDashboardResponse.class);
                            if (responseModel != null /*&& responseModel.getResult().getStatus().equals("success")*/) {
                                dashboardList.removeAll(dashboardList);
                                checkIFNull(String.valueOf(responseModel.getDashboard().getTotalSales()), tvSale) ;
                                checkIFNull(String.valueOf(responseModel.getDashboard().getQuotationAmount()), tvQuotationCount) ;
                                checkIFNull(String.valueOf(responseModel.getDashboard().getLostOpportunityCount()), tvLostApp) ;
                                checkIFNull(String.valueOf(responseModel.getDashboard().getEnquiryCount()), tvEnquiryCount) ;
                                checkIFNull(String.valueOf(responseModel.getDashboard().getNotificationCount()), tvNotifyCount) ;
                                Dashboard dashboard = responseModel.getDashboard();
                                dashboardList.add(new DashModel("Sales Credit","₹"+iSNull(String.valueOf(dashboard.getSalesCredit()),1),mContext.getDrawable(R.drawable.ic_om_sales_credit)));
                                dashboardList.add(new DashModel("Receipt","₹"+iSNull(dashboard.getReceiptAmount(),1),mContext.getDrawable(R.drawable.ic_om_receipt)));
                                dashboardList.add(new DashModel("Top Customer","Top Customer",mContext.getDrawable(R.drawable.ic_om_rating)));
                                dashboardList.add(new DashModel("Total Quotation Amount","₹"+iSNull(dashboard.getQuotationAmount(),1),mContext.getDrawable(R.drawable.ic_om_total_quotation)));
                                dashboardList.add(new DashModel("Dispatch Pending",""+iSNull(dashboard.getPendingDispatchCount(),1),mContext.getDrawable(R.drawable.ic_om_dispatch_pending)));
                                dashboardList.add(new DashModel("Enquiry Report",""+iSNull(dashboard.getEnquiryCount(),1),mContext.getDrawable(R.drawable.ic_om_enquiry_report)));
                                dashboardList.add(new DashModel("Visit Report",""+iSNull(dashboard.getVisitReport(),1),mContext.getDrawable(R.drawable.ic_om_visit_report)));
                                dashboardList.add(new DashModel("Products","",mContext.getDrawable(R.drawable.ic_om_product)));
                                setAdapterForDashboardList();
                            } else {
                                LogUtil.printToastMSG(getActivity(), responseModel.getResult().getMessage());
                            }
                        }
                        try {
                            if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_PROFILE)) {
                                ProfileImageResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ProfileImageResponse.class);
                                if (responseModel != null/* && responseModel.getStatus()==1*/) {
                                    //isUpload = false;
                                    AppUtils.loadImageURL(mContext,responseModel.getResult().getProfileurl(),imgProfileDash, progressBar);
                                    //LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                                }
                                else{
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        }catch (Exception e){
                            progressBar.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                    break;
                case ERROR:
                    ((BaseActivity)getActivity()).dismissSmallProgressBar(mProgressBarHolder);
                    //((BaseActivity)getActivity()).dismissLoader();
                    LogUtil.printToastMSG(getActivity(), getString(R.string.err_msg_connection_was_refused));
                    break;
            }
        }


    private void downloadImageFromUrl(String url, String lr, File file, int pos) {
        @SuppressLint("StaticFieldLeak")
        class DownloadFileFromURL extends AsyncTask<String, String, String> {

            private static final String TAG = "yyyy";
            private String Path = file.getAbsolutePath();

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //mDialogloader.show();
                //showProgressLoader(getString(R.string.scr_message_please_wait));
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                   // Array6 repeatativeList = new Array6();
                    if (!url.equals("")) {
                        int count;
                        try {
                            String baseUrl = url;
                            URL url = new URL(baseUrl/* + params[0]*/);
                            URLConnection connection = url.openConnection();
                            connection.connect();

                            // this will be useful so that you can show a tipical 0-100%
                            // progress bar
                            int lengthOfFile = connection.getContentLength();

                            // download the file
                            InputStream input = new BufferedInputStream(url.openStream(), 8192);

                            // Output stream
                            OutputStream output = new FileOutputStream(file);

                            byte[] data = new byte[1024];

                            long total = 0;

                            while ((count = input.read(data)) != -1) {
                                total += count;
                                // publishing the progress....
                                // After this onProgressUpdate will be called
                                publishProgress("" + (int) ((total * 100) / lengthOfFile));

                                // writing data to file
                                output.write(data, 0, count);
                            }

                            // flushing output
                            output.flush();

                            // closing streams
                            output.close();
                            input.close();
                            Path = file.getAbsolutePath();
                            //new VehicleDetailsLrImage(lr, "", null,file.getAbsolutePath()));

                        } catch (Exception e) {
                            LogUtil.printLog(TAG, "Error: " + Objects.requireNonNull(e.getMessage()));
                        }
                    }
                    //LrNo = mImageList.get(i).getLr();
                    // }
                    return Path;
                } catch (Exception e) {
                    e.printStackTrace();
                    return Path;
                }
            }

            @Override
            protected void onPostExecute(String list) {
                LogUtil.printLog("image_new",list);
                //Bitmap myBitmap = BitmapFactory.decodeFile(new File(list).getAbsolutePath());
                //imgProfileDash.setImageBitmap(myBitmap);
                AppUtils.loadImageURL(mContext, list
                        , imgProfileDash, null);
            }
        }
        new DownloadFileFromURL().execute("", "path", "lr");
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


    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }*/

}