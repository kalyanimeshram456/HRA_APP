
package com.ominfo.hra_app.ui.login.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Entity(tableName = "test_attendance_data")
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AttendanceDaysTable {
    @ColumnInfo(name = "LoginDays")
    @Expose
    @SerializedName("LoginDays")
    private LoginDays loginDays;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ID")
    @Expose
    @SerializedName("ID")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LoginDays getLoginDays() {
        return loginDays;
    }

    public void setLoginDays(LoginDays loginDays) {
        this.loginDays = loginDays;
    }
}
