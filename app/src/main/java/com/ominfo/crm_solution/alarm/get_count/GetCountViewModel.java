package com.ominfo.crm_solution.alarm.get_count;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ominfo.crm_solution.alarm.data.Alarm;
import com.ominfo.crm_solution.alarm.data.AlarmRepository;

import java.util.List;

public class GetCountViewModel extends AndroidViewModel {
    private AlarmRepository alarmRepository;
    private int mData;

    public GetCountViewModel(@NonNull Application application) {
        super(application);

        alarmRepository = new AlarmRepository(application);
        //mData = alarmRepository.getExitsCount();
    }

    public int getAlarmsCount(String title,String date,String time) {
        return alarmRepository.getExitsCount(title,date,time);
    }
}
