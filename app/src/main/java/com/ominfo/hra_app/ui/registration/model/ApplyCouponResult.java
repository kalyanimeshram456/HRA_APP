
package com.ominfo.hra_app.ui.registration.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ApplyCouponResult {

    @SerializedName("list")
    private ApplyCouponList mList;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public ApplyCouponList getList() {
        return mList;
    }

    public void setList(ApplyCouponList list) {
        mList = list;
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
