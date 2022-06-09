package com.ominfo.hra_app.ui.salary.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.basecontrol.BaseFragment;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.interfaces.Constants;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.salary.adapter.SalarySheetListAdapter;
import com.ominfo.hra_app.ui.salary.model.SalarySheetList;
import com.ominfo.hra_app.ui.salary.model.SalarySheetResponse;
import com.ominfo.hra_app.ui.salary.model.SalarySheetViewModel;
import com.ominfo.hra_app.ui.salary.model.SalarySlipResponse;
import com.ominfo.hra_app.ui.salary.model.SalarySlipViewModel;
import com.ominfo.hra_app.ui.sales_credit.adapter.EnquiryPageAdapter;
import com.ominfo.hra_app.ui.sales_credit.model.EnquiryPagermodel;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
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
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
//https://github.com/PhilJay/MPAndroidChart/wiki/Modifying-the-Viewport

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SalarySlipFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalarySlipFragment extends BaseFragment {

    Context mContext;
    @BindView(R.id.imgBack)
    LinearLayoutCompat imgBack;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvToolbarTitle;
    @BindView(R.id.tvCompanyName)
    AppCompatTextView tvCompanyName;
    @BindView(R.id.tvEmpName)
    AppCompatTextView tvEmpName;
    @BindView(R.id.tvMonth)
    AppCompatTextView tvMonth;
    @BindView(R.id.tvDesiValue)
    AppCompatTextView tvDesiValue;
    @BindView(R.id.tvPaymentDate)
    AppCompatTextView tvPaymentDate;
    @BindView(R.id.tvNoOfLeavesValue)
    AppCompatTextView tvNoOfLeavesValue;
    @BindView(R.id.addSubmitButton)
    AppCompatButton addSubmitButton;
    @BindView(R.id.tvSalaryValue)
    AppCompatTextView tvSalaryValue;
    @BindView(R.id.tvAddValue)
    AppCompatAutoCompleteTextView tvAddValue;
    @BindView(R.id.tvDedValue)
    AppCompatAutoCompleteTextView tvDedValue;
    @BindView(R.id.tvTotalValue)
    AppCompatAutoCompleteTextView tvTotalValue;
    @BindView(R.id.imgBirthPro)
    AppCompatImageView imgBirthPro;
    @BindView(R.id.progress_barBirth)
    ProgressBar progress_barBirth;
    @BindView(R.id.imgEdit)
    AppCompatImageView imgEdit;
    private AppDatabase mDb;
    final Calendar myCalendar = Calendar.getInstance();
    @Inject
    ViewModelFactory mViewModelFactory;
    private SalarySlipViewModel salarySlipViewModel;
    private Dialog mDialogChangePass;
    LinearLayoutCompat layoutLeaveTime;
    AppCompatTextView appcomptextLeaveTime;
    View viewToDate;
    RelativeLayout layToDate;
    int cam = 0,noOFDays = 0;
    AppCompatTextView appcomptextNoOfDays;
    AppCompatTextView tvDateValueFrom;
    AppCompatTextView tvDateValueTo,tvTimeValueFrom,tvTimeValueTo ;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    @BindView(R.id.contentPrint)
    RelativeLayout contentPrint;
    String empId = "0";
    double salary = 0.0;
    Bitmap bitmap;
    public static final int REQUEST_PERMISSIONS = 1;
    boolean boolean_permission;
    boolean boolean_save = false,boolean_downled = false,boolean_edit = false;

    public SalarySlipFragment() {
        // Required empty public constructor
    }

    public static SalarySlipFragment newInstance(String param1, String param2) {
        SalarySlipFragment fragment = new SalarySlipFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_salary_slip, container, false);
        ButterKnife.bind(this, view);
        empId = getArguments().getString(Constants.edit);
        //salary = getArguments().getString(Constants.MESSAGE);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((BaseActivity)mContext).getDeps().inject(this);
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        injectAPI();
        init();
    }

    private void init(){
        setToolbar();
        callSalarySheetListApi();
        tvAddValue.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                //if(s.length() != 0) {
                Double totalSum = salary - Double.parseDouble(tvDedValue.getText().toString()
                        .equals("")
                                ||tvDedValue.getText().toString()==null?"0":tvDedValue.getText().toString()) + Double.parseDouble(s.toString().equals("")
                        ||s.toString()==null?"0":s.toString());
                tvTotalValue.setText(totalSum+"");
            }
        });
        tvDedValue.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                //if(s.length() != 0) {
                Double totalSum = salary + Double.parseDouble(tvAddValue.getText().toString().equals("")
                        ||tvAddValue.getText().toString()==null?"0":tvAddValue.getText().toString()) - Double.parseDouble(s.toString().equals("")
                        ||s.toString()==null?"0":s.toString());
                tvTotalValue.setText(totalSum+"");
              /*  }
                else{

                }*/
            }
        });
    }

    private void injectAPI() {
        salarySlipViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SalarySlipViewModel.class);
        salarySlipViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_SALARY_SLIP));
   }
    private Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void share(Bitmap bitmap){
        String pathofBmp=
                MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                        bitmap,"title", null);
        Uri uri = Uri.parse(pathofBmp);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_VIEW);
        shareIntent.setDataAndType(uri, "image/*");
        startActivity(shareIntent);
    }
    /* Call Api For Leave Applications */
    private void callSalarySheetListApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                if(loginTable.getIsadmin().equals("0")){
                    imgEdit.setVisibility(View.GONE);
                    tvAddValue.setEnabled(false);
                    tvDedValue.setEnabled(false);
                    tvTotalValue.setEnabled(false);
                }else{
                    imgEdit.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_edit_black));
                    imgEdit.setVisibility(View.VISIBLE);
                    tvAddValue.setEnabled(false);
                    tvDedValue.setEnabled(false);
                    tvTotalValue.setEnabled(false);
                }
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_emp_sal_slip);
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"),empId);

                salarySlipViewModel.hitSalarySlipAPI(mRequestBodyAction,mRequestBodyTypeEmpId);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    //show salary disbursment popup
    public void showSalaryDisbursmentDialog() {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_salary_disburse);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton downloadButton = mDialog.findViewById(R.id.addSubmitButton);
        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);

        downloadButton.setOnClickListener(new View.OnClickListener() {
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


    private void setToolbar() {
        //set toolbar title
        tvToolbarTitle.setText(R.string.scr_lbl_salary_slip);
        ((BaseActivity)mContext).initToolbar(5, mContext, R.id.imgBack, R.id.imgReport, R.id.imgNotify,tvNotifyCount, R.id.imgBack, R.id.imgCall);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SalarySlipFragment myFrag = new SalarySlipFragment();
                removeFragment(myFrag);
            }
        });
        imgNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity)mContext).launchScreen(mContext, NotificationsActivity.class);;
            }
        });
    }

    //perform click actions
    @OnClick({R.id.addSubmitButton,R.id.imgEdit})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.addSubmitButton:
                if(!boolean_downled) {
                   // addSubmitButton.setText("Downloading...");
                    addSubmitButton.setVisibility(View.GONE);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            boolean_downled= true;
                            bitmap = screenShot(contentPrint);
                            LogUtil.printToastMSG(mContext, "Salary slip saved to picture folder successfully.");
                            addSubmitButton.setVisibility(View.VISIBLE);
                            addSubmitButton.setText("View");
                        }
                    }, 700);
                }else{
                    boolean_downled= false;
                    share(bitmap);
                    addSubmitButton.setText(getString(R.string.scr_lbl_download));
                }
                break;
            case R.id.imgEdit:
                if(!boolean_edit) {
                    boolean_edit = true;
                    imgEdit.setVisibility(View.VISIBLE);
                    imgEdit.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_close));
                    tvTotalValue.setEnabled(true);
                    tvTotalValue.setFocusable(true);
                    tvTotalValue.setFocusableInTouchMode(true);
                    tvTotalValue.requestFocus();
                    tvDedValue.setEnabled(true);
                    tvDedValue.setFocusable(true);
                    tvDedValue.setFocusableInTouchMode(true);
                    tvDedValue.requestFocus();
                    tvAddValue.setEnabled(true);
                    tvAddValue.setFocusable(true);
                    tvAddValue.setFocusableInTouchMode(true);
                    tvAddValue.requestFocus();
                }else{
                    imgEdit.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_edit_black));
                    boolean_edit = false;
                    tvAddValue.setEnabled(false);
                    tvDedValue.setEnabled(false);
                    tvTotalValue.setEnabled(false);
                }
        }
    }

    //set date picker view
    private void openDataPicker(int val) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                if(val==0){
                    tvDateValueTo.setText(sdf.format(myCalendar.getTime()));
                }
                else {
                    tvDateValueFrom.setText(sdf.format(myCalendar.getTime()));
                }
                int diff = AppUtils.getChangeDateForHisab(tvDateValueFrom.getText().toString(),tvDateValueTo.getText().toString());
                appcomptextNoOfDays.setText("Number of days : "+diff +" Days");
                noOFDays = diff;
            }

        };
        /*  new DatePickerDialog(mContext, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();*/

        DatePickerDialog dpDialog = new DatePickerDialog(mContext, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
       /* if(val==0) {
            String dateRestrict = AppUtils.changeDateHisab(tvDateValueTo.getText().toString());
            dpDialog.getDatePicker().setMaxDate(getChangeDateForHisab(dateRestrict));
        }
        else {
            String dateRestrict = AppUtils.changeDateHisab(tvDateValueFrom.getText().toString());
            dpDialog.getDatePicker().setMinDate(getChangeDateForHisab(dateRestrict));
        }*/
        dpDialog.show();

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
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

           /* if (requestCode == REQUEST_PERMISSIONS) {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    boolean_permission = true;

                } else {
                    Toast.makeText(mContext, "Please allow the permission", Toast.LENGTH_LONG).show();

                }
            }*/
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_SALARY_SLIP)) {
                            SalarySlipResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), SalarySlipResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                AppUtils.loadImageURL(mContext,"https://ominfo.in/o_hr/"+responseModel.getResult().getList().get(0).getLogoUrl(),
                                        imgBirthPro,progress_barBirth);
                                tvCompanyName.setText(responseModel.getResult().getList().get(0).getName());
                                tvEmpName.setText(responseModel.getResult().getList().get(0).getEmpName());
                                tvDesiValue.setText(responseModel.getResult().getList().get(0).getEmpPosition());
                                tvPaymentDate.setText(AppUtils.convertyyyytodd(responseModel.getResult().getList().get(0).getCreatedOn()));
                                tvMonth.setText("Payslip of "+AppUtils.convertIntToMonth(Integer.parseInt(responseModel.getResult().getList().get(0).getMonth())));
                                tvNoOfLeavesValue.setText(responseModel.getResult().getList().get(0).getLeaves());
                                tvSalaryValue.setText(responseModel.getResult().getList().get(0).getSalary());
                                salary = Double.parseDouble(tvSalaryValue.getText().toString());
                                tvAddValue.setText(responseModel.getResult().getList().get(0).getAddition());
                                tvDedValue.setText(responseModel.getResult().getList().get(0).getDeduction());
                                tvTotalValue.setText(responseModel.getResult().getList().get(0).getTotal());
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                break;
            case ERROR:
                ((BaseActivity) mContext).dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }

    private void createPDFWithMultipleImage(){
        File myDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        myDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.US).format(new Date());
        String fname = "Image_" + timeStamp + "_capture.pdf";
        File file = new File(myDir, fname);
        if (file != null){
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                PdfDocument pdfDocument = new PdfDocument();

                PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), (0 + 1)).create();
                PdfDocument.Page page = pdfDocument.startPage(pageInfo);
                Canvas canvas = page.getCanvas();
                Paint paint = new Paint();
                paint.setColor(Color.BLUE);
                canvas.drawPaint(paint);
                canvas.drawBitmap(bitmap, 0f, 0f, null);
                pdfDocument.finishPage(page);
                bitmap.recycle();

                pdfDocument.writeTo(fileOutputStream);
                pdfDocument.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void createPdf(){
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

        //        Resources mResources = getResources();
       //        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.screenshot);

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);



        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);


        // write the document content
        File myDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        myDir.mkdirs();
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.US).format(new Date());
        String fname = "Image_" + timeStamp + "_capture.pdf";
        File file = new File(myDir, fname);
       // String targetPdf = "/sdcard/test.pdf";
        File filePath = new File(file.getPath());
        try {
            document.writeTo(new FileOutputStream(filePath));
            //btn_convert.setText("Check PDF");
            LogUtil.printToastMSG(mContext,"Check PDF"+file.getPath());
            boolean_save=true;
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();
    }


}