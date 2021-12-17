package com.ominfo.crm_solution.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ominfo.crm_solution.ui.login.model.LoginTable;
import com.ominfo.crm_solution.ui.lr_number.model.GetVehicleListResult;
import com.ominfo.crm_solution.ui.lr_number.model.VehicleDetailsResultTable;

@Database(entities = {LoginTable.class, VehicleDetailsResultTable.class, GetVehicleListResult.class}, version = 1, exportSchema = false)
@TypeConverters({JsonTypeConverter.class,VehicleImagesTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DBDAO getDbDAO();


}
