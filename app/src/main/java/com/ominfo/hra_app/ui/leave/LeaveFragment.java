package com.ominfo.hra_app.ui.leave;

import static com.ominfo.hra_app.MainActivity.bottomNavigationView;
import static com.ominfo.hra_app.ui.employees.PaginationListener.PAGE_START;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.ominfo.hra_app.MainActivity;
import com.ominfo.hra_app.R;
import com.ominfo.hra_app.basecontrol.BaseActivity;
import com.ominfo.hra_app.basecontrol.BaseApplication;
import com.ominfo.hra_app.basecontrol.BaseFragment;
import com.ominfo.hra_app.database.AppDatabase;
import com.ominfo.hra_app.interfaces.Constants;
import com.ominfo.hra_app.interfaces.SharedPrefKey;
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.hra_app.ui.employees.PaginationListener;
import com.ominfo.hra_app.ui.leave.adapter.AcceptRejectLeaveAdapter;
import com.ominfo.hra_app.ui.leave.fragment.PastLeaveFragment;
import com.ominfo.hra_app.ui.leave.model.AcceptRejectLeave;
import com.ominfo.hra_app.ui.leave.model.AcceptRejectLeaveListViewModel;
import com.ominfo.hra_app.ui.leave.model.AcceptRejectListRequest;
import com.ominfo.hra_app.ui.leave.model.LeaveAcceptRejectResponse;
import com.ominfo.hra_app.ui.leave.model.LeaveStatusRequest;
import com.ominfo.hra_app.ui.leave.model.LeaveStatusResponse;
import com.ominfo.hra_app.ui.leave.model.LeaveStatusViewModel;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.salary.fragment.SalarySlipFragment;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;
import com.ominfo.hra_app.util.SharedPref;

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
//https://github.com/PhilJay/MPAndroidChart/wiki/Modifying-the-Viewport

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaveFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    Context mContext;
    AcceptRejectLeaveAdapter leaveAdapter;
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
    private AppDatabase mDb;
    int startPos = 0 , endPos = 0;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat emptyLayout;
    @BindView(R.id.iv_emptyLayimage)
    AppCompatImageView iv_emptyLayimage;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    final Calendar myCalendar = Calendar.getInstance();
    @BindView(R.id.tvFilterCount)
    AppCompatTextView tvFilterCount;
    @BindView(R.id.imgReport)
    AppCompatImageView imgReport;
    @Inject
    ViewModelFactory mViewModelFactory;
    private AcceptRejectLeaveListViewModel rejectLeaveListViewModel;
    private LeaveStatusViewModel leaveStatusViewModel;
    List<AcceptRejectLeave> acceptRejectLeaveArrayList = new ArrayList<>();
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private String totalPage = "0";
    private boolean isLoading = false;
    int itemCount = 0;
    AppCompatAutoCompleteTextView AutoComFilterStatus,AutoComFilterName,AutoComFilterDesi;
    AppCompatTextView tvDateValueFrom,tvDateValue;

    public LeaveFragment() {
        // Required empty public constructor
    }

    public static LeaveFragment newInstance(String param1, String param2) {
        LeaveFragment fragment = new LeaveFragment();
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
        View view = inflater.inflate(R.layout.activity_leave, container, false);
        ButterKnife.bind(this, view);
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
        //tv_emptyLayTitle.setText("Search something...");
        emptyLayout.setVisibility(View.VISIBLE);
        imgReport.setVisibility(View.VISIBLE);
        showFilterDialog(1);
        swipeRefresh.setOnRefreshListener(this);
        rvSalesList.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvSalesList.setLayoutManager(layoutManager);

        leaveAdapter = new AcceptRejectLeaveAdapter(mContext,new ArrayList<>(), new AcceptRejectLeaveAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(String status,AcceptRejectLeave employeeList) {
                if(!status.equals("PAST")) {
                    String changeStatus = "";
                    if (status.equals("ACCEPTED")) {
                        changeStatus = "APPROVED";
                    }
                    if (status.equals("REJECTED")) {
                        changeStatus = "REJECTED";
                    }
                    callLeaveStatusApi(employeeList.getId(), changeStatus);
                }else{
                    if(employeeList.getEmpId()!=null) {
                        PastLeaveFragment sheetFragment = new PastLeaveFragment();
                        FragmentManager fragmentManager = ((MainActivity) mContext).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Bundle args = new Bundle();
                        args.putString(Constants.edit, employeeList.getEmpId());
                        args.putString(Constants.FROM_SCREEN, "admin");
                        sheetFragment.setArguments(args);
                        fragmentTransaction.add(R.id.framecontainer, sheetFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }else{LogUtil.printToastMSG(mContext,"Something went wrong, Record not found!");}
                }
            }
        });
        rvSalesList.setAdapter(leaveAdapter);
        //callAcceptRejectListApi("0");

        /**
         * add scroll listener while user reach in bottom load more will call
         */
        rvSalesList.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                callAcceptRejectListApi(String.valueOf(currentPage));
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void injectAPI() {
        rejectLeaveListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AcceptRejectLeaveListViewModel.class);
        rejectLeaveListViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_ACCEPT_REJECT_LIST));

        leaveStatusViewModel = ViewModelProviders.of(this, mViewModelFactory).get(LeaveStatusViewModel.class);
        leaveStatusViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_LEAVE_STATUS));
    }

    //show Filter leave popup
    public void showFilterDialog(int val) {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_filter_leave);
        mDialog.setCanceledOnTouchOutside(true);
        RelativeLayout mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton submitButton = mDialog.findViewById(R.id.submitButton);
        AppCompatButton resetButton = mDialog.findViewById(R.id.resetButton);
        AutoComFilterStatus = mDialog.findViewById(R.id.AutoComFilterStatus);
        AutoComFilterName = mDialog.findViewById(R.id.AutoComFilterName);
        AppCompatImageView imgFromDate = mDialog.findViewById(R.id.imgFromDate);
        AppCompatImageView imgToDate = mDialog.findViewById(R.id.imgToDate);;
        tvDateValueFrom = mDialog.findViewById(R.id.tvDateValueFrom);
        tvDateValue = mDialog.findViewById(R.id.tvDateValue);
        String[] strData = AppUtils.getCurrentDateTime_().split("/");
        String[] getMonth = AppUtils.startEndMonthfromInt(Integer.parseInt(strData[1])).split("~");


            String name = SharedPref.getInstance(mContext).read(SharedPrefKey.FILTER_LEAVE_NAME, "");
            String type = SharedPref.getInstance(mContext).read(SharedPrefKey.FILTER_TYPE, "All");
            String from = SharedPref.getInstance(mContext).read(SharedPrefKey.FILTER_FROM, AppUtils.dateConvertYYYYToDD(getMonth[0]));
            String to = SharedPref.getInstance(mContext).read(SharedPrefKey.FILTER_TO, AppUtils.dateConvertYYYYToDD(getMonth[1]));

            AutoComFilterStatus.setText(type);  setDropdownStatus();
            AutoComFilterName.setText(name);tvDateValueFrom.setText(from);
            tvDateValue.setText(to);

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AutoComFilterStatus.setText("All");  setDropdownStatus();
                AutoComFilterName.setText("");tvDateValueFrom.setText(AppUtils.dateConvertYYYYToDD(getMonth[0]));
                tvDateValue.setText(AppUtils.dateConvertYYYYToDD(getMonth[1]));
                SharedPref.getInstance(mContext).write(SharedPrefKey.FILTER_LEAVE_NAME, AutoComFilterName.getText().toString().trim());
                SharedPref.getInstance(mContext).write(SharedPrefKey.FILTER_TYPE, AutoComFilterStatus.getText().toString().trim());
                //SharedPref.getInstance(mContext).write(SharedPrefKey.FILTER_FROM, tvDateValueFrom.getText().toString().trim());
                //SharedPref.getInstance(mContext).write(SharedPrefKey.FILTER_TO, tvDateValue.getText().toString().trim());
            }
            });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                SharedPref.getInstance(mContext).write(SharedPrefKey.FILTER_LEAVE_NAME, AutoComFilterName.getText().toString().trim());
                SharedPref.getInstance(mContext).write(SharedPrefKey.FILTER_TYPE, AutoComFilterStatus.getText().toString().trim());
                //SharedPref.getInstance(mContext).write(SharedPrefKey.FILTER_FROM, tvDateValueFrom.getText().toString().trim());
                //SharedPref.getInstance(mContext).write(SharedPrefKey.FILTER_TO, tvDateValue.getText().toString().trim());
                itemCount = 0;
                currentPage = PAGE_START;
                isLastPage = false;
                leaveAdapter.clear();
                callAcceptRejectListApi("0");
                //doApiCall();
            }
        });

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        imgFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataPicker(0);
            }
        });
        tvDateValueFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataPicker(0);
            }
        });
        imgToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataPicker(1);
            }
        });
        tvDateValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDataPicker(1);
            }
        });
        if(val==0) {
            mDialog.show();
        }
    }
    //set value to status dropdown
    private void setDropdownStatus() {
        List<String> mGenderDropdown = new ArrayList<>();
        mGenderDropdown.add("All");
        mGenderDropdown.add("Casual leave");
        mGenderDropdown.add("Sick leave");
        mGenderDropdown.add("Other leave");

        try {
            int pos = 0;
            if (mGenderDropdown != null && mGenderDropdown.size() > 0) {
                String[] mDropdownList = new String[mGenderDropdown.size()];
                for (int i = 0; i < mGenderDropdown.size(); i++) {
                    mDropdownList[i] = String.valueOf(mGenderDropdown.get(i));
                }
                //AutoComTextViewVehNo.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mContext,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //AutoComTextViewVehNo.setThreshold(1);
                AutoComFilterStatus.setAdapter(adapter);
                //mSelectedColor = mDropdownList[pos];
                AutoComFilterStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //mSelectedColor = mDropdownList[position];
                        AppUtils.hideKeyBoard(getActivity());
                    }
                });
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Call Api For accept reject record
    private void callAcceptRejectListApi(String pageNo) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_accept_reject_List);
                RequestBody mRequestemp_id = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                RequestBody mRequestcom_id = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                String status = AutoComFilterStatus.getText().toString().equals("All")?"": AutoComFilterStatus.getText().toString();
                RequestBody mRequestleave_type = RequestBody.create(MediaType.parse("text/plain"),status);
                //String startD = AppUtils.changeToSlashToDash(tvDateValueFrom.getText().toString());
                //String endD = AppUtils.changeToSlashToDash(tvDateValue.getText().toString());
                //RequestBody mRequestfrom_date = RequestBody.create(MediaType.parse("text/plain"), startD);
                //RequestBody mRequestend_date = RequestBody.create(MediaType.parse("text/plain"), endD);
                RequestBody mRequestName = RequestBody.create(MediaType.parse("text/plain"),AutoComFilterName.getText().toString());
                RequestBody mRequestpage_number = RequestBody.create(MediaType.parse("text/plain"), pageNo);
                RequestBody mRequestpage_size = RequestBody.create(MediaType.parse("text/plain"), Constants.PAG_SIZE);

                AcceptRejectListRequest request = new AcceptRejectListRequest();
                request.setAction(mRequestAction);
                request.setEmpId(mRequestemp_id);
                request.setSearchedEmp(mRequestName);
                request.setLeaveType(mRequestleave_type);
                //request.setFromDate(mRequestfrom_date);
                //request.setEndDate(mRequestend_date);
                request.setPageNo(mRequestpage_number);
                request.setPageSize(mRequestpage_size);
                request.setCompany_id(mRequestcom_id);

                rejectLeaveListViewModel.hitAcceptRejectLeaveAPI(request);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }


    //Call Api For leave status record
    private void callLeaveStatusApi(String id,String status) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_apply_leave_updated);
                RequestBody mRequestid = RequestBody.create(MediaType.parse("text/plain"),id);
                RequestBody mRequestleave_status = RequestBody.create(MediaType.parse("text/plain"), status);
                RequestBody mRequestupdated_by = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());

                LeaveStatusRequest request = new LeaveStatusRequest();
                request.setAction(mRequestAction);
                request.setId(mRequestid);
                request.setLeaveStatus(mRequestleave_status);
                request.setUpdatedBy(mRequestupdated_by);

                leaveStatusViewModel.hitLeaveStatusAPI(request);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        leaveAdapter.clear();
        callAcceptRejectListApi("0");
    }

    @Override
    public void onPause() {
        super.onPause();
       /* Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.status_bar_color));*/
        /*Window window = getActivity().getWindow();
        View view = window.getDecorView();
        BaseActivity.DarkStatusBar.setLightStatusBar(view,getActivity());*/

    }

    private void setToolbar() {
        //set toolbar title
        LoginTable loginTable = mDb.getDbDAO().getLoginData();
        if (!loginTable.getIsadmin().equals("0")){
            imgReport.setVisibility(View.VISIBLE);
            tvFilterCount.setVisibility(View.VISIBLE);
        }else{imgReport.setVisibility(View.INVISIBLE);
            tvFilterCount.setVisibility(View.INVISIBLE);}
        tvToolbarTitle.setText("Leaves");
        ((BaseActivity)mContext).initToolbar(5, mContext, R.id.imgBack, R.id.imgReport, R.id.imgNotify,tvNotifyCount, R.id.imgBack, R.id.imgCall);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new DashboardFragment();
                ((BaseActivity)mContext).moveFragment(mContext,fragment);
                bottomNavigationView.setSelectedItemId(R.id.home);
            }
        });
        imgNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseActivity)mContext).launchScreen(mContext, NotificationsActivity.class);;
            }
        });
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
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                if(val==0){
                    tvDateValueFrom.setText(sdf.format(myCalendar.getTime()));
                }
                else {
                    tvDateValue.setText(sdf.format(myCalendar.getTime()));
                }
            }

        };

        new DatePickerDialog(mContext, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    //perform click actions
    @OnClick({R.id.tvPastLeave,R.id.imgReport})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.tvPastLeave:
                PastLeaveFragment pastLeaveFragment = new PastLeaveFragment();
                moveFromFragment(pastLeaveFragment,mContext);
                break;
            case R.id.imgReport:
                showFilterDialog(0);
                break;
        }
    }

    private void doApiCall() {
        final ArrayList<AcceptRejectLeave> items = new ArrayList<>();
           /* new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {*/
        for (int i = 0; i < acceptRejectLeaveArrayList.size(); i++) {
            items.add(acceptRejectLeaveArrayList.get(i));
        }
        // do this all stuff on Success of APIs response
        /**
         * manage progress view
         */
        if (currentPage != PAGE_START) leaveAdapter.removeLoading();
        leaveAdapter.addItems(items);
        swipeRefresh.setRefreshing(false);

        // check weather is last page or not
        totalPage = totalPage==null || totalPage.equals("")?"0":totalPage;
        if (currentPage < Long.valueOf(totalPage)) {
            leaveAdapter.addLoading();
        } else {
            isLastPage = true;
        }
        isLoading = false;
        if(leaveAdapter.getItemCount()>1) {
           // leaveAdapter.removeLoading();
        }
        //  }
        // }, 0);
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_ACCEPT_REJECT_LIST)) {
                            LeaveAcceptRejectResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), LeaveAcceptRejectResponse.class);
                            totalPage = responseModel.getResult().getTotalrows();
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                if(responseModel.getResult().getLeave()!=null && responseModel.getResult().getLeave().size()>0) {
                                    acceptRejectLeaveArrayList = responseModel.getResult().getLeave();
                                    emptyLayout.setVisibility(View.GONE);
                                    ((BaseActivity)mContext).initToolbar(5, mContext, R.id.imgBack, R.id.imgReport, R.id.imgNotify,tvNotifyCount, R.id.imgBack, R.id.imgCall);
                                    doApiCall();
                                }
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_LEAVE_STATUS)) {
                            LeaveStatusResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), LeaveStatusResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                showSuccessDialogFragment(mContext,responseModel.getResult().getMessage(),true,null);
                                itemCount = 0;
                                currentPage = PAGE_START;
                                isLastPage = false;
                                leaveAdapter.clear();
                                callAcceptRejectListApi("0");
                            }
                            else{
                                showSuccessDialogFragment(mContext,responseModel.getResult().getMessage(),false,null);
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


    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = PAGE_START;
        isLastPage = false;
        leaveAdapter.clear();
        callAcceptRejectListApi("0");
    }
}