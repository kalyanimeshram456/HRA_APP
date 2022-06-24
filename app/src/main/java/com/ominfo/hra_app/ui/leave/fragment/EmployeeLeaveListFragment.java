package com.ominfo.hra_app.ui.leave.fragment;

import static com.ominfo.hra_app.MainActivity.bottomNavigationView;
import static com.ominfo.hra_app.ui.employees.PaginationListener.PAGE_START;
import static com.ominfo.hra_app.util.AppUtils.getChangeDateForHisab;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.basecontrol.BaseFragment;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.interfaces.Constants;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.hra_app.ui.employees.PaginationListener;
import com.ominfo.hra_app.ui.leave.adapter.EmployeeLeaveListAdapter;
import com.ominfo.hra_app.ui.leave.model.AcceptRejectLeave;
import com.ominfo.hra_app.ui.leave.model.AcceptRejectLeaveListViewModel;
import com.ominfo.hra_app.ui.leave.model.AcceptRejectListRequest;
import com.ominfo.hra_app.ui.leave.model.ActiveEmployeeListEmpDatum;
import com.ominfo.hra_app.ui.leave.model.ActiveEmployeeListViewModel;
import com.ominfo.hra_app.ui.leave.model.EmployeeLeaveMonthsList;
import com.ominfo.hra_app.ui.leave.model.LeaveAcceptRejectResponse;
import com.ominfo.hra_app.ui.leave.model.LeavesPendingLeavesViewModel;
import com.ominfo.hra_app.ui.leave.model.PastLeaveListViewModel;
import com.ominfo.hra_app.ui.leave.model.PendingLeave;
import com.ominfo.hra_app.ui.leave.model.PendingLeaveResponse;
import com.ominfo.hra_app.ui.leave.model.PendingLeavesViewModel;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveRequest;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveResponse;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveViewModel;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.sales_credit.model.EnquiryPagermodel;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeLeaveListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeLeaveListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    Context mContext;
    EmployeeLeaveListAdapter leaveAdapter;
    @BindView(R.id.rvSalesList)
    RecyclerView rvSalesList;
    @BindView(R.id.imgBack)
    LinearLayoutCompat imgBack;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvToolbarTitle;
    @BindView(R.id.tv_emptyLayTitle)
    AppCompatTextView tv_emptyLayTitle;
    private AppDatabase mDb;
    List<AcceptRejectLeave> acceptRejectLeaveArrayList = new ArrayList<>();
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat emptyLayout;
    @BindView(R.id.iv_emptyLayimage)
    AppCompatImageView iv_emptyLayimage;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    final Calendar myCalendar = Calendar.getInstance();
    @Inject
    ViewModelFactory mViewModelFactory;
    private PastLeaveListViewModel pastLeaveListViewModel;
    private ActiveEmployeeListViewModel activeEmployeeListViewModel;
    private PendingLeavesViewModel pendingLeavesViewModel;
    private LeavesPendingLeavesViewModel LeavesDetailsViewModel;
    List<EnquiryPagermodel> enquiryPageList = new ArrayList<>();
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private Dialog mDialogChangePass;
    AppCompatTextView tvDateValueFrom;
    LinearLayoutCompat layoutLeaveTime;
    AppCompatTextView appcomptextLeaveTime;
    View viewToDate;
    RelativeLayout layToDate;
    int cam = 0,noOFDays = 0;
    private String pagerClicked = "No", startDateAccept = "", endDateAccept = "";
    AppCompatTextView appcomptextNoOfDays;
    AppCompatTextView tvDateValueTo,tvTimeValueFrom,tvTimeValueTo ;
    AppCompatAutoCompleteTextView AutoComTextViewComment;
    AppCompatAutoCompleteTextView tvAutoLeaveStatus;
    AppCompatAutoCompleteTextView AutoComTextViewDuration;
    AppCompatAutoCompleteTextView AutoComTextViewLeaveType;
    private ApplyLeaveViewModel applyLeaveViewModel;
    @BindView(R.id.AutoComMonth)
    AppCompatAutoCompleteTextView AutoComMonth;
    List<ActiveEmployeeListEmpDatum> activeEmployeeList = new ArrayList<>();
    String selectedActiveEmpid = "0";
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private long totalPage = 0;
    private boolean isLoading = false;
    int itemCount = 0;
    private AcceptRejectLeaveListViewModel rejectLeaveListViewModel;

    public EmployeeLeaveListFragment() {
        // Required empty public constructor
    }

    public static EmployeeLeaveListFragment newInstance(String param1, String param2) {
        EmployeeLeaveListFragment fragment = new EmployeeLeaveListFragment();
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
        View view = inflater.inflate(R.layout.activity_employee_leave_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((BaseActivity)mContext).getDeps().inject(this);
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        injectAPI();
        init();
    }

    private void init(){
        setToolbar();
        Glide.with(this)
                .load(R.drawable.img_bg_search)
                .into(iv_emptyLayimage);
        tv_emptyLayTitle.setText(R.string.scr_lbl_no_data_available);
        tv_emptyLayTitle.setText("Search something...");
        swipeRefresh.setOnRefreshListener(this);
        //AutoComMonth.setText(AppUtils.getCurrentMonth());
        //setDropdownMonth();
        rvSalesList.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvSalesList.setLayoutManager(layoutManager);

        leaveAdapter = new EmployeeLeaveListAdapter(mContext,new ArrayList<>(), new EmployeeLeaveListAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(String mData, AcceptRejectLeave searchresult) {

            }
        });
        rvSalesList.setAdapter(leaveAdapter);
        //callAcceptRejectListApi("0");

        /**
         * add scroll listener while user reach in bottom load more will call
         */
        rvSalesList.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                callAcceptRejectListApi(String.valueOf(currentPage));
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void injectAPI() {
        rejectLeaveListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AcceptRejectLeaveListViewModel.class);
        rejectLeaveListViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_ACCEPT_REJECT_LIST));

        pendingLeavesViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PendingLeavesViewModel.class);
        pendingLeavesViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_PENDING_LEAVES));

        LeavesDetailsViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LeavesPendingLeavesViewModel.class);
        LeavesDetailsViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, "Latest_Leave"));

        applyLeaveViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ApplyLeaveViewModel.class);
        applyLeaveViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_APPLY_LEAVE));
    }

    @Override
    public void onResume() {
        super.onResume();
        try{
            itemCount = 0;
            currentPage = PAGE_START;
            isLastPage = false;
            leaveAdapter.clear();
            callAcceptRejectListApi("0");}catch (Exception e){}
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void setToolbar() {
        //set toolbar title
        tvToolbarTitle.setText(R.string.scr_lbl_past_leaves);
        ((BaseActivity)mContext).initToolbar(5, mContext, R.id.imgBack, R.id.imgReport, R.id.imgNotify,tvNotifyCount, R.id.imgBack, R.id.imgCall);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new DashboardFragment();
                ((BaseActivity)mContext).moveFragment(mContext,fragment);
                bottomNavigationView.setSelectedItemId(R.id.home);
            }
        });
        imgNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity)mContext).launchScreen(mContext, NotificationsActivity.class);;
            }
        });
    }

    //perform click actions
    @OnClick({R.id.layPast,R.id.imgAddLeave,R.id.layLeaveInfo})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgAddLeave:
               callPendingLeaveListApi();
                break;
            case R.id.layLeaveInfo:
                callNewPendingLeaveListApi();
                break;
            case R.id.layPast:
                PastLeaveFragment pastLeaveFragment = new PastLeaveFragment();
                moveFromFragment(pastLeaveFragment,mContext);
                break;
        }
    }
    public void showLeaveDetailsDialog(PendingLeave pendingLeave) {
        Dialog mDialogLeaveDetails = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogLeaveDetails.setContentView(R.layout.dialog_leave_count_details);
        mDialogLeaveDetails.setCanceledOnTouchOutside(true);
        RelativeLayout mClose = mDialogLeaveDetails.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialogLeaveDetails.findViewById(R.id.okayButton);
        AppCompatTextView tvCasual = mDialogLeaveDetails.findViewById(R.id.tvCasual);
        AppCompatTextView tvSick = mDialogLeaveDetails.findViewById(R.id.tvSick);
        AppCompatTextView tvOther = mDialogLeaveDetails.findViewById(R.id.tvOther);
        tvCasual.setText(""+pendingLeave.getLeftCl());
        tvSick.setText(""+pendingLeave.getLeftSl());
        tvOther.setText(""+pendingLeave.getLeftOl());
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogLeaveDetails.dismiss();
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogLeaveDetails.dismiss();
            }
        });
        mDialogLeaveDetails.show();
    }

    //show leave form popup
    public void showLeaveFormDialog(PendingLeave pendingLeave) {
        mDialogChangePass = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogChangePass.setContentView(R.layout.dialog_add_leave_form);
        mDialogChangePass.setCanceledOnTouchOutside(true);
        AutoComTextViewDuration = mDialogChangePass.findViewById(R.id.AutoComTextViewDuration);
        AutoComTextViewLeaveType = mDialogChangePass.findViewById(R.id.AutoComTextViewLeaveType);
        AppCompatImageView mClose = mDialogChangePass.findViewById(R.id.imgCancel);
        AppCompatButton addReceiptButton = mDialogChangePass.findViewById(R.id.addReceiptButton);
        viewToDate = mDialogChangePass.findViewById(R.id.view);
        layToDate = mDialogChangePass.findViewById(R.id.layToDate);
        layoutLeaveTime = mDialogChangePass.findViewById(R.id.layoutLeaveTime);
        appcomptextLeaveTime = mDialogChangePass.findViewById(R.id.appcomptextLeaveTime);
        appcomptextNoOfDays = mDialogChangePass.findViewById(R.id.appcomptextNoOfDays);
        AppCompatImageView imgFromDate  = mDialogChangePass.findViewById(R.id.imgFromDate);
        AppCompatImageView imgToDate  = mDialogChangePass.findViewById(R.id.imgToDate);
        ImageView imgFromTime  = mDialogChangePass.findViewById(R.id.imgToTime);
        ImageView imgToTime  = mDialogChangePass.findViewById(R.id.imgTime);
        tvDateValueFrom = mDialogChangePass.findViewById(R.id.tvDateValueFrom);
        tvDateValueTo = mDialogChangePass.findViewById(R.id.tvDateValue);
        tvTimeValueFrom = mDialogChangePass.findViewById(R.id.tvTimeValueFrom);
        tvTimeValueTo = mDialogChangePass.findViewById(R.id.tvTimeValue);
        AutoComTextViewComment = mDialogChangePass.findViewById(R.id.AutoComTextViewComment);
        TextInputLayout input_textDuration = mDialogChangePass.findViewById(R.id.input_textDuration);
        TextInputLayout input_textType = mDialogChangePass.findViewById(R.id.input_textType);
        layoutLeaveTime.setVisibility(View.GONE);
        appcomptextLeaveTime.setVisibility(View.GONE);
        String mDate = AppUtils.getCurrentDateTime_();//SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.KATA_CHITTI_DATE, AppUtils.getCurrentDateTime_());
        tvDateValueFrom.setText(mDate);
        tvDateValueTo.setText(mDate);
        String mTime = AppUtils.getCurrentTime();
        tvTimeValueFrom.setText(mTime);
        tvTimeValueTo.setText(mTime);
        int diff = getChangeDateForHisab(tvDateValueFrom.getText().toString(),tvDateValueTo.getText().toString());
        appcomptextNoOfDays.setText("Number of days : "+diff +" Days");
        noOFDays = diff;
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogChangePass.dismiss();
            }
        });
        setDropdownLeaveDuration(AutoComTextViewDuration);
        setDropdownType(AutoComTextViewLeaveType,pendingLeave);
        tvDateValueFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataPicker(1);
            }
        });
        imgFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataPicker(1);
            }
        });
        tvDateValueTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataPicker(0);
            }
        });imgToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataPicker(0);
            }
        });
        tvTimeValueFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenTimePicker(1);
            }
        });imgFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenTimePicker(1);
            }
        });
        tvTimeValueTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenTimePicker(0);
            }
        });imgToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenTimePicker(0);
            }
        });

        addReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAttendanceDetailsValid(AutoComTextViewDuration,input_textDuration,AutoComTextViewLeaveType,input_textType
                )){
                    mDialogChangePass.dismiss();
                    callApplyLeaveApi();
                }

            }
        });
        mDialogChangePass.show();
    }
    /* Call Api For Apply Leave */
    private void callApplyLeaveApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_apply_leave);
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                RequestBody mRequestBodyDuration = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewDuration.getText().toString());
                String startTimeStamp = "",endTimeStamp = "",
                        startDateTimeStamp = AppUtils.changeToSlashToDash(tvDateValueFrom.getText().toString()),
                        endDateTimeStamp = AppUtils.changeToSlashToDash(tvDateValueTo.getText().toString());

                if(AutoComTextViewDuration.getText().toString().equals("Half Day"))
                {
                    startTimeStamp = AppUtils.convert12to24ForAttention(tvTimeValueFrom.getText().toString());
                    endTimeStamp = AppUtils.convert12to24ForAttention(tvTimeValueTo.getText().toString());
                    endDateTimeStamp = startDateTimeStamp;
                } else  if(AutoComTextViewDuration.getText().toString().equals("Full Day"))
                {    endDateTimeStamp = startDateTimeStamp;
                    startTimeStamp = "00:00:00";endTimeStamp = "23:59:00";
                }else {
                    startTimeStamp = "00:00:00";endTimeStamp = "23:59:00";
                }

                RequestBody mRequestBodyStartTime = RequestBody.create(MediaType.parse("text/plain"), startDateTimeStamp+" "+startTimeStamp);
                RequestBody mRequestBodyEndTime = RequestBody.create(MediaType.parse("text/plain"), endDateTimeStamp+" "+endTimeStamp);
                String[] test = new String[1];
                if(!AutoComTextViewLeaveType.getText().toString().equals("Unpaid Leave")){
                    test = AutoComTextViewLeaveType.getText().toString().trim().split(" \\(");
                }else{
                    test[0] = AutoComTextViewLeaveType.getText().toString();
                }
                RequestBody mRequestBodyLeaveType = RequestBody.create(MediaType.parse("text/plain"), test[0].trim());
                RequestBody mRequestBodyComment = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewComment.getText().toString());
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

    /*check validations on field*/
    private boolean isAttendanceDetailsValid(AppCompatAutoCompleteTextView duration,
                                             TextInputLayout input_textDuration,
                                             AppCompatAutoCompleteTextView type,
                                             TextInputLayout input_textType
    ) {
        input_textDuration.setErrorEnabled(false);
        input_textType.setErrorEnabled(false);
        if (TextUtils.isEmpty(duration.getText().toString().trim())) {
            setError(input_textDuration, "Select Duration");
            LogUtil.printToastMSGCenter(mContext,"Select Duration");
            return false;
        } else if (TextUtils.isEmpty(type.getText().toString().trim())) {
            setError(input_textType, "Select Leave Type");
            LogUtil.printToastMSGCenter(mContext,"Select Leave Type");
            return false;
        }
        return true;
    }

    //set date picker view
    private void openDataPicker(int val) {
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
                if(val==0){
                    tvDateValueTo.setText(sdf.format(myCalendar.getTime()));
                }
                else {
                    tvDateValueFrom.setText(sdf.format(myCalendar.getTime()));
                }
                int diff = getChangeDateForHisab(tvDateValueFrom.getText().toString(),tvDateValueTo.getText().toString());
                if(AutoComTextViewDuration.getText().toString().equals("Half Day")){
                    appcomptextNoOfDays.setText("Number of days : Half Day");
                    noOFDays = 0;
                }
                else if(AutoComTextViewDuration.getText().toString().equals("Full Day")){
                    appcomptextNoOfDays.setText("Number of days : 01 Days");
                    noOFDays = 1;
                }
                else {
                    appcomptextNoOfDays.setText("Number of days : " + Math.abs(diff) + " Days");
                    noOFDays = diff;
                }
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
        else {*/
            //String dateRestrict = AppUtils.getDashYesterdaysDate();
            dpDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        //}
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
                        if(mListDropdownView.getText().toString().equals("Half Day")){
                            layoutLeaveTime.setVisibility(View.VISIBLE);
                            appcomptextLeaveTime.setVisibility(View.VISIBLE);
                            viewToDate.setVisibility(View.GONE);
                            layToDate.setVisibility(View.GONE);
                            appcomptextNoOfDays.setText("Number of days : Half Day");
                            noOFDays = 0;
                        }
                        else if(mListDropdownView.getText().toString().equals("Full Day")){
                            viewToDate.setVisibility(View.GONE);
                            layToDate.setVisibility(View.GONE);
                            layoutLeaveTime.setVisibility(View.GONE);
                            appcomptextLeaveTime.setVisibility(View.GONE);
                            appcomptextNoOfDays.setText("Number of days : 01 Days");
                            noOFDays = 1;
                        }
                        else{
                            viewToDate.setVisibility(View.VISIBLE);
                            layToDate.setVisibility(View.VISIBLE);
                            layoutLeaveTime.setVisibility(View.GONE);
                            appcomptextLeaveTime.setVisibility(View.GONE);
                            int diff = getChangeDateForHisab(tvDateValueFrom.getText().toString(),tvDateValueTo.getText().toString());
                            appcomptextNoOfDays.setText("Number of days : "+diff +" Days");
                            noOFDays = diff;
                        }
                    }
                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //set value to Search dropdown
    private void setDropdownType(AppCompatAutoCompleteTextView mListDropdownView, PendingLeave pendingLeave) {
        List<String> leaveModelList = new ArrayList<>();

        if((pendingLeave.getLeftCl()!=null && (pendingLeave.getLeftCl()==0)))
        { leaveModelList.add("Casual Leave (0) - Not available");
        }else{leaveModelList.add("Casual Leave ("+pendingLeave.getLeftCl()+")");}
        if((pendingLeave.getLeftSl()!=null && (pendingLeave.getLeftSl()==0)))
        { leaveModelList.add("Sick Leave (0) - Not available");
        }else{ leaveModelList.add("Sick Leave ("+pendingLeave.getLeftSl()+")");}
        if((pendingLeave.getLeftOl()!=null && (pendingLeave.getLeftOl()==0)))
        { leaveModelList.add("Other Leave (0) - Not available");
        }else{leaveModelList.add("Other Leave ("+pendingLeave.getLeftOl()+")");}
        if((pendingLeave.getLeftCl()!=null && (pendingLeave.getLeftCl()==0))
                && (pendingLeave.getLeftSl()!=null && (pendingLeave.getLeftSl()==0))
                && (pendingLeave.getLeftOl()!=null && (pendingLeave.getLeftOl()==0)))
        {
            leaveModelList.add("Unpaid Leave");}

        if(pendingLeave.getLeftCl()==null && pendingLeave.getLeftSl()==null && pendingLeave.getLeftOl()==null){
            leaveModelList.removeAll(leaveModelList);
            leaveModelList.add("Casual Leave (0) - Not available");
            leaveModelList.add("Sick Leave (0) - Not available");
            leaveModelList.add("Other Leave (0) - Not available");
            leaveModelList.add("Unpaid Leave");
        }
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
                        ///if(leaveModelList.size()==4){
                        if(mListDropdownView.getText().toString().equals("Unpaid Leave")){

                        }
                        else{
                            String[] temp = mListDropdownView.getText().toString().trim().split("\\(");
                            String[] count = temp[1].split("\\)");
                            if(count[0].equals("0")){
                                mListDropdownView.setText("");
                                LogUtil.printToastMSG(mContext,getString(R.string.msg_cannot_select_leave));
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

    //Call Api For accept reject record
    private void callAcceptRejectListApi(String pageNo) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_accept_reject_List);
                RequestBody mRequestemp_id = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                RequestBody mRequestleave_type = RequestBody.create(MediaType.parse("text/plain"),"");
                //String monthNumber  =  AppUtils.convertMonthToInt(AutoComMonth.getText().toString().trim());
                //String[] mon = AppUtils.startEndMonthfromInt(Integer.parseInt(monthNumber)).split("~");
                //RequestBody mRequestfrom_date = RequestBody.create(MediaType.parse("text/plain"), mon[0]);
                //RequestBody mRequestend_date = RequestBody.create(MediaType.parse("text/plain"), mon[1]);
                RequestBody mRequestName = RequestBody.create(MediaType.parse("text/plain"),loginTable.getName());
                RequestBody mRequestpage_number = RequestBody.create(MediaType.parse("text/plain"), pageNo);
                RequestBody mRequestpage_size = RequestBody.create(MediaType.parse("text/plain"), Constants.PAG_SIZE);
                RequestBody mRequestcom_id = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());

                AcceptRejectListRequest request = new AcceptRejectListRequest();
                request.setAction(mRequestAction);
                request.setEmpId(mRequestemp_id);
                request.setSearchedEmp(mRequestName);
                request.setLeaveType(mRequestleave_type);
                //request.setFromDate(mRequestfrom_date);
                //request.setEndDate(mRequestend_date);
                request.setPageNo(mRequestpage_number);
                request.setPageSize(mRequestpage_size);
                request.setCompany_id(mRequestcom_id);

                rejectLeaveListViewModel.hitAcceptRejectLeaveAPI(request);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    //Call Api For  record
    private void callPendingLeaveListApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_pending_leaves);
                RequestBody mRequestemp_id = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                RequestBody mRequestcom_id = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());

                pendingLeavesViewModel.hitPendingLeavesAPI(mRequestAction,mRequestemp_id,mRequestcom_id);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    private void callNewPendingLeaveListApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_pending_leaves);
                RequestBody mRequestemp_id = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                RequestBody mRequestcom_id = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());

                LeavesDetailsViewModel.hitPendingLeavesAPI(mRequestAction,mRequestemp_id,mRequestcom_id);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }
    //set value to month dropdown
    private void setDropdownMonth() {
        List<EmployeeLeaveMonthsList> monthsLists = new ArrayList<>();
        monthsLists.add(new EmployeeLeaveMonthsList("January","31 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("February","28 days in a common year and 29 days in leap years"));
        monthsLists.add(new EmployeeLeaveMonthsList("March","31 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("April","30 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("May","31 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("June","30 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("July","31 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("August","31 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("September","30 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("October","31 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("November","30 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("December","31 days"));

        try {
            int pos = 0;
            if (monthsLists != null && monthsLists.size() > 0) {
                String[] mDropdownList = new String[monthsLists.size()];
                for (int i = 0; i < monthsLists.size(); i++) {
                    mDropdownList[i] = String.valueOf(monthsLists.get(i).getName());
                    //pos = i;
                }
                //AutoComMonth.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mContext,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //tvHighlight.setThreshold(1);
                AutoComMonth.setAdapter(adapter);
                AutoComMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        itemCount = 0;
                        currentPage = PAGE_START;
                        isLastPage = false;
                        leaveAdapter.clear();
                        callAcceptRejectListApi("0");
                    }
                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void OpenTimePicker(int val){
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
                    am_pm = "am";
                else if (myCalendar.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "pm";
                String strHrsToShow = (String.valueOf(myCalendar.get(Calendar.HOUR)).length() == 1) ? "0"+myCalendar.get(Calendar.HOUR) : myCalendar.get(Calendar.HOUR) + "";
                //UIHelper.showLongToastInCenter(context, strHrsToShow + ":" + myCalendar.get(Calendar.MINUTE) + " " + am_pm);
                //String min = convertDate(myCalendar.get(Calendar.MINUTE));
                boolean isPM = (selectedHour >= 12);
                if(val==0) {
                    tvTimeValueTo.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, myCalendar.get(Calendar.MINUTE), isPM ? "pm" : "am"));
                }
                else{
                    tvTimeValueFrom.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, myCalendar.get(Calendar.MINUTE), isPM ? "pm" : "am"));

                }
                // AutoComTextViewTime.setText(strHrsToShow + ":" + min + " " + am_pm);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    /*check validations on field*/
    private boolean isDetailsValid(AppCompatAutoCompleteTextView oldPass,
                                   TextInputLayout input_textOldPass,
                                   AppCompatAutoCompleteTextView newPass,
                                   TextInputLayout input_textNewPass ,
                                   AppCompatAutoCompleteTextView ConfPass,
                                   TextInputLayout AutoComConfirmPass
    ) {
        setError(input_textOldPass,"");setError(input_textNewPass, "");
        setError(AutoComConfirmPass, "");
        if (TextUtils.isEmpty(oldPass.getText().toString().trim())) {
            setError(input_textOldPass, getString(R.string.err_msg_old_pass));
            return false;
        } else if (TextUtils.isEmpty(newPass.getText().toString().trim())) {
            setError(input_textNewPass, getString(R.string.err_msg_new_password));
            return false;
        } else if (TextUtils.isEmpty(ConfPass.getText().toString().trim())) {
            setError(AutoComConfirmPass, getString(R.string.err_msg_confirm_password));
            return false;
        } else if (!newPass.getText().toString().trim().equals(ConfPass.getText().toString().trim())) {
            setError(AutoComConfirmPass, getString(R.string.err_msg_wrong_confirm_pass));
            return false;
        }
        return true;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void doApiCall() {
        final ArrayList<AcceptRejectLeave> items = new ArrayList<>();

        for (int i = 0; i < acceptRejectLeaveArrayList.size(); i++) {
            items.add(acceptRejectLeaveArrayList.get(i));
        }
        // do this all stuff on Success of APIs response
        /**
         * manage progress view
         */
        if (currentPage != PAGE_START) leaveAdapter.removeLoading();
        leaveAdapter.addItems(items);
        swipeRefresh.setRefreshing(false);

        // check weather is last page or not
        if (currentPage < totalPage) {
            leaveAdapter.addLoading();
        } else {
            isLastPage = true;
        }
        isLoading = false;
        if(leaveAdapter.getItemCount()>1) {
           // leaveAdapter.removeLoading();
        }
        //  }
        // }, 0);
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_ACCEPT_REJECT_LIST)) {
                            LeaveAcceptRejectResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), LeaveAcceptRejectResponse.class);
                            totalPage = responseModel.getResult().getTotalpages();
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                if (acceptRejectLeaveArrayList != null) {
                                    //employeeListArrayList= new ArrayList<>();
                                }
                                acceptRejectLeaveArrayList = responseModel.getResult().getLeave();
                                doApiCall();
                            }
                        }
                    }catch (Exception e){
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_PENDING_LEAVES)) {
                            PendingLeaveResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), PendingLeaveResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                              showLeaveFormDialog(responseModel.getResult().getLeave().get(0));
                            }else{
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){
                        LogUtil.printToastMSG(mContext,getString(R.string.msg_no_date_leave));
                    }
                    try {
                        if (tag.equalsIgnoreCase("Latest_Leave")) {
                            PendingLeaveResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), PendingLeaveResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                showLeaveDetailsDialog(responseModel.getResult().getLeave().get(0));
                            }else{
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){
                        LogUtil.printToastMSG(mContext,getString(R.string.msg_no_date_leave));
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_APPLY_LEAVE)) {
                            ApplyLeaveResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ApplyLeaveResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                mDialogChangePass.dismiss();
                               showSuccessDialogFragment(mContext,responseModel.getResult().getMessage(),
                                        true,null);
                                //((BaseActivity)mContext).setRateUsCounter(mContext);
                                try{
                                    itemCount = 0;
                                    currentPage = PAGE_START;
                                    isLastPage = false;
                                    leaveAdapter.clear();
                                    callAcceptRejectListApi("0");}catch (Exception e){}
                            }
                            else {
                                mDialogChangePass.dismiss();
                                showSuccessDialogFragment(mContext,responseModel.getResult().getMessage(),
                                        false,null);
                            }
                        }
                    }catch (Exception e){
                        showSuccessDialogFragment(mContext,"Leave application upload failed.",
                                false,null);
                    }
                }
                break;
            case ERROR:
                ((BaseActivity) mContext).dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }


    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        leaveAdapter.clear();
        callAcceptRejectListApi("0");
    }
}