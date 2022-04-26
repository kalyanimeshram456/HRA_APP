package com.ominfo.crm_solution.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ominfo.crm_solution.alarm.data.Alarm;
import com.ominfo.crm_solution.ui.attendance.model.LocationPerHourTable;
import com.ominfo.crm_solution.ui.login.model.AttendanceDaysTable;
import com.ominfo.crm_solution.ui.login.model.LoginResponse;
import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.lr_number.model.GetVehicleListResult;
import com.ominfo.crm_solution.ui.lr_number.model.VehicleDetailsResultTable;
import com.ominfo.crm_solution.ui.reminders.model.ReminderModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {AttendanceDaysTable.class,LocationPerHourTable.class,LoginResponse.class,Alarm.class,LoginTable.class, ReminderModel.class,VehicleDetailsResultTable.class, GetVehicleListResult.class}, version = 1, exportSchema = false)
@TypeConverters({JsonTypeConverter.class,VehicleImagesTypeConverter.class,ToggleTypeConverter.class
,DaysTypeConverter.class,ResultConverter.class, DetailsConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DBDAO getDbDAO();
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "alarm_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }

}
