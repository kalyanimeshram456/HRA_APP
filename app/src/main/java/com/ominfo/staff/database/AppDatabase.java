package com.ominfo.staff.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ominfo.staff.ui.login.model.LoginResultTable;
import com.ominfo.staff.ui.lr_number.model.GetVehicleListResult;
import com.ominfo.staff.ui.lr_number.model.VehicleDetailsResultTable;

@Database(entities = {LoginResultTable.class, VehicleDetailsResultTable.class, GetVehicleListResult.class}, version = 1, exportSchema = false)
@TypeConverters({JsonTypeConverter.class,VehicleImagesTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DBDAO getDbDAO();


}
