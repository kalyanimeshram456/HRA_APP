package com.ominfo.staff.ui.lr_number.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.ominfo.staff.R;
import com.ominfo.staff.basecontrol.BaseApplication;
import com.ominfo.staff.basecontrol.BaseFragment;
import com.ominfo.staff.database.AppDatabase;
import com.ominfo.staff.interfaces.SharedPrefKey;
import com.ominfo.staff.network.DynamicAPIPath;
import com.ominfo.staff.network.NetworkAPIServices;
import com.ominfo.staff.network.NetworkCheck;
import com.ominfo.staff.network.NetworkURLs;
import com.ominfo.staff.network.RetroNetworkModule;
import com.ominfo.staff.network.ViewModelFactory;
import com.ominfo.staff.ui.lr_number.adapter.LrNumberAdapter;
import com.ominfo.staff.ui.driver_hisab.model.DriverHisabModel;
import com.ominfo.staff.ui.lr_number.model.GetVehicleListResult;
import com.ominfo.staff.ui.lr_number.model.UploadVehicleRecordRequest;
import com.ominfo.staff.ui.lr_number.model.UploadVehicleRecordRespoonse;
import com.ominfo.staff.ui.lr_number.model.VehicleDetailsResultTable;
import com.ominfo.staff.util.AppUtils;
import com.ominfo.staff.util.LogUtil;
import com.ominfo.staff.util.SharedPref;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PendingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PendingFragment extends BaseFragment {

    private Context mContext;

    @BindView(R.id.rvLrNumber)
    RecyclerView mRecylerViewLrNumber;
    //@BindView(R.id.searchView)
    @BindView(R.id.iv_emptyLayimage)
    AppCompatImageView imgEmptyImage;
    @BindView(R.id.tv_emptyLayTitle)
    AppCompatTextView tvEmptyImage;
    //AppCompatImageView mImageViewFilter;
    StringBuilder TypeString = new StringBuilder(1000);
    LrNumberAdapter mLrNumberAdapter;
    List<GetVehicleListResult> driverHisabModelList = new ArrayList<>();
    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat linearLayoutEmptyActivity;

    @Inject
    ViewModelFactory mViewModelFactory;
    private AppDatabase mDb;
    String mUrl = "", mFinalURL = "";
    boolean mSearchStatus = false, mScrolledStatus = false;
    int type = 0;
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;

    public PendingFragment() {
        // Required empty public constructor
    }

    public static PendingFragment newInstance(String param1, String param2) {
        PendingFragment fragment = new PendingFragment();
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
        View view = inflater.inflate(R.layout.fragment_pending, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //((SearchActivity) mContext).getDeps().inject(this);
        //injectAPI();
        mDb = BaseApplication.getInstance().getAppDatabase();

        setAdapterForPuranaHisabList();
        /*mSwipeRefreshLayout.setNestedScrollingEnabled(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setAdapterForPuranaHisabList();
                mSwipeRefreshLayout.setRefreshing(false);

            }
        });*/


    }

    private void setAdapterForPuranaHisabList() {
        List<VehicleDetailsResultTable> vehicleList = mDb.getDbDAO().getVehicleList();
        for (int i=0;i<vehicleList.size();i++){
            GetVehicleListResult result = new GetVehicleListResult();
            result.setNoOfLR(vehicleList.get(i).getNoOfLR());
            result.setPlant(vehicleList.get(i).getPlantID());
            result.setTransactionDate(vehicleList.get(i).getTransactionDate());
            result.setTransactionID(vehicleList.get(i).getTransactionID());
            result.setVehicleNo(vehicleList.get(i).getVehicleNo());
            driverHisabModelList.add(result);
        }
        if (driverHisabModelList.size() > 0) {
            mLrNumberAdapter = new LrNumberAdapter(mContext, driverHisabModelList, new LrNumberAdapter.ListItemSelectListener() {
                @Override
                public void onItemClick(GetVehicleListResult mData) {

                }
            });
            mRecylerViewLrNumber.setHasFixedSize(true);
            mRecylerViewLrNumber.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
            mRecylerViewLrNumber.setAdapter(mLrNumberAdapter);
            mRecylerViewLrNumber.setVisibility(View.VISIBLE);
            linearLayoutEmptyActivity.setVisibility(View.GONE);
            imgEmptyImage.setBackground(getResources().getDrawable(R.drawable.ic_error_load));
            tvEmptyImage.setText(getString(R.string.scr_lbl_data_loading));
        } else {
            mRecylerViewLrNumber.setVisibility(View.GONE);
            linearLayoutEmptyActivity.setVisibility(View.VISIBLE);
            imgEmptyImage.setBackground(getResources().getDrawable(R.drawable.ic_done_upload));
            tvEmptyImage.setText(R.string.scr_lbl_no_pending);
        }

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    //perform click actions
    @OnClick({/*R.id.ivFilter*/})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
           /* case R.id.ivFilter:
                TypeString.setLength(0);
                showSelectFilterDialog(0);
                break;*/
        }
    }


    /*Api response *//*
    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {

            case LOADING:
                //showProgressLoader(getString(R.string.scr_message_please_wait));
                break;

            case SUCCESS:
                //dismissLoader();
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.GET_TICKET)) {
                            GetTicketData responseModel = new Gson().fromJson(apiResponse.data.toString(), GetTicketData.class);
                            if (responseModel != null && responseModel.getSuccess()) {
                                //LogUtil.printToastMSG(SearchActivity.this, String.valueOf(responseModel.getData().size()));
                                if (responseModel.getData() != null) {
                                    if (mResList.size() > 0) {
                                        mResList.addAll(setOnlineList(responseModel.getData()));
                                    } else {
                                        mResList = setOnlineList(responseModel.getData());
                                       //saveCitationIssurance(mResList);
                                    }
                                    setAdapterForHistory();
                                }
                            } else {
                                //LogUtil.printToastMSG(LoginActivity.this, responseModel.getResponse());
                            }
                        }
                    } catch (Exception e) {
                        LogUtil.printLog("Data_new", "");
                        e.printStackTrace();
                        //token expires
                        //dismissLoader();
                        //LogUtil.printToastMSG(mContext,e.getMessage());
                        //((BaseActivity)mContext).logout(mContext);
                    }
                }
                break;
            case ERROR:
                //dismissLoader();
                ((BaseActivity) mContext).logout(mContext);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }*/

}