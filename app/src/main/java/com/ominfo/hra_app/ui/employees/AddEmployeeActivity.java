package com.ominfo.hra_app.ui.employees;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.interfaces.Constants;
import com.ominfo.hra_app.interfaces.SharedPrefKey;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.hra_app.ui.dashboard.model.DashModel;
import com.ominfo.hra_app.ui.employees.adapter.EmployeeTimeAdapter;
import com.ominfo.hra_app.ui.employees.model.AddEmployeeRequest;
import com.ominfo.hra_app.ui.employees.model.AddEmployeeResponse;
import com.ominfo.hra_app.ui.employees.model.AddEmployeeViewModel;
import com.ominfo.hra_app.ui.employees.model.DeactivateEmployeeResponse;
import com.ominfo.hra_app.ui.employees.model.DeactivateEmployeeViewModel;
import com.ominfo.hra_app.ui.employees.model.EditEmployeeRequest;
import com.ominfo.hra_app.ui.employees.model.EditEmployeeResponse;
import com.ominfo.hra_app.ui.employees.model.EditEmployeeViewModel;
import com.ominfo.hra_app.ui.employees.model.EmployeeList;
import com.ominfo.hra_app.ui.employees.model.EmployeeListRequest;
import com.ominfo.hra_app.ui.employees.model.EmployeeListResponse;
import com.ominfo.hra_app.ui.employees.model.EmployeeListViewModel;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.my_account.model.GetCompanyList;
import com.ominfo.hra_app.ui.my_account.model.GetCompanyResponse;
import com.ominfo.hra_app.ui.my_account.model.GetCompanyViewModel;
import com.ominfo.hra_app.ui.my_account.model.WorkTimingList;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.visit_report.activity.AddLocationActivity;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;
import com.ominfo.hra_app.util.SharedPref;

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

