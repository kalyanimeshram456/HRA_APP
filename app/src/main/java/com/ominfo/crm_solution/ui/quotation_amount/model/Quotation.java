
package com.ominfo.crm_solution.ui.quotation_amount.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Quotation {

    @SerializedName("company_ID")
    private Long mCompanyID;
    @SerializedName("companyName")
    private String mCompanyName;
    @SerializedName("cust_id")
    private Long mCustId;
    @SerializedName("order_no")
    private String mOrderNo;
    @SerializedName("total_charge")
    private Long mTotalCharge;
    @SerializedName("order_status")
    private String orderStatus;

    public Long getCompanyID() {
        return mCompanyID;
    }

    public void setCompanyID(Long companyID) {
        mCompanyID = companyID;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        mCompanyName = companyName;
    }

    public Long getCustId() {
        return mCustId;
    }

    public void setCustId(Long custId) {
        mCustId = custId;
    }

    public String getOrderNo() {
        return mOrderNo;
    }

    public void setOrderNo(String orderNo) {
        mOrderNo = orderNo;
    }

    public Long getTotalCharge() {
        return mTotalCharge;
    }

    public void setTotalCharge(Long totalCharge) {
        mTotalCharge = totalCharge;
    }

}
