package com.ominfo.hra_app.ui.salary.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
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
import com.ominfo.hra_app.ui.sales_credit.adapter.EnquiryPageAdapter;
import com.ominfo.hra_app.ui.sales_credit.model.EnquiryPagermodel;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;

import java.io.File;
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
//https://github.com/PhilJay/MPAndroidChart/wiki/Modifying-the-Viewport

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SalarySheetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalarySheetFragment extends BaseFragment {

    Context mContext;
    SalarySheetListAdapter salarySheetListAdapter;
    //AddTagAdapter addTagAdapter;
    @BindView(R.id.rvSalesList)
    RecyclerView rvSalesList;
    @BindView(R.id.imgBack)
    LinearLayoutCompat imgBack;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
    @BindView(R.id.tvTitle)
    AppCompatTextView tvToolbarTitle;
    @BindView(R.id.tv_emptyLayTitle)
    AppCompatTextView tv_emptyLayTitle;
    @BindView(R.id.imgBirthPro)
    CircleImageView imgBirthPro;
    @BindView(R.id.progress_barBirth)
    ProgressBar progress_barBirth;
    @BindView(R.id.tvEmpName)
    AppCompatTextView tvEmpName;
    @BindView(R.id.tvDesi)
    AppCompatTextView tvDesi;
    private AppDatabase mDb;
    List<SalarySheetList> searchresultList = new ArrayList<>();
    @BindView(R.id.tvPage)
    AppCompatTextView tvPage;
    long totalPage = 1;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat emptyLayout;
    @BindView(R.id.iv_emptyLayimage)
    AppCompatImageView iv_emptyLayimage;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    final Calendar myCalendar = Calendar.getInstance();
    @Inject
    ViewModelFactory mViewModelFactory;
    private SalarySheetViewModel salarySheetViewModel;

    private Dialog mDialogChangePass;
    LinearLayoutCompat layoutLeaveTime;
    AppCompatTextView appcomptextLeaveTime;
    View viewToDate;
    RelativeLayout layToDate;
    int cam = 0,noOFDays = 0;
    AppCompatTextView appcomptextNoOfDays;
    AppCompatTextView tvDateValueFrom;
    AppCompatTextView tvDateValueTo,tvTimeValueFrom,tvTimeValueTo ;
    AppCompatAutoCompleteTextView AutoComTextViewComment;
    AppCompatAutoCompleteTextView tvAutoLeaveStatus;
    AppCompatAutoCompleteTextView AutoComTextViewDuration;
    AppCompatAutoCompleteTextView AutoComTextViewLeaveType;
    List<EnquiryPagermodel> enquiryPageList = new ArrayList<>();
    EnquiryPageAdapter enquiryPageAdapter;
    @BindView(R.id.rvEnquiryPager)
    RecyclerView rvEnquiryPager;
    private String pagerClicked = "No";
    String empId = "";
    public SalarySheetFragment() {
        // Required empty public constructor
    }

    public static SalarySheetFragment newInstance(String param1, String param2) {
        SalarySheetFragment fragment = new SalarySheetFragment();
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
        View view = inflater.inflate(R.layout.activity_salary_sheet, container, false);
        ButterKnife.bind(this, view);
        empId = getArguments().getString(Constants.edit);
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
        Glide.with(this)
                .load(R.drawable.img_bg_search)
                .into(iv_emptyLayimage);
        tv_emptyLayTitle.setText(R.string.scr_lbl_no_data_available);
        tv_emptyLayTitle.setText("Search something...");
        setEnquiryPagerList(1);
        setAdapterForLeaveList();
        callSalarySheetListApi("0");
    }

    private void injectAPI() {
        salarySheetViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SalarySheetViewModel.class);
        salarySheetViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_SALARY_SHEET));
   }
    private void setEnquiryPagerList(long pageNo) {
        enquiryPageList.clear();
        if(pageNo==0) {
            pageNo = 1;
        }
        setPagerEnquiryList(pageNo);
        if (enquiryPageList.size() > 0) {
            rvEnquiryPager.setVisibility(View.VISIBLE);
        } else {
            rvEnquiryPager.setVisibility(View.GONE);
        }
        enquiryPageAdapter = new EnquiryPageAdapter(mContext, enquiryPageList, new EnquiryPageAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(EnquiryPagermodel mData, List<EnquiryPagermodel> mDataList) {
                enquiryPageList = mDataList;
                try {
                    pagerClicked = String.valueOf(Integer.parseInt(mData.getPageNo())-1);
                    enquiryPageAdapter.updateList(mDataList);
                }catch (Exception e){e.printStackTrace();}
                try {
                    callSalarySheetListApi(String.valueOf(Integer.parseInt(mData.getPageNo()) - 1));
                }catch (Exception e){e.printStackTrace();}
            }
        });
        rvEnquiryPager.setHasFixedSize(true);
        //rvEnquiryPager.setLayoutManager(new GridLayoutManager(mContext, 3));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
        rvEnquiryPager.setLayoutManager(layoutManager);
        rvEnquiryPager.setItemAnimator(new DefaultItemAnimator());
        rvEnquiryPager.setAdapter(enquiryPageAdapter);
        try{
            rvEnquiryPager.scrollToPosition(Integer.parseInt(pagerClicked));}catch (Exception e){e.printStackTrace();}
        final boolean[] check = {false};
    }

    private void setPagerEnquiryList(long pageNo){
        for(int i=0;i<pageNo;i++) {
            if (pagerClicked.equals("No")) {
                if (i == 0) {
                    rvEnquiryPager.scrollToPosition(i+1);
                    enquiryPageList.add(new EnquiryPagermodel(String.valueOf(i + 1), 1));
                } else {
                    enquiryPageList.add(new EnquiryPagermodel(String.valueOf(i + 1), 0));
                }
            } else {
                if (i == Integer.parseInt(pagerClicked)) {
                    enquiryPageList.add(new EnquiryPagermodel(String.valueOf(i + 1), 1));
                } else {
                    enquiryPageList.add(new EnquiryPagermodel(String.valueOf(i + 1), 0));
                }
            }
        }
    }

    /* Call Api For Leave Applications */
    private void callSalarySheetListApi(String pageNo) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_salary_sheet);
                RequestBody mRequestBodyTypeEmpId = RequestBody.create(MediaType.parse("text/plain"),empId);
                RequestBody mRequestBodyPageNo = RequestBody.create(MediaType.parse("text/plain"), pageNo);
                RequestBody mRequestBodyPageSize = RequestBody.create(MediaType.parse("text/plain"), "8");

                salarySheetViewModel.hitSalarySheetAPI(mRequestBodyAction,mRequestBodyTypeEmpId,
                        mRequestBodyPageNo,mRequestBodyPageSize);
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


    private void setAdapterForLeaveList() {
        if (searchresultList!=null && searchresultList.size() > 0) {
            rvSalesList.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            } else {
            rvSalesList.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(R.drawable.img_bg_search)
                    .into(iv_emptyLayimage);
            tv_emptyLayTitle.setText(R.string.scr_lbl_no_data_available);
        }
        salarySheetListAdapter = new SalarySheetListAdapter(mContext, searchresultList, new SalarySheetListAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(int mDataTicket,SalarySheetList searchresult) {
                SalarySlipFragment salarySlipFragment = new SalarySlipFragment();
               moveFromFragment(salarySlipFragment,mContext);
            }
        });
        //RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecorator(mContext.getDrawable(R.drawable.separator_row_item));
        //rvSalesList.addItemDecoration(dividerItemDecoration);
        //rvSalesList.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(salarySheetListAdapter);
        final boolean[] check = {false};

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
        tvToolbarTitle.setText(R.string.scr_lbl_salary_disbursement);
        ((BaseActivity)mContext).initToolbar(5, mContext, R.id.imgBack, R.id.imgReport, R.id.imgNotify,tvNotifyCount, R.id.imgBack, R.id.imgCall);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new DashboardFragment();
                ((BaseActivity)mContext).moveFragment(mContext,fragment);
            }
        });
        imgNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity)mContext).launchScreen(mContext, NotificationsActivity.class);;
            }
        });
    }

    /*//perform click actions
    @OnClick({R.id.btnSubmit})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnSubmit:
                SalarySheetFragment myFrag = new SalarySheetFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove(myFrag);
                trans.commit();
                manager.popBackStack();
                break;
        }
    }*/
    //show leave form popup
    public void showLeaveFormDialog() {
        mDialogChangePass = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialogChangePass.setContentView(R.layout.dialog_add_leave_form);
        mDialogChangePass.setCanceledOnTouchOutside(true);
        AutoComTextViewDuration = mDialogChangePass.findViewById(R.id.AutoComTextViewDuration);
        AutoComTextViewLeaveType = mDialogChangePass.findViewById(R.id.AutoComTextViewLeaveType);
        AppCompatImageView mClose = mDialogChangePass.findViewById(R.id.imgCancel);
        AppCompatButton addReceiptButton = mDialogChangePass.findViewById(R.id.addReceiptButton);
        viewToDate = mDialogChangePass.findViewById(R.id.view);
        layToDate = mDialogChangePass.findViewById(R.id.layToDate);
        layoutLeaveTime = mDialogChangePass.findViewById(R.id.layoutLeaveTime);
        appcomptextLeaveTime = mDialogChangePass.findViewById(R.id.appcomptextLeaveTime);
        appcomptextNoOfDays = mDialogChangePass.findViewById(R.id.appcomptextNoOfDays);
        AppCompatImageView imgFromDate  = mDialogChangePass.findViewById(R.id.imgFromDate);
        AppCompatImageView imgToDate  = mDialogChangePass.findViewById(R.id.imgToDate);
        ImageView imgFromTime  = mDialogChangePass.findViewById(R.id.imgToTime);
        ImageView imgToTime  = mDialogChangePass.findViewById(R.id.imgTime);
        tvDateValueFrom = mDialogChangePass.findViewById(R.id.tvDateValueFrom);
        tvDateValueTo = mDialogChangePass.findViewById(R.id.tvDateValue);
        tvTimeValueFrom = mDialogChangePass.findViewById(R.id.tvTimeValueFrom);
        tvTimeValueTo = mDialogChangePass.findViewById(R.id.tvTimeValue);
        AutoComTextViewComment = mDialogChangePass.findViewById(R.id.AutoComTextViewComment);
        TextInputLayout input_textDuration = mDialogChangePass.findViewById(R.id.input_textDuration);
        TextInputLayout input_textType = mDialogChangePass.findViewById(R.id.input_textType);
        layoutLeaveTime.setVisibility(View.GONE);
        appcomptextLeaveTime.setVisibility(View.GONE);
        String mDate = AppUtils.getCurrentDateTime_();//SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.KATA_CHITTI_DATE, AppUtils.getCurrentDateTime_());
        tvDateValueFrom.setText(mDate);
        tvDateValueTo.setText(mDate);
        String mTime = AppUtils.getCurrentTime();
        tvTimeValueFrom.setText(mTime);
        tvTimeValueTo.setText(mTime);
        int diff = AppUtils.getChangeDateForHisab(tvDateValueFrom.getText().toString(),tvDateValueTo.getText().toString());
        appcomptextNoOfDays.setText("Number of days : "+diff +" Days");
        noOFDays = diff;
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogChangePass.dismiss();
            }
        });
        setDropdownLeaveDuration(AutoComTextViewDuration);
        setDropdownType(AutoComTextViewLeaveType);
        tvDateValueFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataPicker(1);
            }
        });
        imgFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataPicker(1);
            }
        });
        tvDateValueTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataPicker(0);
            }
        });imgToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataPicker(0);
            }
        });
        tvTimeValueFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenTimePicker(1);
            }
        });imgFromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenTimePicker(1);
            }
        });
        tvTimeValueTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenTimePicker(0);
            }
        });imgToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenTimePicker(0);
            }
        });

        addReceiptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogChangePass.dismiss();
                /*if(isAttendanceDetailsValid(AutoComTextViewDuration,input_textDuration,AutoComTextViewLeaveType,input_textType
                )){
                   // callApplyLeaveApi();
                }*/

            }
        });
        mDialogChangePass.show();
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_SALARY_SHEET)) {
                            SalarySheetResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), SalarySheetResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                searchresultList.clear();
                                tvEmpName.setText(responseModel.getResult().getName());
                                tvDesi.setText(responseModel.getResult().getPosition());
                                AppUtils.loadImageURL(mContext,responseModel.getResult().getProfilePic(),
                                        imgBirthPro,progress_barBirth);
                                try {
                                    if (responseModel.getResult().getList() != null && responseModel.getResult().getList().size()>0) {
                                        searchresultList = responseModel.getResult().getList();
                                        totalPage = responseModel.getResult().getTotalpages();
                                        if(responseModel.getResult().getNextpage()==1) {
                                            tvPage.setText("Showing " + String.valueOf(responseModel.getResult().getNextpage()) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1) + searchresultList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalrows()) + "\nEntries"));
                                        }
                                        else {
                                            tvPage.setText("Showing " + String.valueOf(((responseModel.getResult().getNextpage()-1)*8)+1) + " to " +
                                                    String.valueOf(((responseModel.getResult().getNextpage()-1)*8)+ searchresultList.size()) + " of " + String.valueOf(responseModel.getResult().getTotalrows()) + "\nEntries");
                                        }
                                    }
                                    else{
                                        totalPage = 0;
                                        searchresultList.clear();
                                    }
                                }catch(Exception e){
                                    totalPage = 0;
                                    searchresultList.clear();
                                    e.printStackTrace();

                                }
                                setEnquiryPagerList(totalPage);
                                setAdapterForLeaveList();
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