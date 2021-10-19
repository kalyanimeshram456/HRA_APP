package com.ominfo.staff.ui.lr_number;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ominfo.staff.R;
import com.ominfo.staff.basecontrol.BaseActivity;
import com.ominfo.staff.basecontrol.BaseApplication;
import com.ominfo.staff.database.AppDatabase;
import com.ominfo.staff.interfaces.Constants;
import com.ominfo.staff.network.ApiResponse;
import com.ominfo.staff.network.DynamicAPIPath;
import com.ominfo.staff.network.NetworkCheck;
import com.ominfo.staff.network.ViewModelFactory;
import com.ominfo.staff.ui.driver_hisab.model.DriverHisabModel;
import com.ominfo.staff.ui.login.model.LoginResultTable;
import com.ominfo.staff.ui.lr_number.adapter.LrDetailsAdapter;
import com.ominfo.staff.ui.lr_number.model.VehicleDetailsLrImage;
import com.ominfo.staff.ui.lr_number.model.VehicleDetailsRequest;
import com.ominfo.staff.ui.lr_number.model.VehicleDetailsResponse;
import com.ominfo.staff.ui.lr_number.model.VehicleDetailsResultTable;
import com.ominfo.staff.ui.lr_number.model.VehicleDetailsViewModel;
import com.ominfo.staff.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LrDetailsActivity extends BaseActivity {

    Context mContext;
    @BindView(R.id.rvPuranaHisab)
    RecyclerView rvPuranaHisab;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.etDate)
    AppCompatEditText etDate;
    @BindView(R.id.AutoComTextViewVehNo)
    AppCompatAutoCompleteTextView etVehicleNo;
    @BindView(R.id.AutoComTextViewPlant)
    AppCompatAutoCompleteTextView etPlant;
    @BindView(R.id.etNoOfLr)
    AppCompatEditText etNoOfLr;

    @Inject
    ViewModelFactory mViewModelFactory;
    private VehicleDetailsViewModel mVehicleDetailsViewModel;
    LrDetailsAdapter mLrDetailsAdapter;
    List<VehicleDetailsLrImage> vehicleImageModelList = new ArrayList<>();
    private String transactionId = "";
    private AppDatabase mDb;
    private String mUserKey = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lr_details);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mContext = this;
        getDeps().inject(this);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        //set toolbar
        injectAPI();
        setToolbar();
        getIntentDate();
        setAdapterForVehicleList();
    }

    private void setToolbar(){
        //set toolbar title
        toolbarTitle.setText(R.string.scr_lbl_lr_details);
        initToolbar(1,mContext,R.id.imgBack,R.id.imgReport,R.id.imgNotify,0,R.id.imgCall);
    }

    private void injectAPI() {
        mVehicleDetailsViewModel = ViewModelProviders.of(LrDetailsActivity.this, mViewModelFactory).get(VehicleDetailsViewModel.class);
        mVehicleDetailsViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_VEHICLE_DETAILS));
  }

    private void getIntentDate(){
        Intent intent = getIntent();
        if (intent != null) {
            transactionId = intent.getStringExtra(Constants.TRANSACTION_ID);
        }

        //get dataset data
        LoginResultTable loginResultTable = mDb.getDbDAO().getLoginData();
        if (loginResultTable != null) {
            mUserKey = loginResultTable.getUserKey();
        }
        callVehicleDetailsApi();
    }

    /* Call Api For Vehicle No */
    private void callVehicleDetailsApi() {
        if (NetworkCheck.isInternetAvailable(LrDetailsActivity.this)) {
            VehicleDetailsRequest mRequest = new VehicleDetailsRequest();
            mRequest.setUserkey(mUserKey); //6b07b768-926c-49b6-ac1c-89a9d03d4c3b
            mRequest.setTransactionID(transactionId);
            Gson gson = new Gson();
            String bodyInStringFormat = gson.toJson(mRequest);
            mVehicleDetailsViewModel.hitVehicleDetailsApi(bodyInStringFormat);
        } else {
            LogUtil.printToastMSG(LrDetailsActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    private void setVehicleDetails(VehicleDetailsResultTable result){
        etDate.setText(result.getTransactionDate());//AppUtils.getCurrentDateTime_());
        etVehicleNo.setText(result.getVehicleNo());
        etNoOfLr.setText(result.getNoOfLR());
        etPlant.setText(result.getPlantID());
        //vehicleImageModelList = result.getLrImages();
        setAdapterForVehicleList();
    }

    private void setAdapterForVehicleList() {
        if (vehicleImageModelList.size() > 0) {
            mLrDetailsAdapter = new LrDetailsAdapter(mContext, vehicleImageModelList, new LrDetailsAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(DriverHisabModel mDataTicket) {

                }
            });
            rvPuranaHisab.setHasFixedSize(true);
            rvPuranaHisab.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            rvPuranaHisab.setAdapter(mLrDetailsAdapter);
            rvPuranaHisab.setVisibility(View.VISIBLE);
        } else {
            rvPuranaHisab.setVisibility(View.GONE);
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
                    if (tag.equalsIgnoreCase(DynamicAPIPath.POST_VEHICLE_DETAILS)) {
                        VehicleDetailsResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), VehicleDetailsResponse.class);
                        if (responseModel != null && responseModel.getStatus().equals("1")) {
                            LogUtil.printToastMSG(LrDetailsActivity.this, responseModel.getMessage());
                            setVehicleDetails(responseModel.getResult());
                        } else {
                            LogUtil.printToastMSG(LrDetailsActivity.this, responseModel.getMessage());
                        }
                    }
                }
                break;
            case ERROR:
                dismissLoader();
                LogUtil.printToastMSG(LrDetailsActivity.this, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }


    //perform click actions
    @OnClick({/*R.id.imgBack,R.id.imgNotify*/})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {


        }
    }
}