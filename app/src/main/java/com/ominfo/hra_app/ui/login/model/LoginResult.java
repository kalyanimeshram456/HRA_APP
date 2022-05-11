
package com.ominfo.hra_app.ui.login.model;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LoginResult {

    @SerializedName("message")
    private String mMessage;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("day_data")
    private LoginDays dayData;

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

    public LoginDays getDayData() {
        return dayData;
    }

    public void setDayData(LoginDays dayData) {
        this.dayData = dayData;
    }

}
