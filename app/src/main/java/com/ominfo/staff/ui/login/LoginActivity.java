package com.ominfo.staff.ui.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ominfo.staff.R;
import com.ominfo.staff.basecontrol.BaseActivity;
import com.ominfo.staff.basecontrol.BaseApplication;
import com.ominfo.staff.database.AppDatabase;
import com.ominfo.staff.interfaces.SharedPrefKey;
import com.ominfo.staff.network.ApiResponse;
import com.ominfo.staff.network.DynamicAPIPath;
import com.ominfo.staff.network.NetworkAPIServices;
import com.ominfo.staff.network.NetworkCheck;
import com.ominfo.staff.network.NetworkURLs;
import com.ominfo.staff.network.RetroNetworkModule;
import com.ominfo.staff.network.ViewModelFactory;
import com.ominfo.staff.ui.dashboard.DashbooardActivity;
import com.ominfo.staff.ui.dashboard.adapter.CallManagerAdapter;
import com.ominfo.staff.ui.login.model.LoginRequest;
import com.ominfo.staff.ui.login.model.LoginResponse;
import com.ominfo.staff.ui.login.model.LoginViewModel;
import com.ominfo.staff.ui.lr_number.model.UploadVehicleRecordRequest;
import com.ominfo.staff.ui.lr_number.model.UploadVehicleRecordRespoonse;
import com.ominfo.staff.ui.lr_number.model.VehicleDetailsResultTable;
import com.ominfo.staff.util.LogUtil;
import com.ominfo.staff.util.SharedPref;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

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
    CallManagerAdapter mCallManagerAdapter;
    public static boolean activityVisible; // Variable that will check the
    final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    IntentFilter intentFilter;
    MyReceiver receiver;

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
        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        receiver = new MyReceiver();
        //get login status
        Boolean iSLoggedIn = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.IS_LOGGED_IN, false);
        if (!iSLoggedIn){
            if(checkForInternet()){
                try {
                    uploadVehicle(mContext);
                }catch (Exception e){}
            }else{
                //LogUtil.printToastMSG(this,"yo Not Connected");
            }
        }
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String actionOfIntent = intent.getAction();
            boolean isConnected = NetworkCheck.isInternetAvailable(context);
            if(actionOfIntent.equals(CONNECTIVITY_ACTION)){
                //get login status
                Boolean iSLoggedIn = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.IS_LOGGED_IN, false);
                if (!iSLoggedIn){
                    if(isConnected){
                        try {
                            uploadVehicle(context);
                        }catch (Exception e){}
                    }else{
                        //LogUtil.printToastMSG(context,"yo Not Connected");
                    }
                }
            }
        }
    }

    public void uploadVehicle(Context mContext){
        List<VehicleDetailsResultTable> vehicleList = mDb.getDbDAO().getVehicleList();
        if(vehicleList!=null && vehicleList.size()>0) {
            for(int i=0;i<vehicleList.size();i++) {
                try {

                   /* List<UploadVehicleImage> base64List = new ArrayList<>();
                    for(int k=0;k<vehicleList.get(i).getLrImages().size();k++){
                        String mBase64 = AppUtils.getBase64images(vehicleList.get(i).getLrImages().get(k).getImage());
                        base64List.add(new UploadVehicleImage(vehicleList.get(i).getLrImages().get(k).getLr(),mBase64));
                    }*/
                    UploadVehicleRecordRequest mLoginRequest = new UploadVehicleRecordRequest();
                    mLoginRequest.setUserkey(vehicleList.get(i).getUserkey());
                    mLoginRequest.setVehicleID(vehicleList.get(i).getVehicleID());
                    mLoginRequest.setTransactionDate(vehicleList.get(i).getTransactionDate());
                    mLoginRequest.setUserID(vehicleList.get(i).getUserID());
                    mLoginRequest.setGeneratedId(vehicleList.get(i).getGeneratedId());
                    mLoginRequest.setTransactionID(vehicleList.get(i).getTransactionID());
                    mLoginRequest.setPhotoXml(vehicleList.get(i).getLrImages());
                    mLoginRequest.setNoOfLR(vehicleList.get(i).getNoOfLR());
                    mLoginRequest.setBranchID(vehicleList.get(i).getBranchID());
                    mLoginRequest.setPlantID(vehicleList.get(i).getPlantID());///AutoComTextViewPlant.getEditableText().toString().trim());
                    Gson gson = new Gson();
                    String bodyInStringFormat = gson.toJson(mLoginRequest);
                    RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), "saveVehWiseLRTransaction");
                    RequestBody mRequestBodyTypeImage = RequestBody.create(MediaType.parse("text/plain"), bodyInStringFormat);
                    LogUtil.printLog("request save", bodyInStringFormat);

                    NetworkAPIServices mNetworkAPIServices = RetroNetworkModule.getInstance().getAPI();
                    Call<UploadVehicleRecordRespoonse> call = mNetworkAPIServices.uploadVehicleRecord(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL,
                            DynamicAPIPath.POST_UPLOAD_VEHICLE), mRequestBodyType, mRequestBodyTypeImage);

                    int finalI = i;
                    call.enqueue(new Callback<UploadVehicleRecordRespoonse>() {
                        @Override
                        public void onResponse(@NonNull Call<UploadVehicleRecordRespoonse> call, @NonNull retrofit2.Response<UploadVehicleRecordRespoonse> response) {
                            try {
                                //dismissLoader();
                                if (response.body() != null) {
                                    UploadVehicleRecordRespoonse userInfo = response.body();
                                    if (userInfo.getStatus().equals("1")) {
                                        //LogUtil.printToastMSG(mContext, "Pending Lr uploaded!");
                                        mDb.getDbDAO().deleteVehicleDetailsbyID(userInfo.getResult().getGeneratedId());
                                    } else {
                                        //editText.setError(userInfo.getMessage());
                                        //LogUtil.printToastMSG(mContext, userInfo.getMessage());
                                    }
                                }
                            } catch (Exception e) {
                                //editText.setError("LR no already " + "exists");
                                //LogUtil.printToastMSG(mContext, "LR no already " + "exists");
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<UploadVehicleRecordRespoonse> call, @NonNull Throwable t) {
                            //dismissLoader();
                            //Util.showToastMessage(LoginActivity.this, getString(R.string.msg_try_again_later));
                        }
                    });
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

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
        } else if(!getValidUser()){
            setError(inputEmail, "Please Enter Valid Username.");
            return false;
        }
        return true;
    }

    private boolean getValidUser(){
        String currentString = editTextEmail.getText().toString().trim();
        if(currentString.substring(0, 2).toLowerCase().equals("bo")){
            return true;
        }
        else {
            return false;
        }
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