package com.ominfo.hra_app.ui.notifications;

import static com.ominfo.hra_app.util.AppUtils.convertDobDate;
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
import com.ominfo.hra_app.MainActivity;
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
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveRequest;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveResponse;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveViewModel;
import com.ominfo.hra_app.ui.notifications.adapter.NotificationsAdapter;
import com.ominfo.hra_app.ui.notifications.model.AbsentMarkCountViewModel;
import com.ominfo.hra_app.ui.notifications.model.AbsentMarkResponse;
import com.ominfo.hra_app.ui.notifications.model.DeleteNotificationResponse;
import com.ominfo.hra_app.ui.notifications.model.DeleteNotificationViewModel;
import com.ominfo.hra_app.ui.notifications.model.GetSingleRecordViewModel;
import com.ominfo.hra_app.ui.notifications.model.LateMarkCountViewModel;
import com.ominfo.hra_app.ui.notifications.model.LateMarkResponse;
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
    private ApplyLeaveViewModel applyLeaveViewModel;
    private LateMarkCountViewModel lateMarkCountViewModel;
    private AbsentMarkCountViewModel absentMarkCountViewModel;
    private GetSingleRecordViewModel getSingleRecordViewModel;
    private LeaveStatusViewModel leaveStatusViewModel;
    private MarkPresentViewModel markPresentViewModel;
    private UnpaidLeaveViewModel unpaidLeaveViewModel;
    private MarkNotLateViewModel markNotLateViewModel;
    private AppDatabase mDb;
    private Dialog mDialogPaidLeave,mDialogDeductLeave;
    LinearLayoutCompat layoutLeaveTime;
    AppCompatTextView appcomptextLeaveTime;
    View viewToDate;
    RelativeLayout layToDate;
    String record_id = "0",rm_id = "0";
    int notiId = 0;
    AppCompatTextView tvStartMonth,tvEndMonth,tvStartTime,tvEndTime;
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
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                launchScreen(mContext, MainActivity.class);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        launchScreen(mContext, MainActivity.class);
    }

    private void injectAPI() {
        notificationViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(NotificationViewModel.class);
        notificationViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_NOTIFICATION));

        deleteNotificationViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(DeleteNotificationViewModel.class);
        deleteNotificationViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_DEL_NOTIFICATION));

        getSingleRecordViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(GetSingleRecordViewModel.class);
        getSingleRecordViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_SINGLE_NOTIFY));

        /*leaveStatusViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(LeaveStatusViewModel.class);
        leaveStatusViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_LEAVE_STATUS));
*/
        markPresentViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(MarkPresentViewModel.class);
        markPresentViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_MARK_PRESENT));

        unpaidLeaveViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(UnpaidLeaveViewModel.class);
        unpaidLeaveViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_UNPAID_LEAVE));

        markNotLateViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(MarkNotLateViewModel.class);
        markNotLateViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_MARK_NOT_LATE));

        deductLeaveViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(DeductLeaveViewModel.class);
        deductLeaveViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_DEDUCT_LEAVE));

        lateMarkCountViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(LateMarkCountViewModel.class);
        lateMarkCountViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_LATE_MARK_COUNT));

        absentMarkCountViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(AbsentMarkCountViewModel.class);
        absentMarkCountViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_LATE_ABSENT_COUNT));

        applyLeaveViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ApplyLeaveViewModel.class);
        applyLeaveViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_APPLY_LEAVE));
    }

    /* Call Api Notification */
    private void callNotificationApi() {
        if (NetworkCheck.isInternetAvailable(NotificationsActivity.this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            textViewEmptyLayTitle.setText("Please wait...");
            if (loginTable != null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_notification);
                RequestBody mRequestBodyTypeCompId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                RequestBody mRequestBodyDate = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                notificationViewModel.hitNotificationApi(mRequestBodyType,mRequestBodyTypeCompId
                        ,mRequestBodyDate);
            }
        } else {
            LogUtil.printToastMSG(NotificationsActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }


    /* Call Api For Single record */
    private void callSingleRecordApi(String id,String date) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_single_notify);
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"),id);
                String dateNew = AppUtils.convertyyyytodd(date);
                RequestBody mRequestBodyDate = RequestBody.create(MediaType.parse("text/plain"),  AppUtils.dateReminder(dateNew)/*"2022-05-23"*/);
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
        RelativeLayout layClose = mDialogPaidLeave.findViewById(R.id.layClose);
        layClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogPaidLeave.dismiss();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //paid leave api
                record_id = data.getRecordId();
                rm_id = data.getRmId();
                callApplyLeaveApi(AutoComTextViewLeaveType);
            }
        });
        mDialogPaidLeave.show();
    }

    //show leave form popup
    public void showDeductLeaveDialog(NotificationDetailsNotify data) {
        mDialogDeductLeave = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogDeductLeave.setContentView(R.layout.dialog_deduct_leave);
        mDialogDeductLeave.setCanceledOnTouchOutside(true);
        RelativeLayout layClose = mDialogDeductLeave.findViewById(R.id.layClose);
        AppCompatAutoCompleteTextView AutoComTextViewDuration = mDialogDeductLeave.findViewById(R.id.AutoComTextViewDuration);
        AppCompatTextView tvDateValueFrom = mDialogDeductLeave.findViewById(R.id.tvDateValueFrom);
        RelativeLayout layFromDate = mDialogDeductLeave.findViewById(R.id.layFromDate);
        AppCompatTextView tvDateValue = mDialogDeductLeave.findViewById(R.id.tvDateValue);
        RelativeLayout layToDate = mDialogDeductLeave.findViewById(R.id.layToDate);
        AppCompatButton submitButton = mDialogDeductLeave.findViewById(R.id.submitButton);
        setDropdownLeaveDuration(AutoComTextViewDuration);
        tvDateValueFrom.setText(AppUtils.getCurrentDateTime());
        layClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogDeductLeave.dismiss();
            }
        });
        layFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataPicker(tvDateValueFrom);
            }
        });
        layToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openDataPicker(tvDateValue);
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //paid leave api
                if (NetworkCheck.isInternetAvailable(mContext)) {
                    LoginTable loginTable = mDb.getDbDAO().getLoginData();
                    if (loginTable != null) {
                        record_id = data.getRecordId();
                        rm_id = data.getRmId();
                        RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_leave_deduct);
                        RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"),data.getRmId());
                        RequestBody mRequestBodyDate = RequestBody.create(MediaType.parse("text/plain"), AppUtils.dateReminder(tvDateValueFrom.getText().toString()));
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
        mDialogDeductLeave.show();
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
        //leaveModelList.add("Multiple Days");
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
    /* Call Api For Apply Leave */
    private void callApplyLeaveApi(AppCompatAutoCompleteTextView AutoComTextViewLeaveType) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_apply_leave);
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                RequestBody mRequestBodyDuration = RequestBody.create(MediaType.parse("text/plain"), "Full Day");
                String startTimeStamp = "00:00:00",endTimeStamp = "23:59:00";
                RequestBody mRequestBodyStartTime = RequestBody.create(MediaType.parse("text/plain"), AppUtils.getCurrentDateInyyyymmdd()+" "+startTimeStamp);
                RequestBody mRequestBodyEndTime = RequestBody.create(MediaType.parse("text/plain"), AppUtils.getCurrentDateInyyyymmdd()+" "+endTimeStamp);
                RequestBody mRequestBodyLeaveType = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewLeaveType.getText().toString());
                RequestBody mRequestBodyComment = RequestBody.create(MediaType.parse("text/plain"), "This is marked as paid leave from notifications");
                // RequestBody mRequestBodyLeaveStatus = RequestBody.create(MediaType.parse("text/plain") , "approved");
                ///RequestBody mRequestBodyUpdatedBy = RequestBody.create(MediaType.parse("text/plain"),"12"  /*loginTable.getManagerId()*/);

                ApplyLeaveRequest applyLeaveRequest = new ApplyLeaveRequest();
                applyLeaveRequest.setAction(mRequestBodyAction);
                applyLeaveRequest.setEmpId(mRequestBodyTypeEmpId);
                applyLeaveRequest.setDuration(mRequestBodyDuration);
                applyLeaveRequest.setStartTime(mRequestBodyStartTime);
                applyLeaveRequest.setEndTime(mRequestBodyEndTime);
                applyLeaveRequest.setLeaveType(mRequestBodyLeaveType);
                applyLeaveRequest.setComment(mRequestBodyComment);
                // applyLeaveRequest.setLeaveStatus(mRequestBodyLeaveStatus);
                //applyLeaveRequest.setUpdatedBy(mRequestBodyUpdatedBy);
                applyLeaveViewModel.hitApplyLeaveApi(applyLeaveRequest);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    //show leave form popup
    public void showAbsentMarkDialog(NotificationDetailsNotify data) {
        try{
        mDialogAbsentMark = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogAbsentMark.setContentView(R.layout.dialog_notify_absent_details);
        mDialogAbsentMark.setCanceledOnTouchOutside(true);
        RelativeLayout layClose = mDialogAbsentMark.findViewById(R.id.layClose);
        AppCompatTextView tvTitle = mDialogAbsentMark.findViewById(R.id.tvTitle);
        tvStartTime = mDialogAbsentMark.findViewById(R.id.tvStartTime);
        tvEndTime = mDialogAbsentMark.findViewById(R.id.tvEndTime);
        AppCompatButton MarkButton = mDialogAbsentMark.findViewById(R.id.MarkButton);
        AppCompatButton UnpaidButton = mDialogAbsentMark.findViewById(R.id.UnpaidButton);
        AppCompatButton PaidButton = mDialogAbsentMark.findViewById(R.id.PaidButton);
        tvTitle.setText(""+data.getNotifText());
        callAbsentMarkCountApi(data.getRelatedId());
        layClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogAbsentMark.dismiss();
            }
        });
        MarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkCheck.isInternetAvailable(mContext)) {
                    LoginTable loginTable = mDb.getDbDAO().getLoginData();
                    if(loginTable!=null) {
                        record_id = data.getRecordId();
                        rm_id = data.getRmId();
                        RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_mark_as_persent);
                        RequestBody mRequestBodyrecord_id = RequestBody.create(MediaType.parse("text/plain"),data.getRelatedId());
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
                        record_id = data.getRecordId();
                        rm_id = data.getRmId();
                        RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_unpaid_leave);
                        RequestBody mRequestBodyrecord_id = RequestBody.create(MediaType.parse("text/plain"),data.getRelatedId());
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
                mDialogAbsentMark.dismiss();
                record_id = data.getRecordId();
                rm_id = data.getRmId();
               showPaidLeaveDialog(data);
            }
        });
        mDialogAbsentMark.show();
        }catch (Exception e){}
    }

    //show leave form popup
    public void showLateMarkDialog(NotificationDetailsNotify data) {
        try {
            mDialogLateMark = new Dialog(mContext, R.style.ThemeDialogCustom);
            mDialogLateMark.setContentView(R.layout.dialog_notify_late_details);
            mDialogLateMark.setCanceledOnTouchOutside(true);
            RelativeLayout layClose = mDialogLateMark.findViewById(R.id.layClose);
            AppCompatTextView tvTitle = mDialogLateMark.findViewById(R.id.tvTitle);
            AppCompatTextView tvStartTime = mDialogLateMark.findViewById(R.id.tvStartTime);
            AppCompatTextView tvEndTime = mDialogLateMark.findViewById(R.id.tvEndTime);
            tvStartMonth = mDialogLateMark.findViewById(R.id.tvStartMonth);
            tvEndMonth = mDialogLateMark.findViewById(R.id.tvEndMonth);
            AppCompatButton MarkButton = mDialogLateMark.findViewById(R.id.MarkButton);
            AppCompatButton ignoreButton = mDialogLateMark.findViewById(R.id.ignoreButton);
            AppCompatButton DeductButton = mDialogLateMark.findViewById(R.id.DeductButton);
            tvTitle.setText(data.getNotifText());
            tvStartTime.setText("Actual time : " + AppUtils.convert24to12Attendance(data.getStnd_start_tym()));
            tvEndTime.setText("Start time : " + AppUtils.convert24to12Attendance(data.getStartTime()));
            callLateMarkCountApi(data.getRelatedId());
            layClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialogLateMark.dismiss();
                }
            });
            MarkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (NetworkCheck.isInternetAvailable(mContext)) {
                        LoginTable loginTable = mDb.getDbDAO().getLoginData();
                        if (loginTable != null) {
                            record_id = data.getRecordId();
                            rm_id = data.getRmId();
                            RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_mark_as_not_late);
                            RequestBody mRequestBodyrecord_id = RequestBody.create(MediaType.parse("text/plain"), data.getRelatedId());
                            markNotLateViewModel.hitMarkNotLateAPI(mRequestBodyAction, mRequestBodyrecord_id);
                        } else {
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
                    callDeleteNotificationApi(data.getRecordId(),data.getRmId());
                }
            });
            DeductButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialogLateMark.dismiss();
                    showDeductLeaveDialog(data);
                }
            });
            mDialogLateMark.show();
        }catch (Exception e){}
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
    private void callDeleteNotificationApi(String notifyID,String rmId) {
        if (NetworkCheck.isInternetAvailable(NotificationsActivity.this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_delete_notification);
                RequestBody mRequestBodyTypeCompId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"), rmId);
                RequestBody mRequestBodyTypeNotiId = RequestBody.create(MediaType.parse("text/plain"), notifyID);

                deleteNotificationViewModel.hitDeleteNotificationApi(mRequestBodyType,mRequestBodyTypeCompId
                        ,mRequestBodyTypeEmployee,mRequestBodyTypeNotiId);

            }
        } else {
            LogUtil.printToastMSG(NotificationsActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api late mark count */
    private void callLateMarkCountApi(String record_id) {
        if (NetworkCheck.isInternetAvailable(NotificationsActivity.this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_late_mark_count_months);
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"),record_id);

                lateMarkCountViewModel.hitLateMarkCountApi(mRequestBodyType
                        ,mRequestBodyTypeEmployee);

            }
        } else {
            LogUtil.printToastMSG(NotificationsActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api absent mark count */
    private void callAbsentMarkCountApi(String id) {
        if (NetworkCheck.isInternetAvailable(NotificationsActivity.this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_absent_mark_count_months);
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"), id);

                absentMarkCountViewModel.hitAbsentMarkCountApi(mRequestBodyType
                        ,mRequestBodyTypeEmployee);

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
                    callDeleteNotificationApi(mDataTicket.getId(),mDataTicket.getRmid());
                    mNotificationsAdapter.updateList(notificationList);}
                    else{
                        LoginTable loginTable = mDb.getDbDAO().getLoginData();
                        if(loginTable!=null) {
                            if(!loginTable.getIsadmin().equals("0")){
                            callSingleRecordApi(mDataTicket.getRmid(),mDataTicket.getCreated_on());
                            notiId = Integer.parseInt(mDataTicket.getId() == null ? "0" : mDataTicket.getId());
                        }}
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
                                NotificationDetailsNotify notify = responseModel.getResult().getNotify().get(0);
                                if(notify.getType().equals("Late")){
                                    showLateMarkDialog(responseModel.getResult().getNotify().get(0));
                                }
                                if(notify.getType().equals("Absent")){
                                    showAbsentMarkDialog(responseModel.getResult().getNotify().get(0));
                                }
                            }
                            else {
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        LogUtil.printLog("test_val",e.getMessage());
                    }
                    /*try{
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
                    }*/
                    try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_MARK_PRESENT) || tag.equalsIgnoreCase(DynamicAPIPath.POST_UNPAID_LEAVE)) {
                            UpdateLeaveStatusResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), UpdateLeaveStatusResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                mDialogAbsentMark.dismiss();
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                                callDeleteNotificationApi(record_id,rm_id);
                            }
                            else {
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    } try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_MARK_NOT_LATE)) {
                            UpdateLeaveStatusResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), UpdateLeaveStatusResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                mDialogLateMark.dismiss();
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                                callDeleteNotificationApi(record_id,rm_id);
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
                                mDialogDeductLeave.dismiss();
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                                callDeleteNotificationApi(record_id,rm_id);
                            }
                            else {
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_LATE_MARK_COUNT)) {
                            LateMarkResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), LateMarkResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                tvStartMonth.setText("This month : "+responseModel.getResult().getThisMonthCount());
                                tvEndMonth.setText("Last month : "+responseModel.getResult().getPrevMonthCount());
                            }
                            else {
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_LATE_ABSENT_COUNT)) {
                            AbsentMarkResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), AbsentMarkResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                tvStartTime.setText("This month : "+responseModel.getResult().getThisMonthCount());
                                tvEndTime.setText("Last month : "+responseModel.getResult().getPrevMonthCount());
                            }
                            else {
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_APPLY_LEAVE)) {
                            ApplyLeaveResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ApplyLeaveResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                mDialogPaidLeave.dismiss();
                                showSuccessDialog(responseModel.getResult().getMessage(),
                                        false,NotificationsActivity.this);
                                callDeleteNotificationApi(record_id,rm_id);
                            }
                            else {
                                mDialogPaidLeave.dismiss();
                                showSuccessDialog(responseModel.getResult().getMessage(),
                                        false,NotificationsActivity.this);
                            }
                        }
                    }catch (Exception e){
                        showSuccessDialog("Leave application upload failed.",
                                true,NotificationsActivity.this);
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