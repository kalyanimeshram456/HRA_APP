
package com.ominfo.hra_app.ui.leave.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LeaveCountResult {

    @SerializedName("lev_count_emp")
    private String mLevCountEmp;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

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
