/*
package com.ominfo.crm_solution.ui.lr_number;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ominfo.crm_solution.BuildConfig;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.basecontrol.BaseApplication;
import com.ominfo.crm_solution.common.TouchImageView;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.interfaces.Constants;
import com.ominfo.crm_solution.network.ApiResponse;
import com.ominfo.crm_solution.network.DynamicAPIPath;
import com.ominfo.crm_solution.network.NetworkCheck;
import com.ominfo.crm_solution.network.ViewModelFactory;
import com.ominfo.crm_solution.ui.lr_number.adapter.AddLrAdapter;
import com.ominfo.crm_solution.ui.lr_number.adapter.LrDetailsAdapter;
import com.ominfo.crm_solution.ui.lr_number.model.PlantRequest;
import com.ominfo.crm_solution.ui.lr_number.model.PlantResponse;
import com.ominfo.crm_solution.ui.lr_number.model.PlantResponseResult;
import com.ominfo.crm_solution.ui.lr_number.model.PlantViewModel;
import com.ominfo.crm_solution.ui.lr_number.model.SearchLrRequest;
import com.ominfo.crm_solution.ui.lr_number.model.SearchLrResponse;
import com.ominfo.crm_solution.ui.lr_number.model.SearchViewModel;
import com.ominfo.crm_solution.ui.lr_number.model.UploadVehicleImage;
import com.ominfo.crm_solution.ui.lr_number.model.VehicalNoViewModel;
import com.ominfo.crm_solution.ui.lr_number.model.VehicalNumberRequest;
import com.ominfo.crm_solution.ui.lr_number.model.VehicalNumberResponse;
import com.ominfo.crm_solution.ui.lr_number.model.VehicalResponseResult;
import com.ominfo.crm_solution.ui.lr_number.model.VehicleDetailsLrImage;
import com.ominfo.crm_solution.ui.lr_number.model.VehicleDetailsRequest;
import com.ominfo.crm_solution.ui.lr_number.model.VehicleDetailsResponse;
import com.ominfo.crm_solution.ui.lr_number.model.VehicleDetailsViewModel;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;
import com.ominfo.crm_solution.util.RealPathUtils;
import com.ominfo.crm_solution.util.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddLrActivity extends BaseActivity {

    Context mContext;
    @BindView(R.id.rvPuranaHisab)
    RecyclerView rvPuranaHisab;
    @BindView(R.id.toolbarTitle)
    AppCompatTextView toolbarTitle;
    @BindView(R.id.AutoComTextViewVehNo)
    AppCompatAutoCompleteTextView AutoComTextViewVehNo;
    @BindView(R.id.imgEdit)
    AppCompatImageView imgEdit;
    @BindView(R.id.AutoComTextViewPlant)
    AppCompatAutoCompleteTextView AutoComTextViewPlant;
    @BindView(R.id.etDate)
    AppCompatEditText etDate;
    @BindView(R.id.etNoOfLr)
    AppCompatEditText etNoOfLr;
    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat linearLayoutEmptyActivity;
    @BindView(R.id.submitButton)
    AppCompatButton submitButton;
    AddLrAdapter mAddLrAdapter;
    private Uri picUri;
    private String tempUri;
    @Inject
    ViewModelFactory mViewModelFactory;
    private VehicalNoViewModel mVehicalNoViewModel;
    private VehicleDetailsViewModel mVehicleDetailsViewModel;
    private SearchViewModel mSearchViewModel;
    LrDetailsAdapter mLrDetailsAdapter;
    List<VehicleDetailsLrImage> vehicleImageModelList = new ArrayList<>();
    List<PlantResponseResult> plantDropdownList = new ArrayList<>();
    private String transactionId = "";
    private String fromScr = "";
    private AppDatabase mDb;
    private String mUserKey = "";
    //private String plantId = "",vehicleId = "",vehicleNo = "";
    List<VehicalResponseResult> vehicleNoDropdown = new ArrayList<>();
    private PlantViewModel mPlantViewModel;
    private String plantIdDropdown = "" , VehiIdDropdown = "";
    final Calendar myCalendar = Calendar.getInstance();
    private int SELECT_FILE = 1;
    private static final int REQUEST_CAMERA = 0;
    int positionCamera = 0;
    LoginResultTable loginResultTable;
    List<UploadVehicleImage> mImageList = new ArrayList<>();
    private int downloaded = 0, downloadedCount = 0;
    int cam = 0;
    long delay = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_credit);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        mContext = this;
        getDeps().inject(this);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        injectAPI();
        //get dataset data
        loginResultTable = mDb.getDbDAO().getLoginData();
        if (loginResultTable != null) {
            mUserKey = loginResultTable.getUserKey();
        }
        cam=2;
        requestPermission();
        //set toolbar
        setToolbar();
        getVehicleNoFromView();
        callPlantApi();
        getIntentDate();
    }

    private void deleteDir(){
        File dir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), Constants.FILE_NAME);
        //File oldFile = new File(myDir);
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++)
            {
                new File(dir, children[i]).delete();
            }
        }
    }

    private void downloadImageFromUrl(String url,String lr ,File file,boolean status){
        @SuppressLint("StaticFieldLeak")
     class DownloadFileFromURL extends AsyncTask<String, String, VehicleDetailsLrImage> {

        private static final String TAG = "yyyy";
        private String LrNo = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressLoader(getString(R.string.scr_message_please_wait));
        }

        @Override
        protected VehicleDetailsLrImage doInBackground(String... params) {
            VehicleDetailsLrImage repeatativeList = new VehicleDetailsLrImage();
            if (!url.equals("")) {
                int count;
                try {
                    String baseUrl = url;
                    URL url = new URL(baseUrl*/
