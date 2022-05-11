
package com.ominfo.hra_app.ui.my_account.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ProfileImageResult {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("profileurl")
    private String mProfileurl;
    @SerializedName("status")
    private String mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getProfileurl() {
        return mProfileurl;
    }

    public void setProfileurl(String profileurl) {
        mProfileurl = profileurl;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
