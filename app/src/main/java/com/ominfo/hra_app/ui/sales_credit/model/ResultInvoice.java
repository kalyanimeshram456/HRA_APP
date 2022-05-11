
package com.ominfo.hra_app.ui.sales_credit.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ResultInvoice {

    @SerializedName("company_ID")
    private Long mCompanyID;
    @SerializedName("companyName")
    private String mCompanyName;
    @SerializedName("cust_id")
    private Long mCustId;
    @SerializedName("invoice_date")
    private String mInvoiceDate;
    @SerializedName("invoice_no")
    private String mInvoiceNo;
    @SerializedName("payment_status")
    private String paymentStatus;


    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

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

    public String getInvoiceDate() {
        return mInvoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        mInvoiceDate = invoiceDate;
    }

    public String getInvoiceNo() {
        return mInvoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        mInvoiceNo = invoiceNo;
    }

}
