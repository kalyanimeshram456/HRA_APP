package com.ominfo.crm_solution.ui.visit_report;

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
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.ominfo.crm_solution.ui.enquiry_report.model.GetEnquiry;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmResponse;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmlist;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.notifications.NotificationsActivity;
import com.ominfo.crm_solution.ui.sale.adapter.CompanyTagAdapter;
import com.ominfo.crm_solution.ui.sale.model.RmListModel;
import com.ominfo.crm_solution.ui.sales_credit.activity.View360Activity;
import com.ominfo.crm_solution.ui.sales_credit.model.GraphModel;
import com.ominfo.crm_solution.ui.visit_report.adapter.TourTagAdapter;
import com.ominfo.crm_solution.ui.visit_report.adapter.VisitReportAdapter;
import com.ominfo.crm_solution.ui.visit_report.model.GetTourResponse;
import com.ominfo.crm_solution.ui.visit_report.model.GetTourStatuslist;
import com.ominfo.crm_solution.ui.visit_report.model.GetTourViewModel;
import com.ominfo.crm_solution.ui.visit_report.model.GetVisit;
import com.ominfo.crm_solution.ui.visit_report.model.GetVisitRequest;
import com.ominfo.crm_solution.ui.visit_report.model.GetVisitResponse;
import com.ominfo.crm_solution.ui.visit_report.model.GetVisitViewModel;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
 * Use the {@link VisitReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VisitReportFragment extends BaseFragment {

    Context mContext;
    VisitReportAdapter visitReportAdapter;
    @BindView(R.id.rvSalesList)
    RecyclerView rvSalesList;
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
    @BindView(R.id.rvImages)
    RecyclerView rvImages;
    @BindView(R.id.imgBack)
    LinearLayoutCompat imgBack;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
    @BindView(R.id.rvRm)
    RecyclerView rvRm;
    @BindView(R.id.rvTour)
    RecyclerView rvTour;
    @BindView(R.id.submitButton)
    AppCompatButton submitButton;
    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat emptyLayout;
    @BindView(R.id.iv_emptyLayimage)
    AppCompatImageView iv_emptyLayimage;
    @BindView(R.id.AutoComTextViewVisitNo)
    AppCompatEditText AutoComTextViewVisitNo;
    List<DashModel> tagList = new ArrayList<>();
    CompanyTagAdapter addTagAdapter;
    @Inject
    ViewModelFactory mViewModelFactory;
    private GetVisitViewModel getVisitViewModel;
    private AppDatabase mDb;
    BarData barData;
    List<GradientColor> list = new ArrayList<>();
    // variable for our bar data set.
    BarDataSet barDataSet;
    @BindView(R.id.rvEnquiryPager)
    RecyclerView rvEnquiryPager;
    @BindView(R.id.tvTotalCount)
    AppCompatTextView tvTotalCount;
    // array list for storing entries.
    ArrayList barEntriesArrayList;
    //private static final String[] DATA_BAR_GRAPH = new String[6];//{"","09:00",
    private String[] DAYS = new String[100];/*{"C1", "C2", "C3", "C4", "C5", "C6", *//*"C7", "C8", "C9"
            , "C10", "C11", "C12"*//*};*/

    private String[] DAYSY = new String[100];/*{"5", "60", "15", "70", "25",
           "10"*//*, "45","90", "95","50", "55","60", "65"*//*};*/
    int startPos = 0 , endPos = 0;

    List<DashModel> dashboardList = new ArrayList<>();
    List<GraphModel> graphModelsList = new ArrayList<>();
    List<EnquiryPagermodel> enquiryPageList = new ArrayList<>();
    private String pagerClicked = "No";
    EnquiryPageAdapter enquiryPageAdapter;
    @BindView(R.id.nextPage)
    AppCompatImageView nextPage;
    @BindView(R.id.prePage)
    AppCompatImageView prePage;
    final Calendar myCalendar = Calendar.getInstance();
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    List<RmListModel> tagRmList = new ArrayList<>();
    List<RmListModel> tagTourList = new ArrayList<>();
    List<GetRmlist> RMDropdown = new ArrayList<>();
    List<GetTourStatuslist> tourDropdown = new ArrayList<>();
    List<GetVisit> visitResultArrayList = new ArrayList<>();
    RmTagAdapter addRmTagAdapter;
    TourTagAdapter tourTagAdapter;
    private GetRmViewModel getRmViewModel;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    @BindView(R.id.AutoComTextViewResult)
    AppCompatAutoCompleteTextView AutoComTextViewResult;
    @BindView(R.id.AutoComTextViewDis)
    AppCompatAutoCompleteTextView AutoComTextViewDis;
    private GetTourViewModel getTourViewModel;

    @BindView(R.id.imgCompanySort)
    AppCompatImageView imgCompanySort;
    @BindView(R.id.tvCompanyName)
    AppCompatTextView tvCompanyName;
    @BindView(R.id.imgStatus)
    AppCompatImageView imgStatus;
    @BindView(R.id.tvStatus)
    AppCompatTextView tvStatus;
    @BindView(R.id.imgPlace)
    AppCompatImageView imgPlace;
    @BindView(R.id.tvPlace)
    AppCompatTextView tvPlace;

    public VisitReportFragment() {
        // Required empty public constructor
    }

    public static VisitReportFragment newInstance(String param1, String param2) {
        VisitReportFragment fragment = new VisitReportFragment();
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
        View view = inflater.inflate(R.layout.activity_visit_report, container, false);
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
        setDate();
        setDropdownDiscussion();
        setDropdownResult();
        setAdapterForVisitList();
        setVisitPagerList(1);
        callGetVisitApi("0");

        setAddTagList();
        setAddRmTagList();
        setAddTourTagList();
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
        rvTour.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (tagTourList.size() == 1 || tagTourList.size() == 2) {
                    tourTagAdapter.updateList(tagTourList, 1);
                }
                return false;
            }
        });
    }

    private void injectAPI() {
        getVisitViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetVisitViewModel.class);
        getVisitViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_VISIT));

        getTourViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetTourViewModel.class);
        getTourViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_TOUR));

        getRmViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetRmViewModel.class);
        getRmViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_RM));
    }

    //set value to Result dropdown
    private void setDropdownResult() {
        List<String> mResultDropdown = new ArrayList<>();
        mResultDropdown.add("Positive");
        mResultDropdown.add("Negative");
        try {
            int pos = 0;
            if (mResultDropdown != null && mResultDropdown.size() > 0) {
                String[] mDropdownList = new String[mResultDropdown.size()];
                for (int i = 0; i < mResultDropdown.size(); i++) {
                    mDropdownList[i] = String.valueOf(mResultDropdown.get(i));
                }
                //AutoComTextViewVehNo.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mContext,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //AutoComTextViewVehNo.setThreshold(1);
                AutoComTextViewResult.setAdapter(adapter);
                //mSelectedColor = mDropdownList[pos];
                AutoComTextViewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //mSelectedColor = mDropdownList[position];
                        AppUtils.hideKeyBoard(getActivity());
                    }
                });
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //set value to Discussion dropdown
    private void setDropdownDiscussion() {
        List<String> mDiscussionDropdown = new ArrayList<>();
        mDiscussionDropdown.add("Cold Visit");
        mDiscussionDropdown.add("Planed Sales Visit");
        mDiscussionDropdown.add("Complaint Solving");
        mDiscussionDropdown.add("General Visit");
        try {
            int pos = 0;
            if (mDiscussionDropdown != null && mDiscussionDropdown.size() > 0) {
                String[] mDropdownList = new String[mDiscussionDropdown.size()];
                for (int i = 0; i < mDiscussionDropdown.size(); i++) {
                    mDropdownList[i] = String.valueOf(mDiscussionDropdown.get(i));
                }
                //AutoComTextViewVehNo.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mContext,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //AutoComTextViewVehNo.setThreshold(1);
                AutoComTextViewDis.setAdapter(adapter);
                //mSelectedColor = mDropdownList[pos];
                AutoComTextViewDis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //mSelectedColor = mDropdownList[position];
                        AppUtils.hideKeyBoard(getActivity());
                    }
                });
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /* Call Api For Search cust */
    private void callTourApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyTypeAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_tour);
                RequestBody mRequestBodyTypeCompID = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());//loginTable.getCompanyId());
                getTourViewModel.hitGetTourApi(mRequestBodyTypeAction, mRequestBodyTypeCompID);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }


    private void setAdapterForVisitList() {
        try {
            if (visitResultArrayList.size() > 0 && visitResultArrayList != null) {
                rvSalesList.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);
            } else {
                rvSalesList.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.VISIBLE);
            }

            visitReportAdapter = new VisitReportAdapter(mContext, visitResultArrayList, new VisitReportAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(int mDataTicket, GetVisit getEnquiry) {
                    //For not killing pre fragment
                    if (mDataTicket == 0) {
                        Intent i = new Intent(getActivity(), View360Activity.class);
                        i.putExtra(Constants.TRANSACTION_ID, getEnquiry.getCustId());
                        startActivity(i);
                        ((Activity) getActivity()).overridePendingTransition(0, 0);
                    }
                    if (mDataTicket == 1) {
                        showVisitDetailsDialog();//getEnquiry);
                    }
                }
            });

            rvSalesList.setHasFixedSize(true);
            rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            rvSalesList.setAdapter(visitReportAdapter);
            final boolean[] check = {false};
        }catch (Exception e){e.printStackTrace();
            rvSalesList.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);}

    }

    private void setAddRmTagList() {
        tagRmList.clear();
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

    private void setAddTourTagList() {
        tagTourList.clear();
        tagTourList.add(new RmListModel("","1",null));
        if (tagTourList.size() > 0) {
            rvTour.setVisibility(View.VISIBLE);
        } else {
            rvTour.setVisibility(View.GONE);
        }
        tourTagAdapter = new TourTagAdapter(mContext, tagTourList, new TourTagAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(List<RmListModel> mDataTicket) {
                tagTourList =  mDataTicket;
                tourTagAdapter.updateList(tagTourList,0);
                if(tagList.size()>0){
                    int marginInDp40 = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 90, getResources()
                                    .getDisplayMetrics());
                    rvTour.setMinimumHeight(marginInDp40);
                    //setMargins(rvImages, 0, marginInDp40, 0, 0);
                }
            }
        });
        rvTour.setHasFixedSize(true);
        rvTour.setLayoutManager(new GridLayoutManager(mContext, 1));
        rvTour.setItemAnimator(new DefaultItemAnimator());
        rvTour.setAdapter(tourTagAdapter);
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

    private void setDate(){
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());
        toDate.setText(date);fromDate.setText(date);
    }

    private void setVisitPagerList(long pageNo) {
        enquiryPageList.removeAll(enquiryPageList);
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
                    callGetVisitApi(String.valueOf(Integer.parseInt(mData.getPageNo()) - 1));
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


    /* Call Api For RM */
    private void callGetVisitApi(String pageNo) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_visit);
                RequestBody mRequestBodyTypeVisit = RequestBody.create(MediaType.parse("text/plain"), "0");//loginTable.getEmployeeId());//loginTable.getEmployeeId());
                RequestBody mRequestBodyTypeComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                String mStringFrmDate = AppUtils.splitsEnquiryDate(fromDate.getText().toString().trim()),
                        mStringToDate = AppUtils.splitsEnquiryDate(toDate.getText().toString().trim());
                RequestBody mRequestBodyTypeFromDate = RequestBody.create(MediaType.parse("text/plain"), mStringFrmDate);
                RequestBody mRequestBodyTypeToDate = RequestBody.create(MediaType.parse("text/plain"), mStringToDate);
                RequestBody mRequestBodyTypePageNo = RequestBody.create(MediaType.parse("text/plain"), pageNo);//selectedRM.getEmpId());
                RequestBody mRequestBodyTypePageSize = RequestBody.create(MediaType.parse("text/plain"), "7");
                String mCompanyNameList="",mRMList="",mTourList="";
                for(int i=0;i<tagList.size();i++){
                    if(tagList.get(i).getTitle()!=null && !tagList.get(i).getTitle().equals("")) {
                        if (i == 0) {
                            mCompanyNameList = tagList.get(i).getTitle();
                        } else {
                            mCompanyNameList = mCompanyNameList + "~" + tagList.get(i).getTitle();
                        }
                    }
                }
                for(int i=0;i<tagRmList.size();i++){
                    if(tagRmList.get(i).getId()!=null && !tagRmList.get(i).getId().equals("")) {
                        if (i == 0) {
                            mRMList = tagRmList.get(i).getId();
                        } else {
                            mRMList = mRMList + "~" + tagRmList.get(i).getId();
                        }
                    }
                }
                for(int i=0;i<tagTourList.size();i++){
                    if(tagTourList.get(i).getTitle()!=null && !tagTourList.get(i).getTitle().equals("")) {
                        if (i == 0) {
                            mTourList = tagTourList.get(i).getTitle();
                        } else {
                            mTourList = mTourList + "~" + tagTourList.get(i).getTitle();
                        }
                    }
                }
                //RequestBody mRequestBodyTypeENo = RequestBody.create(MediaType.parse("text/plain"), textViewENo.getText().toString().trim());
                ///Gson gson = new Gson();
                ///String bodyInStringFormat = gson.toJson(mCompanyNameList);
                RequestBody mRequestBodyTypeVisitNo = RequestBody.create(MediaType.parse("text/plain"),  AutoComTextViewVisitNo.getText().toString());
                RequestBody mRequestBodyTypeCName = RequestBody.create(MediaType.parse("text/plain"), mCompanyNameList);
                RequestBody mRequestBodyTypeResult = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewResult.getText().toString()/*textViewDropdownEnqStatus.getText().toString().trim()*/);
                RequestBody mRequestBodyTypeTopic = RequestBody.create(MediaType.parse("text/plain"),  AutoComTextViewDis.getText().toString());
                RequestBody mRequestBodyTypeRm = RequestBody.create(MediaType.parse("text/plain"), mRMList);
                RequestBody mRequestBodyTypeTour = RequestBody.create(MediaType.parse("text/plain"), mTourList);
                GetVisitRequest getVisitRequest = new GetVisitRequest();
                getVisitRequest.setAction(mRequestBodyAction);
                getVisitRequest.setVisit(mRequestBodyTypeVisit);
                getVisitRequest.setCompanyId(mRequestBodyTypeComId);
                getVisitRequest.setEmployee(mRequestBodyTypeEmpId);
                getVisitRequest.setFromDate(mRequestBodyTypeFromDate);
                getVisitRequest.setToDate(mRequestBodyTypeToDate);
                getVisitRequest.setPageNumber(mRequestBodyTypePageNo);
                getVisitRequest.setPageSize(mRequestBodyTypePageSize);
                getVisitRequest.setFilterVisitNo(mRequestBodyTypeVisitNo);
                getVisitRequest.setFilterCustomerName(mRequestBodyTypeCName);
                getVisitRequest.setFilterTopic(mRequestBodyTypeTopic);
                getVisitRequest.setFilterVisitResult(mRequestBodyTypeResult);
                getVisitRequest.setFilterRm(mRequestBodyTypeRm);
                getVisitRequest.setFilterTour(mRequestBodyTypeTour);
                getVisitViewModel.hitGetVisitApi(getVisitRequest);
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

    //show Receipt Details popup
    public void showVisitDetailsDialog() {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_visit_details);
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
                RequestBody mRequestBodyTypeImage = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
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
    @OnClick({R.id.imgGraph,R.id.imgTable,/*,R.id.add_fab,*/R.id.imgFilter,R.id.submitButton
    ,R.id.toDate,R.id.fromDate,R.id.resetButton,R.id.tvCompanyName,R.id.imgCompanySort
            ,R.id.tvStatus,R.id.imgStatus ,R.id.tvPlace,R.id.imgPlace})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvStatus:
                setSortIconComQuoAmo(2);
                sortforStatus();
                break;
            case R.id.imgStatus:
                setSortIconComQuoAmo(2);
                sortforStatus();
                break;
            case R.id.tvPlace:
                setSortIconComQuoAmo(1);
                sortforPlace();
                break;
            case R.id.imgPlace:
                setSortIconComQuoAmo(1);
                sortforPlace();
                break;
            case R.id.imgCompanySort:
                setSortIconComQuoAmo(0);
                sortforCompany();
                break;
            case R.id.tvCompanyName:
                setSortIconComQuoAmo(0);
                sortforCompany();
                break;
            case R.id.toDate:
                openDataPicker(1,toDate);
                break;
            case R.id.fromDate:
                openDataPicker(0,fromDate);
                break;
            case R.id.imgFilter:
                //add_fab.setVisibility(View.GONE);
                pieChart.setVisibility(View.GONE);
                layList.setVisibility(View.GONE);
                layPagination.setVisibility(View.GONE);
                layFilter.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);
                submitButton.setVisibility(View.VISIBLE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_grey));
                imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_table_blue));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_blue));
                callRMApi();
                callTourApi();
                break;

            case R.id.imgGraph:
                //add_fab.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
                layList.setVisibility(View.GONE);
                layPagination.setVisibility(View.GONE);
                layFilter.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.GONE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_blue));
                imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_table_blue));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                submitButton.setVisibility(View.GONE);
                break;

            case R.id.imgTable:
                callGetVisitApi("0");
                //add_fab.setVisibility(View.VISIBLE);
                pieChart.setVisibility(View.GONE);
                layList.setVisibility(View.VISIBLE);
                layPagination.setVisibility(View.VISIBLE);
                layFilter.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_grey));
                imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_table));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                callGetVisitApi("0");
                break;

            case R.id.submitButton:
                //add_fab.setVisibility(View.VISIBLE);
                visitResultArrayList.clear();
                setVisitPagerList(0);
                setAdapterForVisitList();
                tvPage.setText("Showing " + String.valueOf(0) + " to " +
                        String.valueOf(0) + " of " + String.valueOf(0) + "\nEntries");
                callGetVisitApi("0");
                pieChart.setVisibility(View.GONE);
                layList.setVisibility(View.VISIBLE);
                layPagination.setVisibility(View.VISIBLE);
                layFilter.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_grey));
                imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_table));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                break;

            case R.id.resetButton:
                AutoComTextViewVisitNo.setText("");
                AutoComTextViewDis.setText(""); AutoComTextViewResult.setText("");
                setAddTagList();
                setAddRmTagList();
                callRMApi();
                setAddTourTagList();
                callTourApi();
                break;
        }
    }
    private void setSortIconComQuoAmo(int res){
        if(res==0){
            imgPlace.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgCompanySort.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort_blue));
            imgStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            tvCompanyName.setTextColor(getResources().getColor(R.color.color_main));
            tvPlace.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvStatus.setTextColor(getResources().getColor(R.color.back_text_colour));
        }
        else if(res==1){
            imgPlace.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort_blue));
            imgCompanySort.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            tvCompanyName.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvPlace.setTextColor(getResources().getColor(R.color.color_main));
            tvStatus.setTextColor(getResources().getColor(R.color.back_text_colour));
        }
        else{
            imgPlace.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgCompanySort.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort_blue));
            tvCompanyName.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvPlace.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvStatus.setTextColor(getResources().getColor(R.color.color_main));
        }
    }
    private void sortforCompany(){
        Collections.sort(visitResultArrayList, new Comparator<GetVisit>() {
            @Override
            public int compare(GetVisit item, GetVisit t1) {
                String s1 = item.getCustName();
                String s2 = t1.getCustName();
                return s1.compareToIgnoreCase(s2);
            }
        });
        visitReportAdapter.notifyDataSetChanged();
    }
    private void sortforPlace(){
        Collections.sort(visitResultArrayList, new Comparator<GetVisit>() {
            @Override
            public int compare(GetVisit item, GetVisit t1) {
                String s1 = item.getPlace();
                String s2 = t1.getPlace();
                return s1.compareToIgnoreCase(s2);
            }
        });
        visitReportAdapter.notifyDataSetChanged();
    }
    private void sortforStatus(){
        Collections.sort(visitResultArrayList, new Comparator<GetVisit>() {
            @Override
            public int compare(GetVisit item, GetVisit t1) {
                String s1 = item.getResult();
                String s2 = t1.getResult();
                return s1.compareToIgnoreCase(s2);
            }
        });
        visitReportAdapter.notifyDataSetChanged();
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
                callGetVisitApi("0");
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_VISIT)) {
                            GetVisitResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetVisitResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                visitResultArrayList.clear();
                                long totalPage = 0;
                                String visitVal = String.valueOf(responseModel.getResult().getTotalvisits());
                                tvTotalCount.setText(visitVal.equals("")||visitVal.equals("null")?"0":visitVal/*+"\n Companies"*/);
                                try {
                                    if (responseModel.getResult().getVisits() != null && responseModel.getResult().getVisits().size()>0) {
                                        visitResultArrayList = responseModel.getResult().getVisits();
                                        totalPage=responseModel.getResult().getTotalpages();
                                        if(responseModel.getResult().getNextpage()==1) {
                                            tvPage.setText("Showing " + String.valueOf(responseModel.getResult().getNextpage()) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1) + visitResultArrayList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalvisits()) + "\nEntries"));
                                        }
                                        else {
                                            tvPage.setText("Showing " + String.valueOf(((responseModel.getResult().getNextpage()-1)*7)+1) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1)*7)+ visitResultArrayList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalvisits()) + "\nEntries");
                                        }
                                    }
                                    else{ visitResultArrayList.clear();
                                        totalPage = 0;}

                                }catch(Exception e){
                                    e.printStackTrace();
                                    visitResultArrayList.clear();
                                    totalPage = 0;
                                }
                                setVisitPagerList(totalPage);
                                setAdapterForVisitList();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
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
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_TOUR)) {
                            GetTourResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetTourResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                tourDropdown.clear();
                                tourDropdown = responseModel.getResult().getStatuslist();
                                ///setDropdownRM();
                                tourTagAdapter.updateRmList(tourDropdown);
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