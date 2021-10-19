package com.ominfo.staff.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ominfo.staff.ui.login.model.LoginResultTable;
import com.ominfo.staff.ui.lr_number.model.GetVehicleListResponse;
import com.ominfo.staff.ui.lr_number.model.GetVehicleListResult;
import com.ominfo.staff.ui.lr_number.model.VehicleDetailsResultTable;

import java.util.List;


@Dao
public interface DBDAO {

    // For login form Data
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertLoginData(LoginResultTable loginResultTable);

    @Query("SELECT * FROM table_login")
    LoginResultTable getLoginData();

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
