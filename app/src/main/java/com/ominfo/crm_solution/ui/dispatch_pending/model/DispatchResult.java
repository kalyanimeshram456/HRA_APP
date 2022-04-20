
package com.ominfo.crm_solution.ui.dispatch_pending.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DispatchResult {

    @SerializedName("company_ID")
    private Long mCompanyID;
    @SerializedName("companyName")
    private String mCompanyName;
    @SerializedName("cust_id")
    private Long mCustId;
    @SerializedName("order_status")
    private String mOrderStatus;
    @SerializedName("po_number")
    private String mPoNumber;
    @SerializedName("total_charge")
    private Long mTotalCharge;

    public Long getCompanyID() {
        return mCompanyID;
    }

    public void setCompanyID(Long companyID) {
        mCompanyID = companyID;
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

    public String getOrderStatus() {
        return mOrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        mOrderStatus = orderStatus;
    }

    public String getPoNumber() {
        return mPoNumber;
    }

    public void setPoNumber(String poNumber) {
        mPoNumber = poNumber;
    }

    public Long getTotalCharge() {
        return mTotalCharge;
    }

    public void setTotalCharge(Long totalCharge) {
        mTotalCharge = totalCharge;
    }

}
