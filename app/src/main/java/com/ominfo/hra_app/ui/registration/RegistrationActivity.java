package com.ominfo.hra_app.ui.registration;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Chronometer;
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
import com.ominfo.hra_app.common.customui.OtpEditText;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.interfaces.Constants;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.login.LoginActivity;
import com.ominfo.hra_app.ui.registration.model.ApplyCouponResponse;
import com.ominfo.hra_app.ui.registration.model.ApplyCouponViewModel;
import com.ominfo.hra_app.ui.registration.model.CheckPrefixResponse;
import com.ominfo.hra_app.ui.registration.model.CheckPrefixViewModel;
import com.ominfo.hra_app.ui.registration.model.GetOtpViewModel;
import com.ominfo.hra_app.ui.registration.model.RegisterResponse;
import com.ominfo.hra_app.ui.registration.model.RegistrationRequest;
import com.ominfo.hra_app.ui.registration.model.RegistrationViewModel;
import com.ominfo.hra_app.ui.registration.model.ResendOtpViewModel;
import com.ominfo.hra_app.ui.registration.model.SubscriptionResponse;
import com.ominfo.hra_app.ui.registration.model.SubscriptionViewModel;
import com.ominfo.hra_app.ui.registration.model.VerifyOtpResponse;
import com.ominfo.hra_app.ui.registration.model.VerifyOtpViewModel;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private GetOtpViewModel getOtpViewModel;
    private ResendOtpViewModel resendOtpViewModel;
    private CheckPrefixViewModel checkPrefixViewModel;
    private SubscriptionViewModel subscriptionViewModel;
    private ApplyCouponViewModel applyCouponViewModel;
    private VerifyOtpViewModel verifyOtpViewModel;
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
    @BindView(R.id.tvMissing)
    AppCompatTextView tvMissing;
    String subCharges = "0",discountAmount = "0",couponCode = "",subChargeApi = "",gstAmount="",
    totalAmountCharges= "";
    int gstPer = 18;
    boolean statusPrefix = false,statusOtp = false;
    @BindView(R.id.edtOpt)
    OtpEditText edtOpt;
    @BindView(R.id.tvResendOtp)
    AppCompatTextView tvResendOtp;
    @BindView(R.id.btnGetOtp)
    AppCompatButton btnGetOtp;
    @BindView(R.id.btnRegister)
    AppCompatButton btnRegister;
    @BindView(R.id.chronometer)
    Chronometer tvCounter;

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
        setClickListener();
        tvMissing.setVisibility(View.GONE);
    }

    private void setClickListener(){
        AutoComUsernamePrefix.addTextChangedListener(new TextWatcher() {

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
                    if(s.length()>2 && s.length()<=5) {
                        callUserPrefixApi();
                    }else{
                        input_textUsernamePrefix.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                    int color=Color.RED;
                    input_textUsernamePrefix.setEndIconTintList(ColorStateList.valueOf(color));
                    setError(0, input_textUsernamePrefix, getString(R.string.err_enter_username_prefix));
             }
                }
                else{
                    input_textUsernamePrefix.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                    int color=Color.RED;
                    input_textUsernamePrefix.setEndIconTintList(ColorStateList.valueOf(color));
                    setError(0, input_textUsernamePrefix, getString(R.string.err_enter_username_prefix));
                }
            }
        });
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
                            setError(1, input_textEmailId, "Entered email is valid.");
                        }
                        else {
                            input_textEmailId.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                            int color=Color.RED;
                            input_textEmailId.setEndIconTintList(ColorStateList.valueOf(color));
                            setError(0, input_textEmailId, getString(R.string.err_enter_email_id));
                        }
                    }
                if(s.length()==0){
                    input_textEmailId.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                    int color=Color.RED;
                    input_textEmailId.setEndIconTintList(ColorStateList.valueOf(color));
                    setError(0, input_textEmailId, getString(R.string.err_enter_email_id));
                }
            }
        });
        AutoComGstNo.setAllCaps(true);
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
                        setError(0, input_textGstNo, getString(R.string.scr_lbl_enter_gst_number));
                    }
                    else{
                        input_textGstNo.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_right_accept));
                        int color=Color.GREEN;
                        input_textGstNo.setEndIconTintList(ColorStateList.valueOf(color));
                        setError(1, input_textGstNo, "Entered Gst number is valid.");
                    }
                }
                if(s.length() == 0){
                    input_textGstNo.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                    int color=Color.TRANSPARENT;
                    input_textGstNo.setEndIconTintList(ColorStateList.valueOf(color));
                    input_textGstNo.setErrorEnabled(false);
                    //setError(0, input_textGstNo, getString(R.string.scr_lbl_enter_gst_number));
                }
            }
        });
        AutoComMobileNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() > 9) {
                    input_textMobileNo.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_right_accept));
                    int color=getResources().getColor(R.color.green);
                    input_textMobileNo.setEndIconTintList(ColorStateList.valueOf(color));
                    setError(1, input_textMobileNo, "Entered mobile number is valid.");
                }else{
                    input_textMobileNo.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_info_check));
                    int color=getResources().getColor(R.color.deep_yellow);
                    input_textMobileNo.setEndIconTintList(ColorStateList.valueOf(color));
                    setError(2, input_textMobileNo, "10 digit mobile number is needed.");
                }
                if(s.length() == 0){
                    input_textMobileNo.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                    int color=Color.RED;
                    input_textMobileNo.setEndIconTintList(ColorStateList.valueOf(color));
                    setError(0, input_textMobileNo, getString(R.string.scr_lbl_enter_mobile_number));
                }
            }
        });
        AutoComMobileNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if(AutoComMobileNo.getText().toString().length()<10){
                        input_textMobileNo.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                        int color=Color.RED;
                        input_textMobileNo.setEndIconTintList(ColorStateList.valueOf(color));
                        setError(0, input_textMobileNo, getString(R.string.scr_lbl_enter_mobile_number));
                    }
                }
            }
        });
        AutoComEmpStrength.addTextChangedListener(new TextWatcher() {

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
                    if(radioYear.isChecked()){
                       callSubscriptionChargesApi();
                    }
                    if(radioTrial.isChecked()) {
                        setDiscountChargesTrial();
                    }
                }
                else{
                   // AutoComEmpStrength.setText("1");
                    //setError(1, input_textEmpStrength, "You should have minimum 1 employee");
                }
            }
        });
        AutoComEmpStrength.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    if(AutoComEmpStrength.getText().toString().trim().equals("") || AutoComEmpStrength.getText().toString().trim().equals("0")) {
                        AutoComEmpStrength.setText("1");
                        AutoComEmpStrength.setSelection(AutoComEmpStrength.getText().length());
                    }
                }
            }
        });
        AutoComEmpStrength.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    if(AutoComEmpStrength.getText().toString().trim().equals("") || AutoComEmpStrength.getText().toString().trim().equals("0")) {
                        AutoComEmpStrength.setText("1");
                    }
                }
                return false;
            }
        });
        AutoComPincode.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() > 5) {
                    input_textPincode.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_right_accept));
                    int color=getResources().getColor(R.color.green);
                    input_textPincode.setEndIconTintList(ColorStateList.valueOf(color));
                    setError(1, input_textPincode, "Entered pincode is valid.");
                }else{
                    input_textPincode.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_info_check));
                    int color=getResources().getColor(R.color.deep_yellow);
                    input_textPincode.setEndIconTintList(ColorStateList.valueOf(color));
                    setError(2, input_textPincode, "6 digit pincode is needed.");
                }
                if(s.length() == 0){
                    input_textPincode.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                    int color=Color.RED;
                    input_textPincode.setEndIconTintList(ColorStateList.valueOf(color));
                    setError(0, input_textPincode, getString(R.string.scr_lbl_enter_pincode));
                }
            }
        });
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

        getOtpViewModel = ViewModelProviders.of(RegistrationActivity.this, mViewModelFactory).get(GetOtpViewModel.class);
        getOtpViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_get_sms_otp));

        resendOtpViewModel = ViewModelProviders.of(RegistrationActivity.this, mViewModelFactory).get(ResendOtpViewModel.class);
        resendOtpViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_resend_otp));

        verifyOtpViewModel = ViewModelProviders.of(RegistrationActivity.this, mViewModelFactory).get(VerifyOtpViewModel.class);
        verifyOtpViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.action_verify_otp));
    }

    public static boolean isValidGSTNo(String str)
    { String regex = "^[0-9]{2}[A-Za-z]{5}[0-9]{4}"
                + "[A-Za-z]{1}[1-9A-Za-z]{1}"
                + "Z[0-9A-Za-z]{1}$";
        Pattern p = Pattern.compile(regex);

        if (str == null)
        {
            return false;
        }
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /* Call Api For Login user and get user details */
    private void callRegisterUserApi() {
        if (NetworkCheck.isInternetAvailable(RegistrationActivity.this)) {
            showProgressLoader(getString(R.string.scr_message_please_wait));
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
            if(radioYear.isChecked()){plan="yearly";} if(radioTrial.isChecked()){plan="trial";}
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

    /* Call Api For Get Otp */
    private void callGetOtpApi() {
        if (NetworkCheck.isInternetAvailable(RegistrationActivity.this)) {
            RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_sms_otp);
            RequestBody mRequestBodyName = RequestBody.create(MediaType.parse("text/plain"), AutoComMobileNo.getText().toString().trim());
            getOtpViewModel.hitGetOtpApi(mRequestBodyAction, mRequestBodyName);

        } else {
            LogUtil.printToastMSG(RegistrationActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Get Otp */
    private void callResendOtpApi() {
        if (NetworkCheck.isInternetAvailable(RegistrationActivity.this)) {
            RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_resend_otp);
            RequestBody mRequestBodyName = RequestBody.create(MediaType.parse("text/plain"), AutoComMobileNo.getText().toString().trim());
            resendOtpViewModel.hitResendOtpApi(mRequestBodyAction, mRequestBodyName);

        } else {
            LogUtil.printToastMSG(RegistrationActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Get Otp */
    private void callVerifyOtpApi() {
        if (NetworkCheck.isInternetAvailable(RegistrationActivity.this)) {
            RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_verify_otp);
            RequestBody mRequestOTP = RequestBody.create(MediaType.parse("text/plain"), edtOpt.getText().toString().trim());
            RequestBody mRequestBodyName = RequestBody.create(MediaType.parse("text/plain"), AutoComMobileNo.getText().toString().trim());
            verifyOtpViewModel.hitVerifyOtpApi(mRequestBodyAction, mRequestOTP,mRequestBodyName);

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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDialog.dismiss();
                finish();
                launchScreen(mContext, LoginActivity.class);
            }
        }, 4300);
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
        radioTrial.setChecked(true);
        setErrorMSG();
        tvCounter.setText("00:00:00");
        tvCounter.setBase(SystemClock.elapsedRealtime());
        tvCounter.stop();
        setTimerMillis(mContext, 0);
        edtOpt.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if(s.length()>3) {
                   callVerifyOtpApi();
                }
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
        setDiscountChargesTrial();
    }

    // set error if input field is blank
    private void setErrorMSG() {
        /*setErrorMessage(inputEmail, editTextEmail, getString(R.string.val_msg_please_enter_email));
        setErrorMessage(inputPassword, editTextPassword, getString(R.string.val_msg_please_enter_password));*/
    }

    public static void setTimerMillis(Context context, long millis)
    {
        SharedPreferences sp = context.getSharedPreferences(Constants.BANK_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putLong(Constants.BANK_LIST, millis); spe.apply();
    }

    public static long getTimerMillis(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.BANK_LIST, Context.MODE_PRIVATE);
        return sp.getLong(Constants.BANK_LIST, 0);
    }

    private int getCurrentMiliSecondsOfChronometer(int val) {
        int stoppedMilliseconds = 0;
        String chronoText = tvCounter.getText().toString().trim();
        String array[] = chronoText.split(":");

        try {
            if (array.length == 2) {
                stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000 + Integer.parseInt(array[1]) * 1000;
            } else if (array.length == 3) {
                stoppedMilliseconds =
                        Integer.parseInt(array[0]) * 60 * 60 * 1000 + Integer.parseInt(array[1]) * 60 * 1000
                                + Integer.parseInt(array[2]) * 1000;
            }
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return Integer.parseInt(String.format("%02d", stoppedMilliseconds));
    }
    private void startChronometer(int val) {
        long stoppedMilliseconds = getTimerMillis(mContext);
        if(val==0){
            long preV = getTimerMillis(mContext);
            tvCounter.setBase(SystemClock.elapsedRealtime() - preV);
            tvCounter.stop();
        }
        else {
            if (SystemClock.elapsedRealtime() > stoppedMilliseconds) {
                tvCounter.setBase(SystemClock.elapsedRealtime() - stoppedMilliseconds);
            } else {
                tvCounter.setBase(stoppedMilliseconds - SystemClock.elapsedRealtime());
            }
        }
        tvCounter.start();
    }

    public void setTimerCounter(int val){
        startChronometer(val);
        tvCounter.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer cArg) {
                long milliseconds = getCurrentMiliSecondsOfChronometer(val);//getTimerMillis(mContext);
                int seconds = (int) (milliseconds / 1000) % 60 ;
                int minutes = (int) ((milliseconds / (1000*60)) % 60);
                int hours   = (int) ((milliseconds / (1000*60*60)) % 24);

                setTimerMillis(mContext, milliseconds);
                tvCounter.setText(String.format("%02d",hours)+":"+String.format("%02d",minutes)+":"+String.format("%02d",seconds));
                if(tvCounter.getText().toString().equals("00:02:00")){
                    tvResendOtp.setVisibility(View.VISIBLE);
                    //LogUtil.printToastMSG(mContext,"test");
                }
                if(tvCounter.getText().toString().equals("00:05:00")){
                    //tvCounter.setText("00:00:00");
                    //tvCounter.setBase(SystemClock.elapsedRealtime());
                    tvCounter.stop();
                    //setTimerMillis(mContext, 1);
                    tvResendOtp.setVisibility(View.GONE);
                    LogUtil.printToastMSG(mContext,"Something went wrong, Please try again later.");
                }
            }
        });

    }

    //perform click actions
    @OnClick({R.id.tvResendOtp,R.id.btnGetOtp,R.id.tvRegClick,R.id.imgInfo, R.id.btnRegister,R.id.applyButton,R.id.removeButton,R.id.radioYear,R.id.radioTrial})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnGetOtp:
                if (isDetailsValid()) {
                    tvMissing.setVisibility(View.GONE);
                    callGetOtpApi();
                    btnGetOtp.setVisibility(View.GONE);
                    edtOpt.setVisibility(View.VISIBLE);
                    tvCounter.setVisibility(View.VISIBLE);
                    setTimerCounter(1);
                } else{
                    tvMissing.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tvResendOtp:
                callResendOtpApi();
                break;
            case R.id.tvRegClick:
                finish();
                launchScreen(mContext, LoginActivity.class);
                break;
            case R.id.radioYear:
                break;
            case R.id.radioTrial:

                break;
            case R.id.imgInfo:
                showSubscriptionDialog();
                break;
            case R.id.applyButton:
                if (!TextUtils.isEmpty(AutoComCoupon.getText().toString().trim())){
                    callCheckCouponApi();
                }
                else{
                    AutoComCoupon.setError("Enter Coupon code!");
                    LogUtil.printSnackBar(mContext,Color.YELLOW,findViewById(android.R.id.content),getString(R.string.scr_lbl_coupn_msg));
                }
                break;
            case R.id.removeButton:
                input_textCoupon.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                int color=Color.TRANSPARENT;
                input_textCoupon.setEndIconTintList(ColorStateList.valueOf(color));
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
                    tvMissing.setVisibility(View.GONE);
                    if(statusOtp) {
                        callRegisterUserApi();
                    }
                    else{
                        LogUtil.printToastMSG(mContext,"Please verify your mobile number with OTP.");
                    }
                } else{
                    tvMissing.setVisibility(View.VISIBLE);
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
        input_textName.setErrorEnabled(false);input_textAdminName.setErrorEnabled(false);
        input_textAddress.setErrorEnabled(false);input_textPincode.setErrorEnabled(false);
        input_textUsernamePrefix.setErrorEnabled(false);input_textEmailId.setErrorEnabled(false);
        input_textMobileNo.setErrorEnabled(false);input_textEmpStrength.setErrorEnabled(false);
        if (TextUtils.isEmpty(AutoComTextViewName.getText().toString().trim())) {
            setError(0, input_textName, getString(R.string.err_enter_company_name));
            return false;
        } else if (TextUtils.isEmpty(AutoComAddress.getText().toString().trim())) {
            setError(0, input_textAddress, getString(R.string.err_enter_address));
            return false;
        } else if (TextUtils.isEmpty(AutoComPincode.getText().toString().trim()) ||
                AutoComPincode.getText().toString().trim().length()<6) {
            setError(0, input_textPincode, getString(R.string.err_enter_pincode));
            return false;
        } else if (TextUtils.isEmpty(AutoComUsernamePrefix.getText().toString().trim())|| !statusPrefix ||
                !(AutoComUsernamePrefix.getText().toString().trim().length()>2 && AutoComUsernamePrefix.getText().toString().trim().length()<=5)) {
            setError(0, input_textUsernamePrefix, getString(R.string.err_enter_username_prefix));
            return false;
        } else if (TextUtils.isEmpty(AutoComEmailId.getText().toString().trim()) || !Patterns.EMAIL_ADDRESS.matcher(AutoComEmailId.getText().toString().trim()).matches()) {
            setError(0, input_textEmailId, getString(R.string.err_enter_email_id));
            return false;
        } else if (TextUtils.isEmpty(AutoComAdminName.getText().toString().trim())) {
            setError(0, input_textAdminName, getString(R.string.err_enter_admin_name));
            return false;
        } else if (TextUtils.isEmpty(AutoComMobileNo.getText().toString().trim()) || AutoComMobileNo.getText().toString().trim().length()<10) {
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
                //
                break;

            case SUCCESS:
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    if (tag.equalsIgnoreCase(DynamicAPIPath.action_register)) {
                        RegisterResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), RegisterResponse.class);
                        dismissLoader();
                        if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                            showThanksForRegisterDialog();
                        } else {
                            LogUtil.printToastMSG(RegistrationActivity.this, responseModel.getResult().getMessage());
                        }
                    }
                    if (tag.equalsIgnoreCase(DynamicAPIPath.action_check_prefix)) {
                        CheckPrefixResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), CheckPrefixResponse.class);
                        if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                            tvEx.setVisibility(View.GONE);
                            statusPrefix = true;
                            input_textUsernamePrefix.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_right_accept));
                            int color=getResources().getColor(R.color.green);
                            input_textUsernamePrefix.setEndIconTintList(ColorStateList.valueOf(color));
                            setError(1, input_textUsernamePrefix, responseModel.getResult().getMessage());
                        } else {
                            statusPrefix = false;
                            tvEx.setVisibility(View.GONE);
                            input_textUsernamePrefix.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                            int color=Color.RED;
                            input_textUsernamePrefix.setEndIconTintList(ColorStateList.valueOf(color));
                            setError(0, input_textUsernamePrefix, responseModel.getResult().getMessage());
                            //LogUtil.printToastMSG(RegistrationActivity.this, responseModel.getResult().getMessage());
                        }
                    }
                    if (tag.equalsIgnoreCase(DynamicAPIPath.action_get_subs_price)) {
                        SubscriptionResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), SubscriptionResponse.class);
                        if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                            if(responseModel.getResult().getMessage().equals("No record found.")){
                                subCharges ="0";
                            }else {
                                subCharges = responseModel.getResult().getLeave().get(0).getPricePerPerson();
                            }
                            tvSubDesc.setText("Subscription charges will be\n ₹" + subCharges + " / User / Year + GST");
                            setSubCharges();
                            //LogUtil.printToastMSG(RegistrationActivity.this, responseModel.getResult().getMessage());
                        }
                    }
                     if (tag.equalsIgnoreCase(DynamicAPIPath.action_get_sms_otp) || tag.equalsIgnoreCase(DynamicAPIPath.action_resend_otp)
                     ) {
                        VerifyOtpResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), VerifyOtpResponse.class);
                        if (responseModel != null && responseModel.getResult().getStatus().equals("Success")) {
                            LogUtil.printToastMSG(RegistrationActivity.this, responseModel.getResult().getMessage());
                        }
                        else { LogUtil.printToastMSG(RegistrationActivity.this, responseModel.getResult().getMessage());
                        }
                    }
                      if (tag.equalsIgnoreCase(DynamicAPIPath.action_verify_otp)) {
                        VerifyOtpResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), VerifyOtpResponse.class);
                        if (responseModel != null && responseModel.getResult().getStatus().equals("Success")) {
                            statusOtp = true;
                            btnRegister.setVisibility(View.VISIBLE);
                            tvCounter.setText("00:00:00");
                            tvCounter.setBase(SystemClock.elapsedRealtime());
                            tvCounter.stop();
                            setTimerMillis(mContext, 0);
                            LogUtil.printToastMSG(RegistrationActivity.this, responseModel.getResult().getMessage());
                        }
                        else {
                            statusOtp = false;
                            LogUtil.printToastMSG(RegistrationActivity.this, responseModel.getResult().getMessage());
                        }
                    }
                     if (tag.equalsIgnoreCase(DynamicAPIPath.action_check_coupon_validity)) {
                        ApplyCouponResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ApplyCouponResponse.class);
                        if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                if(responseModel.getResult().getMessage().equals("No record found.")){
                                    input_textCoupon.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                                    int color=Color.TRANSPARENT;
                                    input_textCoupon.setEndIconTintList(ColorStateList.valueOf(color));
                                    AutoComCoupon.setError("Coupon code is not valid!");
                                    LogUtil.printSnackBar(mContext,Color.YELLOW,findViewById(android.R.id.content),getString(R.string.scr_lbl_coupn_msg));
                                }
                                else{
                                    input_textCoupon.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_right_accept));
                                    int color=getResources().getColor(R.color.green);
                                    input_textCoupon.setEndIconTintList(ColorStateList.valueOf(color));
                                    discountAmount = responseModel.getResult().getList().getDiscountAmt();
                                    couponCode = responseModel.getResult().getList().getCode();
                                    LogUtil.printSnackBar(mContext, getResources().getColor(R.color.green),findViewById(android.R.id.content),"Congratulations! You have got a Rs"+discountAmount+" Discount on Coupon code -"+
                                            couponCode+".");
                                    //AutoComCoupon.setError(getString(R.string.scr_lbl_coupon_applid));
                                    //setError(1, input_textCoupon, getString(R.string.scr_lbl_coupon_applid));
                                    removeButton.setVisibility(View.VISIBLE);
                                    applyButton.setVisibility(View.GONE);
                                    if(radioYear.isChecked()){
                                        setSubCharges();
                                    }
                                    if(radioTrial.isChecked()) {
                                        setDiscountChargesTrial();
                                    }
                                }

                                // LogUtil.printToastMSG(RegistrationActivity.this, responseModel.getResult().getMessage());
                            }
                            else{ //AutoComCoupon.setError("Coupon is not valid.");
                                AutoComCoupon.setError("Coupon code is not valid!");
                                input_textCoupon.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                                int color=Color.TRANSPARENT;
                                input_textCoupon.setEndIconTintList(ColorStateList.valueOf(color));
                                LogUtil.printSnackBar(mContext,Color.YELLOW,findViewById(android.R.id.content),getString(R.string.scr_lbl_coupn_msg));

                            }
                        }
                        else{ //AutoComCoupon.setError("Coupon is not valid.");
                            AutoComCoupon.setError("Coupon code is not valid!");
                            input_textCoupon.setEndIconDrawable(getResources().getDrawable(R.drawable.ic_close_reject));
                            int color=Color.TRANSPARENT;
                            input_textCoupon.setEndIconTintList(ColorStateList.valueOf(color));
                            LogUtil.printSnackBar(mContext,Color.YELLOW,findViewById(android.R.id.content),getString(R.string.scr_lbl_coupn_msg));
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
        int strength = AutoComEmpStrength.getText().toString().equals("")
                ||AutoComEmpStrength.getText().toString().equals("0")?1:
                Integer.parseInt(AutoComEmpStrength.getText().toString());
        Double price = Double.parseDouble(subCharges);
        subChargeApi = String.valueOf(strength*price);
        gstAmount = String.valueOf((strength*price)*gstPer/100);
        Double big1 = strength*price+((strength*price)*gstPer/100);
        totalAmountCharges = Double.toString(big1-Integer.parseInt(discountAmount));
        tvSubRs.setText(big1-Integer.parseInt(discountAmount)+"");
    }

}