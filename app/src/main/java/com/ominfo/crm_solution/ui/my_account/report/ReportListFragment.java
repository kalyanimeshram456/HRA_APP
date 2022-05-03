package com.ominfo.crm_solution.ui.my_account.report;

import static com.ominfo.crm_solution.util.AppUtils.getChangeDateForHisab;

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

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.basecontrol.BaseApplication;
import com.ominfo.crm_solution.basecontrol.BaseFragment;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.interfaces.Constants;
import com.ominfo.crm_solution.network.ApiResponse;
import com.ominfo.crm_solution.network.DynamicAPIPath;
import com.ominfo.crm_solution.network.NetworkCheck;
import com.ominfo.crm_solution.network.ViewModelFactory;
import com.ominfo.crm_solution.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.enquiry_report.adapter.EnquiryPageAdapter;
import com.ominfo.crm_solution.ui.enquiry_report.model.EnquiryPagermodel;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmlist;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.my_account.leave.adapter.LeaveListAdapter;
import com.ominfo.crm_solution.ui.my_account.model.ApplicationLeave;
import com.ominfo.crm_solution.ui.my_account.model.GetTicketRequest;
import com.ominfo.crm_solution.ui.my_account.model.GetTicketResponse;
import com.ominfo.crm_solution.ui.my_account.model.GetTicketViewModel;
import com.ominfo.crm_solution.ui.my_account.model.GetTicketNoResponse;
import com.ominfo.crm_solution.ui.my_account.model.GetTicketNoViewModel;
import com.ominfo.crm_solution.ui.my_account.model.RaiseTicketRequest;
import com.ominfo.crm_solution.ui.my_account.model.RaiseTicketResponse;
import com.ominfo.crm_solution.ui.my_account.model.RaiseTicketViewModel;
import com.ominfo.crm_solution.ui.my_account.model.Raisedticketfilter;
import com.ominfo.crm_solution.ui.my_account.model.UpdateTicketRequest;
import com.ominfo.crm_solution.ui.my_account.model.UpdateTicketResponse;
import com.ominfo.crm_solution.ui.my_account.model.UpdateTicketViewModel;
import com.ominfo.crm_solution.ui.my_account.report.model.GetReportListAdapter;
import com.ominfo.crm_solution.ui.notifications.NotificationsActivity;
import com.ominfo.crm_solution.ui.sale.adapter.CompanyTagAdapter;
import com.ominfo.crm_solution.ui.sale.model.RmListModel;
import com.ominfo.crm_solution.ui.sales_credit.model.GraphModel;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;

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
 * Use the {@link ReportListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReportListFragment extends BaseFragment {

    Context mContext;
    GetReportListAdapter getReportListAdapter;
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
    @BindView(R.id.idBarChart)
    PieChart pieChart;
    @BindView(R.id.imgTable)
    AppCompatImageView imgTable;
    @BindView(R.id.imgGraph)
    AppCompatImageView imgGraph;
    @BindView(R.id.imgFilter)
    AppCompatImageView imgFilter;
    @BindView(R.id.layFilter)
    LinearLayoutCompat layFilter;
    @BindView(R.id.imgBack)
    AppCompatImageView imgBack;
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
    private GetTicketViewModel getTicketViewModel;
    private GetRmViewModel getRmViewModel;
    private GetTicketNoViewModel getTicketNoViewModel;
    private RaiseTicketViewModel raiseTicketViewModel;
    private UpdateTicketViewModel updateTicketViewModel;
    BarData barData;
    List<GradientColor> list = new ArrayList<>();
    // variable for our bar data set.
    BarDataSet barDataSet;
    AppCompatAutoCompleteTextView AutoComTextViewPriority;
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
    List<Raisedticketfilter> raisedticketfilterList = new ArrayList<>();
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
    List<RmListModel> tagRmList = new ArrayList<>();
    @BindView(R.id.nextPage)
    AppCompatImageView nextPage;
    @BindView(R.id.prePage)
    AppCompatImageView prePage;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    @BindView(R.id.submitButton)
    AppCompatButton submitButton;
    long totalPage = 0;
    List<GetRmlist> RMDropdown = new ArrayList<>();
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
    @BindView(R.id.tvAutoLeaveStatus)
    AppCompatAutoCompleteTextView tvAutoLeaveStatus;
    @BindView(R.id.imgReport)
    AppCompatImageView imgReport;
    @BindView(R.id.AutoComTextViewTicketNumber)
    AppCompatAutoCompleteTextView AutoComTextViewTicketNumber;
    String tickerNo = "";
    Dialog mDialogReport;
    AppCompatAutoCompleteTextView AutoComTextViewTitle,AutoComTextViewIssueType,
            AutoComTextViewDescr,AutoComTextViewStatus,AutoComTextViewReason;
    AppCompatTextView tvResolu,tvTicketNo;
    public ReportListFragment() {
        // Required empty public constructor
    }

    public static ReportListFragment newInstance(String param1, String param2) {
        ReportListFragment fragment = new ReportListFragment();
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
        View view = inflater.inflate(R.layout.activity_report_list, container, false);
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
        imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_grey));
        imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_table));
        imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
        layFilter.setVisibility(View.GONE);
        setToolbar();
        setDropdownStatus(tvAutoLeaveStatus);
        setDate();
        setEnquiryPagerList(1);
        setAdapterForSalesList();
        callReportListApi("0");

        graphModelsList.removeAll(graphModelsList);
        graphModelsList.add(new GraphModel("State C1", "Company Test 1", "5"));
        graphModelsList.add(new GraphModel("State C2", "Company Test 2", "60"));
        graphModelsList.add(new GraphModel("State C3", "Company Test 3", "15"));
        graphModelsList.add(new GraphModel("State C4", "Company Test 4", "90"));
        graphModelsList.add(new GraphModel("State C5", "Company Test 5", "25"));
        graphModelsList.add(new GraphModel("State C6", "Company Test 6", "10"));
        graphModelsList.add(new GraphModel("State C7", "Company Test 7", "45"));
        graphModelsList.add(new GraphModel("State C8", "Company Test 8", "90"));
        graphModelsList.add(new GraphModel("State C9", "Company Test 9", "95"));
        graphModelsList.add(new GraphModel("State C10", "Company Test 10", "50"));
        graphModelsList.add(new GraphModel("State C11", "Company Test 11", "55"));
        graphModelsList.add(new GraphModel("State C12", "Company Test 12", "60"));
        setGraphData(3);

        //TODO REMOVE LATER
       // tvInvoices.setText("QUO-HD/21-22/00023");
        //tvMaxAmount.setText("0");
       // tvMinAmount.setText("0");
       // fromDate.setText("06/01/2022");
       // toDate.setText("17/01/2022");
        imgReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callGetTicketNoApi();
            }
        });
    }

    private void injectAPI() {
        getTicketViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetTicketViewModel.class);
        getTicketViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_TICKET));

        getRmViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetRmViewModel.class);
        getRmViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_RM));

        getTicketNoViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetTicketNoViewModel.class);
        getTicketNoViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_TICKET_NO));

        raiseTicketViewModel = ViewModelProviders.of(this, mViewModelFactory).get(RaiseTicketViewModel.class);
        raiseTicketViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_RAISE_TICKET));

        updateTicketViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UpdateTicketViewModel.class);
        updateTicketViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_UPDATE_TICKET));
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
        leaveModelList.add("Open");
        leaveModelList.add("Closed");
        leaveModelList.add("In progress");
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

    //set value to Issue Type
    private void setDropdownIssueType(AppCompatAutoCompleteTextView mListDropdownView) {
        List<String> leaveModelList = new ArrayList<>();
        leaveModelList.add("App is not Responding");
        leaveModelList.add("Attendance related issue");
        leaveModelList.add("Visit related issue");
        leaveModelList.add("Enquiry related issue");
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

    //set value to priority
    private void setDropdownPriority(AppCompatTextView tvResolution) {
        List<String> leaveModelList = new ArrayList<>();
        leaveModelList.add("Low");
        leaveModelList.add("Medium");
        leaveModelList.add("High");

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
                AutoComTextViewPriority.setAdapter(adapter);
                //mListDropdownView.setHint(mDropdownList[pos]);
                AutoComTextViewPriority.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(AutoComTextViewPriority.getText().toString().equals("Low")) {
                            tvResolution.setText("Resolution time : Within 5 - 6 working days.");
                        }
                        else if(AutoComTextViewPriority.getText().toString().equals("Medium")) {
                            tvResolution.setText("Resolution time : Within 2 - 3 working days.");
                        }
                        else{
                            tvResolution.setText("Resolution time : Within 24 hrs.");
                        }
                    }
                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //set value to status
    private void setDropdownIssueStatus(AppCompatAutoCompleteTextView mListDropdownView) {
        List<String> leaveModelList = new ArrayList<>();
        leaveModelList.add("Update");
        leaveModelList.add("Close");

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
    private void callReportListApi(String pageNo) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                String startLeaveDate = AppUtils.dateReminder(fromDate.getText().toString()) ,
                        endLeaveDate =  AppUtils.dateReminder(toDate.getText().toString());
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_ticket);
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                RequestBody mRequestBodyPageNo = RequestBody.create(MediaType.parse("text/plain"), pageNo);
                RequestBody mRequestBodyPageSize = RequestBody.create(MediaType.parse("text/plain"), Constants.PAG_SIZE);
                RequestBody mRequestBodyTicketNo = RequestBody.create(MediaType.parse("text/plain"),AutoComTextViewTicketNumber.getText().toString() );
                RequestBody mRequestBodyTypestatus = RequestBody.create(MediaType.parse("text/plain"),tvAutoLeaveStatus.getText().toString());
                RequestBody mRequestBodyfrom_date = RequestBody.create(MediaType.parse("text/plain"), startLeaveDate);
                RequestBody mRequestBodyend_date = RequestBody.create(MediaType.parse("text/plain"), endLeaveDate);
                //RequestBody mRequestBodyPriority = RequestBody.create(MediaType.parse("text/plain"), endLeaveDate);

                GetTicketRequest applicationRequest = new GetTicketRequest();
                applicationRequest.setAction(mRequestBodyAction);
                applicationRequest.setEmpId(mRequestBodyTypeEmpId);
                applicationRequest.setPageno(mRequestBodyPageNo);
                applicationRequest.setPagesize(mRequestBodyPageSize);
                applicationRequest.setTicketNo(mRequestBodyTicketNo);
                applicationRequest.setStatus(mRequestBodyTypestatus);
                applicationRequest.setFromDate(mRequestBodyfrom_date);
                applicationRequest.setEndDate(mRequestBodyend_date);
                //applicationRequest.setPriority(mRequestBodyPriority);
                getTicketViewModel.hitGetTicketApi(applicationRequest);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    private void setGraphData(int initStatus) {
        if(initStatus!=3) {
            DAYS = new String[6];
            DAYSY = new String[6];
        }
        if(initStatus==3){
            DAYS = new String[graphModelsList.size()+1];
            DAYSY = new String[graphModelsList.size()+1];
        }
        if(initStatus!=3) {
            try {
                endPos = startPos + 6;
                if (endPos <= graphModelsList.size()) {
                    //if(startPos<6) {

                    for (int i = 0; i < graphModelsList.size(); i++) {
                        if (graphModelsList.get(i).getxValue() != null) {
                            DAYS[i] = graphModelsList.get(i).getxValue();
                        }
                        if (graphModelsList.get(i).getyValue() != null) {
                            DAYSY[i] = graphModelsList.get(i).getyValue();
                        }
                    }
                    try {
                        getGraph();
                        //setAdapterForDashboardList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(initStatus==3){
            for (int i = 0; i < graphModelsList.size(); i++) {
                if(graphModelsList.get(i).getxValue()!=null) {
                    DAYS[i] = graphModelsList.get(i).getxValue();
                }
                if(graphModelsList.get(i).getyValue()!=null) {
                    DAYSY[i] = graphModelsList.get(i).getyValue();
                }
            }
            try {
                getGraph();
                //setAdapterForDashboardList();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    //show Report popup
    public void showReportDialog(int status,Raisedticketfilter data) {
        mDialogReport = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogReport.setContentView(R.layout.dialog_ticket_details);
        mDialogReport.setCanceledOnTouchOutside(true);
         AutoComTextViewTitle = mDialogReport.findViewById(R.id.AutoComTextViewTitle);
         AutoComTextViewIssueType = mDialogReport.findViewById(R.id.AutoComTextViewIssueType);
         AutoComTextViewDescr = mDialogReport.findViewById(R.id.AutoComTextViewDescr);
         AutoComTextViewPriority = mDialogReport.findViewById(R.id.AutoComTextViewPriority);
         tvResolu = mDialogReport.findViewById(R.id.tvResolu);
         AutoComTextViewStatus = mDialogReport.findViewById(R.id.AutoComTextViewStatus);
         AutoComTextViewReason = mDialogReport.findViewById(R.id.AutoComTextViewReason);

        AppCompatImageView mClose = mDialogReport.findViewById(R.id.imgCancel);
        AppCompatButton submitButton = mDialogReport.findViewById(R.id.submitButton);
        AppCompatButton dismissButton = mDialogReport.findViewById(R.id.dismissButton);
        AppCompatTextView tvStatus = mDialogReport.findViewById(R.id.tvStatus);
        AppCompatTextView appcomptextReason = mDialogReport.findViewById(R.id.appcomptextReason);
        TextInputLayout input_textStatus = mDialogReport.findViewById(R.id.input_textStatus);
        tvTicketNo = mDialogReport.findViewById(R.id.tvTicketNo);
        TextInputLayout input_textReason = mDialogReport.findViewById(R.id.input_textReason);
        setDropdownIssueType(AutoComTextViewIssueType);
        setDropdownIssueStatus(AutoComTextViewStatus);
        if(status==0){
            tvTicketNo.setText("Ticket No : "+tickerNo);
            tvStatus.setVisibility(View.GONE);
            input_textStatus.setVisibility(View.GONE);
            appcomptextReason.setVisibility(View.GONE);
            input_textReason.setVisibility(View.GONE);
            AutoComTextViewPriority.setText("Low");
            setDropdownPriority(tvResolu);
            tvResolu.setText("Resolution time : Within 5 - 6 working days.");
        }
        else{
            dismissButton.setVisibility(View.VISIBLE);
            submitButton.setText("Save changes");
            dismissButton.setText("Don't save");
            tickerNo = data.getTicketNo();
            tvTicketNo.setText("Ticket No : "+tickerNo);
            AutoComTextViewTitle.setText(data.getSubject());
            AutoComTextViewIssueType.setText(data.getIssueType());
            setDropdownIssueType(AutoComTextViewIssueType);
            AutoComTextViewDescr.setText(data.getDescription());
            String statUpdate = data.getStatus()==null || data.getStatus().equals("Open")?"Update":data.getStatus();
            AutoComTextViewStatus.setText(statUpdate);
            setDropdownIssueStatus(AutoComTextViewStatus);
            AutoComTextViewReason.setText(data.getReason());
            if(data.getPriority().equals("3")) {
                AutoComTextViewPriority.setText("Low");
                tvResolu.setText("Resolution time : Within 5 - 6 working days.");
            }
            else if(data.getPriority().equals("2")) {
                AutoComTextViewPriority.setText("Medium");
                tvResolu.setText("Resolution time : Within 2 - 3 working days.");
            }
            else {
                AutoComTextViewPriority.setText("High");
                tvResolu.setText("Resolution time : Within 24 hrs.");
            }
            setDropdownPriority(tvResolu);
        }
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogReport.dismiss();
                if(status==0){
                    //raise issue
                    callRaiseTicketApi();
                }
                else{
                    //update
                    callUpdateTicketApi();
                }
            }
        });

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogReport.dismiss();
            }
        });
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogReport.dismiss();
            }
        });
        mDialogReport.show();
    }

    private BarData getBarEntries() {

        barEntriesArrayList = new ArrayList<>();
        for (int i = 0; i < DAYSY.length; i++) {
            Random r = new Random();
            float x = i;
            if(DAYSY[i]!=null) {
                float y = Integer.parseInt(DAYSY[i]);
                barEntriesArrayList.add(new BarEntry(x, y));
            }
        }

        MyBarDataSet set1 = new MyBarDataSet(barEntriesArrayList, "Test");
        set1.setBarBorderColor(Color.YELLOW);
        String dark_Red ="#A10616";
        String light_Red = "#FB6571";
        String Yellow = "#F9B747";
        String pink = "#e12c2c";
        String darkPink = "#DD3546";

       /* list.add(new GradientColor(Color.parseColor("#F9B747"),
                Color.parseColor("#A10616")));*/
        /*list.add(new GradientColor(Color.parseColor(Yellow),
                Color.parseColor(pink)));*/
       /* list.add(new GradientColor(Color.parseColor(pink),
                Color.parseColor(darkPink)));*/
        list.add(new GradientColor(Color.parseColor(Yellow),
                Color.parseColor(darkPink)));
        list.add(new GradientColor(Color.parseColor(Yellow),
                Color.parseColor(dark_Red)));

        //set1.setColor(R.drawable.chart_fill);
        set1.setGradientColors(list);
        set1.setDrawValues(false);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        set1.setHighlightEnabled(false);
        set1.setDrawValues(true);

        dataSets.add(set1);
        // adding color to our bar data set.
        BarData data = new BarData(dataSets);
        return data;
    }
    /* Call Api For get Ticket no*/
    private void callGetTicketNoApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_ticket_no);
                getTicketNoViewModel.hitGetTicketNoApi(mRequestBodyAction);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }
    /* Call Api For Raise Ticket */
    private void callRaiseTicketApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_raise_ticket);
                RequestBody mRequestBodyCustId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                RequestBody mRequestBodySubj = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewTitle.getText().toString());
                RequestBody mRequestBodyDesr = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewDescr.getText().toString());
                String val = "3";
                if(AutoComTextViewPriority.getText().toString().equals("Low")){
                    val = "3";
                }
                else if(AutoComTextViewPriority.getText().toString().equals("Medium")){
                    val = "2";
                }
                else if(AutoComTextViewPriority.getText().toString().equals("High")){
                    val = "1";
                }
                String[] tickData = tvTicketNo.getText().toString().split("Ticket No : ");
                RequestBody mRequestBodyPri = RequestBody.create(MediaType.parse("text/plain"), val);
                RequestBody mRequestBodyIssType = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewIssueType.getText().toString());
                RequestBody mRequestBodyTicketNo = RequestBody.create(MediaType.parse("text/plain"),tickerNo);

                RaiseTicketRequest raiseTicketRequest = new RaiseTicketRequest();
                raiseTicketRequest.setAction(mRequestBodyAction);
                raiseTicketRequest.setCustId(mRequestBodyCustId);
                raiseTicketRequest.setSubject(mRequestBodySubj);
                raiseTicketRequest.setDescription(mRequestBodyDesr);
                raiseTicketRequest.setPriority(mRequestBodyPri);
                raiseTicketRequest.setIssueType(mRequestBodyIssType);
                raiseTicketRequest.setTicketNo(mRequestBodyTicketNo);
                raiseTicketViewModel.hitRaiseTicketApi(raiseTicketRequest);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Update Ticket */
    private void callUpdateTicketApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_update_ticket);
                //RequestBody mRequestBodyCustId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                RequestBody mRequestBodySubj = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewTitle.getText().toString());
                RequestBody mRequestBodyDesr = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewDescr.getText().toString());
                String val = "3";
                if(AutoComTextViewPriority.getText().toString().equals("Low")){
                    val = "3";
                }
                else if(AutoComTextViewPriority.getText().toString().equals("Medium")){
                    val = "2";
                }
                else if(AutoComTextViewPriority.getText().toString().equals("High")){
                    val = "1";
                }
                String[] tickData = tvTicketNo.getText().toString().split("Ticket No : ");
                RequestBody mRequestBodyPri = RequestBody.create(MediaType.parse("text/plain"), val);
                RequestBody mRequestBodyIssType = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewIssueType.getText().toString());
                RequestBody mRequestBodyTicketNo = RequestBody.create(MediaType.parse("text/plain"), tickerNo);
                String status = AutoComTextViewStatus.getText().toString().equals("Update")?"Open":AutoComTextViewStatus.getText().toString();
                RequestBody mRequestBodyStatus = RequestBody.create(MediaType.parse("text/plain"), status);
                RequestBody mRequestBodyReason = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewReason.getText().toString());

                UpdateTicketRequest updateTicketRequest = new UpdateTicketRequest();
                updateTicketRequest.setAction(mRequestBodyAction);
                updateTicketRequest.setSubject(mRequestBodySubj);
                updateTicketRequest.setDescription(mRequestBodyDesr);
                updateTicketRequest.setPriority(mRequestBodyPri);
                updateTicketRequest.setIssueType(mRequestBodyIssType);
                updateTicketRequest.setStatus(mRequestBodyStatus);
                updateTicketRequest.setReason(mRequestBodyReason);
                updateTicketRequest.setTicket_no(mRequestBodyTicketNo);
                updateTicketViewModel.hitUpdateTicketApi(updateTicketRequest);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    private void setAdapterForSalesList() {
        if (raisedticketfilterList !=null && raisedticketfilterList.size() > 0) {

            rvSalesList.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        } else {
            rvSalesList.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }
        getReportListAdapter = new GetReportListAdapter(mContext, raisedticketfilterList, new GetReportListAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(int mDataTicket,Raisedticketfilter raisedticketfilter) {
                showReportDialog(1,raisedticketfilter);
            }
        });
        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(getReportListAdapter);
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
        ((BaseActivity)mContext).initToolbar(1, mContext, R.id.imgBack, R.id.imgReport, R.id.imgNotify,tvNotifyCount, R.id.layBack, R.id.imgCall);
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

    private void getGraph(){

        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        String label = "type";

        //initializing data
        Map<String, Integer> typeAmountMap = new HashMap<>();
        typeAmountMap.put("Bosch Ltd",50);
        typeAmountMap.put("Snacks Ltd",50);
        typeAmountMap.put("Tara Steel Ltd",100);
        typeAmountMap.put("Alpha Ltd",200);

        //initializing colors for the entries
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#223E6D"));//Blue
        colors.add(Color.parseColor("#7eb3ff"));//Sky Blue
        colors.add(Color.parseColor("#38A15E"));//Green
        colors.add(Color.parseColor("#BB120D"));//RED
       // colors.add(Color.parseColor("#a35567"));
       // colors.add(Color.parseColor("#ff5f67"));
       // colors.add(Color.parseColor("#3ca567"));

        //input data and fit data into pie chart entry
        for(String type: typeAmountMap.keySet()){
            pieEntries.add(new PieEntry(typeAmountMap.get(type).floatValue(), type));
        }

        //collecting the entries with label name
        PieDataSet pieDataSet = new PieDataSet(pieEntries,label);
        //setting text size of the value
        pieDataSet.setValueTextSize(12f);
        //providing color list for coloring different entries
        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(4.5f);

        //grouping the data set from entry to chart
        PieData pieData = new PieData(pieDataSet);
        //showing the value of the entries, default true if not set
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setWordWrapEnabled(true);
        l.setDrawInside(false);
        l.setYOffset(5f);
        //pieData.setDrawValues(true);
        pieChart.setDrawSliceText(false);
        pieChart.getDescription().setEnabled(false);
        pieData.setDrawValues(false);
        pieChart.setData(pieData);
        //pieChart.setDrawSlicesUnderHole(true);
        //pieChart.setMinAngleForSlices(10);
        pieChart.setHoleRadius(45f);
        pieChart.setTransparentCircleRadius(45f);
        pieChart.invalidate();
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
                openDataPicker(1);
                break;
            case R.id.fromDate:
                openDataPicker(0);
                break;
            case R.id.submitButton:
                mCompnyList.clear();
                mTRMList.clear();
                raisedticketfilterList.clear();
                setEnquiryPagerList(0);
                setAdapterForSalesList();
                tvPage.setText("Showing " + String.valueOf(0) + " to " +
                        String.valueOf(0) + " of " + String.valueOf(0) + "\nEntries");
                try {
                    callReportListApi("0");
                }catch (Exception e){e.printStackTrace();}
                pieChart.setVisibility(View.GONE);
                layList.setVisibility(View.VISIBLE);
                //layIndicators.setVisibility(View.VISIBLE);
                layPagination.setVisibility(View.VISIBLE);
                layFilter.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_grey));
                imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_table));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                break;
            case R.id.imgFilter:
                //add_fab.setVisibility(View.GONE);
                pieChart.setVisibility(View.GONE);
                layList.setVisibility(View.GONE);
                //layIndicators.setVisibility(View.GONE);
                layPagination.setVisibility(View.GONE);
                layFilter.setVisibility(View.VISIBLE);
                submitButton.setVisibility(View.VISIBLE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_grey));
                imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_table_blue));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_blue));
                //callRMApi();
                break;

            case R.id.imgGraph:
                //add_fab.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
                layList.setVisibility(View.GONE);
                //layIndicators.setVisibility(View.GONE);
                layPagination.setVisibility(View.GONE);
                layFilter.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_blue));
                imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_table_blue));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                break;

            case R.id.imgTable:
                //add_fab.setVisibility(View.VISIBLE);
                pieChart.setVisibility(View.GONE);
                layList.setVisibility(View.VISIBLE);
                //layIndicators.setVisibility(View.VISIBLE);
                layPagination.setVisibility(View.VISIBLE);
                layFilter.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_grey));
                imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_table));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                break;

            case R.id.resetButton:
                tvAutoLeaveStatus.setText(""); AutoComTextViewTicketNumber.setText("");
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
             diff = getChangeDateForHisab(tvDateValueFrom.getText().toString(), tvDateValue.getText().toString());
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
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.status_bar_color));

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
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                if(val==1){
                    toDate.setText(sdf.format(myCalendar.getTime()));
                }
                else {
                    fromDate.setText(sdf.format(myCalendar.getTime()));
                }
                callReportListApi("0");
            }

        };

        new DatePickerDialog(mContext, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    public class MyBarDataSet extends BarDataSet {


        public MyBarDataSet(List<BarEntry> yVals, String label) {
            super(yVals, label);
        }

        @Override
        public GradientColor getGradientColor(int index) {
            if (Integer.parseInt(graphModelsList.get(index).getyValue()) < 75) // less than 95 green
                return list.get(0);
            else if (Integer.parseInt(graphModelsList.get(index).getyValue()) > 75
            ) // less than 100 orange
                return list.get(1);
            else if(Integer.parseInt(graphModelsList.get(index).getyValue()) > 75
                    && Integer.parseInt(graphModelsList.get(index).getyValue()) < 150) // less than 100 orange
                return list.get(1);
            else // greater or equal than 100 red
                return list.get(0);
        }


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
        enquiryPageAdapter = new EnquiryPageAdapter(mContext, enquiryPageList, new EnquiryPageAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(EnquiryPagermodel mData, List<EnquiryPagermodel> mDataList) {
                enquiryPageList = mDataList;
                try {
                    pagerClicked = String.valueOf(Integer.parseInt(mData.getPageNo())-1);
                    enquiryPageAdapter.updateList(mDataList);
                }catch (Exception e){e.printStackTrace();}
                try {
                    callReportListApi(String.valueOf(Integer.parseInt(mData.getPageNo()) - 1));
                }catch (Exception e){e.printStackTrace();}
            }
        });
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_TICKET)) {
                            GetTicketResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetTicketResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                raisedticketfilterList.clear();
                                String ct = String.valueOf(responseModel.getResult().getTotalrows())==null
                                        || String.valueOf(responseModel.getResult().getTotalrows()).equals("null")?"0":String.valueOf(responseModel.getResult().getTotalrows());
                                tvTotalCount.setText(ct);
                                try {
                                    if (responseModel.getResult().getRaisedticketfilter() != null && responseModel.getResult().getRaisedticketfilter().size()>0) {
                                        raisedticketfilterList = responseModel.getResult().getRaisedticketfilter();
                                        totalPage = responseModel.getResult().getTotalpages();
                                        if(responseModel.getResult().getNextpage()==1) {
                                            tvPage.setText("Showing " + String.valueOf(responseModel.getResult().getNextpage()) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1) + raisedticketfilterList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalrows()) + "\nEntries"));
                                        }
                                        else {
                                            tvPage.setText("Showing " + String.valueOf(((responseModel.getResult().getNextpage()-1)*7)+1) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1)*7)+ raisedticketfilterList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalrows()) + "\nEntries");
                                        }
                                    }
                                    else{
                                        totalPage = 0;
                                        raisedticketfilterList.clear();
                                    }
                                }catch(Exception e){
                                    totalPage = 0;
                                    raisedticketfilterList.clear();
                                    e.printStackTrace();

                                }
                                setEnquiryPagerList(totalPage);
                                setAdapterForSalesList();
                            }

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_TICKET_NO)) {
                            GetTicketNoResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetTicketNoResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                tickerNo = responseModel.getResult().getTicketno();
                                showReportDialog(0,null);
                            }

                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_RAISE_TICKET)) {
                            RaiseTicketResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), RaiseTicketResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                showSuccessDialogFragment(mContext,responseModel.getResult().getMessage(),
                                        false,mDialogReport);
                                callReportListApi("0");
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_UPDATE_TICKET)) {
                            UpdateTicketResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), UpdateTicketResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                showSuccessDialogFragment(mContext,responseModel.getResult().getMessage(),
                                        false,mDialogReport);
                                callReportListApi("0");

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
                    raisedticketfilterList.clear();
                    setEnquiryPagerList(totalPage);
                    setAdapterForSalesList();
                }
               break;
        }
    }

}