
package com.ominfo.crm_solution.ui.attendance.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LocationPerHourResult {

    @SerializedName("last_inserted_id")
    private String mLastInsertedId;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("requested_token")
    private String mRequestedToken;
    @SerializedName("status")
    private String mStatus;

    public String getLastInsertedId() {
        return mLastInsertedId;
    }

    public void setLastInsertedId(String lastInsertedId) {
        mLastInsertedId = lastInsertedId;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getRequestedToken() {
        return mRequestedToken;
    }

    public void setRequestedToken(String requestedToken) {
        mRequestedToken = requestedToken;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
