package com.ominfo.crm_solution.alarm.get_count;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ominfo.crm_solution.alarm.data.AlarmRepository;

public class UpdateRecordIdViewModel extends AndroidViewModel {
    private AlarmRepository alarmRepository;
    private int mData;

    public UpdateRecordIdViewModel(@NonNull Application application) {
        super(application);

        alarmRepository = new AlarmRepository(application);
        //mData = alarmRepository.getExitsCount();
    }

    public void updateRecordId(String title,String date,String time,String recId,String status) {
         alarmRepository.updateRecordId(title,date,time,recId,status);
    }
}
