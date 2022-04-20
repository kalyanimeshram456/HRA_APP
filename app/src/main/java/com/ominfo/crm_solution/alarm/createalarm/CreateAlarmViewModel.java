package com.ominfo.crm_solution.alarm.createalarm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ominfo.crm_solution.alarm.data.Alarm;
import com.ominfo.crm_solution.alarm.data.AlarmRepository;

public class CreateAlarmViewModel extends AndroidViewModel {
    private AlarmRepository alarmRepository;

    public CreateAlarmViewModel(@NonNull Application application) {
        super(application);

        alarmRepository = new AlarmRepository(application);
    }

    public void insert(Alarm alarm) {
        alarmRepository.insert(alarm);
    }
}
