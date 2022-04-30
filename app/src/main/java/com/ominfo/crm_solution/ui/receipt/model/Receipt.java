
package com.ominfo.crm_solution.ui.receipt.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Receipt {

    @SerializedName("amount")
    private String mAmount;
    @SerializedName("company_ID")
    private String mCompanyID;
    @SerializedName("created_by")
    private String mCreatedBy;
    @SerializedName("created_on")
    private String mCreatedOn;
    @SerializedName("cust_id")
    private String mCustId;
    @SerializedName("cust_mobile")
    private String mCustMobile;
    @SerializedName("cust_name")
    private String mCustName;
    @SerializedName("record_id")
    private String mRecordId;
    @SerializedName("remark")
    private String mRemark;
    @SerializedName("rm_id")
    private String mRmId;
    @SerializedName("ticket_no")
    private String mTicketNo;
    @SerializedName("updated_by")
    private String mUpdatedBy;
    @SerializedName("updated_on")
    private String mUpdatedOn;
    @SerializedName("voucher_date")
    private String mVoucherDate;
    @SerializedName("voucher_type")
    private Object mVoucherType;

    public String getAmount() {
        return mAmount;
    }

    public void setAmount(String amount) {
        mAmount = amount;
    }

    public String getCompanyID() {
        return mCompanyID;
    }

    public void setCompanyID(String companyID) {
        mCompanyID = companyID;
    }

    public String getCreatedBy() {
        return mCreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        mCreatedBy = createdBy;
    }

    public String getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        mCreatedOn = createdOn;
    }

    public String getCustId() {
        return mCustId;
    }

    public void setCustId(String custId) {
        mCustId = custId;
    }

    public String getCustMobile() {
        return mCustMobile;
    }

    public void setCustMobile(String custMobile) {
        mCustMobile = custMobile;
    }

    public String getCustName() {
        return mCustName;
    }

    public void setCustName(String custName) {
        mCustName = custName;
    }

    public String getRecordId() {
        return mRecordId;
    }

    public void setRecordId(String recordId) {
        mRecordId = recordId;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String remark) {
        mRemark = remark;
    }

    public String getRmId() {
        return mRmId;
    }

    public void setRmId(String rmId) {
        mRmId = rmId;
    }

    public String getTicketNo() {
        return mTicketNo;
    }

    public void setTicketNo(String ticketNo) {
        mTicketNo = ticketNo;
    }

    public String getUpdatedBy() {
        return mUpdatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        mUpdatedBy = updatedBy;
    }

    public String getUpdatedOn() {
        return mUpdatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        mUpdatedOn = updatedOn;
    }

    public String getVoucherDate() {
        return mVoucherDate;
    }

    public void setVoucherDate(String voucherDate) {
        mVoucherDate = voucherDate;
    }

    public Object getVoucherType() {
        return mVoucherType;
    }

    public void setVoucherType(Object voucherType) {
        mVoucherType = voucherType;
    }

}