public class AddEmployeeActivity extends BaseActivity {
    Context mContext;
    String transactionId = "";
    List<DashModel> dashboardList = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();
    GoogleApiClient googleApiClient;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    String lat = "", lng = "";
    boolean mStartVisit = false;
    @Inject
    ViewModelFactory mViewModelFactory;
    private AddEmployeeViewModel addEmployeeViewModel;
    private DeactivateEmployeeViewModel deactivateEmployeeViewModel;
    private EditEmployeeViewModel editEmployeeViewModel;
    private GetCompanyViewModel getCompanyViewModel;
    private EmployeeListViewModel employeeListViewModel;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvToolbarTitle;
    @BindView(R.id.imgBack)
    LinearLayoutCompat imgBack;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    private AppDatabase mDb;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    //add employee fields
    @BindView(R.id.input_textName)
    TextInputLayout input_textName;
    @BindView(R.id.AutoComName)
    AppCompatAutoCompleteTextView AutoComName;
    @BindView(R.id.input_textEmailId)
    TextInputLayout input_textEmailId;
    @BindView(R.id.AutoComEmailId)
    AppCompatAutoCompleteTextView AutoComEmailId;
    @BindView(R.id.input_textMobile)
    TextInputLayout input_textMobile;
    @BindView(R.id.AutoComMobile)
    AppCompatAutoCompleteTextView AutoComMobile;
    @BindView(R.id.input_textDesi)
    TextInputLayout input_textDesi;
    @BindView(R.id.AutoComDesi)
    AppCompatAutoCompleteTextView AutoComDesi;
    @BindView(R.id.input_textGender)
    TextInputLayout input_textGender;
    @BindView(R.id.AutoComGender)
    AppCompatAutoCompleteTextView AutoComGender;
    @BindView(R.id.tvDateValue)
    AppCompatTextView tvDateValue;
    @BindView(R.id.tvJoiningDate)
    AppCompatTextView tvJoiningDate;
    @BindView(R.id.tvMissing)
    AppCompatTextView tvMissing;
    @BindView(R.id.imgToDate)
    AppCompatImageView imgToDate;
    @BindView(R.id.input_textAddress)
    TextInputLayout input_textAddress;
    @BindView(R.id.AutoComAddress)
    AppCompatAutoCompleteTextView AutoComAddress;
    @BindView(R.id.input_textPincode)
    TextInputLayout input_textPincode;
    @BindView(R.id.rvSalesList)
    RecyclerView rvSalesList;
    @BindView(R.id.AutoComPincode)
    AppCompatAutoCompleteTextView AutoComPincode;
    @BindView(R.id.input_textCurrSalary)
    TextInputLayout input_textCurrSalary;
    @BindView(R.id.AutoComCurrSalary)
    AppCompatAutoCompleteTextView AutoComCurrSalary;
   /* @BindView(R.id.input_textJoiningDate)
    TextInputLayout input_textJoiningDate;*/
    @BindView(R.id.input_textCasualLeave)
    TextInputLayout input_textCasualLeave;
    @BindView(R.id.AutoComCasualLeave)
    AppCompatAutoCompleteTextView AutoComCasualLeave;
    @BindView(R.id.input_textSickLeave)
    TextInputLayout input_textSickLeave;
    @BindView(R.id.AutoComSickLeave)
    AppCompatAutoCompleteTextView AutoComSickLeave;
    @BindView(R.id.input_textOtherLeave)
    TextInputLayout input_textOtherLeave;
    @BindView(R.id.AutoComOtherLeave)
    AppCompatAutoCompleteTextView AutoComOtherLeave;
    @BindView(R.id.AutoComOfficeLocation)
    AppCompatAutoCompleteTextView AutoComOfficeLocation;
    Dialog mDialogDeactivate,mDialogDiscard;
    String from = "add", empId= "0",officeLat= "",officeLong="";
    @BindView(R.id.btnDeactivate)
    AppCompatButton btnDeactivate;
    EmployeeList employeeList;
    EmployeeTimeAdapter employeeTimeAdapter;
    List<WorkTimingList> timingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mContext = this;
        getDeps().inject(this);
        ButterKnife.bind(this);
        injectAPI();
        init();
    }

    private void init() {
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        // initialise tha layout
        setToolbar();
        //multiple line with done
        AutoComAddress.setImeOptions(EditorInfo.IME_ACTION_DONE);
        AutoComAddress.setRawInputType(InputType.TYPE_CLASS_TEXT);

        timingList.add(new WorkTimingList(true,getString(R.string.scr_lbl_mon),getString(R.string.scr_lbl_yes),"09:30:00","18:30:00"));
        timingList.add(new WorkTimingList(true,getString(R.string.scr_lbl_tue),getString(R.string.scr_lbl_yes),"09:30:00","18:30:00"));
        timingList.add(new WorkTimingList(true,getString(R.string.scr_lbl_wed),getString(R.string.scr_lbl_yes),"09:30:00","18:30:00"));
        timingList.add(new WorkTimingList(true,getString(R.string.scr_lbl_thur),getString(R.string.scr_lbl_yes),"09:30:00","18:30:00"));
        timingList.add(new WorkTimingList(true,getString(R.string.scr_lbl_fri),getString(R.string.scr_lbl_yes),"09:30:00","18:30:00"));
        timingList.add(new WorkTimingList(true,getString(R.string.scr_lbl_sat),getString(R.string.scr_lbl_yes),"09:30:00","18:30:00"));
        timingList.add(new WorkTimingList(true,getString(R.string.scr_lbl_sun),getString(R.string.scr_lbl_yes),"09:30:00","18:30:00"));
        setAdapterForTimingList();
        getIntentData();
        tvMissing.setVisibility(View.GONE);
        setDropdownGender();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void getIntentData(){
        Intent intent = getIntent();
        if(intent!=null){
            from = intent.getStringExtra(Constants.FROM_SCREEN);
            empId = intent.getStringExtra(Constants.TITLE);

            if(from.equals(Constants.add)){
                tvToolbarTitle.setText(R.string.scr_lbl_add_employees);
                btnDeactivate.setVisibility(View.GONE);
                callCompanyListApi();
            }
            else if(from.equals(Constants.edit)){
                tvToolbarTitle.setText(R.string.scr_lbl_manage_employee);
                btnDeactivate.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                employeeList = gson.fromJson(getIntent().getStringExtra(Constants.EMPLOYEE_OBJ), EmployeeList.class);
                empId = employeeList.getEmpId();
                callEmployeeListApi();
            }
        }
        else{
            tvToolbarTitle.setText(R.string.scr_lbl_manage_employee);
        }
    }

    private void injectAPI() {
        addEmployeeViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AddEmployeeViewModel.class);
        addEmployeeViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_add_employee));

        deactivateEmployeeViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DeactivateEmployeeViewModel.class);
        deactivateEmployeeViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_deactivate_employee));

        editEmployeeViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EditEmployeeViewModel.class);
        editEmployeeViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_edit_employee));

        employeeListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EmployeeListViewModel.class);
        employeeListViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_EMPLOYEES_LIST));

        getCompanyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetCompanyViewModel.class);
        getCompanyViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_COMPANY));
    }
    /* Call Api For employee list */
    private void callEmployeeListApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_employee_list);
                RequestBody mRequestComId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                RequestBody mRequestEmployee = RequestBody.create(MediaType.parse("text/plain"), empId);
                RequestBody mRequestToken = RequestBody.create(MediaType.parse("text/plain"),  loginTable.getToken());
                RequestBody mRequestpage_number = RequestBody.create(MediaType.parse("text/plain"), "0");
                RequestBody mRequestpage_size = RequestBody.create(MediaType.parse("text/plain"), Constants.PAG_SIZE);
                RequestBody mRequestfilter_emp_name = RequestBody.create(MediaType.parse("text/plain"), "");
                RequestBody mRequestfilter_emp_position = RequestBody.create(MediaType.parse("text/plain"),  "");
                String status = "1";
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

    /* Call Api For employee list */
    private void callCompanyListApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_company_details);
                RequestBody mRequestComId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                RequestBody mRequestpage_number = RequestBody.create(MediaType.parse("text/plain"), "0");
                RequestBody mRequestpage_size = RequestBody.create(MediaType.parse("text/plain"), Constants.PAG_SIZE);

                getCompanyViewModel.hitGetCompanyApi(mRequestAction,mRequestComId,
                        mRequestpage_number,mRequestpage_size);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    private void setAdapterForTimingList() {
            if (timingList!=null && timingList.size() > 0) {
            employeeTimeAdapter = new EmployeeTimeAdapter(true,mContext, timingList, new EmployeeTimeAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(WorkTimingList mDataTicket, List<WorkTimingList> workTimingListList, boolean status) {
                    timingList = workTimingListList;
                    //employeeTimeAdapter.updateList(timingList);
                }
            });
            rvSalesList.setHasFixedSize(true);
            rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            rvSalesList.setAdapter(employeeTimeAdapter);
            rvSalesList.setVisibility(View.VISIBLE);
        }
    }

    /* Call Api For add employee */
    private void callAddEmployeeApi() {
        if (NetworkCheck.isInternetAvailable(this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyTypeAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_add_employee);
                RequestBody mRequestBodyTypeEmpName = RequestBody.create(MediaType.parse("text/plain"),AutoComName.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpMob = RequestBody.create(MediaType.parse("text/plain"), AutoComMobile.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpEmail = RequestBody.create(MediaType.parse("text/plain"), AutoComEmailId.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpAddr = RequestBody.create(MediaType.parse("text/plain"), AutoComAddress.getText().toString().trim());//loginTable.getCompanyId());
                String dob = AppUtils.changeToSlashToDash(tvDateValue.getText().toString().trim());
                RequestBody mRequestBodyTypeEmpDob = RequestBody.create(MediaType.parse("text/plain"), dob);//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpGender = RequestBody.create(MediaType.parse("text/plain"), AutoComGender.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpPincode = RequestBody.create(MediaType.parse("text/plain"), AutoComPincode.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpPos = RequestBody.create(MediaType.parse("text/plain"), AutoComDesi.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeCreBy = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeToken = RequestBody.create(MediaType.parse("text/plain"), loginTable.getToken());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeSalary = RequestBody.create(MediaType.parse("text/plain"), AutoComCurrSalary.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyOtherLeave = RequestBody.create(MediaType.parse("text/plain"), AutoComOtherLeave.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyCasualLeave = RequestBody.create(MediaType.parse("text/plain"), AutoComCasualLeave.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodySickLeave = RequestBody.create(MediaType.parse("text/plain"), AutoComSickLeave.getText().toString().trim());//loginTable.getCompanyId());
                String join = AppUtils.changeToSlashToDash(tvJoiningDate.getText().toString().trim());
                RequestBody mRequestBodyJoiningDate = RequestBody.create(MediaType.parse("text/plain"),join);//loginTable.getCompanyId());
                RequestBody mRequestAddr = RequestBody.create(MediaType.parse("text/plain"),AutoComOfficeLocation.getText().toString());//loginTable.getCompanyId());
                RequestBody mRequestLat = RequestBody.create(MediaType.parse("text/plain"),officeLat);//loginTable.getCompanyId());
                RequestBody mRequestLong = RequestBody.create(MediaType.parse("text/plain"),officeLong);//loginTable.getCompanyId());
                RequestBody mRequestMon = RequestBody.create(MediaType.parse("text/plain"),timingList.get(0).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestTue = RequestBody.create(MediaType.parse("text/plain"),timingList.get(1).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestWed = RequestBody.create(MediaType.parse("text/plain"),timingList.get(2).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestThur = RequestBody.create(MediaType.parse("text/plain"),timingList.get(3).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestFri = RequestBody.create(MediaType.parse("text/plain"),timingList.get(4).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestSat = RequestBody.create(MediaType.parse("text/plain"),timingList.get(5).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestSun = RequestBody.create(MediaType.parse("text/plain"),timingList.get(6).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestMonStart = RequestBody.create(MediaType.parse("text/plain"),timingList.get(0).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestTueStart = RequestBody.create(MediaType.parse("text/plain"),timingList.get(1).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestWedStart = RequestBody.create(MediaType.parse("text/plain"),timingList.get(2).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestThurStart = RequestBody.create(MediaType.parse("text/plain"),timingList.get(3).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestFriStart = RequestBody.create(MediaType.parse("text/plain"),timingList.get(4).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestSatStart = RequestBody.create(MediaType.parse("text/plain"),timingList.get(5).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestSunStart = RequestBody.create(MediaType.parse("text/plain"),timingList.get(6).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestMonEnd = RequestBody.create(MediaType.parse("text/plain"),timingList.get(0).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestTueEnd = RequestBody.create(MediaType.parse("text/plain"),timingList.get(1).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestWedEnd = RequestBody.create(MediaType.parse("text/plain"),timingList.get(2).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestThurEnd = RequestBody.create(MediaType.parse("text/plain"),timingList.get(3).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestFriEnd = RequestBody.create(MediaType.parse("text/plain"),timingList.get(4).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestSatEnd = RequestBody.create(MediaType.parse("text/plain"),timingList.get(5).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestSunEnd = RequestBody.create(MediaType.parse("text/plain"),timingList.get(6).getMonEndTime());//loginTable.getCompanyId());

                AddEmployeeRequest request = new AddEmployeeRequest();
                request.setAction(mRequestBodyTypeAction);
                request.setEmpName(mRequestBodyTypeEmpName);
                request.setEmpMob(mRequestBodyTypeEmpMob);
                request.setEmpEmail(mRequestBodyTypeEmpEmail);
                request.setEmpAddr(mRequestBodyTypeEmpAddr);
                request.setEmpDob(mRequestBodyTypeEmpDob);
                request.setEmpGender(mRequestBodyTypeEmpGender);
                request.setEmpPincode(mRequestBodyTypeEmpPincode);
                request.setEmpPosition(mRequestBodyTypeEmpPos);
                request.setCreatedBy(mRequestBodyTypeCreBy);
                request.setCompanyID(mRequestBodyTypeComId);
                request.setToken(mRequestBodyTypeToken);
                request.setSalary(mRequestBodyTypeSalary);
                request.setOtherLeaves(mRequestBodyOtherLeave);
                request.setCasualLeaves(mRequestBodyCasualLeave);
                request.setSickLeaves(mRequestBodySickLeave);
                request.setJoiningDate(mRequestBodyJoiningDate);
                request.setOfficeAddress(mRequestAddr);
                request.setOfficeLatitude(mRequestLat);
                request.setOfficeLongitude(mRequestLong);
                request.setMonWorking(mRequestMon);
                request.setTueWorking(mRequestTue);
                request.setWedWorking(mRequestWed);
                request.setThursWorking(mRequestThur);
                request.setFri_working(mRequestFri);
                request.setSatWorking(mRequestSat);
                request.setSunWorking(mRequestSun);
                request.setMonStartTime(mRequestMonStart);
                request.setTueStartTime(mRequestTueStart);
                request.setWedStartTime(mRequestWedStart);
                request.setThursStartTime(mRequestThurStart);
                request.setFri_start_time(mRequestFriStart);
                request.setSatStartTime(mRequestSatStart);
                request.setSunStartTime(mRequestSunStart);
                request.setMonEndTime(mRequestMonEnd);
                request.setTueEndTime(mRequestTueEnd);
                request.setWedEndTime(mRequestWedEnd);
                request.setThursEndTime(mRequestThurEnd);
                request.setFri_end_time(mRequestFriEnd);
                request.setSatEndTime(mRequestSatEnd);
                request.setSunEndTime(mRequestSunEnd);
                addEmployeeViewModel.executeAddEmployeeAPI(request);
            }
            else {
                LogUtil.printToastMSG(this, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For edit employee */
    private void callEditEmployeeApi() {
        if (NetworkCheck.isInternetAvailable(this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyTypeAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_edit_employee);
                RequestBody mRequestBodyTypeEmpName = RequestBody.create(MediaType.parse("text/plain"),AutoComName.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpMob = RequestBody.create(MediaType.parse("text/plain"), AutoComMobile.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpEmail = RequestBody.create(MediaType.parse("text/plain"), AutoComEmailId.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpAddr = RequestBody.create(MediaType.parse("text/plain"), AutoComAddress.getText().toString().trim());//loginTable.getCompanyId());
                String dob = AppUtils.changeToSlashToDash(tvDateValue.getText().toString().trim());
                RequestBody mRequestBodyTypeEmpDob = RequestBody.create(MediaType.parse("text/plain"), dob);//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpGender = RequestBody.create(MediaType.parse("text/plain"), AutoComGender.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpPincode = RequestBody.create(MediaType.parse("text/plain"), AutoComPincode.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpPos = RequestBody.create(MediaType.parse("text/plain"), AutoComDesi.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeupdated_by = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeemp_id = RequestBody.create(MediaType.parse("text/plain"), empId);//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeSalary = RequestBody.create(MediaType.parse("text/plain"), AutoComCurrSalary.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyOtherLeave = RequestBody.create(MediaType.parse("text/plain"), AutoComOtherLeave.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyCasualLeave = RequestBody.create(MediaType.parse("text/plain"), AutoComCasualLeave.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodySickLeave = RequestBody.create(MediaType.parse("text/plain"), AutoComSickLeave.getText().toString().trim());//loginTable.getCompanyId());
                String join = AppUtils.changeToSlashToDash(tvJoiningDate.getText().toString().trim());
                RequestBody mRequestBodyJoiningDate = RequestBody.create(MediaType.parse("text/plain"),join);//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeToken = RequestBody.create(MediaType.parse("text/plain"), loginTable.getToken());//loginTable.getCompanyId());
                RequestBody mRequestAddr = RequestBody.create(MediaType.parse("text/plain"),AutoComOfficeLocation.getText().toString());//loginTable.getCompanyId());
                RequestBody mRequestLat = RequestBody.create(MediaType.parse("text/plain"),officeLat==null?"0.0":officeLat);//loginTable.getCompanyId());
                RequestBody mRequestLong = RequestBody.create(MediaType.parse("text/plain"),officeLong==null?"0.0":officeLong);//loginTable.getCompanyId());
                RequestBody mRequestMon = RequestBody.create(MediaType.parse("text/plain"),timingList.get(0).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestTue = RequestBody.create(MediaType.parse("text/plain"),timingList.get(1).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestWed = RequestBody.create(MediaType.parse("text/plain"),timingList.get(2).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestThur = RequestBody.create(MediaType.parse("text/plain"),timingList.get(3).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestFri = RequestBody.create(MediaType.parse("text/plain"),timingList.get(4).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestSat = RequestBody.create(MediaType.parse("text/plain"),timingList.get(5).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestSun = RequestBody.create(MediaType.parse("text/plain"),timingList.get(6).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestMonStart = RequestBody.create(MediaType.parse("text/plain"),timingList.get(0).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestTueStart = RequestBody.create(MediaType.parse("text/plain"),timingList.get(1).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestWedStart = RequestBody.create(MediaType.parse("text/plain"),timingList.get(2).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestThurStart = RequestBody.create(MediaType.parse("text/plain"),timingList.get(3).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestFriStart = RequestBody.create(MediaType.parse("text/plain"),timingList.get(4).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestSatStart = RequestBody.create(MediaType.parse("text/plain"),timingList.get(5).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestSunStart = RequestBody.create(MediaType.parse("text/plain"),timingList.get(6).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestMonEnd = RequestBody.create(MediaType.parse("text/plain"),timingList.get(0).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestTueEnd = RequestBody.create(MediaType.parse("text/plain"),timingList.get(1).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestWedEnd = RequestBody.create(MediaType.parse("text/plain"),timingList.get(2).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestThurEnd = RequestBody.create(MediaType.parse("text/plain"),timingList.get(3).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestFriEnd = RequestBody.create(MediaType.parse("text/plain"),timingList.get(4).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestSatEnd = RequestBody.create(MediaType.parse("text/plain"),timingList.get(5).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestSunEnd = RequestBody.create(MediaType.parse("text/plain"),timingList.get(6).getMonEndTime());//loginTable.getCompanyId());

                EditEmployeeRequest request = new EditEmployeeRequest();
                request.setAction(mRequestBodyTypeAction);
                request.setEmpName(mRequestBodyTypeEmpName);
                request.setEmpMob(mRequestBodyTypeEmpMob);
                request.setEmpEmail(mRequestBodyTypeEmpEmail);
                request.setEmpAddr(mRequestBodyTypeEmpAddr);
                request.setEmpDob(mRequestBodyTypeEmpDob);
                request.setEmpGender(mRequestBodyTypeEmpGender);
                request.setEmpPincode(mRequestBodyTypeEmpPincode);
                request.setEmpPosition(mRequestBodyTypeEmpPos);
                request.setUpdatedBy(mRequestBodyTypeupdated_by);
                request.setCompanyID(mRequestBodyTypeComId);
                request.setEmpId(mRequestBodyTypeemp_id);
                request.setSalary(mRequestBodyTypeSalary);
                request.setOtherLeaves(mRequestBodyOtherLeave);
                request.setCasualLeaves(mRequestBodyCasualLeave);
                request.setSickLeaves(mRequestBodySickLeave);
                request.setJoiningDate(mRequestBodyJoiningDate);
                request.setToken(mRequestBodyTypeToken);
                request.setOfficeAddress(mRequestAddr);
                request.setOfficeLatitude(mRequestLat);
                request.setOfficeLongitude(mRequestLong);
                request.setMonWorking(mRequestMon);
                request.setTueWorking(mRequestTue);
                request.setWedWorking(mRequestWed);
                request.setThursWorking(mRequestThur);
                request.setFri_working(mRequestFri);
                request.setSatWorking(mRequestSat);
                request.setSunWorking(mRequestSun);
                request.setMonStartTime(mRequestMonStart);
                request.setTueStartTime(mRequestTueStart);
                request.setWedStartTime(mRequestWedStart);
                request.setThursStartTime(mRequestThurStart);
                request.setFri_start_time(mRequestFriStart);
                request.setSatStartTime(mRequestSatStart);
                request.setSunStartTime(mRequestSunStart);
                request.setMonEndTime(mRequestMonEnd);
                request.setTueEndTime(mRequestTueEnd);
                request.setWedEndTime(mRequestWedEnd);
                request.setThursEndTime(mRequestThurEnd);
                request.setFri_end_time(mRequestFriEnd);
                request.setSatEndTime(mRequestSatEnd);
                request.setSunEndTime(mRequestSunEnd);
                editEmployeeViewModel.hitEditEmployeeAPI(request);
            }
            else {
                LogUtil.printToastMSG(this, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For deactivate employee */
    private void callDeactivateEmployeeApi() {
        if (NetworkCheck.isInternetAvailable(this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyTypeAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_deactivate_employee);
                RequestBody mRequestBodyUpdatedBy = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());//loginTable.getCompanyId());
                RequestBody mRequestBodyEmpId = RequestBody.create(MediaType.parse("text/plain"), empId);//loginTable.getCompanyId());

                deactivateEmployeeViewModel.executeDeactivateEmployeeAPI(mRequestBodyTypeAction,
                        mRequestBodyUpdatedBy,mRequestBodyEmpId);
            }
            else {
                LogUtil.printToastMSG(this, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    private void setToolbar() {
        //set toolbar title
        tvToolbarTitle.setText(R.string.scr_lbl_employees);
        ((BaseActivity) mContext).initToolbar(5, mContext, R.id.imgBack, R.id.imgReport, R.id.imgNotify, tvNotifyCount, R.id.imgBack, R.id.imgCall);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
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
    @OnClick({R.id.btnSubmit,R.id.btnCancel,R.id.btnDeactivate,R.id.layCalender,
            R.id.imgToDate,R.id.layJoiningDate, R.id.imgJoiningDate,R.id.layAddLocation})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnSubmit:
                if(isDetailsValid()) {
                    if(from.equals(Constants.add)) {
                        callAddEmployeeApi();
                    }else if(from.equals(Constants.edit)) {
                        callEditEmployeeApi();
                    }
                }
                else{
                    tvMissing.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.layCalender:
                openDataPicker(0);
                break;
            case R.id.layJoiningDate:
                openDataPicker(1);
                break;
            case R.id.btnCancel:
                showDiscardDialog();
                break;
            case R.id.btnDeactivate:
                showDeactivateAccountDialog(this);
                break;
            case R.id.layAddLocation:
                int LAUNCH_SECOND_ACTIVITY = 1000;
                Intent i = new Intent(this, AddLocationActivity.class);
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
                //launchScreen(mContext,AddLocationActivity.class);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if(resultCode == Activity.RESULT_OK){
                String locationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ENTERED_VISIT_LAT, "0.0");
                String locationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ENTERED_VISIT_LNG, "0.0");
                String startlocationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.START_VISIT_LAT, "0.0");
                String startlocationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.START_VISIT_LNG, "0.0");
                String endlocationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.END_VISIT_LAT, "0.0");
                String endlocationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.END_VISIT_LNG, "0.0");
                String location = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.LOCATION_ENTERED_TXT, "Not Available");
                String result=data.getStringExtra("result");
                //String str = "<b>"+result+"</b>";
                AutoComOfficeLocation.setText(location);
                officeLat = locationLat;
                officeLong = locationLng;
                //tvAddLocation.setText(location);
                /*double km = distance(Double.parseDouble(locationLat),Double.parseDouble(startlocationLat),
                        Double.parseDouble(locationLng),Double.parseDouble(startlocationLng));*/
                //LogUtil.printToastMSG(mContext,km+" K.M.");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }

    //set value to gender dropdown
    private void setDropdownGender() {
        List<String> mGenderDropdown = new ArrayList<>();
        mGenderDropdown.add("Male");
        mGenderDropdown.add("Female");
        mGenderDropdown.add("Other");

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
                AutoComGender.setAdapter(adapter);
                //mSelectedColor = mDropdownList[pos];
                AutoComGender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //mSelectedColor = mDropdownList[position];
                        AppUtils.hideKeyBoard(AddEmployeeActivity.this);
                    }
                });
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showDeactivateAccountDialog(Activity mContext) {
        mDialogDeactivate = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogDeactivate.setContentView(R.layout.dialog_deactive_account);
        mDialogDeactivate.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialogDeactivate.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialogDeactivate.findViewById(R.id.uploadButton);
        AppCompatButton cancelButton = mDialogDeactivate.findViewById(R.id.cancelButton);

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callDeactivateEmployeeApi();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDeactivate.dismiss();
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDeactivate.dismiss();
            }
        });
        mDialogDeactivate.show();
    }

    public void showDiscardDialog() {
        mDialogDiscard = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogDiscard.setContentView(R.layout.dialog_deactive_account);
        mDialogDiscard.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialogDiscard.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialogDiscard.findViewById(R.id.uploadButton);
        AppCompatButton cancelButton = mDialogDiscard.findViewById(R.id.cancelButton);
        AppCompatTextView tvTitle = mDialogDiscard.findViewById(R.id.tvStart);
        tvTitle.setText("Are you sure you want to discard this changes ?");
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDiscard.dismiss();
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogDiscard.dismiss();
            }
        });
        mDialogDiscard.show();
    }
    /*check validations on field*/
    private boolean isDetailsValid() {
        setError(0, input_textName, "");  setError(0, input_textEmailId, "");
        setError(0, input_textAddress, ""); setError(0, input_textGender, "");
        setError(0, input_textPincode, "");setError(0, input_textCurrSalary, "");
        setError(0, input_textDesi, ""); setError(0, input_textMobile, "");
        setError(0, input_textCasualLeave, "");setError(0, input_textSickLeave, "");
        setError(0, input_textOtherLeave,"");
        //tvDateValue.setError("Please select date of birth.",getDrawable(R.drawable.ic_calendar));
        if (TextUtils.isEmpty(AutoComName.getText().toString().trim())) {
            setError(0, input_textName, getString(R.string.err_enter_name));
            return false;
        } else if (TextUtils.isEmpty(AutoComAddress.getText().toString().trim())) {
            setError(0, input_textAddress, getString(R.string.err_enter_address));
            return false;
        } else if (TextUtils.isEmpty(AutoComPincode.getText().toString().trim())) {
            setError(0, input_textPincode, getString(R.string.err_enter_pincode));
            return false;
        } else if (TextUtils.isEmpty(AutoComDesi.getText().toString().trim())) {
            setError(0, input_textDesi, getString(R.string.err_enter_designation));
            return false;
        } else if (TextUtils.isEmpty(AutoComEmailId.getText().toString().trim()) || !Patterns.EMAIL_ADDRESS.matcher(AutoComEmailId.getText().toString().trim()).matches())
         {
            setError(0, input_textEmailId, getString(R.string.err_enter_email_id));
            return false;
        } else if (TextUtils.isEmpty(AutoComGender.getText().toString().trim())) {
            setError(0, input_textGender, getString(R.string.err_select_gender));
            return false;
        } else if (TextUtils.isEmpty(AutoComCurrSalary.getText().toString().trim())) {
            setError(0, input_textCurrSalary, getString(R.string.err_enter_current_salary));
            return false;
        }  else if (TextUtils.isEmpty(AutoComMobile.getText().toString().trim())) {
            setError(0, input_textMobile, getString(R.string.err_enter_mobile_no));
            return false;
        } else if (TextUtils.isEmpty(tvDateValue.getText().toString().trim())) {
            tvDateValue.setError("Please select date of birth.",getDrawable(R.drawable.ic_calendar));
            return false;
        } else if (TextUtils.isEmpty(tvJoiningDate.getText().toString().trim())) {
            tvJoiningDate.setError("Please select joining date.",getDrawable(R.drawable.ic_calendar));
            return false;
        } else if (TextUtils.isEmpty(AutoComCasualLeave.getText().toString().trim())) {
            setError(0, input_textCasualLeave, getString(R.string.err_enter_casual_leave));
            return false;
        } else if (TextUtils.isEmpty(AutoComSickLeave.getText().toString().trim())) {
            setError(0, input_textSickLeave, getString(R.string.err_enter_sick_leave));
            return false;
        } else if (TextUtils.isEmpty(AutoComOtherLeave.getText().toString().trim())) {
            setError(0, input_textOtherLeave, getString(R.string.err_enter_other_leave));
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
                if(val==0) {
                    tvDateValue.setText(sdf.format(myCalendar.getTime()));
                }else{
                    tvJoiningDate.setText(sdf.format(myCalendar.getTime()));
                }
            }

        };

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    /*Api response */
    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {

            case LOADING:
                showSmallProgressBar(mProgressBarHolder);
                break;

            case SUCCESS:
                dismissSmallProgressBar(mProgressBarHolder);
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.action_add_employee)) {
                            AddEmployeeResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), AddEmployeeResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                showSuccessDialog("Employee Added successfully !",false,AddEmployeeActivity.this);
                                  }
                            else {
                                showSuccessDialog(responseModel.getResult().getMessage(),true,AddEmployeeActivity.this);
                                //LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.action_edit_employee)) {
                            EditEmployeeResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), EditEmployeeResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                showSuccessDialog("Employee Edited successfully !",false,AddEmployeeActivity.this);
                            }
                            else {
                                LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.action_deactivate_employee)) {
                            DeactivateEmployeeResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), DeactivateEmployeeResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                mDialogDeactivate.dismiss();
                                showSuccessDialog("Account deactived successfully.",false,AddEmployeeActivity.this);
                            }
                            else {
                                LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_COMPANY)) {
                            GetCompanyResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetCompanyResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                GetCompanyList employeeList = responseModel.getResult().getList().get(0);
                                AutoComOfficeLocation.setText(employeeList.getOfficeAddress());
                                officeLat = employeeList.getOfficeLatitude();
                                officeLong = employeeList.getOfficeLongitude();
                                try{ timingList.clear();}catch (Exception e){}
                                timingList.add(new WorkTimingList(false,getString(R.string.scr_lbl_mon), employeeList.getMonWorking()==null?"no":employeeList.getMonWorking(),
                                        employeeList.getMonStartTime()==null?getString(R.string.scr_lbl_start_time):employeeList.getMonStartTime(), employeeList.getMonEndTime()==null?getString(R.string.scr_lbl_end_time):employeeList.getMonEndTime()));
                                timingList.add(new WorkTimingList(false,getString(R.string.scr_lbl_tue), employeeList.getTueWorking()==null?"no":employeeList.getTueWorking(),
                                        employeeList.getTueStartTime()==null?getString(R.string.scr_lbl_start_time):employeeList.getTueStartTime(), employeeList.getTueEndTime()==null?getString(R.string.scr_lbl_end_time):employeeList.getTueEndTime()));
                                timingList.add(new WorkTimingList(false,getString(R.string.scr_lbl_wed), employeeList.getWedWorking()==null?"no":employeeList.getWedWorking(),
                                        employeeList.getWedStartTime()==null?getString(R.string.scr_lbl_start_time):employeeList.getWedStartTime(), employeeList.getWedEndTime()==null?getString(R.string.scr_lbl_end_time):employeeList.getWedEndTime()));
                                timingList.add(new WorkTimingList(false,getString(R.string.scr_lbl_thur), employeeList.getThursWorking()==null?"no":employeeList.getThursWorking(),
                                        employeeList.getThursStartTime()==null?getString(R.string.scr_lbl_start_time):employeeList.getThursStartTime(), employeeList.getThursEndTime()==null?getString(R.string.scr_lbl_end_time):employeeList.getThursEndTime()));
                                timingList.add(new WorkTimingList(false,getString(R.string.scr_lbl_fri), employeeList.getFriWorking()==null?"no":employeeList.getFriWorking(),
                                        employeeList.getFriStartTime()==null?getString(R.string.scr_lbl_start_time):employeeList.getFriStartTime(), employeeList.getFriEndTime()==null?getString(R.string.scr_lbl_end_time):employeeList.getFriEndTime()));
                                timingList.add(new WorkTimingList(false,getString(R.string.scr_lbl_sat), employeeList.getSatWorking()==null?"no":employeeList.getSatWorking(),
                                        employeeList.getSatStartTime()==null?getString(R.string.scr_lbl_start_time):employeeList.getSatStartTime(), employeeList.getSatEndTime()==null?getString(R.string.scr_lbl_end_time):employeeList.getSatEndTime()));
                                timingList.add(new WorkTimingList(false,getString(R.string.scr_lbl_sun), employeeList.getSunWorking()==null?"no":employeeList.getSunWorking(),
                                        employeeList.getSunStartTime()==null?getString(R.string.scr_lbl_start_time):employeeList.getSunStartTime(), employeeList.getSunEndTime()==null?getString(R.string.scr_lbl_end_time):employeeList.getSunEndTime()));
                                setAdapterForTimingList();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_EMPLOYEES_LIST)) {
                            EmployeeListResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), EmployeeListResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                EmployeeList employeeResList = responseModel.getResult().getList().get(0);
                                AutoComName.setText(employeeResList.getEmpName());
                                AutoComEmailId.setText(employeeResList.getEmpEmail());
                                AutoComMobile.setText(employeeResList.getEmpMob());
                                AutoComDesi.setText(employeeResList.getEmpPosition());
                                AutoComGender.setText(employeeResList.getEmpGender());
                                setDropdownGender();
                                tvDateValue.setText(AppUtils.dateConvertYYYYToDD(employeeResList.getEmpDob()));
                                AutoComAddress.setText(employeeResList.getEmpAddr());
                                AutoComPincode.setText(employeeResList.getEmpPincode());
                                AutoComCurrSalary.setText(employeeResList.getSalary());
                                tvJoiningDate.setText(AppUtils.dateConvertYYYYToDD(employeeResList.getJoiningDate()));
                                AutoComCasualLeave.setText(employeeResList.getCasualLeaves());
                                AutoComSickLeave.setText(employeeResList.getSickLeaves());
                                AutoComOtherLeave.setText(employeeResList.getOtherLeaves());
                                empId = employeeResList.getEmpId();
                                AutoComOfficeLocation.setText(employeeResList.getOfficeAddress());
                                officeLat = employeeResList.getOfficeLatitude();
                                officeLong = employeeResList.getOfficeLongitude();
                                try{ timingList.clear();}catch (Exception e){}
                                timingList.add(new WorkTimingList(false,getString(R.string.scr_lbl_mon), employeeResList.getMonWorking()==null?"no":employeeResList.getMonWorking(),
                                        employeeResList.getMonStartTime()==null?getString(R.string.scr_lbl_start_time):employeeResList.getMonStartTime(), employeeResList.getMonEndTime()==null?getString(R.string.scr_lbl_end_time):employeeResList.getMonEndTime()));
                                timingList.add(new WorkTimingList(false,getString(R.string.scr_lbl_tue), employeeResList.getTueWorking()==null?"no":employeeResList.getTueWorking(),
                                        employeeResList.getTueStartTime()==null?getString(R.string.scr_lbl_start_time):employeeResList.getTueStartTime(), employeeResList.getTueEndTime()==null?getString(R.string.scr_lbl_end_time):employeeResList.getTueEndTime()));
                                timingList.add(new WorkTimingList(false,getString(R.string.scr_lbl_wed), employeeResList.getWedWorking()==null?"no":employeeResList.getWedWorking(),
                                        employeeResList.getWedStartTime()==null?getString(R.string.scr_lbl_start_time):employeeResList.getWedStartTime(), employeeResList.getWedEndTime()==null?getString(R.string.scr_lbl_end_time):employeeResList.getWedEndTime()));
                                timingList.add(new WorkTimingList(false,getString(R.string.scr_lbl_thur), employeeResList.getThrusWorking()==null?"no":employeeResList.getThrusWorking(),
                                        employeeResList.getThrusStartTime()==null?getString(R.string.scr_lbl_start_time):employeeResList.getThrusStartTime(), employeeResList.getThrusEndTime()==null?getString(R.string.scr_lbl_end_time):employeeResList.getThrusEndTime()));
                                timingList.add(new WorkTimingList(false,getString(R.string.scr_lbl_fri), employeeResList.getFriWorking()==null?"no":employeeResList.getFriWorking(),
                                        employeeResList.getFriStartTime()==null?getString(R.string.scr_lbl_start_time):employeeResList.getFriStartTime(), employeeResList.getFriEndTime()==null?getString(R.string.scr_lbl_end_time):employeeResList.getFriEndTime()));
                                timingList.add(new WorkTimingList(false,getString(R.string.scr_lbl_sat), employeeResList.getSatWorking()==null?"no":employeeResList.getSatWorking(),
                                        employeeResList.getSatStartTime()==null?getString(R.string.scr_lbl_start_time):employeeResList.getSatStartTime(), employeeResList.getSatEndTime()==null?getString(R.string.scr_lbl_end_time):employeeResList.getSatEndTime()));
                                timingList.add(new WorkTimingList(false,getString(R.string.scr_lbl_sun), employeeResList.getSunWorking()==null?"no":employeeResList.getSunWorking(),
                                        employeeResList.getSunStartTime()==null?getString(R.string.scr_lbl_start_time):employeeResList.getSunStartTime(), employeeResList.getSunEndTime()==null?getString(R.string.scr_lbl_end_time):employeeResList.getSunEndTime()));
                                setAdapterForTimingList();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case ERROR:
                dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }


}