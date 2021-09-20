package com.ominfo.app.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface DBDAO {

    /*// For welcome form Data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWelcomeForm(CommonLoginResponse welcomeData);

    @Query("SELECT * FROM login")
    CommonLoginResponse getWelcomeForm();*/

}
