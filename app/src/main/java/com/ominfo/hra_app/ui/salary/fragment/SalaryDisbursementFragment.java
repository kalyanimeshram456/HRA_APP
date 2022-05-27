package com.ominfo.hra_app.ui.salary.fragment;

import static com.ominfo.hra_app.ui.employees.PaginationListener.PAGE_START;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
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
import com.ominfo.hra_app.ui.employees.PaginationListener;
import com.ominfo.hra_app.ui.employees.model.EmployeeList;
import com.ominfo.hra_app.ui.leave.model.ActiveEmployeeListEmpDatum;
import com.ominfo.hra_app.ui.leave.model.ActiveEmployeeListResponse;
import com.ominfo.hra_app.ui.leave.model.ActiveEmployeeListViewModel;
import com.ominfo.hra_app.ui.leave.model.EmployeeLeaveMonthsList;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.salary.adapter.SalaryDisAdapter;
import com.ominfo.hra_app.ui.salary.adapter.SalaryNewDisAdapter;
import com.ominfo.hra_app.ui.salary.model.SalaryAllList;
import com.ominfo.hra_app.ui.salary.model.SalaryAllListRequest;
import com.ominfo.hra_app.ui.salary.model.SalaryAllListViewModel;
import com.ominfo.hra_app.ui.salary.model.SalaryAllResponse;
import com.ominfo.hra_app.ui.employees.model.EmployeeListViewModel;
import com.ominfo.hra_app.ui.salary.model.SalaryDisbursetViewModel;
import com.ominfo.hra_app.ui.salary.model.UpdateSalaryViewModel;
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
//https://github.com/PhilJay/MPAndroidChart/wiki/Modifying-the-Viewport

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SalaryDisbursementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalaryDisbursementFragment extends BaseFragment {

    Context mContext;
    SalaryNewDisAdapter salaryDisbursementAdapter;
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
    List<EmployeeList> searchresultList = new ArrayList<>();
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
    private ActiveEmployeeListViewModel activeEmployeeListViewModel;
    private SalaryAllListViewModel salaryAllListViewModel;
    private SalaryDisbursetViewModel salaryDisbursetViewModel;
    private UpdateSalaryViewModel updateSalaryViewModel;

    private Dialog mDialogChangePass;
    LinearLayoutCompat layoutLeaveTime;
    AppCompatTextView appcomptextLeaveTime;
    View viewToDate;
    RelativeLayout layToDate;
    int cam = 0,noOFDays = 0;
    AppCompatTextView appcomptextNoOfDays;
    AppCompatTextView tvDateValueFrom;
    AppCompatTextView tvDateValueTo,tvTimeValueFrom,tvTimeValueTo ;
    AppCompatAutoCompleteTextView AutoComTextViewComment;
    AppCompatAutoCompleteTextView tvAutoLeaveStatus;
    AppCompatAutoCompleteTextView AutoComTextViewDuration;
    AppCompatAutoCompleteTextView AutoComTextViewLeaveType;
    List<SalaryAllList> salaryAllresultList = new ArrayList<>();
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private long totalPage = 0;
    private boolean isLoading = false;
    int itemCount = 0;
    List<ActiveEmployeeListEmpDatum> activeEmployeeList = new ArrayList<>();
    @BindView(R.id.AutoComAddEmp)
    AppCompatAutoCompleteTextView AutoComAddEmp;
    @BindView(R.id.AutoComMonth)
    AppCompatAutoCompleteTextView AutoComMonth;
    @BindView(R.id.tvTotalDays)
    AppCompatTextView tvTotalDays;
    @BindView(R.id.tvTotalSalaries)
    AppCompatTextView tvTotalSalaries;
    String selectedActiveEmpid = "0";
    double total = 0.0;
    public SalaryDisbursementFragment() {
        // Required empty public constructor
    }

    public static SalaryDisbursementFragment newInstance(String param1, String param2) {
        SalaryDisbursementFragment fragment = new SalaryDisbursementFragment();
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
        View view = inflater.inflate(R.layout.activity_salary_disbursment, container, false);
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
        tv_emptyLayTitle.setText("Search something...");
        setDropdownMonth();
        callGetActiveEmployeeListApi();
        setAdapterForSalaryAllList();
        callSalaryAllListApi();
    }

    private void injectAPI() {
        activeEmployeeListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ActiveEmployeeListViewModel.class);
        activeEmployeeListViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_ACTIVE_EMP_LIST));

        salaryAllListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SalaryAllListViewModel.class);
        salaryAllListViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_SALARY_ALL_LIST));

        salaryDisbursetViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SalaryDisbursetViewModel.class);
        salaryDisbursetViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_ADD_SALARY));
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

        try {
            int pos = 0;
            if (monthsLists != null && monthsLists.size() > 0) {
                String[] mDropdownList = new String[monthsLists.size()];
                for (int i = 0; i < monthsLists.size(); i++) {
                    mDropdownList[i] = String.valueOf(monthsLists.get(i).getName());
                    //pos = i;
                }
                AutoComMonth.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mContext,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //tvHighlight.setThreshold(1);
                AutoComMonth.setAdapter(adapter);
                AutoComMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       try{
                        for(int i=0;i<monthsLists.size();i++){
                            if(monthsLists.get(i).getName().equals(AutoComMonth.getText().toString().trim())){
                                tvTotalDays.setText("Total days of month : "+monthsLists.get(i).getDays());
                            }
                        }}catch (Exception e){}
                        callSalaryAllListApi();
                    }
                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Call Api For employee list */
    private void callSalaryAllListApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_salary_all_list);
                RequestBody mRequestComId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                RequestBody mRequestEmployee = RequestBody.create(MediaType.parse("text/plain"), selectedActiveEmpid);
                RequestBody mRequestisAd = RequestBody.create(MediaType.parse("text/plain"),  loginTable.getIsadmin());
                //RequestBody mRequestpage_number = RequestBody.create(MediaType.parse("text/plain"), pageNo);
                //RequestBody mRequestpage_size = RequestBody.create(MediaType.parse("text/plain"), Constants.PAG_SIZE);
                String monthNumber  =  AppUtils.convertMonthToInt(AutoComMonth.getText().toString().trim());
                RequestBody mRequestMonth = RequestBody.create(MediaType.parse("text/plain"), monthNumber);
                String year = AppUtils.getCurrentYear();
                RequestBody mRequestYear = RequestBody.create(MediaType.parse("text/plain"), year);

                SalaryAllListRequest request= new SalaryAllListRequest();
                request.setYear(mRequestYear);
                request.setAction(mRequestAction);
                request.setCompany_ID(mRequestComId);
                request.setEmp_id(mRequestEmployee);
                request.setIsAdmin(mRequestisAd);
                //request.setPageNumber(mRequestpage_number);
                //request.setPageSize(mRequestpage_size);
                request.setMonth(mRequestMonth);
                salaryAllListViewModel.hitSalaryAllListAPI(request);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For employee list */
    private void callAddSalaryApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_add_Salary);
                Gson gson = new Gson();
                String arrayData = gson.toJson(salaryAllresultList);
                RequestBody mRequestList = RequestBody.create(MediaType.parse("text/plain"),arrayData);

                salaryDisbursetViewModel.hitSalaryDisbursetAPI(mRequestAction,mRequestList);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Update list */
    private void callUpdateSalaryApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_update_Salary);
                RequestBody mRequestaddition = RequestBody.create(MediaType.parse("text/plain"), "");
                RequestBody mRequesttotal = RequestBody.create(MediaType.parse("text/plain"), "");
                RequestBody mRequestremark = RequestBody.create(MediaType.parse("text/plain"), "");
                RequestBody mRequestemp_id = RequestBody.create(MediaType.parse("text/plain"), "");
                RequestBody mRequestdeduction = RequestBody.create(MediaType.parse("text/plain"), "");
                RequestBody mRequestyear = RequestBody.create(MediaType.parse("text/plain"), "");
                RequestBody mRequestmonth = RequestBody.create(MediaType.parse("text/plain"), "");

                Gson gson = new Gson();
                String arrayData = gson.toJson(salaryAllresultList);
                RequestBody mRequestList = RequestBody.create(MediaType.parse("text/plain"),arrayData);

                salaryDisbursetViewModel.hitSalaryDisbursetAPI(mRequestAction,mRequestList);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }


    /* Call Api For active employee List */
    private void callGetActiveEmployeeListApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_active_emp_list);
                RequestBody mRequestComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                RequestBody mRequestEmpId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                activeEmployeeListViewModel.hitActiveEmployeeListAPI(mRequestBodyAction,
                        mRequestComId,mRequestEmpId);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
       }

    //show salary disbursment popup
    public void showSalaryDisbursmentDialog(int pos , SalaryAllList employeeList) {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_salary_disburse);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatEditText etAddition = mDialog.findViewById(R.id.etAddition);
        AppCompatTextView etBasicSalary = mDialog.findViewById(R.id.etBasicSalary);
        AppCompatEditText etDeduction = mDialog.findViewById(R.id.etDeduction);
        AppCompatTextView tvTotalValue = mDialog.findViewById(R.id.tvTotalValue);
        AppCompatTextView appcomptextRemark = mDialog.findViewById(R.id.appcomptextRemark);
        AppCompatEditText etDescr = mDialog.findViewById(R.id.etDescr);
        AppCompatButton addSubmitButton = mDialog.findViewById(R.id.addSubmitButton);
        etBasicSalary.setText(employeeList.getSalaryThisMonth()+"");
        etAddition.setText("");etDeduction.setText("");
        tvTotalValue.setText(employeeList.getSalaryThisMonth()+"");
        if(employeeList.getStatus()!=null && !employeeList.getStatus().equals("")
                && !employeeList.getStatus().equals("null")){
            //call api
            etDescr.setVisibility(View.VISIBLE);appcomptextRemark.setVisibility(View.VISIBLE);
        }else{ etDescr.setVisibility(View.GONE);appcomptextRemark.setVisibility(View.GONE);}
        etAddition.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                //if(s.length() != 0) {
                    Double tot = Double.parseDouble(tvTotalValue.getText().toString().trim());
                    Double totalSum = tot + Double.parseDouble(s.toString().equals("")
                            ||s.toString()==null?"0":s.toString());
                    tvTotalValue.setText(totalSum+"");
              /*  }
                else{

                }*/
            }
        });
        etDeduction.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0) {
                    Double tot = Double.parseDouble(tvTotalValue.getText().toString().trim());
                    Double totalSum = tot - Double.parseDouble(s.toString().equals("")
                            ||s.toString()==null?"0":s.toString());
                    tvTotalValue.setText(totalSum+"");
                }
            }
        });
        addSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mDialog.dismiss();
                if(employeeList.getStatus()!=null && !employeeList.getStatus().equals("")
                && !employeeList.getStatus().equals("null")){
                    //call api
                }
                salaryAllresultList.get(pos).setSalary(tvTotalValue.getText().toString());
                salaryAllresultList.get(pos).setSalaryThisMonth(Double.valueOf(tvTotalValue.getText().toString()));
                salaryDisbursementAdapter.updateList(salaryAllresultList);
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


    private void setAdapterForSalaryAllList() {
        if (salaryAllresultList!=null && salaryAllresultList.size() > 0) {
            rvSalesList.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            total = 0.0;
            for(int i=0;i<salaryAllresultList.size();i++){
                total = total + (double)(salaryAllresultList.get(i).getSalaryThisMonth());
            }
            tvTotalSalaries.setText(""+total);
            } else {
            rvSalesList.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(R.drawable.img_bg_search)
                    .into(iv_emptyLayimage);
            tv_emptyLayTitle.setText(R.string.scr_lbl_no_data_available);
        }
        salaryDisbursementAdapter = new SalaryNewDisAdapter(mContext, salaryAllresultList, new SalaryNewDisAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(int mDataTicket,SalaryAllList searchresult) {
                showSalaryDisbursmentDialog(mDataTicket,searchresult);
            }
        });
         rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(salaryDisbursementAdapter);
        final boolean[] check = {false};

    }

    //show Receipt Details popup
    public void showVisitDetailsDialog() {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.activity_reminder_alert);
        mDialog.setCanceledOnTouchOutside(true);
        //AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        //AppCompatButton closeButton = mDialog.findViewById(R.id.closeButton);

        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);

        /*closeButton.setOnClickListener(new View.OnClickListener() {
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
        });*/
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
    @OnClick({R.id.btnSubmit})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnSubmit:
                callAddSalaryApi();
             /*   SalaryDisbursementFragment myFrag = new SalaryDisbursementFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(myFrag);
                trans.commit();
                manager.popBackStack();*/
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
                /*if(isAttendanceDetailsValid(AutoComTextViewDuration,input_textDuration,AutoComTextViewLeaveType,input_textType
                )){
                   // callApplyLeaveApi();
                }*/

            }
        });
        mDialogChangePass.show();
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

    //set value to Search dropdown
    private void setDropdownActiveEmpList() {
        try {
            int pos = 0;
            if (activeEmployeeList != null && activeEmployeeList.size() > 0) {
                String[] mDropdownList = new String[activeEmployeeList.size()];
                for (int i = 0; i < activeEmployeeList.size(); i++) {
                    mDropdownList[i] = String.valueOf(activeEmployeeList.get(i).getEmpName());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mContext,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //tvHighlight.setThreshold(1);
                AutoComAddEmp.setAdapter(adapter);
                //mListDropdownView.setHint(mDropdownList[pos]);
                AutoComAddEmp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        for (int i = 0; i < activeEmployeeList.size(); i++) {
                            if (AutoComAddEmp.getText().toString().trim().equals(activeEmployeeList.get(i).getEmpName()))
                            { selectedActiveEmpid = activeEmployeeList.get(i).getEmpId();
                            }
                        }
                        callSalaryAllListApi();
                    }
                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
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
                ((BaseActivity) mContext).dismissSmallProgressBar(mProgressBarHolder);
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_SALARY_ALL_LIST)) {
                            SalaryAllResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), SalaryAllResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                if (salaryAllresultList != null) {
                                    salaryAllresultList= new ArrayList<>();
                                }
                                salaryAllresultList = responseModel.getResult().getList();
                                setAdapterForSalaryAllList();
                            }                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_ACTIVE_EMP_LIST)) {
                            ActiveEmployeeListResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ActiveEmployeeListResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                try{activeEmployeeList.removeAll(activeEmployeeList);}catch (Exception e){}
                                if(responseModel.getResult().getEmpData()!=null &&
                                        responseModel.getResult().getEmpData().size()>0){
                                    activeEmployeeList = responseModel.getResult().getEmpData();
                                    setDropdownActiveEmpList();
                                }
                            }
                            else {

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
                break;
        }
    }

}