/* + params[0]*//*
);
                    URLConnection connection = url.openConnection();
                    connection.connect();

                    // this will be useful so that you can show a tipical 0-100%
                    // progress bar
                    int lengthOfFile = connection.getContentLength();

                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream(), 8192);

                    // Output stream
                    OutputStream output = new FileOutputStream(file);

                    byte[] data = new byte[1024];

                    long total = 0;

                    while ((count = input.read(data)) != -1) {
                        total += count;
                        // publishing the progress....
                        // After this onProgressUpdate will be called
                        publishProgress("" + (int) ((total * 100) / lengthOfFile));

                        // writing data to file
                        output.write(data, 0, count);
                    }

                    // flushing output
                    output.flush();

                    // closing streams
                    output.close();
                    input.close();
                    repeatativeList.setLr(lr);
                    repeatativeList.setImage("");
                    repeatativeList.setImageUri(null);
                    repeatativeList.setImagePath(file.getAbsolutePath());
                    //new VehicleDetailsLrImage(lr, "", null,file.getAbsolutePath()));

                } catch (Exception e) {
                    LogUtil.printLog(TAG, "Error: " + Objects.requireNonNull(e.getMessage()));
                }
            }
                //LrNo = mImageList.get(i).getLr();
           // }
            return repeatativeList;
        }

        @Override
        protected void onPostExecute(VehicleDetailsLrImage list) {
            vehicleImageModelList.add(list);
            downloadedCount++;
            if(downloaded==downloadedCount){
                final Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismissLoader();
                        linearLayoutEmptyActivity.setVisibility(View.GONE);
                        setAdapterForLrImageList(status);
            }
                }, delay);
            }
        }
    }
        new DownloadFileFromURL().execute("url", "path","lr");
    }

    private void setToolbar() {
        //set toolbar title
        //toolbarTitle.setText(R.string.scr_lbl_add_new_lr);
        initToolbar(1, mContext, R.id.imgBack, R.id.imgReport, R.id.imgNotify, 0, R.id.imgCall);
    }

    private void injectAPI() {
        mVehicalNoViewModel = ViewModelProviders.of(AddLrActivity.this, mViewModelFactory).get(VehicalNoViewModel.class);
        mVehicalNoViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_VEHICLE_NO));

        mVehicleDetailsViewModel = ViewModelProviders.of(AddLrActivity.this, mViewModelFactory).get(VehicleDetailsViewModel.class);
        mVehicleDetailsViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_VEHICLE_DETAILS));

        mPlantViewModel = ViewModelProviders.of(AddLrActivity.this, mViewModelFactory).get(PlantViewModel.class);
        mPlantViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_PLANT));

        mSearchViewModel = ViewModelProviders.of(AddLrActivity.this, mViewModelFactory).get(SearchViewModel.class);
        mSearchViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_SEARCH));
    }

    private void getIntentDate(){
        //get dataset data
        Intent intent = getIntent();
        if (intent != null) {
            transactionId = intent.getStringExtra(Constants.TRANSACTION_ID);
            fromScr = intent.getStringExtra(Constants.FROM_SCREEN);
            if(fromScr.equals(Constants.ADD)){
                submitButton.setVisibility(View.VISIBLE);
                imgEdit.setVisibility(View.GONE);
                toolbarTitle.setText(R.string.scr_lbl_add_new_lr);
                etDate.setText(AppUtils.getCurrentDateTime_());
                setAdapterForLrImageList(false);
            }
            if(fromScr.equals(Constants.LIST)){
                setLrPageDissable();
                callVehicleDetailsApi();
            }
            if(fromScr.equals(Constants.SEARCH)){
                setLrPageDissable();
                callSearchApi();
            }
        }
    }

    private void setLrPageDissable(){
        etDate.setEnabled(false);
        etNoOfLr.setEnabled(false);
        AutoComTextViewPlant.setEnabled(false);
        AutoComTextViewVehNo.setEnabled(false);
        submitButton.setVisibility(View.GONE);
        imgEdit.setVisibility(View.VISIBLE);
        toolbarTitle.setText(R.string.scr_lbl_lr_details);
    }

    */
/* Call Api For Vehicle No *//*

    private void callVehicleDetailsApi() {
        if (NetworkCheck.isInternetAvailable(AddLrActivity.this)) {
            VehicleDetailsRequest mRequest = new VehicleDetailsRequest();
            mRequest.setUserkey(mUserKey); //6b07b768-926c-49b6-ac1c-89a9d03d4c3b
            mRequest.setTransactionID(transactionId);
            Gson gson = new Gson();
            String bodyInStringFormat = gson.toJson(mRequest);
            mVehicleDetailsViewModel.hitVehicleDetailsApi(bodyInStringFormat);
        } else {
            LogUtil.printToastMSG(AddLrActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    */
