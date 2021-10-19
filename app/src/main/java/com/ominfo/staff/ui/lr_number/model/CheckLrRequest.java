
package com.ominfo.staff.ui.lr_number.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CheckLrRequest {

    @SerializedName("Lr_no")
    private String mLrNo;
    @SerializedName("userkey")
    private String mUserkey;

    public String getLrNo() {
        return mLrNo;
    }

    public void setLrNo(String lrNo) {
        mLrNo = lrNo;
    }

    public String getUserkey() {
        return mUserkey;
    }

    public void setUserkey(String userkey) {
        mUserkey = userkey;
    }

}
