package com.ominfo.hra_app.ui.payment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.ominfo.hra_app.ui.employees.model.EmployeeList;
import com.ominfo.hra_app.ui.leave.model.ActiveEmployeeListEmpDatum;
import com.ominfo.hra_app.ui.leave.model.ActiveEmployeeListResponse;
import com.ominfo.hra_app.ui.login.LoginActivity;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.my_account.MyAccountFragment;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.payment.adapter.PaymentAdapter;
import com.ominfo.hra_app.ui.payment.model.AddUserRequest;
import com.ominfo.hra_app.ui.payment.model.AddUserViewModel;
import com.ominfo.hra_app.ui.payment.model.MyPlanViewModel;
import com.ominfo.hra_app.ui.payment.model.MyPlansResponse;
import com.ominfo.hra_app.ui.payment.model.PayPlanResponse;
import com.ominfo.hra_app.ui.payment.model.PayRenewPlanViewModel;
import com.ominfo.hra_app.ui.payment.model.RenewPlanEmpDatum;
import com.ominfo.hra_app.ui.payment.model.RenewPlanResponse;
import com.ominfo.hra_app.ui.payment.model.RenewPlanViewModel;
import com.ominfo.hra_app.ui.salary.adapter.SalaryNewDisAdapter;
import com.ominfo.hra_app.ui.salary.model.SalaryAllList;
import com.ominfo.hra_app.ui.salary.model.SalaryAllListViewModel;
import com.ominfo.hra_app.ui.salary.model.SalaryAllResponse;
import com.ominfo.hra_app.ui.salary.model.UpdateSalaryRequest;
import com.ominfo.hra_app.ui.salary.model.UpdateSalaryResponse;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentFragment extends BaseFragment {

    Context mContext;
    PaymentAdapter salaryDisbursementAdapter;
    @BindView(R.id.imgBack)
    LinearLayoutCompat imgBack;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvToolbarTitle;
    private AppDatabase mDb;
    List<SalaryAllList> searchresultList = new ArrayList<>();
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    final Calendar myCalendar = Calendar.getInstance();
    @Inject
    ViewModelFactory mViewModelFactory;
    private MyPlanViewModel myPlanViewModel;
    private RenewPlanViewModel renewPlanViewModel;
    private PayRenewPlanViewModel payRenewPlanViewModel;
    private AddUserViewModel addUserViewModel;
    String selectedActiveEmpid = "0";
    double total = 0.0;

    @BindView(R.id.tvPerUser)
    AppCompatTextView tvPerUser;
    @BindView(R.id.tvStaffStrength)
    AppCompatTextView tvStaffStrength;
    @BindView(R.id.tvPlanDuration)
    AppCompatTextView tvPlanDuration;
    boolean User = false;
    @BindView(R.id.AddUser)
    RelativeLayout AddUser;
    @BindView(R.id.renewUser)
    RelativeLayout renewUser;
    Dialog mDialogRenewUser;
    double gstPer =0;Double pricePerDay = 0.0;long days = 0;
    double big1 = 0.0;double subChargeApi = 0.0, gstAmount = 0.0;
    int strength = 0;
    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance(String param1, String param2) {
        PaymentFragment fragment = new PaymentFragment();
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
        View view = inflater.inflate(R.layout.activity_payment, container, false);
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
        callMyPlansApi();
    }

    private void injectAPI() {
        myPlanViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MyPlanViewModel.class);
        myPlanViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_MY_PLANS));

        renewPlanViewModel = ViewModelProviders.of(this, mViewModelFactory).get(RenewPlanViewModel.class);
        renewPlanViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_RENEW_PLANS));

        payRenewPlanViewModel = ViewModelProviders.of(this, mViewModelFactory).get(PayRenewPlanViewModel.class);
        payRenewPlanViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_PAY_RENEW_PLAN));

        addUserViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AddUserViewModel.class);
        addUserViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_ADD_USER));

    }

    /* Call Api For My Plans list */
    private void callMyPlansApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_current_subs_plan);
                RequestBody mRequestCom = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                myPlanViewModel.hitPaymentAPI(mRequestAction, mRequestCom);
            } else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }
    /* Call Api For Renew Plans list */
    private void callRenewPlanApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_renew_plan_details);
                RequestBody mRequestCom = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                renewPlanViewModel.hitRenewPlanAPI(mRequestAction, mRequestCom);
            } else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }


    /* Call Api For Renew Plans list */
    private void callPayRenewPlanApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_renew_subs_plan);
                RequestBody mRequestCom = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                payRenewPlanViewModel.hitPayRenewPlanAPI(mRequestAction, mRequestCom);
            } else {
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
    public void showRenewUserDialog(RenewPlanEmpDatum data) {
        mDialogRenewUser = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogRenewUser.setContentView(R.layout.dialog_renew_plan);
        mDialogRenewUser.setCanceledOnTouchOutside(true);
        AppCompatButton payButton = mDialogRenewUser.findViewById(R.id.payButton);
        AppCompatTextView tvDateRenew = mDialogRenewUser.findViewById(R.id.tvDateRenew);
        AppCompatTextView tvNoOfEmployees = mDialogRenewUser.findViewById(R.id.tvNoOfEmployees);
        AppCompatTextView tvTotalEmp = mDialogRenewUser.findViewById(R.id.tvTotalEmp);
        AppCompatTextView tvCharges = mDialogRenewUser.findViewById(R.id.tvCharges);
        AppCompatTextView tvGstValue = mDialogRenewUser.findViewById(R.id.tvGstValue);
        AppCompatTextView tvGstPer = mDialogRenewUser.findViewById(R.id.tvGstPer);
        AppCompatTextView tvTotalValue = mDialogRenewUser.findViewById(R.id.tvTotalValue);
        RelativeLayout layClose = mDialogRenewUser.findViewById(R.id.layClose);
        tvDateRenew.setText("Date : "+AppUtils.dateConvertYYYYToDD(data.getValidFrom())+" to "+
                AppUtils.dateConvertYYYYToDD(data.getValidTo()));
        tvNoOfEmployees.setText("No of Employees : "+data.getStaffStrength());
        tvTotalEmp.setText(data.getStaffStrength());
        tvCharges.setText(getString(R.string.scr_lbl_rs)+data.getPricePerPerson()+" / user / year");
        tvGstValue.setText(getString(R.string.scr_lbl_rs)+data.getGstAmount());
        tvGstPer.setText("Gst "+data.getGstPercent()+"%");
        tvTotalValue.setText(getString(R.string.scr_lbl_rs)+data.getTotalCharge());

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callPayRenewPlanApi();
            }
        });
        layClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogRenewUser.dismiss();
            }
        });

        mDialogRenewUser.show();
    }

    //show salary add user popup
    public void showAddUserDialog(RenewPlanEmpDatum data) {
        mDialogRenewUser = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogRenewUser.setContentView(R.layout.dialog_add_user);
        mDialogRenewUser.setCanceledOnTouchOutside(true);
        AppCompatButton payButton = mDialogRenewUser.findViewById(R.id.payButton);
        AppCompatTextView tvDateRenew = mDialogRenewUser.findViewById(R.id.tvDateRenew);
        AppCompatAutoCompleteTextView tvNoOfEmployees = mDialogRenewUser.findViewById(R.id.tvNoOfEmp);
        AppCompatTextView tvTotalEmp = mDialogRenewUser.findViewById(R.id.tvTotalEmp);
        AppCompatTextView tvCharges = mDialogRenewUser.findViewById(R.id.tvCharges);
        AppCompatTextView tvGstValue = mDialogRenewUser.findViewById(R.id.tvGstValue);
        AppCompatTextView tvGstPer = mDialogRenewUser.findViewById(R.id.tvGstPer);
        AppCompatTextView tvTotalValue = mDialogRenewUser.findViewById(R.id.tvTotalValue);
        RelativeLayout layClose = mDialogRenewUser.findViewById(R.id.layClose);
        tvDateRenew.setText("Date : "+AppUtils.dateConvertYYYYToDD(data.getValidFrom())+" to "+
                AppUtils.dateConvertYYYYToDD(data.getValidTo()));
        tvNoOfEmployees.setText("0");
        tvTotalEmp.setText(data.getStaffStrength());
        tvCharges.setText(getString(R.string.scr_lbl_rs)+data.getPricePerPerson()+" / user / year");
        tvGstValue.setText(getString(R.string.scr_lbl_rs)+data.getGstAmount());
        tvGstPer.setText("Gst "+data.getGstPercent()+"%");
        tvTotalValue.setText(getString(R.string.scr_lbl_rs)+data.getTotalCharge());

        try{
            gstPer = Double.parseDouble(data.getGstPercent());
            pricePerDay = Double.parseDouble(data.getPricePerPerson()) / 365;
            days = getDaysBetweenDates(AppUtils.getCurrentDateInyyyymmdd(),data.getValidTo());
            /*strength = tvNoOfEmployees.getText().toString().equals("")
                    ||tvNoOfEmployees.getText().toString().equals("0")?1:
                    Integer.parseInt(tvNoOfEmployees.getText().toString());
            Double price = (pricePerDay*days*strength)+Double.parseDouble(data.getSubCharge());//subcharges
            subChargeApi = String.valueOf(price);
            gstAmount = String.valueOf(price*gstPer/100); //gst amount
            Double big1 = price+((price*gstPer)/100); //total
            tvGstValue.setText(getString(R.string.scr_lbl_rs)+Math.floor(Double.parseDouble(gstAmount)));
            tvTotalValue.setText(getString(R.string.scr_lbl_rs)+""+Math.floor(big1));*/

        }catch (Exception e){
           LogUtil.printLog("test_user_",e.getMessage());
        }

        tvNoOfEmployees.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                    try{
                         strength = s.toString().equals("")
                               ?0:
                                Integer.parseInt(s.toString());
                        Double price = (pricePerDay*days*strength)+Double.parseDouble(data.getSubCharge());//subcharges
                        subChargeApi = price;
                        gstAmount = price*gstPer/100; //gst amount
                        Double big1 = price+((price*gstPer)/100); //total
                        tvGstValue.setText(getString(R.string.scr_lbl_rs)+Math.floor(gstAmount));
                        tvTotalValue.setText(getString(R.string.scr_lbl_rs)+""+Math.floor(big1));

                    }catch (Exception e){
                        LogUtil.printLog("test_user_",e.getMessage());
                    }
                //}
            }
        });
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (NetworkCheck.isInternetAvailable(mContext)) {
                    LoginTable loginTable = mDb.getDbDAO().getLoginData();
                    if (loginTable != null) {
                        RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_add_user);
                        RequestBody mRequestComId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                        RequestBody mRequestAddUser = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(strength));
                        RequestBody mRequestEmpId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                        RequestBody mRequestGstPer = RequestBody.create(MediaType.parse("text/plain"), String.format("%.2f", gstPer));
                        RequestBody mRequestSubcharges = RequestBody.create(MediaType.parse("text/plain"), String.format("%.2f",subChargeApi));
                        RequestBody mRequestGstAmt = RequestBody.create(MediaType.parse("text/plain"), String.format("%.2f",gstAmount));
                        RequestBody mRequestTotalCharges = RequestBody.create(MediaType.parse("text/plain"), String.format("%.2f",big1));

                        AddUserRequest addUserRequest = new AddUserRequest();
                        addUserRequest.setAction(mRequestAction);
                        addUserRequest.setCompany_id(mRequestComId);
                        addUserRequest.setAdd_user(mRequestAddUser);
                        addUserRequest.setEmp_id(mRequestEmpId);
                        addUserRequest.setGst_percent(mRequestGstPer);
                        addUserRequest.setSub_charge(mRequestSubcharges);
                        addUserRequest.setGst_amount(mRequestGstAmt);
                        addUserRequest.setTotal_charge(mRequestTotalCharges);

                        addUserViewModel.hitAddUserPlanAPI(addUserRequest);
                    } else {
                        LogUtil.printToastMSG(mContext, "Something is wrong.");
                    }
                } else {
                    LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                }
            }
        });
        layClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogRenewUser.dismiss();
            }
        });

        mDialogRenewUser.show();
    }

    public static long getDaysBetweenDates(String start, String end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date startDate, endDate;
        long numberOfDays = 0;
        try {
            startDate = dateFormat.parse(start);
            endDate = dateFormat.parse(end);
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return numberOfDays;
    }

    private static long getUnitBetweenDates(Date startDate, Date endDate, TimeUnit unit) {
        long timeDiff = endDate.getTime() - startDate.getTime();
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS);
    }

    private void setToolbar() {
        //set toolbar title
        tvToolbarTitle.setText(R.string.scr_lbl_my_planes);
        ((BaseActivity) mContext).initToolbar(5, mContext, R.id.imgBack, R.id.imgReport, R.id.imgNotify, tvNotifyCount, R.id.imgBack, R.id.imgCall);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MyAccountFragment();
                ((BaseActivity) mContext).moveFragment(mContext, fragment);
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
    @OnClick({R.id.renewUser,R.id.AddUser})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.renewUser:
                User = true;
                callRenewPlanApi();
                break;
            case R.id.AddUser:
                User = false;
                callRenewPlanApi();
                break;
        }
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    //show Succes register popup
    public void showThanksForRegisterDialog(String msg) {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_thanks_for_registering);
        //mDialog.setCanceledOnTouchOutside(true);
        RelativeLayout mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatTextView appcomptext = mDialog.findViewById(R.id.appcomptext);
        appcomptext.setText(msg);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
            }
        }, 5000);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_MY_PLANS)) {
                            MyPlansResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), MyPlansResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                tvPerUser.setText(responseModel.getResult().getEmpData().get(0).getPricePerPerson());
                                tvStaffStrength.setText("No of employees : "+responseModel.getResult().getEmpData().get(0).getStaffStrength());
                                tvPlanDuration.setText("Plan Duration : "+
                                        AppUtils.dateConvertYYYYToDD(responseModel.getResult().getEmpData().get(0).getValidFrom())
                                +" - "+AppUtils.dateConvertYYYYToDD(responseModel.getResult().getEmpData().get(0).getValidTo()));
                            }
                            else{
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_RENEW_PLANS)) {
                            RenewPlanResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), RenewPlanResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                              if(User) {
                                  showRenewUserDialog(responseModel.getResult().getEmpData().get(0));
                              }else{
                                  //show salary add user popup
                                  showAddUserDialog(responseModel.getResult().getEmpData().get(0));
                              }
                            }else{
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }

                    } catch (Exception e) {
                        LogUtil.printToastMSG(mContext,getString(R.string.msg_no_date_leave));
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_PAY_RENEW_PLAN)) {
                            PayPlanResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), PayPlanResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                mDialogRenewUser.dismiss();
                                showThanksForRegisterDialog(responseModel.getResult().getMessage());
                            }
                            else{
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_ADD_USER)) {
                            PayPlanResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), PayPlanResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                mDialogRenewUser.dismiss();
                                showThanksForRegisterDialog(responseModel.getResult().getMessage());
                            }
                            else{
                                LogUtil.printToastMSG(mContext,responseModel.getResult().getMessage());
                            }
                        }

                    } catch (Exception e) {
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