package com.ominfo.crm_solution.ui.reminders;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.model.GradientColor;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.alarm.alarmslist.AlarmRecyclerViewAdapter;
import com.ominfo.crm_solution.alarm.alarmslist.AlarmsListViewModel;
import com.ominfo.crm_solution.alarm.alarmslist.OnToggleAlarmListener;
import com.ominfo.crm_solution.alarm.createalarm.CreateAlarmViewModel;
import com.ominfo.crm_solution.alarm.data.Alarm;
import com.ominfo.crm_solution.alarm.get_count.DeleteReminderViewModel;
import com.ominfo.crm_solution.alarm.get_count.GetCountViewModel;
import com.ominfo.crm_solution.alarm.get_count.UpdateOnlyRecordIdViewModel;
import com.ominfo.crm_solution.alarm.get_count.UpdateRecordIdViewModel;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.basecontrol.BaseApplication;
import com.ominfo.crm_solution.basecontrol.BaseFragment;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.interfaces.Constants;
import com.ominfo.crm_solution.interfaces.ErrorCallbacks;
import com.ominfo.crm_solution.interfaces.SharedPrefKey;
import com.ominfo.crm_solution.network.ApiResponse;
import com.ominfo.crm_solution.network.DynamicAPIPath;
import com.ominfo.crm_solution.network.NetworkCheck;
import com.ominfo.crm_solution.network.ViewModelFactory;
import com.ominfo.crm_solution.ui.dashboard.fragment.DashboardFragment;
import com.ominfo.crm_solution.ui.dashboard.model.DashModel;
import com.ominfo.crm_solution.ui.enquiry_report.adapter.RmTagAdapter;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmResponse;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmViewModel;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmlist;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.notifications.NotificationsActivity;
import com.ominfo.crm_solution.ui.reminders.model.AddReminderRequest;
import com.ominfo.crm_solution.ui.reminders.model.AddReminderResponse;
import com.ominfo.crm_solution.ui.reminders.model.AddReminderViewModel;
import com.ominfo.crm_solution.ui.reminders.model.EmployeeListViewModel;
import com.ominfo.crm_solution.ui.reminders.model.ReminderListRequest;
import com.ominfo.crm_solution.ui.reminders.model.ReminderListResponse;
import com.ominfo.crm_solution.ui.reminders.model.ReminderListViewModel;
import com.ominfo.crm_solution.ui.reminders.model.UpdateReminderRequest;
import com.ominfo.crm_solution.ui.reminders.model.UpdateReminderResponse;
import com.ominfo.crm_solution.ui.reminders.model.UpdateReminderViewModel;
import com.ominfo.crm_solution.ui.sale.model.RmListModel;
import com.ominfo.crm_solution.ui.sales_credit.model.GraphModel;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;
import com.ominfo.crm_solution.util.SharedPref;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
//https://github.com/PhilJay/MPAndroidChart/wiki/Modifying-the-Viewport

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 * Use the {@link ReminderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReminderFragment extends BaseFragment implements OnToggleAlarmListener {

    public static Context mContext;
    private static RecyclerView alarmsRecyclerView;
    //ReminderAdapter reminderAdapter;
    RmTagAdapter addRmTagAdapter;
     //RecyclerView alarmsRecyclerView;

    @BindView(R.id.imgAddReminder)
    AppCompatImageView imgAddReminder;

    @BindView(R.id.iv_emptyLayimage)
    AppCompatImageView iv_emptyLayimage;

    @BindView(R.id.tv_emptyLayTitle)
    AppCompatTextView tv_emptyLayTitle;

    @BindView(R.id.tvSearchView)
    AppCompatTextView tvToolbarTitle;

    @BindView(R.id.layReminder)
    LinearLayoutCompat layReminder;

    @BindView(R.id.empty_layoutActivity)
    LinearLayoutCompat emptyLayout;
    AppCompatAutoCompleteTextView AutoComTextViewDesr,AutoComTextViewDate,AutoComTextViewTime;

    @BindView(R.id.imgBack)
    AppCompatImageView imgBack;
    @BindView(R.id.imgNotify)
    AppCompatImageView imgNotify;
    @BindView(R.id.imgRefresh)
    AppCompatImageView imgRefresh;
    @BindView(R.id.tvNotifyCount)
    AppCompatTextView tvNotifyCount;
    String mTimeHour = "", mTimeMinute = "";
    List<RmListModel> tagRmList = new ArrayList<>();
    public static AlarmRecyclerViewAdapter alarmRecyclerViewAdapter;
    public static AlarmsListViewModel alarmsListViewModel;
    public static UpdateRecordIdViewModel updateRecordIdViewModel;
    public static UpdateOnlyRecordIdViewModel updateOnlyRecordIdViewModel;
    public static GetCountViewModel getCountViewModel;
    public static EmployeeListViewModel employeeListViewModel;
    //private RecyclerView alarmsRecyclerView;
    //private Button addAlarm;
    public static CreateAlarmViewModel createAlarmViewModel;
    public static List<Alarm> allAlarms = new ArrayList<>();
    BarData barData;
    List<GradientColor> list = new ArrayList<>();
    // variable for our bar data set.
    BarDataSet barDataSet;
    String dialogStatus = "";
    // array list for storing entries.
    ArrayList barEntriesArrayList;
    //private static final String[] DATA_BAR_GRAPH = new String[6];//{"","09:00",
    private String[] DAYS = new String[100];/*{"C1", "C2", "C3", "C4", "C5", "C6", *//*"C7", "C8", "C9"
            , "C10", "C11", "C12"*//*};*/

    private String[] DAYSY = new String[100];/*{"5", "60", "15", "70", "25",
           "10"*//*, "45","90", "95","50", "55","60", "65"*//*};*/
    int startPos = 0 , endPos = 0;
    List<GetRmlist> RMDropdown = new ArrayList<>();
    List<DashModel> dashboardList = new ArrayList<>();
    List<DashModel> tagList = new ArrayList<>();
    List<GraphModel> graphModelsList = new ArrayList<>();
    private AppDatabase mDb;
    final Calendar myCalendar = Calendar.getInstance();
    @Inject
    ViewModelFactory mViewModelFactory;
    private ReminderListViewModel reminderListViewModel;
    private AddReminderViewModel addReminderViewModel;
    private GetRmViewModel getRmViewModel;
    private UpdateReminderViewModel updateReminderViewModel;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    int incHeight = 3;
    public static DeleteReminderViewModel deleteReminderViewModel;

    public ReminderFragment() {
        // Required empty public constructor
    }

    public static ReminderFragment newInstance(String param1, String param2) {
        ReminderFragment fragment = new ReminderFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        deleteReminderViewModel = ViewModelProviders.of(this).get(DeleteReminderViewModel .class);
        getCountViewModel = ViewModelProviders.of(this).get(GetCountViewModel.class);
        createAlarmViewModel = ViewModelProviders.of(this).get(CreateAlarmViewModel.class);
        alarmRecyclerViewAdapter = new AlarmRecyclerViewAdapter(this,mContext, alarmRecyclerViewAdapter, new AlarmRecyclerViewAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(Alarm alarm, int checkStatus) {
              //callUpdateReminderApi();
                if(checkStatus==0) {
                    LogUtil.printToastMSG(mContext, "completed");
                    callUpdateReminderApi(alarm.getRecordId(),"COMPLETED","statuschange",
                            alarm.getTime(),alarm.getDate());
                }
                if(checkStatus==2) {
                    LogUtil.printToastMSG(mContext, "cancelled");
                    callUpdateReminderApi(alarm.getRecordId(),"CANCELLED","statuschange",
                            alarm.getTime(),alarm.getDate());
                }
                if(checkStatus==1) {
                    LogUtil.printToastMSG(mContext, "Snoozed");
                    callUpdateReminderApi(alarm.getRecordId(),"SNOOZED","snooze",
                            alarm.getTime(),alarm.getDate());
                }
            }

        });
        //alarmRecyclerViewAdapter = new AlarmRecyclerViewAdapter(this,mContext, alarmRecyclerViewAdapter);
        alarmsListViewModel = ViewModelProviders.of(this).get(AlarmsListViewModel.class);
        updateRecordIdViewModel = ViewModelProviders.of(this).get(UpdateRecordIdViewModel.class);
        updateOnlyRecordIdViewModel = ViewModelProviders.of(this).get(UpdateOnlyRecordIdViewModel.class);
        alarmsListViewModel.getAlarmsLiveData().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(List<Alarm> alarms) {
                allAlarms = alarms;
                //if (alarms != null) {
                for(int i=0;i<alarms.size();i++) {
                    LogUtil.printLog("alarm_list_test", "title - "+alarms.get(i).getTitle()+" "+alarms.get(i).getDate() + "-yo- "
                            + alarms.get(i).getTime());
                }
                //}
                if (alarms.size() > 0) {
                    alarmRecyclerViewAdapter.setAlarms(alarms);
                    //layReminder.setVisibility(View.VISIBLE);
                    alarmsRecyclerView.setVisibility(View.VISIBLE);
                    emptyLayout.setVisibility(View.GONE);
                } else {
                    Glide.with(mContext)
                            .load(R.drawable.img_bg_reminders)
                            .into(iv_emptyLayimage);
                    tv_emptyLayTitle.setText(R.string.scr_lbl_no_reminder);
                    emptyLayout.setVisibility(View.VISIBLE);
                    alarmsRecyclerView.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_reminder, container, false);
        ButterKnife.bind(this, view);
        try{dialogStatus = getArguments().getString("dialog");}catch (Exception e){e.printStackTrace();}
        alarmsRecyclerView = view.findViewById(R.id.rvSalesList);
        showAddReminderDialog(dialogStatus==null || dialogStatus.equals("")?"0":dialogStatus);
        iv_emptyLayimage.setImageDrawable(mContext.getDrawable(R.drawable.img_bg_reminders));
        emptyLayout.setVisibility(View.VISIBLE);
        setAlarmList();
        return view;
    }

    public static void setAlarmList(){
        //updateAlarmStatus
        alarmsRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        alarmsRecyclerView.setAdapter(alarmRecyclerViewAdapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((BaseActivity)mContext).getDeps().inject(this);
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        injectAPI();
        init();
        //fromDate.setPaintFlags(fromDate.getPaintFlags() |  Paint.UNDERLINE_TEXT_FLAG);
        //toDate.setPaintFlags(toDate.getPaintFlags() |  Paint.UNDERLINE_TEXT_FLAG);
    }

    private void injectAPI() {
        reminderListViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ReminderListViewModel.class);
        reminderListViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_REMINDER));

        addReminderViewModel = ViewModelProviders.of(this, mViewModelFactory).get(AddReminderViewModel.class);
        addReminderViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse -> consumeResponse(apiResponse, DynamicAPIPath.POST_ADD_REMINDER));

        getRmViewModel = ViewModelProviders.of(this, mViewModelFactory).get(GetRmViewModel.class);
        getRmViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_GET_RM));

        updateReminderViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UpdateReminderViewModel.class);
        updateReminderViewModel.getResponse().observe(getViewLifecycleOwner(), apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_UPDATE_REMINDER));
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getActivity().getResources().getColor(R.color.status_bar_color));

    }


    private void init(){
        mDb = BaseApplication.getInstance(mContext).getAppDatabase();
        setToolbar();
        //set empty layout data
        Glide.with(this)
                .load(R.drawable.img_bg_reminders)
                .into(iv_emptyLayimage);
        tv_emptyLayTitle.setText(R.string.scr_lbl_no_reminder);
        callReminderListApi();
        callRMApi();
    }

    /* Call Api For RM */
    private void callRMApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                RequestBody mRequestBodyType = RequestBody.create(MediaType.parse("text/plain"), DynamicAPIPath.action_get_rm);
                RequestBody mRequestBodyTypeImage = RequestBody.create(MediaType.parse("text/plain"), "0");//loginTable.getEmployeeId());
                RequestBody mRequestBodyTypeImage1 = RequestBody.create(MediaType.parse("text/plain"), loginTable.getCompanyId());
                getRmViewModel.hitGetRmApi(mRequestBodyType, mRequestBodyTypeImage, mRequestBodyTypeImage1);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Update Reminder */
    private void callUpdateReminderApi(String recordId,String status,String requestType,String reminder,String dateSnooze) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                String rem = AppUtils.convert12to24(reminder);
                //String reminderTime =  AppUtils.addSnoozedTime(rem);
                //String[] timeMain = rem.split(":");
                String[] date = dateSnooze.split("/");
                UpdateReminderRequest updateReminderRequest = new UpdateReminderRequest();
                updateReminderRequest.setCompanyId(loginTable.getCompanyId());
                updateReminderRequest.setEmployeeId(loginTable.getEmployeeId());
                updateReminderRequest.setRequestType(requestType);
                updateReminderRequest.setDate(date[2]+"-"+date[1]+ "-"+date[0]);
                updateReminderRequest.setRecordId(recordId);
                updateReminderRequest.setStatus(status);
                updateReminderRequest.setTime(rem/*+":00"*/);
                updateReminderViewModel.hitUpdateReminderApi(updateReminderRequest);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    private void setGraphData(int initStatus) {
        if(initStatus!=3) {
            DAYS = new String[6];
            DAYSY = new String[6];
        }
        if(initStatus==3){
            DAYS = new String[graphModelsList.size()+1];
            DAYSY = new String[graphModelsList.size()+1];
        }
        if(initStatus!=3) {
            try {
                endPos = startPos + 6;
                if (endPos <= graphModelsList.size()) {
                    //if(startPos<6) {

                    for (int i = 0; i < graphModelsList.size(); i++) {
                        if (graphModelsList.get(i).getxValue() != null) {
                            DAYS[i] = graphModelsList.get(i).getxValue();
                        }
                        if (graphModelsList.get(i).getyValue() != null) {
                            DAYSY[i] = graphModelsList.get(i).getyValue();
                        }
                    }
                    try {
                        //getGraph();
                        //setAdapterForDashboardList();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(initStatus==3){
            for (int i = 0; i < graphModelsList.size(); i++) {
                if(graphModelsList.get(i).getxValue()!=null) {
                    DAYS[i] = graphModelsList.get(i).getxValue();
                }
                if(graphModelsList.get(i).getyValue()!=null) {
                    DAYSY[i] = graphModelsList.get(i).getyValue();
                }
            }
            try {
                //getGraph();
                //setAdapterForDashboardList();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* Call Api For Reminder */
    private void callReminderListApi() {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                ReminderListRequest request = new ReminderListRequest();
                request.setCompanyId(loginTable.getCompanyId());
                request.setEmployeeId(loginTable.getEmployeeId());
                reminderListViewModel.hitReminderListApi(request);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    /* Call Api For Add Reminder */
    private void callAddReminderApi(String remark,String descr,String date,String time) {
        if (NetworkCheck.isInternetAvailable(mContext)) {
            LoginTable loginTable = mDb.getDbDAO().getLoginData();
            if(loginTable!=null) {
                List<String> mRMList= new ArrayList<>();
                mRMList.add(loginTable.getEmployeeId());
                for(int i=0;i<tagRmList.size();i++){
                    if(!TextUtils.isEmpty(tagRmList.get(i).getTitle())) {
                        mRMList.add(tagRmList.get(i).getId());
                    }
                }
                String dateChanged = AppUtils.dateReminder(date);
                String timeChanged = AppUtils.convert12to24(time);
                AddReminderRequest request = new AddReminderRequest();
                request.setCompanyId(loginTable.getCompanyId());
                request.setRemark(remark);
                request.setRemDate(dateChanged);
                request.setEmployeeId(loginTable.getEmployeeId());
                request.setEmployeelist(mRMList);
                request.setRemdescription(descr);
                request.setRemTime(timeChanged/*+":00"*/);
                request.setRemStatus("CREATED");
                addReminderViewModel.hitAddReminderApi(request);
            }
            else {
                LogUtil.printToastMSG(mContext, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
        }
    }

    @Override
    public void onToggle(Alarm alarm) {
        if (alarm.isStarted()) {
            alarm.cancelAlarm(getContext());
            alarmsListViewModel.update(alarm);
        } else {
            alarm.schedule(getContext());
            alarmsListViewModel.update(alarm);
        }
    }


    //set date picker view
    private void openDataPicker(AppCompatAutoCompleteTextView datePickerField) {
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat="";
                myFormat = "dd/MM/yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                datePickerField.setText(sdf.format(myCalendar.getTime()));
            }

        };

        new DatePickerDialog(mContext, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void OpenTimePicker(AppCompatAutoCompleteTextView AutoComTextViewTime){
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
                String min = convertDate(myCalendar.get(Calendar.MINUTE));
                boolean isPM = (selectedHour >= 12);
                AutoComTextViewTime.setText(String.format("%02d:%02d %s", (selectedHour == 12 || selectedHour == 0) ? 12 : selectedHour % 12, myCalendar.get(Calendar.MINUTE), isPM ? "pm" : "am"));

               // AutoComTextViewTime.setText(strHrsToShow + ":" + min + " " + am_pm);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public String convertDate(int input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }

    //show Add Reminder popup
    public void showAddReminderDialog(String dialogStatus) {
        Dialog mDialog = new Dialog(mContext, R.style.ThemeDialogCustom);
        mDialog.setContentView(R.layout.dialog_add_reminder);
        mDialog.setCanceledOnTouchOutside(true);
        AppCompatAutoCompleteTextView AutoComTextViewStatus = mDialog.findViewById(R.id.AutoComTextViewStatus);
        AppCompatImageView mClose = mDialog.findViewById(R.id.imgCancel);
        AppCompatButton addReminderButton = mDialog.findViewById(R.id.addReminderButton);
        AppCompatEditText etRemark = mDialog.findViewById(R.id.etLocationDescr);
        RecyclerView rvImages = mDialog.findViewById(R.id.rvImages);
         AutoComTextViewDesr = mDialog.findViewById(R.id.AutoComTextViewDesr);
         AutoComTextViewDate = mDialog.findViewById(R.id.AutoComTextViewDate);
         AutoComTextViewTime = mDialog.findViewById(R.id.AutoComTextViewTime);
        TextInputLayout input_text= mDialog.findViewById(R.id.input_text);
        TextInputLayout input_textDate= mDialog.findViewById(R.id.input_textDate);
        TextInputLayout input_textTime= mDialog.findViewById(R.id.input_textTime);
        //setAddTagList(rvImages);
        setAddRmTagList(rvImages);
        setDropdownReminder(AutoComTextViewStatus);
        rvImages.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (tagRmList.size() == 1 || tagRmList.size() == 2) {
                    addRmTagAdapter.updateList(tagRmList, 1);
                }
                return false;
            }
        });
        AutoComTextViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDataPicker(AutoComTextViewDate);
            }
        });
        AutoComTextViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTimePicker(AutoComTextViewTime);
            }
        });

       /* rvImages.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (tagList.size() == 1 || tagList.size() == 2) {
                    addTagAdapter.updateList(tagList, 1);
                }
                return false;
            }
        });*/
        addReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDetailsValid(AutoComTextViewDesr, input_text, AutoComTextViewDate, input_textDate, AutoComTextViewTime,
                        input_textTime)) {
                    mDialog.dismiss();
                    String currentString = AutoComTextViewTime.getText().toString().trim();
                    String _24hourFormat = AppUtils.convert12to24(currentString);
                    String[] separated = _24hourFormat.split(":");
                    try {
                        if (separated.length > 0) {
                            mTimeHour = separated[0];
                            mTimeMinute = separated[1];
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //LogUtil.printToastMSG(mContext,mTimeHour+":"+mTimeMinute+"v-"+AutoComTextViewDesr.getText().toString().trim()
                    //);
                    callAddReminderApi(etRemark.getText().toString().trim(),AutoComTextViewDesr.getText().toString().trim()
                            , AutoComTextViewDate.getText().toString().trim(), AutoComTextViewTime.getText().toString().trim());
                }
            }
        });

        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
        if(dialogStatus.equals("1")) {
            mDialog.show();
        }
    }

    //set value to Reminder dropdown
    private void setDropdownReminder(AppCompatAutoCompleteTextView AutoComTextViewReminder) {
        List<String> RMDropdown = new ArrayList<>();
        RMDropdown.add("Call Later");
        RMDropdown.add("Close");
        RMDropdown.add("Ongoing Call");
        RMDropdown.add("Successful");

        try {
            int pos = 0;
            if (RMDropdown != null && RMDropdown.size() > 0) {
                String[] mDropdownList = new String[RMDropdown.size()];
                for (int i = 0; i < RMDropdown.size(); i++) {
                    mDropdownList[i] = String.valueOf(RMDropdown.get(i));
                   /* if (!VehiIdDropdown.equals("")) {
                        if (RMDropdown.get(i).getId().equals(VehiIdDropdown)) {
                            pos = i;
                        }
                    }*/
                }
                //AutoComTextViewVehNo.setText(mDropdownList[pos]);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        mContext,
                        R.layout.row_dropdown_item,
                        mDropdownList);
                //AutoComTextViewVehNo.setThreshold(1);
                AutoComTextViewReminder.setAdapter(adapter);
                //mSelectedColor = mDropdownList[pos];
                AutoComTextViewReminder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    /*check validations on field*/
    private boolean isDetailsValid(AppCompatAutoCompleteTextView AutoComTextViewDesr,
                                   TextInputLayout input_text,
                                   AppCompatAutoCompleteTextView AutoComTextViewDate,
                                   TextInputLayout input_textDate,
                                   AppCompatAutoCompleteTextView AutoComTextViewTime,
                                   TextInputLayout input_textTime) {
        if (TextUtils.isEmpty(AutoComTextViewDesr.getText().toString().trim())) {
            setError(input_text, getString(R.string.val_msg_please_enter_description));
            return false;
        } else if (isTitleExits(AutoComTextViewDesr.getText().toString().trim())) {
            setError(input_text, getString(R.string.val_msg_please_enter_valid_description));
            return false;
        } else if (TextUtils.isEmpty(AutoComTextViewDate.getText().toString().trim())) {
            setError(input_textDate, getString(R.string.val_msg_please_enter_password));
            return false;
        }  else if (TextUtils.isEmpty(AutoComTextViewTime.getText().toString().trim())) {
            setError(input_textTime, getString(R.string.val_msg_please_enter_password));
        return false;
    }
        return true;
    }

    private boolean isTitleExits(String value) {
        boolean Status  = false;
        for (int i = 0; i < allAlarms.size(); i++) {
            if (allAlarms.get(i).getTitle().equals(value)) {
                Status = true;
            }
        }
        return Status;
    }

    private void setAddRmTagList(RecyclerView rvRm) {
        tagRmList.clear();
        tagRmList.add(new RmListModel("","1",""));
        if (tagRmList.size() > 0) {
            rvRm.setVisibility(View.VISIBLE);
        } else {
            rvRm.setVisibility(View.GONE);
        }

        addRmTagAdapter = new RmTagAdapter(mContext, tagRmList, new RmTagAdapter.ListItemSelectListener() {
            @Override
            public void onItemClick(List<RmListModel> mDataTicket) {
                tagRmList =  mDataTicket;
                addRmTagAdapter.updateList(tagRmList,0);
                if(tagRmList.size()>1 && tagRmList.size()%(incHeight)==0){
                    int calHeight = tagRmList.size()/2;
                    int marginInDp40 = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 90*(calHeight>0?calHeight:1), getResources()
                                    .getDisplayMetrics());
                    ViewGroup.LayoutParams params = rvRm.getLayoutParams();
                   // Changes the height and width to the specified *pixels*
                    params.height = marginInDp40;
                    params.width = MATCH_PARENT;
                    rvRm.setLayoutParams(params);
                    incHeight = incHeight + 2;
                    //rvRm.setMinimumHeight(marginInDp40);
                    //setMargins(rvImages, 0, marginInDp40, 0, 0);
                }
            }
        });
        rvRm.setHasFixedSize(true);
        rvRm.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvRm.setItemAnimator(new DefaultItemAnimator());
        rvRm.setAdapter(addRmTagAdapter);

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
        tvToolbarTitle.setText(R.string.scr_lbl_reminders);
      /*  String notiCount = SharedPref.getInstance(mContext).read(SharedPrefKey.IS_NOTIFY_COUNT, "0");
        tvNotifyCount.setText(notiCount);*/
        ((BaseActivity)mContext).initToolbar(5, mContext, R.id.imgBack, R.id.imgReport, R.id.imgNotify,tvNotifyCount, R.id.layBack, R.id.imgCall);
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
    @OnClick({R.id.imgAddReminder,R.id.imgRefresh/*R.id.imgGraph,R.id.imgTable,*//*,R.id.add_fab,*//*R.id.imgFilter,R.id.resetButton
    ,R.id.toDate,R.id.fromDate*/})
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgAddReminder:
                callRMApi();
                showAddReminderDialog("1");
                break;
            case R.id.imgRefresh:
                Glide.with(this)
                        .load(R.drawable.img_bg_reminders)
                        .into(iv_emptyLayimage);
                tv_emptyLayTitle.setText(R.string.scr_lbl_no_reminder);
                callReminderListApi();
                callRMApi();
                break;
        }
    }

    private void scheduleAlarm(String hour,String min,String desr,String date,String time
            ,String recordId ,String status) {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);
        //String _24Hour= AppUtils.convert12to24(hour);
        long _24DateMin =  AppUtils.DateToMilise(date+" "+hour+":"+min);
        Alarm alarm = new Alarm(
                alarmId,
                Integer.parseInt(hour),
                Integer.parseInt(min),
                desr,
                _24DateMin,/*System.currentTimeMillis(),*/
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                date,
                time,
                "0",
                recordId,
                status
        );

        createAlarmViewModel.insert(alarm);

        alarm.schedule(getContext());
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

    public class MyBarDataSet extends BarDataSet {

        public MyBarDataSet(List<BarEntry> yVals, String label) {
            super(yVals, label);
        }

        @Override
        public GradientColor getGradientColor(int index) {
            if (Integer.parseInt(graphModelsList.get(index).getyValue()) < 75) // less than 95 green
                return list.get(0);
            else if (Integer.parseInt(graphModelsList.get(index).getyValue()) > 75
            ) // less than 100 orange
                return list.get(1);
            else if(Integer.parseInt(graphModelsList.get(index).getyValue()) > 75
                    && Integer.parseInt(graphModelsList.get(index).getyValue()) < 150) // less than 100 orange
                return list.get(1);
            else // greater or equal than 100 red
                return list.get(0);
        }


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

        public class DividerItemDecorator extends RecyclerView.ItemDecoration {

            private Drawable mDivider;
            private final Rect mBounds = new Rect();

            public DividerItemDecorator(Drawable divider) {
                mDivider = divider;
            }

            @Override
            public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
                canvas.save();
                final int left;
                final int right;
                if (parent.getClipToPadding()) {
                    left = parent.getPaddingLeft();
                    right = parent.getWidth() - parent.getPaddingRight();
                    canvas.clipRect(left, parent.getPaddingTop(), right,
                            parent.getHeight() - parent.getPaddingBottom());
                } else {
                    left = 0;
                    right = parent.getWidth();
                }

                final int childCount = parent.getChildCount();
                for (int i = 0; i < childCount - 1; i++) {
                    final View child = parent.getChildAt(i);
                    parent.getDecoratedBoundsWithMargins(child, mBounds);
                    final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
                    final int top = bottom - mDivider.getIntrinsicHeight();
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                canvas.restore();
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

                if (parent.getChildAdapterPosition(view) == state.getItemCount() - 1) {
                    outRect.setEmpty();
                } else
                    outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
            }
        }

    /*Api response */
    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {

            case LOADING:
                ((BaseActivity) mContext).showSmallProgressBar(mProgressBarHolder);
                break;

            case SUCCESS:
                ((BaseActivity)getActivity()).dismissSmallProgressBar(mProgressBarHolder);
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_REMINDER)) {
                            ReminderListResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), ReminderListResponse.class);
                            if (responseModel != null && responseModel.getStatus() == 1) {
                                if(responseModel.getResult()!=null && responseModel.getResult().size()>0)
                                {
                                    for(int i=0;i<responseModel.getResult().size();i++) {
                                        String[] rem = responseModel.getResult().get(i).getRemTime().split(":");
                                        //String[] dateMain = responseModel.getResult().get(i).getRemDate().split("T");
                                        String[] date = responseModel.getResult().get(i).getNewDateReminder().split("-");
                                        String mtime12hrs = AppUtils.convert24to12(rem[0] +":"+ rem[1]);
                                        //"2021-08-11T18:30:00.000Z"
                                        int finalI = i;
                                        AsyncTask.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                int iExitsCheck = 0;
                                                String AMPM= "";
                                                int iExits = getCountViewModel.getAlarmsCount(responseModel.getResult().get(finalI).getRemDescription()
                                                        , date[2] + "/" + date[1] + "/" + date[0], mtime12hrs);
                                                String[] checkAMam = mtime12hrs.split(" ");
                                                if(checkAMam.length>0){
                                                    if(checkAMam[1].equals("AM")){
                                                        AMPM = " am";
                                                       }
                                                    else if(checkAMam[1].equals("am")){
                                                        AMPM = " AM";
                                                    } else if(checkAMam[1].equals("pm")){
                                                        AMPM = " PM";
                                                    } else if(checkAMam[1].equals("PM")){
                                                    AMPM = " pm";
                                                    }
                                                    iExitsCheck = getCountViewModel.getAlarmsCount(responseModel.getResult().get(finalI).getRemDescription()
                                                            , date[2] + "/" + date[1] + "/" + date[0], checkAMam[0]+AMPM);

                                                }
                                                //LogUtil.printLog("sizeRem= ",iExits);
                                                if (iExits == 0 && iExitsCheck==0) {
                                                    //LogUtil.printLog("sizeRem= ", responseModel.getResult().get(0).getRecordId().toString());
                                                    scheduleAlarm(rem[0], rem[1], responseModel.getResult().get(finalI).getRemDescription()
                                                            , date[2] + "/" + date[1] + "/" + date[0], mtime12hrs,
                                                            responseModel.getResult().get(finalI).getRecordId().toString(),
                                                            responseModel.getResult().get(finalI).getRemStatus());
                                                }
                                                else {
                                                    updateRecordIdViewModel.updateRecordId(responseModel.getResult().get(finalI).getRemDescription()
                                                            , date[2] + "/" + date[1] + "/" + date[0], mtime12hrs,
                                                            responseModel.getResult().get(finalI).getRecordId().toString(),
                                                            responseModel.getResult().get(finalI).getRemStatus());
                                                    //LogUtil.printLog("sizeRem= " ,iExits);
                                                }
                                            }
                                        });

                                    }

                                }
                                else{
                                    //LogUtil.printToastMSG(mContext, "size= " + 0);
                                }

                            }
                        }
                    }catch (Exception e){
                        //LogUtil.printLog( "sizeRem=" , e.getMessage());
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_ADD_REMINDER)) {
                            AddReminderResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), AddReminderResponse.class);
                            if (responseModel != null/* && responseModel.getStatus()==1*/) {
                                scheduleAlarm(mTimeHour, mTimeMinute, AutoComTextViewDesr.getText().toString().trim()
                                        , AutoComTextViewDate.getText().toString().trim(), AutoComTextViewTime.getText().toString().trim()
                                        ,String.valueOf(responseModel.getAddRemResultList().get(0).getRecordId()),"CREATED");
                               /* //TODO UPDATE
                                updateOnlyRecordIdViewModel.updateOnlyRecordId(AutoComTextViewDesr.getText().toString().trim(),
                                        AutoComTextViewDate.getText().toString().trim(), AutoComTextViewTime.getText().toString().trim()
                                , String.valueOf(responseModel.getAddRemResultList().get(0).getRecordId()));*/
                            }
                            else{
                                ((BaseActivity) mContext).errorMessage(responseModel.getMessage(), new ErrorCallbacks() {
                                    @Override
                                    public void onOkClick() {
                                        //DO something;
                                    }
                                });
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_GET_RM)) {
                            GetRmResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), GetRmResponse.class);
                            if (responseModel != null && responseModel.getResult().getStatus().equals("success")) {
                                RMDropdown.clear();
                                RMDropdown = responseModel.getResult().getRmlist();
                                ///setDropdownRM();
                                addRmTagAdapter.updateRmList(RMDropdown);
                            } else {
                                LogUtil.printToastMSG(mContext, responseModel.getResult().getMessage());
                            }
                        }
                    }catch (Exception e){e.printStackTrace();}
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_UPDATE_REMINDER)) {
                            UpdateReminderResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), UpdateReminderResponse.class);
                            if (responseModel != null && responseModel.getStatus()==1) {
                                LogUtil.printToastMSG(mContext, "updated");
                            } else {
                                LogUtil.printToastMSG(mContext, responseModel.getMessage());
                            }
                        }
                    }catch (Exception e){
                        LogUtil.printLog( "sizeRem=" , e.getMessage());
                        e.printStackTrace();
                    }
                }
                break;
            case ERROR:
                ((BaseActivity)getActivity()).dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(mContext, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }

}