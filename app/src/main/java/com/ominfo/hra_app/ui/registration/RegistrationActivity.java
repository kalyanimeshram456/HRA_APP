package com.ominfo.hra_app.ui.registration;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.login.LoginActivity;
import com.ominfo.hra_app.ui.registration.model.ApplyCouponResponse;
import com.ominfo.hra_app.ui.registration.model.ApplyCouponViewModel;
import com.ominfo.hra_app.ui.registration.model.CheckPrefixResponse;
import com.ominfo.hra_app.ui.registration.model.CheckPrefixViewModel;
import com.ominfo.hra_app.ui.registration.model.RegisterResponse;
import com.ominfo.hra_app.ui.registration.model.RegistrationRequest;
import com.ominfo.hra_app.ui.registration.model.RegistrationViewModel;
import com.ominfo.hra_app.ui.registration.model.SubscriptionResponse;
import com.ominfo.hra_app.ui.registration.model.SubscriptionViewModel;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.http.Part;

public class RegistrationActivity extends BaseActivity {

    Context mContext;
    @Inject
    ViewModelFactory mViewModelFactory;
    private RegistrationViewModel registrationViewModel;
    private CheckPrefixViewModel checkPrefixViewModel;
    private SubscriptionViewModel subscriptionViewModel;
    private ApplyCouponViewModel applyCouponViewModel;
    private AppDatabase mDb;
    public static boolean activityVisible; // Variable that will check the

