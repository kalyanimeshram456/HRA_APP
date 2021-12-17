package com.ominfo.crm_solution.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.basecontrol.BaseApplication;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.network.DynamicAPIPath;
import com.ominfo.crm_solution.network.NetworkAPIServices;
import com.ominfo.crm_solution.network.NetworkCheck;
import com.ominfo.crm_solution.network.NetworkURLs;
import com.ominfo.crm_solution.network.RetroNetworkModule;
import com.ominfo.crm_solution.network.ViewModelFactory;
import com.ominfo.crm_solution.ui.dashboard.adapter.CallManagerAdapter;
import com.ominfo.crm_solution.ui.dashboard.adapter.CrmAdapter;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.lr_number.model.UploadVehicleRecordRequest;
import com.ominfo.crm_solution.ui.lr_number.model.UploadVehicleRecordRespoonse;
import com.ominfo.crm_solution.ui.lr_number.model.VehicleDetailsResultTable;
import com.ominfo.crm_solution.ui.sales_credit.SalesCreditActivity;
import com.ominfo.crm_solution.ui.sales_credit.fragment.SalesCreditFragment;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class DashbooardActivity extends BaseActivity {

   /* @BindView(R.id.cardKataChithi)
    CardView mCardKataChithi;*/
    @BindView(R.id.imgSearchLr)
    AppCompatImageView imgSearchLr;
    /*@BindView(R.id.tvSearchView)
    AppCompatEditText etSearch;*/
    @BindView(R.id.rvDashboardList)
    RecyclerView rvDashboardList;
    Context mContext;
    final Calendar myCalendar = Calendar.getInstance();
    private AppDatabase mDb;
    CrmAdapter mCrmAdapter;
    List<DashModel> dashboardList = new ArrayList<>();
    @Inject
    ViewModelFactory mViewModelFactory;
    CallManagerAdapter mCallManagerAdapter;
    public static boolean activityVisible; // Variable that will check the
    final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    IntentFilter intentFilter;
    MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbooard);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mContext = this;
        getDeps().inject(this);
        ButterKnife.bind(this);
        init();
        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        receiver = new MyReceiver();
        //get login status
            if(checkForInternet()){
                //LogUtil.printToastMSG(this,"yo Connected");
                //uploadVehicle(mContext);
            }else{
                //LogUtil.printToastMSG(this,"yo Not Connected");
            }
    }

    private void init(){
        mDb =BaseApplication.getInstance(mContext).getAppDatabase();
        requestPermission();
        setToolbar();
        dashboardList.add(new DashModel("Sales Credit","₹13245647",getDrawable(R.drawable.ic_om_sales_credit)));
        dashboardList.add(new DashModel("Receipt","₹13245647",getDrawable(R.drawable.ic_om_receipt)));
        dashboardList.add(new DashModel("Top Customer","₹13245647",getDrawable(R.drawable.ic_om_rating)));
        dashboardList.add(new DashModel("Total Quotation Amount","₹13245647",getDrawable(R.drawable.ic_om_total_quotation)));
        dashboardList.add(new DashModel("Dispatch Pending","₹13245647",getDrawable(R.drawable.ic_om_dispatch_pending)));
        dashboardList.add(new DashModel("Enquiry Report","₹13245647",getDrawable(R.drawable.ic_om_enquiry_report)));
        dashboardList.add(new DashModel("Visit Report","₹13245647",getDrawable(R.drawable.ic_om_visit_report)));
        dashboardList.add(new DashModel("Products","₹13245647",getDrawable(R.drawable.ic_om_product)));

        setAdapterForDashboardList();
    }

    private void setToolbar(){
        initToolbar(0,mContext,R.id.imgBack,R.id.imgReport,R.id.imgNotify,R.id.imgLogout,R.id.imgCall);
    }


    private void setAdapterForDashboardList() {
        if (dashboardList.size() > 0) {
            rvDashboardList.setVisibility(View.VISIBLE);
        } else {
            rvDashboardList.setVisibility(View.GONE);
        }
        mCrmAdapter = new CrmAdapter(mContext, dashboardList, new CrmAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(DashModel mDataTicket) {
                if (mDataTicket.getTitle().equals("Sales Credit")) {
                 //launchScreen(mContext, SalesCreditActivity.class);
                    FragmentTransaction fr = getSupportFragmentManager().beginTransaction();
                    fr.replace(R.id.framecontainer,new SalesCreditFragment());
                    fr.commit();
                   /* Fragment fragment = new SalesCreditFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.framecontainer, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();*/
                }
            }
        });
        rvDashboardList.setHasFixedSize(true);
        rvDashboardList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvDashboardList.setAdapter(mCrmAdapter);
    }

    //set date picker view
    private void openDataPicker(AppCompatTextView datePickerField,int mFrom) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat="";
                if(mFrom==0) {
                     myFormat = "dd MMM yyyy"; //In which you need put here
                }
                else{
                     myFormat = "dd/MM/yyyy"; //In which you need put here
                }
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                datePickerField.setText(sdf.format(myCalendar.getTime()));
            }

        };

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    //show truck details popup
    public void showTruckDetailsDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_truck_details);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.detailsButton);
        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);
        RelativeLayout relRC = mDialog.findViewById(R.id.relRC);
        RelativeLayout relPUC = mDialog.findViewById(R.id.relPUC);
        RelativeLayout relIss = mDialog.findViewById(R.id.relIss);

        relRC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showFullImageDialog();
            }
        });
        relPUC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showFullImageDialog();
            }
        });
        relIss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showFullImageDialog();
            }
        });
        okayButton.setOnClickListener(new View.OnClickListener() {
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
        });
        mDialog.show();
    }

    //show truck details popup
    public void showFullImageDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_doc_full_view);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.detailsButton);

        okayButton.setOnClickListener(new View.OnClickListener() {
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
        });
        mDialog.show();
    }


    //request camera and storage permission
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mContext.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                   ) {

                requestPermissions(new String[]
                                { Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                },
                        1000);

            } else {
                //createFolder();
            }
        } else {
            //createFolder();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            String actionOfIntent = intent.getAction();
            boolean isConnected = NetworkCheck.isInternetAvailable(context);
            if(actionOfIntent.equals(CONNECTIVITY_ACTION)){
                //get login status
                    if(isConnected){
                        //LogUtil.printToastMSG(context,"yo Connected");
                        //uploadVehicle(context);
                    }else{
                        //LogUtil.printToastMSG(context,"yo Not Connected");
                    }
            }
        }
    }

/*
    public void uploadVehicle(Context mContext){
        List<VehicleDetailsResultTable> vehicleList = mDb.getDbDAO().getVehicleList();
        if(vehicleList!=null && vehicleList.size()>0) {
            for(int i=0;i<vehicleList.size();i++) {
                try {

                   */
/* List<UploadVehicleImage> base64List = new ArrayList<>();
                    for(int k=0;k<vehicleList.get(i).getLrImages().size();k++){
                        String mBase64 = AppUtils.getBase64images(vehicleList.get(i).getLrImages().get(k).getImage());
                        base64List.add(new UploadVehicleImage(vehicleList.get(i).getLrImages().get(k).getLr(),mBase64));
                    }*//*

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
                                        LogUtil.printToastMSG(mContext, "Pending Lr uploaded successfully!");
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
*/
}