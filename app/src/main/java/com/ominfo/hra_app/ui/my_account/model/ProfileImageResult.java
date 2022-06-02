
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
    @SerializedName("emp_name")
    private String emp_name;
    @SerializedName("emp_position")
    private String emp_position;
    @SerializedName("emp_username")
    private String emp_username;
    @SerializedName("status")
    private String mStatus;

    public String getEmp_username() {
        return emp_username;
    }

    public void setEmp_username(String emp_username) {
        this.emp_username = emp_username;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_position() {
        return emp_position;
    }

    public void setEmp_position(String emp_position) {
        this.emp_position = emp_position;
    }

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
