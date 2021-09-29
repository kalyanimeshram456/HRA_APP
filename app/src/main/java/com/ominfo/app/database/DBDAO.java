package com.ominfo.app.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ominfo.app.ui.login.model.LoginRequest;
import com.ominfo.app.ui.login.model.LoginResultTable;


@Dao
public interface DBDAO {

    // For welcome form Data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLoginData(LoginResultTable loginResultTable);

    @Query("SELECT * FROM table_login")
    LoginResultTable getLoginData();

}
