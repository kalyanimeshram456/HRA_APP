package com.ominfo.crm_solution.alarm.activity;

import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.DATETIME;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.ID;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.TITLE;
import static com.ominfo.crm_solution.ui.reminders.ReminderFragment.alarmsListViewModel;
import static com.ominfo.crm_solution.ui.reminders.ReminderFragment.setAlarmList;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.alarm.alarmslist.AlarmsListViewModel;
import com.ominfo.crm_solution.alarm.alarmslist.OnToggleAlarmListener;
import com.ominfo.crm_solution.alarm.data.Alarm;
import com.ominfo.crm_solution.alarm.get_count.GetCountViewModel;
import com.ominfo.crm_solution.alarm.get_count.GetRecordViewModel;
import com.ominfo.crm_solution.alarm.service.AlarmService;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.basecontrol.BaseApplication;
import com.ominfo.crm_solution.common.OnSwipeTouchListener;
import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.interfaces.Constants;
import com.ominfo.crm_solution.interfaces.ErrorCallbacks;
import com.ominfo.crm_solution.interfaces.SharedPrefKey;
import com.ominfo.crm_solution.network.ApiResponse;
import com.ominfo.crm_solution.network.DynamicAPIPath;
import com.ominfo.crm_solution.network.NetworkCheck;
import com.ominfo.crm_solution.network.ViewModelFactory;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmResponse;
import com.ominfo.crm_solution.ui.enquiry_report.model.GetRmViewModel;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.reminders.ReminderFragment;
import com.ominfo.crm_solution.ui.reminders.model.AddReminderResponse;
import com.ominfo.crm_solution.ui.reminders.model.AddReminderViewModel;
import com.ominfo.crm_solution.ui.reminders.model.ReminderListResponse;
import com.ominfo.crm_solution.ui.reminders.model.ReminderListViewModel;
import com.ominfo.crm_solution.ui.reminders.model.UpdateReminderRequest;
import com.ominfo.crm_solution.ui.reminders.model.UpdateReminderResponse;
import com.ominfo.crm_solution.ui.reminders.model.UpdateReminderViewModel;
import com.ominfo.crm_solution.util.AppUtils;
import com.ominfo.crm_solution.util.LogUtil;
import com.ominfo.crm_solution.util.SharedPref;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RingActivity extends BaseActivity implements OnToggleAlarmListener {
    @BindView(R.id.tvDone) AppCompatTextView dismiss;
    @BindView(R.id.tvSnooze) AppCompatTextView snooze;
    @BindView(R.id.imgRing) AppCompatSeekBar clock;
    @BindView(R.id.tvTime) AppCompatTextView tvTime;
    @BindView(R.id.tvDate) AppCompatTextView tvDate;
    @BindView(R.id.tvTitle) AppCompatTextView tvTitle;
    private GetRecordViewModel getRecordViewModel;
    @Inject
    ViewModelFactory mViewModelFactory;
    private UpdateReminderViewModel updateReminderViewModel;
    Context context;
    String mAlarmId = "",mAlarmDateTime = "";
    Alarm alarm = new Alarm();
    String mTimeReal = "";
    private AppDatabase mDb;
    @BindView(R.id.progressBarHolder)
    FrameLayout mProgressBarHolder;
    //private OnToggleAlarmListener listener;
    private AlarmsListViewModel alarmsListViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_alert);
        ButterKnife.bind(this);
        getDeps().inject(this);
        mDb = BaseApplication.getInstance(this).getAppDatabase();
        injectAPI();
        getRecordViewModel = ViewModelProviders.of(this).get(GetRecordViewModel.class);
        alarmsListViewModel = ViewModelProviders.of(this).get(AlarmsListViewModel.class);

        Intent intent = getIntent();
        if(intent!=null){
            mAlarmId = intent.getStringExtra(TITLE);
            mAlarmDateTime = intent.getStringExtra(DATETIME);
        }
       /* mAlarmId = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.TITLE_ALARM, "");
        mAlarmDateTime = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.DATE_ALARM, "");
*/
       try{
            if(mAlarmDateTime!=null) {
                String[] separated = mAlarmDateTime.split(",");
                String mTime = separated[0];
                mTimeReal = separated[1];
                String mDate = AppUtils.convertAlarmDate(separated[1]);
                tvTime.setText(mTime);
                tvDate.setText(mDate==null?"":mDate);
                tvTitle.setText(mAlarmId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                alarm = getRecordViewModel.getAlarmsRecord(mAlarmId,mTimeReal,
                        tvTime.getText().toString());
                //tvTime.setText(alarm.getTime()+"yoo"+alarm.getTitle()+"uuu"+alarm.getDate());
            }
        });

        //try {

       /* }catch (Exception e){e.printStackTrace();}*/
        // get the 'extra' from the intent
        /*if(mAlarmId==null) {
            mAlarmId = getIntent().getStringExtra(ID);
            mAlarmDateTime = getIntent().getStringExtra(DATETIME);
        }*/



        context = this;

        animateClock();
        clock.setProgress(50);
        clock.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                try{
                if(progress>65){
                    setAlarmSnooze();
                    finish();
                    //Alarm alarm = new Alarm();
                    //alarm.setDataAlarmList(mAlarmId);
                }
                if(progress<35){

                    setAlarmComplete();
                    finish();
                }}catch (Exception e){e.printStackTrace();}
                //Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void injectAPI() {
        updateReminderViewModel = ViewModelProviders.of(this, mViewModelFactory).get(UpdateReminderViewModel.class);
        updateReminderViewModel.getResponse().observe(this, apiResponse ->consumeResponse(apiResponse, DynamicAPIPath.POST_UPDATE_REMINDER));
    }

    private void setAlarmComplete(){
        alarm.setValue("1");
        alarm.setStatus("COMPLETED");
        callUpdateReminderApi(alarm.getRecordId(),"COMPLETED","statuschange",
                alarm.getTime(),alarm.getDate());
        alarmsListViewModel.update(alarm);
        Intent intentService = new Intent(this, AlarmService.class);
        this.stopService(intentService);
        LogUtil.printToastMSG(this, "completed");

    }

    private void setAlarmSnooze(){
        String currentString = alarm.getTime();
        String _24hourFormat = AppUtils.convert12to24(currentString);
        String[] separated = _24hourFormat.split(":");
        String mTimeHour = "", mTimeMinute = "";
        try {
            if (separated.length > 0) {
                mTimeHour = separated[0];
                mTimeMinute = separated[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long _24DateMin =  AppUtils.DateToMilise(alarm.getDate()+" "+mTimeHour+":"+mTimeMinute);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(_24DateMin);//System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, Constants.afterAlarm);//10

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String mDate = dateFormat.format(calendar.getTime());
        SimpleDateFormat dateFormatTime = new SimpleDateFormat("hh:mm a");
        String mDateTime = dateFormatTime.format(calendar.getTime());

        Alarm alarmNew = new Alarm(
                new Random().nextInt(Integer.MAX_VALUE),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                tvTitle.getText().toString(),
                calendar.getTimeInMillis(),
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                mDate,
                mDateTime,
                alarm.getValue(),
                alarm.getRecordId(),
                "SNOOZED"

        );

        alarmNew.schedule(this);
        alarmNew.setAlarmId(alarm.getAlarmId());
        callUpdateReminderApi(alarm.getRecordId(),"SNOOZED","snooze",
                mDateTime,mDate);
        alarmsListViewModel.update(alarmNew);
        Intent intentServiceQ = new Intent(this, AlarmService.class);
        this.stopService(intentServiceQ);
        LogUtil.printToastMSG(this, "Snoozed");
    }

    /* Call Api For Update Reminder */
    private void callUpdateReminderApi(String recordId,String status,String requestType,String reminder,String dateSnooze) {
        if (NetworkCheck.isInternetAvailable(this)) {
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
                LogUtil.printToastMSG(this, "Something is wrong.");
            }
        } else {
            LogUtil.printToastMSG(this, getString(R.string.err_msg_connection_was_refused));
        }
    }


    private void animateClock() {
        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(clock, "rotation", 0f, 20f, 0f, -20f, 0f);
        rotateAnimation.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimation.setDuration(800);
        rotateAnimation.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.status_bar_color));

    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);//getActivity().getResources().getColor(R.color.black));

    }

    /*Api response */
    private void consumeResponse(ApiResponse apiResponse, String tag) {
        switch (apiResponse.status) {

            case LOADING:
                showSmallProgressBar(mProgressBarHolder);
                break;

            case SUCCESS:
                dismissSmallProgressBar(mProgressBarHolder);
                if (!apiResponse.data.isJsonNull()) {
                    LogUtil.printLog(tag, apiResponse.data.toString());
                    try {
                        if (tag.equalsIgnoreCase(DynamicAPIPath.POST_UPDATE_REMINDER)) {
                            UpdateReminderResponse responseModel = new Gson().fromJson(apiResponse.data.toString(), UpdateReminderResponse.class);
                            if (responseModel != null && responseModel.getStatus()==1) {
                                LogUtil.printToastMSG(this, "updated");
                            } else {
                                LogUtil.printToastMSG(this, responseModel.getMessage());
                            }
                        }
                    }catch (Exception e){
                        LogUtil.printLog( "sizeRem=" , e.getMessage());
                        e.printStackTrace();
                    }
                }
                break;
            case ERROR:
                dismissSmallProgressBar(mProgressBarHolder);
                LogUtil.printToastMSG(this, getString(R.string.err_msg_connection_was_refused));
                break;
        }
    }

    @Override
    public void onToggle(Alarm alarm) {
        if (alarm.isStarted()) {
            alarm.cancelAlarm(this);
            alarmsListViewModel.update(alarm);
        } else {
            alarm.schedule(this);
            alarmsListViewModel.update(alarm);
        }
    }

        /* clock.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
               *//* Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                Alarm alarm = new Alarm();
                alarm.setDataAlarmList(mAlarmId);
                finish();*//*
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                //LogUtil.printToastMSG(context,"Will remind you after 10 minutes");
               *//* Intent intentService = new Intent(getApplicationContext(), AlarmService.class);
                getApplicationContext().stopService(intentService);
                //Alarm alarm = new Alarm();
                //alarm.setDataAlarmList(mAlarmId);
                finish();*//*
            }
        });*/
}
