
package com.ominfo.hra_app.ui.dashboard.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class BirthDayResult {

    @SerializedName("dobdata")
    private List<BirthDayDobdatum> mDobdata;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public List<BirthDayDobdatum> getDobdata() {
        return mDobdata;
    }

    public void setDobdata(List<BirthDayDobdatum> dobdata) {
        mDobdata = dobdata;
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
