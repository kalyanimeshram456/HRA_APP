
package com.ominfo.hra_app.ui.employees.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AttendanceEmployeeListData {

    @SerializedName("date")
    private String mDate;
    @SerializedName("emp_id")
    private String mEmpId;
    @SerializedName("end_time")
    private String mEndTime;
    @SerializedName("is_early")
    private String mIsEarly;
    @SerializedName("is_late")
    private String mIsLate;
    @SerializedName("office_end_addr")
    private String mOfficeEndAddr;
    @SerializedName("office_start_addr")
    private String mOfficeStartAddr;
    @SerializedName("start_time")
    private String mStartTime;

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getEmpId() {
        return mEmpId;
    }

    public void setEmpId(String empId) {
        mEmpId = empId;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public String getIsEarly() {
        return mIsEarly;
    }

    public void setIsEarly(String isEarly) {
        mIsEarly = isEarly;
    }

    public String getIsLate() {
        return mIsLate;
    }

    public void setIsLate(String isLate) {
        mIsLate = isLate;
    }

    public String getOfficeEndAddr() {
        return mOfficeEndAddr;
    }

    public void setOfficeEndAddr(String officeEndAddr) {
        mOfficeEndAddr = officeEndAddr;
    }

    public String getOfficeStartAddr() {
        return mOfficeStartAddr;
    }

    public void setOfficeStartAddr(String officeStartAddr) {
        mOfficeStartAddr = officeStartAddr;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

}
