
package com.ominfo.hra_app.ui.salary.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalarySlipResult {

    @SerializedName("list")
    private java.util.List<SalarySlipList> mList;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public java.util.List<SalarySlipList> getList() {
        return mList;
    }

    public void setList(java.util.List<SalarySlipList> list) {
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
