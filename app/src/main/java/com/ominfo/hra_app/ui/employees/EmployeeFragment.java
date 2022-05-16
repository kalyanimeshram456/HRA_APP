package com.ominfo.hra_app.ui.employees;

import static com.ominfo.hra_app.ui.employees.PaginationListener.PAGE_START;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
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
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.model.GradientColor;
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
import com.ominfo.hra_app.ui.employees.adapter.PostRecyclerAdapter;
import com.ominfo.hra_app.ui.employees.model.EmployeeList;
import com.ominfo.hra_app.ui.employees.model.EmployeeListRequest;
import com.ominfo.hra_app.ui.employees.model.EmployeeListResponse;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.sales_credit.model.GraphModel;
import com.ominfo.hra_app.ui.employees.adapter.EmployeeAdapter;
import com.ominfo.hra_app.ui.employees.model.EmployeeListViewModel;
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
//https://github.com/PhilJay/MPAndroidChart/wiki/Modifying-the-Viewport

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 * Use the {@link EmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener
    {

    Context mContext;
    PostRecyclerAdapter employeeAdapter;
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

    List<EmployeeList> employeeListArrayList = new ArrayList<>();
    List<DashModel> tagList = new ArrayList<>();
    List<GraphModel> graphModelsList = new ArrayList<>();
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.tvSearch)
    AppCompatTextView textViewSearch;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat emptyLayout;
    @BindView(R.id.iv_emptyLayimage)
    AppCompatImageView iv_emptyLayimage;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    @BindView(R.id.tvFilterCount)
    AppCompatTextView tvFilterCount;

    final Calendar myCalendar = Calendar.getInstance();
    @Inject
    ViewModelFactory mViewModelFactory;
    private EmployeeListViewModel employeeListViewModel;
    AppCompatAutoCompleteTextView AutoComFilterStatus,AutoComFilterName,AutoComFilterDesi;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private long totalPage = 0;
    private boolean isLoading = false;
    int itemCount = 0;

    public EmployeeFragment() {
        // Required empty public constructor
    }

    public static EmployeeFragment newInstance(String param1, String param2) {
        EmployeeFragment fragment = new EmployeeFragment();
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
        View view = inflater.inflate(R.layout.activity_employee, container, false);
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
        setToolbar();
        Glide.with(this)
                .load(R.drawable.img_bg_search)
                .into(iv_emptyLayimage);
        tv_emptyLayTitle.setText(R.string.scr_lbl_no_data_available);
        showFilterEmployeeDialog(1);
        swipeRefresh.setOnRefreshListener(this);

        rvSalesList.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvSalesList.setLayoutManager(layoutManager);

        employeeAdapter = new PostRecyclerAdapter(mContext,new ArrayList<>(), new PostRecyclerAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(int mDataTicket,EmployeeList employeeList) {
                Intent add = new Intent(getContext(),AddEmployeeActivity.class);
                add.putExtra(Constants.FROM_SCREEN,Constants.edit);
                Gson gson = new Gson();
                String myJson = gson.toJson(employeeList);
                add.putExtra(Constants.EMPLOYEE_OBJ, myJson);
                startActivity(add);
            }
        });
        rvSalesList.setAdapter(employeeAdapter);
        callEmployeeListApi("0");

        /**
         * add scroll listener while user reach in bottom load more will call
         */
        rvSalesList.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                callEmployeeListApi(String.valueOf(currentPage));
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
        //callEmployeeListApi("0");
        tv_emptyLayTitle.setText("Search something...");
    }

        private void doApiCall() {
            final ArrayList<EmployeeList> items = new ArrayList<>();
           /* new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {*/
                    for (int i = 0; i < employeeListArrayList.size(); i++) {
                        items.add(employeeListArrayList.get(i));
                    }
                    // do this all stuff on Success of APIs response
                    /**
                     * manage progress view
                     */
                    if (currentPage != PAGE_START) employeeAdapter.removeLoading();
                    employeeAdapter.addItems(items);
                    swipeRefresh.setRefreshing(false);

                    // check weather is last page or not
                    if (currentPage < totalPage) {
                        employeeAdapter.addLoading();
                    } else {
                        isLastPage = true;
                    }
                    isLoading = false;
              //  }
           // }, 0);
        }


        /**
     * do api call here to fetch data from server
     * In example i'm adding data manually
     */

    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        employeeAdapter.clear();
        callEmployeeListApi("0");
    }

    private void injectAPI() {
        employeeListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EmployeeListViewModel.class);
        employeeListViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_EMPLOYEES_LIST));
   }
    /* Call Api For employee list */
    private void callEmployeeListApi(String pageNo) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_employee_list);
                RequestBody mRequestComId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                RequestBody mRequestEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                RequestBody mRequestToken = RequestBody.create(MediaType.parse("text/plain"),  loginTable.getToken());
                RequestBody mRequestpage_number = RequestBody.create(MediaType.parse("text/plain"), pageNo);
                RequestBody mRequestpage_size = RequestBody.create(MediaType.parse("text/plain"), Constants.PAG_SIZE);
                RequestBody mRequestfilter_emp_name = RequestBody.create(MediaType.parse("text/plain"), AutoComFilterName.getText().toString());
                RequestBody mRequestfilter_emp_position = RequestBody.create(MediaType.parse("text/plain"),  AutoComFilterDesi.getText().toString());
                String status = "";
                if(AutoComFilterStatus.getText().toString().equals("All")){
                    status = "";
                }
                else if(AutoComFilterStatus.getText().toString().equals("Active")){
                    status = "1";
                }
                else{
                    status = "0";
                }
                RequestBody filter_emp_isActive = RequestBody.create(MediaType.parse("text/plain"),  status);

                EmployeeListRequest request = new EmployeeListRequest();
                request.setAction(mRequestAction);
                request.setCompanyId(mRequestComId);
                request.setEmployee(mRequestEmployee);
                request.setToken(mRequestToken);
                request.setPageNumber(mRequestpage_number);
                request.setPageSize(mRequestpage_size);
                request.setFilterEmpName(mRequestfilter_emp_name);
                request.setFilterEmpPosition(mRequestfilter_emp_position);
                request.setFilterEmpIsActive(filter_emp_isActive);

                employeeListViewModel.executeEmployeeListAPI(request);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    //set value to status dropdown
    private void setDropdownStatus() {
        List<String> mGenderDropdown = new ArrayList<>();
        mGenderDropdown.add("All");
        mGenderDropdown.add("Active");
        mGenderDropdown.add("Inactive");

        try {
            int pos = 0;
            if (mGenderDropdown != null && mGenderDropdown.size() > 0) {
                String[] mDropdownList = new String[mGenderDropdown.size()];
                for (int i = 0; i < mGenderDropdown.size(); i++) {
                    mDropdownList[i] = String.valueOf(mGenderDropdown.get(i));
                }
                //AutoComTextViewVehNo.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mContext,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //AutoComTextViewVehNo.setThreshold(1);
                AutoComFilterStatus.setAdapter(adapter);
                //mSelectedColor = mDropdownList[pos];
                AutoComFilterStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
       /* Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.status_bar_color));*/
        /*Window window = getActivity().getWindow();
        View view = window.getDecorView();
        BaseActivity.DarkStatusBar.setLightStatusBar(view,getActivity());*/

    }

    //show Filter Employee popup
    public void showFilterEmployeeDialog(int val) {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_filter_employee);
        mDialog.setCanceledOnTouchOutside(true);
        RelativeLayout mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton submitButton = mDialog.findViewById(R.id.submitButton);
        AutoComFilterStatus = mDialog.findViewById(R.id.AutoComFilterStatus);
        AutoComFilterName = mDialog.findViewById(R.id.AutoComFilterName);
        AutoComFilterDesi = mDialog.findViewById(R.id.AutoComFilterDesi);
        AutoComFilterStatus.setText("All");
        setDropdownStatus();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                callEmployeeListApi("0");
                //doApiCall();
            }
        });

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        if(val==0) {
            mDialog.show();
        }
    }

    private void setToolbar() {
        //set toolbar title
        tvToolbarTitle.setText(R.string.scr_lbl_employees);
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
    @OnClick({R.id.imgAddEmployee,R.id.imgReport})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgAddEmployee:
                Intent add = new Intent(getContext(),AddEmployeeActivity.class);
                add.putExtra(Constants.FROM_SCREEN,Constants.add);
                startActivity(add);
                break;
            case R.id.imgReport:
                showFilterEmployeeDialog(0);
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


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }


    /*Api response */
    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {
            case LOADING:
                    //((BaseActivity) mContext).showSmallProgressBar(mProgressBarHolder);
                break;

            case SUCCESS:
                //((BaseActivity) mContext).dismissSmallProgressBar(mProgressBarHolder);
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_EMPLOYEES_LIST)) {
                            EmployeeListResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), EmployeeListResponse.class);
                            totalPage = responseModel.getResult().getTotalrows();
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                if (employeeListArrayList != null) {
                                    //employeeListArrayList= new ArrayList<>();
                                }
                                employeeListArrayList = responseModel.getResult().getList();
                                doApiCall();
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
                break;
        }
    }


}