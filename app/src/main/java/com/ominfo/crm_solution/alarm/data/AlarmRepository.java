package com.ominfo.crm_solution.alarm.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ominfo.crm_solution.database.AppDatabase;
import com.ominfo.crm_solution.database.DBDAO;
import android.app.Application;
import androidx.lifecycle.LiveData;
import java.util.List;

import java.util.List;

public class AlarmRepository {
    private DBDAO alarmDao;
    private LiveData<List<Alarm>> alarmsLiveData;
    private int mCount;

    public AlarmRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        alarmDao = db.getDbDAO();
        alarmsLiveData = alarmDao.getAlarms();
    }

    public void insert(Alarm alarm) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.insert(alarm);
        });
    }

    public void update(Alarm alarm) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.update(alarm);
        });
    }

    public void deleteRemiders() {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.deleteAll();
        });
    }

    public void updateRecordId(String title,String date,String time,String recId,String status) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.updateReminderId(title,date,time,recId,status);
        });
    }
    public void updateOnlyRecordId(String title,String date,String time,String recId) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.updateOnlyReminderId(title,date,time,recId);
        });
    }

    public Alarm updateAlarmStatus(String title) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.getAlarmByTitle(title);
        });
        return alarmDao.getAlarmByTitle(title);
    }

    public LiveData<List<Alarm>> getAlarmsLiveData() {
        return alarmsLiveData;
    }

    public int getExitsCount(String title,String date,String time) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.getExistingCount(title,date,time);
        });
        return  alarmDao.getExistingCount(title,date,time);
    }

    public Alarm getAlarmByDetails(String title,String date,String time) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            alarmDao.getRecordDetails(title,date,time);
        });
        return  alarmDao.getRecordDetails(title,date,time);
    }
}
