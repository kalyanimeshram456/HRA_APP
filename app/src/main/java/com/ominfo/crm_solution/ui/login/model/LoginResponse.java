
package com.ominfo.crm_solution.ui.login.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "attendance_data")
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LoginResponse {
    @ColumnInfo(name = "result")
    @Expose
    @SerializedName("result")
    private LoginResult mResult;

    @ColumnInfo(name = "details")
    @Expose
    @SerializedName("details")
    private LoginTable details;

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

    public LoginResult getResult() {
        return mResult;
    }

    public void setResult(LoginResult result) {
        mResult = result;
    }

    public LoginTable getDetails() {
        return details;
    }

    public void setDetails(LoginTable details) {
        this.details = details;
    }
}