/* Call Api For Search *//*

    private void callSearchApi() {
        if (NetworkCheck.isInternetAvailable(AddLrActivity.this)) {
            SearchLrRequest mRequest = new SearchLrRequest();
            mRequest.setUserkey(mUserKey); //6b07b768-926c-49b6-ac1c-89a9d03d4c3b
            mRequest.setLrNo(transactionId);
            Gson gson = new Gson();
            String bodyInStringFormat = gson.toJson(mRequest);
            mSearchViewModel.hitSearchApi(bodyInStringFormat);
        } else {
            LogUtil.printToastMSG(AddLrActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    private void setVehicleDetails(List<UploadVehicleImage> mImageList,String date,String vehNo ,String NoLr ,String plant,boolean status){
        etDate.setText(AppUtils.getLrDetailsDate(date));//AppUtils.getCurrentDateTime_());
        etNoOfLr.setText(NoLr);
        AutoComTextViewVehNo.setText(vehNo);
        plantIdDropdown = plant;
        setDropdownPlant();
        downloadImageList(mImageList,status);
    }

    private void getVehicleNoFromView() {
        AutoComTextViewVehNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before,
                                      int count) {

            }

            @Override
            public void afterTextChanged(final Editable s) {
                //avoid triggering event when text is empty
                if(s.length()==0){
                    if(vehicleNoDropdown!=null){
                    vehicleNoDropdown.remove(vehicleNoDropdown);}
                }
                if (s.length() > 1 && s.length()<8) {
                        callVehicleNoApi(s.toString());
                } else {
                   //LogUtil.printToastMSG(mContext,s.length()+"is short");
                }
            }
        });

    }

    */
/* Call Api For Vehicle No *//*

    private void callVehicleNoApi(String searchFor) {
        if (NetworkCheck.isInternetAvailable(AddLrActivity.this)) {
            VehicalNumberRequest mLoginRequest = new VehicalNumberRequest();
            mLoginRequest.setUserkey(mUserKey); //6b07b768-926c-49b6-ac1c-89a9d03d4c3b
            mLoginRequest.setSearchFor(searchFor);
            Gson gson = new Gson();
            String bodyInStringFormat = gson.toJson(mLoginRequest);
            mVehicalNoViewModel.hitVehicleNoApi(bodyInStringFormat);
        } else {
            LogUtil.printToastMSG(AddLrActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    */
/* Call Api For Vehicle No *//*

    private void callPlantApi() {
        if (NetworkCheck.isInternetAvailable(AddLrActivity.this)) {
            PlantRequest mLoginRequest = new PlantRequest();
            mLoginRequest.setUserkey(mUserKey); //6b07b768-926c-49b6-ac1c-89a9d03d4c3b
            Gson gson = new Gson();
            String bodyInStringFormat = gson.toJson(mLoginRequest);
            mPlantViewModel.hitPlantApi(bodyInStringFormat);
        } else {
            LogUtil.printToastMSG(AddLrActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    //set value to vehicle colour dropdown
    private void setDropdownVehNo() {
        try {
            int pos = 0;
            if (vehicleNoDropdown != null && vehicleNoDropdown.size() > 0) {
                String[] mDropdownList = new String[vehicleNoDropdown.size()];
                for (int i = 0; i < vehicleNoDropdown.size(); i++) {
                    mDropdownList[i] = String.valueOf(vehicleNoDropdown.get(i).getName());
                    if (!VehiIdDropdown.equals("")) {
                        if (vehicleNoDropdown.get(i).getId().equals(VehiIdDropdown)) {
                            pos = i;
                        }
                    }
                }
                //AutoComTextViewVehNo.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //AutoComTextViewVehNo.setThreshold(1);
                AutoComTextViewVehNo.setAdapter(adapter);
                //mSelectedColor = mDropdownList[pos];
                AutoComTextViewVehNo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //mSelectedColor = mDropdownList[position];
                        AppUtils.hideKeyBoard(AddLrActivity.this);
                        VehiIdDropdown = vehicleNoDropdown.get(position).getId();
                        */
