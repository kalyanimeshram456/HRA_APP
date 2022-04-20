package com.ominfo.crm_solution.alarm.alarmslist;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ominfo.crm_solution.alarm.data.Alarm;
import com.ominfo.crm_solution.alarm.data.AlarmRepository;

import java.util.List;

public class AlarmsListViewModel extends AndroidViewModel {
    private AlarmRepository alarmRepository;
    private LiveData<List<Alarm>> alarmsLiveData;

    public AlarmsListViewModel(@NonNull Application application) {
        super(application);

        alarmRepository = new AlarmRepository(application);
        alarmsLiveData = alarmRepository.getAlarmsLiveData();
    }

    public void update(Alarm alarm) {
        alarmRepository.update(alarm);
    }

    public Alarm getAlarmByTitle(String title) {
        return alarmRepository.updateAlarmStatus(title);
    }

    public LiveData<List<Alarm>> getAlarmsLiveData() {
        return alarmsLiveData;
    }
}
