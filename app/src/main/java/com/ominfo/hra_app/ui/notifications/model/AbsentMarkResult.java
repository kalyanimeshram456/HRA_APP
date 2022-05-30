
package com.ominfo.hra_app.ui.notifications.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AbsentMarkResult {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("prev_month_count")
    private String mPrevMonthCount;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("this_month_count")
    private String mThisMonthCount;

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