/*new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                AutoComTextViewVehNo.setEnabled(true);
                            }
                        }, 200);*//*

                    }

                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //set value to vehicle colour dropdown
    private void setDropdownPlant() {
        try {
            int pos = 0;
            if (plantDropdownList != null && plantDropdownList.size() > 0) {
                String[] mDropdownList = new String[plantDropdownList.size()];
                for (int i = 0; i < plantDropdownList.size(); i++) {
                    mDropdownList[i] = String.valueOf(plantDropdownList.get(i).getServiceLocationName());
                    if (!plantIdDropdown.equals("")) {
                        if (plantDropdownList.get(i).getServiceLocationID().equals(plantIdDropdown)) {
                            pos = i;
                            AutoComTextViewPlant.setText(mDropdownList[pos]);
                        }
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //AutoComTextViewPlant.setText("");
                //AutoComTextViewPlant.setThreshold(1);
                AutoComTextViewPlant.setAdapter(adapter);
                plantIdDropdown=plantDropdownList.get(pos).getServiceLocationID();
                AutoComTextViewPlant.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //mSelectedColor = mDropdownList[position];
                        plantIdDropdown = plantDropdownList.get(position).getServiceLocationID();
                    }

                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void shareToInstant(String content, File imageFile, View view) {

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/png");
        sharingIntent.setType("text/plain");
        sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sharingIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(view.getContext(), BuildConfig.APPLICATION_ID + ".provider", imageFile));
        sharingIntent.putExtra(Intent.EXTRA_TEXT, content);

        try {
            view.getContext().startActivity(Intent.createChooser(sharingIntent, "Share it Via"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(view.getContext(), R.string.err_msg_connection_was_refused, Toast.LENGTH_SHORT).show();
        }
    }


    //show truck details popup
    public void showFullImageDialog(VehicleDetailsLrImage mData,Bitmap imgUrl) {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_doc_full_view);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.detailsButton);
        TouchImageView imgShow = mDialog.findViewById(R.id.imgShowPhoto);
        AppCompatImageView imgShare = mDialog.findViewById(R.id.imgShare);

        try {
            imgShow.setImageBitmap(imgUrl);
            if (mData.getImageUri() != null) {
                if (!mData.getImageUri().equals("")) {
                    String actualPath = RealPathUtils.getActualPath(mContext, Uri.parse(mData.getImageUri()));
                    File imgFile = new File(actualPath);
                    //imgShow.setImageURI(Uri.fromFile(imgFile));
                    imgShare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                shareToInstant("Shared Lr No "+mData.getLr()+" photo", imgFile, view);
                            } catch (Exception e) {
                            }
                        }
                    });
                }
            }
            if (mData.getImagePath() != null) {
                if (!mData.getImagePath().equals("")) {
                    File imgFile = new File(mData.getImagePath());
                    imgShow.setImageURI(Uri.fromFile(imgFile));
                    imgShare.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                shareToInstant("Shared Lr No "+mData.getLr()+" photo", imgFile, view);
                            } catch (Exception e) {
                            }
                        }
                    });
                }
            }
        }catch (Exception e){}

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

    private void setAdapterForLrImageList(boolean editStatus) {
        if(!editStatus) {
            if(vehicleImageModelList.size()==0) {
                vehicleImageModelList.add(new VehicleDetailsLrImage("", "", null,null));
            }
        }

        if (vehicleImageModelList.size() > 0) {
            mAddLrAdapter = new AddLrAdapter(mContext, editStatus, vehicleImageModelList, new AddLrAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(List<VehicleDetailsLrImage> mDataTicket, int position) {
                    vehicleImageModelList = mDataTicket;
                    mAddLrAdapter.updateList(vehicleImageModelList);
                }
            }, new AddLrAdapter.ListItemSelectCamera() {
                @Override
                public void onItemClick(List<VehicleDetailsLrImage> mData, int Position, Bitmap bitmap,boolean editStatus) {
                    try {
                        vehicleImageModelList = mData;
                        mAddLrAdapter.updateList(vehicleImageModelList);
                        positionCamera = Position;
                        if(!editStatus) {
                            if (bitmap != null) {
                                showFullImageDialog(mData.get(positionCamera), bitmap);
                            } else {
                                showChooseCameraDialog();
                            }
                        }
                        else {
                            try{
                                showFullImageDialog(mData.get(positionCamera), bitmap);
                            }catch (Exception e){}
                        }
                    }catch (Exception e){}
                }
            }, new AddLrAdapter.ListItemSelectRemove() {
                @Override
                public void onItemClick(List<VehicleDetailsLrImage> mData, int position) {
                    vehicleImageModelList = mData;
                    mAddLrAdapter.updateList(vehicleImageModelList);
                }
            }, new AddLrAdapter.ListItemSelectRemoveImage() {
                @Override
                public void onItemClick(List<VehicleDetailsLrImage> mData, int position) {
                    vehicleImageModelList = mData;
                    mAddLrAdapter.updateList(vehicleImageModelList);
                }
            });
            rvPuranaHisab.setHasFixedSize(true);
            rvPuranaHisab.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            rvPuranaHisab.setAdapter(mAddLrAdapter);
            rvPuranaHisab.setVisibility(View.VISIBLE);
            linearLayoutEmptyActivity.setVisibility(View.GONE);

        } else {
            rvPuranaHisab.setVisibility(View.GONE);
            linearLayoutEmptyActivity.setVisibility(View.VISIBLE);
        }
    }

    //perform click actions
    @OnClick({R.id.imgEdit,R.id.submitButton,R.id.etDate})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgEdit:
                setLrPageEnable();
                break;
            case R.id.submitButton:
                if(isDetailsValid()){
                    try{
                    callUploadVehicleRecordApi(transactionId);
                    }catch (Exception e){}
                }
                break;
            case R.id.etDate:
                openDataPicker(etDate);
                break;
        }
    }

    private void setLrPageEnable(){
        etDate.setEnabled(true);
        etNoOfLr.setEnabled(true);
        AutoComTextViewPlant.setEnabled(true);
        AutoComTextViewVehNo.setEnabled(true);
        submitButton.setVisibility(View.VISIBLE);
        setAdapterForLrImageList(false);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        picUri = getOutputPhotoFile();//Uri.fromFile(getOutputPhotoFile());
        //tempUri=picUri;
        intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
        //intent.putExtra("URI", picUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private Uri getOutputPhotoFile() {
        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), Constants.FILE_NAME);
        tempUri = directory.getPath();
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Log.e("getOutputPhotoFile", "Failed to create storage directory.");
                return null;
            }
        }

        Uri path;
        if (Build.VERSION.SDK_INT > 23) {
            File oldPath = new File(directory.getPath() + File.separator + "IMG_temp.jpg");
            String fileUrl = oldPath.getPath();
            if (fileUrl.substring(0, 7).matches("file://")) {
                fileUrl = fileUrl.substring(7);
            }
            File file = new File(fileUrl);

            path = FileProvider.getUriForFile(mContext, this.getPackageName() + ".provider", file);
        } else {
            path = Uri.fromFile(new File(directory.getPath() + File.separator + "IMG_temp.jpg"));
        }
        return path;
    }
    */
