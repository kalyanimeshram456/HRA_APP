package com.ominfo.crm_solution.ui.visit_report.activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ominfo.crm_solution.MainActivity;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.basecontrol.BaseApplication;
import com.ominfo.crm_solution.common.TouchImageView;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.interfaces.Constants;
import com.ominfo.crm_solution.interfaces.SharedPrefKey;
import com.ominfo.crm_solution.network.ApiResponse;
import com.ominfo.crm_solution.network.DynamicAPIPath;
import com.ominfo.crm_solution.network.NetworkCheck;
import com.ominfo.crm_solution.network.ViewModelFactory;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.CustomerList;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmResponse;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.SaveEnquiryResponse;
import com.ominfo.crm_solution.ui.enquiry_report.model.SaveEnquiryViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.SearchCustResponse;
import com.ominfo.crm_solution.ui.enquiry_report.model.SearchCustViewModel;
import com.ominfo.crm_solution.ui.login.LoginActivity;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.visit_report.model.EditVisitRequest;
import com.ominfo.crm_solution.ui.visit_report.model.EditVisitResponse;
import com.ominfo.crm_solution.ui.visit_report.model.EditVisitViewModel;
import com.ominfo.crm_solution.ui.visit_report.model.GetTourResponse;
import com.ominfo.crm_solution.ui.visit_report.model.GetTourStatuslist;
import com.ominfo.crm_solution.ui.visit_report.model.GetTourViewModel;
import com.ominfo.crm_solution.ui.visit_report.model.GetVisitNoViewModel;
import com.ominfo.crm_solution.ui.visit_report.model.PhpEditVisitRequest;
import com.ominfo.crm_solution.ui.visit_report.model.VisitImageModel;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;
import com.ominfo.crm_solution.util.RealPathUtils;
import com.ominfo.crm_solution.util.SharedPref;
import com.ominfo.crm_solution.util.Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UploadVisitActivity extends BaseActivity {

    Context mContext;
    @BindView(R.id.tvTime)
    AppCompatTextView tvTime;
    @BindView(R.id.tvAddLocation)
    AppCompatEditText tvAddLocation;
    @BindView(R.id.tvAddLocationTitle)
    AppCompatEditText tvAddLocationTitle;
    @BindView(R.id.tvVisitNum)
    AppCompatTextView tvVisitNum;
    @BindView(R.id.imgSearchCust)
    AppCompatImageView imgSearchCust;
    @BindView(R.id.AutoComTextViewName)
    AppCompatAutoCompleteTextView AutoComTextViewName;
    @BindView(R.id.AutoComTextViewMobile)
    AppCompatAutoCompleteTextView AutoComTextViewMobile;
    @BindView(R.id.AutoComTextViewDis)
    AppCompatAutoCompleteTextView AutoComTextViewDis;
    @BindView(R.id.AutoComTextViewResult)
    AppCompatAutoCompleteTextView AutoComTextViewResult;
    @BindView(R.id.autoCompSearchView)
    AppCompatAutoCompleteTextView autoCompSearchView;
    @BindView(R.id.AutoComTextViewTour)
    AppCompatAutoCompleteTextView AutoComTextViewTour;
    @BindView(R.id.etDescr)
    AppCompatEditText etDescr;
    @BindView(R.id.endButton)
    AppCompatButton endButton;
    @BindView(R.id.imgCamera)
    AppCompatImageView imgCamera;
    @BindView(R.id.imgCross)
    AppCompatImageView imgCross;
    @BindView(R.id.etPlace)
    AppCompatEditText etPlace;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    @BindView(R.id.input_textTour)
    TextInputLayout input_textTour;
    @BindView(R.id.input_textDis)
    TextInputLayout input_textDis;
    @BindView(R.id.input_textResult)
    TextInputLayout input_textResult;
    @BindView(R.id.input_textMobile)
    TextInputLayout input_textMobile;
    @BindView(R.id.input_textCName)
    TextInputLayout input_textCName;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    private static final int REQUEST_CAMERA = 0;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    @Inject
    ViewModelFactory mViewModelFactory;
    private SearchCustViewModel searchCustViewModel;
    private GetTourViewModel getTourViewModel;
    private EditVisitViewModel editVisitViewModel;
    final Calendar myCalendar = Calendar.getInstance();
    private AppDatabase mDb;
    String mTourId = "";
    private Uri picUri;
    private String tempUri;
    int cam = 0;
    private int downloaded = 0, downloadedCount = 0, requestStatus = 1;
    private int SELECT_FILE = 1;
    VisitImageModel visitImageModel = new VisitImageModel();
    private String base64Path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visit);
        //for full screen toolbar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        ButterKnife.bind(this);
        mContext = this;
        getDeps().inject(this);
        injectAPI();
        init();
        getTimerMillis(mContext);
        cam=2;
        requestPermission();
    }

    public static void setTimerMillis(Context context, long millis)
    {
        SharedPreferences sp = context.getSharedPreferences(Constants.BANK_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit();
        spe.putLong(Constants.BANK_LIST, millis); spe.apply();
    }

    private void init(){
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        setToolbar();
        imgCross.setVisibility(View.GONE);
        setDropdownDiscussion();
        setDropdownResult();
        callTourApi();
        String visit = SharedPref.getInstance(mContext).read(SharedPrefKey.VISIT_NO, "");
        tvVisitNum.setText(" Visit Number : "+visit);
        autoCompSearchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        autoCompSearchView.setRawInputType(InputType.TYPE_CLASS_TEXT);
    }

    private void injectAPI() {
        searchCustViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SearchCustViewModel.class);
        searchCustViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_SEARCH_CUST));

        getTourViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetTourViewModel.class);
        getTourViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_TOUR));

        editVisitViewModel = ViewModelProviders.of(this, mViewModelFactory).get(EditVisitViewModel.class);
        editVisitViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_EDIT_VISIT));
    }

   //set value to Discussion dropdown
    private void setDropdownDiscussion() {
        List<String> mDiscussionDropdown = new ArrayList<>();
        mDiscussionDropdown.add("Cold Visit");
        mDiscussionDropdown.add("Planed Sales Visit");
        mDiscussionDropdown.add("Complaint Solving");
        mDiscussionDropdown.add("General Visit");
        try {
            int pos = 0;
            if (mDiscussionDropdown != null && mDiscussionDropdown.size() > 0) {
                String[] mDropdownList = new String[mDiscussionDropdown.size()];
                for (int i = 0; i < mDiscussionDropdown.size(); i++) {
                    mDropdownList[i] = String.valueOf(mDiscussionDropdown.get(i));
                }
                //AutoComTextViewVehNo.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //AutoComTextViewVehNo.setThreshold(1);
                AutoComTextViewDis.setAdapter(adapter);
                //mSelectedColor = mDropdownList[pos];
                AutoComTextViewDis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //mSelectedColor = mDropdownList[position];
                        //AppUtils.hideSoftKeyboard(UploadVisitActivity.this);
                    }
                });
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double distance(double lat1,
                                  double lat2, double lon1,
                                  double lon2)
    {
        // The math module contains a function
        // named toRadians which converts from
        // degrees to radians.
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2),2);
        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return(c * r); //2.0043678382716137 K.M
    }

    private void deleteImagesFolder() {
        try {
            //private void deleteImagesFolder(){
            File myDir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), Constants.FILE_NAME);
            if (myDir.exists()) {
                myDir.delete();
            }
            // }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            File dir = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), Constants.FILE_NAME);
            //File oldFile = new File(myDir);
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                        1001);

            } else {
                reqPermissionCode();
            }
        } else {
            reqPermissionCode();
        }
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    //show truck details popup
    public void showFullImageDialog(VisitImageModel model) {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_doc_full_view);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.detailsButton);
        TouchImageView imgShow = mDialog.findViewById(R.id.imgShowPhoto);
        AppCompatImageView imgShare = mDialog.findViewById(R.id.imgShare);
        if(model.getImageType()==null){
            imgShow.setImageBitmap(model.getImageBitmap());
        }
        if(model.getImageBitmap()==null){
            imgShow.setImageURI(model.getImageType());
        }
        // if (model.getImageType()==1) {
      /*  try {
           // File imgFile = new File(model.getImagePath());
            //imgShow.setImageURI(Uri.fromFile(imgFile));
            if (imgUrl != null && !imgUrl.equals("") && !imgUrl.equals("null")) {
                imgShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            //sendPhotoToOtherApps(Uri.fromFile(imgFile));
                            //shareToInstant("Shared kata chitthi photo", imgFile, view);
                        } catch (Exception e) {
                        }
                    }
                });
            } else {
                LogUtil.printToastMSG(mContext, "Sorry, Image not available!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.printToastMSG(mContext, "Sorry, Image not available!");
        }*/

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

    private void reqPermissionCode(){
        if(cam==0) {
            cameraIntent();
        }else if(cam==1) {
            galleryIntent();
        }else if(cam==2) {
            deleteImagesFolder();
        }
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

    /*
     * permission result
     * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1001:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                ) {
                    reqPermissionCode();
                } else {
                    //Toast.makeText(mContext, getString(R.string.somthing_went_wrong), Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }

    /*private void onSelectFromGalleryResult(Intent data,String pathFile,int cameraOrGallery) {
        try {
            String actualPath = RealPathUtils.getActualPath(mContext, data.getData());
            kataChitthiImageList.add(new KataChitthiImageModel(actualPath, 1, null));
            setAdapterForPuranaHisabList();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_FILE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String actualPath = RealPathUtils.getActualPath(mContext, data.getData());
                String mBase64 = "";
                if(actualPath!=null){
                 mBase64 = AppUtils.getBase64images(actualPath);
                }
                base64Path = /*"data:image/png;base64," +*/ mBase64;
                visitImageModel.setImagePath(actualPath);
                visitImageModel.setImageType(data.getData());
                visitImageModel.setImageBitmap(null);
                imgCamera.invalidate();
                imgCamera.setImageURI(data.getData());
                imgCross.setVisibility(View.VISIBLE);
            }
        }
        if (requestCode == REQUEST_CAMERA && resultCode == Activity.RESULT_OK) {
            //if (data != null) {
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
           /* }
            else {
                LogUtil.printToastMSG(mContext,"image failed");
            }*/
        }
        if (requestCode == 1000) {
            if(resultCode == Activity.RESULT_OK){
                String locationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ENTERED_VISIT_LAT, "0.0");
                String locationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ENTERED_VISIT_LNG, "0.0");
                String startlocationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.START_VISIT_LAT, "0.0");
                String startlocationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.START_VISIT_LNG, "0.0");
                String endlocationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.END_VISIT_LAT, "0.0");
                String endlocationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.END_VISIT_LNG, "0.0");
                String location = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.LOCATION_ENTERED_TXT, "Not Available");
                String result=data.getStringExtra("result");
                //String str = "<b>"+result+"</b>";
                tvAddLocationTitle.setText(result);
                tvAddLocation.setText(location);
                double km = distance(Double.parseDouble(locationLat),Double.parseDouble(startlocationLat),
                        Double.parseDouble(locationLng),Double.parseDouble(startlocationLng));
                //LogUtil.printToastMSG(mContext,km+" K.M.");
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                // Write your code if there's no result
            }
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
            String pathDb = file.getPath();
            String mBase64 = AppUtils.getBase64images(pathDb);
            base64Path = /*"data:image/png;base64," +*/ mBase64;
            visitImageModel.setImagePath(pathDb);
            visitImageModel.setImageType(null);
            visitImageModel.setImageBitmap(finalBitmap);
            imgCamera.invalidate();
            //set image list adapter
            imgCamera.setImageBitmap(finalBitmap);
            imgCross.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void convertToBase64(String path){

    }

    //set value to Result dropdown
    private void setDropdownResult() {
        List<String> mResultDropdown = new ArrayList<>();
        mResultDropdown.add("Positive");
        mResultDropdown.add("Negative");
        try {
            int pos = 0;
            if (mResultDropdown != null && mResultDropdown.size() > 0) {
                String[] mDropdownList = new String[mResultDropdown.size()];
                for (int i = 0; i < mResultDropdown.size(); i++) {
                    mDropdownList[i] = String.valueOf(mResultDropdown.get(i));
                }
                //AutoComTextViewVehNo.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //AutoComTextViewVehNo.setThreshold(1);
                AutoComTextViewResult.setAdapter(adapter);
                //mSelectedColor = mDropdownList[pos];
                AutoComTextViewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //mSelectedColor = mDropdownList[position];
                        AppUtils.hideKeyBoard(UploadVisitActivity.this);
                    }
                });
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Call Api For Edit Visit */
    private void callEditVisitApi() {
        if (NetworkCheck.isInternetAvailable(this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                String mLocDesc = "",mLocTitle = "";
                String endlocationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.END_VISIT_LAT, "0.0");
                String endlocationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.END_VISIT_LNG, "0.0");

                Geocoder geocoder = new Geocoder(UploadVisitActivity.this);

                try {
                    List<Address> addressList = geocoder.getFromLocation(Double.parseDouble(endlocationLat),
                            Double.parseDouble(endlocationLng), 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);
                        LatLng latLng1 = new LatLng(address.getLatitude(), address.getLongitude());
                        String locality = addressList.get(0).getAddressLine(0);
                        String country = addressList.get(0).getCountryName();
                        mLocTitle = addressList.get(0).getFeatureName();
                        if (!locality.isEmpty() && !country.isEmpty())
                            mLocDesc = locality + "  " + country;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                PhpEditVisitRequest request = new PhpEditVisitRequest();
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_edit_visit);
                RequestBody mRequestBodyResult = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewResult.getText().toString());
                RequestBody mRequestBodyCustMobile = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewMobile.getText().toString());
                RequestBody mRequestBodyCustName = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewName.getText().toString());
                RequestBody mRequestBodyPlace = RequestBody.create(MediaType.parse("text/plain"), etPlace.getText().toString());
                String[] mVisitNo = tvVisitNum.getText().toString().split("Visit Number : ");
                RequestBody mRequestBodyVisitNo = RequestBody.create(MediaType.parse("text/plain"), mVisitNo[1]);
                RequestBody mRequestBodyStopLocationName = RequestBody.create(MediaType.parse("text/plain"), mLocTitle);
                RequestBody mRequestBodyStopLocationAddress = RequestBody.create(MediaType.parse("text/plain"), mLocDesc);
                RequestBody mRequestBodyStopLocationLatitude = RequestBody.create(MediaType.parse("text/plain"), endlocationLat);
                RequestBody mRequestBodyStopLocationLongitute = RequestBody.create(MediaType.parse("text/plain"), endlocationLng);
                RequestBody mRequestBodyRmId = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                RequestBody mRequestBodyTopic = RequestBody.create(MediaType.parse("text/plain"), AutoComTextViewDis.getText().toString());
                RequestBody mRequestBodyTourId = RequestBody.create(MediaType.parse("text/plain"), mTourId);
                RequestBody mRequestBodyDescription = RequestBody.create(MediaType.parse("text/plain"), etDescr.getText().toString());
                RequestBody mRequestBodyVisitDuration = RequestBody.create(MediaType.parse("text/plain"), tvTime.getText().toString());
                RequestBody mRequestBodyVisitingCard = RequestBody.create(MediaType.parse("text/plain"), base64Path);
                String locationLat = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ENTERED_VISIT_LAT, "0.0");
                String locationLng = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.ENTERED_VISIT_LNG, "0.0");
                RequestBody mRequestBodyVisitLocationName = RequestBody.create(MediaType.parse("text/plain"), tvAddLocationTitle.getText().toString());
                RequestBody mRequestBodyVisitLocationAddress = RequestBody.create(MediaType.parse("text/plain"), tvAddLocation.getText().toString());
                RequestBody mRequestBodyVisitLocationLatitude = RequestBody.create(MediaType.parse("text/plain"),locationLat);
                RequestBody mRequestBodyVisitLocationLongitute = RequestBody.create(MediaType.parse("text/plain"),locationLng);
                String endTime = AppUtils.getStartVisitTime();
                RequestBody mRequestBodyVisitTimeEnd = RequestBody.create(MediaType.parse("text/plain"),endTime);
                request.setAction(mRequestBodyAction);
                request.setResult(mRequestBodyResult);
                request.setCustMobile(mRequestBodyCustMobile);
                request.setCustName(mRequestBodyCustName);
                request.setPlace(mRequestBodyPlace);
                request.setVisitNo(mRequestBodyVisitNo);
                request.setStopLocationName(mRequestBodyStopLocationName);
                request.setStopLocationAddress(mRequestBodyStopLocationAddress);
                request.setStopLocationLatitude(mRequestBodyStopLocationLatitude);
                request.setStopLocationLongitute(mRequestBodyStopLocationLongitute);
                request.setRmId(mRequestBodyRmId);
                request.setTopic(mRequestBodyTopic);
                request.setTourId(mRequestBodyTourId);
                request.setDescription(mRequestBodyDescription);
                request.setVisitDuration(mRequestBodyVisitDuration);
                request.setVisitingCard(mRequestBodyVisitingCard);
                request.setVisitLocationName(mRequestBodyVisitLocationName);
                request.setVisitLocationAddress(mRequestBodyVisitLocationAddress);
                request.setVisitLocationLatitude(mRequestBodyVisitLocationLatitude);
                request.setVisitLocationLongitute(mRequestBodyVisitLocationLongitute);
                request.setVisitTimeEnd(mRequestBodyVisitTimeEnd);
                editVisitViewModel.hitEditVisitApi(request);
            }
            else {
                LogUtil.printToastMSG(this, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Search cust */
    private void callSearchCustApi() {
        if (NetworkCheck.isInternetAvailable(this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyTypeAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_search_cust);
                RequestBody mRequestBodyTypeEmpID = RequestBody.create(MediaType.parse("text/plain"), "134");//loginTable.getEmployeeId());//loginTable.getEmployeeId());//loginTable.getEmployeeId());
                RequestBody mRequestBodyTypeCompID = RequestBody.create(MediaType.parse("text/plain"), "96");/*loginTable.getCompanyId());*///loginTable.getCompanyId());
                RequestBody mRequestBodyTypeString = RequestBody.create(MediaType.parse("text/plain"),autoCompSearchView.getEditableText().toString().trim());
                searchCustViewModel.hitSearchCustApi(mRequestBodyTypeAction, mRequestBodyTypeEmpID, mRequestBodyTypeCompID,
                        mRequestBodyTypeString);
            }
            else {
                LogUtil.printToastMSG(this, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(this, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Search cust */
    private void callTourApi() {
        if (NetworkCheck.isInternetAvailable(this)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyTypeAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_tour);
                RequestBody mRequestBodyTypeCompID = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());//loginTable.getCompanyId());
                getTourViewModel.hitGetTourApi(mRequestBodyTypeAction, mRequestBodyTypeCompID);
            }
            else {
                LogUtil.printToastMSG(this, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(this, getString(R.string.err_msg_connection_was_refused));
        }
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


    public long getTimerMillis(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.BANK_LIST, Context.MODE_PRIVATE);
        long milliseconds = sp.getLong(Constants.BANK_LIST, 0);//getTimerMillis(mContext);
        int seconds = (int) (milliseconds / 1000) % 60 ;
        int minutes = (int) ((milliseconds / (1000*60)) % 60);
        int hours   = (int) ((milliseconds / (1000*60*60)) % 24);
        tvTime.setText(String.format("%02d",hours)+":"+String.format("%02d",minutes)+":"+String.format("%02d",seconds));
        //tvTime.setText(DateFormat.format("HH:mm:ss", sp.getLong(Constants.BANK_LIST, 0)));
        setTimerMillis(mContext,0);
        return sp.getLong(Constants.BANK_LIST, 0);
    }

    private void setToolbar() {
        //set toolbar title
        //toolbarTitle.setText(R.string.scr_lbl_add_new_lr);
        initToolbar(6, mContext, R.id.imgBack, R.id.imgReport, R.id.imgReport,tvNotifyCount, 0, R.id.imgCall);
    }


    /*check validations on field*/
    private boolean isEnqDetailsValid() {
        if (TextUtils.isEmpty(AutoComTextViewMobile.getText().toString().trim())) {
            setError(input_textMobile, getString(R.string.val_msg_please_enter_mobile));
            LogUtil.printToastMSG(mContext,getString(R.string.val_msg_please_enter_mobile));
            return false;
        }  else if (TextUtils.isEmpty(AutoComTextViewName.getText().toString().trim())) {
            setError(input_textCName, getString(R.string.val_msg_please_enter_name));
            LogUtil.printToastMSG(mContext,getString(R.string.val_msg_please_enter_name));
            return false;
        }  else if (TextUtils.isEmpty(AutoComTextViewTour.getText().toString().trim())) {
            setError(input_textTour, getString(R.string.val_msg_please_enter_tour));
            LogUtil.printToastMSG(mContext,getString(R.string.val_msg_please_enter_tour));
            return false;
        } else if (TextUtils.isEmpty(AutoComTextViewDis.getText().toString().trim())) {
            setError(input_textDis, "Please select topic of discussion.");
            LogUtil.printToastMSG(mContext,"Please select topic of discussion.");
            return false;
        } else if (TextUtils.isEmpty(AutoComTextViewResult.getText().toString().trim())) {
            setError(input_textResult, "Please select result.");
            LogUtil.printToastMSG(mContext,"Please select result.");
            return false;
        }
        return true;
    }


    //perform click actions
    @OnClick({R.id.endButton,R.id.img_location,R.id.imgSearchCust,R.id.imgCamera,R.id.imgCross})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.endButton:
                if(isEnqDetailsValid()) {
                    callEditVisitApi();
                }
                break;
            case R.id.imgCross:
                visitImageModel.setImageBitmap(null);
                visitImageModel.setImagePath(null);
                visitImageModel.setImageType(null);
                imgCross.setVisibility(View.GONE);
                imgCamera.invalidate();
                imgCamera.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_camera_with_card));
                base64Path = "";
                break;
            case R.id.imgCamera:
                if(visitImageModel.getImagePath()!=null && !visitImageModel.getImagePath().equals("")) {
                    showFullImageDialog(visitImageModel);
                }
                else {
                    showChooseCameraDialog();
                }
                break;
            case R.id.img_location:
                int LAUNCH_SECOND_ACTIVITY = 1000;
                Intent i = new Intent(this, AddLocationActivity.class);
                startActivityForResult(i, LAUNCH_SECOND_ACTIVITY);
                //launchScreen(mContext,AddLocationActivity.class);
                break;
            case R.id.imgSearchCust:
                if(autoCompSearchView.getEditableText().toString().trim()!=null && !autoCompSearchView.getEditableText().toString().trim().equals("")) {
                    callSearchCustApi();
                }
                else {
                    LogUtil.printToastMSG(mContext,"Search filed is empty.");
                }
                break;
        }
    }

    //set value to Search dropdown
    private void setDropdownSearch(List<CustomerList> RMDropdown) {
        try {
            int pos = 0;
            if (RMDropdown != null && RMDropdown.size() > 0) {
                String[] mDropdownList = new String[RMDropdown.size()];
                for (int i = 0; i < RMDropdown.size(); i++) {
                    mDropdownList[i] = String.valueOf(RMDropdown.get(i).getCustomerName()+" ~ "+RMDropdown.get(i).getCustomerMobile());
                   /* if (!VehiIdDropdown.equals("")) {
                        if (RMDropdown.get(i).getId().equals(VehiIdDropdown)) {
                            pos = i;
                        }
                    }*/
                }
                //AutoComTextViewVehNo.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //AutoComTextViewVehNo.setThreshold(1);
                autoCompSearchView.setAdapter(adapter);
                autoCompSearchView.setScrollContainer(true);
                //mSelectedColor = mDropdownList[pos];
                autoCompSearchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //LogUtil.printToastMSG(mContext,"cikmkskm");
                        view.clearFocus();
                        autoCompSearchView.clearFocus();
                        AppUtils.hideKeyBoard(UploadVisitActivity.this);
                        String[] separated = mDropdownList[position].split(" ~ ");
                        try {
                            if (separated.length > 1) {
                                AutoComTextViewName.setText(separated[0]);
                                AutoComTextViewMobile.setText(separated[1]);
                            }
                        }catch (Exception e){e.printStackTrace();}
                        //setDropdownRM();
                    }
                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //set value to Search dropdown
    private void setDropdownTour(List<GetTourStatuslist> RMDropdown) {
        try {
            int pos = 0;
            if (RMDropdown != null && RMDropdown.size() > 0) {
                String[] mDropdownList = new String[RMDropdown.size()];
                for (int i = 0; i < RMDropdown.size(); i++) {
                    mDropdownList[i] = String.valueOf(RMDropdown.get(i).getTourName());
                   /* if (!VehiIdDropdown.equals("")) {
                        if (RMDropdown.get(i).getId().equals(VehiIdDropdown)) {
                            pos = i;
                        }
                    }*/
                }
                //AutoComTextViewVehNo.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        this,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //AutoComTextViewVehNo.setThreshold(1);
                AutoComTextViewTour.setAdapter(adapter);
                //AutoComTextViewTour.setScrollContainer(true);
                //mSelectedColor = mDropdownList[pos];
                AutoComTextViewTour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //LogUtil.printToastMSG(mContext,"cikmkskm");
                        AppUtils.hideKeyBoard(UploadVisitActivity.this);
                        for(int i=0;i<RMDropdown.size();i++){
                            if(RMDropdown.get(i).getTourName().equals(AutoComTextViewTour.getText().toString()))
                            {
                                mTourId = RMDropdown.get(i).getTourId();
                            }
                        }
                    }
                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_SEARCH_CUST)) {
                            SearchCustResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), SearchCustResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                if(responseModel.getResult().getCustomerlist()!=null && responseModel.getResult().getCustomerlist().size()>0) {
                                    setDropdownSearch(responseModel.getResult().getCustomerlist());
                                    autoCompSearchView.showDropDown();
                                }
                                else{
                                    LogUtil.printToastMSG(mContext, "No data available.");
                                }
                            } else {
                                LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();}

                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_TOUR)) {
                            GetTourResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetTourResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                setDropdownTour(responseModel.getResult().getStatuslist());
                                //autoCompSearchView.showDropDown();
                            } else {
                                LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}

                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_EDIT_VISIT)) {
                            //LogUtil.printToastMSG(mContext, "Visit upload failed");
                            EditVisitResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), EditVisitResponse.class);
                            if (responseModel != null ) {
                                if(responseModel.getResult().getStatus().equals("success")) {
                                    String visit = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.VISIT_NO, "");
                                    SharedPref.getInstance(mContext).write(SharedPrefKey.VISIT_NO, "");
                                    showSuccessDialog("Visit Report Uploaded Successfully!", false, UploadVisitActivity.this);
                                }
                                else{
                                    LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                                }
                                } else {
                                LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){
                        LogUtil.printToastMSG(mContext, "Visit upload failed");
                        e.printStackTrace();}
                }
                break;
            case ERROR:
                ((BaseActivity) mContext).dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }


    //set date picker view
    private void openDataPicker(int val , AppCompatTextView datePickerField) {
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
                if(val==1){
                datePickerField.setText("-"+sdf.format(myCalendar.getTime()));
                }
                else {
                    datePickerField.setText(sdf.format(myCalendar.getTime()));
                }
            }

        };

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    @Override
    public void onBackPressed() {
        showVisitCloseAlertDialog(mContext);
        //super.onBackPressed();
    }



}