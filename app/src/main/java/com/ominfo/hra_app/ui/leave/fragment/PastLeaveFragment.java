package com.ominfo.hra_app.ui.leave.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.model.GradientColor;
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
import com.ominfo.hra_app.ui.dashboard.model.DashModel;
import com.ominfo.hra_app.ui.employees.model.EmployeeList;
import com.ominfo.hra_app.ui.leave.adapter.PastLeaveListAdapter;
import com.ominfo.hra_app.ui.leave.model.PastLeave;
import com.ominfo.hra_app.ui.leave.model.PastLeaveListRequest;
import com.ominfo.hra_app.ui.leave.model.PastLeaveListResponse;
import com.ominfo.hra_app.ui.leave.model.PastLeaveListViewModel;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveRequest;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveResponse;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveViewModel;
import com.ominfo.hra_app.ui.my_account.model.LeaveApplicationRequest;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.sales_credit.adapter.EnquiryPageAdapter;
import com.ominfo.hra_app.ui.sales_credit.model.EnquiryPagermodel;
import com.ominfo.hra_app.ui.sales_credit.model.GraphModel;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;

import java.io.File;
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
 * Use the {@link PastLeaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PastLeaveFragment extends BaseFragment {

    Context mContext;
    PastLeaveListAdapter leaveAdapter;
    //AddTagAdapter addTagAdapter;
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
    List<PastLeave> pastLeaveList = new ArrayList<>();
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
    List<EnquiryPagermodel> enquiryPageList = new ArrayList<>();
    @BindView(R.id.rvEnquiryPager)
    RecyclerView rvEnquiryPager;
    @BindView(R.id.nextPage)
    AppCompatImageView nextPage;
    @BindView(R.id.prePage)
    AppCompatImageView prePage;
    long totalPage = 0;
    EnquiryPageAdapter enquiryPageAdapter;
    private Dialog mDialogChangePass;
    LinearLayoutCompat layoutLeaveTime;
    AppCompatTextView appcomptextLeaveTime;
    View viewToDate;
    RelativeLayout layToDate;
    int cam = 0,noOFDays = 0;
    private String pagerClicked = "No";
    @BindView(R.id.tvPage)
    AppCompatTextView tvPage;
    AppCompatTextView appcomptextNoOfDays;
    AppCompatTextView tvDateValueFrom;
    AppCompatTextView tvDateValueTo,tvTimeValueFrom,tvTimeValueTo ;
    AppCompatAutoCompleteTextView AutoComTextViewComment;
    AppCompatAutoCompleteTextView tvAutoLeaveStatus;
    AppCompatAutoCompleteTextView AutoComTextViewDuration;
    AppCompatAutoCompleteTextView AutoComTextViewLeaveType;
    private ApplyLeaveViewModel applyLeaveViewModel;
    @BindView(R.id.layList)
    LinearLayoutCompat layList;
    @BindView(R.id.layPagination)
    RelativeLayout layPagination;

    public PastLeaveFragment() {
        // Required empty public constructor
    }

    public static PastLeaveFragment newInstance(String param1, String param2) {
        PastLeaveFragment fragment = new PastLeaveFragment();
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
        View view = inflater.inflate(R.layout.activity_past_leave, container, false);
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
        layList.setVisibility(View.VISIBLE);
        layPagination.setVisibility(View.VISIBLE);
        setEnquiryPagerList(1);
        setAdapterForLeaveList();
        callGetPastLeaveApi("0");
    }

    private void injectAPI() {
        pastLeaveListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PastLeaveListViewModel.class);
        pastLeaveListViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_PAST_LEAVE));

        applyLeaveViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ApplyLeaveViewModel.class);
        applyLeaveViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_APPLY_LEAVE));
    }

    private void setPagerEnquiryList(long pageNo){
        for(int i=0;i<pageNo;i++) {
            if (pagerClicked.equals("No")) {
                if (i == 0) {
                    rvEnquiryPager.scrollToPosition(i+1);
                    enquiryPageList.add(new EnquiryPagermodel(String.valueOf(i + 1), 1));
                } else {
                    enquiryPageList.add(new EnquiryPagermodel(String.valueOf(i + 1), 0));
                }
            } else {
                if (i == Integer.parseInt(pagerClicked)) {
                    enquiryPageList.add(new EnquiryPagermodel(String.valueOf(i + 1), 1));
                } else {
                    enquiryPageList.add(new EnquiryPagermodel(String.valueOf(i + 1), 0));
                }
            }
        }
    }

    private void setEnquiryPagerList(long pageNo) {
        enquiryPageList.clear();
        if(pageNo==0) {
            pageNo = 1;
        }
        setPagerEnquiryList(pageNo);
        if (enquiryPageList.size() > 0) {
            rvEnquiryPager.setVisibility(View.VISIBLE);
        } else {
            rvEnquiryPager.setVisibility(View.GONE);
        }

        rvEnquiryPager.setHasFixedSize(true);
        //rvEnquiryPager.setLayoutManager(new GridLayoutManager(mContext, 3));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rvEnquiryPager.setLayoutManager(layoutManager);
        rvEnquiryPager.setItemAnimator(new DefaultItemAnimator());
        rvEnquiryPager.setAdapter(enquiryPageAdapter);
        try{
            rvEnquiryPager.scrollToPosition(Integer.parseInt(pagerClicked));}catch (Exception e){e.printStackTrace();}
        final boolean[] check = {false};
        prePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //try{
                /*LogUtil.printToastMSG(mContext,"prev");
                int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                rvEnquiryPager.scrollToPosition(firstVisiblePosition-1);
                //int firstVisiblePositionNew = layoutManager.findFirstVisibleItemPosition();
                enquiryPageAdapter.updatePageList(firstVisiblePosition-1);
                }catch (Exception e){e.printStackTrace();*/
                //}catch (Exception e){e.printStackTrace();}
            }
        });
        nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    /*LogUtil.printToastMSG(mContext,"next");
                    int firstVisiblePositionLast = layoutManager.findLastVisibleItemPosition();
                    int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                    rvEnquiryPager.scrollToPosition(firstVisiblePositionLast-1);
                    //int firstVisiblePositionNew = layoutManager.findFirstVisibleItemPosition();
                    enquiryPageAdapter.updatePageList(firstVisiblePosition + 1);*/
                }catch (Exception e){e.printStackTrace();}
            }
        });
    }

    private void resetApiData(){
        pastLeaveList.clear();
        setEnquiryPagerList(0);
        setAdapterForLeaveList();
        tvPage.setText("Showing " + String.valueOf(0) + " to " +
                String.valueOf(0) + " of " + String.valueOf(0) + "\nEntries");
        try {
            callGetPastLeaveApi("0");
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    //show Quotation popup
    public void showQuotationDialog() {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_single_quotation);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton downloadButton = mDialog.findViewById(R.id.downloadButton);
        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);

        downloadButton.setOnClickListener(new View.OnClickListener() {
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


    private void setAdapterForLeaveList() {
        if (pastLeaveList !=null && pastLeaveList.size() > 0) {
            rvSalesList.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            } else {
            rvSalesList.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(R.drawable.img_bg_search)
                    .into(iv_emptyLayimage);
            tv_emptyLayTitle.setText(R.string.scr_lbl_no_data_available);
        }
        leaveAdapter = new PastLeaveListAdapter(mContext, pastLeaveList, new PastLeaveListAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(int mDataTicket,PastLeave searchresult) {

            }
        });
        //RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(mContext.getDrawable(R.drawable.separator_row_item));
        //rvSalesList.addItemDecoration(dividerItemDecoration);
        //rvSalesList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(leaveAdapter);
        final boolean[] check = {false};

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
    @OnClick({R.id.imgAddLeave})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgAddLeave:
                showLeaveFormDialog();
                break;
        }
    }
    //show leave form popup
    public void showLeaveFormDialog() {
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
        int diff = AppUtils.getChangeDateForHisab(tvDateValueFrom.getText().toString(),tvDateValueTo.getText().toString());
        appcomptextNoOfDays.setText("Number of days : "+diff +" Days");
        noOFDays = diff;
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogChangePass.dismiss();
            }
        });
        setDropdownLeaveDuration(AutoComTextViewDuration);
        setDropdownType(AutoComTextViewLeaveType);
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
                mDialogChangePass.dismiss();
                if(isAttendanceDetailsValid(AutoComTextViewDuration,input_textDuration,AutoComTextViewLeaveType,input_textType
                )){
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
                } else  if(AutoComTextViewDuration.getText().toString().equals("Full Day"))
                {
                    startTimeStamp = "00:00:00";endTimeStamp = "23:59:00";
                }else {
                    startTimeStamp = "00:00:00";endTimeStamp = "23:59:00";
                }

                RequestBody mRequestBodyStartTime = RequestBody.create(MediaType.parse("text/plain"), startDateTimeStamp+" "+startTimeStamp);
                RequestBody mRequestBodyEndTime = RequestBody.create(MediaType.parse("text/plain"), endDateTimeStamp+" "+endTimeStamp);
                RequestBody mRequestBodyLeaveType = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewLeaveType.getText().toString());
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

    /* Call Api For past Leave */
    private void callGetPastLeaveApi(String pageNo) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_past_leave);
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                Calendar instance = Calendar.getInstance();
                int currentMonth = instance.get(Calendar.MONTH);
                int month=currentMonth+1;
                String monthNumber  =  String.valueOf(month).length()==1?"0"+month : String.valueOf(month);
                RequestBody mRequestBodyMonth = RequestBody.create(MediaType.parse("text/plain"), monthNumber);
                RequestBody mRequestPageNo = RequestBody.create(MediaType.parse("text/plain"), pageNo);
                RequestBody mRequestPageSize = RequestBody.create(MediaType.parse("text/plain"), Constants.PAG_SIZE);

                PastLeaveListRequest request = new PastLeaveListRequest();
                request.setAction(mRequestBodyAction);
                request.setEmpId(mRequestBodyTypeEmpId);
                request.setMonth(mRequestBodyMonth);
                request.setPageNo(mRequestPageNo);
                request.setPageSize(mRequestPageSize);

                pastLeaveListViewModel.hitPastLeaveListAPI(request);
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
        setError(input_textDuration,"");setError(input_textType, "");
        //setError(AutoComConfirmPass, "");
        if (TextUtils.isEmpty(duration.getText().toString().trim())) {
            setError(input_textDuration, "Select Duration");
            LogUtil.printToastMSG(mContext,"Select Duration");
            return false;
        } else if (TextUtils.isEmpty(type.getText().toString().trim())) {
            setError(input_textType, "Select Leave Type");
            LogUtil.printToastMSG(mContext,"Select Leave Type");
            return false;
        }
        return true;
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
                            int diff = AppUtils.getChangeDateForHisab(tvDateValueFrom.getText().toString(),tvDateValueTo.getText().toString());
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
    private void setDropdownType(AppCompatAutoCompleteTextView mListDropdownView) {
        List<String> leaveModelList = new ArrayList<>();
        leaveModelList.add("Casual Leave");
        leaveModelList.add("Sick Leave");
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
                int diff = AppUtils.getChangeDateForHisab(tvDateValueFrom.getText().toString(),tvDateValueTo.getText().toString());
                appcomptextNoOfDays.setText("Number of days : "+diff +" Days");
                noOFDays = diff;
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


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_APPLY_LEAVE)) {
                            ApplyLeaveResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ApplyLeaveResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                mDialogChangePass.dismiss();
                                ((BaseActivity)mContext).showSuccessDialog(responseModel.getResult().getMessage(),
                                        true,getActivity());
                                //((BaseActivity)mContext).setRateUsCounter(mContext);
                            }
                            else {
                                mDialogChangePass.dismiss();
                                ((BaseActivity)mContext).showSuccessDialog(responseModel.getResult().getMessage(),
                                        true,getActivity());
                            }
                        }
                    }catch (Exception e){
                        ((BaseActivity)mContext).showSuccessDialog("Leave application upload failed.",
                                true,getActivity());
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_PAST_LEAVE)) {
                            PastLeaveListResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), PastLeaveListResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                pastLeaveList.clear();
                                try {
                                    if (responseModel.getResult().getLeave() != null && responseModel.getResult().getLeave().size()>0) {
                                        pastLeaveList = responseModel.getResult().getLeave();
                                        totalPage = responseModel.getResult().getTotalpages();
                                        if(responseModel.getResult().getNextpage()==1) {
                                            tvPage.setText("Showing " + String.valueOf(responseModel.getResult().getNextpage()) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1) + pastLeaveList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalrows()) + "\nEntries"));
                                        }
                                        else {
                                            tvPage.setText("Showing " + String.valueOf(((responseModel.getResult().getNextpage()-1)*7)+1) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1)*7)+ pastLeaveList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalrows()) + "\nEntries");
                                        }
                                    }
                                    else{
                                        totalPage = 0;
                                        pastLeaveList.clear();
                                    }
                                }catch(Exception e){
                                    totalPage = 0;
                                    pastLeaveList.clear();
                                    e.printStackTrace();

                                }
                                setEnquiryPagerList(totalPage);
                                setAdapterForLeaveList();
                            }
                        }
                    }catch (Exception e){
                        ((BaseActivity)mContext).showSuccessDialog("Leave application upload failed.",
                                true,getActivity());
                        e.printStackTrace();
                    }
                }
                break;
            case ERROR:
                ((BaseActivity) mContext).dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                if(tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_PAST_LEAVE)){
                    totalPage = 0;
                    pastLeaveList.clear();
                    setEnquiryPagerList(totalPage);
                    setAdapterForLeaveList();
                }
                break;
        }
    }


}