/*
     * permission result
     * *//*

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                ) {
                    if(cam==0) {
                        cameraIntent();
                    }else if(cam==1) {
                        galleryIntent();
                    }else if(cam==2) {
                        deleteDir();
                    }
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
                if(cam==0) {
                    cameraIntent();
                }else if(cam==1) {
                    galleryIntent();
                }else if(cam==2) {
                    deleteDir();
                }
            }
        } else {
            if(cam==0) {
                cameraIntent();
            }else if(cam==1) {
                galleryIntent();
            }else if(cam==2) {
                deleteDir();
            }
        }
    }

    */
/*Api response *//*

    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {

            case LOADING:
                showProgressLoader(getString(R.string.scr_message_please_wait));
                break;

            case SUCCESS:
                dismissLoader();
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_VEHICLE_NO)) {
                            VehicalNumberResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), VehicalNumberResponse.class);
                            if (responseModel != null && responseModel.getStatus().equals("1")) {
                                //LogUtil.printToastMSG(AddLrActivity.this, responseModel.getMessage());
                                vehicleNoDropdown = responseModel.getResult();
                                setDropdownVehNo();
                                AutoComTextViewVehNo.showDropDown();
                            } else {
                                LogUtil.printToastMSG(AddLrActivity.this, responseModel.getMessage());
                            }
                        }
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_PLANT)) {
                            PlantResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), PlantResponse.class);
                            if (responseModel != null && responseModel.getStatus().equals("1")) {
                                //LogUtil.printToastMSG(AddLrActivity.this, responseModel.getMessage());
                                plantDropdownList = responseModel.getResult();
                                setDropdownPlant();
                            } else {
                                LogUtil.printToastMSG(AddLrActivity.this, responseModel.getMessage());
                            }
                        }
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_VEHICLE_DETAILS)) {
                            VehicleDetailsResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), VehicleDetailsResponse.class);
                            if (responseModel != null && responseModel.getStatus().equals("1")) {
                                //LogUtil.printToastMSG(AddLrActivity.this, responseModel.getMessage());
                                plantIdDropdown = responseModel.getResult().getPlantID();
                                VehiIdDropdown = responseModel.getResult().getVehicleID();
                                vehicleImageModelList.removeAll(vehicleImageModelList);
                                */
