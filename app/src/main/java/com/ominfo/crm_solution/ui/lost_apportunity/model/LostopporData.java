
package com.ominfo.crm_solution.ui.lost_apportunity.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LostopporData {

    @SerializedName("cust_id")
    private String mCustId;
    @SerializedName("cust_mob")
    private String mCustMob;
    @SerializedName("cust_name")
    private String mCustName;
    @SerializedName("date")
    private String mDate;
    @SerializedName("docno")
    private String mDocno;
    @SerializedName("reason")
    private String mReason;
    @SerializedName("remarks")
    private String mRemarks;
    @SerializedName("rm")
    private String mRm;
    @SerializedName("status")
    private String mStatus;

    public String getCustId() {
        return mCustId;
    }

    public void setCustId(String custId) {
        mCustId = custId;
    }

    public String getCustMob() {
        return mCustMob;
    }

    public void setCustMob(String custMob) {
        mCustMob = custMob;
    }

    public String getCustName() {
        return mCustName;
    }

    public void setCustName(String custName) {
        mCustName = custName;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getDocno() {
        return mDocno;
    }

    public void setDocno(String docno) {
        mDocno = docno;
    }

    public String getReason() {
        return mReason;
    }

    public void setReason(String reason) {
        mReason = reason;
    }

    public String getRemarks() {
        return mRemarks;
    }

    public void setRemarks(String remarks) {
        mRemarks = remarks;
    }

    public String getRm() {
        return mRm;
    }

    public void setRm(String rm) {
        mRm = rm;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
