package com.ominfo.crm_solution.ui.product;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.ominfo.crm_solution.ui.enquiry_report.adapter.RmTagAdapter;
import com.ominfo.crm_solution.ui.enquiry_report.model.EnquiryPagermodel;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmResponse;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmlist;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.notifications.NotificationsActivity;
import com.ominfo.crm_solution.ui.product.adapter.ProductAdapter;
import com.ominfo.crm_solution.ui.product.model.Product;
import com.ominfo.crm_solution.ui.product.model.ProductRequest;
import com.ominfo.crm_solution.ui.product.model.ProductResponse;
import com.ominfo.crm_solution.ui.product.model.ProductResult;
import com.ominfo.crm_solution.ui.product.model.ProductViewModel;
import com.ominfo.crm_solution.ui.sale.adapter.CompanyTagAdapter;
import com.ominfo.crm_solution.ui.sale.model.RmListModel;
import com.ominfo.crm_solution.ui.sales_credit.activity.View360Activity;
import com.ominfo.crm_solution.ui.sales_credit.model.GraphModel;
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
 * Use the {@link ProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductFragment extends BaseFragment {

    Context mContext;
    ProductAdapter productAdapter;
    @BindView(R.id.rvSalesList)
    RecyclerView rvSalesList;
   /* @BindView(R.id.fromDate)
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
    @BindView(R.id.imgTable)
    AppCompatImageView imgTable;
    @BindView(R.id.imgGraph)
    AppCompatImageView imgGraph;
    @BindView(R.id.imgFilter)
    AppCompatImageView imgFilter;
   /* @BindView(R.id.layFilter)
    LinearLayoutCompat layFilter;*/
    @BindView(R.id.imgBack)
    AppCompatImageView imgBack;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
    private AppDatabase mDb;
    @BindView(R.id.layFilter)
    LinearLayoutCompat layFilter;
    @BindView(R.id.tvProductCode)
    AppCompatAutoCompleteTextView tvProductCode;
    @BindView(R.id.tvProdName)
    AppCompatAutoCompleteTextView tvProdName;

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

    List<Product> productResultList = new ArrayList<>();
    List<GraphModel> graphModelsList = new ArrayList<>();
    @Inject
    ViewModelFactory mViewModelFactory;
    private ProductViewModel productViewModel;
    private GetRmViewModel getRmViewModel;
    final Calendar myCalendar = Calendar.getInstance();
    List<DashModel> tagList = new ArrayList<>();
    CompanyTagAdapter addTagAdapter;
    List<String> mCompnyList = new ArrayList<>();
    //test
    List<String> mTRMList = new ArrayList<>();
    @BindView(R.id.rvEnquiryPager)
    RecyclerView rvEnquiryPager;
    private String pagerClicked = "No";
 /*   @BindView(R.id.tvTotalCount)
    AppCompatTextView tvTotalCount;*/
    List<EnquiryPagermodel> enquiryPageList = new ArrayList<>();
    EnquiryPageAdapter enquiryPageAdapter;
    List<RmListModel> tagRmList = new ArrayList<>();
   /* @BindView(R.id.rvRm)
    RecyclerView rvRm;*/
    RmTagAdapter addRmTagAdapter;
    @BindView(R.id.nextPage)
    AppCompatImageView nextPage;
    @BindView(R.id.prePage)
    AppCompatImageView prePage;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    @BindView(R.id.submitButton)
    AppCompatButton submitButton;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
  /*  @BindView(R.id.tvQutationNo)
    AppCompatEditText tvQutationNo;
    @BindView(R.id.tvMinAmount)
    AppCompatEditText tvMinAmount;
    @BindView(R.id.tvMaxAmount)
    AppCompatEditText tvMaxAmount;*/
    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat emptyLayout;
    List<GetRmlist> RMDropdown = new ArrayList<>();
    String date = "";
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance(String param1, String param2) {
        ProductFragment fragment = new ProductFragment();
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
        View view = inflater.inflate(R.layout.activity_product_grid, container, false);
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

    private void injectAPI() {
        productViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ProductViewModel.class);
        productViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_PRODUCT));

        getRmViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetRmViewModel.class);
        getRmViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_RM));
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
        setEnquiryPagerList(1);
        setAdapterForProductList();
        callProductApi("0");

        graphModelsList.removeAll(productResultList);
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

    private void setDate(){
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar.getTime());
        //toDate.setText(date);fromDate.setText(date);
    }

    /* Call Api For Sales */
    private void callProductApi(String pageNo) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_products);
                RequestBody mRequestBodyPageNo = RequestBody.create(MediaType.parse("text/plain"),pageNo);
                RequestBody mRequestBodyPageSize = RequestBody.create(MediaType.parse("text/plain"),Constants.MIN_PAG_SIZE);
                RequestBody mRequestBodyCode = RequestBody.create(MediaType.parse("text/plain"),tvProductCode.getText().toString());
                RequestBody mRequestBodyName = RequestBody.create(MediaType.parse("text/plain"),tvProdName.getText().toString());

                ProductRequest request = new ProductRequest();
                request.setAction(mRequestBodyAction);
                request.setPageno(mRequestBodyPageNo);
                request.setPagesize(mRequestBodyPageSize);
                request.setProdCode(mRequestBodyCode);
                request.setProdName(mRequestBodyName);
                productViewModel.hitProductApi(request);
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
        enquiryPageAdapter = new EnquiryPageAdapter(mContext, enquiryPageList, new EnquiryPageAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(EnquiryPagermodel mData, List<EnquiryPagermodel> mDataList) {
                enquiryPageList = mDataList;
                try {
                    pagerClicked = String.valueOf(Integer.parseInt(mData.getPageNo())-1);
                    enquiryPageAdapter.updateList(mDataList);
                }catch (Exception e){e.printStackTrace();}
                try {
                    callProductApi(String.valueOf(Integer.parseInt(mData.getPageNo()) - 1));
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

    private void setAdapterForProductList() {
        if (productResultList.size() > 0) {
            rvSalesList.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            rvSalesList.setVisibility(View.GONE);
        }
        productAdapter = new ProductAdapter(mContext, productResultList, new ProductAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(int mDataTicket,Product productResult) {
                //For not killing pre fragment
                if(mDataTicket==0) {
                    Intent i = new Intent(getActivity(), View360Activity.class);
                    i.putExtra(Constants.TRANSACTION_ID, "1");
                    startActivity(i);
                    ((Activity) getActivity()).overridePendingTransition(0, 0);
                }
                if(mDataTicket==1){
                    showVisitDetailsDialog(productResult);
                }
            }
        });

        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(productAdapter);
    }

    //show Receipt Details popup
    public void showVisitDetailsDialog(Product productResult) {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_product_details);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton closeButton = mDialog.findViewById(R.id.closeButton);
        AppCompatTextView tvQuantity = mDialog.findViewById(R.id.tvQuantity);
        AppCompatTextView tvSaleAmount = mDialog.findViewById(R.id.tvSaleAmount);
        AppCompatTextView tvProdCode = mDialog.findViewById(R.id.tvProdCode);
        AppCompatTextView tvProdName = mDialog.findViewById(R.id.tvProdName);
        tvProdName.setText(productResult.getProdName()+"...");
        tvProdCode.setText(productResult.getProdCode());
        tvQuantity.setText(productResult.getSalesquantity());
        tvSaleAmount.setText(productResult.getTotalsaleamt());
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
    @OnClick({R.id.imgGraph,R.id.imgTable,R.id.submitButton,R.id.resetButton
    ,R.id.toDate,R.id.fromDate,R.id.imgFilter})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.toDate:
                //openDataPicker(1,toDate);
                break;
            case R.id.fromDate:
                //openDataPicker(0,fromDate);
                break;
            case R.id.submitButton:
                //add_fab.setVisibility(View.VISIBLE);
                productResultList.clear();
                setEnquiryPagerList(0);
                setAdapterForProductList();
                tvPage.setText("Showing " + String.valueOf(0) + " to " +
                        String.valueOf(0) + " of " + String.valueOf(0) + "\nEntries");
                callProductApi("0");
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
                break;

            case R.id.imgGraph:
                //add_fab.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
                layList.setVisibility(View.GONE);
                layPagination.setVisibility(View.GONE);
                //layFilter.setVisibility(View.GONE);
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
                emptyLayout.setVisibility(View.GONE);
                submitButton.setVisibility(View.GONE);
                imgGraph.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_donut_grey));
                imgTable.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_table));
                imgFilter.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_om_filter_grey));
                break;

            case R.id.resetButton:
                tvProductCode.setText("");
                tvProdName.setText("");
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
                    datePickerField.setText(sdf.format(myCalendar.getTime()));
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_PRODUCT)) {
                            ProductResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ProductResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                productResultList.clear();
                                long totalPage = 0;
                                //tvTotalCount.setText(String.valueOf(responseModel.getTotalenquiries()));
                                try {
                                    if (responseModel.getResult().getProduct() != null && responseModel.getResult().getProduct().size()>0) {
                                        productResultList = responseModel.getResult().getProduct();
                                        totalPage = responseModel.getResult().getTotalpages();
                                        if(responseModel.getResult().getNextpage()==1) {
                                            tvPage.setText("Showing " + String.valueOf(responseModel.getResult().getNextpage()) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1) + productResultList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalrows()) + "\nEntries"));
                                        }
                                        else {
                                            tvPage.setText("Showing " + String.valueOf(((responseModel.getResult().getNextpage()-1)*4)+1) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1)*4)+productResultList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalrows()) + "\nEntries");
                                        }
                                    }

                                }catch(Exception e){
                                    totalPage = 0;
                                    productResultList.clear();
                                    e.printStackTrace();

                                }
                                setEnquiryPagerList(totalPage);
                                setAdapterForProductList();
                            }

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
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case ERROR:
                ((BaseActivity)getActivity()).dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }

}