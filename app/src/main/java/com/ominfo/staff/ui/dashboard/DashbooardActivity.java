package com.ominfo.staff.ui.dashboard;

import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;
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
import com.ominfo.staff.R;
import com.ominfo.staff.basecontrol.BaseActivity;
import com.ominfo.staff.basecontrol.BaseApplication;
import com.ominfo.staff.database.AppDatabase;
import com.ominfo.staff.interfaces.Constants;
import com.ominfo.staff.interfaces.SharedPrefKey;
import com.ominfo.staff.network.DynamicAPIPath;
import com.ominfo.staff.network.NetworkAPIServices;
import com.ominfo.staff.network.NetworkCheck;
import com.ominfo.staff.network.NetworkURLs;
import com.ominfo.staff.network.RetroNetworkModule;
import com.ominfo.staff.network.ViewModelFactory;
import com.ominfo.staff.ui.dashboard.adapter.CallManagerAdapter;
import com.ominfo.staff.ui.login.LoginActivity;
import com.ominfo.staff.ui.login.model.LoginResultTable;
import com.ominfo.staff.ui.lr_number.AddLrActivity;
import com.ominfo.staff.ui.lr_number.SelectedLrNumberActivity;
import com.ominfo.staff.ui.dashboard.adapter.TripAwadhiAdapter;
import com.ominfo.staff.ui.driver_hisab.model.DriverHisabModel;
import com.ominfo.staff.ui.lr_number.model.PlantViewModel;
import com.ominfo.staff.ui.lr_number.model.SearchViewModel;
import com.ominfo.staff.ui.lr_number.model.UploadVehicleImage;
import com.ominfo.staff.ui.lr_number.model.UploadVehicleRecordRequest;
import com.ominfo.staff.ui.lr_number.model.UploadVehicleRecordRespoonse;
import com.ominfo.staff.ui.lr_number.model.VehicalNoViewModel;
import com.ominfo.staff.ui.lr_number.model.VehicleDetailsResultTable;
import com.ominfo.staff.ui.lr_number.model.VehicleDetailsViewModel;
import com.ominfo.staff.util.AppUtils;
import com.ominfo.staff.util.LogUtil;
import com.ominfo.staff.util.SharedPref;

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
import retrofit2.Call;
import retrofit2.Callback;

public class DashbooardActivity extends BaseActivity {

    @BindView(R.id.cardKataChithi)
    CardView mCardKataChithi;
    @BindView(R.id.imgSearchLr)
    AppCompatImageView imgSearchLr;
    @BindView(R.id.tvSearchView)
    AppCompatEditText etSearch;
    @BindView(R.id.tvDriverName)
    AppCompatTextView tvDriverName;
    Context mContext;
    final Calendar myCalendar = Calendar.getInstance();
    private AppDatabase mDb;
    TripAwadhiAdapter mTripAwadhiAdapter;
    List<DriverHisabModel> driverHisabModelList = new ArrayList<>();
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
                uploadVehicle(mContext);
            }else{
                //LogUtil.printToastMSG(this,"yo Not Connected");
            }
    }

    private void init(){
        mDb =BaseApplication.getInstance(mContext).getAppDatabase();
        requestPermission();
        setToolbar();
        LoginResultTable loginResultTable = mDb.getDbDAO().getLoginData();
        if(loginResultTable!=null){
            tvDriverName.setText(loginResultTable.getFirstName()+" !");
        }
        else {
            tvDriverName.setText("Staff !");
        }

        driverHisabModelList.add(new DriverHisabModel("Surat",""));
        driverHisabModelList.add(new DriverHisabModel("","Enter Rout"));

    }

    private void setToolbar(){
        initToolbar(0,mContext,R.id.imgBack,R.id.imgReport,R.id.imgNotify,R.id.imgLogout,R.id.imgCall);
    }

   /* private void setSearchView(){
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b) {
                    textViewSearch.setVisibility(View.VISIBLE);
                }

                else {
                    textViewSearch.setVisibility(View.GONE);
                }
            }
        });
    }*/

    //perform click actions
    @OnClick({R.id.layViewAll,R.id.layAddLr,R.id.imgSearchLr})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layViewAll:
                launchScreen(mContext, SelectedLrNumberActivity.class);
                break;
            case R.id.layAddLr:
                 Intent intent = new Intent(mContext,AddLrActivity.class);
                 intent.putExtra(Constants.TRANSACTION_ID, "0");
                 intent.putExtra(Constants.FROM_SCREEN, Constants.ADD);
                 startActivity(intent);
                break;
            case R.id.imgSearchLr:
                Intent intentSearchLr = new Intent(mContext,AddLrActivity.class);
                intentSearchLr.putExtra(Constants.TRANSACTION_ID, etSearch.getEditableText().toString().trim());
                intentSearchLr.putExtra(Constants.FROM_SCREEN, Constants.SEARCH);
                startActivity(intentSearchLr);
                break;
        }
    }

    private void showDriverHisabDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_trip_awadhi);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.okayButton);
        LinearLayoutCompat layCalender = mDialog.findViewById(R.id.layCalender);
        AppCompatTextView tvDateValue = mDialog.findViewById(R.id.tvDateValue);
        AppCompatTextView tvFromDate = mDialog.findViewById(R.id.tvDateValueFrom);
        RecyclerView rvTripAwadhi = mDialog.findViewById(R.id.rvTripAwadhi);
        AppCompatImageView imgAddTrip = mDialog.findViewById(R.id.imgAddTrip);
        LinearLayoutCompat layFromDate = mDialog.findViewById(R.id.layFromDate);
        setAdapterForTripAwadhiList(driverHisabModelList,rvTripAwadhi);

        imgAddTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = driverHisabModelList.size()-1;
                String latestTrip = driverHisabModelList.get(pos).getDriverHisabTitle();
                //driverHisabModelList.set(pos,new DriverHisabModel(latestTrip,"Enter Rout"));
                //mTripAwadhiAdapter.notifyDataSetChanged();
                driverHisabModelList.add(new DriverHisabModel("","Enter Rout"));
                mTripAwadhiAdapter.notifyDataSetChanged();
            }
        });

        tvFromDate.setText(AppUtils.getYesterdaysDate());
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                //launchScreen(mContext, DriverHisabActivity.class);
            }
        });
        layFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDataPicker(tvFromDate,1);
            }
        });
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        layCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDataPicker(tvDateValue,0);
            }
        });
        mDialog.show();
    }

    private void setAdapterForTripAwadhiList(List<DriverHisabModel> driverHisabModelList,RecyclerView rvTripAwadhi) {
        if (driverHisabModelList.size() > 0) {
            rvTripAwadhi.setVisibility(View.VISIBLE);
        } else {
            rvTripAwadhi.setVisibility(View.GONE);
        }
        mTripAwadhiAdapter = new TripAwadhiAdapter(mContext, driverHisabModelList, new TripAwadhiAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(DriverHisabModel mDataTicket) {

            }
        });
        rvTripAwadhi.setHasFixedSize(true);
        rvTripAwadhi.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvTripAwadhi.setAdapter(mTripAwadhiAdapter);
        mTripAwadhiAdapter.notifyDataSetChanged();
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
                        uploadVehicle(context);
                    }else{
                        //LogUtil.printToastMSG(context,"yo Not Connected");
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
}