    @BindView(R.id.input_textName)
    TextInputLayout input_textName;
    @BindView(R.id.AutoComTextViewName)
    AppCompatAutoCompleteTextView AutoComTextViewName;
    @BindView(R.id.input_textGstNo)
    TextInputLayout input_textGstNo;
    @BindView(R.id.AutoComGstNo)
    AppCompatAutoCompleteTextView AutoComGstNo;
    @BindView(R.id.input_textPincode)
    TextInputLayout input_textPincode;
    @BindView(R.id.AutoComPincode)
    AppCompatAutoCompleteTextView AutoComPincode;
    @BindView(R.id.input_textAddress)
    TextInputLayout input_textAddress;
    @BindView(R.id.AutoComAddress)
    AppCompatAutoCompleteTextView AutoComAddress;
    @BindView(R.id.input_textUsernamePrefix)
    TextInputLayout input_textUsernamePrefix;
    @BindView(R.id.AutoComUsernamePrefix)
    AppCompatAutoCompleteTextView AutoComUsernamePrefix;
    @BindView(R.id.input_textCoupon)
    TextInputLayout input_textCoupon;
    @BindView(R.id.AutoComCoupon)
    AppCompatAutoCompleteTextView AutoComCoupon;
    @BindView(R.id.input_textEmailId)
    TextInputLayout input_textEmailId;
    @BindView(R.id.AutoComEmailId)
    AppCompatAutoCompleteTextView AutoComEmailId;
    @BindView(R.id.input_textAdminName)
    TextInputLayout input_textAdminName;
    @BindView(R.id.AutoComAdminName)
    AppCompatAutoCompleteTextView AutoComAdminName;
    @BindView(R.id.input_textMobileNo)
    TextInputLayout input_textMobileNo;
    @BindView(R.id.AutoComMobileNo)
    AppCompatAutoCompleteTextView AutoComMobileNo;
    @BindView(R.id.input_textEmpStrength)
    TextInputLayout input_textEmpStrength;
    @BindView(R.id.AutoComEmpStrength)
    AppCompatAutoCompleteTextView AutoComEmpStrength;
    @BindView(R.id.tvEx)
    AppCompatTextView tvEx;
    @BindView(R.id.tvSubDesc)
    AppCompatTextView tvSubDesc;
    @BindView(R.id.tvSubRs)
    AppCompatTextView tvSubRs;
    @BindView(R.id.radioYear)
    AppCompatRadioButton radioYear;
    @BindView(R.id.radioTrial)
    AppCompatRadioButton radioTrial;
    @BindView(R.id.applyButton)
    AppCompatButton applyButton;
    @BindView(R.id.removeButton)
    AppCompatButton removeButton;
    String subCharges = "0",discountAmount = "0",couponCode = "",subChargeApi = "",gstAmount="",
    totalAmountCharges= "";
    int gstPer = 18;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getDeps().inject(this);
        ButterKnife.bind(this);
        mContext = this;
        injectAPI();
        init();
    }

    private void injectAPI() {
        registrationViewModel = ViewModelProviders.of(RegistrationActivity.this, mViewModelFactory).get(RegistrationViewModel.class);
        registrationViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_register));

        checkPrefixViewModel = ViewModelProviders.of(RegistrationActivity.this, mViewModelFactory).get(CheckPrefixViewModel.class);
        checkPrefixViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_check_prefix));

        subscriptionViewModel = ViewModelProviders.of(RegistrationActivity.this, mViewModelFactory).get(SubscriptionViewModel.class);
        subscriptionViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_get_subs_price));

        applyCouponViewModel = ViewModelProviders.of(RegistrationActivity.this, mViewModelFactory).get(ApplyCouponViewModel.class);
        applyCouponViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_check_coupon_validity));
    }

    /* Call Api For Login user and get user details */
    private void callRegisterUserApi() {
        if (NetworkCheck.isInternetAvailable(RegistrationActivity.this)) {
            RegistrationRequest request = new RegistrationRequest();
            RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_register);
            RequestBody mRequestBodyName = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewName.getText().toString().trim());
            RequestBody mRequestBodyAddress = RequestBody.create(MediaType.parse("text/plain"), AutoComAddress.getText().toString().trim());
            RequestBody mRequestBodyPincode = RequestBody.create(MediaType.parse("text/plain"), AutoComPincode.getText().toString().trim());
            RequestBody mRequestBodyContactNo = RequestBody.create(MediaType.parse("text/plain"), AutoComMobileNo.getText().toString().trim());
            RequestBody mRequestBodyEmailId = RequestBody.create(MediaType.parse("text/plain"), AutoComEmailId.getText().toString().trim());
            RequestBody mRequestBodyStaffStrength = RequestBody.create(MediaType.parse("text/plain"), AutoComEmpStrength.getText().toString().trim());
            RequestBody mRequestBodyUserPrefix = RequestBody.create(MediaType.parse("text/plain"), AutoComUsernamePrefix.getText().toString().trim());
            RequestBody mRequestgst_percent = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(gstPer));
            RequestBody mRequestsub_charge = RequestBody.create(MediaType.parse("text/plain"), subChargeApi);
            RequestBody mRequestgst_amount = RequestBody.create(MediaType.parse("text/plain"), gstAmount);
            RequestBody mRequestdiscount_rate = RequestBody.create(MediaType.parse("text/plain"), discountAmount);
            RequestBody mRequesttotal_charge = RequestBody.create(MediaType.parse("text/plain"),totalAmountCharges);
            RequestBody mRequestcoupon = RequestBody.create(MediaType.parse("text/plain"), couponCode);
            RequestBody mRequestadmin_name = RequestBody.create(MediaType.parse("text/plain"), AutoComAdminName.getText().toString().trim());
            RequestBody mRequestgst_no = RequestBody.create(MediaType.parse("text/plain"), AutoComGstNo.getText().toString().trim());

            String plan = "";
            if(radioYear.isChecked()){plan="Yearly";} if(radioTrial.isChecked()){plan="Trial";}
            RequestBody mRequestplan_type = RequestBody.create(MediaType.parse("text/plain"), plan);

            request.setAction(mRequestBodyAction);
            request.setName(mRequestBodyName);
            request.setAddress(mRequestBodyAddress);
            request.setPincode(mRequestBodyPincode);
            request.setContactNo(mRequestBodyContactNo);
            request.setEmailId(mRequestBodyEmailId);
            request.setStaffStrength(mRequestBodyStaffStrength);
            request.setUserPrefix(mRequestBodyUserPrefix);
            request.setGst_percent(mRequestgst_percent);
            request.setSub_charge(mRequestsub_charge);
            request.setGst_amount(mRequestgst_amount);
            request.setDiscount_rate(mRequestdiscount_rate);
            request.setTotal_charge(mRequesttotal_charge);
            request.setCoupon(mRequestcoupon);
            request.setPlan_type(mRequestplan_type);
            request.setAdmin_name(mRequestadmin_name);
            request.setGst_no(mRequestgst_no);
            registrationViewModel.hitRegistrationApi(request);

        } else {
            LogUtil.printToastMSG(RegistrationActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For user prefix */
    private void callUserPrefixApi() {
        if (NetworkCheck.isInternetAvailable(RegistrationActivity.this)) {
            RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_check_prefix);
            RequestBody mRequestBodyName = RequestBody.create(MediaType.parse("text/plain"), AutoComUsernamePrefix.getText().toString().trim());
            checkPrefixViewModel.hitCheckPrefixApi(mRequestBodyAction, mRequestBodyName);

        } else {
            LogUtil.printToastMSG(RegistrationActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }
    /* Call Api For user prefix */
    private void callCheckCouponApi() {
        if (NetworkCheck.isInternetAvailable(RegistrationActivity.this)) {
            RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_check_coupon_validity);
            RequestBody mRequestBodyName = RequestBody.create(MediaType.parse("text/plain"), AutoComCoupon.getText().toString().trim());
            applyCouponViewModel.hitApplyCouponApi(mRequestBodyAction, mRequestBodyName);

        } else {
            LogUtil.printToastMSG(RegistrationActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
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
                        1000);

            } else {
                // reqPermissionCode();
            }
        } else {
            //reqPermissionCode();
        }
    }


    //show subscription popup
    public void showSubscriptionDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_subscription_details);
        mDialog.setCanceledOnTouchOutside(true);
        RelativeLayout mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatTextView tvEmpNo = mDialog.findViewById(R.id.tvEmpNo);
        AppCompatTextView tvCharge = mDialog.findViewById(R.id.tvCharge);
        AppCompatTextView tvGST = mDialog.findViewById(R.id.tvGST);
        AppCompatTextView tvTotal = mDialog.findViewById(R.id.tvTotal);
        AppCompatTextView tvDiscountValue = mDialog.findViewById(R.id.tvDiscountValue);
        AppCompatTextView tvDiscount = mDialog.findViewById(R.id.tvDiscount);
        tvDiscount.setText("Coupon Discount\n"+"code-"+couponCode);
        if(radioYear.isChecked()) {
            tvCharge.setText(getString(R.string.scr_lbl_rs) + subCharges + " / user");
            int strength = AutoComEmpStrength.getText().toString().equals("") ? 1 :
                    Integer.parseInt(AutoComEmpStrength.getText().toString());
            tvEmpNo.setText(AutoComEmpStrength.getText().toString().equals("") ? "0" : AutoComEmpStrength.getText().toString() + " Employees");
            Double price = Double.parseDouble(subCharges);
            Double biggst = ((strength * price) * gstPer / 100);
            Double big1 = strength * price + ((strength * price) * gstPer / 100);
            tvGST.setText(getString(R.string.scr_lbl_rs)+biggst + "");
            tvTotal.setText(getString(R.string.scr_lbl_rs)+(big1-Integer.parseInt(discountAmount)) + "");
            tvDiscountValue.setText(getString(R.string.scr_lbl_rs)+discountAmount);
        }
        if(radioTrial.isChecked()) {
            tvCharge.setText(getString(R.string.scr_lbl_rs) + "200");
            int strength = AutoComEmpStrength.getText().toString().equals("") ? 1 :
                    Integer.parseInt(AutoComEmpStrength.getText().toString());
            tvEmpNo.setText(AutoComEmpStrength.getText().toString().equals("") ? "0" : AutoComEmpStrength.getText().toString() + " Employees");
            Double price = Double.parseDouble("200");
            Double biggst = ((price) * gstPer / 100);
            Double big1 = price + ((price) * gstPer / 100);
            tvGST.setText(getString(R.string.scr_lbl_rs)+biggst + "");
            tvTotal.setText(getString(R.string.scr_lbl_rs)+(big1-Integer.parseInt(discountAmount)) + "");
            tvDiscountValue.setText(getString(R.string.scr_lbl_rs)+discountAmount);
        }

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    //show Succes register popup
    public void showThanksForRegisterDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_thanks_for_registering);
        //mDialog.setCanceledOnTouchOutside(true);
        RelativeLayout mClose = mDialog.findViewById(R.id.imgCancel);
        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
                finish();
                launchScreen(mContext, LoginActivity.class);
            }
        }, 1100);*/
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                finish();
                launchScreen(mContext, LoginActivity.class);
            }
        });
        mDialog.show();
    }

    private void init() {
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        //editTextEmail.setText("hd001");
        //editTextPassword.setText("2437");
        radioYear.setChecked(true);
        setErrorMSG();
        AutoComUsernamePrefix.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    //callUserPrefixApi();
                }
            }
        });
        AutoComUsernamePrefix.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    callUserPrefixApi();
                }
                return false;
            }
        });
        AutoComEmpStrength.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    setSubCharges();
                }
            }
        });
        AutoComEmpStrength.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    setSubCharges();
                }
                return false;
            }
        });
        radioTrial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    radioYear.setChecked(false);
                    setDiscountChargesTrial();
                }
            }
        });
        radioYear.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    radioTrial.setChecked(false);
                    callSubscriptionChargesApi();
                }
            }
        });
        callSubscriptionChargesApi();
    }

    // set error if input field is blank
    private void setErrorMSG() {
        /*setErrorMessage(inputEmail, editTextEmail, getString(R.string.val_msg_please_enter_email));
        setErrorMessage(inputPassword, editTextPassword, getString(R.string.val_msg_please_enter_password));*/
    }

    //perform click actions
    @OnClick({R.id.imgInfo, R.id.btnRegister,R.id.applyButton,R.id.removeButton,R.id.radioYear,R.id.radioTrial})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.radioYear:
                break;
            case R.id.radioTrial:

                break;
            case R.id.imgInfo:
                showSubscriptionDialog();
                break;
            case R.id.applyButton:
                callCheckCouponApi();
                break;
            case R.id.removeButton:
                removeButton.setVisibility(View.GONE);
                applyButton.setVisibility(View.VISIBLE);
                AutoComCoupon.setText("");
                couponCode = "0";
                discountAmount = "0";
                if(radioYear.isChecked()){
                    setSubCharges();
                }
               if(radioTrial.isChecked()) {
                   setDiscountChargesTrial();
               }
                break;
            case R.id.btnRegister:
                if (isDetailsValid()) {
                    callRegisterUserApi();
                }
                break;
        }
    }

    private void setDiscountChargesTrial(){
        Double Charges = Double.valueOf(200);
        tvSubDesc.setText("Subscription charges will be\n ₹" + Charges + " for 20 days + GST");
        subChargeApi = Double.toString(Charges);
        gstAmount = String.valueOf((Charges)*gstPer/100);
        Double big1 = Charges+((Charges)*gstPer/100);
        totalAmountCharges = Double.toString(big1-Integer.parseInt(discountAmount));
        tvSubRs.setText(big1-Integer.parseInt(discountAmount)+"");
    }

    /* Call Api For Subscription Charges */
    private void callSubscriptionChargesApi() {
        if (NetworkCheck.isInternetAvailable(RegistrationActivity.this)) {
            RequestBody mRequestBodyName = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_subs_price);
            RequestBody mRequestBodyStart = RequestBody.create(MediaType.parse("text/plain"), AppUtils.getCurrentDateInyyyymmdd());
            RequestBody mRequestBodyEnd = RequestBody.create(MediaType.parse("text/plain"), AppUtils.dateAfterYear());
            subscriptionViewModel.hitSubscriptionApi(mRequestBodyName, mRequestBodyStart, mRequestBodyEnd);
        } else {
            LogUtil.printToastMSG(RegistrationActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /*check validations on field*/
    private boolean isDetailsValid() {
        if (TextUtils.isEmpty(AutoComTextViewName.getText().toString().trim())) {
            setError(0, input_textName, getString(R.string.err_enter_company_name));
            return false;
        } else if (TextUtils.isEmpty(AutoComAddress.getText().toString().trim())) {
            setError(0, input_textAddress, getString(R.string.err_enter_address));
            return false;
        } else if (TextUtils.isEmpty(AutoComPincode.getText().toString().trim())) {
            setError(0, input_textPincode, getString(R.string.err_enter_pincode));
            return false;
        } else if (TextUtils.isEmpty(AutoComUsernamePrefix.getText().toString().trim())) {
            setError(0, input_textUsernamePrefix, getString(R.string.err_enter_username_prefix));
            return false;
        } else if (TextUtils.isEmpty(AutoComEmailId.getText().toString().trim())) {
            setError(0, input_textEmailId, getString(R.string.err_enter_email_id));
            return false;
        } else if (TextUtils.isEmpty(AutoComAdminName.getText().toString().trim())) {
            setError(0, input_textAdminName, getString(R.string.err_enter_admin_name));
            return false;
        } else if (TextUtils.isEmpty(AutoComMobileNo.getText().toString().trim())) {
            setError(0, input_textMobileNo, getString(R.string.err_enter_mobile_no));
            return false;
        } else if (TextUtils.isEmpty(AutoComEmpStrength.getText().toString().trim())) {
            setError(0, input_textEmpStrength, getString(R.string.err_enter_emp_stregth));
            return false;
        }
        return true;
    }

    private boolean getValidUser() {
       /* String currentString = editTextEmail.getText().toString().trim();
        if(currentString.substring(0, 2).toLowerCase().equals("hd") ||
                currentString.substring(0, 2).toLowerCase().equals("ho")
                || currentString.substring(0, 2).toLowerCase().equals("jp")){
            return true;
        }
        else {
            return false;
        }*/
        return false;
    }

    /*Api response */
    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {

            case LOADING:
                showProgressLoader(getString(R.string.scr_message_please_wait));
                break;

            case SUCCESS:
                dismissLoader();
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    if (tag.equalsIgnoreCase(DynamicAPIPath.action_register)) {
                        RegisterResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), RegisterResponse.class);
                        if (responseModel != null/* && responseModel.getResult().getStatus().equals("success")*/) {
                            showThanksForRegisterDialog();
                        } else {
                            LogUtil.printToastMSG(RegistrationActivity.this, responseModel.getResult().getMessage());
                        }
                    }
                    if (tag.equalsIgnoreCase(DynamicAPIPath.action_check_prefix)) {
                        CheckPrefixResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), CheckPrefixResponse.class);
                        if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                            tvEx.setVisibility(View.GONE);
                            setError(1, input_textUsernamePrefix, responseModel.getResult().getMessage());
                        } else {
                            tvEx.setVisibility(View.GONE);
                            setError(0, input_textUsernamePrefix, responseModel.getResult().getMessage());
                            LogUtil.printToastMSG(RegistrationActivity.this, responseModel.getResult().getMessage());
                        }
                    }
                    if (tag.equalsIgnoreCase(DynamicAPIPath.action_get_subs_price)) {
                        SubscriptionResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), SubscriptionResponse.class);
                        if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                            subCharges = responseModel.getResult().getLeave().get(0).getPricePerPerson();
                            tvSubDesc.setText("Subscription charges will be\n ₹" + subCharges + " / User / Year + GST");
                            setSubCharges();
                            //LogUtil.printToastMSG(RegistrationActivity.this, responseModel.getResult().getMessage());
                        }
                    }
                    if (tag.equalsIgnoreCase(DynamicAPIPath.action_check_coupon_validity)) {
                        ApplyCouponResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ApplyCouponResponse.class);
                        if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                            discountAmount = responseModel.getResult().getList().getDiscountAmt();
                            couponCode = responseModel.getResult().getList().getCode();
                            AutoComCoupon.setError(getString(R.string.scr_lbl_coupon_applid));
                            removeButton.setVisibility(View.VISIBLE);
                            applyButton.setVisibility(View.GONE);
                            if(radioYear.isChecked()){
                                setSubCharges();
                            }
                            if(radioTrial.isChecked()) {
                                setDiscountChargesTrial();
                            }
                            setError(1, input_textCoupon, getString(R.string.scr_lbl_coupon_applid));
                           // LogUtil.printToastMSG(RegistrationActivity.this, responseModel.getResult().getMessage());
                        }
                        else{ AutoComCoupon.setError("Coupon is not valid.");
                            setError(0, input_textCoupon, "Coupon is not valid.");
                        }
                    }
                }
                break;
            case ERROR:
                dismissLoader();
                LogUtil.printToastMSG(RegistrationActivity.this, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }

    private void setSubCharges(){
        int strength = AutoComEmpStrength.getText().toString().equals("")?1:
                Integer.parseInt(AutoComEmpStrength.getText().toString());
        Double price = Double.parseDouble(subCharges);
        subChargeApi = String.valueOf(strength*price);
        gstAmount = String.valueOf((strength*price)*gstPer/100);
        Double big1 = strength*price+((strength*price)*gstPer/100);
        totalAmountCharges = Double.toString(big1-Integer.parseInt(discountAmount));
        tvSubRs.setText(big1-Integer.parseInt(discountAmount)+"");
    }

}