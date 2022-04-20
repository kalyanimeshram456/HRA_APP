package com.ominfo.crm_solution.alarm.get_count;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ominfo.crm_solution.alarm.data.AlarmRepository;

public class DeleteReminderViewModel extends AndroidViewModel {
    private AlarmRepository alarmRepository;
    private int mData;

    public DeleteReminderViewModel(@NonNull Application application) {
        super(application);

        alarmRepository = new AlarmRepository(application);
        //mData = alarmRepository.getExitsCount();
    }

    public void DeleteReminder() {
         alarmRepository.deleteRemiders();
    }
}
