package com.ominfo.staff.ui.lr_number.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.ominfo.staff.R;
import com.ominfo.staff.basecontrol.BaseActivity;
import com.ominfo.staff.basecontrol.BaseApplication;
import com.ominfo.staff.basecontrol.BaseFragment;
import com.ominfo.staff.database.AppDatabase;
import com.ominfo.staff.interfaces.Constants;
import com.ominfo.staff.interfaces.SharedPrefKey;
import com.ominfo.staff.network.ApiResponse;
import com.ominfo.staff.network.DynamicAPIPath;
import com.ominfo.staff.network.NetworkCheck;
import com.ominfo.staff.network.ViewModelFactory;
import com.ominfo.staff.ui.login.model.LoginResultTable;
import com.ominfo.staff.ui.lr_number.AddLrActivity;
import com.ominfo.staff.ui.lr_number.LrDetailsActivity;
import com.ominfo.staff.ui.lr_number.SelectedLrNumberActivity;
import com.ominfo.staff.ui.lr_number.adapter.LrNumberAdapter;
import com.ominfo.staff.ui.driver_hisab.model.DriverHisabModel;
import com.ominfo.staff.ui.lr_number.model.GetVehicleListRequest;
import com.ominfo.staff.ui.lr_number.model.GetVehicleListResponse;
import com.ominfo.staff.ui.lr_number.model.GetVehicleListResult;
import com.ominfo.staff.ui.lr_number.model.GetVehicleViewModel;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewFragment extends BaseFragment {

    private Context mContext;

    @BindView(R.id.rvLrNumber)
    RecyclerView mRecylerViewLrNumber;
    @BindView(R.id.imgFilter)
    AppCompatImageView imgFilter;
    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat linearLayoutEmptyActivity;
    @BindView(R.id.tv_emptyLayTitle)
    AppCompatTextView tvEmptyLayTitle;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    StringBuilder TypeString = new StringBuilder(1000);
    LrNumberAdapter mLrNumberAdapter;
    List<GetVehicleListResult> vehicleModelList = new ArrayList<>();
    final Calendar myCalendar = Calendar.getInstance();
    @BindView(R.id.iv_emptyLayimage)
    AppCompatImageView imgEmptyImage;

    @Inject
    ViewModelFactory mViewModelFactory;
    private GetVehicleViewModel mGetVehicleViewModel;
    private AppDatabase mDb;
    String mUrl = "", mFinalURL = "";
    boolean mSearchStatus = false, mScrolledStatus = false;
    int type = 0;
    private boolean loading = true;
    private String mUserKey = "";

    public ViewFragment() {
        // Required empty public constructor
    }

    public static ViewFragment newInstance(String param1, String param2) {
        ViewFragment fragment = new ViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((SelectedLrNumberActivity) mContext).getDeps().inject(this);
        injectAPI();
        mDb = BaseApplication.getInstance().getAppDatabase();
        //get dataset data
        LoginResultTable loginResultTable = mDb.getDbDAO().getLoginData();
        if (loginResultTable != null) {
            mUserKey = loginResultTable.getUserKey();
        }
       /* List<GetVehicleListResult> dbList = mDb.getDbDAO().getVehicleDetailsList();
        if(dbList!=null && dbList.size()>0){
            vehicleModelList = dbList;
            setAdapterForVehicleList();
        }
        else {*/
            String FromDate = AppUtils.getDashCurrentDateTime_(),ToDate = AppUtils.getDashCurrentDateTime_();
            SharedPref.getInstance(mContext).write(SharedPrefKey.FROM_DATE, FromDate);
            SharedPref.getInstance(mContext).write(SharedPrefKey.TO_DATE, ToDate);
            callVehicleListApi(FromDate,ToDate);
        //}

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkCheck.isInternetAvailable(mContext)) {
                    mDb.getDbDAO().deleteVehicleDetailsList();
                    String fromDate = SharedPref.getInstance(getContext()).read(SharedPrefKey.FROM_DATE, AppUtils.getDashCurrentDateTime_());
                    String toDate = SharedPref.getInstance(getContext()).read(SharedPrefKey.FROM_DATE, AppUtils.getDashCurrentDateTime_());
                    callVehicleListApi(fromDate, toDate);
                } else {
                    LogUtil.printToastMSG(mContext,getString(R.string.err_msg_connection_was_refused));
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    private void injectAPI() {
        mGetVehicleViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetVehicleViewModel.class);
        mGetVehicleViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_GET_VEHICLE));
    }

    /* Call Api For Vehicle List */
    private void callVehicleListApi(String fromDate,String toDate) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            GetVehicleListRequest mRequest = new GetVehicleListRequest();
            mRequest.setUserkey(mUserKey);//mUserKey); //6b07b768-926c-49b6-ac1c-89a9d03d4c3b
            mRequest.setFromDate(fromDate);
            mRequest.setToDate(toDate);
            Gson gson = new Gson();
            String bodyInStringFormat = gson.toJson(mRequest);
            mGetVehicleViewModel.hitGetVehicleApi(bodyInStringFormat);
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }



    private void setAdapterForVehicleList() {
        if (vehicleModelList.size() > 0) {
            mLrNumberAdapter = new LrNumberAdapter(mContext, vehicleModelList, new LrNumberAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(GetVehicleListResult mDataTicket) {
                    Intent intent = new Intent(mContext,AddLrActivity.class);
                    intent.putExtra(Constants.TRANSACTION_ID, mDataTicket.getTransactionID());
                    intent.putExtra(Constants.FROM_SCREEN, Constants.LIST);
                    startActivity(intent);
                }
            });
            mRecylerViewLrNumber.setHasFixedSize(true);
            mRecylerViewLrNumber.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            mRecylerViewLrNumber.setAdapter(mLrNumberAdapter);
            mRecylerViewLrNumber.setVisibility(View.VISIBLE);
            linearLayoutEmptyActivity.setVisibility(View.GONE);
            imgEmptyImage.setBackground(getResources().getDrawable(R.drawable.ic_error_load));
            tvEmptyLayTitle.setText(getString(R.string.scr_lbl_data_loading));
        } else {
            mRecylerViewLrNumber.setVisibility(View.GONE);
            linearLayoutEmptyActivity.setVisibility(View.VISIBLE);
            imgEmptyImage.setBackground(getResources().getDrawable(R.drawable.ic_error_load));
            tvEmptyLayTitle.setText(R.string.scr_lbl_no_data_available);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    //perform click actions
    @OnClick({R.id.imgFilter})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgFilter:
                showSelectDateDialog();
                break;
        }
    }

    private void showSelectDateDialog() {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_select_date);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.submitButton);
        AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);
        LinearLayoutCompat layCalender = mDialog.findViewById(R.id.layCalender);
        AppCompatTextView tvDateValue = mDialog.findViewById(R.id.tvDateValue);
        AppCompatTextView tvFromDate = mDialog.findViewById(R.id.tvDateValueFrom);
        LinearLayoutCompat layFromDate = mDialog.findViewById(R.id.layFromDate);
        tvDateValue.setText(AppUtils.getCurrentDateTime_());
        //tvFromDate.setText(AppUtils.getCurrentDateTime_());
        tvFromDate.setText(AppUtils.getCurrentDateTime_());
        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                mDb.getDbDAO().deleteVehicleDetailsList();
                String[] separatedDateFrom = tvFromDate.getText().toString().trim().split("/");
                String DateFrom = separatedDateFrom[0]+"-"+separatedDateFrom[1]+"-"+separatedDateFrom[2];
                String[] separatedToFrom = tvDateValue.getText().toString().trim().split("/");
                String DateTo = separatedToFrom[0]+"-"+separatedToFrom[1]+"-"+separatedToFrom[2];
                SharedPref.getInstance(mContext).write(SharedPrefKey.FROM_DATE, DateFrom);
                SharedPref.getInstance(mContext).write(SharedPrefKey.TO_DATE, DateTo);
                callVehicleListApi(DateFrom,DateTo);
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
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
                openDataPicker(tvDateValue,1);
            }
        });
        mDialog.show();
    }

    //set date picker view
    public void openDataPicker(AppCompatTextView datePickerField, int mFrom) {
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
                    myFormat = "yyyy/MM/dd"; //In which you need put here
                }
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                datePickerField.setText(sdf.format(myCalendar.getTime()));
            }

        };

        new DatePickerDialog(mContext, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }


    //Api response
    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {

            case LOADING:
                ((BaseActivity) mContext).showProgressLoader(getString(R.string.scr_message_please_wait));
                break;

            case SUCCESS:
                ((BaseActivity) mContext).dismissLoader();
                mSwipeRefreshLayout.setRefreshing(false);
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_VEHICLE)) {
                            GetVehicleListResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetVehicleListResponse.class);
                            if (responseModel != null && responseModel.getStatus().equals("1")) {
                                vehicleModelList.removeAll(vehicleModelList);
                                vehicleModelList = responseModel.getResult();
                                try{mDb.getDbDAO().insertVehicleList(vehicleModelList);}
                                catch (Exception e){e.printStackTrace();}
                                if(vehicleModelList!=null)
                                {
                                    tvEmptyLayTitle.setText(getString(R.string.scr_lbl_data_loading));
                                }
                                else {
                                    tvEmptyLayTitle.setText(R.string.scr_lbl_no_data_available);
                                }
                                //LogUtil.printToastMSG(SearchActivity.this, String.valueOf(responseModel.getData().size()));
                                setAdapterForVehicleList();
                            } else {
                                //LogUtil.printToastMSG(LoginActivity.this, responseModel.getResponse());
                            }
                        }
                    } catch (Exception e) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        LogUtil.printLog("Data_new", "");
                        e.printStackTrace();
                    }
                }
                break;
            case ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                ((BaseActivity) mContext).dismissLoader();
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }

}