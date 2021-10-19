
package com.ominfo.staff.ui.lr_number.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetVehicleListRequest {

    @SerializedName("FromDate")
    private String mFromDate;
    @SerializedName("ToDate")
    private String mToDate;
    @SerializedName("userkey")
    private String mUserkey;

    public String getFromDate() {
        return mFromDate;
    }

    public void setFromDate(String fromDate) {
        mFromDate = fromDate;
    }

    public String getToDate() {
        return mToDate;
    }

    public void setToDate(String toDate) {
        mToDate = toDate;
    }

    public String getUserkey() {
        return mUserkey;
    }

    public void setUserkey(String userkey) {
        mUserkey = userkey;
    }

}
