package com.ominfo.hra_app.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ominfo.hra_app.ui.attendance.model.LocationPerHourTable;
import com.ominfo.hra_app.ui.login.model.AttendanceDaysTable;
import com.ominfo.hra_app.ui.login.model.LoginTable;


@Dao
public interface DBDAO {

    // For login form Data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLoginData(LoginTable loginResultTable);

    //for locations list
    @Insert
    void insertLocationPerHour(LocationPerHourTable location);

    // For login Attendance Data
   /* @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAttendanceData(LoginResponse loginResponse);*/

    // For login Attendance test Data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAttendanceData(AttendanceDaysTable attendanceDaysTable);

    @Query("SELECT * FROM login_data")
    LoginTable getLoginData();

 /*   @Query("SELECT * FROM attendance_data")
    LoginResponse getAttendanceData();*/

    @Query("SELECT * FROM test_attendance_data")
    AttendanceDaysTable getTestAttendanceData();

    @Query("DELETE FROM login_data")
    void deleteLoginData();

    @Query("DELETE FROM test_attendance_data")
    void deleteAttendanceData();

    @Query("DELETE FROM location_data")
    void deleteLocationData();

    @Query("DELETE FROM location_data where requested_token=:id")
    void deleteLocationById(String id);

    @Query("SELECT COUNT(*) FROM location_data")
    int getCountLocation();


}
