
package com.ominfo.crm_solution.ui.receipt.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ReceiptRequest {

    @SerializedName("companyName")
    private List<String> mCompanyID;
    @SerializedName("EndDate")
    private String mEndDate;
    @SerializedName("MaxAmount")
    private String mMaxAmount;
    @SerializedName("MinAmount")
    private String mMinAmount;
    @SerializedName("pageno")
    private String mPageno;
    @SerializedName("pagesize")
    private String mPagesize;
    @SerializedName("receiptNo")
    private String mReceiptNo;
    @SerializedName("Startdate")
    private String mStartdate;

    public List<String> getCompanyID() {
        return mCompanyID;
    }

    public void setCompanyID(List<String> companyID) {
        mCompanyID = companyID;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public String getMaxAmount() {
        return mMaxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        mMaxAmount = maxAmount;
    }

    public String getMinAmount() {
        return mMinAmount;
    }

    public void setMinAmount(String minAmount) {
        mMinAmount = minAmount;
    }

    public String getPageno() {
        return mPageno;
    }

    public void setPageno(String pageno) {
        mPageno = pageno;
    }

    public String getPagesize() {
        return mPagesize;
    }

    public void setPagesize(String pagesize) {
        mPagesize = pagesize;
    }

    public String getReceiptNo() {
        return mReceiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        mReceiptNo = receiptNo;
    }

    public String getStartdate() {
        return mStartdate;
    }

    public void setStartdate(String startdate) {
        mStartdate = startdate;
    }

}