/*if(responseModel.getResult().getLrImages()!=null) {
                                    for (int i = 0; i < responseModel.getResult().getLrImages().size(); i++) {
                                        vehicleImageModelList.add(new VehicleDetailsLrImage(responseModel.getResult().getLrImages().get(i).getLr(), responseModel.getResult().getLrImages().get(i).getImage(), "", ""));
                                    }
                                }*//*

                                setVehicleDetails(responseModel.getResult().getLrImages(),responseModel.getResult().getTransactionDate(),
                                        responseModel.getResult().getVehicleNo(),responseModel.getResult().getNoOfLR()
                                ,responseModel.getResult().getPlantID(),true);
                            } else {
                                LogUtil.printToastMSG(AddLrActivity.this, responseModel.getMessage());
                            }
                        }
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_SEARCH)) {
                            try {
                                SearchLrResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), SearchLrResponse.class);
                                if (responseModel != null && responseModel.getStatus().equals("1")) {
                                    //LogUtil.printToastMSG(AddLrActivity.this, responseModel.getMessage());
                                    plantIdDropdown = responseModel.getResult().getPlantID();
                                    VehiIdDropdown = responseModel.getResult().getVehicleID();
                                    vehicleImageModelList.removeAll(vehicleImageModelList);
                                    vehicleImageModelList = responseModel.getResult().getLrImages();
                                    List<UploadVehicleImage> mImageList = new ArrayList<>();
                                    if(responseModel.getResult().getLrImages()!=null) {
                                        for (int i = 0; i < responseModel.getResult().getLrImages().size(); i++) {
                                            mImageList.add(new UploadVehicleImage(responseModel.getResult().getLrImages().get(i).getLr(), responseModel.getResult().getLrImages().get(i).getImage()));
                                        }
                                    }
                                    setVehicleDetails(mImageList,responseModel.getResult().getTransactionDate(),
                                            responseModel.getResult().getVehicleNo(), responseModel.getResult().getNoOfLR()
                                            , responseModel.getResult().getPlantID(), true);
                                }
                            }catch (Exception e){
                                showSuccessDialog(getString(R.string.msg_no_such_lr),true);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 1200);
                            }
                            //LogUtil.printToastMSG(AddLrActivity.this, responseModel.getMessage());

                        }
                    }catch (Exception e){
                       e.printStackTrace();
                    }
                }
                break;
            case ERROR:
                dismissLoader();
                LogUtil.printToastMSG(AddLrActivity.this, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void showChooseCameraDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_select_image);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatTextView tvChooseFromCamera = mDialog.findViewById(R.id.tvChooseFromCamera);
        AppCompatTextView tvCameraImage = mDialog.findViewById(R.id.tvCameraImage);
        tvChooseFromCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                cam = 1;
                requestPermission();
            }
        });
        tvCameraImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                cam = 0;
                requestPermission();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                if (data != null) {
                    onSelectFromGalleryResult(data,"",0);
                }
            }
            if (requestCode == REQUEST_CAMERA  && resultCode == Activity.RESULT_OK) {
                if (resultCode == RESULT_OK) {
                    try {

                        File file = new File(tempUri + "/IMG_temp.jpg");

                        Bitmap mImgaeBitmap = null;
                        try {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                            // Calculate inSampleSize
                            options.inSampleSize = Util.calculateInSampleSize(options, 600, 600);

                            // Decode bitmap with inSampleSize set
                            options.inJustDecodeBounds = false;
                            Bitmap scaledBitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                            //check the rotation of the image and display it properly
                            ExifInterface exif;
                            exif = new ExifInterface(file.getAbsolutePath());
                            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
                            Matrix matrix = new Matrix();
                            if (orientation == 6) {
                                matrix.postRotate(90);
                            } else if (orientation == 3) {
                                matrix.postRotate(180);
                            } else if (orientation == 8) {
                                matrix.postRotate(270);
                            }
                            mImgaeBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);
                            SaveImageMM(mImgaeBitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

//                    CropImage.activity(picUri).start(this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void onSelectFromGalleryResult(Intent data,String pathFile,int cameraOrGallery) {
        if(cameraOrGallery==0) {
            Uri path = data.getData();
            vehicleImageModelList.set(positionCamera, new VehicleDetailsLrImage(vehicleImageModelList.get(positionCamera).getLr(), "", path.toString(), null));
            mAddLrAdapter.updateList(vehicleImageModelList);
        }else {
            vehicleImageModelList.set(positionCamera, new VehicleDetailsLrImage(vehicleImageModelList.get(positionCamera).getLr(), "", null, pathFile));
            mAddLrAdapter.updateList(vehicleImageModelList);
        }
    }

    //TODO will add comments later
    private void SaveImageMM(Bitmap finalBitmap) {
        File myDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), Constants.FILE_NAME);
        myDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.US).format(new Date());
        String fname = "Image_" + timeStamp + "_capture.jpg";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            //new ImageCompression(this,file.getAbsolutePath()).execute(finalBitmap);
            FileOutputStream out = new FileOutputStream(file);
            //finalBitmap = Bitmap.createScaledBitmap(finalBitmap,(int)1080/2,(int)1920/2, true);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 60, out); //less than 300 kb
            out.flush();
            out.close();
            String oldFname = "IMG_temp.jpg";
            File oldFile = new File(myDir, oldFname);
            if (oldFile.exists()) oldFile.delete();
            //save image to db
            int id = 0;
            String pathDb = file.getAbsolutePath();
            onSelectFromGalleryResult(null,pathDb,1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void downloadImageList(List<UploadVehicleImage> mImageList,boolean status){
        if(mImageList!=null) {
            File myDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), Constants.FILE_NAME);
            myDir.mkdirs();
            vehicleImageModelList.removeAll(vehicleImageModelList);
            downloaded = mImageList.size();
            for (int i = 0; i < mImageList.size(); i++) {
                Random rnd = new Random();
                int n = 100000 + rnd.nextInt(900000);
                String fname = "Image_" + n + "_capture.jpg";
                File file = new File(myDir, fname);
                final Handler handler = new Handler(Looper.getMainLooper());
                int finalI = i;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        downloadImageFromUrl(mImageList.get(finalI).getImage(),mImageList.get(finalI).getLr(), file,status);
                    }
                }, delay);
            }
           */
/* DownloadFileAsync dloadFAsync = new DownloadFileAsync(mImageList);
            dloadFAsync.execute(mImageList);*//*

        }
    }

    //              Async  Task
    class DownloadFileAsync extends AsyncTask<List<UploadVehicleImage>, String, String> {
        int current=0;
        List<UploadVehicleImage> paths;
        String fpath;
        boolean show = false;
        File file=null;

        public DownloadFileAsync(List<UploadVehicleImage> paths) {
            super();
            this.paths = paths;
            for(int i=0; i<paths.size(); i++)
                System.out.println((i+1)+"check:  "+paths.get(i).getImage());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressLoader(getString(R.string.scr_message_please_wait));
        }

        @Override
        protected String doInBackground(List<UploadVehicleImage>... lists) {

            File myDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), Constants.FILE_NAME);
            myDir.mkdirs();
            int rows = paths.size();
            while(current < rows)
            {
                int count;
                if (!paths.get(current).getImage().equals("")) {
                    String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.US).format(new Date());
                    String fname = "Image_" + timeStamp + "_capture.jpg";
                     file = new File(myDir, fname);
                     final Handler handler = new Handler(Looper.getMainLooper());
                     handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            LogUtil.printToastMSG(mContext, "Error: " +paths.get(current).getImage());
                        }
                    }, 0);
                    try {
                        String baseUrl = paths.get(current).getImage();
                        URL url = new URL(baseUrl*/
