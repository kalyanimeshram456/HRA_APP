
package com.ominfo.hra_app.ui.attendance.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Entity(tableName = "location_data")
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LocationPerHourTable {
    @ColumnInfo(name = "action")
    @Expose
    @SerializedName("action")
    private String action;

    @ColumnInfo(name = "emp_id")
    @Expose
    @SerializedName("emp_id")
    private String empId;

    @ColumnInfo(name = "date")
    @Expose
    @SerializedName("date")
    private String date;

    @ColumnInfo(name = "longitude")
    @Expose
    @SerializedName("longitude")
    private String longitude;

    @ColumnInfo(name = "latitude")
    @Expose
    @SerializedName("latitude")
    private String latitude;

    @ColumnInfo(name = "startTime")
    @Expose
    @SerializedName("startTime")
    private String startTime;

    @ColumnInfo(name = "requested_token")
    @Expose
    @SerializedName("requested_token")
    private String requestedToken;

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "ID")
    @Expose
    @SerializedName("ID")
    private int id;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getRequestedToken() {
        return requestedToken;
    }

    public void setRequestedToken(String requestedToken) {
        this.requestedToken = requestedToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
