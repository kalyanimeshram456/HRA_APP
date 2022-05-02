
package com.ominfo.crm_solution.ui.sale.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalesData {

    @SerializedName("company_ID")
    private String mCompanyID;
    @SerializedName("created_on")
    private String mCreatedOn;
    @SerializedName("cust_id")
    private String mCustId;
    @SerializedName("cust_name")
    private String mCustName;
    @SerializedName("order_id")
    private String mOrderId;
    @SerializedName("order_no")
    private String mOrderNo;
    @SerializedName("payment_status")
    private String mPaymentStatus;
    @SerializedName("pdf_link")
    private String mPdfLink;
    @SerializedName("rm_id")
    private String mRmId;
    @SerializedName("total_charge")
    private String mTotalCharge;

    public String getCompanyID() {
        return mCompanyID;
    }

    public void setCompanyID(String companyID) {
        mCompanyID = companyID;
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

    public String getCustName() {
        return mCustName;
    }

    public void setCustName(String custName) {
        mCustName = custName;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String orderId) {
        mOrderId = orderId;
    }

    public String getOrderNo() {
        return mOrderNo;
    }

    public void setOrderNo(String orderNo) {
        mOrderNo = orderNo;
    }

    public String getPaymentStatus() {
        return mPaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        mPaymentStatus = paymentStatus;
    }

    public String getPdfLink() {
        return mPdfLink;
    }

    public void setPdfLink(String pdfLink) {
        mPdfLink = pdfLink;
    }

    public String getRmId() {
        return mRmId;
    }

    public void setRmId(String rmId) {
        mRmId = rmId;
    }

    public String getTotalCharge() {
        return mTotalCharge;
    }

    public void setTotalCharge(String totalCharge) {
        mTotalCharge = totalCharge;
    }

}
