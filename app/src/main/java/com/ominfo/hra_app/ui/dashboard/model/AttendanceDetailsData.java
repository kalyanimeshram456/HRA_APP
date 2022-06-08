
package com.ominfo.hra_app.ui.dashboard.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AttendanceDetailsData {

    @SerializedName("emp_id")
    private String mEmpId;
    @SerializedName("emp_name")
    private String mEmpName;
    @SerializedName("emp_position")
    private String mEmpPosition;
    @SerializedName("end_time")
    private String mEndTime;
    @SerializedName("leave_type")
    private String mLeaveType;
    @SerializedName("profile_pic")
    private String mProfilePic;
    @SerializedName("start_time")
    private String mStartTime;
    @SerializedName("is_late")
    private String is_late;
    @SerializedName("is_early")
    private String is_early;

    public String getIs_late() {
        return is_late;
    }

    public void setIs_late(String is_late) {
        this.is_late = is_late;
    }

    public String getIs_early() {
        return is_early;
    }

    public void setIs_early(String is_early) {
        this.is_early = is_early;
    }

    public String getEmpId() {
        return mEmpId;
    }

    public void setEmpId(String empId) {
        mEmpId = empId;
    }

    public String getEmpName() {
        return mEmpName;
    }

    public void setEmpName(String empName) {
        mEmpName = empName;
    }

    public String getEmpPosition() {
        return mEmpPosition;
    }

    public void setEmpPosition(String empPosition) {
        mEmpPosition = empPosition;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public String getLeaveType() {
        return mLeaveType;
    }

    public void setLeaveType(String leaveType) {
        mLeaveType = leaveType;
    }

    public String getProfilePic() {
        return mProfilePic;
    }

    public void setProfilePic(String profilePic) {
        mProfilePic = profilePic;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

}
