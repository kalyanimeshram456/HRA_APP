package com.ominfo.app.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ominfo.app.R;
import com.ominfo.app.basecontrol.BaseActivity;
import com.ominfo.app.basecontrol.BaseApplication;
import com.ominfo.app.database.AppDatabase;
import com.ominfo.app.interfaces.SharedPrefKey;
import com.ominfo.app.network.ApiResponse;
import com.ominfo.app.network.NetworkCheck;
import com.ominfo.app.network.ViewModelFactory;
import com.ominfo.app.ui.dashboard.DashbooardActivity;
import com.ominfo.app.ui.login.model.CommonLoginResponse;
import com.ominfo.app.ui.login.model.LoginRequest;
import com.ominfo.app.ui.login.model.LoginResponse;
import com.ominfo.app.ui.login.model.LoginViewModel;
import com.ominfo.app.util.LogUtil;
import com.ominfo.app.util.SharedPref;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.input_password)
    TextInputLayout inputPassword;

    @BindView(R.id.input_email)
    TextInputLayout inputEmail;

    @BindView(R.id.editTextPassword)
    TextInputEditText editTextPassword;

    @BindView(R.id.editTextEmail)
    TextInputEditText editTextEmail;

    Context mContext;
    @Inject
    ViewModelFactory mViewModelFactory;
    private LoginViewModel mLoginViewModel;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
         mLoginViewModel = ViewModelProviders.of(LoginActivity.this, mViewModelFactory).get(LoginViewModel.class);
         mLoginViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, "Login"));
    }

    private void init(){
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        setErrorMSG();
        //set login cred
        //editTextEmail.setText("DR0001");
        //editTextPassword.setText("123");
    }

    // set error if input field is blank
    private void setErrorMSG() {
        setErrorMessage(inputEmail, editTextEmail, getString(R.string.val_msg_please_enter_email));
        setErrorMessage(inputPassword, editTextPassword, getString(R.string.val_msg_please_enter_password));
    }

    //perform click actions
    @OnClick({R.id.loginButton})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.loginButton:
                if(isDetailsValid()) {
                    callLoginUserApi();
                }
                break;

        }
    }

    /* Call Api For Login user and get user details */
    private void callLoginUserApi() {
        if (NetworkCheck.isInternetAvailable(LoginActivity.this)) {
            LoginRequest mLoginRequest = new LoginRequest();
            mLoginRequest.setUsername(editTextEmail.getEditableText().toString().trim()); //6b07b768-926c-49b6-ac1c-89a9d03d4c3b
            mLoginRequest.setPassword(editTextPassword.getEditableText().toString().trim());
            Gson gson = new Gson();
            String bodyInStringFormat = gson.toJson(mLoginRequest);
            mLoginViewModel.hitLoginApi(bodyInStringFormat);
        } else {
            LogUtil.printToastMSG(LoginActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /*check validations on field*/
    private boolean isDetailsValid() {
        if (TextUtils.isEmpty(editTextEmail.getText().toString().trim())) {
            setError(inputEmail, getString(R.string.val_msg_please_enter_email));
            return false;
        } else if (TextUtils.isEmpty(editTextPassword.getText().toString().trim())) {
            setError(inputPassword, getString(R.string.val_msg_please_enter_password));
            return false;
        }/* else if (editTextPassword.getEditableText().toString().trim().length() < 6) { //6
            setError(inputPassword, getString(R.string.val_msg_minimum_password));
            return false;
        }*/
        return true;
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
                    if (tag.equalsIgnoreCase("Login")) {
                        LoginResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), LoginResponse.class);
                        if (responseModel != null && responseModel.getStatus().equals("1")) {
                            LogUtil.printToastMSG(LoginActivity.this, responseModel.getMessage());
                            SharedPref.getInstance(this).write(SharedPrefKey.IS_LOGGED_IN, true);
                            try{
                                mDb.getDbDAO().insertLoginData(responseModel.getResult());
                            }
                            catch (Exception e){
                                e.printStackTrace();
                            }
                            launchScreen(mContext, DashbooardActivity.class);
                            //mDb.getDbDAO().insertLogin(responseModel);
                        } else {
                            LogUtil.printToastMSG(LoginActivity.this, responseModel.getMessage());
                        }
                    }
                }
                break;
            case ERROR:
                dismissLoader();
                LogUtil.printToastMSG(LoginActivity.this, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }


}