package com.ominfo.crm_solution.ui.enquiry_report;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
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
import androidx.room.Ignore;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
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
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
//https://github.com/PhilJay/MPAndroidChart/wiki/Modifying-the-Viewport

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 * Use the {@link EnquiryReportFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EnquiryReportFragment extends BaseFragment {

    Context mContext;
    EnquiryReportAdapter enquiryReportAdapter;
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
    @BindView(R.id.rvRm)
    RecyclerView rvRm;
    @BindView(R.id.tvAutoEnqStatus)
    AppCompatAutoCompleteTextView textViewDropdownEnqStatus;
    @BindView(R.id.rvEnquiryPager)
    RecyclerView rvEnquiryPager;
    @BindView(R.id.submitButton)
    AppCompatButton submitButton;
    @BindView(R.id.AutoComTextViewENo)
    AppCompatEditText textViewENo;
    @BindView(R.id.imgBack)
    AppCompatImageView imgBack;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    List<DashModel> tagList = new ArrayList<>();
    List<RmListModel> tagRmList = new ArrayList<>();
    List<EnquiryPagermodel> enquiryPageList = new ArrayList<>();
    CompanyTagAdapter addTagAdapter;
    RmTagAdapter addRmTagAdapter;
    EnquiryPageAdapter enquiryPageAdapter;
    BarData barData;
    List<GradientColor> list = new ArrayList<>();
    // variable for our bar data set.
    BarDataSet barDataSet;
    List<EnquiryStatuslist> EnquiryStatusDropdown = new ArrayList<>();
    // array list for storing entries.
    ArrayList barEntriesArrayList;
    //private static final String[] DATA_BAR_GRAPH = new String[6];//{"","09:00",
    private String[] DAYS = new String[100];/*{"C1", "C2", "C3", "C4", "C5", "C6", *//*"C7", "C8", "C9"
            , "C10", "C11", "C12"*//*};*/

    private String[] DAYSY = new String[100];/*{"5", "60", "15", "70", "25",
           "10"*//*, "45","90", "95","50", "55","60", "65"*//*};*/
    int startPos = 0 , endPos = 0;
    List<GetRmlist> RMDropdown = new ArrayList<>();
    List<GetEnquiry> enquiryResultArrayList = new ArrayList<>();
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String pagerClicked = "No";

    @Inject
    ViewModelFactory mViewModelFactory;
    private GetEnquiryViewModel getEnquiryViewModel;
    private GetRmViewModel getRmViewModel;
    private EnquiryStatusViewModel enquiryStatusViewModel;
    private AppDatabase mDb;
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
    GetRmlist selectedRM = new GetRmlist();
    final Calendar myCalendar = Calendar.getInstance();
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;

    @BindView(R.id.imgCompanySort)
    AppCompatImageView imgCompanySort;
    @BindView(R.id.imgEnqSort)
    AppCompatImageView imgEnqSort;
    @BindView(R.id.imgEnqStatus)
    AppCompatImageView imgEnqStatus;
    @BindView(R.id.tvCompanyName)
    AppCompatTextView tvCompanyName;
    @BindView(R.id.tvEnqName)
    AppCompatTextView tvEnqName;
    @BindView(R.id.tvEnqStatus)
    AppCompatTextView tvEnqStatus;
    public EnquiryReportFragment() {
        // Required empty public constructor
    }

    public static EnquiryReportFragment newInstance(String param1, String param2) {
        EnquiryReportFragment fragment = new EnquiryReportFragment();
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
        View view = inflater.inflate(R.layout.activity_enquiry_report, container, false);
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


    private void init() {
        //mDb =BaseApplication.getInstance(mContext).getAppDatabase();
       // iv_emptyLayimage.setImageDrawable(mContext.getDrawable(R.drawable.ic_error_load_grey));
        tv_emptyLayTitle.setText(R.string.scr_lbl_no_data_available);
        layList.setVisibility(View.VISIBLE);
        layPagination.setVisibility(View.VISIBLE);
        //add_fab.setVisibility(View.VISIBLE);
        imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_grey));
        imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_table));
        imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
        layFilter.setVisibility(View.GONE);

        setToolbar();
        setDate();
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
    }

    private void injectAPI() {
        getEnquiryViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetEnquiryViewModel.class);
        getEnquiryViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_ENQUIRY));

        getRmViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetRmViewModel.class);
        getRmViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_RM));

        enquiryStatusViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EnquiryStatusViewModel.class);
        enquiryStatusViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_ENQUIRY_STATUS));
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

    /* Call Api For RM */
    private void callEnquiryStatusApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_enquiry_status);
                RequestBody mRequestBodyTypeStatus = RequestBody.create(MediaType.parse("text/plain"), "1");//loginTable.getEmployeeId());
                enquiryStatusViewModel.hitEnquiryStatusApi(mRequestBodyType, mRequestBodyTypeStatus);
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
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_ENQUIRY_STATUS)) {
                            EnquiryStatusResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), EnquiryStatusResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                EnquiryStatusDropdown.removeAll(EnquiryStatusDropdown);
                                EnquiryStatusDropdown = responseModel.getResult().getStatuslist();
                                setDropdownEnquiryStatus();
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

    //set value to Enquiry status dropdown
    private void setDropdownEnquiryStatus() {
        try {
            int pos = 0;
            if (EnquiryStatusDropdown != null && EnquiryStatusDropdown.size() > 0) {
                String[] mDropdownList = new String[EnquiryStatusDropdown.size()];
                for (int i = 0; i < EnquiryStatusDropdown.size(); i++) {
                    mDropdownList[i] = String.valueOf(EnquiryStatusDropdown.get(i).getStatusText());
                    /*if (mRmId!=null && !mRmId.equals("")) {
                        if (mRmId.equals(RMDropdown.get(i).getEmpId())) {
                            pos = i;
                            AutoComTextViewRM.setText(mDropdownList[pos]);
                            selectedRM = RMDropdown.get(i);
                        }
                    }*/
                }
                //AutoComTextViewVehNo.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getActivity(),
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //AutoComTextViewVehNo.setThreshold(1);
                textViewDropdownEnqStatus.setAdapter(adapter);
                //mSelectedColor = mDropdownList[pos];
                textViewDropdownEnqStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                if(mDataTicket==0) {
                    Intent i = new Intent(getActivity(), View360Activity.class);
                    i.putExtra(Constants.TRANSACTION_ID, "1");
                    startActivity(i);
                    ((Activity) getActivity()).overridePendingTransition(0, 0);
                }
                if(mDataTicket==1){
                    showEnquiryDetailsDialog(getEnquiry);
                }
            }
        });

        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(enquiryReportAdapter);
        final boolean[] check = {false};

    }

    //show Receipt Details popup
    public void showEnquiryDetailsDialog(GetEnquiry getEnquiry) {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_enquiry_details);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton closeButton = mDialog.findViewById(R.id.closeButton);
        AppCompatTextView tvEnquiryNo = mDialog.findViewById(R.id.tvEnquiryNo);
        AppCompatTextView tvCustName = mDialog.findViewById(R.id.tvCustName);
        AppCompatTextView tvMobile = mDialog.findViewById(R.id.tvMobile);
        AppCompatTextView tvPOI = mDialog.findViewById(R.id.tvPOI);
        AppCompatTextView tvSourceValue = mDialog.findViewById(R.id.tvSourceValue);
        AppCompatTextView tvDetailsValue = mDialog.findViewById(R.id.tvDetailsValue);
        AppCompatTextView tvRMValue = mDialog.findViewById(R.id.tvRMValue);

        tvEnquiryNo.setText(getEnquiry.getEnquiryNo());
        tvCustName.setText(getEnquiry.getCustName());
        tvMobile.setText(getEnquiry.getCustMob());
        tvPOI.setText(getEnquiry.getProductOfInterest());
        tvSourceValue.setText(getEnquiry.getEnquirySource());
        tvDetailsValue.setText(Html.fromHtml(getEnquiry.getEnqDescription()));
        tvRMValue.setText(getEnquiry.getEmpName());

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

    private void setDate(){
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(calendar.getTime());
        toDate.setText(date);fromDate.setText(date);
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


    /* Call Api For RM */
    private void callGetEnquiryApi(String pageNo) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_enquiry);
                RequestBody mRequestBodyTypeEnquiry = RequestBody.create(MediaType.parse("text/plain"), "0");//loginTable.getEmployeeId());//loginTable.getEmployeeId());
                RequestBody mRequestBodyTypeComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                String mStringFrmDate = AppUtils.splitsEnquiryDate(fromDate.getText().toString().trim()),
                        mStringToDate = AppUtils.splitsEnquiryDate(toDate.getText().toString().trim());
                RequestBody mRequestBodyTypeFromDate = RequestBody.create(MediaType.parse("text/plain"), mStringFrmDate);
                RequestBody mRequestBodyTypeToDate = RequestBody.create(MediaType.parse("text/plain"), mStringToDate);
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
                RequestBody mRequestBodyTypeENo = RequestBody.create(MediaType.parse("text/plain"), textViewENo.getText().toString().trim());
                ///Gson gson = new Gson();
                ///String bodyInStringFormat = gson.toJson(mCompanyNameList);
                RequestBody mRequestBodyTypeCName = RequestBody.create(MediaType.parse("text/plain"), mCompanyNameList);
                RequestBody mRequestBodyTypeEStatus = RequestBody.create(MediaType.parse("text/plain"), textViewDropdownEnqStatus.getText().toString().trim());
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


    //perform click actions
    @OnClick({R.id.imgGraph,R.id.imgTable,/*,R.id.add_fab,*/R.id.imgFilter,R.id.resetButton
    ,R.id.toDate,R.id.fromDate,R.id.submitButton,R.id.imgCompanySort, R.id.tvCompanyName,
            R.id.tvEnqName, R.id.imgEnqSort,R.id.tvEnqStatus,R.id.imgEnqStatus})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvEnqStatus:
                setSortIconComQuoAmo(2);
                sortforEnqStatus();
                break;
            case R.id.imgEnqStatus:
                setSortIconComQuoAmo(2);
                sortforEnqStatus();
                break;
            case R.id.tvEnqName:
                setSortIconComQuoAmo(1);
                sortforEnqName();
                break;
            case R.id.imgEnqSort:
                setSortIconComQuoAmo(1);
                sortforEnqName();
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
            case R.id.submitButton:
                enquiryResultArrayList.clear();
                setEnquiryPagerList(0);
                setAdapterForEnquiryList();
                tvPage.setText("Showing " + String.valueOf(0) + " to " +
                        String.valueOf(0) + " of " + String.valueOf(0) + "\nEntries");
                try {
                callGetEnquiryApi("0");
            }catch (Exception e){e.printStackTrace();}
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
                callEnquiryStatusApi();
                break;

            case R.id.imgGraph:
                //add_fab.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
                layList.setVisibility(View.GONE);
                layPagination.setVisibility(View.GONE);
                layFilter.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_blue));
                imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_table_blue));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                //callGetEnquiryApi("0");
                break;

            case R.id.imgTable:
                //add_fab.setVisibility(View.VISIBLE);
                pieChart.setVisibility(View.GONE);
                layList.setVisibility(View.VISIBLE);
                layPagination.setVisibility(View.VISIBLE);
                layFilter.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                emptyLayout.setVisibility(View.GONE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_grey));
                imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_table));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                callGetEnquiryApi("0");
                break;

            case R.id.resetButton:
                textViewENo.setText("");
                textViewDropdownEnqStatus.setText("");
                setAddTagList();
                setAddRmTagList();
                callRMApi();
                callEnquiryStatusApi();
                break;
        }
    }
    private void setSortIconComQuoAmo(int res){
        if(res==0){
            imgEnqSort.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgCompanySort.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort_blue));
            imgEnqStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            tvCompanyName.setTextColor(getResources().getColor(R.color.color_main));
            tvEnqStatus.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvEnqName.setTextColor(getResources().getColor(R.color.back_text_colour));
        }
        else if(res==1){
            imgEnqStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgCompanySort.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgEnqSort.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort_blue));
            tvCompanyName.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvEnqName.setTextColor(getResources().getColor(R.color.color_main));
            tvEnqStatus.setTextColor(getResources().getColor(R.color.back_text_colour));
        }
        else{
            imgEnqStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort_blue));
            imgCompanySort.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgEnqSort.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            tvCompanyName.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvEnqName.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvEnqStatus.setTextColor(getResources().getColor(R.color.color_main));
        }
    }
    private void sortforCompany(){
        Collections.sort(enquiryResultArrayList, new Comparator<GetEnquiry>() {
            @Override
            public int compare(GetEnquiry item, GetEnquiry t1) {
                String s1 = item.getCustName();
                String s2 = t1.getCustName();
                return s1.compareToIgnoreCase(s2);
            }
        });
        enquiryReportAdapter.notifyDataSetChanged();
    }
    private void sortforEnqName(){

        Collections.sort(enquiryResultArrayList, new Comparator<GetEnquiry>() {
            @Override
            public int compare(GetEnquiry item, GetEnquiry t1) {
                int returnVal = 0;
                try {
                    String[] s1 = item.getEnquiryNo().split("/");
                    String[] s2 = t1.getEnquiryNo().split("/");
                    returnVal = s1[3].compareToIgnoreCase(s2[3]);
                }catch (Exception e){
                    returnVal = 0;
                }
                return returnVal;
                //return s1.compareToIgnoreCase(s2);
            }
        });
        enquiryReportAdapter.notifyDataSetChanged();
    }
    private void sortforEnqStatus(){
        Collections.sort(enquiryResultArrayList, new Comparator<GetEnquiry>() {
            @Override
            public int compare(GetEnquiry item, GetEnquiry t1) {
                String s1 = item.getStatus();
                String s2 = t1.getStatus();
                return s1.compareToIgnoreCase(s2);
            }
        });
        enquiryReportAdapter.notifyDataSetChanged();
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
                callGetEnquiryApi("0");
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


}