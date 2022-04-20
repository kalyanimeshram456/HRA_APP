package com.ominfo.crm_solution.ui.top_customer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
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
import com.ominfo.crm_solution.ui.enquiry_report.adapter.EnquiryReportAdapter;
import com.ominfo.crm_solution.ui.enquiry_report.adapter.RmTagAdapter;
import com.ominfo.crm_solution.ui.enquiry_report.model.EnquiryPagermodel;
import com.ominfo.crm_solution.ui.enquiry_report.model.EnquiryStatusResponse;
import com.ominfo.crm_solution.ui.enquiry_report.model.EnquiryStatusViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.EnquiryStatuslist;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetEnquiry;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetEnquiryRequest;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetEnquiryResponse;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetEnquiryViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmResponse;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmlist;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.notifications.NotificationsActivity;
import com.ominfo.crm_solution.ui.sale.adapter.CompanyTagAdapter;
import com.ominfo.crm_solution.ui.sale.model.RmListModel;
import com.ominfo.crm_solution.ui.sales_credit.activity.View360Activity;
import com.ominfo.crm_solution.ui.sales_credit.adapter.SalesCreditAdapter;
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
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 * Use the {@link TopCustomerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopCustomerFragment extends BaseFragment {

    Context mContext;
    EnquiryReportAdapter enquiryReportAdapter;
    @BindView(R.id.rvSalesList)
    RecyclerView rvSalesList;
    /*@BindView(R.id.fromDate)
    AppCompatTextView fromDate;
    @BindView(R.id.toDate)
    AppCompatTextView toDate;*/
    @BindView(R.id.tvPage)
    AppCompatTextView tvPage;
    @BindView(R.id.layList)
    LinearLayoutCompat layList;
    @BindView(R.id.layPagination)
    RelativeLayout layPagination;
    @BindView(R.id.idBarChart)
    PieChart pieChart;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    @BindView(R.id.imgTable)
    AppCompatImageView imgTable;
    @BindView(R.id.imgGraph)
    AppCompatImageView imgGraph;
    @BindView(R.id.imgFilter)
    AppCompatImageView imgFilter;
    @BindView(R.id.layFilter)
    LinearLayoutCompat layFilter;
    @BindView(R.id.rvImages)
    RecyclerView rvImages;
    @BindView(R.id.imgBack)
    AppCompatImageView imgBack;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
    List<DashModel> tagList = new ArrayList<>();
    CompanyTagAdapter addTagAdapter;
    @BindView(R.id.submitButton)
    AppCompatButton submitButton;
    @Inject
    ViewModelFactory mViewModelFactory;
    private GetEnquiryViewModel getEnquiryViewModel;
    private GetRmViewModel getRmViewModel;

    BarData barData;
    List<GradientColor> list = new ArrayList<>();
    // variable for our bar data set.
    BarDataSet barDataSet;

    // array list for storing entries.
    ArrayList barEntriesArrayList;
    //private static final String[] DATA_BAR_GRAPH = new String[6];//{"","09:00",
    private String[] DAYS = new String[100];/*{"C1", "C2", "C3", "C4", "C5", "C6", *//*"C7", "C8", "C9"
            , "C10", "C11", "C12"*//*};*/

    private String[] DAYSY = new String[100];/*{"5", "60", "15", "70", "25",
           "10"*//*, "45","90", "95","50", "55","60", "65"*//*};*/
    int startPos = 0 , endPos = 0;
    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat emptyLayout;
    @BindView(R.id.iv_emptyLayimage)
    AppCompatImageView iv_emptyLayimage;
    @BindView(R.id.nextPage)
    AppCompatImageView nextPage;
    @BindView(R.id.prePage)
    AppCompatImageView prePage;
    @BindView(R.id.tv_emptyLayTitle)
    AppCompatTextView tv_emptyLayTitle;
    @BindView(R.id.tvTotalCount)
    AppCompatTextView tvTotalCount;
    @BindView(R.id.rvEnquiryPager)
    RecyclerView rvEnquiryPager;
    @BindView(R.id.inputInvMinAmount)
    AppCompatEditText inputInvMinAmount;
    @BindView(R.id.inputInvMaxAmount)
    AppCompatEditText inputInvMaxAmount;
    @BindView(R.id.inputMaxSaleAmount)
    AppCompatEditText inputMaxSaleAmount;
    @BindView(R.id.inputMinSaleAmount)
    AppCompatEditText inputMinSaleAmount;
    List<RmListModel> tagRmList = new ArrayList<>();
    List<EnquiryPagermodel> enquiryPageList = new ArrayList<>();
    RmTagAdapter addRmTagAdapter;
    EnquiryPageAdapter enquiryPageAdapter;
    List<GetRmlist> RMDropdown = new ArrayList<>();
    List<GetEnquiry> enquiryResultArrayList = new ArrayList<>();
    GetRmlist selectedRM = new GetRmlist();
    final Calendar myCalendar = Calendar.getInstance();
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    List<DashModel> dashboardList = new ArrayList<>();
    List<GraphModel> graphModelsList = new ArrayList<>();
    private AppDatabase mDb;
    @BindView(R.id.rvRm)
    RecyclerView rvRm;
    private String pagerClicked = "No";
    public TopCustomerFragment() {
        // Required empty public constructor
    }

    public static TopCustomerFragment newInstance(String param1, String param2) {
        TopCustomerFragment fragment = new TopCustomerFragment();
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
        View view = inflater.inflate(R.layout.activity_top_customers, container, false);
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
        //fromDate.setPaintFlags(fromDate.getPaintFlags() |  Paint.UNDERLINE_TEXT_FLAG);
        //toDate.setPaintFlags(toDate.getPaintFlags() |  Paint.UNDERLINE_TEXT_FLAG);
    }


    private void init(){
        //mDb =BaseApplication.getInstance(mContext).getAppDatabase();
        //requestPermission();
        layList.setVisibility(View.VISIBLE);
        layPagination.setVisibility(View.VISIBLE);
        imgGraph.setVisibility(View.GONE);
        imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_grey));
        imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_table));
        imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
        layFilter.setVisibility(View.GONE);

        setToolbar();
        setEnquiryPagerList(1);
        callGetEnquiryApi("0");
        setAdapterForEnquiryList();
        setAddTagList();
        setAddRmTagList();
        rvImages.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (tagList.size() == 1 || tagList.size() == 2) {
                    addTagAdapter.updateList(tagList, 1);
                }
                return false;
            }
        });
        rvRm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (tagRmList.size() == 1 || tagRmList.size() == 2) {
                    addRmTagAdapter.updateList(tagRmList, 1);
                }
                return false;
            }
        });
        graphModelsList.removeAll(dashboardList);
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

    }

    private void injectAPI() {
        getEnquiryViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetEnquiryViewModel.class);
        getEnquiryViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_ENQUIRY));

        getRmViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetRmViewModel.class);
        getRmViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_RM));
  }


    /* Call Api For RM */
    private void callGetEnquiryApi(String pageNo) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_enquiry);
                RequestBody mRequestBodyTypeEnquiry = RequestBody.create(MediaType.parse("text/plain"), "0");//loginTable.getEmployeeId());//loginTable.getEmployeeId());
                RequestBody mRequestBodyTypeComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
               /* String mStringFrmDate = AppUtils.splitsEnquiryDate(fromDate.getText().toString().trim()),
                        mStringToDate = AppUtils.splitsEnquiryDate(toDate.getText().toString().trim());*/
                RequestBody mRequestBodyTypeFromDate = RequestBody.create(MediaType.parse("text/plain"), "2022-12-02");
                RequestBody mRequestBodyTypeToDate = RequestBody.create(MediaType.parse("text/plain"), "2022-12-02");
                RequestBody mRequestBodyTypePageNo = RequestBody.create(MediaType.parse("text/plain"), pageNo);//selectedRM.getEmpId());
                RequestBody mRequestBodyTypePageSize = RequestBody.create(MediaType.parse("text/plain"), Constants.PAG_SIZE);
                String mCompanyNameList="",mRMList="";
                for(int i=0;i<tagList.size();i++){
                    if(i==0){
                        mCompanyNameList = tagList.get(i).getTitle();
                    }
                    else {
                        mCompanyNameList = mCompanyNameList+"~"+tagList.get(i).getTitle();
                    }
                }
                for(int i=0;i<tagRmList.size();i++){
                    if(i==0){
                        mRMList = tagRmList.get(i).getTitle();
                    }
                    else {
                        mRMList = mRMList+"~"+tagRmList.get(i).getTitle();
                    }
                }
                RequestBody mRequestBodyTypeENo = RequestBody.create(MediaType.parse("text/plain"), "");
                ///Gson gson = new Gson();
                ///String bodyInStringFormat = gson.toJson(mCompanyNameList);
                RequestBody mRequestBodyTypeCName = RequestBody.create(MediaType.parse("text/plain"), mCompanyNameList);
                RequestBody mRequestBodyTypeEStatus = RequestBody.create(MediaType.parse("text/plain"), "");
                RequestBody mRequestBodyTypeCloseReason = RequestBody.create(MediaType.parse("text/plain"), "");
                RequestBody mRequestBodyTypeRm = RequestBody.create(MediaType.parse("text/plain"), mRMList);

                GetEnquiryRequest getEnquiryRequest = new GetEnquiryRequest();
                getEnquiryRequest.setAction(mRequestBodyAction);
                getEnquiryRequest.setEnquiry(mRequestBodyTypeEnquiry);
                getEnquiryRequest.setCompanyId(mRequestBodyTypeComId);
                getEnquiryRequest.setEmployee(mRequestBodyTypeEmpId);
                getEnquiryRequest.setFromDate(mRequestBodyTypeFromDate);
                getEnquiryRequest.setToDate(mRequestBodyTypeToDate);
                getEnquiryRequest.setPageNumber(mRequestBodyTypePageNo);
                getEnquiryRequest.setPageSize(mRequestBodyTypePageSize);
                getEnquiryRequest.setFilterEnquiryNo(mRequestBodyTypeENo);
                getEnquiryRequest.setFilterCustomerName(mRequestBodyTypeCName);
                getEnquiryRequest.setFilterEnquiryStatus(mRequestBodyTypeEStatus);
                getEnquiryRequest.setFilterCloseReason(mRequestBodyTypeCloseReason);
                getEnquiryRequest.setFilterRm(mRequestBodyTypeRm);

                getEnquiryViewModel.hitGetEnquiryApi(getEnquiryRequest);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }


    private void setAddTagList() {
        tagList.removeAll(tagList);
        tagList.add(new DashModel("","1",null));
        if (tagList.size() > 0) {
            rvImages.setVisibility(View.VISIBLE);
        } else {
            rvImages.setVisibility(View.GONE);
        }
        addTagAdapter = new CompanyTagAdapter(mContext, tagList, new CompanyTagAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(List<DashModel> mDataTicket) {
                tagList =  mDataTicket;
                addTagAdapter.updateList(tagList,0);
                if(tagList.size()>2){
                    int marginInDp40 = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 90, getResources()
                                    .getDisplayMetrics());
                    rvImages.setMinimumHeight(marginInDp40);
                    //setMargins(rvImages, 0, marginInDp40, 0, 0);
                }
            }
        });
        rvImages.setHasFixedSize(true);
        rvImages.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvImages.setItemAnimator(new DefaultItemAnimator());
        rvImages.setAdapter(addTagAdapter);
        final boolean[] check = {false};

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

