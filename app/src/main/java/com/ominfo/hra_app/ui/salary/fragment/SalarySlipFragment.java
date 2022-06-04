package com.ominfo.hra_app.ui.salary.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

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
    @BindView(R.id.tvSalaryValue)
    AppCompatTextView tvSalaryValue;
    @BindView(R.id.tvAddValue)
    AppCompatAutoCompleteTextView tvAddValue;
    @BindView(R.id.tvDedValue)
    AppCompatAutoCompleteTextView tvDedValue;
    @BindView(R.id.tvTotalValue)
    AppCompatAutoCompleteTextView tvTotalValue;
    @BindView(R.id.imgBirthPro)
    CircleImageView imgBirthPro;
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
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/*");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Star App");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        mContext.startActivity(Intent.createChooser(shareIntent, "hello hello"));
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


    //show Receipt Details popup
    public void showVisitDetailsDialog() {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.activity_reminder_alert);
        mDialog.setCanceledOnTouchOutside(true);
        //AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        //AppCompatButton closeButton = mDialog.findViewById(R.id.closeButton);

        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);

        /*closeButton.setOnClickListener(new View.OnClickListener() {
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
        });*/
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
                Bitmap bitmap = screenShot(contentPrint);
                share(bitmap);
                break;
            case R.id.imgEdit:
                imgEdit.setVisibility(View.VISIBLE);
                tvAddValue.setEnabled(true);
                tvDedValue.setEnabled(true);
                tvTotalValue.setEnabled(true);
        }
    }

    //set value to Search dropdown
    private void setDropdownLeaveDuration(AppCompatAutoCompleteTextView mListDropdownView) {
        List<String> leaveModelList = new ArrayList<>();
        leaveModelList.add("Half Day");
        leaveModelList.add("Full Day");
        leaveModelList.add("Multiple Days");
        try {
            int pos = 0;
            if (leaveModelList != null && leaveModelList.size() > 0) {
                String[] mDropdownList = new String[leaveModelList.size()];
                for (int i = 0; i < leaveModelList.size(); i++) {
                    mDropdownList[i] = String.valueOf(leaveModelList.get(i));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mContext,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //tvHighlight.setThreshold(1);
                mListDropdownView.setAdapter(adapter);
                //mListDropdownView.setHint(mDropdownList[pos]);
                mListDropdownView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(mListDropdownView.getText().toString().equals("Half Day")){
                            layoutLeaveTime.setVisibility(View.VISIBLE);
                            appcomptextLeaveTime.setVisibility(View.VISIBLE);
                            viewToDate.setVisibility(View.GONE);
                            layToDate.setVisibility(View.GONE);
                            appcomptextNoOfDays.setText("Number of days : Half Day");
                            noOFDays = 0;
                        }
                        else if(mListDropdownView.getText().toString().equals("Full Day")){
                            viewToDate.setVisibility(View.GONE);
                            layToDate.setVisibility(View.GONE);
                            layoutLeaveTime.setVisibility(View.GONE);
                            appcomptextLeaveTime.setVisibility(View.GONE);
                            appcomptextNoOfDays.setText("Number of days : 01 Days");
                            noOFDays = 1;
                        }
                        else{
                            viewToDate.setVisibility(View.VISIBLE);
                            layToDate.setVisibility(View.VISIBLE);
                            layoutLeaveTime.setVisibility(View.GONE);
                            appcomptextLeaveTime.setVisibility(View.GONE);
                            int diff = AppUtils.getChangeDateForHisab(tvDateValueFrom.getText().toString(),tvDateValueTo.getText().toString());
                            appcomptextNoOfDays.setText("Number of days : "+diff +" Days");
                            noOFDays = diff;
                        }
                    }
                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //set value to Search dropdown
    private void setDropdownType(AppCompatAutoCompleteTextView mListDropdownView) {
        List<String> leaveModelList = new ArrayList<>();
        leaveModelList.add("Casual Leave");
        leaveModelList.add("Sick Leave");
        //leaveModelList.add("Multiple Days");
        try {
            int pos = 0;
            if (leaveModelList != null && leaveModelList.size() > 0) {
                String[] mDropdownList = new String[leaveModelList.size()];
                for (int i = 0; i < leaveModelList.size(); i++) {
                    mDropdownList[i] = String.valueOf(leaveModelList.get(i));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mContext,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //tvHighlight.setThreshold(1);
                mListDropdownView.setAdapter(adapter);
                //mListDropdownView.setHint(mDropdownList[pos]);
                mListDropdownView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                });

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void OpenTimePicker(int val){
        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String am_pm = "";
                myCalendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                myCalendar.set(Calendar.MINUTE, selectedMinute);
                if (myCalendar.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "am";
                else if (myCalendar.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "pm";
                String strHrsToShow = (String.valueOf(myCalendar.get(Calendar.HOUR)).length() == 1) ? "0"+myCalendar.get(Calendar.HOUR) : myCalendar.get(Calendar.HOUR) + "";
                //UIHelper.showLongToastInCenter(context, strHrsToShow + ":" + myCalendar.get(Calendar.MINUTE) + " " + am_pm);
                //String min = convertDate(myCalendar.get(Calendar.MINUTE));
                boolean isPM = (selectedHour >= 12);
                if(val==0) {
                    tvTimeValueTo.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, myCalendar.get(Calendar.MINUTE), isPM ? "pm" : "am"));
                }
                else{
                    tvTimeValueFrom.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, myCalendar.get(Calendar.MINUTE), isPM ? "pm" : "am"));

                }
                // AutoComTextViewTime.setText(strHrsToShow + ":" + min + " " + am_pm);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    /*check validations on field*/
    private boolean isDetailsValid(AppCompatAutoCompleteTextView oldPass,
                                   TextInputLayout input_textOldPass,
                                   AppCompatAutoCompleteTextView newPass,
                                   TextInputLayout input_textNewPass ,
                                   AppCompatAutoCompleteTextView ConfPass,
                                   TextInputLayout AutoComConfirmPass
    ) {
        setError(input_textOldPass,"");setError(input_textNewPass, "");
        setError(AutoComConfirmPass, "");
        if (TextUtils.isEmpty(oldPass.getText().toString().trim())) {
            setError(input_textOldPass, getString(R.string.err_msg_old_pass));
            return false;
        } else if (TextUtils.isEmpty(newPass.getText().toString().trim())) {
            setError(input_textNewPass, getString(R.string.err_msg_new_password));
            return false;
        } else if (TextUtils.isEmpty(ConfPass.getText().toString().trim())) {
            setError(AutoComConfirmPass, getString(R.string.err_msg_confirm_password));
            return false;
        } else if (!newPass.getText().toString().trim().equals(ConfPass.getText().toString().trim())) {
            setError(AutoComConfirmPass, getString(R.string.err_msg_wrong_confirm_pass));
            return false;
        }
        return true;
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


}