/* + params[0]*//*
);
                        URLConnection connection = url.openConnection();
                        connection.connect();

                        // this will be useful so that you can show a tipical 0-100%
                        // progress bar
                        int lengthOfFile = connection.getContentLength();

                        // download the file
                        InputStream input = new BufferedInputStream(url.openStream(), 8192);

                        // Output stream
                        OutputStream output = new FileOutputStream(file);

                        byte[] data = new byte[1024];

                        long total = 0;

                        while ((count = input.read(data)) != -1) {
                            total += count;
                            // publishing the progress....
                            // After this onProgressUpdate will be called
                            publishProgress("" + (int) ((total * 100) / lengthOfFile));

                            // writing data to file
                            output.write(data, 0, count);
                        }
                        show = true;
                        vehicleImageModelList.add(new VehicleDetailsLrImage(paths.get(current).getLr(), "", null,file.getAbsolutePath()));

                        // flushing output
                        output.flush();

                        // closing streams
                        output.close();
                        input.close();
                        current++;
                    } catch (Exception e) {
                        //LogUtil.printLog(TAG, "Error: " + Objects.requireNonNull(e.getMessage()));
                    }
                }
            }   //  while end
            return null;
        }

        @Override
        protected void onProgressUpdate(String... progress) {
            //prgBar1.setProgress(Integer.parseInt(progress[0]));
            if(show) {

               */
/* File dir = Environment.getExternalStorageDirectory();
                File imgFile = new File(dir, getFileName(this.paths[(current-1)]));
                Bitmap bmp  = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imgv1.setImageBitmap(bmp);*//*

                show = false;
            }
        }

        @Override
        protected void onPostExecute(String unused) {
            dismissLoader();
            linearLayoutEmptyActivity.setVisibility(View.GONE);
            setAdapterForLrImageList(true);
        }
    }

    */
/* Call Api to save kata chitthi *//*

    private void callUploadVehicleRecordApi(String tranId) {
        CallUploadVehicleInBg(tranId,"","");
    }

    private void CallUploadVehicleInBg(String tranId ,String path , String lr){
        @SuppressLint("StaticFieldLeak")
        class CallUploadVehicleInBg extends AsyncTask<String, String, String> {

            private static final String TAG = "yyyy";

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgressLoader(getString(R.string.scr_message_please_wait));
            }

            @Override
            protected String doInBackground(String... params) {
                int count;
                Random rnd = new Random();
                int number = rnd.nextInt(999);
                // this will convert any number sequence into 6 character.
                String geneatedId = String.format("%03d", number);
                showProgressLoader(getString(R.string.scr_message_please_wait));
                for (int i = 0; i < vehicleImageModelList.size(); i++) {
                    // Get the file instance
                    if(vehicleImageModelList.get(i).getImageUri()!=null){
                        if(!vehicleImageModelList.get(i).getImageUri().equals("")) {
                            try {
                                String actualPath = RealPathUtils.getActualPath(mContext, Uri.parse(vehicleImageModelList.get(i).getImageUri()));
                                String mBase64 = AppUtils.getBase64images(actualPath);
                                mImageList.add(new UploadVehicleImage(vehicleImageModelList.get(i).getLr(), mBase64));
                            }catch (Exception e){
                                e.printStackTrace();
                                mImageList.add(new UploadVehicleImage(vehicleImageModelList.get(i).getLr(), "data:image/png;base64,"));
                            }
                        }
                    }
                    if(vehicleImageModelList.get(i).getImagePath()!=null){
                        if(!vehicleImageModelList.get(i).getImagePath().equals("")) {
                            try{
                            String mBase64 = AppUtils.getBase64images(vehicleImageModelList.get(i).getImagePath());
                            mImageList.add(new UploadVehicleImage(vehicleImageModelList.get(i).getLr(), mBase64));
                            }catch (Exception e){
                                e.printStackTrace();
                                mImageList.add(new UploadVehicleImage(vehicleImageModelList.get(i).getLr(), "data:image/png;base64,"));
                            }
                        }
                    }
                    if(i==vehicleImageModelList.size()-1){
                       dismissLoader();
                    }
                }
               */
