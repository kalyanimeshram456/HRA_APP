package com.ominfo.hra_app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ominfo.hra_app.ui.attendance.model.LocationPerHourTable;
import com.ominfo.hra_app.ui.login.model.AttendanceDaysTable;
import com.ominfo.hra_app.ui.login.model.LoginResponse;
import com.ominfo.hra_app.ui.login.model.LoginTable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {AttendanceDaysTable.class,LocationPerHourTable.class,LoginResponse.class,LoginTable.class}, version = 1, exportSchema = false)
@TypeConverters({JsonTypeConverter.class,DaysTypeConverter.class,ResultConverter.class, DetailsConverter.class})
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
