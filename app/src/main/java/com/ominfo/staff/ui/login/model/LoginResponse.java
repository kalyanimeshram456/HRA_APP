
package com.ominfo.staff.ui.login.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LoginResponse {

    @SerializedName("Message")
    private String mMessage;
    @SerializedName("Result")
    private LoginResultTable mResult;
    @SerializedName("Status")
    private String mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public LoginResultTable getResult() {
        return mResult;
    }

    public void setResult(LoginResultTable result) {
        mResult = result;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
