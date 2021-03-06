
package com.ominfo.hra_app.ui.leave.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AcceptRejectLeave {

    @SerializedName("comment")
    private String mComment;
    @SerializedName("days_diff")
    private Long mDaysDiff;
    @SerializedName("duration")
    private String mDuration;
    @SerializedName("emp_id")
    private String mEmpId;
    @SerializedName("end_time")
    private String mEndTime;
    @SerializedName("id")
    private String mId;
    @SerializedName("leave_status")
    private Object mLeaveStatus;
    @SerializedName("leave_type")
    private String mLeaveType;
    @SerializedName("profile_pic")
    private String mProfilePic;
    @SerializedName("start_time")
    private String mStartTime;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("updated_by")
    private Object mUpdatedBy;
    @SerializedName("updated_on")
    private String mUpdatedOn;
    @SerializedName("emp_name")
    private String empName;

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getComment() {
        return mComment;
    }

    public void setComment(String comment) {
        mComment = comment;
    }

    public Long getDaysDiff() {
        return mDaysDiff;
    }

    public void setDaysDiff(Long daysDiff) {
        mDaysDiff = daysDiff;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Object getLeaveStatus() {
        return mLeaveStatus;
    }

    public void setLeaveStatus(Object leaveStatus) {
        mLeaveStatus = leaveStatus;
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

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Object getUpdatedBy() {
        return mUpdatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        mUpdatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return mUpdatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        mUpdatedOn = updatedOn;
    }

}
