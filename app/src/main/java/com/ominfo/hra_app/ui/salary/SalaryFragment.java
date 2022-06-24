package com.ominfo.hra_app.ui.salary;

import static com.ominfo.hra_app.MainActivity.bottomNavigationView;
import static com.ominfo.hra_app.ui.employees.PaginationListener.PAGE_START;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.ominfo.hra_app.network.ApiResponse;
import com.ominfo.hra_app.network.DynamicAPIPath;
import com.ominfo.hra_app.network.NetworkCheck;
import com.ominfo.hra_app.network.ViewModelFactory;
import com.ominfo.hra_app.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.hra_app.ui.dashboard.model.DashModel;
import com.ominfo.hra_app.ui.employees.PaginationListener;
import com.ominfo.hra_app.ui.login.model.LoginTable;
import com.ominfo.hra_app.ui.notifications.NotificationsActivity;
import com.ominfo.hra_app.ui.salary.adapter.SalaryAdapter;
import com.ominfo.hra_app.ui.salary.adapter.SalaryNewAdapter;
import com.ominfo.hra_app.ui.salary.adapter.SalarySheetListAdapter;
import com.ominfo.hra_app.ui.salary.fragment.SalaryDisbursementFragment;
import com.ominfo.hra_app.ui.salary.fragment.SalarySheetFragment;
import com.ominfo.hra_app.ui.salary.fragment.SalarySlipFragment;
import com.ominfo.hra_app.ui.salary.model.SalaryAllData;
import com.ominfo.hra_app.ui.salary.model.SalaryAllList;
import com.ominfo.hra_app.ui.salary.model.SalaryAllListRequest;
import com.ominfo.hra_app.ui.salary.model.SalaryAllListViewModel;
import com.ominfo.hra_app.ui.salary.model.SalaryAllResponse;
import com.ominfo.hra_app.ui.salary.model.SalarySheetList;
import com.ominfo.hra_app.ui.sales_credit.model.GraphModel;
import com.ominfo.hra_app.util.AppUtils;
import com.ominfo.hra_app.util.LogUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
 * Use the {@link SalaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalaryFragment extends BaseFragment {

    Context mContext;
    SalaryNewAdapter salaryAdapter;
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
    List<SalaryAllList> salaryAllresultList = new ArrayList<>();
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat emptyLayout;
    @BindView(R.id.iv_emptyLayimage)
    AppCompatImageView iv_emptyLayimage;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    @BindView(R.id.btnSubmit)
    AppCompatButton btnSubmit;
    final Calendar myCalendar = Calendar.getInstance();
    @Inject
    ViewModelFactory mViewModelFactory;
    private SalaryAllListViewModel salaryAllListViewModel;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private long totalPage = 0;
    private boolean isLoading = false;
    int itemCount = 0;

    public SalaryFragment() {
        // Required empty public constructor
    }

    public static SalaryFragment newInstance(String param1, String param2) {
        SalaryFragment fragment = new SalaryFragment();
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
        View view = inflater.inflate(R.layout.activity_salary, container, false);
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
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this)
                .load(R.drawable.img_bg_search)
                .into(iv_emptyLayimage);
        tv_emptyLayTitle.setText(R.string.scr_lbl_no_data_available);
        setAdapterForSalaryList();
        callSalaryAllListApi();
    }

    private void setAdapterForSalaryList() {
        LoginTable loginTable = mDb.getDbDAO().getLoginData();
        if (salaryAllresultList!=null && salaryAllresultList.size() > 0) {
            Collections.sort(salaryAllresultList, new Comparator<SalaryAllList>() {
                @Override
                public int compare(SalaryAllList o1, SalaryAllList o2) {
                    return Long.valueOf(o2.getIsActive()==null||o2.getIsActive()
                    .equals("")?"0":o2.getIsActive()).compareTo(Long.valueOf(o1.getIsActive()==null ||
                            o1.getIsActive().equals("")?"0":o1.getIsActive()));
                }
                @Override
                public boolean equals(Object obj) {
                    return false;
                }});
            rvSalesList.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            if(loginTable!=null){
                if(!loginTable.getIsadmin().equals("0")){
                    btnSubmit.setVisibility(View.VISIBLE);
                }
            }
        } else {
            rvSalesList.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(R.drawable.img_bg_search)
                    .into(iv_emptyLayimage);
            tv_emptyLayTitle.setText(R.string.scr_lbl_no_data_available);
        }
        salaryAdapter = new SalaryNewAdapter(mContext, salaryAllresultList, new SalaryNewAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(int mDataTicket, SalaryAllList employeeList) {
                SalarySheetFragment sheetFragment = new SalarySheetFragment();
                FragmentManager fragmentManager = ((MainActivity)mContext).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle args = new Bundle();
                args.putString(Constants.edit, employeeList.getEmpId());
                sheetFragment.setArguments(args);
                fragmentTransaction.add(R.id.framecontainer, sheetFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        rvSalesList.setHasFixedSize(true);
        rvSalesList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvSalesList.setAdapter(salaryAdapter);
    }

    private void injectAPI() {
        salaryAllListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(SalaryAllListViewModel.class);
        salaryAllListViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_SALARY_ALL_LIST));
   }
    /* Call Api For employee list */
    private void callSalaryAllListApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                tv_emptyLayTitle.setText(R.string.scr_message_please_wait);
                btnSubmit.setVisibility(View.GONE);
                RequestBody mRequestAction = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_salary_all_list);
                RequestBody mRequestComId = RequestBody.create(MediaType.parse("text/plain"),loginTable.getCompanyId());
                RequestBody mRequestEmployee = RequestBody.create(MediaType.parse("text/plain"), loginTable.getEmployeeId());
                RequestBody mRequestisAd = RequestBody.create(MediaType.parse("text/plain"),  loginTable.getIsadmin());
                //RequestBody mRequestpage_number = RequestBody.create(MediaType.parse("text/plain"), pageNo);
               // RequestBody mRequestpage_size = RequestBody.create(MediaType.parse("text/plain"), Constants.PAG_SIZE);

                String mon = AppUtils.convertMonthSalary();
                String newMon = mon;
                newMon = String.valueOf(Integer.parseInt(mon) == 1 ? 12 :Integer.parseInt(mon)-1);
                RequestBody mRequestMonth = RequestBody.create(MediaType.parse("text/plain"), newMon.length()==1?"0"+newMon:newMon);
                String year = AppUtils.getCurrentYear();
                RequestBody mRequestYear = RequestBody.create(MediaType.parse("text/plain"), year);

                SalaryAllListRequest request= new SalaryAllListRequest();
                request.setYear(mRequestYear);
                request.setAction(mRequestAction);
                request.setCompany_ID(mRequestComId);
               // if(!loginTable.getIsadmin().equals("0")) {
                request.setEmp_id(mRequestEmployee);
                //}
                request.setIsAdmin(mRequestisAd);
                //request.setPageNumber(mRequestpage_number);
                //request.setPageSize(mRequestpage_size);
                request.setMonth(mRequestMonth);
                salaryAllListViewModel.hitSalaryAllListAPI(request);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    private void setToolbar() {
        //set toolbar title
        tvToolbarTitle.setText(R.string.scr_lbl_salary);
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
                    datePickerField.setText(sdf.format(myCalendar.getTime()));
                }
                else {
                    datePickerField.setText(sdf.format(myCalendar.getTime()));
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
    @OnClick({R.id.btnSubmit})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnSubmit:
                SalaryDisbursementFragment salaryDisbursementFragment = new SalaryDisbursementFragment();
                moveFromFragment(salaryDisbursementFragment,mContext);
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
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_SALARY_ALL_LIST)) {
                            SalaryAllResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), SalaryAllResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                    if (responseModel.getResult().getList() != null && responseModel.getResult().getList().size()>0) {
                                        salaryAllresultList= new ArrayList<>();
                                        salaryAllresultList = responseModel.getResult().getList();
                                    }
                                    setAdapterForSalaryList();
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