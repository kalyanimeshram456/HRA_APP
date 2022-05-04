package com.ominfo.crm_solution.ui.notifications;

import static com.ominfo.crm_solution.util.AppUtils.getChangeDateForHisab;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
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

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ominfo.crm_solution.MainActivity;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.basecontrol.BaseApplication;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.interfaces.Constants;
import com.ominfo.crm_solution.interfaces.SharedPrefKey;
import com.ominfo.crm_solution.network.ApiResponse;
import com.ominfo.crm_solution.network.DynamicAPIPath;
import com.ominfo.crm_solution.network.NetworkCheck;
import com.ominfo.crm_solution.network.ViewModelFactory;
import com.ominfo.crm_solution.ui.attendance.model.UpdateAttendanceResponse;
import com.ominfo.crm_solution.ui.login.LoginActivity;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.my_account.model.ApplicationLeave;
import com.ominfo.crm_solution.ui.my_account.model.LeaveApplicationRequest;
import com.ominfo.crm_solution.ui.notifications.adapter.NotificationsAdapter;
import com.ominfo.crm_solution.ui.notifications.model.DeleteNotificationResponse;
import com.ominfo.crm_solution.ui.notifications.model.DeleteNotificationViewModel;
import com.ominfo.crm_solution.ui.notifications.model.LeaveSingleRecordResponse;
import com.ominfo.crm_solution.ui.notifications.model.LeaveSingleRecordViewModel;
import com.ominfo.crm_solution.ui.notifications.model.LeaveStatusViewModel;
import com.ominfo.crm_solution.ui.notifications.model.NotificationResponse;
import com.ominfo.crm_solution.ui.notifications.model.NotificationResult;
import com.ominfo.crm_solution.ui.notifications.model.NotificationViewModel;
import com.ominfo.crm_solution.ui.notifications.model.SingleLeave;
import com.ominfo.crm_solution.ui.notifications.model.UpdateLeaveStatusResponse;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;
import com.ominfo.crm_solution.util.SharedPref;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.tvSearchView)
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
    List<NotificationResult> notificationList = new ArrayList<>();
    @Inject
    ViewModelFactory mViewModelFactory;
    private NotificationViewModel notificationViewModel;
    private DeleteNotificationViewModel deleteNotificationViewModel;
    private LeaveSingleRecordViewModel leaveSingleRecordViewModel;
    private LeaveStatusViewModel leaveStatusViewModel;
    private AppDatabase mDb;
    private Dialog mDialogChangePass;
    LinearLayoutCompat layoutLeaveTime;
    AppCompatTextView appcomptextLeaveTime;
    View viewToDate;
    RelativeLayout layToDate;
    List<NotificationResult> notificationResultList = new ArrayList<>();
    int notiId = 0;

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
            imgBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishAffinity();
                    launchScreen(mContext,MainActivity.class);
                }
            });
        }else {
            finishAffinity();
            launchScreen(mContext,LoginActivity.class);
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
        Window window = getWindow();
        View view = window.getDecorView();
        BaseActivity.DarkStatusBar.setLightStatusBar(view,this);

        toolbarTitle.setText("Notifications");
        initToolbar(1,mContext,R.id.imgBack,R.id.imgReport,R.id.imgNotify,tvNotifyCount,0,R.id.imgCall);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Boolean iSLoggedIn = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.IS_LOGGED_IN, false);
        if (iSLoggedIn){
            //launchScreen(MainActivity.class);
            finishAffinity();
            launchScreen(mContext,MainActivity.class);
        }
    }

    private void injectAPI() {
        notificationViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(NotificationViewModel.class);
        notificationViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_NOTIFICATION));

        deleteNotificationViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(DeleteNotificationViewModel.class);
        deleteNotificationViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_DEL_NOTIFICATION));

        leaveSingleRecordViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(LeaveSingleRecordViewModel.class);
        leaveSingleRecordViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_LEAVE_SINGLE));

        leaveStatusViewModel = ViewModelProviders.of(NotificationsActivity.this, mViewModelFactory).get(LeaveStatusViewModel.class);
        leaveStatusViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_LEAVE_STATUS));
    }

    /* Call Api Notification */
    private void callNotificationApi() {
        if (NetworkCheck.isInternetAvailable(NotificationsActivity.this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            textViewEmptyLayTitle.setText("Please wait...");
            if (loginTable != null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_notification);
                RequestBody mRequestBodyTypeCompId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                notificationViewModel.hitNotificationApi(mRequestBodyType,mRequestBodyTypeCompId
                        ,mRequestBodyTypeEmployee);
            }
        } else {
            LogUtil.printToastMSG(NotificationsActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Single record */
    private void callSingleRecordApi(String id) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_leave_single);
                RequestBody mRequestBodyTypeId = RequestBody.create(MediaType.parse("text/plain"),id);
                leaveSingleRecordViewModel.hitLeaveSingleRecordApi(mRequestBodyAction,mRequestBodyTypeId);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }
    /* Call Api For Sales */
    private void callUpadteLeaveStatusApi(String notID, String status) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_update_leave_status);
                RequestBody mRequestBodyTypeId = RequestBody.create(MediaType.parse("text/plain"),notID);
                RequestBody mRequestBodyStatus = RequestBody.create(MediaType.parse("text/plain"), status);
                RequestBody mRequestBodyby = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                leaveStatusViewModel.hitLeaveStatusRecordApi(mRequestBodyAction,mRequestBodyTypeId
                ,mRequestBodyStatus,mRequestBodyby);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    //show leave form popup
    public void showLeaveFormDialog(SingleLeave data) {
        mDialogChangePass = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogChangePass.setContentView(R.layout.dialog_status_leave_form);
        mDialogChangePass.setCanceledOnTouchOutside(true);
        AppCompatAutoCompleteTextView AutoComTextViewDuration = mDialogChangePass.findViewById(R.id.AutoComTextViewDuration);
        AppCompatImageView mClose = mDialogChangePass.findViewById(R.id.imgCancel);
        AppCompatButton addReceiptButton = mDialogChangePass.findViewById(R.id.addReceiptButton);
        AppCompatButton addRejectButton = mDialogChangePass.findViewById(R.id.addRejectButton);
        viewToDate = mDialogChangePass.findViewById(R.id.view);
        layToDate = mDialogChangePass.findViewById(R.id.layToDate);
        layoutLeaveTime = mDialogChangePass.findViewById(R.id.layoutLeaveTime);
        appcomptextLeaveTime = mDialogChangePass.findViewById(R.id.appcomptextLeaveTime);
        AppCompatTextView tvDateValueFrom = mDialogChangePass.findViewById(R.id.tvDateValueFrom);
        AppCompatImageView imgFromDate = mDialogChangePass.findViewById(R.id.imgFromDate);
        AppCompatTextView tvDateValue = mDialogChangePass.findViewById(R.id.tvDateValue);
        AppCompatTextView appcomptextNoOfDays = mDialogChangePass.findViewById(R.id.appcomptextNoOfDays);
        AppCompatImageView imgToDate = mDialogChangePass.findViewById(R.id.imgToDate);
        AppCompatTextView tvTimeValueFrom = mDialogChangePass.findViewById(R.id.tvTimeValueFrom);
        AppCompatImageView imgToTime = mDialogChangePass.findViewById(R.id.imgToTime);
        AppCompatTextView tvTimeValue = mDialogChangePass.findViewById(R.id.tvTimeValue);
        AppCompatTextView tvTitleName = mDialogChangePass.findViewById(R.id.tvTitleName);
        AppCompatImageView imgTime = mDialogChangePass.findViewById(R.id.imgTime);
        AppCompatAutoCompleteTextView AutoComTextViewLeaveType = mDialogChangePass.findViewById(R.id.AutoComTextViewLeaveType);
        AppCompatAutoCompleteTextView AutoComTextViewPOI = mDialogChangePass.findViewById(R.id.AutoComTextViewComment);

        String start = "NA" ,end = "NA";
        try{
            start = AppUtils.convertyyyytodd(data.getStartTime());
            end = AppUtils.convertyyyytodd(data.getEndTime());
        }catch (Exception e){
        }
        tvDateValueFrom.setText(start);
        tvDateValue.setText(end);
        String diff = "0";
        if(data.getDuration().equals("single day"))
        {
            diff = "1";
            viewToDate.setVisibility(View.GONE);
            layToDate.setVisibility(View.GONE);
            layoutLeaveTime.setVisibility(View.GONE);
            appcomptextLeaveTime.setVisibility(View.GONE);
        }else if(data.getDuration().equals("Half Day")){
            diff = "Half";
            viewToDate.setVisibility(View.GONE);
            layToDate.setVisibility(View.GONE);
            layoutLeaveTime.setVisibility(View.VISIBLE);
            appcomptextLeaveTime.setVisibility(View.VISIBLE);
            String startT = "NA" ,endT = "NA";
            try{
                String[] mST = data.getStartTime().split(" ");
                startT = AppUtils.convert24to12Attendance(mST[1]);
                String[] mET = data.getEndTime().split(" ");
                endT = AppUtils.convert24to12Attendance(mET[1]);
            }catch (Exception e){
            }
            tvTimeValueFrom.setText(startT);
            tvTimeValue.setText(endT);
        }
        else {
            viewToDate.setVisibility(View.VISIBLE);
            layToDate.setVisibility(View.VISIBLE);
            layoutLeaveTime.setVisibility(View.GONE);
            appcomptextLeaveTime.setVisibility(View.GONE);
            diff = String.valueOf(getChangeDateForHisab(tvDateValueFrom.getText().toString(), tvDateValue.getText().toString()));
        }
        if(diff.equals("Half")){
            appcomptextNoOfDays.setText("Number of days : " + diff + " Day");
        }
        else {
            appcomptextNoOfDays.setText("Number of days : " + diff + " Days");
        }

        //disable boxes
        AutoComTextViewDuration.setEnabled(false);AutoComTextViewDuration.setText(data.getDuration());
        tvDateValueFrom.setEnabled(false);
        imgFromDate.setVisibility(View.GONE);
        tvDateValue.setEnabled(false);
        imgToDate.setVisibility(View.GONE);
        tvTimeValueFrom.setEnabled(false);
        imgToTime.setVisibility(View.GONE);
        tvTimeValue.setEnabled(false);
        imgTime.setVisibility(View.GONE);
        AutoComTextViewLeaveType.setEnabled(false);AutoComTextViewLeaveType.setText(data.getLeaveType());
        AutoComTextViewPOI.setEnabled(false);AutoComTextViewPOI.setText(data.getComment());
        addReceiptButton.setText(R.string.scr_lbl_accept);tvTitleName.setText(R.string.scr_lbl_leave_details);
        addRejectButton.setVisibility(View.VISIBLE);

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogChangePass.dismiss();
            }
        });
        addRejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callUpadteLeaveStatusApi(data.getId(),"REJECTED");
            }
        });
        addReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callUpadteLeaveStatusApi(data.getId(),"APPROVED");
            }
        });
        mDialogChangePass.show();
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
                public void onItemClick(NotificationResult mDataTicket,List<NotificationResult> notificationResultListAdapter,boolean status) {
                     notificationResultList = notificationResultListAdapter;
                    tvNotifyCount.setText(String.valueOf(notificationResultList.size()));
                    if(notificationResultList.size()==0){
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
                    mNotificationsAdapter.updateList(notificationResultList);}
                    else{
                        callSingleRecordApi(mDataTicket.getRelativeId());
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
                        if (responseModel != null) {
                            mSwipeRefreshLayout.setRefreshing(false);
                            notificationList.clear();
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_LEAVE_SINGLE)) {
                            LeaveSingleRecordResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), LeaveSingleRecordResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                showLeaveFormDialog(responseModel.getResult().getLeave().get(0));
                            }
                            else {
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try{
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_LEAVE_STATUS)) {
                             UpdateLeaveStatusResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), UpdateLeaveStatusResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                mDialogChangePass.dismiss();
                                callDeleteNotificationApi(String.valueOf(notiId));
                                mNotificationsAdapter.updateList(notificationResultList);
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