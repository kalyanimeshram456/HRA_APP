
package com.ominfo.hra_app.ui.notifications.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LateMarkResult {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("prev_month_count")
    private String mPrevMonthCount;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("this_month_count")
    private String mThisMonthCount;
    @SerializedName("current_leave_deduction_count")
    private String current_leave_deduction_count;
    @SerializedName("prev_leave_deduction_count")
    private String prev_leave_deduction_count;

    public String getCurrent_leave_deduction_count() {
        return current_leave_deduction_count;
    }

    public void setCurrent_leave_deduction_count(String current_leave_deduction_count) {
        this.current_leave_deduction_count = current_leave_deduction_count;
    }

    public String getPrev_leave_deduction_count() {
        return prev_leave_deduction_count;
    }

    public void setPrev_leave_deduction_count(String prev_leave_deduction_count) {
        this.prev_leave_deduction_count = prev_leave_deduction_count;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getPrevMonthCount() {
        return mPrevMonthCount;
    }

    public void setPrevMonthCount(String prevMonthCount) {
        mPrevMonthCount = prevMonthCount;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getThisMonthCount() {
        return mThisMonthCount;
    }

    public void setThisMonthCount(String thisMonthCount) {
        mThisMonthCount = thisMonthCount;
    }

}
