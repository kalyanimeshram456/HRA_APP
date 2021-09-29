package com.ominfo.app.ui.kata_chithi;

import static com.ominfo.app.interfaces.Constants.CAMERA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ProgressBar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.ominfo.app.R;
import com.ominfo.app.basecontrol.BaseActivity;
import com.ominfo.app.basecontrol.BaseApplication;
import com.ominfo.app.database.AppDatabase;
import com.ominfo.app.interfaces.Constants;
import com.ominfo.app.interfaces.SharedPrefKey;
import com.ominfo.app.network.ApiResponse;
import com.ominfo.app.network.NetworkCheck;
import com.ominfo.app.network.ViewModelFactory;
import com.ominfo.app.ui.dashboard.DashbooardActivity;
import com.ominfo.app.ui.driver_hisab.model.DriverHisabModel;
import com.ominfo.app.ui.kata_chithi.adapter.ImagesAdapter;
import com.ominfo.app.ui.kata_chithi.model.FetchKataChitthiRequest;
import com.ominfo.app.ui.kata_chithi.model.FetchKataChitthiResponse;
import com.ominfo.app.ui.kata_chithi.model.FetchKataChitthiViewModel;
import com.ominfo.app.ui.kata_chithi.model.SaveKataChitthiRequest;
import com.ominfo.app.ui.kata_chithi.model.SaveKataChitthiResponse;
import com.ominfo.app.ui.kata_chithi.model.SaveKataChitthiViewModel;
import com.ominfo.app.ui.login.LoginActivity;
import com.ominfo.app.ui.login.model.LoginRequest;
import com.ominfo.app.ui.login.model.LoginResponse;
import com.ominfo.app.ui.login.model.LoginResultTable;
import com.ominfo.app.ui.login.model.LoginViewModel;
import com.ominfo.app.ui.notifications.NotificationsActivity;
import com.ominfo.app.ui.purana_hisab.activity.HisabDetailsActivity;
import com.ominfo.app.ui.purana_hisab.adapter.PuranaHisabAdapter;
import com.ominfo.app.util.AppUtils;
import com.ominfo.app.util.LogUtil;
import com.ominfo.app.util.SharedPref;
import com.ominfo.app.util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class KataChithiActivity extends BaseActivity {

    Context mContext;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private Uri picUri;
    private String tempUri;
    final Calendar myCalendar = Calendar.getInstance();
    Bitmap mImgaeBitmap;
    private AppDatabase mDb;

    ImagesAdapter mImagesAdapter;
    List<DriverHisabModel> driverHisabModelList = new ArrayList<>();

    @BindView(R.id.tvDateValue)
    AppCompatTextView tvDateValue;

    @BindView(R.id.rvImages)
    RecyclerView recyclerViewImages;

    @BindView(R.id.imgNoImage)
    CardView imageViewNoImage;

    @Inject
    ViewModelFactory mViewModelFactory;
    private FetchKataChitthiViewModel mFetchKataChitthiViewModel;
    private SaveKataChitthiViewModel mSaveKataChitthiViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kata_chithi);
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
        tvDateValue.setText(AppUtils.getCurrentDateTime());
        callFetchKataChitthiApi();
        setAdapterForPuranaHisabList();
    }

    private void injectAPI() {
        mFetchKataChitthiViewModel = ViewModelProviders.of(KataChithiActivity.this, mViewModelFactory).get(FetchKataChitthiViewModel.class);
        mFetchKataChitthiViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, "fetch"));

        mSaveKataChitthiViewModel = ViewModelProviders.of(KataChithiActivity.this, mViewModelFactory).get(SaveKataChitthiViewModel.class);
        mSaveKataChitthiViewModel.getResponse().observe(this, apiResponse -> consumeResponse(apiResponse, "save"));
    }

    //perform click actions
    @OnClick({R.id.imgBack, R.id.submitButton, R.id.imgCapture, R.id.tvDateValue, R.id.imgNotify
            /*,R.id.imgShow*/})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgBack:
                finish();
                break;
           /* case R.id.imgShow:
                showFullImageDialog();
                break;*/
            case R.id.submitButton:
                // Encode into Base64 URL format
                callSaveKataChitthiApi();
                break;
            case R.id.imgCapture:
                requestPermission();
                break;
            case R.id.tvDateValue:
                openDataPicker(tvDateValue);
                break;
            case R.id.imgNotify:
                launchScreen(mContext, NotificationsActivity.class);
                break;
        }
    }

    //show truck details popup
    public void showFullImageDialog(Bitmap img, Bitmap imgUrl) {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_doc_full_view);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.detailsButton);
        AppCompatImageView imgShow = mDialog.findViewById(R.id.imgShow);
        //File imgFile = new File(img);
        imgShow.setImageBitmap(img);
        if (img != null) {
            imgShow.setImageBitmap(img);
        } else {
            imgShow.setImageBitmap(imgUrl);
        }
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

    //set date picker view
    private void openDataPicker(AppCompatTextView datePickerField) {
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
                datePickerField.setText(sdf.format(myCalendar.getTime()));
            }

        };

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

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
                cameraIntent();
            }
        } else {
            //createFolderForLprImages();
            cameraIntent();

        }
    }

    private void cameraIntent() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    /* Call Api to fetch kata chitthi */
    private void callFetchKataChitthiApi() {
        if (NetworkCheck.isInternetAvailable(KataChithiActivity.this)) {
            LoginResultTable loginResultTable = mDb.getDbDAO().getLoginData();
            if (loginResultTable != null) {
                FetchKataChitthiRequest mLoginRequest = new FetchKataChitthiRequest();
                mLoginRequest.setDriverID(loginResultTable.getDriverId()); //6b07b768-926c-49b6-ac1c-89a9d03d4c3b
                mLoginRequest.setUserkey(loginResultTable.getUserKey());
                mLoginRequest.setVehicleID("6");
                mLoginRequest.setTransactionDate(AppUtils.getCurrentDateTime_());
                Gson gson = new Gson();
                String bodyInStringFormat = gson.toJson(mLoginRequest);
                LogUtil.printLog("request fetch", bodyInStringFormat);
                mFetchKataChitthiViewModel.hitGetKataChitthiApi(bodyInStringFormat);
            }
        } else {
            LogUtil.printToastMSG(KataChithiActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For update profile *//*
    private void callUploadImages(File file, int num) {
        if (NetworkCheck.isInternetAvailable(LprPreviewActivity.this)) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part files = MultipartBody.Part.createFormData("files", file != null ? file.getName() : "", requestFile);
            String[] mDropdownList = new String[]{mCitationNumberId + "_" + num};
            RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), "CitationImages");
            mUploadImageViewModel.hitUploadImagesApi(mDropdownList, mRequestBodyType, files);
        } else {
            //LogUtil.printToastMSG(LprPreviewActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }
*/
    /* Call Api to save kata chitthi */
    private void callSaveKataChitthiApi() {
        if (NetworkCheck.isInternetAvailable(KataChithiActivity.this)) {
            LoginResultTable loginResultTable = mDb.getDbDAO().getLoginData();
            List<String> mImageList = new ArrayList<>();
            for (int i = 0; i < driverHisabModelList.size(); i++) {
                if (driverHisabModelList.get(i).getDriverHisabValue().equals("1")) {
                    String mBase64 = AppUtils.convertBitmapToBas64(driverHisabModelList.get(i).getImgBitmap());
                    mImageList.add("data:image/png;base64," + mBase64);
                }
            }
            if(mImageList.size()>0) {
                if (loginResultTable != null) {
                    SaveKataChitthiRequest mLoginRequest = new SaveKataChitthiRequest();
                    mLoginRequest.setDriverID(Long.parseLong(loginResultTable.getDriverId())); //6b07b768-926c-49b6-ac1c-89a9d03d4c3b
                    mLoginRequest.setUserkey(loginResultTable.getUserKey());
                    mLoginRequest.setVehicleID(Long.parseLong("6"));
                    mLoginRequest.setTransactionDate(getDate(tvDateValue.getText().toString()));
                    mLoginRequest.setUserID(Long.parseLong(loginResultTable.getUserID()));
                    mLoginRequest.setTransactionID(Long.parseLong("0"));
                    mLoginRequest.setPhotoXml(mImageList);
                    Gson gson = new Gson();
                    String bodyInStringFormat = gson.toJson(mLoginRequest);
                    RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), "saveKantaChitthi");
                    RequestBody mRequestBodyTypeImage = RequestBody.create(MediaType.parse("text/plain"), bodyInStringFormat);

               /* RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("action", "saveHisaab")
                        .addFormDataPart("jsonreq", bodyInStringFormat)
                        .build();*/
                    LogUtil.printLog("request save", bodyInStringFormat);
                    mSaveKataChitthiViewModel.hitSaveKataChitthi(mRequestBodyType, mRequestBodyTypeImage);
                }
            }else {
                LogUtil.printToastMSG(mContext,"Please Add Photo.");
            }
        } else {
            LogUtil.printToastMSG(KataChithiActivity.this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    private String getDate(String strDate){
        try {
            String[] separated = strDate.split("/");
            String dd = separated[0];
            String mm = separated[1];
            String yyyy = separated[2];
            return yyyy + "-" + mm + "-" + dd;
        }catch (Exception e){
            return AppUtils.getCurrentDateTime_();
        }
    }

    private void setAdapterForPuranaHisabList() {
        if (driverHisabModelList.size() > 0) {
            mImagesAdapter = new ImagesAdapter(mContext, driverHisabModelList, new ImagesAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(DriverHisabModel mDataTicket, Bitmap bitmap) {
                    showFullImageDialog(mDataTicket.getImgBitmap(), bitmap);
                }
            });

            recyclerViewImages.setHasFixedSize(true);
            recyclerViewImages.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewImages.setItemAnimator(new DefaultItemAnimator());
            recyclerViewImages.setAdapter(mImagesAdapter);
            recyclerViewImages.setVisibility(View.VISIBLE);
            imageViewNoImage.setVisibility(View.GONE);
            mImagesAdapter.notifyDataSetChanged();
        } else {
            recyclerViewImages.setVisibility(View.GONE);
            imageViewNoImage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            //setAdapterForPuranaHisabList();
            if (driverHisabModelList.size() > 0) {
                driverHisabModelList.add(new DriverHisabModel("", "1", photo));
                setAdapterForPuranaHisabList();
            } else {
                driverHisabModelList.add(new DriverHisabModel("", "1", photo));
                setAdapterForPuranaHisabList();
            }
        }
    }


    /*
     * permission result
     * */
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
                    cameraIntent();
                } else {
                    //Toast.makeText(mContext, getString(R.string.somthing_went_wrong), Toast.LENGTH_SHORT).show();
                }
                break;

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
                    if (tag.equalsIgnoreCase("fetch")) {
                        FetchKataChitthiResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), FetchKataChitthiResponse.class);
                        if (responseModel != null /*&& responseModel.getStatus().equals("1")*/) {
                            //LogUtil.printToastMSG(KataChithiActivity.this, responseModel.getMessage());
                            if (responseModel.getResult() != null) {
                                for (int i = 0; i < responseModel.getResult().size(); i++) {
                                    driverHisabModelList.add(new DriverHisabModel(responseModel.getResult().get(i).getUrlPrefix() + responseModel.getResult().get(i).getUrls(), "0", null));
                                }
                            }
                            setAdapterForPuranaHisabList();
                        } else {
                            LogUtil.printToastMSG(KataChithiActivity.this, responseModel.getMessage());
                        }
                    }
                    if (tag.equalsIgnoreCase("save")) {
                        SaveKataChitthiResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), SaveKataChitthiResponse.class);
                        if (responseModel != null && responseModel.getStatus().equals("1")) {
                            //LogUtil.printToastMSG(KataChithiActivity.this, responseModel.getMessage());
                            showSuccessDialog(getString(R.string.msg_weight_submitted));
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 900);
                        } else {
                            LogUtil.printToastMSG(KataChithiActivity.this, responseModel.getMessage());
                        }
                    }
                }
                break;
            case ERROR:
                dismissLoader();
                LogUtil.printToastMSG(KataChithiActivity.this, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }


}