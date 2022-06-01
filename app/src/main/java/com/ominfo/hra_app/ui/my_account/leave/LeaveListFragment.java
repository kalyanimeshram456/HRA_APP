package com.ominfo.hra_app.ui.my_account.leave;

import static com.ominfo.hra_app.util.AppUtils.getChangeDateForHisab;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

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
import com.ominfo.hra_app.ui.dashboard.model.DashModel;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.my_account.MyAccountFragment;
import com.ominfo.hra_app.ui.my_account.leave.adapter.LeaveListAdapter;
import com.ominfo.hra_app.ui.my_account.model.ApplicationLeave;
import com.ominfo.hra_app.ui.my_account.model.LeaveApplicationRequest;
import com.ominfo.hra_app.ui.my_account.model.LeaveApplicationResponse;
import com.ominfo.hra_app.ui.my_account.model.LeaveApplicationViewModel;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.sales_credit.adapter.CompanyTagAdapter;
import com.ominfo.hra_app.ui.sales_credit.adapter.EnquiryPageAdapter;
import com.ominfo.hra_app.ui.sales_credit.model.EnquiryPagermodel;
import com.ominfo.hra_app.ui.sales_credit.model.GraphModel;
import com.ominfo.hra_app.ui.sales_credit.model.RmListModel;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
//https://github.com/PhilJay/MPAndroidChart/wiki/Modifying-the-Viewport

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaveListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaveListFragment extends BaseFragment {

    Context mContext;
    LeaveListAdapter leaveListAdapter;
    @BindView(R.id.rvSalesList)
    RecyclerView rvSalesList;
    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat emptyLayout;
    @BindView(R.id.fromDate)
    AppCompatTextView fromDate;
    @BindView(R.id.toDate)
    AppCompatTextView toDate;
    @BindView(R.id.tvPage)
    AppCompatTextView tvPage;
    @BindView(R.id.layList)
    LinearLayoutCompat layList;
    @BindView(R.id.layPagination)
    RelativeLayout layPagination;
    @BindView(R.id.imgFilter)
    AppCompatImageView imgFilter;
    @BindView(R.id.layFilter)
    LinearLayoutCompat layFilter;
    @BindView(R.id.imgBack)
    LinearLayoutCompat imgBack;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
   /* @BindView(R.id.layIndicators)
    LinearLayoutCompat layIndicators;*/
    List<DashModel> tagList = new ArrayList<>();
    CompanyTagAdapter addTagAdapter;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @Inject
    ViewModelFactory mViewModelFactory;
    private LeaveApplicationViewModel leaveApplicationViewModel;
    // array list for storing entries.
    ArrayList barEntriesArrayList;
    //private static final String[] DATA_BAR_GRAPH = new String[6];//{"","09:00",
    private String[] DAYS = new String[100];/*{"C1", "C2", "C3", "C4", "C5", "C6", *//*"C7", "C8", "C9"
            , "C10", "C11", "C12"*//*};*/

    private String[] DAYSY = new String[100];/*{"5", "60", "15", "70", "25",
           "10"*//*, "45","90", "95","50", "55","60", "65"*//*};*/
    int startPos = 0 , endPos = 0;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    List<ApplicationLeave> leaveArrayList = new ArrayList<>();
    List<GraphModel> graphModelsList = new ArrayList<>();
    private AppDatabase mDb;
    final Calendar myCalendar = Calendar.getInstance();
    List<String> mCompnyList = new ArrayList<>();
    //test
    List<String> mTRMList = new ArrayList<>();
    @BindView(R.id.rvEnquiryPager)
    RecyclerView rvEnquiryPager;
    private String pagerClicked = "No";
    @BindView(R.id.tvTotalCount)
    AppCompatTextView tvTotalCount;
    List<EnquiryPagermodel> enquiryPageList = new ArrayList<>();
    EnquiryPageAdapter enquiryPageAdapter;
    @BindView(R.id.nextPage)
    AppCompatImageView nextPage;
    @BindView(R.id.prePage)
    AppCompatImageView prePage;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    @BindView(R.id.submitButton)
    AppCompatButton submitButton;
    long totalPage = 0;
    @BindView(R.id.tvCompanyName)
    AppCompatTextView tvCompanyName;
    @BindView(R.id.imgCompanySort)
    AppCompatImageView imgCompanySort;
    @BindView(R.id.tvInvoiceNum)
    AppCompatTextView tvInvoiceNum;
    @BindView(R.id.imgInvoiceNum)
    AppCompatImageView imgInvoiceNum;
    private Dialog mDialogChangePass;
    LinearLayoutCompat layoutLeaveTime;
    AppCompatTextView appcomptextLeaveTime;
    View viewToDate;
    RelativeLayout layToDate;
    @BindView(R.id.tvAutoTypeLeave)
    AppCompatAutoCompleteTextView tvAutoTypeLeave;
    @BindView(R.id.tvAutoLeaveStatus)
    AppCompatAutoCompleteTextView tvAutoLeaveStatus;

    public LeaveListFragment() {
        // Required empty public constructor
    }

    public static LeaveListFragment newInstance(String param1, String param2) {
        LeaveListFragment fragment = new LeaveListFragment();
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
        View view = inflater.inflate(R.layout.activity_leave_list, container, false);
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
        fromDate.setPaintFlags(fromDate.getPaintFlags() |  Paint.UNDERLINE_TEXT_FLAG);
        toDate.setPaintFlags(toDate.getPaintFlags() |  Paint.UNDERLINE_TEXT_FLAG);
    }


    private void init(){
        //mDb =BaseApplication.getInstance(mContext).getAppDatabase();
        //requestPermission();
        layList.setVisibility(View.VISIBLE);
        layPagination.setVisibility(View.VISIBLE);
        //add_fab.setVisibility(View.VISIBLE);
        imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
        layFilter.setVisibility(View.GONE);
        setToolbar();
        setDropdownType(tvAutoTypeLeave);
        setDropdownStatus(tvAutoLeaveStatus);
        setDate();
        setEnquiryPagerList(1);
        setAdapterForSalesList();
        callLeaveListApi("0");

        //TODO REMOVE LATER
       // tvInvoices.setText("QUO-HD/21-22/00023");
        //tvMaxAmount.setText("0");
       // tvMinAmount.setText("0");
       // fromDate.setText("06/01/2022");
       // toDate.setText("17/01/2022");

    }

    private void injectAPI() {
        leaveApplicationViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LeaveApplicationViewModel.class);
        leaveApplicationViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_LEAVE_APP));
   }

    private void setDate(){
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());
        toDate.setText(date);fromDate.setText(date);
    }
    //set value to leave type
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
    //set value to leave status
    private void setDropdownStatus(AppCompatAutoCompleteTextView mListDropdownView) {
        List<String> leaveModelList = new ArrayList<>();
        leaveModelList.add("Approved");
        leaveModelList.add("Rejected");
        leaveModelList.add("Applied");
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

    /* Call Api For Leave Applications */
    private void callLeaveListApi(String pageNo) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                String startLeaveDate = AppUtils.dateReminder(fromDate.getText().toString()) ,
                        endLeaveDate =  AppUtils.dateReminder(toDate.getText().toString());
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_leave_app);
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                RequestBody mRequestBodyPageNo = RequestBody.create(MediaType.parse("text/plain"), pageNo);
                RequestBody mRequestBodyPageSize = RequestBody.create(MediaType.parse("text/plain"), Constants.PAG_SIZE);
                RequestBody mRequestBodyleaveType = RequestBody.create(MediaType.parse("text/plain"),tvAutoTypeLeave.getText().toString() );
                RequestBody mRequestBodyTypestatus = RequestBody.create(MediaType.parse("text/plain"),tvAutoLeaveStatus.getText().toString());
                RequestBody mRequestBodyfrom_date = RequestBody.create(MediaType.parse("text/plain"), startLeaveDate);
                RequestBody mRequestBodyend_date = RequestBody.create(MediaType.parse("text/plain"), endLeaveDate);

                LeaveApplicationRequest applicationRequest = new LeaveApplicationRequest();
                applicationRequest.setAction(mRequestBodyAction);
                applicationRequest.setEmpId(mRequestBodyTypeEmpId);
                applicationRequest.setPageno(mRequestBodyPageNo);
                applicationRequest.setPagesize(mRequestBodyPageSize);
                applicationRequest.setLeaveType(mRequestBodyleaveType);
                applicationRequest.setStatus(mRequestBodyTypestatus);
                applicationRequest.setFromDate(mRequestBodyfrom_date);
                applicationRequest.setEndDate(mRequestBodyend_date);
                leaveApplicationViewModel.hitLeaveApplicationApi(applicationRequest);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
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


    private void setAdapterForSalesList() {
        if (leaveArrayList !=null && leaveArrayList.size() > 0) {

            rvSalesList.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        } else {
            rvSalesList.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }
        leaveListAdapter = new LeaveListAdapter(mContext, leaveArrayList, new LeaveListAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(int mDataTicket,ApplicationLeave applicationLeave) {
                //For not killing pre fragment
                showLeaveFormDialog(applicationLeave);
            }
        });
        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(leaveListAdapter);

    }

    //show Receipt Details popup
    public void showReceiptDetailsDialog() {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_receipt_details);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton closeButton = mDialog.findViewById(R.id.closeButton);

        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);

        closeButton.setOnClickListener(new View.OnClickListener() {
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
        ((BaseActivity)mContext).initToolbar(1, mContext, R.id.imgBack, R.id.imgReport, R.id.imgNotify,tvNotifyCount, R.id.imgBack, R.id.imgCall);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MyAccountFragment();
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
    @OnClick({R.id.imgGraph,R.id.imgTable,/*,R.id.add_fab,*/R.id.imgFilter,R.id.resetButton
    ,R.id.toDate,R.id.fromDate,R.id.submitButton,R.id.imgCompanySort, R.id.tvCompanyName,
            R.id.tvInvoiceNum, R.id.imgInvoiceNum})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvInvoiceNum:
                setSortIconComQuoAmo(1);
                //sortforInvoiceNum();
                break;
            case R.id.imgInvoiceNum:
                setSortIconComQuoAmo(1);
                //sortforInvoiceNum();
                break;
            case R.id.imgCompanySort:
                setSortIconComQuoAmo(0);
                //sortforCompany();
                break;
            case R.id.tvCompanyName:
                setSortIconComQuoAmo(0);
                //sortforCompany();
                break;
            case R.id.toDate:
                openDataPicker(1,toDate);
                break;
            case R.id.fromDate:
                openDataPicker(0,fromDate);
                break;
            case R.id.submitButton:
                mCompnyList.clear();
                mTRMList.clear();
                leaveArrayList.clear();
                setEnquiryPagerList(0);
                setAdapterForSalesList();
                tvPage.setText("Showing " + String.valueOf(0) + " to " +
                        String.valueOf(0) + " of " + String.valueOf(0) + "\nEntries");
                try {
                    callLeaveListApi("0");
                }catch (Exception e){e.printStackTrace();}
                layList.setVisibility(View.VISIBLE);
                //layIndicators.setVisibility(View.VISIBLE);
                layPagination.setVisibility(View.VISIBLE);
                layFilter.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                break;
            case R.id.imgFilter:
                //add_fab.setVisibility(View.GONE);
                layList.setVisibility(View.GONE);
                //layIndicators.setVisibility(View.GONE);
                layPagination.setVisibility(View.GONE);
                layFilter.setVisibility(View.VISIBLE);
                submitButton.setVisibility(View.VISIBLE);
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_blue));
                //callRMApi();
                break;

            case R.id.imgGraph:
                //add_fab.setVisibility(View.GONE);
                layList.setVisibility(View.GONE);
                //layIndicators.setVisibility(View.GONE);
                layPagination.setVisibility(View.GONE);
                layFilter.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                break;

            case R.id.imgTable:
                //add_fab.setVisibility(View.VISIBLE);
                layList.setVisibility(View.VISIBLE);
                //layIndicators.setVisibility(View.VISIBLE);
                layPagination.setVisibility(View.VISIBLE);
                layFilter.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                callLeaveListApi("0");
                break;

            case R.id.resetButton:
                tvAutoLeaveStatus.setText(""); tvAutoTypeLeave.setText("");
                break;
        }
    }

    //show leave form popup
    public void showLeaveFormDialog(ApplicationLeave data) {
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
        int diff = 0;
        if(data.getDuration().equals("single day"))
        {
            diff = 1;
            viewToDate.setVisibility(View.GONE);
            layToDate.setVisibility(View.GONE);
            layoutLeaveTime.setVisibility(View.GONE);
            appcomptextLeaveTime.setVisibility(View.GONE);
        }else if(data.getDuration().equals("Half Day")){
            diff = 0;
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
             diff = AppUtils.getChangeDateForHisab(tvDateValueFrom.getText().toString(), tvDateValue.getText().toString());
        }
        appcomptextNoOfDays.setText("Number of days : " + diff + " Days");
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
        addReceiptButton.setText(R.string.scr_lbl_close);tvTitleName.setText(R.string.scr_lbl_leave_details);
        addRejectButton.setVisibility(View.GONE);

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogChangePass.dismiss();
            }
        });
        addRejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogChangePass.dismiss();
            }
        });
        addReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogChangePass.dismiss();
               /* if(isDetailsValid(oldPass,input_textOldPass,newPass,input_textNewPass
                        ,ConfPass,input_textConfirmPass)){
                    callChangePassApi(oldPass.getText().toString(),newPass.getText().toString());
                }*/
            }
        });
        mDialogChangePass.show();
    }

    private void setSortIconComQuoAmo(int res){
        if(res==0){
            imgInvoiceNum.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgCompanySort.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort_blue));
            tvCompanyName.setTextColor(getResources().getColor(R.color.color_main));
            tvInvoiceNum.setTextColor(getResources().getColor(R.color.back_text_colour));
        }
        else if(res==1){
            imgInvoiceNum.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort_blue));
            imgCompanySort.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            tvCompanyName.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvInvoiceNum.setTextColor(getResources().getColor(R.color.color_main));
        }
    }
    /*private void sortforInvoiceNum(){

        Collections.sort(leaveArrayList, new Comparator<ResultInvoice>() {
            @Override
            public int compare(ResultInvoice item, ResultInvoice t1) {
                int returnVal = 0;
                try {
                    String[] s1 = item.getInvoiceNo().split("/");
                    String[] s2 = t1.getInvoiceNo().split("/");
                    returnVal = s1[3].compareToIgnoreCase(s2[3]);
                }catch (Exception e){
                    returnVal = 0;
                }
                return returnVal;
                //return s1.compareToIgnoreCase(s2);
            }
        });
        leaveListAdapter.notifyDataSetChanged();
    }
    private void sortforCompany(){
        Collections.sort(leaveArrayList, new Comparator<ResultInvoice>() {
            @Override
            public int compare(ResultInvoice item, ResultInvoice t1) {
                String s1 = item.getCompanyName();
                String s2 = t1.getCompanyName();
                return s1.compareToIgnoreCase(s2);
            }
        });
        leaveListAdapter.notifyDataSetChanged();
    }*/
    @Override
    public void onResume() {
        super.onResume();
        /*Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.status_bar_color));*/
        Window window = getActivity().getWindow();
        View view = window.getDecorView();
        BaseActivity.DarkStatusBar.setLightStatusBar(view,getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
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
                    datePickerField.setText(sdf.format(myCalendar.getTime()));
                }
                else {
                    datePickerField.setText(sdf.format(myCalendar.getTime()));
                }
                callLeaveListApi("0");
            }

        };

        new DatePickerDialog(mContext, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_LEAVE_APP)) {
                            LeaveApplicationResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), LeaveApplicationResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                leaveArrayList.clear();
                                String ct = String.valueOf(responseModel.getResult().getTotalrows())==null
                                        || String.valueOf(responseModel.getResult().getTotalrows()).equals("null")?"0":String.valueOf(responseModel.getResult().getTotalrows());
                                tvTotalCount.setText(ct);
                                try {
                                    if (responseModel.getResult().getLeave() != null && responseModel.getResult().getLeave().size()>0) {
                                        leaveArrayList = responseModel.getResult().getLeave();
                                        totalPage = responseModel.getResult().getTotalpages();
                                        if(responseModel.getResult().getNextpage()==1) {
                                            tvPage.setText("Showing " + String.valueOf(responseModel.getResult().getNextpage()) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1) + leaveArrayList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalrows()) + "\nEntries"));
                                        }
                                        else {
                                            tvPage.setText("Showing " + String.valueOf(((responseModel.getResult().getNextpage()-1)*7)+1) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1)*7)+ leaveArrayList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalrows()) + "\nEntries");
                                        }
                                    }
                                    else{
                                        totalPage = 0;
                                        leaveArrayList.clear();
                                    }
                                }catch(Exception e){
                                    totalPage = 0;
                                    leaveArrayList.clear();
                                    e.printStackTrace();

                                }
                                setEnquiryPagerList(totalPage);
                                setAdapterForSalesList();
                            }

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case ERROR:
                ((BaseActivity) mContext).dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                if(tag.equalsIgnoreCase(DynamicAPIPath.POST_SALES)){
                    totalPage = 0;
                    leaveArrayList.clear();
                    setEnquiryPagerList(totalPage);
                    setAdapterForSalesList();
                }
               break;
        }
    }

}