
package com.ominfo.hra_app.ui.leave.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LeaveCountResult {

    @SerializedName("lev_count")
    private String mLevCountEmp;
    @SerializedName("total_absent_late")
    private String total_absent_late;
    @SerializedName("curent_month_days")
    private String curent_month_days;
    @SerializedName("late_mark")
    private String late_mark;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("active_employees")
    private String active_employees;
    @SerializedName("total_active")
    private String total_active;

    public String getActive_employees() {
        return active_employees;
    }

    public void setActive_employees(String active_employees) {
        this.active_employees = active_employees;
    }

    public String getTotal_active() {
        return total_active;
    }

    public void setTotal_active(String total_active) {
        this.total_active = total_active;
    }

    public String getmLevCountEmp() {
        return mLevCountEmp;
    }

    public void setmLevCountEmp(String mLevCountEmp) {
        this.mLevCountEmp = mLevCountEmp;
    }

    public String getTotal_absent_late() {
        return total_absent_late;
    }

    public void setTotal_absent_late(String total_absent_late) {
        this.total_absent_late = total_absent_late;
    }

    public String getCurent_month_days() {
        return curent_month_days;
    }

    public void setCurent_month_days(String curent_month_days) {
        this.curent_month_days = curent_month_days;
    }

    public String getLate_mark() {
        return late_mark;
    }

    public void setLate_mark(String late_mark) {
        this.late_mark = late_mark;
    }

    public String getLevCountEmp() {
        return mLevCountEmp;
    }

    public void setLevCountEmp(String levCountEmp) {
        mLevCountEmp = levCountEmp;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
