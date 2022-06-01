package com.ominfo.hra_app.ui.sales_credit.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.sales_credit.activity.View360Activity;
import com.ominfo.hra_app.ui.sales_credit.adapter.CompanyTagAdapter;
import com.ominfo.hra_app.ui.sales_credit.adapter.EnquiryPageAdapter;
import com.ominfo.hra_app.ui.sales_credit.adapter.RmTagAdapter;
import com.ominfo.hra_app.ui.sales_credit.adapter.SalesCreditReportAdapter;
import com.ominfo.hra_app.ui.sales_credit.model.EnquiryPagermodel;
import com.ominfo.hra_app.ui.sales_credit.model.GraphModel;
import com.ominfo.hra_app.ui.sales_credit.model.RmListModel;
import com.ominfo.hra_app.ui.sales_credit.model.SalesCreditReport;
import com.ominfo.hra_app.ui.sales_credit.model.SalesCreditRequest;
import com.ominfo.hra_app.ui.sales_credit.model.SalesCreditResponse;
import com.ominfo.hra_app.ui.sales_credit.model.SalesCreditViewModel;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
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
 * Use the {@link SalesCreditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalesCreditFragment extends BaseFragment {

    Context mContext;
    SalesCreditReportAdapter salesCreditReportAdapter;
    @BindView(R.id.rvSalesList)
    RecyclerView rvSalesList;
    @BindView(R.id.fromDate)
    AppCompatTextView fromDate;
    @BindView(R.id.toDate)
    AppCompatTextView toDate;
    @BindView(R.id.tvPage)
    AppCompatTextView tvPage;
    @BindView(R.id.tvOverdueByDays)
    AppCompatEditText tvOverdueByDays;
    @BindView(R.id.input_textAmount)
    AppCompatEditText inputMinAmount;
    @BindView(R.id.input_textMaxAmount)
    AppCompatEditText inputMaxAmount;
    @BindView(R.id.layList)
    LinearLayoutCompat layList;
    @BindView(R.id.layPagination)
    RelativeLayout layPagination;
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
    @BindView(R.id.submitButton)
    AppCompatButton submitButton;
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
    // variable for our bar data set.
    // array list for storing entries.
    ArrayList barEntriesArrayList;
    //private static final String[] DATA_BAR_GRAPH = new String[6];//{"","09:00",
    private String[] DAYS = new String[100];/*{"C1", "C2", "C3", "C4", "C5", "C6", *//*"C7", "C8", "C9"
            , "C10", "C11", "C12"*//*};*/

    private String[] DAYSY = new String[100];/*{"5", "60", "15", "70", "25",
           "10"*//*, "45","90", "95","50", "55","60", "65"*//*};*/
    int startPos = 0 , endPos = 0;
    List<SalesCreditReport> salesCreditReportList = new ArrayList<>();
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String pagerClicked = "No";
    List<GraphModel> graphModelsList = new ArrayList<>();
    @Inject
    ViewModelFactory mViewModelFactory;
    private SalesCreditViewModel salesCreditViewModel;
    @BindView(R.id.tv_emptyLayTitle)
    AppCompatTextView tv_emptyLayTitle;
    @BindView(R.id.tvTotalCount)
    AppCompatTextView tvTotalCount;
    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat emptyLayout;
    @BindView(R.id.iv_emptyLayimage)
    AppCompatImageView iv_emptyLayimage;
    @BindView(R.id.rvEnquiryPager)
    RecyclerView rvEnquiryPager;
    @BindView(R.id.nextPage)
    AppCompatImageView nextPage;
    @BindView(R.id.prePage)
    AppCompatImageView prePage;
    final Calendar myCalendar = Calendar.getInstance();
    private AppDatabase mDb;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.rvRm)
    RecyclerView rvRm;

    @BindView(R.id.tvCompanyName)
    AppCompatTextView tvCompanyName;
    @BindView(R.id.imgCompanySort)
    AppCompatImageView imgCompanySort;
    @BindView(R.id.tvBalance)
    AppCompatTextView tvBalance;
    @BindView(R.id.imgBalance)
    AppCompatImageView imgBalance;
    @BindView(R.id.tvOverdue)
    AppCompatTextView tvOverdue;
    @BindView(R.id.imgOverdue)
    AppCompatImageView imgOverdue;
    @BindView(R.id.tvLimit)
    AppCompatTextView tvLimit;
    @BindView(R.id.imgLimit)
    AppCompatImageView imgLimit;

    public SalesCreditFragment() {
        // Required empty public constructor
    }

    public static SalesCreditFragment newInstance(String param1, String param2) {
        SalesCreditFragment fragment = new SalesCreditFragment();
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
        View view = inflater.inflate(R.layout.activity_sales_credit, container, false);
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
        imgGraph.setVisibility(View.GONE);
        imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_bar_graph));
        imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
        layFilter.setVisibility(View.GONE);

        setToolbar();
        setEnquiryPagerList(1);
        callGetSalesCreditApi("0");
        setAdapterForSalesCreditList();
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

        setAddTagList();
        rvImages.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(tagList.size()==1||tagList.size()==2){
                    addTagAdapter.updateList(tagList,1);
                }
                return false;
            }
        });
    }

    private void injectAPI() {
        salesCreditViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SalesCreditViewModel.class);
        salesCreditViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_SALES_CREDIT));
 }

    private void setAddRmTagList() {
        tagRmList.removeAll(tagRmList);
        tagRmList.add(new RmListModel("","1",null));
        if (tagRmList.size() > 0) {
            rvRm.setVisibility(View.VISIBLE);
        } else {
            rvRm.setVisibility(View.GONE);
        }

        rvRm.setHasFixedSize(true);
        rvRm.setLayoutManager(new GridLayoutManager(mContext, 1));
        rvRm.setItemAnimator(new DefaultItemAnimator());
        rvRm.setAdapter(addRmTagAdapter);
        final boolean[] check = {false};

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

    private void setAdapterForSalesCreditList() {
        if (salesCreditReportList.size() > 0) {
            rvSalesList.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        } else {
            rvSalesList.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
        }

        salesCreditReportAdapter = new SalesCreditReportAdapter(mContext, salesCreditReportList, new SalesCreditReportAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(int mDataTicket,SalesCreditReport getEnquiry) {
                //For not killing pre fragment
               // if(mDataTicket==0) {
                    Intent i = new Intent(getActivity(), View360Activity.class);
                    i.putExtra(Constants.TRANSACTION_ID, getEnquiry.getCustomerId());
                    startActivity(i);
                    ((Activity) getActivity()).overridePendingTransition(0, 0);

            }
        });

        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(salesCreditReportAdapter);
        final boolean[] check = {false};

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

    //perform click actions
    @OnClick({R.id.imgGraph,R.id.imgTable,R.id.resetButton,R.id.imgFilter,R.id.submitButton
            ,R.id.imgCompanySort, R.id.tvCompanyName, R.id.tvBalance, R.id.imgBalance,
            R.id.tvOverdue,R.id.imgOverdue, R.id.tvLimit,R.id.imgLimit})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvLimit:
                setSortIconComQuoAmo(1);
                sortforLimit();
                break;
            case R.id.imgLimit:
                setSortIconComQuoAmo(1);
                sortforLimit();
                break;
            case R.id.tvBalance:
                setSortIconComQuoAmo(2);
                sortforBalance();
                break;
            case R.id.imgBalance:
                setSortIconComQuoAmo(2);
                sortforBalance();
                break;
            case R.id.tvOverdue:
                setSortIconComQuoAmo(3);
                sortforOverdue();
                break;
            case R.id.imgOverdue:
                setSortIconComQuoAmo(3);
                sortforOverdue();
                break;
            case R.id.imgCompanySort:
                setSortIconComQuoAmo(0);
                sortforCompany();
                break;
            case R.id.tvCompanyName:
                setSortIconComQuoAmo(0);
                sortforCompany();
                break;
            case R.id.imgFilter:
                //add_fab.setVisibility(View.GONE);
                layList.setVisibility(View.GONE);
                layPagination.setVisibility(View.GONE);
                submitButton.setVisibility(View.VISIBLE);
                layFilter.setVisibility(View.VISIBLE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_bar_graph));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_blue));
                //callRMApi();
                break;

            case R.id.imgGraph:
                //add_fab.setVisibility(View.GONE);
                layList.setVisibility(View.GONE);
                layPagination.setVisibility(View.GONE);
                layFilter.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_bar_graph_blue));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                break;

            case R.id.imgTable:
                //add_fab.setVisibility(View.VISIBLE);
                layList.setVisibility(View.VISIBLE);
                layPagination.setVisibility(View.VISIBLE);
                layFilter.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_bar_graph));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                callGetSalesCreditApi("0");
                break;

            case R.id.submitButton:
                //add_fab.setVisibility(View.VISIBLE);
                salesCreditReportList.clear();
                setEnquiryPagerList(0);
                setAdapterForSalesCreditList();
                tvPage.setText("Showing " + String.valueOf(0) + " to " +
                        String.valueOf(0) + " of " + String.valueOf(0) + "\nEntries");
                try {
                    callGetSalesCreditApi("0");
                }catch (Exception e){e.printStackTrace();}
                layList.setVisibility(View.VISIBLE);
                layPagination.setVisibility(View.VISIBLE);
                layFilter.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_bar_graph));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                break;
            case R.id.resetButton:
                inputMinAmount.setText("");
                inputMaxAmount.setText("");
                tvOverdueByDays.setText("");
                setAddTagList();
                setAddRmTagList();
                //callRMApi();
                break;
        }
    }
    private void setSortIconComQuoAmo(int res){
        if(res==0){
            imgLimit.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgCompanySort.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort_blue));
            imgBalance.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgOverdue.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            tvCompanyName.setTextColor(getResources().getColor(R.color.color_main));
            tvLimit.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvOverdue.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvBalance.setTextColor(getResources().getColor(R.color.back_text_colour));
        }
        else if(res==1){
            imgBalance.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgCompanySort.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgLimit.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort_blue));
            imgOverdue.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            tvCompanyName.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvLimit.setTextColor(getResources().getColor(R.color.color_main));
            tvOverdue.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvBalance.setTextColor(getResources().getColor(R.color.back_text_colour));
        }
        else if(res==2){
            imgBalance.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort_blue));
            imgCompanySort.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgLimit.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgOverdue.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            tvCompanyName.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvBalance.setTextColor(getResources().getColor(R.color.color_main));
            tvLimit.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvOverdue.setTextColor(getResources().getColor(R.color.back_text_colour));
        }
        else {
            imgBalance.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgCompanySort.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgLimit.setImageDrawable(getResources().getDrawable(R.drawable.ic_om_sort));
            imgOverdue.setImageDrawable(getResources().getDrawable(R.drawable.ic_sort_blue));
            tvCompanyName.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvBalance.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvLimit.setTextColor(getResources().getColor(R.color.back_text_colour));
            tvOverdue.setTextColor(getResources().getColor(R.color.color_main));
        }
    }
    private void sortforCompany(){
        Collections.sort(salesCreditReportList, new Comparator<SalesCreditReport>() {
            @Override
            public int compare(SalesCreditReport item, SalesCreditReport t1) {
                String s1 = item.getCustomerName();
                String s2 = t1.getCustomerName();
                return s1.compareToIgnoreCase(s2);
            }
        });
        salesCreditReportAdapter.notifyDataSetChanged();
    }
    private void sortforLimit(){

        Collections.sort(salesCreditReportList, new Comparator<SalesCreditReport>() {
            @Override
            public int compare(SalesCreditReport item, SalesCreditReport t1) {
                return Long.compare(Long.valueOf(item.getCreditLimit()), Long.valueOf(t1.getCreditLimit()));
                //return s1.compareToIgnoreCase(s2);
            }
        });
        salesCreditReportAdapter.notifyDataSetChanged();
    }
    private void sortforBalance(){
        Collections.sort(salesCreditReportList, new Comparator<SalesCreditReport>() {
            @Override
            public int compare(SalesCreditReport item, SalesCreditReport t1) {
                return Long.compare(Long.valueOf(item.getBalance()), Long.valueOf(t1.getBalance()));
                //return s1.compareToIgnoreCase(s2);
            }
        });
        salesCreditReportAdapter.notifyDataSetChanged();
    }
    private void sortforOverdue(){
        Collections.sort(salesCreditReportList, new Comparator<SalesCreditReport>() {
            @Override
            public int compare(SalesCreditReport item, SalesCreditReport t1) {
                return Long.compare(Long.valueOf(item.getOverdue()), Long.valueOf(t1.getOverdue()));
                //return s1.compareToIgnoreCase(s2);
            }
        });
        salesCreditReportAdapter.notifyDataSetChanged();
    }


    /* Call Api For Sales credit report */
    private void callGetSalesCreditApi(String pageNo) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_sales_credit);
                RequestBody mRequestBodyTypeIsAdmin = RequestBody.create(MediaType.parse("text/plain"), loginTable.getIsadmin());//loginTable.getEmployeeId());//loginTable.getEmployeeId());
                RequestBody mRequestBodyTypeComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                String mStringFrmDate = AppUtils.splitsEnquiryDate(fromDate.getText().toString().trim()),
                        mStringToDate = AppUtils.splitsEnquiryDate(toDate.getText().toString().trim());
                //RequestBody mRequestBodyTypeFromDate = RequestBody.create(MediaType.parse("text/plain"), mStringFrmDate);
                //RequestBody mRequestBodyTypeToDate = RequestBody.create(MediaType.parse("text/plain"), mStringToDate);
                RequestBody mRequestBodyTypePageNo = RequestBody.create(MediaType.parse("text/plain"), pageNo);//selectedRM.getEmpId());
                RequestBody mRequestBodyTypePageSize = RequestBody.create(MediaType.parse("text/plain"), Constants.MIN_PAG_SIZE);
                String mCompanyNameList="",mRMList="";
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
                //RequestBody mRequestBodyTypeENo = RequestBody.create(MediaType.parse("text/plain"), "");
                ///Gson gson = new Gson();
                ///String bodyInStringFormat = gson.toJson(mCompanyNameList);
                RequestBody mRequestBodyTypeCName = RequestBody.create(MediaType.parse("text/plain"), mCompanyNameList);
              /*  RequestBody mRequestBodyTypeEStatus = RequestBody.create(MediaType.parse("text/plain"), "");
                RequestBody mRequestBodyTypeCloseReason = RequestBody.create(MediaType.parse("text/plain"), "");
              */  RequestBody mRequestBodyTypeRm = RequestBody.create(MediaType.parse("text/plain"), mRMList);

                SalesCreditRequest salesCreditRequest = new SalesCreditRequest();
                salesCreditRequest.setAction(mRequestBodyAction);
                salesCreditRequest.setIsAdmin(mRequestBodyTypeIsAdmin);
                salesCreditRequest.setCompanyId(mRequestBodyTypeComId);
                salesCreditRequest.setEmployeeId(mRequestBodyTypeEmpId);
                salesCreditRequest.setPageNumber(mRequestBodyTypePageNo);
                salesCreditRequest.setPageSize(mRequestBodyTypePageSize);
                salesCreditRequest.setFilterCustomerName(mRequestBodyTypeCName);
                salesCreditRequest.setFilterRm(mRequestBodyTypeRm);

                salesCreditViewModel.hitGetSalesCreditApi(salesCreditRequest);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
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
                ((BaseActivity)getActivity()).dismissSmallProgressBar(mProgressBarHolder);
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_SALES_CREDIT)) {
                            SalesCreditResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), SalesCreditResponse.class);
                            if (responseModel != null/* && responseModel.getResult().getStatus().equals("success")*/) {
                                long totalPage = 0;
                                tvTotalCount.setText(getString(R.string.scr_lbl_rs)+String.valueOf(responseModel.getResult().getTotalenquiries()));
                                try {
                                    if (responseModel.getResult().getReport() != null && responseModel.getResult().getReport().size()>0) {
                                        salesCreditReportList.clear();
                                        salesCreditReportList = responseModel.getResult().getReport();
                                        totalPage=responseModel.getResult().getTotalpages();

                                        if(responseModel.getResult().getNextpage()==1) {
                                            tvPage.setText("Showing " + String.valueOf(responseModel.getResult().getNextpage()) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1) + salesCreditReportList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalenquiries()) + "\nEntries"));
                                        }
                                        else {
                                            tvPage.setText("Showing " + String.valueOf(((responseModel.getResult().getNextpage()-1)*4)+1) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1)*4)+ salesCreditReportList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalenquiries()) + "\nEntries");
                                        }
                                    }
                                    else{
                                        totalPage=0;
                                        salesCreditReportList.clear();
                                    }

                                }catch(Exception e){
                                    e.printStackTrace();
                                    totalPage=0;
                                    salesCreditReportList.clear();
                                }
                                setEnquiryPagerList(totalPage);
                                setAdapterForSalesCreditList();
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