/*
    private void getGraph() {
        // initializing variable for bar chart.

        barChart.getDescription().setEnabled(false);
        barChart.getDescription().setTextAlign(Paint.Align.LEFT);
        barChart.setDrawValueAboveBar(true);
        barChart.animateY(1000);
        barChart.getLegend().setEnabled(false);

        // calling method to get bar entries.
        BarData data = getBarEntries();

        // creating a new bar data set.
        barDataSet = new BarDataSet(barEntriesArrayList, "");
        // creating a new bar data and
        // passing our bar data set.
        barData = new BarData(barDataSet);

        // below line is to set data
        // to our bar chart.

        barChart.setData(data);

        data.setBarWidth(0.5f);

        // adding color to our bar data set.
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // setting text color.
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        XAxis xAxis = barChart.getXAxis();
        //set labels des to bottom
        xAxis.setLabelCount(DAYSY.length, false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setDrawGridLines(false);
        //set x axis label values
        xAxis.setLabelRotationAngle(-70);
        try {
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    try {
                        return DAYS[(int) Math.floor(value)];
                    }catch (Exception e){
                        return "";
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        YAxis axisLeft = barChart.getAxisLeft();
        //axisLeft.setGranularity(0.6f);
        axisLeft.setAxisMinimum((int) Math.floor(0));
        axisLeft.setAxisMaximum((int) Math.floor(100));
        ArrayList<String> yAxisVals = new ArrayList<>();
        for(int i=0;i<graphModelsList.size();i++){
            yAxisVals.add(graphModelsList.get(i).getyValue());
        }
        axisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "₹"+(int)value;
            }
        });
       */
