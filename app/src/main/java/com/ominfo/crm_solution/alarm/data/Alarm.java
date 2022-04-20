package com.ominfo.crm_solution.alarm.data;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.DATETIME;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.FRIDAY;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.ID;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.MONDAY;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.RECORD_ID;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.RECURRING;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.SATURDAY;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.STATUS;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.SUNDAY;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.THURSDAY;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.TITLE;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.TUESDAY;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.VALUE;
import static com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver.WEDNESDAY;
import static com.ominfo.crm_solution.ui.reminders.ReminderFragment.alarmRecyclerViewAdapter;
import static com.ominfo.crm_solution.ui.reminders.ReminderFragment.alarmsListViewModel;
import static com.ominfo.crm_solution.ui.reminders.ReminderFragment.allAlarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ominfo.crm_solution.alarm.broadcastreceiver.AlarmBroadcastReceiver;
import com.ominfo.crm_solution.alarm.createalarm.DayUtil;
import com.ominfo.crm_solution.basecontrol.BaseActivity;
import com.ominfo.crm_solution.interfaces.ErrorCallbacks;
import com.ominfo.crm_solution.ui.reminders.ReminderFragment;
import com.ominfo.crm_solution.util.AppUtils;

import java.util.Calendar;

@Entity(tableName = "alarm_table")
public class Alarm {
    @PrimaryKey
    @NonNull
    private int alarmId;

    private int hour, minute;
    private boolean started, recurring;
    private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;
    private String title,date,time,value, recordId, status;
    private long created;

    public Alarm(int alarmId, int hour, int minute, String title, long created, boolean started, boolean recurring, boolean monday, boolean tuesday, boolean wednesday, boolean thursday, boolean friday,
                 boolean saturday, boolean sunday,String date, String time, String value
    ,String recordId, String status) {
        this.alarmId = alarmId;
        this.hour = hour;
        this.minute = minute;
        this.started = started;

        this.recurring = recurring;

        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;

        this.title = title;

        this.created = created;
        this.date = date;
        this.time = time;
        this.value = value;
        this.recordId = recordId;
        this.status = status;
    }

    @Ignore
    public Alarm() {

    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isStarted() {
        return started;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public boolean isMonday() {
        return monday;
    }

    public boolean isTuesday() {
        return tuesday;
    }

    public boolean isWednesday() {
        return wednesday;
    }

    public boolean isThursday() {
        return thursday;
    }

    public boolean isFriday() {
        return friday;
    }

    public boolean isSaturday() {
        return saturday;
    }

    public boolean isSunday() {
        return sunday;
    }

    public void setDataAlarmList(String mAlarmId){
        Alarm alarm = new Alarm();//new Alarm(alarmId, hour, minute,  title,  created,  started,  recurring,  monday,  tuesday,  wednesday,  thursday,  friday,  saturday,  sunday, date,  time,  "1") ;
        for(int i=0;i<allAlarms.size();i++){
            if(allAlarms.get(i).getTitle().equals(mAlarmId)){
                allAlarms.get(i).setValue("1");
                alarm = allAlarms.get(i);
            }
        }
        alarmsListViewModel.update(alarm);
        //alarmRecyclerViewAdapter.setAlarms(allAlarms);
        //ReminderFragment.setAlarmList();
    }

    public void schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(RECURRING, recurring);
        intent.putExtra(MONDAY, monday);
        intent.putExtra(TUESDAY, tuesday);
        intent.putExtra(WEDNESDAY, wednesday);
        intent.putExtra(THURSDAY, thursday);
        intent.putExtra(FRIDAY, friday);
        intent.putExtra(SATURDAY, saturday);
        intent.putExtra(SUNDAY, sunday);
        intent.putExtra(DATETIME, time+","+date);
        intent.putExtra(ID, alarmId);
        intent.putExtra(TITLE, title);
        intent.putExtra(RECORD_ID, recordId);
        intent.putExtra(STATUS, status);
        intent.putExtra(VALUE, value);
        PendingIntent alarmPendingIntent;
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmPendingIntent = PendingIntent.getBroadcast(context,
                    alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        }else {
            alarmPendingIntent = PendingIntent.getBroadcast(context,
                    alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        }
        //PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(created);//System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // if alarm time has already passed, increment day by 1
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        if (!recurring) {
            String toastText = null;
            try {
                String mDate = AppUtils.dateFormate(date);
                toastText = String.format("Reminder %s scheduled for %s at %s", title, DayUtil.toDay(calendar.get(Calendar.DAY_OF_WEEK))+" "+ mDate, time);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();
            ((BaseActivity) context).errorMessage(toastText, new ErrorCallbacks() {
                @Override
                public void onOkClick() {
                    //DO something;
                }
            });

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        alarmPendingIntent);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        alarmPendingIntent);
            } else {
                alarmManager.set(
                        AlarmManager.RTC_WAKEUP,
                        calendar.getTimeInMillis(),
                        alarmPendingIntent);
            }
           /* alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    alarmPendingIntent*/
           // );
        } else {
            String toastText = String.format("Recurring Alarm %s scheduled for %s at %02d:%02d", title, getRecurringDaysText(), hour, minute, alarmId);
            //Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();
            ((BaseActivity) context).errorMessage(toastText, new ErrorCallbacks() {
                @Override
                public void onOkClick() {
                    //DO something;
                }
            });
            final long RUN_DAILY = 24 * 60 * 60 * 1000;
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    RUN_DAILY,
                    alarmPendingIntent
            );
        }

        this.started = true;
    }

    public void cancelAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        //PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        PendingIntent alarmPendingIntent;
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmPendingIntent = PendingIntent.getBroadcast(context,
                    alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        }else {
            alarmPendingIntent = PendingIntent.getBroadcast(context,
                    alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        }
        alarmManager.cancel(alarmPendingIntent);
        this.started = false;
        String toastText = String.format("Alarm cancelled for %02d:%02d with id %d", hour, minute, alarmId);
        //Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
        ((BaseActivity) context).errorMessage(toastText, new ErrorCallbacks() {
            @Override
            public void onOkClick() {
                //DO something;
            }
        });
        Log.i("cancel", toastText);
    }

    public String getRecurringDaysText() {
        if (!recurring) {
            return null;
        }

        String days = "";
        if (monday) {
            days += "Mo ";
        }
        if (tuesday) {
            days += "Tu ";
        }
        if (wednesday) {
            days += "We ";
        }
        if (thursday) {
            days += "Th ";
        }
        if (friday) {
            days += "Fr ";
        }
        if (saturday) {
            days += "Sa ";
        }
        if (sunday) {
            days += "Su ";
        }

        return days;
    }

    public String getTitle() {
        return title;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
