package com.ominfo.hra_app.ui.my_account;

import static com.ominfo.hra_app.util.AppUtils.dateConvertYYYYToDD;
import static com.ominfo.hra_app.util.AppUtils.getChangeDateForHisab;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
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
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
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
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
import com.ominfo.hra_app.interfaces.SharedPrefKey;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.hra_app.ui.employees.adapter.EmployeeTimeAdapter;
import com.ominfo.hra_app.ui.employees.model.EditEmployeeRequest;
import com.ominfo.hra_app.ui.employees.model.EditEmployeeResponse;
import com.ominfo.hra_app.ui.employees.model.EditEmployeeViewModel;
import com.ominfo.hra_app.ui.employees.model.EmployeeList;
import com.ominfo.hra_app.ui.employees.model.EmployeeListRequest;
import com.ominfo.hra_app.ui.employees.model.EmployeeListResponse;
import com.ominfo.hra_app.ui.employees.model.EmployeeListViewModel;
import com.ominfo.hra_app.ui.login.LoginActivity;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.login.model.LogoutMobileTokenViewModel;
import com.ominfo.hra_app.ui.login.model.LogoutResponse;
import com.ominfo.hra_app.ui.login.model.LogoutViewModel;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveViewModel;
import com.ominfo.hra_app.ui.my_account.model.ChangeProfileImageResponse;
import com.ominfo.hra_app.ui.my_account.model.ChangeProfileImageViewModel;
import com.ominfo.hra_app.ui.my_account.model.EditCompanyRequest;
import com.ominfo.hra_app.ui.my_account.model.EditCompanyResonse;
import com.ominfo.hra_app.ui.my_account.model.EditCompanyViewModel;
import com.ominfo.hra_app.ui.my_account.model.GetCompanyList;
import com.ominfo.hra_app.ui.my_account.model.GetCompanyResponse;
import com.ominfo.hra_app.ui.my_account.model.GetCompanyViewModel;
import com.ominfo.hra_app.ui.my_account.model.GetProfileImageViewModel;
import com.ominfo.hra_app.ui.my_account.model.ProfileImageResponse;
import com.ominfo.hra_app.ui.my_account.model.WorkTimingList;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.payment.PaymentFragment;
import com.ominfo.hra_app.ui.visit_report.activity.AddLocationActivity;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    //AddTagAdapter addTagAdapter;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.btnNext)
    AppCompatButton btnNext;
    @BindView(R.id.btnSubmit)
    AppCompatButton btnSubmit;
    @BindView(R.id.imgCall)
    AppCompatImageView imgEdit;
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
    @BindView(R.id.layAddLocation)
    LinearLayoutCompat layAddLocation;
    @BindView(R.id.tvSearchView)
    AppCompatTextView tvToolbarTitle;
    @BindView(R.id.imgBack)
    LinearLayoutCompat imgBack;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
    private boolean isUpload = false;
    @Inject
    ViewModelFactory mViewModelFactory;
    private GetProfileImageViewModel getProfileImageViewModel;
    private ApplyLeaveViewModel applyLeaveViewModel;
    private ChangeProfileImageViewModel changeProfileImageViewModel;
    private EmployeeListViewModel employeeListViewModel;
    private GetCompanyViewModel getCompanyViewModel;
    private EditEmployeeViewModel editEmployeeViewModel;
    private EditCompanyViewModel editCompanyViewModel;
    private LogoutViewModel logoutViewModel;
    private LogoutMobileTokenViewModel logoutMobileTokenViewModel;
    EmployeeList employeeListResData;
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
    @BindView(R.id.expandedImage)
    AppCompatImageView imgAccBg;
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
    @BindView(R.id.input_textEmailId)
    TextInputLayout input_textEmailId;
    @BindView(R.id.input_textGstNo)
    TextInputLayout input_textGstNo;
    @BindView(R.id.input_textEEmail)
    TextInputLayout input_textEEmail;
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
    @BindView(R.id.imgDots)
    AppCompatImageView imgDots;
    @BindView(R.id.imgDotsLogout)
    AppCompatImageView imgDotsLogout;
    @BindView(R.id.AutoComDistRange)
    AppCompatAutoCompleteTextView AutoComDistRange;
    @BindView(R.id.input_textDistRange)
    TextInputLayout input_textDistRange;
    List<WorkTimingList> workTimingLists = new ArrayList<>();
    String officeLat = "",officeLong="";
    boolean editClick = false,editNAME = false;
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
        //done + multiple
        etOfAddress.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etOfAddress.setRawInputType(InputType.TYPE_CLASS_TEXT);
        AutoComOfficeLocation.setImeOptions(EditorInfo.IME_ACTION_DONE);
        AutoComOfficeLocation.setRawInputType(InputType.TYPE_CLASS_TEXT);
        imgEdit.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_edit_all));
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

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(mContext, v);
        MenuInflater inflater = popup.getMenuInflater();
        popup.getMenu().clear();
        inflater.inflate(R.menu.menu_account, popup.getMenu());
        LoginTable loginTable =mDb.getDbDAO().getLoginData();
        if(loginTable!=null) {
            if(loginTable.equals("0")) {
            MenuItem item = popup.getMenu().findItem(R.id.myPlan);
            item.setVisible(false);
            popup.getMenu().clear();
        }}
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.myPlan:
                        moveFromFragment(new PaymentFragment(),mContext);
                        // Not implemented here
                        return false;
                    case R.id.logout:
                        showLogoutDialog(mContext);
                        // Do Fragment menu item stuff here
                        return true;

                    default:
                        break;
                }
                return false;
            }
        });
        popup.show();

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
             callLogoutMobileTokenApi();
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


    private void injectAPI() {

        getProfileImageViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetProfileImageViewModel.class);
        getProfileImageViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_PROFILE));

        changeProfileImageViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ChangeProfileImageViewModel.class);
        changeProfileImageViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_CHANGE_PROFILE));

        applyLeaveViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ApplyLeaveViewModel.class);
        applyLeaveViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_APPLY_LEAVE));

        employeeListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EmployeeListViewModel.class);
        employeeListViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_EMPLOYEES_LIST));

        getCompanyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetCompanyViewModel.class);
        getCompanyViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_COMPANY));

        editEmployeeViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EditEmployeeViewModel.class);
        editEmployeeViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_edit_employee));

        editCompanyViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EditCompanyViewModel.class);
        editCompanyViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_EDIT_COMPANY));

        logoutViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LogoutViewModel.class);
        logoutViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_logout));

        logoutMobileTokenViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LogoutMobileTokenViewModel.class);
        logoutMobileTokenViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_logout_mt_update));
    }

    /* Call Api For edit employee */
    private void callEditEmployeeApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyTypeAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_edit_employee);
                RequestBody mRequestBodyTypeEmpName = RequestBody.create(MediaType.parse("text/plain"),tvEmpName.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpMob = RequestBody.create(MediaType.parse("text/plain"), AutoComEMobile.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpEmail = RequestBody.create(MediaType.parse("text/plain"), AutoComtEEmail.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpAddr = RequestBody.create(MediaType.parse("text/plain"), etDescr.getText().toString().trim());//loginTable.getCompanyId());
                String dob = AppUtils.changeToSlashToDash(tvDateValue.getText().toString().trim());
                RequestBody mRequestBodyTypeEmpDob = RequestBody.create(MediaType.parse("text/plain"), dob);//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpGender = RequestBody.create(MediaType.parse("text/plain"), AutoComtEGender.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpPincode = RequestBody.create(MediaType.parse("text/plain"), AutoComPincode.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeEmpPos = RequestBody.create(MediaType.parse("text/plain"), AutoComEDesi.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeupdated_by = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeemp_id = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeSalary = RequestBody.create(MediaType.parse("text/plain"), employeeListResData.getSalary()==null?"":employeeListResData.getSalary());//loginTable.getCompanyId());
                RequestBody mRequestBodyOtherLeave = RequestBody.create(MediaType.parse("text/plain"), employeeListResData.getOtherLeaves()==null?"":employeeListResData.getOtherLeaves());//loginTable.getCompanyId());
                RequestBody mRequestBodyCasualLeave = RequestBody.create(MediaType.parse("text/plain"), employeeListResData.getCasualLeaves()==null?"":employeeListResData.getCasualLeaves());//loginTable.getCompanyId());
                RequestBody mRequestBodySickLeave = RequestBody.create(MediaType.parse("text/plain"), employeeListResData.getSickLeaves()==null?"":employeeListResData.getSickLeaves());//loginTable.getCompanyId());
                String join = AppUtils.changeToSlashToDash(employeeListResData.getJoiningDate()==null?"":employeeListResData.getJoiningDate());
                RequestBody mRequestBodyJoiningDate = RequestBody.create(MediaType.parse("text/plain"),join);//loginTable.getCompanyId());
                RequestBody mRequestBodyTypeToken = RequestBody.create(MediaType.parse("text/plain"), loginTable.getToken());//loginTable.getCompanyId());
                RequestBody mRequestAddr = RequestBody.create(MediaType.parse("text/plain"),AutoComOfficeLocation.getText().toString());//loginTable.getCompanyId());
                RequestBody mRequestLat = RequestBody.create(MediaType.parse("text/plain"),officeLat==null?"0.0":officeLat);//loginTable.getCompanyId());
                RequestBody mRequestLong = RequestBody.create(MediaType.parse("text/plain"),officeLong==null?"0.0":officeLong);//loginTable.getCompanyId());
                RequestBody mRequestMon = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(0).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestTue = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(1).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestWed = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(2).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestThur = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(3).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestFri = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(4).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestSat = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(5).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestSun = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(6).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestMonStart = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(0).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestTueStart = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(1).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestWedStart = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(2).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestThurStart = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(3).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestFriStart = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(4).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestSatStart = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(5).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestSunStart = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(6).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestMonEnd = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(0).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestTueEnd = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(1).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestWedEnd = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(2).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestThurEnd = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(3).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestFriEnd = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(4).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestSatEnd = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(5).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestSunEnd = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(6).getMonEndTime());//loginTable.getCompanyId());

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
            if (loginTable != null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_logout);
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getToken());
                logoutViewModel.hitLogoutApi(mRequestBodyAction, mRequestBodyTypeEmployee);
            } else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Logout */
    private void callLogoutMobileTokenApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_logout_mt_update);
                RequestBody mRequestBodyTypeEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                logoutMobileTokenViewModel.hitLogoutMobileTokenApi(mRequestBodyAction, mRequestBodyTypeEmployee);
            } else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    public static boolean isValidGSTNo(String str)
    { String regex = "^[0-9]{2}[A-Z]{5}[0-9]{4}"
            + "[A-Z]{1}[1-9A-Z]{1}"
            + "Z[0-9A-Z]{1}$";
        Pattern p = Pattern.compile(regex);

        if (str == null)
        {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }
    private void setClickListener(){
      /*  input_textEmailId.setErrorEnabled(true);
        input_textEEmail.setErrorEnabled(true);
        input_textGstNo.setErrorEnabled(true);*/
        AutoComEmailId.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if(s.length()>5) {
                    if(!TextUtils.isEmpty(AutoComEmailId.getText().toString().trim()) &&
                            Patterns.EMAIL_ADDRESS.matcher(AutoComEmailId.getText().toString().trim()).matches()){
                        input_textEmailId.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_right_accept));
                        int color=Color.GREEN;
                        input_textEmailId.setEndIconTintList(ColorStateList.valueOf(color));
                        ((BaseActivity)mContext).setError(1, input_textEmailId, "Entered email is valid.");
                    }
                    else {
                        input_textEmailId.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                        int color=Color.RED;
                        input_textEmailId.setEndIconTintList(ColorStateList.valueOf(color));
                        ((BaseActivity)mContext).setError(0, input_textEmailId, getString(R.string.err_enter_email_id));
                    }
                }
                if(s.length()==0){
                    input_textEmailId.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                    int color=Color.RED;
                    input_textEmailId.setEndIconTintList(ColorStateList.valueOf(color));
                    ((BaseActivity)mContext).setError(0, input_textEmailId, getString(R.string.err_enter_email_id));
                }
            }
        });
        AutoComtEEmail.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if(s.length()>5) {
                    if(!TextUtils.isEmpty(AutoComtEEmail.getText().toString().trim()) &&
                            Patterns.EMAIL_ADDRESS.matcher(AutoComtEEmail.getText().toString().trim()).matches()){
                        input_textEEmail.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_right_accept));
                        int color=Color.GREEN;
                        input_textEEmail.setEndIconTintList(ColorStateList.valueOf(color));
                        ((BaseActivity)mContext).setError(1, input_textEEmail, "Entered email is valid.");
                    }
                    else {
                        input_textEEmail.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                        int color=Color.RED;
                        input_textEEmail.setEndIconTintList(ColorStateList.valueOf(color));
                        ((BaseActivity)mContext).setError(0, input_textEEmail, getString(R.string.err_enter_email_id));
                    }
                }
                if(s.length()==0){
                    input_textEEmail.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                    int color=Color.RED;
                    input_textEEmail.setEndIconTintList(ColorStateList.valueOf(color));
                    ((BaseActivity)mContext).setError(0, input_textEEmail, getString(R.string.err_enter_email_id));
                }
            }
        });
        AutoComGstNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() > 13) {
                    if(TextUtils.isEmpty(AutoComGstNo.getText().toString().trim()) ||
                            !isValidGSTNo(AutoComGstNo.getText().toString().trim())){
                        input_textGstNo.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                        int color=Color.RED;
                        input_textGstNo.setEndIconTintList(ColorStateList.valueOf(color));
                        ((BaseActivity)mContext).setError(0, input_textGstNo, getString(R.string.scr_lbl_enter_gst_number));
                    }
                    else{
                        input_textGstNo.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_right_accept));
                        int color=Color.GREEN;
                        input_textGstNo.setEndIconTintList(ColorStateList.valueOf(color));
                        ((BaseActivity)mContext).setError(1, input_textGstNo, "Entered Gst number is valid.");
                    }
                }
                if(s.length() == 0){
                    input_textGstNo.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                    int color=Color.RED;
                    input_textGstNo.setEndIconTintList(ColorStateList.valueOf(color));
                    ((BaseActivity)mContext).setError(0, input_textGstNo, getString(R.string.scr_lbl_enter_gst_number));
                }
            }
        });
        int color=Color.TRANSPARENT;
        input_textEmailId.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
        input_textEmailId.setEndIconTintList(ColorStateList.valueOf(color));
        input_textEEmail.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_right_accept));
        input_textEEmail.setEndIconTintList(ColorStateList.valueOf(color));
        input_textGstNo.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_right_accept));
        input_textGstNo.setEndIconTintList(ColorStateList.valueOf(color));
    }
    /* Call Api For edit Company */
    private void callEditCompanyApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyTypeAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_edit_company);
                RequestBody mRequestBodyComId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                RequestBody mRequestBodyName = RequestBody.create(MediaType.parse("text/plain"), tvEmpName.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyContact = RequestBody.create(MediaType.parse("text/plain"), AutoComMobile.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyEmail = RequestBody.create(MediaType.parse("text/plain"), AutoComEmailId.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodystaff = RequestBody.create(MediaType.parse("text/plain"), AutoComStaff.getText().toString());//loginTable.getCompanyId());
                RequestBody mRequestBodyGst = RequestBody.create(MediaType.parse("text/plain"), AutoComGstNo.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestBodyPincode = RequestBody.create(MediaType.parse("text/plain"), AutoComOPincode.getText().toString().trim());//loginTable.getCompanyId());
                RequestBody mRequestRegAddress = RequestBody.create(MediaType.parse("text/plain"), etOfAddress.getText().toString());//loginTable.getCompanyId());
                RequestBody mRequestdist_range = RequestBody.create(MediaType.parse("text/plain"), AutoComDistRange.getText().toString());//loginTable.getCompanyId());
                RequestBody mRequestAddr = RequestBody.create(MediaType.parse("text/plain"),AutoComOfficeLocation.getText().toString());//loginTable.getCompanyId());
                RequestBody mRequestLat = RequestBody.create(MediaType.parse("text/plain"),officeLat==null?"0.0":officeLat);//loginTable.getCompanyId());
                RequestBody mRequestLong = RequestBody.create(MediaType.parse("text/plain"),officeLong==null?"0.0":officeLong);//loginTable.getCompanyId());
                RequestBody mRequestMon = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(0).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestTue = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(1).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestWed = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(2).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestThur = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(3).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestFri = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(4).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestSat = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(5).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestSun = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(6).getMonWorking());//loginTable.getCompanyId());
                RequestBody mRequestMonStart = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(0).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestTueStart = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(1).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestWedStart = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(2).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestThurStart = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(3).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestFriStart = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(4).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestSatStart = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(5).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestSunStart = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(6).getMonStartTime());//loginTable.getCompanyId());
                RequestBody mRequestMonEnd = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(0).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestTueEnd = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(1).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestWedEnd = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(2).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestThurEnd = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(3).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestFriEnd = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(4).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestSatEnd = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(5).getMonEndTime());//loginTable.getCompanyId());
                RequestBody mRequestSunEnd = RequestBody.create(MediaType.parse("text/plain"),workTimingLists.get(6).getMonEndTime());//loginTable.getCompanyId());

                EditCompanyRequest request = new EditCompanyRequest();
                request.setAction(mRequestBodyTypeAction);
                request.setName(mRequestBodyName);
                request.setCompany_ID(mRequestBodyComId);
                request.setContact_no(mRequestBodyContact);
                request.setEmail_id(mRequestBodyEmail);
                request.setStaff_strength(mRequestBodystaff);
                request.setGst_no(mRequestBodyGst);
                request.setPincode(mRequestBodyPincode);
                request.setRegistered_address(mRequestRegAddress);
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
                request.setDist_range(mRequestdist_range);

                editCompanyViewModel.hitEditCompanyAPI(request);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
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
        boolean toEnable = editClick;
        if(isAdmin.equals("0")) {
            toEnable = false;
        }
        if (workTimingLists!=null && workTimingLists.size() > 0) {
            employeeTimeAdapter = new EmployeeTimeAdapter(toEnable,isToggle,mContext, workTimingLists, new EmployeeTimeAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(WorkTimingList mDataTicket, List<WorkTimingList> notificationResultListAdapter, boolean status) {
                    workTimingLists = notificationResultListAdapter;
                }
            });
            rvSalesList.setHasFixedSize(true);
            rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            rvSalesList.setAdapter(employeeTimeAdapter);
            rvSalesList.setVisibility(View.VISIBLE);
        }
    }


    private void init(){
        Glide.with(this)
                .load(R.drawable.bg_my_account_2)
                .into(imgAccBg);
        tvEmpName.setEnabled(false);
        btnSubmit.setVisibility(View.GONE);
        btnESubmit.setVisibility(View.GONE);
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

        workTimingLists.add(new WorkTimingList(true,getString(R.string.scr_lbl_mon),getString(R.string.scr_lbl_yes),getString(R.string.scr_lbl_start_time_values),getString(R.string.scr_lbl_end_start_value)));
        workTimingLists.add(new WorkTimingList(true,getString(R.string.scr_lbl_tue),getString(R.string.scr_lbl_yes),getString(R.string.scr_lbl_start_time_values),getString(R.string.scr_lbl_end_start_value)));
        workTimingLists.add(new WorkTimingList(true,getString(R.string.scr_lbl_wed),getString(R.string.scr_lbl_yes),getString(R.string.scr_lbl_start_time_values),getString(R.string.scr_lbl_end_start_value)));
        workTimingLists.add(new WorkTimingList(true,getString(R.string.scr_lbl_thur),getString(R.string.scr_lbl_yes),getString(R.string.scr_lbl_start_time_values),getString(R.string.scr_lbl_end_start_value)));
        workTimingLists.add(new WorkTimingList(true,getString(R.string.scr_lbl_fri),getString(R.string.scr_lbl_yes),getString(R.string.scr_lbl_start_time_values),getString(R.string.scr_lbl_end_start_value)));
        workTimingLists.add(new WorkTimingList(true,getString(R.string.scr_lbl_sat),getString(R.string.scr_lbl_yes),getString(R.string.scr_lbl_start_time_values),getString(R.string.scr_lbl_end_start_value)));
        workTimingLists.add(new WorkTimingList(true,getString(R.string.scr_lbl_sun),getString(R.string.scr_lbl_yes),getString(R.string.scr_lbl_start_time_values),getString(R.string.scr_lbl_end_start_value)));
        setAdapterForTimingList(false);

        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);//getActivity().getResources().getColor(R.color.black));
        setToolbar();
        cam=2;
        requestPermission();
        callProfileImageApi();
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
            ,R.id.layCalender,R.id.imgCall,R.id.btnESubmit,R.id.layAddLocation})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layCalender:
                openDataPicker(0);
                break;
            case R.id.imgEdit:
                tvEmpName.setEnabled(false);
                showProfileNameDialog();
                break;
            case R.id.imgCall:
                //edit all
                if(!editClick) {
                    editClick = true;
                    setAllDisabled(Integer.parseInt(isAdmin), true);
                }
                else{
                    editClick = false;
                    setAllDisabled(Integer.parseInt(isAdmin), false);
                }
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
                setAllDisabled(1,false);
                callEditCompanyApi();
                break;
            case R.id.btnESubmit:
                layAdmin.setVisibility(View.GONE);
                laySubmit.setVisibility(View.GONE);
                layUser.setVisibility(View.VISIBLE);
                setAllDisabled(0,false);
                callEditEmployeeApi();
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
            case R.id.layAddLocation:
                int LAUNCH_SECOND_ACTIVITY = 1000;
                Intent i = new Intent(mContext, AddLocationActivity.class);
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
                //launchScreen(mContext,AddLocationActivity.class);
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

    private void showProfileNameDialog() {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_profile_name);
        mDialog.setCanceledOnTouchOutside(true);
        RelativeLayout mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatAutoCompleteTextView AutoComNameDialog = mDialog.findViewById(R.id.AutoComNameDialog);
        AppCompatButton doneButton = mDialog.findViewById(R.id.doneButton);
        AutoComNameDialog.setText(""+tvEmpName.getText().toString());
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              tvEmpName.setText(""+AutoComNameDialog.getText().toString());
               mDialog.dismiss();
                if(isAdmin.equals("1")) {
                    layAdmin.setVisibility(View.VISIBLE);
                    laySubmit.setVisibility(View.GONE);
                    layUser.setVisibility(View.GONE);
                    setAllDisabled(1,false);
                    callEditCompanyApi();
                } if(isAdmin.equals("0")) {
                    layAdmin.setVisibility(View.GONE);
                    laySubmit.setVisibility(View.GONE);
                    layUser.setVisibility(View.VISIBLE);
                    setAllDisabled(0,false);
                    callEditEmployeeApi();}
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
            isUpload = true;
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

            if(isAdmin.equals("0")){
                imgDots.setVisibility(View.GONE);
                imgDotsLogout.setVisibility(View.VISIBLE);
                imgDotsLogout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showLogoutDialog(mContext);
                    }
                });
            }else{
                imgDotsLogout.setVisibility(View.GONE);
                imgDots.setVisibility(View.VISIBLE);
                imgDots.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showPopup(view);
                        //showLogoutDialog(mContext);
                    }
                });
            }

    }

    private void moveToReportAppList(){

    }
    private void setTranStatus(){
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.action_edit_employee)) {
                            EditEmployeeResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), EditEmployeeResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                showSuccessDialogFragment(mContext,"Employee Edited successfully !",true, null);
                            }
                            else {
                                showSuccessDialogFragment(mContext,responseModel.getResult().getMessage(),false, null);
                            }
                            callEmployeeListApi();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showSuccessDialogFragment(mContext,"Something went wrong.",false, null);
                        callEmployeeListApi();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_EDIT_COMPANY)) {
                            EditCompanyResonse responseModel = new Gson().fromJson(apiResponse.data.toString(), EditCompanyResonse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                showSuccessDialogFragment(mContext,"Company Edited successfully !",true, null);
                            }
                            else {
                                showSuccessDialogFragment(mContext,responseModel.getResult().getMessage(),false, null);
                            }
                            callCompanyListApi();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showSuccessDialogFragment(mContext,"Something went wrong.",false, null);
                        callCompanyListApi();
                    }

                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_PROFILE)) {
                            ProfileImageResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ProfileImageResponse.class);
                            isUpload = false;
                            if (responseModel != null/* && responseModel.getStatus()==1*/) {
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.action_logout)) {
                            LogoutResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), LogoutResponse.class);
                            //if (responseModel != null && responseModel.getResult().getStatus().equals("Success")) {
                            //LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            mDialogLogout.dismiss();
                            getActivity().finishAffinity();
                            launchScreen(getActivity(), LoginActivity.class);
                            SharedPref.getInstance(mContext).write(SharedPrefKey.IS_LOGGED_IN, false);
                            try {
                                mDb.getDbDAO().deleteLoginData();
                                mDb.getDbDAO().deleteLocationData();
                                mDb.getDbDAO().deleteAttendanceData();
                                AsyncTask.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        //deleteReminderViewModel.DeleteReminder();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            progressBar.setVisibility(View.GONE);
                            /*}
                            else{

                            }*/
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.action_logout_mt_update)) {
                            LogoutResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), LogoutResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                               callLogoutApi();
                            }
                            else{
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_CHANGE_PROFILE)) {
                            ChangeProfileImageResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ChangeProfileImageResponse.class);
                            isUpload = false;
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_EMPLOYEES_LIST)) {
                            EmployeeListResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), EmployeeListResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                btnSubmit.setVisibility(View.GONE);
                                btnESubmit.setVisibility(View.GONE);
                                employeeListResData = responseModel.getResult().getList().get(0);
                                tvEmpName.setText(employeeListResData.getEmpName());
                                AutoComtEEmail.setText(employeeListResData.getEmpEmail());
                                AutoComEMobile.setText(employeeListResData.getEmpMob());
                                AutoComEDesi.setText(employeeListResData.getEmpPosition());
                                AutoComtEGender.setText(employeeListResData.getEmpGender());
                                setDropdownGender();
                                tvDateValue.setText(dateConvertYYYYToDD(employeeListResData.getEmpDob()));
                                AutoComOfficeLocation.setText(employeeListResData.getOfficeAddress());
                                etDescr.setText(employeeListResData.getEmpAddr());
                                AutoComPincode.setText(employeeListResData.getEmpPincode());
                                officeLat = employeeListResData.getOfficeLatitude();
                                officeLong = employeeListResData.getOfficeLongitude();
                                //set days list
                                try{ workTimingLists.removeAll(workTimingLists);}catch (Exception e){}
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_mon), employeeListResData.getMonWorking()==null?"no":employeeListResData.getMonWorking(),
                                        employeeListResData.getMonStartTime()==null?getString(R.string.scr_lbl_start_time):employeeListResData.getMonStartTime(), employeeListResData.getMonEndTime()==null?getString(R.string.scr_lbl_end_time):employeeListResData.getMonEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_tue), employeeListResData.getTueWorking()==null?"no":employeeListResData.getTueWorking(),
                                        employeeListResData.getTueStartTime()==null?getString(R.string.scr_lbl_start_time):employeeListResData.getTueStartTime(), employeeListResData.getTueEndTime()==null?getString(R.string.scr_lbl_end_time):employeeListResData.getTueEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_wed), employeeListResData.getWedWorking()==null?"no":employeeListResData.getWedWorking(),
                                        employeeListResData.getWedStartTime()==null?getString(R.string.scr_lbl_start_time):employeeListResData.getWedStartTime(), employeeListResData.getWedEndTime()==null?getString(R.string.scr_lbl_end_time):employeeListResData.getWedEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_thur), employeeListResData.getThrusWorking()==null?"no":employeeListResData.getThrusWorking(),
                                        employeeListResData.getThrusStartTime()==null?getString(R.string.scr_lbl_start_time):employeeListResData.getThrusStartTime(), employeeListResData.getThrusEndTime()==null?getString(R.string.scr_lbl_end_time):employeeListResData.getThrusEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_fri), employeeListResData.getFriWorking()==null?"no":employeeListResData.getFriWorking(),
                                        employeeListResData.getFriStartTime()==null?getString(R.string.scr_lbl_start_time):employeeListResData.getFriStartTime(), employeeListResData.getFriEndTime()==null?getString(R.string.scr_lbl_end_time):employeeListResData.getFriEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_sat), employeeListResData.getSatWorking()==null?"no":employeeListResData.getSatWorking(),
                                        employeeListResData.getSatStartTime()==null?getString(R.string.scr_lbl_start_time):employeeListResData.getSatStartTime(), employeeListResData.getSatEndTime()==null?getString(R.string.scr_lbl_end_time):employeeListResData.getSatEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_sun), employeeListResData.getSunWorking()==null?"no":employeeListResData.getSunWorking(),
                                        employeeListResData.getSunStartTime()==null?getString(R.string.scr_lbl_start_time):employeeListResData.getSunStartTime(), employeeListResData.getSunEndTime()==null?getString(R.string.scr_lbl_end_time):employeeListResData.getSunEndTime()));
                                setAllDisabled(Integer.parseInt(isAdmin),false);
                                editClick = false;
                                setTimeDisabled(0);
                                setDis();
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_COMPANY)) {
                            GetCompanyResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetCompanyResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                btnSubmit.setVisibility(View.GONE);
                                btnESubmit.setVisibility(View.GONE);
                                GetCompanyList employeeList = responseModel.getResult().getList().get(0);
                                tvEmpName.setText(employeeList.getName()+"");
                                AutoComEmailId.setText(employeeList.getEmailId()+"");
                                AutoComMobile.setText(employeeList.getContactNo()+"");
                                etOfAddress.setText(employeeList.getOfficeAddress()+"");
                                AutoComDistRange.setText(employeeList.getDist_range()==null?"50":employeeList.getDist_range());
                                AutoComOPincode.setText(employeeList.getPincode()+"");
                                AutoComGstNo.setText(employeeList.getGstNo()+"");
                                AutoComStaff.setText(employeeList.getStaffStrength()+"");
                                AutoComPrefix.setText(employeeList.getUserPrefix()+"");
                                officeLat = employeeList.getOfficeLatitude()+"";
                                officeLong = employeeList.getOfficeLongitude()+"";
                                AutoComOfficeLocation.setText(employeeList.getOfficeAddress());
                                try{ workTimingLists.removeAll(workTimingLists);}catch (Exception e){}
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_mon), employeeList.getMonWorking()==null?"no":employeeList.getMonWorking(),
                                        employeeList.getMonStartTime()==null?getString(R.string.scr_lbl_start_time):employeeList.getMonStartTime(), employeeList.getMonEndTime()==null?getString(R.string.scr_lbl_end_time):employeeList.getMonEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_tue), employeeList.getTueWorking()==null?"no":employeeList.getTueWorking(),
                                        employeeList.getTueStartTime()==null?getString(R.string.scr_lbl_start_time):employeeList.getTueStartTime(), employeeList.getTueEndTime()==null?getString(R.string.scr_lbl_end_time):employeeList.getTueEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_wed), employeeList.getWedWorking()==null?"no":employeeList.getWedWorking(),
                                        employeeList.getWedStartTime()==null?getString(R.string.scr_lbl_start_time):employeeList.getWedStartTime(), employeeList.getWedEndTime()==null?getString(R.string.scr_lbl_end_time):employeeList.getWedEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_thur), employeeList.getThursWorking()==null?"no":employeeList.getThursWorking(),
                                        employeeList.getThursStartTime()==null?getString(R.string.scr_lbl_start_time):employeeList.getThursStartTime(), employeeList.getThursEndTime()==null?getString(R.string.scr_lbl_end_time):employeeList.getThursEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_fri), employeeList.getFriWorking()==null?"no":employeeList.getFriWorking(),
                                        employeeList.getFriStartTime()==null?getString(R.string.scr_lbl_start_time):employeeList.getFriStartTime(), employeeList.getFriEndTime()==null?getString(R.string.scr_lbl_end_time):employeeList.getFriEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_sat), employeeList.getSatWorking()==null?"no":employeeList.getSatWorking(),
                                        employeeList.getSatStartTime()==null?getString(R.string.scr_lbl_start_time):employeeList.getSatStartTime(), employeeList.getSatEndTime()==null?getString(R.string.scr_lbl_end_time):employeeList.getSatEndTime()));
                                workTimingLists.add(new WorkTimingList(false,getString(R.string.scr_lbl_sun), employeeList.getSunWorking()==null?"no":employeeList.getSunWorking(),
                                        employeeList.getSunStartTime()==null?getString(R.string.scr_lbl_start_time):employeeList.getSunStartTime(), employeeList.getSunEndTime()==null?getString(R.string.scr_lbl_end_time):employeeList.getSunEndTime()));
                                setAllDisabled(Integer.parseInt(isAdmin),false);
                                editClick = false;
                                setTimeDisabled(0);
                                setDis();
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
        if (requestCode == 1000) {
            if(resultCode == Activity.RESULT_OK){
                String locationLat = SharedPref.getInstance(mContext).read(SharedPrefKey.ENTERED_VISIT_LAT, "0.0");
                String locationLng = SharedPref.getInstance(mContext).read(SharedPrefKey.ENTERED_VISIT_LNG, "0.0");
                String location = SharedPref.getInstance(mContext).read(SharedPrefKey.LOCATION_ENTERED_TXT, "Not Available");
                String result=data.getStringExtra("result");
                //String str = "<b>"+result+"</b>";
                AutoComOfficeLocation.setText(location);
                officeLat = locationLat;
                officeLong = locationLng;
                //tvAddLocation.setText(location);

                //LogUtil.printToastMSG(mContext,km+" K.M.");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
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

    private void setDis(){
        try {
            int color = Color.WHITE;
            input_textEmailId.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
            input_textEmailId.setEndIconTintList(ColorStateList.valueOf(color));
           /* ((BaseActivity)mContext).setError(1, input_textEmailId, "");
            ((BaseActivity)mContext).setError(1, input_textEEmail, "");
            ((BaseActivity)mContext).setError(1, input_textGstNo, "");*/
            input_textEEmail.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_right_accept));
            input_textEEmail.setEndIconTintList(ColorStateList.valueOf(color));
            input_textGstNo.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_right_accept));
            input_textGstNo.setEndIconTintList(ColorStateList.valueOf(color));
            input_textEmailId.setErrorEnabled(false);
            input_textEEmail.setErrorEnabled(false);
            input_textGstNo.setErrorEnabled(false);
        }catch (Exception e){e.printStackTrace();}
    }

    private void setAllDisabled(int type,boolean state){
        //all
        AutoComOfficeLocation.setEnabled(state);
        if(state){ setClickListener();}
        if(!state){
            setDis();
            imgEdit.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_edit_all));
        }else{
            imgEdit.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_edit_close));
        }
        if(type==0) {
            //employee
            if(!state){
            tvEmpName.setEnabled(state);}
            layAddLocation.setEnabled(state);
            AutoComtEEmail.setEnabled(state);
            AutoComEMobile.setEnabled(state);
            AutoComEDesi.setEnabled(state);
            AutoComtEGender.setEnabled(state);
            layCalender.setEnabled(state);
            etDescr.setEnabled(state);
            AutoComPincode.setEnabled(state);
            tvDateValue.setEnabled(state);
            if(state){
            btnESubmit.setVisibility(View.VISIBLE);}else{btnESubmit.setVisibility(View.GONE);}
            setTimeDisabled(0);
        }else {
            //company
            if(!state){
                tvEmpName.setEnabled(state);}
            layAddLocation.setEnabled(state);
            AutoComGstNo.setEnabled(state);
            etOfAddress.setEnabled(state);
            AutoComDistRange.setEnabled(state);
            AutoComOPincode.setEnabled(state);
            AutoComMobile.setEnabled(state);
            AutoComEmailId.setEnabled(state);
            //if(!state){tvDateValue.setTextColor(mContext.getResources().getColor(R.color.light_grey_30));}else{}
            //AutoComStaff.setEnabled(state);
            if(state){
            btnSubmit.setVisibility(View.VISIBLE);}else{btnSubmit.setVisibility(View.GONE);}
            setTimeDisabled(type);
        }
    }

    private void setTimeDisabled(int type){
        if(type==0) {
            //editClick = false;
           setAdapterForTimingList(false);
        }else {
            //company
            setAdapterForTimingList(true);
        }
    }

}