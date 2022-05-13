package com.ominfo.hra_app.ui.employees;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

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
import com.ominfo.hra_app.ui.dashboard.model.DashModel;
import com.ominfo.hra_app.ui.employees.model.AddEmployeeRequest;
import com.ominfo.hra_app.ui.employees.model.AddEmployeeResponse;
import com.ominfo.hra_app.ui.employees.model.AddEmployeeViewModel;
import com.ominfo.hra_app.ui.employees.model.DeactivateEmployeeResponse;
import com.ominfo.hra_app.ui.employees.model.DeactivateEmployeeViewModel;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.visit_report.model.VisitNoResponse;
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
import retrofit2.http.Part;

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
    //private UpdateAttendanceViewModel updateAttendanceViewModel;
    private AppDatabase mDb;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;
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
    Dialog mDialogDeactivate,mDialogDiscard;
    String from = "add";
    @BindView(R.id.btnDeactivate)
    AppCompatButton btnDeactivate;


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
        getIntentData();
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
            if(from.equals(Constants.add)){
                btnDeactivate.setVisibility(View.GONE);
            }
            else {
                btnDeactivate.setVisibility(View.VISIBLE);
            }
        }
    }

    private void injectAPI() {
        addEmployeeViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AddEmployeeViewModel.class);
        addEmployeeViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_add_employee));

        deactivateEmployeeViewModel = ViewModelProviders.of(this, mViewModelFactory).get(DeactivateEmployeeViewModel.class);
        deactivateEmployeeViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_deactivate_employee));
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
                addEmployeeViewModel.executeAddEmployeeAPI(request);
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
                RequestBody mRequestBodyUpdatedBy = RequestBody.create(MediaType.parse("text/plain"),loginTable.getToken());//loginTable.getCompanyId());
                RequestBody mRequestBodyEmpId = RequestBody.create(MediaType.parse("text/plain"), "0");//loginTable.getCompanyId());

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
        tvTitle.setText(R.string.scr_lbl_manage_employee);
        //initToolbar(1, mContext, R.id.imgBack, R.id.imgReport, R.id.imgReport, 0, R.id.imgCall);
    }


    //perform click actions
    @OnClick({R.id.btnSubmit,R.id.btnCancel,R.id.btnDeactivate,R.id.layCalender,
            R.id.imgToDate,R.id.layJoiningDate, R.id.imgJoiningDate})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnSubmit:
                if(isDetailsValid()) {
                    callAddEmployeeApi();
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
        } else if (TextUtils.isEmpty(AutoComEmailId.getText().toString().trim())) {
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
            setError(0, input_textOtherLeave, getString(R.string.err_enter_sick_leave));
            return false;
        }
        tvMissing.setVisibility(View.GONE);
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
                                showSuccessDialog("Employee edited successfully !",false,this);
                                finish();
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
                                showSuccessDialog("Account deactived successfully.",false,this);
                            }
                            else {
                                LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                            }
                        }
                    } catch (Exception e) {
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