package com.ominfo.hra_app.ui.my_account;

import static com.ominfo.hra_app.util.AppUtils.getChangeDateForHisab;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.data.BarData;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.basecontrol.BaseFragment;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.interfaces.Constants;
import com.ominfo.hra_app.interfaces.ErrorCallbacks;
import com.ominfo.hra_app.interfaces.SharedPrefKey;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.hra_app.ui.dashboard.model.DashModel;
import com.ominfo.hra_app.ui.employees.adapter.EmployeeTimeAdapter;
import com.ominfo.hra_app.ui.employees.model.EmployeeList;
import com.ominfo.hra_app.ui.employees.model.EmployeeListRequest;
import com.ominfo.hra_app.ui.employees.model.EmployeeListResponse;
import com.ominfo.hra_app.ui.employees.model.EmployeeListViewModel;
import com.ominfo.hra_app.ui.login.LoginActivity;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.login.model.LogoutResponse;
import com.ominfo.hra_app.ui.login.model.LogoutViewModel;
import com.ominfo.hra_app.ui.my_account.adapter.AccountAdapter;
import com.ominfo.hra_app.ui.my_account.leave.LeaveListFragment;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveRequest;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveResponse;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveViewModel;
import com.ominfo.hra_app.ui.my_account.model.ChangePasswordResponse;
import com.ominfo.hra_app.ui.my_account.model.ChangePasswordViewModel;
import com.ominfo.hra_app.ui.my_account.model.ChangeProfileImageResponse;
import com.ominfo.hra_app.ui.my_account.model.ChangeProfileImageViewModel;
import com.ominfo.hra_app.ui.my_account.model.GetCompanyList;
import com.ominfo.hra_app.ui.my_account.model.GetCompanyResponse;
import com.ominfo.hra_app.ui.my_account.model.GetCompanyViewModel;
import com.ominfo.hra_app.ui.my_account.model.GetProfileImageViewModel;
import com.ominfo.hra_app.ui.my_account.model.ProfileImageResponse;
import com.ominfo.hra_app.ui.my_account.model.ProfileRequest;
import com.ominfo.hra_app.ui.my_account.model.ProfileResponse;
import com.ominfo.hra_app.ui.my_account.model.ProfileViewModel;
import com.ominfo.hra_app.ui.my_account.model.WorkTimingList;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.sales_credit.activity.PdfPrintActivity;
import com.ominfo.hra_app.ui.sales_credit.model.GraphModel;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;
import com.ominfo.hra_app.util.RealPathUtils;
import com.ominfo.hra_app.util.SharedPref;
import com.ominfo.hra_app.util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
//https://github.com/PhilJay/MPAndroidChart/wiki/Modifying-the-Viewport

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 * Use the {@link MyAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAccountFragment extends BaseFragment {

    Context mContext;
    AccountAdapter accountAdapter;
    //AddTagAdapter addTagAdapter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.btnNext)
    AppCompatButton btnNext;
    @BindView(R.id.btnSubmit)
    AppCompatButton btnSubmit;
    @BindView(R.id.layAdmin)
    LinearLayoutCompat layAdmin;
    @BindView(R.id.layCalender)
    RelativeLayout layCalender;
    @BindView(R.id.btnBack)
    AppCompatButton btnBack;
    @BindView(R.id.btnEBack)
    AppCompatButton btnEBack;
    @BindView(R.id.layUser)
    LinearLayoutCompat layUser;
    @BindView(R.id.laySubmit)
    LinearLayoutCompat laySubmit;
    @BindView(R.id.btnESubmit)
    AppCompatButton btnESubmit;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout app_bar_layout;

    @BindView(R.id.tvSearchView)
    AppCompatTextView tvToolbarTitle;
    @BindView(R.id.imgBack)
    LinearLayoutCompat imgBack;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
    BarData barData;
    private boolean isUpload = false;

    List<DashModel> dashboardList = new ArrayList<>();
    List<DashModel> tagList = new ArrayList<>();
    List<GraphModel> graphModelsList = new ArrayList<>();
    @Inject
    ViewModelFactory mViewModelFactory;
    private ProfileViewModel profileViewModel;
    private GetProfileImageViewModel getProfileImageViewModel;
    private ChangePasswordViewModel changePasswordViewModel;
    private ApplyLeaveViewModel applyLeaveViewModel;
    private ChangeProfileImageViewModel changeProfileImageViewModel;
    private LogoutViewModel logoutViewModel;
    private EmployeeListViewModel employeeListViewModel;
    private GetCompanyViewModel getCompanyViewModel;

    final Calendar myCalendar = Calendar.getInstance();
    private AppDatabase mDb;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.tvEmpName)
    AppCompatAutoCompleteTextView tvEmpName;
    @BindView(R.id.imgEmp)
    CircleImageView imgEmp;
    @BindView(R.id.imgAdd)
    AppCompatImageView imgAdd;
    private Uri picUri;
    private String tempUri;
    int cam = 0,noOFDays = 0;
    private int SELECT_FILE = 1;
    private static final int REQUEST_CAMERA = 0;
    private String base64Path = "",isAdmin="0";
    private Dialog mDialogChangePass;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    LinearLayoutCompat layoutLeaveTime;
    AppCompatTextView appcomptextLeaveTime;
    AppCompatTextView appcomptextNoOfDays;
    AppCompatTextView tvDateValueFrom;
    AppCompatTextView tvDateValueTo,tvTimeValueFrom,tvTimeValueTo ;
    AppCompatAutoCompleteTextView AutoComTextViewComment;
    View viewToDate;
    RelativeLayout layToDate;
    AppCompatAutoCompleteTextView AutoComTextViewDuration;
    AppCompatAutoCompleteTextView AutoComTextViewLeaveType;
    Dialog mDialogLogout;
    EmployeeTimeAdapter employeeTimeAdapter;
    @BindView(R.id.rvSalesList)
    RecyclerView rvSalesList;
    @BindView(R.id.AutoComtEEmail)
    AppCompatAutoCompleteTextView AutoComtEEmail;
    @BindView(R.id.AutoComEMobile)
    AppCompatAutoCompleteTextView AutoComEMobile;
    @BindView(R.id.AutoComGstNo)
    AppCompatAutoCompleteTextView AutoComGstNo;
    @BindView(R.id.AutoComMobile)
    AppCompatAutoCompleteTextView AutoComMobile;
    @BindView(R.id.AutoComEmailId)
    AppCompatAutoCompleteTextView AutoComEmailId;
    @BindView(R.id.AutoComStaff)
    AppCompatAutoCompleteTextView AutoComStaff;
    @BindView(R.id.AutoComOfficeLocation)
    AppCompatAutoCompleteTextView AutoComOfficeLocation;
    @BindView(R.id.AutoComPrefix)
    AppCompatAutoCompleteTextView AutoComPrefix;
    @BindView(R.id.AutoComEDesi)
    AppCompatAutoCompleteTextView AutoComEDesi;
    @BindView(R.id.AutoComtEGender)
    AppCompatAutoCompleteTextView AutoComtEGender;
    @BindView(R.id.AutoComPincode)
    AppCompatAutoCompleteTextView AutoComPincode;
    @BindView(R.id.AutoComOPincode)
    AppCompatAutoCompleteTextView AutoComOPincode;
    @BindView(R.id.tvDateValue)
    AppCompatTextView tvDateValue;
    @BindView(R.id.imgToDate)
    AppCompatImageView imgToDate;
    @BindView(R.id.etDescr)
    AppCompatEditText etDescr;
    @BindView(R.id.etOfAddress)
    AppCompatEditText etOfAddress;
    List<WorkTimingList> workTimingLists = new ArrayList<>();

    public MyAccountFragment() {
        // Required empty public constructor
    }

    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
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
        View view = inflater.inflate(R.layout.activity_my_account, container, false);
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
        app_bar_layout.addOnOffsetChangedListener(new   AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == 0 || verticalOffset <= mToolbar.getHeight() /*&& !mToolbar.getTitle().equals(mCollapsedTitle)*/){
                    mCollapsingToolbar.setTitle("mCollapsedTitle");
                }
                if(verticalOffset >= mToolbar.getHeight()){
                }
                if (verticalOffset == -mCollapsingToolbar.getHeight() + mToolbar.getHeight()) {
                    //toolbar is collapsed here
                    //write your code here
                    mToolbar.setBackground(mContext.getResources().getDrawable(R.drawable.bg_my_account_2));
                }
                else{
                    mToolbar.setBackground(null);
                    //LogUtil.printToastMSG(mContext,"Lets 3");
                }
            }
        });
    }

    private void injectAPI() {

        profileViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ProfileViewModel.class);
        profileViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_PROFILE));

        getProfileImageViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetProfileImageViewModel.class);
        getProfileImageViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_PROFILE));

        changeProfileImageViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ChangeProfileImageViewModel.class);
        changeProfileImageViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_CHANGE_PROFILE));

        changePasswordViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ChangePasswordViewModel.class);
        changePasswordViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_CHANGE_PASS));

        applyLeaveViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ApplyLeaveViewModel.class);
        applyLeaveViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_APPLY_LEAVE));

        logoutViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LogoutViewModel.class);
        logoutViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_LOGOUT));

        employeeListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EmployeeListViewModel.class);
        employeeListViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_EMPLOYEES_LIST));

        getCompanyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetCompanyViewModel.class);
        getCompanyViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_COMPANY));
    }

    /* Call Api For employee list */
    private void callEmployeeListApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_employee_list);
                RequestBody mRequestComId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                RequestBody mRequestEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
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

    private void setAdapterForTimingList(boolean isToggle) {
        if (workTimingLists!=null && workTimingLists.size() > 0) {
            employeeTimeAdapter = new EmployeeTimeAdapter(isToggle,mContext, workTimingLists, new EmployeeTimeAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(WorkTimingList mDataTicket, List<WorkTimingList> notificationResultListAdapter, boolean status) {
                }
            });
            rvSalesList.setHasFixedSize(true);
            rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            rvSalesList.setAdapter(employeeTimeAdapter);
            rvSalesList.setVisibility(View.VISIBLE);
        }
    }

    /* Call Api For change password */
    private void callChangePassApi(String oldPass , String newPass) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_change_pass);
                RequestBody mRequestBodyTypeCompId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                RequestBody mRequestBodyTypeOld = RequestBody.create(MediaType.parse("text/plain"), oldPass);
                RequestBody mRequestBodyTypeNew = RequestBody.create(MediaType.parse("text/plain"), newPass);

                changePasswordViewModel.executeChangePasswordAPI(mRequestBodyType,mRequestBodyTypeCompId,
                        mRequestBodyTypeEmployee,mRequestBodyTypeOld,mRequestBodyTypeNew);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Logout */
    private void callLogoutApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_logout);
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                logoutViewModel.hitLogoutApi(mRequestBodyAction,mRequestBodyTypeEmployee);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }


    /* Call Api For Apply Leave */
    private void callApplyLeaveApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_apply_leave);
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                RequestBody mRequestBodyDuration = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewDuration.getText().toString());
                String startTimeStamp = "",endTimeStamp = "",
                        startDateTimeStamp = AppUtils.changeToSlashToDash(tvDateValueFrom.getText().toString()),
                        endDateTimeStamp = AppUtils.changeToSlashToDash(tvDateValueTo.getText().toString());

                if(AutoComTextViewDuration.getText().toString().equals("Half Day"))
                {
                    startTimeStamp = AppUtils.convert12to24ForAttention(tvTimeValueFrom.getText().toString());
                    endTimeStamp = AppUtils.convert12to24ForAttention(tvTimeValueTo.getText().toString());
                } else  if(AutoComTextViewDuration.getText().toString().equals("Full Day"))
                {
                    startTimeStamp = "00:00:00";endTimeStamp = "23:59:00";
                }else {
                    startTimeStamp = "00:00:00";endTimeStamp = "23:59:00";
                }

                RequestBody mRequestBodyStartTime = RequestBody.create(MediaType.parse("text/plain"), startDateTimeStamp+" "+startTimeStamp);
                RequestBody mRequestBodyEndTime = RequestBody.create(MediaType.parse("text/plain"), endDateTimeStamp+" "+endTimeStamp);
                RequestBody mRequestBodyLeaveType = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewLeaveType.getText().toString());
                RequestBody mRequestBodyComment = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewComment.getText().toString());
               // RequestBody mRequestBodyLeaveStatus = RequestBody.create(MediaType.parse("text/plain") , "approved");
                ///RequestBody mRequestBodyUpdatedBy = RequestBody.create(MediaType.parse("text/plain"),"12"  /*loginTable.getManagerId()*/);

                ApplyLeaveRequest applyLeaveRequest = new ApplyLeaveRequest();
                applyLeaveRequest.setAction(mRequestBodyAction);
                applyLeaveRequest.setEmpId(mRequestBodyTypeEmpId);
                applyLeaveRequest.setDuration(mRequestBodyDuration);
                applyLeaveRequest.setStartTime(mRequestBodyStartTime);
                applyLeaveRequest.setEndTime(mRequestBodyEndTime);
                applyLeaveRequest.setLeaveType(mRequestBodyLeaveType);
                applyLeaveRequest.setComment(mRequestBodyComment);
               // applyLeaveRequest.setLeaveStatus(mRequestBodyLeaveStatus);
                //applyLeaveRequest.setUpdatedBy(mRequestBodyUpdatedBy);
                applyLeaveViewModel.hitApplyLeaveApi(applyLeaveRequest);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    private void init(){
        tvEmpName.setEnabled(false);
        LoginTable loginTable = mDb.getDbDAO().getLoginData();
        if(loginTable!=null){
            isAdmin = loginTable.getIsadmin();
            if(isAdmin.equals("0")){
                laySubmit.setVisibility(View.GONE);
                layUser.setVisibility(View.VISIBLE);
                layAdmin.setVisibility(View.GONE);
                setAllDisabled(0,false);
                callEmployeeListApi();
            }
            else{
                laySubmit.setVisibility(View.GONE);
                layUser.setVisibility(View.GONE);
                layAdmin.setVisibility(View.VISIBLE);
                setAllDisabled(1,false);
                callCompanyListApi();
            }
        }
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);//getActivity().getResources().getColor(R.color.black));
        setToolbar();
        cam=2;
        requestPermission();
        callProfileImageApi();
        callProfileApi();
    }
    //request camera and storage permission
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mContext.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || mContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || mContext.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]
                                {
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                },
                        1001);

            } else {
                reqPermissionCode();
            }
        } else {
            reqPermissionCode();
        }
    }
    private void reqPermissionCode(){
        if(cam==0) {
            cameraIntent();
        }else if(cam==1) {
            galleryIntent();
        }else if(cam==2) {
            deleteImagesFolder();
        }
    }
    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
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
                AutoComtEGender.setAdapter(adapter);
                //mSelectedColor = mDropdownList[pos];
                AutoComtEGender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //mSelectedColor = mDropdownList[position];
                        //AppUtils.hideKeyBoard(AddEmployeeActivity.this);
                    }
                });
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //perform click actions
    @OnClick({R.id.imgEdit,R.id.imgAdd,R.id.btnNext,R.id.btnSubmit,R.id.btnBack,R.id.btnEBack,R.id.btnENext
            ,R.id.layCalender,R.id.imgCall,R.id.btnESubmit})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layCalender:
                openDataPicker(0);
                break;
            case R.id.imgEdit:
                tvEmpName.setEnabled(true);
                tvEmpName.setBackground(mContext.getDrawable(R.drawable.round_corner_shape_without_fill_thin_yellow));
                break;
            case R.id.imgCall:
                //edit all
                setAllDisabled(Integer.parseInt(isAdmin),true);
                break;
            case R.id.imgAdd:
                showChooseCameraDialog();
                break;
            case R.id.btnNext:
                layAdmin.setVisibility(View.GONE);
                laySubmit.setVisibility(View.VISIBLE);
                layUser.setVisibility(View.GONE);
                btnEBack.setVisibility(View.GONE);
                btnESubmit.setVisibility(View.GONE);
                setAdapterForTimingList(true);
                break;
            case R.id.btnENext:
                layAdmin.setVisibility(View.GONE);
                laySubmit.setVisibility(View.VISIBLE);
                layUser.setVisibility(View.GONE);
                btnBack.setVisibility(View.GONE);
                btnSubmit.setVisibility(View.GONE);
                setAdapterForTimingList(false);
                break;
            case R.id.btnSubmit:
                layAdmin.setVisibility(View.VISIBLE);
                laySubmit.setVisibility(View.GONE);
                layUser.setVisibility(View.GONE);
                break;
            case R.id.btnESubmit:
                layAdmin.setVisibility(View.GONE);
                laySubmit.setVisibility(View.GONE);
                layUser.setVisibility(View.VISIBLE);
                break;
            case R.id.btnBack:
                layAdmin.setVisibility(View.VISIBLE);
                laySubmit.setVisibility(View.GONE);
                layUser.setVisibility(View.GONE);
                break;
            case R.id.btnEBack:
                layAdmin.setVisibility(View.GONE);
                laySubmit.setVisibility(View.GONE);
                layUser.setVisibility(View.VISIBLE);
                break;
        }
    }


    private void showChooseCameraDialog() {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_select_image);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatTextView tvChooseFromCamera = mDialog.findViewById(R.id.tvChooseFromCamera);
        AppCompatTextView tvCameraImage = mDialog.findViewById(R.id.tvCameraImage);
        tvChooseFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                cam = 1;
                requestPermission();
            }
        });
        tvCameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                cam = 0;
                requestPermission();
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
    private void deleteImagesFolder() {
        try {
            //private void deleteImagesFolder(){
            File myDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), Constants.FILE_NAME);
            if (myDir.exists()) {
                myDir.delete();
            }
            // }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File dir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), Constants.FILE_NAME);
            //File oldFile = new File(myDir);
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        picUri = getOutputPhotoFile();//Uri.fromFile(getOutputPhotoFile());
        //tempUri=picUri;
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        //intent.putExtra("URI", picUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private Uri getOutputPhotoFile() {
        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), Constants.FILE_NAME);
        tempUri = directory.getPath();
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Log.e("getOutputPhotoFile", "Failed to create storage directory.");
                return null;
            }
        }

        Uri path;
        if (Build.VERSION.SDK_INT > 23) {
            File oldPath = new File(directory.getPath() + File.separator + "IMG_temp.jpg");
            String fileUrl = oldPath.getPath();
            if (fileUrl.substring(0, 7).matches("file://")) {
                fileUrl = fileUrl.substring(7);
            }
            File file = new File(fileUrl);

            path = FileProvider.getUriForFile(mContext, mContext.getPackageName() + ".provider", file);
        } else {
            path = Uri.fromFile(new File(directory.getPath() + File.separator + "IMG_temp.jpg"));
        }
        return path;
    }


    @Override
    public void onResume() {
        super.onResume();
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);//getActivity().getResources().getColor(R.color.black));

    }


    /* Call Api For Profile Image */
    private void callProfileImageApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            progressBar.setVisibility(View.VISIBLE);
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_profile_img);
                RequestBody mRequestBodyTypeCompId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());

                getProfileImageViewModel.executeGetProfileImageAPI(mRequestBodyType,mRequestBodyTypeCompId,
                        mRequestBodyTypeEmployee);
            }
            else {
                progressBar.setVisibility(View.GONE);
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            progressBar.setVisibility(View.GONE);
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Profile Image */
    private void callUploadProfileImageApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            isUpload = true;
            progressBar.setVisibility(View.VISIBLE);
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_change_profile_img);
                RequestBody mRequestBodyTypeCompId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                RequestBody mRequestBodyTypeImage = RequestBody.create(MediaType.parse("text/plain"), base64Path);
                changeProfileImageViewModel.executeChangeProfileImageAPI(mRequestBodyType,mRequestBodyTypeCompId,
                        mRequestBodyTypeEmployee,mRequestBodyTypeImage);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
                progressBar.setVisibility(View.GONE);
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
            progressBar.setVisibility(View.GONE);
        }
    }


    //show Change password popup
    public void showChangePassDialog() {
        mDialogChangePass = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogChangePass.setContentView(R.layout.dialog_change_password);
        mDialogChangePass.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialogChangePass.findViewById(R.id.imgCancel);
        AppCompatButton addReceiptButton = mDialogChangePass.findViewById(R.id.addReceiptButton);
        AppCompatAutoCompleteTextView oldPass = mDialogChangePass.findViewById(R.id.AutoComOldPass);
        AppCompatAutoCompleteTextView newPass = mDialogChangePass.findViewById(R.id.AutoComNewPass);
        AppCompatAutoCompleteTextView ConfPass = mDialogChangePass.findViewById(R.id.AutoComConfirmPass);
        TextInputLayout input_textOldPass = mDialogChangePass.findViewById(R.id.input_textOldPass);
        TextInputLayout input_textNewPass = mDialogChangePass.findViewById(R.id.input_textNewPass);
        TextInputLayout input_textConfirmPass = mDialogChangePass.findViewById(R.id.input_textConfirmPass);

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogChangePass.dismiss();
            }
        });
        addReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDetailsValid(oldPass,input_textOldPass,newPass,input_textNewPass
                        ,ConfPass,input_textConfirmPass)){
                    callChangePassApi(oldPass.getText().toString(),newPass.getText().toString());
                }
            }
        });
        mDialogChangePass.show();
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
                if(isAttendanceDetailsValid(AutoComTextViewDuration,input_textDuration,AutoComTextViewLeaveType,input_textType
                        )){
                    callApplyLeaveApi();
                }

            }
        });
        mDialogChangePass.show();
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

    /*check validations on field*/
    private boolean isAttendanceDetailsValid(AppCompatAutoCompleteTextView duration,
                                   TextInputLayout input_textDuration,
                                   AppCompatAutoCompleteTextView type,
                                   TextInputLayout input_textType
    ) {
        setError(input_textDuration,"");setError(input_textType, "");
        //setError(AutoComConfirmPass, "");
        if (TextUtils.isEmpty(duration.getText().toString().trim())) {
            setError(input_textDuration, "Select Duration");
            LogUtil.printToastMSG(mContext,"Select Duration");
            return false;
        } else if (TextUtils.isEmpty(type.getText().toString().trim())) {
            setError(input_textType, "Select Leave Type");
            LogUtil.printToastMSG(mContext,"Select Leave Type");
            return false;
        }
        return true;
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
        tvToolbarTitle.setText(R.string.scr_lbl_my_account);
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

private void setTermsAndPolicy(String webUrl){
    Intent iTerms = new Intent(getActivity(), PdfPrintActivity.class);
    iTerms.putExtra(Constants.URL, webUrl);
    iTerms.putExtra(Constants.TRANSACTION_ID, "acc");
    startActivity(iTerms);
    ((Activity) getActivity()).overridePendingTransition(0, 0);
}
    private void moveToReportAppList(){

    }
    private void setTranStatus(){
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    private void moveToAppList(){
        Fragment fragment = new LeaveListFragment();
        moveFromFragment(fragment,mContext);
    }
    public void showLogoutDialog(Context mContext) {
        mDialogLogout = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogLogout.setContentView(R.layout.dialog_logout);
        mDialogLogout.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialogLogout.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialogLogout.findViewById(R.id.uploadButton);
        AppCompatButton cancelButton = mDialogLogout.findViewById(R.id.cancelButton);

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callLogoutApi();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogLogout.dismiss();
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogLogout.dismiss();
            }
        });
        mDialogLogout.show();
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
                tvDateValue.setText(sdf.format(myCalendar.getTime()));
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


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /* Call Api For RM */
    private void callProfileApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                ProfileRequest profileRequest = new ProfileRequest();
                profileRequest.setEmpId(loginTable.getEmployeeId());
                profileViewModel.hitProfileApi(profileRequest);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }


        public class DividerItemDecorator extends RecyclerView.ItemDecoration {

            private Drawable mDivider;
            private final Rect mBounds = new Rect();

            public DividerItemDecorator(Drawable divider) {
                mDivider = divider;
            }

            @Override
            public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
                canvas.save();
                final int left;
                final int right;
                if (parent.getClipToPadding()) {
                    left = parent.getPaddingLeft();
                    right = parent.getWidth() - parent.getPaddingRight();
                    canvas.clipRect(left, parent.getPaddingTop(), right,
                            parent.getHeight() - parent.getPaddingBottom());
                } else {
                    left = 0;
                    right = parent.getWidth();
                }

                final int childCount = parent.getChildCount();
                for (int i = 0; i < childCount - 1; i++) {
                    final View child = parent.getChildAt(i);
                    parent.getDecoratedBoundsWithMargins(child, mBounds);
                    final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
                    final int top = bottom - mDivider.getIntrinsicHeight();
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                canvas.restore();
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
                    outRect.setEmpty();
                } else
                    outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            }
        }

    /*Api response */
    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {

            case LOADING:
                if(!isUpload) {
                    ((BaseActivity) mContext).showSmallProgressBar(mProgressBarHolder);
                }
                break;

            case SUCCESS:
                ((BaseActivity) mContext).dismissSmallProgressBar(mProgressBarHolder);
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_PROFILE)) {
                            ProfileResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ProfileResponse.class);
                            if (responseModel != null && responseModel.getStatus()==1) {
                                tvEmpName.setText(responseModel.getResult().get(0).getEmpUsername());

                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_APPLY_LEAVE)) {
                            ApplyLeaveResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ApplyLeaveResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                mDialogChangePass.dismiss();
                                ((BaseActivity)mContext).showSuccessDialog(responseModel.getResult().getMessage(),
                                        true,getActivity());
                                ((BaseActivity)mContext).setRateUsCounter(mContext);
                            }
                            else {
                                mDialogChangePass.dismiss();
                                ((BaseActivity)mContext).showSuccessDialog(responseModel.getResult().getMessage(),
                                        true,getActivity());
                            }
                        }
                    }catch (Exception e){
                        ((BaseActivity)mContext).showSuccessDialog("Leave application upload failed.",
                                true,getActivity());
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_CHANGE_PASS)) {
                            ChangePasswordResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ChangePasswordResponse.class);
                            if (responseModel != null/* && responseModel.getStatus()==1*/) {
                                mDialogChangePass.dismiss();
                                ((BaseActivity) mContext).errorMessage(responseModel.getResult().getMessage(), new ErrorCallbacks() {
                                    @Override
                                    public void onOkClick() {
                                        //DO something;
                                    }
                                });
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    //mDialogChangePass.dismiss();
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_PROFILE)) {
                            ProfileImageResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ProfileImageResponse.class);
                            if (responseModel != null/* && responseModel.getStatus()==1*/) {
                                isUpload = false;
                                AppUtils.loadImageURL(mContext,responseModel.getResult().getProfileurl(),imgEmp, progressBar);
                                //LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                            else{
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_CHANGE_PROFILE)) {
                            ChangeProfileImageResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ChangeProfileImageResponse.class);
                            if (responseModel != null/* && responseModel.getStatus()==1*/) {
                                AppUtils.loadImageURL(mContext,"https://ominfo.in/o_hr/"+responseModel.getResult().getProfileurl(),imgEmp,progressBar);
                                //LogUtil.printLog("url_test","https://ominfo.in/crm/"+responseModel.getResult().getProfileurl());
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                            else{
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_LOGOUT)) {
                            LogoutResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), LogoutResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                //LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                                mDialogLogout.dismiss();
                                getActivity().finishAffinity();
                                launchScreen(getActivity(), LoginActivity.class);
                                SharedPref.getInstance(mContext).write(SharedPrefKey.IS_LOGGED_IN, false);
                                try{
                                    mDb.getDbDAO().deleteLoginData();
                                    mDb.getDbDAO().deleteLocationData();
                                    mDb.getDbDAO().deleteAttendanceData();
                                    AsyncTask.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            //deleteReminderViewModel.DeleteReminder();
                                        }
                                    });
                                }catch (Exception e){e.printStackTrace();}
                            }
                            else{
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_EMPLOYEES_LIST)) {
                            EmployeeListResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), EmployeeListResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                EmployeeList employeeList = responseModel.getResult().getList().get(0);
                                tvEmpName.setText(employeeList.getEmpName());
                                AutoComtEEmail.setText(employeeList.getEmpEmail());
                                AutoComEMobile.setText(employeeList.getEmpMob());
                                AutoComEDesi.setText(employeeList.getEmpPosition());
                                AutoComtEGender.setText(employeeList.getEmpGender());
                                setDropdownGender();
                                tvDateValue.setText(employeeList.getEmpDob());
                                etDescr.setText(employeeList.getEmpAddr());
                                AutoComPincode.setText(employeeList.getEmpPincode());
                                //set days list
                                try{ workTimingLists.clear();}catch (Exception e){}
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_mon), employeeList.getMonWorking()==null?"no":employeeList.getMonWorking(), employeeList.getMonStartTime(), employeeList.getMonEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_tue), employeeList.getTueWorking()==null?"no":employeeList.getTueWorking(), employeeList.getTueStartTime(), employeeList.getTueEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_wed), employeeList.getWedWorking()==null?"no":employeeList.getWedWorking(), employeeList.getWedStartTime(), employeeList.getWedEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_thur), employeeList.getThrusWorking()==null?"no":employeeList.getThrusWorking(), employeeList.getThrusStartTime(), employeeList.getThrusEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_fri), employeeList.getFriWorking()==null?"no":employeeList.getFriWorking(), employeeList.getFriStartTime(), employeeList.getFriEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_sat), employeeList.getSatWorking()==null?"no":employeeList.getSatWorking(), employeeList.getSatStartTime(), employeeList.getSatStartTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_sun), employeeList.getSunWorking()==null?"no":employeeList.getSunWorking(), employeeList.getSunStartTime(), employeeList.getSunEndTime()));
                                setTimeDisabled(Integer.parseInt(isAdmin));
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_COMPANY)) {
                            GetCompanyResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetCompanyResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                GetCompanyList employeeList = responseModel.getResult().getList().get(0);
                                AutoComEmailId.setText(employeeList.getEmailId());
                                AutoComMobile.setText(employeeList.getContactNo());
                                etOfAddress.setText(employeeList.getOfficeAddress());
                                AutoComOPincode.setText(employeeList.getPincode());
                                AutoComGstNo.setText(employeeList.getGstNo());
                                AutoComStaff.setText(employeeList.getStaffStrength());
                                AutoComPrefix.setText(employeeList.getUserPrefix());
                                try{ workTimingLists.clear();}catch (Exception e){}
                                workTimingLists.add(new WorkTimingList(false,"Mon", employeeList.getMonWorking()==null?"no":employeeList.getMonWorking(), employeeList.getMonStartTime(), employeeList.getMonEndTime()));
                                workTimingLists.add(new WorkTimingList(false,"Tue", employeeList.getTueWorking()==null?"no":employeeList.getTueWorking(), employeeList.getTueStartTime(), employeeList.getTueEndTime()));
                                workTimingLists.add(new WorkTimingList(false,"Wed", employeeList.getWedWorking()==null?"no":employeeList.getWedWorking(), employeeList.getWedStartTime(), employeeList.getWedEndTime()));
                                workTimingLists.add(new WorkTimingList(false,"Thur", employeeList.getThursWorking()==null?"no":employeeList.getThursWorking(), employeeList.getThursStartTime(), employeeList.getThursEndTime()));
                                workTimingLists.add(new WorkTimingList(false,"Fri", employeeList.getFriWorking()==null?"no":employeeList.getFriWorking(), employeeList.getFriStartTime(), employeeList.getFriEndTime()));
                                workTimingLists.add(new WorkTimingList(false,"Sat", employeeList.getSatWorking()==null?"no":employeeList.getSatWorking(), employeeList.getSatStartTime(), employeeList.getSatStartTime()));
                                workTimingLists.add(new WorkTimingList(false,"Sun", employeeList.getSunWorking()==null?"no":employeeList.getSunWorking(), employeeList.getSunStartTime(), employeeList.getSunEndTime()));
                                setTimeDisabled(Integer.parseInt(isAdmin));
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case ERROR:
                progressBar.setVisibility(View.GONE);
                ((BaseActivity) mContext).dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String actualPath = RealPathUtils.getActualPath(mContext, data.getData());
                convertToBase64(actualPath);
            }
        }
        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            //if (data != null) {
            try {
                File file = new File(tempUri + "/IMG_temp.jpg");

                Bitmap mImgaeBitmap = null;
                try {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                    // Calculate inSampleSize
                    options.inSampleSize = Util.calculateInSampleSize(options, 600, 600);

                    // Decode bitmap with inSampleSize set
                    options.inJustDecodeBounds = false;
                    Bitmap scaledBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                    //check the rotation of the image and display it properly
                    ExifInterface exif;
                    exif = new ExifInterface(file.getAbsolutePath());
                    int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                    Matrix matrix = new Matrix();
                    if (orientation == 6) {
                        matrix.postRotate(90);
                    } else if (orientation == 3) {
                        matrix.postRotate(180);
                    } else if (orientation == 8) {
                        matrix.postRotate(270);
                    }
                    mImgaeBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                    SaveImageMM(mImgaeBitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                    CropImage.activity(picUri).start(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //}
        }
    }

    //TODO will add comments later
    private void SaveImageMM(Bitmap finalBitmap) {
        File myDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        myDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.US).format(new Date());
        String fname = "Image_" + timeStamp + "_capture.jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            //new ImageCompression(this,file.getAbsolutePath()).execute(finalBitmap);
            FileOutputStream out = new FileOutputStream(file);
            //finalBitmap = Bitmap.createScaledBitmap(finalBitmap,(int)1080/2,(int)1920/2, true);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 60, out); //less than 300 kb
            out.flush();
            out.close();
            String oldFname = "IMG_temp.jpg";
            File oldFile = new File(myDir, oldFname);
            if (oldFile.exists()) oldFile.delete();
            //save image to db
            int id = 0;
            String pathDb = file.getPath();
            //set image list adapter
            convertToBase64(pathDb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void convertToBase64(String path){
        String mBase64 = AppUtils.getBase64images(path);
        base64Path = /*"data:image/png;base64," +*/ mBase64;
        callUploadProfileImageApi();
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

    /*
     * permission result
     * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                ) {
                    reqPermissionCode();
                } else {
                    //Toast.makeText(mContext, getString(R.string.somthing_went_wrong), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void setAllDisabled(int type,boolean state){
        //all
        AutoComOfficeLocation.setEnabled(state);
        if(type==0) {
            //employee
            AutoComtEEmail.setEnabled(state);
            AutoComEMobile.setEnabled(state);
            AutoComEDesi.setEnabled(state);
            AutoComtEGender.setEnabled(state);
            layCalender.setEnabled(state);
            etDescr.setEnabled(state);
            AutoComPincode.setEnabled(state);
            setTimeDisabled(type);
        }else {
            //company
            AutoComGstNo.setEnabled(state);
            etOfAddress.setEnabled(state);
            AutoComOPincode.setEnabled(state);
            AutoComMobile.setEnabled(state);
            AutoComEmailId.setEnabled(state);
            AutoComStaff.setEnabled(state);
            setTimeDisabled(type);
        }
    }

    private void setTimeDisabled(int type){
        if(type==0) {
           setAdapterForTimingList(false);
        }else {
            //company
            setAdapterForTimingList(true);
        }
    }

}