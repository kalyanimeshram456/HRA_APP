
package com.ominfo.hra_app.ui.login.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
@Entity(tableName = "attendance_data")
public class LoginResponse {

    @ColumnInfo(name = "details")
    @Expose
    @SerializedName("details")
    private LoginTable mDetails;

    @ColumnInfo(name = "result")
    @Expose
    @SerializedName("result")
    private LoginResult mResult;

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


    public LoginTable getDetails() {
        return mDetails;
    }

    public void setDetails(LoginTable details) {
        mDetails = details;
    }

    public LoginResult getResult() {
        return mResult;
    }

    public void setResult(LoginResult result) {
        mResult = result;
    }

}
