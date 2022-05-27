package com.ominfo.hra_app.ui.notifications;

import static com.ominfo.hra_app.util.AppUtils.getChangeDateForHisab;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.interfaces.SharedPrefKey;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.login.LoginActivity;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.notifications.adapter.NotificationsAdapter;
import com.ominfo.hra_app.ui.notifications.model.DeleteNotificationResponse;
import com.ominfo.hra_app.ui.notifications.model.DeleteNotificationViewModel;
import com.ominfo.hra_app.ui.notifications.model.GetSingleRecordViewModel;
import com.ominfo.hra_app.ui.notifications.model.LeaveStatusViewModel;
import com.ominfo.hra_app.ui.notifications.model.NotificationData;
import com.ominfo.hra_app.ui.notifications.model.NotificationDetailsNotify;
import com.ominfo.hra_app.ui.notifications.model.NotificationDetailsResponse;
import com.ominfo.hra_app.ui.notifications.model.NotificationResponse;
import com.ominfo.hra_app.ui.notifications.model.NotificationViewModel;
import com.ominfo.hra_app.ui.notifications.model.UpdateLeaveStatusResponse;
import com.ominfo.hra_app.ui.salary.model.DeductLeaveViewModel;
import com.ominfo.hra_app.ui.salary.model.MarkNotLateViewModel;
import com.ominfo.hra_app.ui.salary.model.MarkPresentViewModel;
import com.ominfo.hra_app.ui.salary.model.UnpaidLeaveViewModel;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;
import com.ominfo.hra_app.util.SharedPref;

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

public class NotificationsActivity extends BaseActivity {

