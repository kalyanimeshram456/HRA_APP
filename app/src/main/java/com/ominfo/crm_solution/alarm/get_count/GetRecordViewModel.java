package com.ominfo.crm_solution.alarm.get_count;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ominfo.crm_solution.alarm.data.Alarm;
import com.ominfo.crm_solution.alarm.data.AlarmRepository;

public class GetRecordViewModel extends AndroidViewModel {
    private AlarmRepository alarmRepository;
    private int mData;

    public GetRecordViewModel(@NonNull Application application) {
        super(application);

        alarmRepository = new AlarmRepository(application);
        //mData = alarmRepository.getExitsCount();
    }

    public Alarm getAlarmsRecord(String title, String date, String time) {
        return alarmRepository.getAlarmByDetails(title,date,time);
    }
}
