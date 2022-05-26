package com.ominfo.hra_app.ui.leave.fragment;

import static com.ominfo.hra_app.ui.employees.PaginationListener.PAGE_START;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.ominfo.hra_app.ui.employees.PaginationListener;
import com.ominfo.hra_app.ui.leave.adapter.EmployeeLeaveListAdapter;
import com.ominfo.hra_app.ui.leave.model.AcceptRejectLeave;
import com.ominfo.hra_app.ui.leave.model.AcceptRejectLeaveListViewModel;
import com.ominfo.hra_app.ui.leave.model.AcceptRejectListRequest;
import com.ominfo.hra_app.ui.leave.model.ActiveEmployeeListEmpDatum;
import com.ominfo.hra_app.ui.leave.model.ActiveEmployeeListViewModel;
import com.ominfo.hra_app.ui.leave.model.EmployeeLeaveMonthsList;
import com.ominfo.hra_app.ui.leave.model.LeaveAcceptRejectResponse;
import com.ominfo.hra_app.ui.leave.model.PastLeaveListViewModel;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.my_account.model.ApplyLeaveViewModel;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.sales_credit.model.EnquiryPagermodel;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmployeeLeaveListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmployeeLeaveListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    Context mContext;
    EmployeeLeaveListAdapter leaveAdapter;
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
    List<AcceptRejectLeave> acceptRejectLeaveArrayList = new ArrayList<>();
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
    private PastLeaveListViewModel pastLeaveListViewModel;
    private ActiveEmployeeListViewModel activeEmployeeListViewModel;
    List<EnquiryPagermodel> enquiryPageList = new ArrayList<>();
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    private Dialog mDialogChangePass;
    LinearLayoutCompat layoutLeaveTime;
    AppCompatTextView appcomptextLeaveTime;
    View viewToDate;
    RelativeLayout layToDate;
    int cam = 0,noOFDays = 0;
    private String pagerClicked = "No", startDateAccept = "", endDateAccept = "";
    AppCompatTextView appcomptextNoOfDays;
    AppCompatTextView tvDateValueTo,tvTimeValueFrom,tvTimeValueTo ;
    AppCompatAutoCompleteTextView AutoComTextViewComment;
    AppCompatAutoCompleteTextView tvAutoLeaveStatus;
    AppCompatAutoCompleteTextView AutoComTextViewDuration;
    AppCompatAutoCompleteTextView AutoComTextViewLeaveType;
    private ApplyLeaveViewModel applyLeaveViewModel;
    @BindView(R.id.AutoComMonth)
    AppCompatAutoCompleteTextView AutoComMonth;
    List<ActiveEmployeeListEmpDatum> activeEmployeeList = new ArrayList<>();
    String selectedActiveEmpid = "0";
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private long totalPage = 0;
    private boolean isLoading = false;
    int itemCount = 0;
    private AcceptRejectLeaveListViewModel rejectLeaveListViewModel;

    public EmployeeLeaveListFragment() {
        // Required empty public constructor
    }

    public static EmployeeLeaveListFragment newInstance(String param1, String param2) {
        EmployeeLeaveListFragment fragment = new EmployeeLeaveListFragment();
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
        View view = inflater.inflate(R.layout.activity_employee_leave_list, container, false);
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
        tv_emptyLayTitle.setText("Search something...");
        swipeRefresh.setOnRefreshListener(this);
        setDropdownMonth();
        rvSalesList.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        rvSalesList.setLayoutManager(layoutManager);

        leaveAdapter = new EmployeeLeaveListAdapter(mContext,new ArrayList<>(), new EmployeeLeaveListAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(String mData, AcceptRejectLeave searchresult) {

            }
        });
        rvSalesList.setAdapter(leaveAdapter);
        callAcceptRejectListApi("0");

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
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void setToolbar() {
        //set toolbar title
        tvToolbarTitle.setText(R.string.scr_lbl_past_leaves);
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

    //perform click actions
    @OnClick({R.id.layPast})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.layPast:
                PastLeaveFragment pastLeaveFragment = new PastLeaveFragment();
                moveFromFragment(pastLeaveFragment,mContext);
                break;
        }
    }

    //Call Api For accept reject record
    private void callAcceptRejectListApi(String pageNo) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_accept_reject_List);
                RequestBody mRequestemp_id = RequestBody.create(MediaType.parse("text/plain"),loginTable.getEmployeeId());
                RequestBody mRequestleave_type = RequestBody.create(MediaType.parse("text/plain"),"");
                String monthNumber  =  AppUtils.convertMonthToInt(AutoComMonth.getText().toString().trim());
                String[] mon = AppUtils.startEndMonthfromInt(Integer.parseInt(monthNumber)).split("~");
                RequestBody mRequestfrom_date = RequestBody.create(MediaType.parse("text/plain"), mon[0]);
                RequestBody mRequestend_date = RequestBody.create(MediaType.parse("text/plain"), mon[1]);
                RequestBody mRequestName = RequestBody.create(MediaType.parse("text/plain"),loginTable.getName());
                RequestBody mRequestpage_number = RequestBody.create(MediaType.parse("text/plain"), pageNo);
                RequestBody mRequestpage_size = RequestBody.create(MediaType.parse("text/plain"), Constants.PAG_SIZE);

                AcceptRejectListRequest request = new AcceptRejectListRequest();
                request.setAction(mRequestAction);
                request.setEmpId(mRequestemp_id);
                request.setSearchedEmp(mRequestName);
                request.setLeaveType(mRequestleave_type);
                request.setFromDate(mRequestfrom_date);
                request.setEndDate(mRequestend_date);
                request.setPageNo(mRequestpage_number);
                request.setPageSize(mRequestpage_size);

                rejectLeaveListViewModel.hitAcceptRejectLeaveAPI(request);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    //set value to month dropdown
    private void setDropdownMonth() {
        List<EmployeeLeaveMonthsList> monthsLists = new ArrayList<>();
        monthsLists.add(new EmployeeLeaveMonthsList("January","31 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("February","28 days in a common year and 29 days in leap years"));
        monthsLists.add(new EmployeeLeaveMonthsList("March","31 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("April","30 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("May","31 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("June","30 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("July","31 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("August","31 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("September","30 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("October","31 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("November","30 days"));
        monthsLists.add(new EmployeeLeaveMonthsList("December","31 days"));

        try {
            int pos = 0;
            if (monthsLists != null && monthsLists.size() > 0) {
                String[] mDropdownList = new String[monthsLists.size()];
                for (int i = 0; i < monthsLists.size(); i++) {
                    mDropdownList[i] = String.valueOf(monthsLists.get(i).getName());
                    //pos = i;
                }
                AutoComMonth.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mContext,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //tvHighlight.setThreshold(1);
                AutoComMonth.setAdapter(adapter);
                AutoComMonth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     callAcceptRejectListApi("0");
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


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void doApiCall() {
        final ArrayList<AcceptRejectLeave> items = new ArrayList<>();

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
        if (currentPage < totalPage) {
            leaveAdapter.addLoading();
        } else {
            isLastPage = true;
        }
        isLoading = false;
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
                            totalPage = responseModel.getResult().getTotalpages();
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                if (acceptRejectLeaveArrayList != null) {
                                    //employeeListArrayList= new ArrayList<>();
                                }
                                acceptRejectLeaveArrayList = responseModel.getResult().getLeave();
                                doApiCall();
                            }
                        }
                    }catch (Exception e){
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