    Context mContext;
    @BindView(R.id.rvPuranaHisab)
    RecyclerView rvPuranaHisab;
    @BindView(R.id.tvTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.tv_emptyLayTitle)
    AppCompatTextView textViewEmptyLayTitle;
    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat linearLayoutEmptyActivity;
    @BindView(R.id.iv_emptyLayimage)
    AppCompatImageView iv_emptyLayimage;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.emptyBox)
    LinearLayoutCompat emptyBox;
    @BindView(R.id.imgBack)
    LinearLayoutCompat imgBack;
    NotificationsAdapter mNotificationsAdapter;
    List<NotificationData> notificationList = new ArrayList<>();
    @Inject
    ViewModelFactory mViewModelFactory;
    DeductLeaveViewModel deductLeaveViewModel;
    private NotificationViewModel notificationViewModel;
    private DeleteNotificationViewModel deleteNotificationViewModel;
    private GetSingleRecordViewModel getSingleRecordViewModel;
    private LeaveStatusViewModel leaveStatusViewModel;
    private MarkPresentViewModel markPresentViewModel;
    private UnpaidLeaveViewModel unpaidLeaveViewModel;
    private MarkNotLateViewModel markNotLateViewModel;
    private AppDatabase mDb;
    private Dialog mDialogPaidLeave;
    LinearLayoutCompat layoutLeaveTime;
    AppCompatTextView appcomptextLeaveTime;
    View viewToDate;
    RelativeLayout layToDate;
    int notiId = 0;
    Dialog mDialogAbsentMark,mDialogLateMark;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mContext = this;
        ButterKnife.bind(this);
        getDeps().inject(this);
        injectAPI();
        init();
    }

    private void init(){
        //set toolbar
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        setToolbar();
        Boolean iSLoggedIn = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.IS_LOGGED_IN, false);
        if (iSLoggedIn){
            //launchScreen(MainActivity.class);
           /* imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //finishAffinity();
                    //launchScreen(mContext,MainActivity.class);
                }*/
            //});
        }else {
            finishAffinity();
            launchScreen(mContext, LoginActivity.class);
        }
        SharedPref.getInstance(getApplicationContext()).write(SharedPrefKey.IS_NOTIFY, false);
        SharedPref.getInstance(mContext).write(SharedPrefKey.IS_NOTIFY_COUNT,"0");
        //initToolbar(1,mContext,R.id.imgBack,R.id.imgReport,R.id.tvComplaintCount,R.id.imgNotify,R.id.tvNotifyCount,0,R.id.imgCall);
        setNotifyCount();
        setAdapterForNotificationList();
        callNotificationApi();
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkCheck.isInternetAvailable(mContext)) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something
                            setNotifyCount();
                            setAdapterForNotificationList();
                            callNotificationApi();
                        }
                    }, 1000);

                } else {
                    LogUtil.printToastMSG(mContext,getString(R.string.err_msg_connection_was_refused));
                    mSwipeRefreshLayout.setRefreshing(false);

                }
            }
        });
    }

    private void setToolbar(){
        //set toolbar title
        toolbarTitle.setText("Notifications");
        initToolbar(1,mContext,R.id.imgBack,R.id.imgReport,R.id.imgNotify,tvNotifyCount,0,R.id.imgCall);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Boolean iSLoggedIn = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.IS_LOGGED_IN, false);
        /*if (!iSLoggedIn){
            //launchScreen(MainActivity.class);
            finishAffinity();
            launchScreen(mContext,MainActivity.class);
        }*/
    }

    private void injectAPI() {
        notificationViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(NotificationViewModel.class);
        notificationViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_NOTIFICATION));

        deleteNotificationViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(DeleteNotificationViewModel.class);
        deleteNotificationViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_DEL_NOTIFICATION));

        getSingleRecordViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(GetSingleRecordViewModel.class);
        getSingleRecordViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_SINGLE_NOTIFY));

        leaveStatusViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(LeaveStatusViewModel.class);
        leaveStatusViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_LEAVE_STATUS));

        markPresentViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(MarkPresentViewModel.class);
        markPresentViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_MARK_PRESENT));

        unpaidLeaveViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(UnpaidLeaveViewModel.class);
        unpaidLeaveViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_UNPAID_LEAVE));

        markNotLateViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(MarkNotLateViewModel.class);
        markNotLateViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_MARK_NOT_LATE));

        deductLeaveViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(DeductLeaveViewModel.class);
        deductLeaveViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_DEDUCT_LEAVE));

    }

    /* Call Api Notification */
    private void callNotificationApi() {
        if (NetworkCheck.isInternetAvailable(NotificationsActivity.this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            textViewEmptyLayTitle.setText("Please wait...");
            if (loginTable != null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_notification);
                RequestBody mRequestBodyTypeCompId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                RequestBody mRequestBodyDate = RequestBody.create(MediaType.parse("text/plain"), AppUtils.getCurrentDateInyyyymmdd());
                notificationViewModel.hitNotificationApi(mRequestBodyType,mRequestBodyTypeCompId
                        ,mRequestBodyDate);
            }
        } else {
            LogUtil.printToastMSG(NotificationsActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }


    /* Call Api For Single record */
    private void callSingleRecordApi(String id) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_single_notify);
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                RequestBody mRequestBodyDate = RequestBody.create(MediaType.parse("text/plain"), AppUtils.getCurrentDateInyyyymmdd());
                getSingleRecordViewModel.hitLeaveSingleRecordApi(mRequestBodyType,mRequestBodyTypeEmpId
                        ,mRequestBodyDate);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    //show leave form popup
    public void showPaidLeaveDialog(NotificationDetailsNotify data) {
        mDialogPaidLeave = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogPaidLeave.setContentView(R.layout.dialog_paid_leave);
        mDialogPaidLeave.setCanceledOnTouchOutside(true);
        AppCompatAutoCompleteTextView AutoComTextViewLeaveType = mDialogPaidLeave.findViewById(R.id.AutoComTextViewLeaveType);
        AppCompatButton submitButton = mDialogPaidLeave.findViewById(R.id.submitButton);
        setDropdownType(AutoComTextViewLeaveType);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //paid leave api
            }
        });
        mDialogPaidLeave.show();
    }

    //show leave form popup
    public void showDeductLeaveDialog(NotificationDetailsNotify data) {
        mDialogPaidLeave = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogPaidLeave.setContentView(R.layout.dialog_deduct_leave);
        mDialogPaidLeave.setCanceledOnTouchOutside(true);
        AppCompatAutoCompleteTextView AutoComTextViewDuration = mDialogPaidLeave.findViewById(R.id.AutoComTextViewDuration);
        AppCompatTextView tvDateValueFrom = mDialogPaidLeave.findViewById(R.id.tvDateValueFrom);
        RelativeLayout layFromDate = mDialogPaidLeave.findViewById(R.id.layFromDate);
        AppCompatTextView tvDateValue = mDialogPaidLeave.findViewById(R.id.tvDateValue);
        RelativeLayout layToDate = mDialogPaidLeave.findViewById(R.id.layToDate);
        AppCompatButton submitButton = mDialogPaidLeave.findViewById(R.id.submitButton);
        setDropdownLeaveDuration(AutoComTextViewDuration);
        layFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataPicker(tvDateValueFrom);
            }
        });
        layToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataPicker(tvDateValue);
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //paid leave api
                if (NetworkCheck.isInternetAvailable(mContext)) {
                    LoginTable loginTable = mDb.getDbDAO().getLoginData();
                    if (loginTable != null) {
                        RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_leave_deduct);
                        RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                        RequestBody mRequestBodyDate = RequestBody.create(MediaType.parse("text/plain"), AppUtils.convert24to12(tvDateValueFrom.getText().toString()));
                        RequestBody mRequestStatus = RequestBody.create(MediaType.parse("text/plain"), "absent");
                        RequestBody mRequestleave_type = RequestBody.create(MediaType.parse("text/plain"), "unpaid");
                        RequestBody mRequestleave_days = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewDuration.getText().toString());

                        deductLeaveViewModel.hitDeductLeaveAPI(mRequestBodyType,mRequestBodyTypeEmpId
                                ,mRequestBodyDate,mRequestStatus,mRequestleave_type,mRequestleave_days);
                    }
                    else {
                        LogUtil.printToastMSG(mContext, "Something is wrong.");
                    }
                } else {
                    LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                }
            }
        });
        mDialogPaidLeave.show();
    }
    //set date picker view
    private void openDataPicker(AppCompatTextView textView) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                textView.setText(sdf.format(myCalendar.getTime()));
            }

        };
        /*  new DatePickerDialog(mContext, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/

        DatePickerDialog dpDialog = new DatePickerDialog(mContext, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
       /* if(val==0) {
            String dateRestrict = AppUtils.changeDateHisab(tvDateValueTo.getText().toString());
            dpDialog.getDatePicker().setMaxDate(getChangeDateForHisab(dateRestrict));
        }
        else {
            String dateRestrict = AppUtils.changeDateHisab(tvDateValueFrom.getText().toString());
            dpDialog.getDatePicker().setMinDate(getChangeDateForHisab(dateRestrict));
        }*/
        dpDialog.show();

    }

    //set value to Search dropdown
    private void setDropdownLeaveDuration(AppCompatAutoCompleteTextView mListDropdownView) {
        List<String> leaveModelList = new ArrayList<>();
        leaveModelList.add("Half Day");
        leaveModelList.add("Full Day");
        leaveModelList.add("Multiple Days");
        try {
            int pos = 0;
            if (leaveModelList != null && leaveModelList.size() > 0) {
                String[] mDropdownList = new String[leaveModelList.size()];
                for (int i = 0; i < leaveModelList.size(); i++) {
                    mDropdownList[i] = String.valueOf(leaveModelList.get(i));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mContext,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //tvHighlight.setThreshold(1);
                mListDropdownView.setAdapter(adapter);
                //mListDropdownView.setHint(mDropdownList[pos]);
                mListDropdownView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    }
                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //show leave form popup
    public void showAbsentMarkDialog(NotificationDetailsNotify data) {
        mDialogAbsentMark = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogAbsentMark.setContentView(R.layout.dialog_notify_absent_details);
        mDialogAbsentMark.setCanceledOnTouchOutside(true);
        AppCompatTextView tvTitle = mDialogAbsentMark.findViewById(R.id.tvTitle);
        AppCompatTextView tvStartTime = mDialogAbsentMark.findViewById(R.id.tvStartTime);
        AppCompatTextView tvEndTime = mDialogAbsentMark.findViewById(R.id.tvEndTime);
        AppCompatButton MarkButton = mDialogAbsentMark.findViewById(R.id.MarkButton);
        AppCompatButton UnpaidButton = mDialogAbsentMark.findViewById(R.id.UnpaidButton);
        AppCompatButton PaidButton = mDialogAbsentMark.findViewById(R.id.PaidButton);
        tvTitle.setText(data.getNotifText());
        int tM = 0,lM=0;
        tvStartTime.setText("This month : "+tM);
        tvEndTime.setText("Last month : "+lM);
        MarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkCheck.isInternetAvailable(mContext)) {
                    LoginTable loginTable = mDb.getDbDAO().getLoginData();
                    if(loginTable!=null) {
                        RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_mark_as_persent);
                        RequestBody mRequestBodyrecord_id = RequestBody.create(MediaType.parse("text/plain"),data.getRecordId());
                        markPresentViewModel.hitMarkPresentAPI(mRequestBodyAction,mRequestBodyrecord_id);
                    }
                    else {
                        LogUtil.printToastMSG(mContext, "Something is wrong.");
                    }
                } else {
                    LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                }
            }
        });
        UnpaidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkCheck.isInternetAvailable(mContext)) {
                    LoginTable loginTable = mDb.getDbDAO().getLoginData();
                    if(loginTable!=null) {
                        RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_unpaid_leave);
                        RequestBody mRequestBodyrecord_id = RequestBody.create(MediaType.parse("text/plain"),data.getRecordId());
                        unpaidLeaveViewModel.hitUnpaidLeaveAPI(mRequestBodyAction,mRequestBodyrecord_id);
                    }
                    else {
                        LogUtil.printToastMSG(mContext, "Something is wrong.");
                    }
                } else {
                    LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                }
            }
        });
        PaidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             showPaidLeaveDialog(data);
            }
        });
        mDialogAbsentMark.show();
    }

    //show leave form popup
    public void showLateMarkDialog(NotificationDetailsNotify data) {
         mDialogLateMark = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogLateMark.setContentView(R.layout.dialog_notify_late_details);
        mDialogLateMark.setCanceledOnTouchOutside(true);
        AppCompatTextView tvTitle = mDialogLateMark.findViewById(R.id.tvTitle);
        AppCompatTextView tvStartTime = mDialogLateMark.findViewById(R.id.tvStartTime);
        AppCompatTextView tvEndTime = mDialogLateMark.findViewById(R.id.tvEndTime);
        AppCompatTextView tvStartMonth = mDialogLateMark.findViewById(R.id.tvStartMonth);
        AppCompatTextView tvEndMonth = mDialogLateMark.findViewById(R.id.tvEndMonth);
        AppCompatButton MarkButton = mDialogLateMark.findViewById(R.id.MarkButton);
        AppCompatButton ignoreButton = mDialogLateMark.findViewById(R.id.ignoreButton);
        AppCompatButton DeductButton = mDialogLateMark.findViewById(R.id.DeductButton);
        tvTitle.setText(data.getNotifText());
        String tM = "0",lM="0",tS = "00:00:00",lS="00:00:00";
        tvStartTime.setText("This month : "+tM);
        tvEndTime.setText("Last month : "+lM);
        tvStartMonth.setText("Actual time : "+tS);
        tvEndMonth.setText("Start time : "+lS);
        MarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkCheck.isInternetAvailable(mContext)) {
                    LoginTable loginTable = mDb.getDbDAO().getLoginData();
                    if(loginTable!=null) {
                        RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_mark_as_not_late);
                        RequestBody mRequestBodyrecord_id = RequestBody.create(MediaType.parse("text/plain"),data.getRecordId());
                        markNotLateViewModel.hitMarkNotLateAPI(mRequestBodyAction,mRequestBodyrecord_id);
                    }
                    else {
                        LogUtil.printToastMSG(mContext, "Something is wrong.");
                    }
                } else {
                    LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                }
            }
        });
        ignoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mDialogLateMark.dismiss();
              callDeleteNotificationApi(data.getRecordId());
            }
        });
        DeductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showDeductLeaveDialog(data);
            }
        });
        mDialogLateMark.show();
    }

    //set value to Search dropdown
    private void setDropdownType(AppCompatAutoCompleteTextView mListDropdownView) {
        List<String> leaveModelList = new ArrayList<>();
        leaveModelList.add("Casual Leave");
        leaveModelList.add("Sick Leave");
        leaveModelList.add("Other Leave");
        try {
            int pos = 0;
            if (leaveModelList != null && leaveModelList.size() > 0) {
                String[] mDropdownList = new String[leaveModelList.size()];
                for (int i = 0; i < leaveModelList.size(); i++) {
                    mDropdownList[i] = String.valueOf(leaveModelList.get(i));
                }
                mListDropdownView.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mContext,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //tvHighlight.setThreshold(1);
                mListDropdownView.setAdapter(adapter);
                mListDropdownView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Call Api delete Notification */
    private void callDeleteNotificationApi(String notifyID) {
        if (NetworkCheck.isInternetAvailable(NotificationsActivity.this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_delete_notification);
                RequestBody mRequestBodyTypeCompId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                RequestBody mRequestBodyTypeNotiId = RequestBody.create(MediaType.parse("text/plain"), notifyID);

                deleteNotificationViewModel.hitDeleteNotificationApi(mRequestBodyType,mRequestBodyTypeCompId
                        ,mRequestBodyTypeEmployee,mRequestBodyTypeNotiId);

            }
        } else {
            LogUtil.printToastMSG(NotificationsActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    private void setAdapterForNotificationList() {
        if (notificationList!=null && notificationList.size() > 0) {
            mNotificationsAdapter = new NotificationsAdapter(mContext, notificationList, new NotificationsAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(NotificationData mDataTicket, List<NotificationData> notificationResultListAdapter, boolean status) {
                    notificationList = notificationResultListAdapter;
                     tvNotifyCount.setText(String.valueOf(notificationList.size()));
                    if(notificationList.size()==0){
                        emptyBox.setBackground(getResources().getDrawable(R.drawable.layout_round_shape_blue_border));
                        textViewEmptyLayTitle.setVisibility(View.VISIBLE);
                        linearLayoutEmptyActivity.setVisibility(View.VISIBLE);
                        iv_emptyLayimage.setVisibility(View.VISIBLE);
                        textViewEmptyLayTitle.setText("No Notifications !");
                        iv_emptyLayimage.setImageDrawable(mContext.getDrawable(R.drawable.img_bg_notification));
                        rvPuranaHisab.setVisibility(View.GONE);}
                    else{ rvPuranaHisab.setVisibility(View.VISIBLE);
                        textViewEmptyLayTitle.setVisibility(View.GONE);
                        linearLayoutEmptyActivity.setVisibility(View.GONE);
                        emptyBox.setBackground(null);
                        iv_emptyLayimage.setVisibility(View.GONE);}
                    if(status){
                    callDeleteNotificationApi(mDataTicket.getId());
                    mNotificationsAdapter.updateList(notificationList);}
                    else{
                        callSingleRecordApi(mDataTicket.getRelatedId());
                        notiId = Integer.parseInt(mDataTicket.getId()==null?"0":mDataTicket.getId());
                    }
                }
            });
            rvPuranaHisab.setHasFixedSize(true);
            rvPuranaHisab.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            rvPuranaHisab.setAdapter(mNotificationsAdapter);
            rvPuranaHisab.setVisibility(View.VISIBLE);
            textViewEmptyLayTitle.setVisibility(View.GONE);
            linearLayoutEmptyActivity.setVisibility(View.GONE);
            iv_emptyLayimage.setVisibility(View.GONE);
            emptyBox.setBackground(null);
        } else {
            emptyBox.setBackground(getResources().getDrawable(R.drawable.layout_round_shape_blue_border));
            textViewEmptyLayTitle.setVisibility(View.VISIBLE);
            linearLayoutEmptyActivity.setVisibility(View.VISIBLE);
            iv_emptyLayimage.setVisibility(View.VISIBLE);
            textViewEmptyLayTitle.setText("No Notifications !");
            iv_emptyLayimage.setImageDrawable(mContext.getDrawable(R.drawable.img_bg_notification));
            rvPuranaHisab.setVisibility(View.GONE);
        }
    }

    /*Api response */
    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {

            case LOADING:
                showSmallProgressBar(mProgressBarHolder);
                //showProgressLoader(getString(R.string.scr_message_please_wait));
                break;

            case SUCCESS:
                //dismissLoader();
                dismissSmallProgressBar(mProgressBarHolder);
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    if (tag.equalsIgnoreCase(DynamicAPIPath.POST_NOTIFICATION)) {
                        NotificationResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), NotificationResponse.class);
                        if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            try{notificationList.removeAll(notificationList);}catch (Exception e){}
                            if(responseModel.getResult()!=null && responseModel.getResult().getNotifdata().size()>0) {
                                notificationList = responseModel.getResult().getNotifdata();
                            }
                            //TODO REMOVE
                            //notificationList.add(new NotificationResult("Static Test Demo Leave","Tap to perform Action...","INFO"));
                            setAdapterForNotificationList();
                        }
                    }
                    try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_DEL_NOTIFICATION)) {
                            DeleteNotificationResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), DeleteNotificationResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                //initToolbar(1,mContext,R.id.imgBack,R.id.imgReport,R.id.imgNotify,tvNotifyCount,0,R.id.imgCall);
                                setNotifyCount();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_SINGLE_NOTIFY)) {
                            NotificationDetailsResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), NotificationDetailsResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                NotificationDetailsNotify notify = new NotificationDetailsNotify();
                                if(notify.getType().equals("Late")){
                                    showLateMarkDialog(responseModel.getResult().getNotify().get(0));
                                }
                                else{
                                    showAbsentMarkDialog(responseModel.getResult().getNotify().get(0));
                                }
                            }
                            else {
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_LEAVE_STATUS)) {
                             UpdateLeaveStatusResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), UpdateLeaveStatusResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                mDialogPaidLeave.dismiss();
                                callDeleteNotificationApi(String.valueOf(notiId));
                                mNotificationsAdapter.updateList(notificationList);
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                            else {
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_MARK_PRESENT) || tag.equalsIgnoreCase(DynamicAPIPath.POST_UNPAID_LEAVE)) {
                            UpdateLeaveStatusResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), UpdateLeaveStatusResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                mDialogAbsentMark.dismiss();
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                            else {
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_DEDUCT_LEAVE)) {
                            UpdateLeaveStatusResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), UpdateLeaveStatusResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                mDialogLateMark.dismiss();
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                            else {
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case ERROR:
                //dismissLoader();
                dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(NotificationsActivity.this, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }


    //perform click actions
    @OnClick({R.id.imgBack,R.id.imgNotify})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgBack:
                finish();
                break;
            case R.id.imgNotify:
                launchScreen(mContext, NotificationsActivity.class);
                break;
        }
    }

}