/* barChart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return "₹"+(int)value;
            }
    });*//*

        //barChart.getAxisLeft().setValueFormatter(new IndexAxisValueFormatter(graphModelsList));
        // left y-axis
        barChart.getAxisLeft().setTextColor(ContextCompat.getColor(mContext, R.color.deep_red));
        //customize - y axis rows numbers
        axisLeft.setLabelCount(5, true);
        axisLeft.setDrawGridLines(true);
        //set right side
        YAxis axisRight = barChart.getAxisRight();
        //axisRight.setGranularity(0.6f);
        axisRight.setAxisMinimum(0);
        axisRight.setAxisMaximum(0);
        axisRight.setLabelCount(0, false);
        axisRight.setDrawGridLines(false);
        axisRight.setAxisMaximum(0);
        barChart.setDrawValueAboveBar(true);
        barChart.getXAxis().setGranularity(1);
        barChart.getXAxis().setGranularityEnabled(true);
        barChart.getDescription().setEnabled(false);
        //barChart.fitScreen();
        barChart.zoomOut();
        //barChart.zoomIn();
        //barChart.fitScreen();
        //barChart.setFitBars(true);

        barChart.setVisibleXRangeMaximum(6);
        barChart.moveViewToX(0);
        barChart.setVisibleXRangeMaximum(12);
        barChart.invalidate();
    }
*/


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
        /*set1.setGradientColor(new int[]{new GradientColor(Color.parseColor(Yellow),
                Color.parseColor(dark_Red)),
                new GradientColor(Color.parseColor(Yellow),
                        Color.parseColor(dark_Red)),
                        new GradientColor(Color.parseColor(Yellow),
                                Color.parseColor(dark_Red))});*/
        //ArrayList<BarDataSet> dataSets = new ArrayList<>();
        //dataSets.add(set1);


       // BarData data = new BarData(xVals, dataSets);

        //set1.setColor(getResources().getColor(R.color.deep_yellow));
        set1.setDrawValues(false);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();

        set1.setHighlightEnabled(false);
        set1.setDrawValues(true);

        dataSets.add(set1);
        // adding color to our bar data set.
        BarData data = new BarData(dataSets);
        return data;
    }

    private void setAdapterForEnquiryList() {
        if (enquiryResultArrayList.size() > 0) {
            rvSalesList.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        } else {
            rvSalesList.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }

        enquiryReportAdapter = new EnquiryReportAdapter(mContext, enquiryResultArrayList, new EnquiryReportAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(int mDataTicket,GetEnquiry getEnquiry) {
                //For not killing pre fragment
                showReceiptDetailsDialog();
            }
        });

        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(enquiryReportAdapter);
        final boolean[] check = {false};

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
    /* Call Api For RM */
    private void callRMApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_rm);
                RequestBody mRequestBodyTypeImage = RequestBody.create(MediaType.parse("text/plain"), "0");//loginTable.getEmployeeId());
                RequestBody mRequestBodyTypeImage1 = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                getRmViewModel.hitGetRmApi(mRequestBodyType, mRequestBodyTypeImage, mRequestBodyTypeImage1);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }


    //perform click actions
    @OnClick({R.id.imgGraph,R.id.imgTable,R.id.resetButton,R.id.imgFilter,R.id.submitButton})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgFilter:
                //add_fab.setVisibility(View.GONE);
                pieChart.setVisibility(View.GONE);
                layList.setVisibility(View.GONE);
                layPagination.setVisibility(View.GONE);
                layFilter.setVisibility(View.VISIBLE);
                submitButton.setVisibility(View.VISIBLE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_grey));
                imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_table_blue));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_blue));
                callRMApi();
                break;

            case R.id.imgGraph:
                //add_fab.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
                layList.setVisibility(View.GONE);
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
                layPagination.setVisibility(View.VISIBLE);
                layFilter.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_grey));
                imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_table));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                break;

            case R.id.submitButton:
                enquiryResultArrayList.clear();
                setEnquiryPagerList(0);
                setAdapterForEnquiryList();
                tvPage.setText("Showing " + String.valueOf(0) + " to " +
                        String.valueOf(0) + " of " + String.valueOf(0) + "\nEntries");
                try {
                    callGetEnquiryApi("0");
                }catch (Exception e){e.printStackTrace();}
                //add_fab.setVisibility(View.VISIBLE);
                pieChart.setVisibility(View.GONE);
                layList.setVisibility(View.VISIBLE);
                layPagination.setVisibility(View.VISIBLE);
                layFilter.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_grey));
                imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_table));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                break;
            case R.id.resetButton:
                inputInvMaxAmount.setText("");
                inputInvMinAmount.setText("");
                inputMinSaleAmount.setText("");
                inputMaxSaleAmount.setText("");
                setAddTagList();
                setAddRmTagList();
                callRMApi();
                break;
        }
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
                    datePickerField.setText("-"+sdf.format(myCalendar.getTime()));
                }
                else {
                    datePickerField.setText(sdf.format(myCalendar.getTime()));
                }
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



    /*private void injectAPI() {
        mGetVehicleViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetVehicleViewModel.class);
        mGetVehicleViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_GET_VEHICLE));
    }*/

  /*  *//* Call Api For Vehicle List *//*
    private void callVehicleListApi(String fromDate,String toDate) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            GetVehicleListRequest mRequest = new GetVehicleListRequest();
            mRequest.setUserkey(mUserKey);//mUserKey); //6b07b768-926c-49b6-ac1c-89a9d03d4c3b
            mRequest.setFromDate(fromDate);
            mRequest.setToDate(toDate);
            Gson gson = new Gson();
            String bodyInStringFormat = gson.toJson(mRequest);
            mGetVehicleViewModel.hitGetVehicleApi(bodyInStringFormat);
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }*/



  /*  private void setAdapterForVehicleList() {
        if (vehicleModelList.size() > 0) {
            mLrNumberAdapter = new LrNumberAdapter(mContext, vehicleModelList, new LrNumberAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(GetVehicleListResult mDataTicket) {
                    Intent intent = new Intent(mContext,AddLrActivity.class);
                    intent.putExtra(Constants.TRANSACTION_ID, mDataTicket.getTransactionID());
                    intent.putExtra(Constants.FROM_SCREEN, Constants.LIST);
                    startActivity(intent);
                }
            });
            mRecylerViewLrNumber.setHasFixedSize(true);
            mRecylerViewLrNumber.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            mRecylerViewLrNumber.setAdapter(mLrNumberAdapter);
            mRecylerViewLrNumber.setVisibility(View.VISIBLE);
            linearLayoutEmptyActivity.setVisibility(View.GONE);
            imgEmptyImage.setBackground(getResources().getDrawable(R.drawable.ic_error_load));
            tvEmptyLayTitle.setText(getString(R.string.scr_lbl_data_loading));
        } else {
            mRecylerViewLrNumber.setVisibility(View.GONE);
            linearLayoutEmptyActivity.setVisibility(View.VISIBLE);
            imgEmptyImage.setBackground(getResources().getDrawable(R.drawable.ic_error_load));
            tvEmptyLayTitle.setText(R.string.scr_lbl_no_data_available);
        }
    }*/

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }


  /*  //set date picker view
    private void openDataPicker(AppCompatTextView datePickerField,int mFrom) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat="";
                if(mFrom==0) {
                    myFormat = "dd MMM yyyy"; //In which you need put here
                }
                else{
                    myFormat = "dd/MM/yyyy"; //In which you need put here
                }
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                datePickerField.setText(sdf.format(myCalendar.getTime()));
            }

        };

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    //show truck details popup
    public void showTruckDetailsDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_truck_details);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.detailsButton);
        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);
        RelativeLayout relRC = mDialog.findViewById(R.id.relRC);
        RelativeLayout relPUC = mDialog.findViewById(R.id.relPUC);
        RelativeLayout relIss = mDialog.findViewById(R.id.relIss);

        relRC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showFullImageDialog();
            }
        });
        relPUC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showFullImageDialog();
            }
        });
        relIss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showFullImageDialog();
            }
        });
        okayButton.setOnClickListener(new View.OnClickListener() {
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

    //show truck details popup
    public void showFullImageDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_doc_full_view);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.detailsButton);

        okayButton.setOnClickListener(new View.OnClickListener() {
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
    }*/


    /*//request camera and storage permission
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mContext.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {

                requestPermissions(new String[]
                                { Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                },
                        1000);

            } else {
                //createFolder();
            }
        } else {
            //createFolder();
        }
    }
*/

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
    private void setAddRmTagList() {
        tagRmList.removeAll(tagRmList);
        tagRmList.add(new RmListModel("","1",null));
        if (tagRmList.size() > 0) {
            rvRm.setVisibility(View.VISIBLE);
        } else {
            rvRm.setVisibility(View.GONE);
        }
        addRmTagAdapter = new RmTagAdapter(mContext, tagRmList, new RmTagAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(List<RmListModel> mDataTicket) {
                tagRmList =  mDataTicket;
                addRmTagAdapter.updateList(tagRmList,0);
                if(tagList.size()>0){
                    int marginInDp40 = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 90, getResources()
                                    .getDisplayMetrics());
                    rvRm.setMinimumHeight(marginInDp40);
                    //setMargins(rvImages, 0, marginInDp40, 0, 0);
                }
            }
        });
        rvRm.setHasFixedSize(true);
        rvRm.setLayoutManager(new GridLayoutManager(mContext, 1));
        rvRm.setItemAnimator(new DefaultItemAnimator());
        rvRm.setAdapter(addRmTagAdapter);
        final boolean[] check = {false};

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
            public void onItemClick(EnquiryPagermodel mData,List<EnquiryPagermodel> mDataList) {
                enquiryPageList = mDataList;
                try {
                    pagerClicked = String.valueOf(Integer.parseInt(mData.getPageNo())-1);
                    enquiryPageAdapter.updateList(mDataList);
                }catch (Exception e){e.printStackTrace();}
                try {
                    callGetEnquiryApi(String.valueOf(Integer.parseInt(mData.getPageNo()) - 1));
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
                ((BaseActivity)getActivity()).dismissSmallProgressBar(mProgressBarHolder);
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_ENQUIRY)) {
                            GetEnquiryResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetEnquiryResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                long totalPage = 0;
                                tvTotalCount.setText(String.valueOf(responseModel.getResult().getTotalEnquiries()));
                                try {
                                    if (responseModel.getResult().getEnquiries() != null && responseModel.getResult().getEnquiries().size()>0) {
                                        enquiryResultArrayList.clear();
                                        enquiryResultArrayList = responseModel.getResult().getEnquiries();
                                        totalPage=responseModel.getResult().getTotalpages();

                                        if(responseModel.getResult().getNextpage()==1) {
                                            tvPage.setText("Showing " + String.valueOf(responseModel.getResult().getNextpage()) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1) + enquiryResultArrayList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalEnquiries()) + "\nEntries"));
                                        }
                                        else {
                                            tvPage.setText("Showing " + String.valueOf(((responseModel.getResult().getNextpage()-1)*7)+1) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1)*7)+enquiryResultArrayList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalEnquiries()) + "\nEntries");
                                        }
                                    }
                                    else{
                                        totalPage=0;
                                        enquiryResultArrayList.clear();
                                    }

                                }catch(Exception e){
                                    e.printStackTrace();
                                    totalPage=0;
                                    enquiryResultArrayList.clear();
                                }
                                setEnquiryPagerList(totalPage);
                                setAdapterForEnquiryList();
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_RM)) {
                            GetRmResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetRmResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                RMDropdown.removeAll(RMDropdown);
                                RMDropdown = responseModel.getResult().getRmlist();
                                ///setDropdownRM();
                                addRmTagAdapter.updateRmList(RMDropdown);
                            } else {
                                LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}
                }
                break;
            case ERROR:
                ((BaseActivity)getActivity()).dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }


}