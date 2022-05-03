
package com.ominfo.crm_solution.ui.dispatch_pending.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DispatchData {

    @SerializedName("company_name")
    private String mCompanyName;
    @SerializedName("cust_id")
    private String mCustId;
    @SerializedName("emp_id")
    private String mEmpId;
    @SerializedName("emp_name")
    private String mEmpName;
    @SerializedName("emp_profile_pic")
    private String mEmpProfilePic;
    @SerializedName("order_accept_date")
    private String mOrderAcceptDate;
    @SerializedName("order_id")
    private String mOrderId;
    @SerializedName("order_no")
    private String mOrderNo;
    @SerializedName("order_status")
    private String mOrderStatus;
    @SerializedName("pdf")
    private String mPdf;
    @SerializedName("pendqty")
    private Long mPendqty;
    @SerializedName("po_number")
    private String mPoNumber;
    @SerializedName("total_charge")
    private String mTotalCharge;

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        mCompanyName = companyName;
    }

    public String getCustId() {
        return mCustId;
    }

    public void setCustId(String custId) {
        mCustId = custId;
    }

    public String getEmpId() {
        return mEmpId;
    }

    public void setEmpId(String empId) {
        mEmpId = empId;
    }

    public String getEmpName() {
        return mEmpName;
    }

    public void setEmpName(String empName) {
        mEmpName = empName;
    }

    public String getEmpProfilePic() {
        return mEmpProfilePic;
    }

    public void setEmpProfilePic(String empProfilePic) {
        mEmpProfilePic = empProfilePic;
    }

    public String getOrderAcceptDate() {
        return mOrderAcceptDate;
    }

    public void setOrderAcceptDate(String orderAcceptDate) {
        mOrderAcceptDate = orderAcceptDate;
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

    public String getOrderStatus() {
        return mOrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        mOrderStatus = orderStatus;
    }

    public String getPdf() {
        return mPdf;
    }

    public void setPdf(String pdf) {
        mPdf = pdf;
    }

    public Long getPendqty() {
        return mPendqty;
    }

    public void setPendqty(Long pendqty) {
        mPendqty = pendqty;
    }

    public String getPoNumber() {
        return mPoNumber;
    }

    public void setPoNumber(String poNumber) {
        mPoNumber = poNumber;
    }

    public String getTotalCharge() {
        return mTotalCharge;
    }

    public void setTotalCharge(String totalCharge) {
        mTotalCharge = totalCharge;
    }

}
