package com.ominfo.app.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.ominfo.app.ui.login.model.CommonLoginResponse;
import com.ominfo.app.ui.login.model.LoginResponse;
import com.ominfo.app.ui.login.model.LoginResultTable;

@Database(entities = {LoginResultTable.class}, version = 2, exportSchema = false)
@TypeConverters({JsonTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract DBDAO getDbDAO();


}