/* if (NetworkCheck.isInternetAvailable(AddLrActivity.this)) {
                    //showProgressLoader(getString(R.string.scr_message_please_wait));
                    if(mImageList.size()>0) {
                        try {
                            if (loginResultTable != null) {
                                UploadVehicleRecordRequest mLoginRequest = new UploadVehicleRecordRequest();
                                mLoginRequest.setUserkey(loginResultTable.getUserKey());
                                mLoginRequest.setVehicleID(VehiIdDropdown);
                                mLoginRequest.setTransactionDate(AppUtils.splitsDashFormatVehicle(etDate.getEditableText().toString().trim()));
                                mLoginRequest.setUserID(loginResultTable.getUserID());
                                mLoginRequest.setGeneratedId(geneatedId);
                                mLoginRequest.setTransactionID(tranId);
                                mLoginRequest.setPhotoXml(mImageList);
                                mLoginRequest.setNoOfLR(etNoOfLr.getEditableText().toString().trim());
                                mLoginRequest.setBranchID(loginResultTable.getMainId());
                                mLoginRequest.setPlantID(plantIdDropdown);///AutoComTextViewPlant.getEditableText().toString().trim());
                                Gson gson = new Gson();
                                String bodyInStringFormat = gson.toJson(mLoginRequest);
                                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), "saveVehWiseLRTransaction");
                                RequestBody mRequestBodyTypeImage = RequestBody.create(MediaType.parse("text/plain"), bodyInStringFormat);
                                LogUtil.printLog("request save", bodyInStringFormat);

                                NetworkAPIServices mNetworkAPIServices = RetroNetworkModule.getInstance().getAPI();
                                Call<UploadVehicleRecordRespoonse> call = mNetworkAPIServices.uploadVehicleRecord(DynamicAPIPath.makeDynamicEndpointAPIGateWay(NetworkURLs.BASE_URL,
                                        DynamicAPIPath.POST_UPLOAD_VEHICLE), mRequestBodyType, mRequestBodyTypeImage);

                                call.enqueue(new Callback<UploadVehicleRecordRespoonse>() {
                                    @Override
                                    public void onResponse(@NonNull Call<UploadVehicleRecordRespoonse> call, @NonNull retrofit2.Response<UploadVehicleRecordRespoonse> response) {
                                        try {
                                            //dismissLoader();
                                            if (response.body() != null) {
                                                UploadVehicleRecordRespoonse userInfo = response.body();
                                                if (userInfo.getStatus().equals("1")) {
                                                    showSuccessDialog("Lr Details Saved\nSuccessfully.",true);
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            finish();
                                                        }
                                                    }, 1200);
                                                    //LogUtil.printToastMSG(mContext, userInfo.getMessage());
                                                } else {
                                                    showSuccessDialog(userInfo.getMessage(),true);
                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            finish();
                                                        }
                                                    }, 1200);
                                                }
                                            }
                                        } catch (Exception e) {
                                            //editText.setError("LR no already " + "exists");
                                            LogUtil.printToastMSG(mContext, "LR no already " + "exists");
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFailure(@NonNull Call<UploadVehicleRecordRespoonse> call, @NonNull Throwable t) {
                                        //dismissLoader();
                                        //LogUtil.printToastMSG(mContext,t.getMessage());
                                    }
                                });
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                LogUtil.printToastMSG(mContext,"Please Add Lr Photo.");
                            }
                        });
                    }
                } else {
                    //if(mImageList.size()>0) {
                        try{
                        VehicleDetailsResultTable mDbData = new VehicleDetailsResultTable();
                        mDbData.setLrImages(mImageList);
                        mDbData.setNoOfLR(etNoOfLr.getEditableText().toString().trim());
                        mDbData.setPlantID(plantIdDropdown);
                        mDbData.setTransactionDate(AppUtils.splitsDashFormatVehicle(etDate.getEditableText().toString().trim()));
                        mDbData.setVehicleID(VehiIdDropdown);
                        mDbData.setUserkey(loginResultTable.getUserKey());
                        mDbData.setGeneratedId(geneatedId);
                        mDbData.setVehicleNo(AutoComTextViewVehNo.getEditableText().toString().trim());
                        mDbData.setUploadStatus(1);
                        mDbData.setTransactionID(tranId);
                        mDbData.setBranchID(loginResultTable.getMainId());
                        mDbData.setUserID(loginResultTable.getUserID());
                        mDb.getDbDAO().insertVehicleRecord(mDbData);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            showSuccessDialog("Lr Details will upload shortly, Please check Pending List.",false);
                        }
                    });

                    *//*
*/
/*} else {
                        LogUtil.printToastMSG(mContext,"Please Add Lr.");
                    }*//*
*/
/*
                    //LogUtil.printToastMSG(AddLrActivity.this, getString(R.string.err_msg_connection_was_refused));
                }*//*

                return null;
            }

            @Override
            protected void onPostExecute(String file_url) {
                dismissLoader();
            }
        }
        new CallUploadVehicleInBg().execute(tranId, path,lr);
    }

    //set date picker view
    private void openDataPicker(AppCompatEditText datePickerField) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy/MM/dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                datePickerField.setText(sdf.format(myCalendar.getTime()));
            }

        };

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    */
/*check validations on field*//*

    private boolean isDetailsValid() {
        if (TextUtils.isEmpty(etDate.getText().toString().trim())) {
            LogUtil.printToastMSG(mContext,"Please Enter Date");
            return false;
        } else if (TextUtils.isEmpty(AutoComTextViewVehNo.getText().toString().trim())) {
            LogUtil.printToastMSG(mContext,"Please Enter Vehicle No.");
            return false;
        } else if (TextUtils.isEmpty(AutoComTextViewPlant.getText().toString().trim())) { //6
            LogUtil.printToastMSG(mContext,"Please Enter Plant.");
            return false;
        }else if (TextUtils.isEmpty(etNoOfLr.getText().toString().trim())) { //6
            LogUtil.printToastMSG(mContext, "Please Enter No Of Lr.");
            return false;
        }else if (!etNoOfLr.getText().toString().trim().equals(String.valueOf(vehicleImageModelList.size()))) { //6
            LogUtil.printToastMSG(mContext, "No of Lrs is mismatched with Lr List attached.");
            return false;
        }else {
            for(int i=0;i<vehicleImageModelList.size();i++){
                if(vehicleImageModelList.get(i).getLr()!=null){
                    if(vehicleImageModelList.get(i).getLr().equals("")){
                        LogUtil.printToastMSG(mContext,"Please Enter Lr.");
                        return false;
                    }
                }
                 if(vehicleImageModelList.get(i).getImagePath()!=null){
                    if(vehicleImageModelList.get(i).getImagePath().equals("")){
                        LogUtil.printToastMSG(mContext,"Please Add Lr Photo.");
                        return false;
                    }
                }
                if(vehicleImageModelList.get(i).getImageUri()!=null){
                    if(vehicleImageModelList.get(i).getImageUri().equals("")){
                        LogUtil.printToastMSG(mContext,"Please Add Lr Photo.");
                        return false;
                    }
                }
            }
        }
        return true;
    }


}*/
