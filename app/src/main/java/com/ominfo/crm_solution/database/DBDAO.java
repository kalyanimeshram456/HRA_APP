package com.ominfo.crm_solution.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ominfo.crm_solution.alarm.data.Alarm;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.lr_number.model.GetVehicleListResult;
import com.ominfo.crm_solution.ui.lr_number.model.VehicleDetailsResultTable;
import com.ominfo.crm_solution.ui.reminders.model.ReminderModel;

import java.util.List;


@Dao
public interface DBDAO {

    //for alarm
    @Insert
    void insert(Alarm alarm);

    @Query("DELETE FROM alarm_table")
    void deleteAll();

    @Query("SELECT * FROM alarm_table ORDER BY created ASC")
    LiveData<List<Alarm>> getAlarms();

    @Query("SELECT * FROM alarm_table where title=:title")
    Alarm getAlarmByTitle(String title);

    @Query("SELECT COUNT(*) FROM alarm_table WHERE title=:title AND date=:date AND time=:time")
    int getExistingCount(String title,String date,String time);

    @Query("SELECT * FROM alarm_table WHERE title=:title AND date=:date AND time=:time")
    Alarm getRecordDetails(String title,String date,String time);

    @Update
    void update(Alarm alarm);

    @Query("UPDATE alarm_table SET recordId = :recId,status = :status WHERE title=:title AND date=:date AND time=:time")
    void updateReminderId(String title,String date,String time,String recId,String status);

    @Query("UPDATE alarm_table SET recordId = :recId WHERE title=:title AND date=:date AND time=:time")
    void updateOnlyReminderId(String title,String date,String time,String recId);

    // For login form Data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLoginData(LoginTable loginResultTable);

    @Insert
    void insertReminderData(ReminderModel reminderModel);

    @Query("SELECT * FROM reminder_data")
    List<ReminderModel> getReminderList();

    @Query("SELECT * FROM login_data")
    LoginTable getLoginData();

    @Query("DELETE FROM login_data")
    void deleteLoginData();

    /* For vehicle details*/
    @Insert
    void insertVehicleRecord(VehicleDetailsResultTable vehicleDetailsResult);

    @Query("SELECT * FROM vehicle_details")
    List<VehicleDetailsResultTable> getVehicleList();

    /* For vehicle list*/
    @Insert
    void insertVehicleList(List<GetVehicleListResult> getVehicleListResults);

    @Query("SELECT * FROM vehicle_list")
    List<GetVehicleListResult> getVehicleDetailsList();

    @Query("DELETE FROM vehicle_list")
    void deleteVehicleDetailsList();

    @Query("DELETE FROM vehicle_details where generated_id=:id")
    void deleteVehicleDetailsbyID(String id);

}
