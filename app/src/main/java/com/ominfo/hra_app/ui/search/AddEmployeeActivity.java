package com.ominfo.hra_app.ui.search;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.interfaces.SharedPrefKey;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.attendance.model.MarkAttendanceRequest;
import com.ominfo.hra_app.ui.attendance.model.UpdateAttendanceRequest;
import com.ominfo.hra_app.ui.dashboard.model.DashModel;
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
    //private GetVisitNoViewModel getVisitNoViewModel;
    //private MarkAttendanceViewModel markAttendanceViewModel;
    //private UpdateAttendanceViewModel updateAttendanceViewModel;
    private AppDatabase mDb;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvTitle;

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
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    private void injectAPI() {
       /* getVisitNoViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetVisitNoViewModel.class);
        getVisitNoViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_GET_VISIT_NO));

        markAttendanceViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MarkAttendanceViewModel.class);
        markAttendanceViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_MARK_ATTENDANCE));

        updateAttendanceViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UpdateAttendanceViewModel.class);
        updateAttendanceViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_UPDATE_ATTENDANCE));
   */ }

    /* Call Api For visit no */
    private void callVisitNoApi() {
        if (NetworkCheck.isInternetAvailable(this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            /*if(loginTable!=null) {
                RequestBody mRequestBodyTypeAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_visit_no);
                RequestBody mRequestBodyTypeCompID = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());//loginTable.getCompanyId());
            */
            //getVisitNoViewModel.hitGetVisitNoApi();
            /*}
            else {
                LogUtil.printToastMSG(this, "Something is wrong.");
            }*/
        } else {
            LogUtil.printToastMSG(this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Mark Attendance */
    private void callMarkAttendanceApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_mark_attendance);
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                RequestBody mRequestBodyDate = RequestBody.create(MediaType.parse("text/plain"), AppUtils.getCurrentDateInyyyymmdd());
                String startlocationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LAT, "0.0");
                String startlocationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LONG, "0.0");
                String mTime_ = AppUtils.getCurrentTimeIn24hr();
                RequestBody mRequestBodyStartTime = RequestBody.create(MediaType.parse("text/plain"), mTime_);
                RequestBody mRequestBodyStartLat = RequestBody.create(MediaType.parse("text/plain"), startlocationLat);
                RequestBody mRequestBodyStartLong = RequestBody.create(MediaType.parse("text/plain"), startlocationLng);
                SharedPref.getInstance(getBaseContext()).write(SharedPrefKey.ATTENDANCE_CHECKIN_TIME, mTime_);
                MarkAttendanceRequest markAttendanceRequest = new MarkAttendanceRequest();
                markAttendanceRequest.setAction(mRequestBodyAction);
                markAttendanceRequest.setEmpId(mRequestBodyTypeEmpId);
                markAttendanceRequest.setDate(mRequestBodyDate);
                markAttendanceRequest.setStartTime(mRequestBodyStartTime);
                markAttendanceRequest.setStartLatitude(mRequestBodyStartLat);
                markAttendanceRequest.setStartLongitude(mRequestBodyStartLong);
                //markAttendanceViewModel.hitMarkAttendanceApi(markAttendanceRequest);
            } else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Update Attendance */
    private void callUpdateAttendanceApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                String id = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENDANCE_ID, "0");
                String startTime = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENDANCE_CHECKIN_TIME, "00:00:00");
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_update_attendance);
                RequestBody mRequestBodyStartTime = RequestBody.create(MediaType.parse("text/plain"), startTime);
                RequestBody mRequestBodyEndTime = RequestBody.create(MediaType.parse("text/plain"), AppUtils.getCurrentTimeIn24hr());
                RequestBody mRequestBodyId = RequestBody.create(MediaType.parse("text/plain"), id);
                String startlocationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LAT, "0.0");
                String startlocationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ATTENTION_LOC_LONG, "0.0");
                RequestBody mRequestBodyEndLat = RequestBody.create(MediaType.parse("text/plain"), startlocationLat);
                RequestBody mRequestBodyEndLong = RequestBody.create(MediaType.parse("text/plain"), startlocationLng);

                UpdateAttendanceRequest markAttendanceRequest = new UpdateAttendanceRequest();
                markAttendanceRequest.setAction(mRequestBodyAction);
                markAttendanceRequest.setStartTime(mRequestBodyStartTime);
                markAttendanceRequest.setEndTime(mRequestBodyEndTime);
                markAttendanceRequest.setId(mRequestBodyId);
                markAttendanceRequest.setEndLatitude(mRequestBodyEndLat);
                markAttendanceRequest.setEndLongitude(mRequestBodyEndLong);
                //updateAttendanceViewModel.hitUpdateAttendanceApi(markAttendanceRequest);
            } else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }


    private void setToolbar() {
        //set toolbar title
        tvTitle.setText(R.string.scr_lbl_manage_employee);
        //initToolbar(1, mContext, R.id.imgBack, R.id.imgReport, R.id.imgReport, 0, R.id.imgCall);
    }


    //perform click actions
    @OnClick({R.id.btnSubmit,R.id.btnCancel,R.id.btnDeactivate})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnSubmit:
                showSuccessDialog("Employee edited successfully !",false,this);
                finish();
                break;
            case R.id.btnCancel:
                finish();
                break;
            case R.id.btnDeactivate:
                showDeactivateAccountDialog(this);
                break;
        }
    }

    public void showDeactivateAccountDialog(Activity mContext) {
        Dialog mDialogLogout = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogLogout.setContentView(R.layout.dialog_deactive_account);
        mDialogLogout.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialogLogout.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialogLogout.findViewById(R.id.uploadButton);
        AppCompatButton cancelButton = mDialogLogout.findViewById(R.id.cancelButton);

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogLogout.dismiss();
                showSuccessDialog("Account deactived successfully.",false,mContext);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
                    datePickerField.setText("-" + sdf.format(myCalendar.getTime()));
                } else {
                    datePickerField.setText(sdf.format(myCalendar.getTime()));
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_VISIT_NO)) {
                            VisitNoResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), VisitNoResponse.class);
                            if (responseModel != null && responseModel.getStatus() == 1) {
                                //textVisitNo.setText("Visit Number : "+responseModel.getNumber());
                                SharedPref.getInstance(this).write(SharedPrefKey.VISIT_NO, responseModel.getNumber());

                            } else {
                                LogUtil.printToastMSG(mContext, responseModel.getMessage());
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