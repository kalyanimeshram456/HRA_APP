package com.ominfo.crm_solution.ui.dashboard.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.basecontrol.BaseApplication;
import com.ominfo.crm_solution.basecontrol.BaseFragment;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.interfaces.Constants;
import com.ominfo.crm_solution.network.ViewModelFactory;
import com.ominfo.crm_solution.ui.dashboard.DashbooardActivity;
import com.ominfo.crm_solution.ui.dashboard.adapter.CrmAdapter;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.enquiry_report.EnquiryReportFragment;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.quotation_amount.QuotationFragment;
import com.ominfo.crm_solution.ui.sale.SaleFragment;
import com.ominfo.crm_solution.ui.visit_report.activity.StartEndVisitActivity;
import com.ominfo.crm_solution.util.AppUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends BaseFragment {

    private Context mContext;

    /* @BindView(R.id.cardKataChithi)
     CardView mCardKataChithi;*/
    @BindView(R.id.imgSearchLr)
    AppCompatImageView imgSearchLr;
    /*@BindView(R.id.tvSearchView)
    AppCompatEditText etSearch;*/
    @BindView(R.id.rvDashboardList)
    RecyclerView rvDashboardList;
    @BindView(R.id.imgProfileDash)
    CircleImageView imgProfileDash;
    /*@BindView(R.id.chronometer)
    Chronometer tvCounter;*/
    final Calendar myCalendar = Calendar.getInstance();
    private AppDatabase mDb;
    CrmAdapter mCrmAdapter;
    List<DashModel> dashboardList = new ArrayList<>();
    @Inject
    ViewModelFactory mViewModelFactory;
    //CallManagerAdapter mCallManagerAdapter;
    public static boolean activityVisible; // Variable that will check the
    final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    IntentFilter intentFilter;
    DashbooardActivity.MyReceiver receiver;

    //private GetVehicleViewModel mGetVehicleViewModel;
    //private AppDatabase mDb;
    String mUrl = "", mFinalURL = "";
    boolean mSearchStatus = false, mScrolledStatus = false;
    int type = 0;
    private boolean loading = true;
    private String mUserKey = "";

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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
        View view = inflater.inflate(R.layout.activity_dashbooard, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        intentFilter = new IntentFilter();
        intentFilter.addAction(CONNECTIVITY_ACTION);
        //startChronometer();
        /*tvCounter.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer cArg) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, getCurrentMiliSecondsOfChronometer());
                setTimerMillis(getContext(), cArg.getBase());
                tvCounter.setText(DateFormat.format("HH:mm:ss", calendar.getTime()));
            }
        });*/
    }

    @Override
    public void onResume() {
        super.onResume();


        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.status_bar_color));

    }


    public static void setTimerMillis(Context context, long millis)
    {
        SharedPreferences sp = context.getSharedPreferences(Constants.BANK_LIST, Context.MODE_PRIVATE);
        SharedPreferences.Editor spe = sp.edit(); spe.putLong(Constants.BANK_LIST, millis); spe.apply();
    }

   /* private int getCurrentMiliSecondsOfChronometer() {
        int stoppedMilliseconds = 0;
        String chronoText = tvCounter.getText().toString();
        String array[] = chronoText.split(":");
        if (array.length == 2) {
            stoppedMilliseconds = Integer.parseInt(array[0]) * 60 * 1000 + Integer.parseInt(array[1]) * 1000;
        } else if (array.length == 3) {
            stoppedMilliseconds =
                    Integer.parseInt(array[0]) * 60 * 60 * 1000 + Integer.parseInt(array[1]) * 60 * 1000
                            + Integer.parseInt(array[2]) * 1000;
        }
        return Integer.parseInt(String.format("%02d", stoppedMilliseconds));
    }

    private void startChronometer() {
        long stoppedMilliseconds = getTimerMillis(mContext);
        tvCounter.setBase(stoppedMilliseconds);
        tvCounter.start();
    }*/

    public static long getTimerMillis(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constants.BANK_LIST, Context.MODE_PRIVATE);
        return sp.getLong(Constants.BANK_LIST, 0);
    }


    /*private void injectAPI() {
        mGetVehicleViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetVehicleViewModel.class);
        mGetVehicleViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_GET_VEHICLE));
    }*/

    /*  *//* Call Api For Vehicle List *//*
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
    }*/



  /*  private void setAdapterForVehicleList() {
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
    }*/

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    //perform click actions
    @OnClick({R.id.cardSale,R.id.cardQuotation,R.id.cardEnquiry,R.id.layVisitTimer})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.cardSale:
                Fragment fragment = new SaleFragment();
                moveFromFragment(fragment,mContext);
                break;
            case R.id.cardQuotation:
                Fragment fragmentQuo = new QuotationFragment();
                moveFromFragment(fragmentQuo,mContext);
                break;
            case R.id.cardEnquiry:
                Fragment fragmentEnq = new EnquiryReportFragment();
                moveFromFragment(fragmentEnq,mContext);
                break;
            case R.id.layVisitTimer:
                Intent i = new Intent(mContext, StartEndVisitActivity.class);
                i.putExtra(Constants.TRANSACTION_ID, "0");
                startActivity(i);
                break;



        }
    }



    private void init(){
        mDb =BaseApplication.getInstance(mContext).getAppDatabase();
        //requestPermission();
        setToolbar();
        dashboardList.removeAll(dashboardList);
        dashboardList.add(new DashModel("Sales Credit","₹1,24,567,90",mContext.getDrawable(R.drawable.ic_om_sales_credit)));
        dashboardList.add(new DashModel("Receipt","₹1,24,567,90",mContext.getDrawable(R.drawable.ic_om_receipt)));
        dashboardList.add(new DashModel("Top Customer","₹1,24,567,90",mContext.getDrawable(R.drawable.ic_om_rating)));
        dashboardList.add(new DashModel("Total Quotation Amount","₹1,24,567,90",mContext.getDrawable(R.drawable.ic_om_total_quotation)));
        dashboardList.add(new DashModel("Dispatch Pending","₹1,24,567,90",mContext.getDrawable(R.drawable.ic_om_dispatch_pending)));
        dashboardList.add(new DashModel("Enquiry Report","₹1,24,567,90",mContext.getDrawable(R.drawable.ic_om_enquiry_report)));
        dashboardList.add(new DashModel("Visit Report","₹1,24,567,90",mContext.getDrawable(R.drawable.ic_om_visit_report)));
        dashboardList.add(new DashModel("Products","₹1,24,567,90",mContext.getDrawable(R.drawable.ic_om_product)));

        setAdapterForDashboardList();
        try {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if (loginTable != null) {
                //LogUtil.printLog("image_url", loginTable.getBaseUrl() + loginTable.getProfilePicture());
                AppUtils.loadImageURL(mContext, loginTable.getBaseUrl() + loginTable.getProfilePicture()
                        , imgProfileDash, null);
            }
        }catch (Exception e){e.printStackTrace();}
    }

    private void setToolbar(){
        ((BaseActivity)mContext).initToolbar(0,mContext,R.id.imgBack,R.id.imgReport,R.id.imgNotify,0,R.id.imgCall);
    }


    private void setAdapterForDashboardList() {
        if (dashboardList.size() > 0) {
            rvDashboardList.setVisibility(View.VISIBLE);
        } else {
            rvDashboardList.setVisibility(View.GONE);
        }
        mCrmAdapter = new CrmAdapter(mContext, dashboardList, new CrmAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(DashModel mDataTicket) {

            }
        });
        rvDashboardList.setHasFixedSize(true);
        rvDashboardList.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        rvDashboardList.setAdapter(mCrmAdapter);
    }

  /*  //set date picker view
    private void openDataPicker(AppCompatTextView datePickerField,int mFrom) {
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
                    myFormat = "dd/MM/yyyy"; //In which you need put here
                }
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                datePickerField.setText(sdf.format(myCalendar.getTime()));
            }

        };

        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    //show truck details popup
    public void showTruckDetailsDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_truck_details);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.detailsButton);
        //AppCompatButton cancelButton = mDialog.findViewById(R.id.cancelButton);
        RelativeLayout relRC = mDialog.findViewById(R.id.relRC);
        RelativeLayout relPUC = mDialog.findViewById(R.id.relPUC);
        RelativeLayout relIss = mDialog.findViewById(R.id.relIss);

        relRC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showFullImageDialog();
            }
        });
        relPUC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showFullImageDialog();
            }
        });
        relIss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
                showFullImageDialog();
            }
        });
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

    //show truck details popup
    public void showFullImageDialog() {
        Dialog mDialog = new Dialog(this, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_doc_full_view);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton okayButton = mDialog.findViewById(R.id.detailsButton);

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
    }*/


    /*//request camera and storage permission
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (mContext.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {

                requestPermissions(new String[]
                                { Manifest.permission.CAMERA,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                },
                        1000);

            } else {
                //createFolder();
            }
        } else {
            //createFolder();
        }
    }
*/

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

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }*/

}