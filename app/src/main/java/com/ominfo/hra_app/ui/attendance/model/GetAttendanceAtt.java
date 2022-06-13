
package com.ominfo.hra_app.ui.attendance.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetAttendanceAtt {

    @SerializedName("date")
    private String mDate;
    @SerializedName("emp_id")
    private String mEmpId;
    @SerializedName("end_longitude")
    private String mEndLongitude;
    @SerializedName("end_time")
    private String mEndTime;
    @SerializedName("id")
    private String mId;
    @SerializedName("is_late")
    private String mIsLate;
    @SerializedName("office_end_addr")
    private String mOfficeEndAddr;
    @SerializedName("office_start_addr")
    private String mOfficeStartAddr;
    @SerializedName("start_latitude")
    private String mStartLatitude;
    @SerializedName("start_longitude")
    private String mStartLongitude;
    @SerializedName("start_time")
    private String mStartTime;
    @SerializedName("disable_location")
    private String disable_location;

    public String getDisable_location() {
        return disable_location;
    }

    public void setDisable_location(String disable_location) {
        this.disable_location = disable_location;
    }

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

    public String getEndLongitude() {
        return mEndLongitude;
    }

    public void setEndLongitude(String endLongitude) {
        mEndLongitude = endLongitude;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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

    public String getStartLatitude() {
        return mStartLatitude;
    }

    public void setStartLatitude(String startLatitude) {
        mStartLatitude = startLatitude;
    }

    public String getStartLongitude() {
        return mStartLongitude;
    }

    public void setStartLongitude(String startLongitude) {
        mStartLongitude = startLongitude;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

}
