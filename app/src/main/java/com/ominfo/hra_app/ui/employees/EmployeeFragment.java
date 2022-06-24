package com.ominfo.hra_app.ui.employees;

import static com.ominfo.hra_app.MainActivity.bottomNavigationView;
import static com.ominfo.hra_app.ui.employees.PaginationListener.PAGE_START;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.basecontrol.BaseFragment;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.interfaces.Constants;
import com.ominfo.hra_app.interfaces.SharedPrefKey;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.hra_app.ui.employees.adapter.AttendanceEmployeeAdapter;
import com.ominfo.hra_app.ui.employees.adapter.EmployeeListRecyclerAdapter;
import com.ominfo.hra_app.ui.employees.model.AttendanceEmployeeListData;
import com.ominfo.hra_app.ui.employees.model.AttendanceEmployeeListResponse;
import com.ominfo.hra_app.ui.employees.model.AttendanceEmployeeListViewModel;
import com.ominfo.hra_app.ui.employees.model.EmployeeList;
import com.ominfo.hra_app.ui.employees.model.EmployeeListRequest;
import com.ominfo.hra_app.ui.employees.model.EmployeeListResponse;
import com.ominfo.hra_app.ui.leave.model.EmployeeLeaveMonthsList;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.employees.model.EmployeeListViewModel;
import com.ominfo.hra_app.ui.salary.adapter.SalaryNewDisAdapter;
import com.ominfo.hra_app.ui.salary.model.SalaryAllList;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;
import com.ominfo.hra_app.util.SharedPref;

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
 * Use the {@link EmployeeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    Context mContext;
    EmployeeListRecyclerAdapter employeeAdapter;
    AttendanceEmployeeAdapter attendanceEmployeeAdapter;
    @BindView(R.id.rvSalesList)
    RecyclerView rvSalesList;
    @BindView(R.id.rvAttendanceList)
    RecyclerView rvAttendanceList;
    @BindView(R.id.imgBack)
    LinearLayoutCompat imgBack;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvToolbarTitle;
    @BindView(R.id.tvEmployeeActive)
    AppCompatTextView tvEmployeeActive;
    @BindView(R.id.tv_emptyLayTitle)
    AppCompatTextView tv_emptyLayTitle;
    @BindView(R.id.tvTitleError)
    AppCompatTextView tvTitleError;
    private AppDatabase mDb;
    List<EmployeeList> employeeListArrayList = new ArrayList<>();
    List<AttendanceEmployeeListData> attendanceEmployeeListDataList = new ArrayList<>();
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
    @BindView(R.id.layAttendancecard)
    LinearLayoutCompat layAttendancecard;
    final Calendar myCalendar = Calendar.getInstance();
    @Inject
    ViewModelFactory mViewModelFactory;
    private EmployeeListViewModel employeeListViewModel;
    private AttendanceEmployeeListViewModel attendanceEmployeeListViewModel;
    AppCompatAutoCompleteTextView AutoComFilterStatus, AutoComFilterName, AutoComFilterDesi;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.imgReport)
    AppCompatImageView imgReport;
    @BindView(R.id.imgAddEmployee)
    FloatingActionButton imgAddEmployee;
    @BindView(R.id.AutoComMonth)
    AppCompatAutoCompleteTextView AutoComMonth;
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
        ((BaseActivity) mContext).getDeps().inject(this);
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        injectAPI();
        init();
    }

    private void init() {
        setToolbar();
        layAttendancecard.setVisibility(View.GONE);
        Glide.with(this)
                .load(R.drawable.img_bg_search)
                .into(iv_emptyLayimage);
        emptyLayout.setVisibility(View.VISIBLE);
        tv_emptyLayTitle.setText(R.string.scr_lbl_no_data_available);
        showFilterEmployeeDialog(1);
        swipeRefresh.setOnRefreshListener(this);
        AutoComMonth.setText(AppUtils.getCurrentMonth());
        setDropdownMonth();
        rvSalesList.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvSalesList.setLayoutManager(layoutManager);

        employeeAdapter = new EmployeeListRecyclerAdapter(mContext, new ArrayList<>(), new EmployeeListRecyclerAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(int mDataTicket, EmployeeList employeeList) {
                try {
                    LoginTable loginTable = mDb.getDbDAO().getLoginData();
                    if (loginTable != null) {
                        if (!loginTable.getIsadmin().equals("0")) {
                            Intent add = new Intent(getContext(), AddEmployeeActivity.class);
                            add.putExtra(Constants.FROM_SCREEN, Constants.edit);
                            add.putExtra(Constants.TITLE, employeeList.getEmpId());
                            Gson gson = new Gson();
                            String myJson = gson.toJson(employeeList);
                            add.putExtra(Constants.EMPLOYEE_OBJ, myJson);
                            startActivity(add);
                        }
                    }
                } catch (Exception e) {
                }
            }
        });
        rvSalesList.setAdapter(employeeAdapter);
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
        //tv_emptyLayTitle.setText("Search something...");

        try {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                if (loginTable.getIsadmin().equals("0")) {
                    imgAddEmployee.setVisibility(View.GONE);
                    setAdapterForAttendanceList();
                    callAttendanceEmployeeListApi();
                    layAttendancecard.setVisibility(View.VISIBLE);
                    //tvEmployeeActive.setVisibility(View.VISIBLE);
                }else {
                    imgAddEmployee.setVisibility(View.VISIBLE);
                    layAttendancecard.setVisibility(View.GONE);
                    //tvEmployeeActive.setVisibility(View.GONE);
                }
            }
        } catch (Exception e) {
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

        List<EmployeeLeaveMonthsList> monthsListWOW = new ArrayList<>();
        if (monthsLists.size() > 0) {
            for (int i = 0; i < monthsLists.size(); i++) {
                // LogUtil.printLog("month_rag",AppUtils.getCurrentMonth()+"-"+(monthsLists.get(i).getName()));
                if (!AppUtils.getCurrentMonth().equals(monthsLists.get(i).getName())) {
                    monthsListWOW.add(monthsLists.get(i));
                } else {
                    monthsListWOW.add(monthsLists.get(i));
                    break;
                }
            }
        }

        try {
            int pos = 0;
            if (monthsListWOW.size() > 0) {
                String[] mDropdownList = new String[monthsListWOW.size()];
                for (int i = 0; i < monthsListWOW.size(); i++) {
                    if(monthsListWOW.get(i).getName()!=null) {
                        mDropdownList[i] = String.valueOf(monthsListWOW.get(i).getName());
                    }
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
                       setAdapterForAttendanceList();
                       callAttendanceEmployeeListApi();
                    }
                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if(employeeAdapter.getItemCount()>1) {
            //employeeAdapter.removeLoading();
        }
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
        employeeListViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_EMPLOYEES_LIST));

        attendanceEmployeeListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AttendanceEmployeeListViewModel.class);
        attendanceEmployeeListViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_ATTENDANCE_EMPLOYEES_LIST));
    }

    /* Call Api For employee list */
    private void callEmployeeListApi(String pageNo) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_employee_list);
                RequestBody mRequestComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                RequestBody mRequestEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                RequestBody mRequestToken = RequestBody.create(MediaType.parse("text/plain"), loginTable.getToken());
                RequestBody mRequestpage_number = RequestBody.create(MediaType.parse("text/plain"), pageNo);
                RequestBody mRequestpage_size = RequestBody.create(MediaType.parse("text/plain"), Constants.PAG_SIZE);
                RequestBody mRequestfilter_emp_name = RequestBody.create(MediaType.parse("text/plain"), AutoComFilterName.getText().toString());
                RequestBody mRequestfilter_emp_position = RequestBody.create(MediaType.parse("text/plain"), AutoComFilterDesi.getText().toString());
                String status = "";
                if (AutoComFilterStatus.getText().toString().equals("All")) {
                    status = "";
                } else if (AutoComFilterStatus.getText().toString().equals("Active")) {
                    status = "1";
                } else {
                    status = "0";
                }
                RequestBody filter_emp_isActive = RequestBody.create(MediaType.parse("text/plain"), status);

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
            } else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Attendance employee list */
    private void callAttendanceEmployeeListApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_late_monthly_details);
                RequestBody mRequestEmpId = RequestBody.create(MediaType.parse("text/plain"), "46"/*loginTable.getEmployeeId()*/);
                String monthNumber  =  AppUtils.convertMonthToInt(AutoComMonth.getText().toString().trim());
                RequestBody mRequestMonth = RequestBody.create(MediaType.parse("text/plain"), monthNumber);

                attendanceEmployeeListViewModel.hitAttendanceEmployeeListAPI(mRequestAction,
                        mRequestEmpId,mRequestMonth);
            } else {
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
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        employeeAdapter.clear();
        callEmployeeListApi("0");
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
        AppCompatButton resetButton = mDialog.findViewById(R.id.resetButton);
        AutoComFilterStatus = mDialog.findViewById(R.id.AutoComFilterStatus);
        AutoComFilterName = mDialog.findViewById(R.id.AutoComFilterName);
        AutoComFilterDesi = mDialog.findViewById(R.id.AutoComFilterDesi);

            String name = SharedPref.getInstance(mContext).read(SharedPrefKey.FILTER_NAME, "");
            String desi = SharedPref.getInstance(mContext).read(SharedPrefKey.FILTER_DESI, "");
            String status = SharedPref.getInstance(mContext).read(SharedPrefKey.FILTER_STATUS, "All");
            AutoComFilterStatus.setText(status);   setDropdownStatus();
            AutoComFilterName.setText(name);AutoComFilterDesi.setText(desi);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoComFilterStatus.setText("All");   setDropdownStatus();
                AutoComFilterName.setText("");AutoComFilterDesi.setText("");
                SharedPref.getInstance(mContext).write(SharedPrefKey.FILTER_NAME, AutoComFilterName.getText().toString().trim());
                SharedPref.getInstance(mContext).write(SharedPrefKey.FILTER_DESI, AutoComFilterDesi.getText().toString().trim());
                SharedPref.getInstance(mContext).write(SharedPrefKey.FILTER_STATUS, AutoComFilterStatus.getText().toString().trim());
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                SharedPref.getInstance(mContext).write(SharedPrefKey.FILTER_NAME, AutoComFilterName.getText().toString().trim());
                SharedPref.getInstance(mContext).write(SharedPrefKey.FILTER_DESI, AutoComFilterDesi.getText().toString().trim());
                SharedPref.getInstance(mContext).write(SharedPrefKey.FILTER_STATUS, AutoComFilterStatus.getText().toString().trim());
                itemCount = 0;
                currentPage = PAGE_START;
                isLastPage = false;
                employeeAdapter.clear();
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
        if (val == 0) {
            mDialog.show();
        }
    }

    private void setToolbar() {
        //set toolbar title
        LoginTable loginTable = mDb.getDbDAO().getLoginData();
        if (!loginTable.getIsadmin().equals("0")){
            imgReport.setVisibility(View.VISIBLE);
            tvFilterCount.setVisibility(View.VISIBLE);
        }else{imgReport.setVisibility(View.INVISIBLE);
            tvFilterCount.setVisibility(View.INVISIBLE);}
        tvToolbarTitle.setText(R.string.scr_lbl_employees);
        ((BaseActivity) mContext).initToolbar(5, mContext, R.id.imgBack, R.id.imgReport, R.id.imgNotify, tvNotifyCount, R.id.imgBack, R.id.imgCall);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new DashboardFragment();
                ((BaseActivity) mContext).moveFragment(mContext, fragment);
                bottomNavigationView.setSelectedItemId(R.id.home);
            }
        });
        imgNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity) mContext).launchScreen(mContext, NotificationsActivity.class);
                ;
            }
        });
    }

    //perform click actions
    @OnClick({R.id.imgAddEmployee, R.id.imgReport})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgAddEmployee:
                Intent add = new Intent(getContext(), AddEmployeeActivity.class);
                add.putExtra(Constants.FROM_SCREEN, Constants.add);
                add.putExtra(Constants.TITLE, "");
                startActivity(add);
                break;
            case R.id.imgReport:
                showFilterEmployeeDialog(0);
                break;
        }
    }

    //set date picker view
    private void openDataPicker(int val, AppCompatTextView datePickerField) {
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
                if (val == 1) {
                    datePickerField.setText(sdf.format(myCalendar.getTime()));
                } else {
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
                                if (responseModel.getResult().getList() != null && responseModel.getResult().getList().size()>0) {
                                    employeeListArrayList = responseModel.getResult().getList();
                                    emptyLayout.setVisibility(View.GONE);
                                    doApiCall();
                                }
                                LoginTable loginTable = mDb.getDbDAO().getLoginData();
                                if (!loginTable.getIsadmin().equals("0")){
                                    tvEmployeeActive.setVisibility(View.VISIBLE);
                                    tvEmployeeActive.setText(((responseModel.getResult().getTotal_prest_emp()==null || responseModel.getResult().getTotal_prest_emp().equals(""))?"0":
                                            responseModel.getResult().getTotal_prest_emp())+ " / " + ((responseModel.getResult().getTotalactiveemp()==null || responseModel.getResult().getTotalactiveemp().equals(""))?
                                                    "0":responseModel.getResult().getTotalactiveemp())+ " Employees active");
                                   }

                            }else{
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }
                    } catch (Exception e) {
                       // LogUtil.printLog("tet_error",e.getMessage());
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_ATTENDANCE_EMPLOYEES_LIST)) {
                            AttendanceEmployeeListResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), AttendanceEmployeeListResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                attendanceEmployeeListDataList.removeAll(attendanceEmployeeListDataList);
                                if (responseModel.getResult().getData() != null && responseModel.getResult().getData().size()>0) {
                                    attendanceEmployeeListDataList = responseModel.getResult().getData();
                                    setAdapterForAttendanceList();
                                }else{
                                    tvTitleError.setVisibility(View.VISIBLE);
                                    tvTitleError.setText("Congrats ! You dont have any Late check in OR Early check out record" +
                                            " for "+AutoComMonth.getText().toString().toString()+" Month.");
                                }

                            }else{
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                                attendanceEmployeeListDataList.removeAll(attendanceEmployeeListDataList);
                                setAdapterForAttendanceList();
                            }
                        }
                    } catch (Exception e) {
                       // LogUtil.printLog("tet_error",e.getMessage());
                        e.printStackTrace();
                        attendanceEmployeeListDataList.removeAll(attendanceEmployeeListDataList);
                        setAdapterForAttendanceList();
                    }
                }
                break;
            case ERROR:
                ((BaseActivity) mContext).dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                break;

        }
    }

    private void setAdapterForAttendanceList() {
        if (attendanceEmployeeListDataList!=null && attendanceEmployeeListDataList.size() > 0) {
            rvAttendanceList.setVisibility(View.VISIBLE);
            tvTitleError.setVisibility(View.GONE);
        } else {

        }
        attendanceEmployeeAdapter = new AttendanceEmployeeAdapter(mContext, attendanceEmployeeListDataList, new AttendanceEmployeeAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(int mDataTicket,AttendanceEmployeeListData searchresult,List<AttendanceEmployeeListData> mList) {

            }
        });
        rvAttendanceList.setHasFixedSize(true);
        rvAttendanceList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvAttendanceList.setAdapter(attendanceEmployeeAdapter);
        final boolean[] check = {false};

    }

}