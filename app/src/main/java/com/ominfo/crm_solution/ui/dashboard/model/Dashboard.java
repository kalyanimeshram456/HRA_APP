
package com.ominfo.crm_solution.ui.dashboard.model;

import com.google.gson.annotations.SerializedName;
import com.ominfo.crm_solution.ui.login.model.LoginDays;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Dashboard {

    @SerializedName("enquiry_count")
    private String mEnquiryCount;
    @SerializedName("lost_opportunity_count")
    private Long mLostOpportunityCount;
    @SerializedName("pending_dispatch_count")
    private String mPendingDispatchCount;
    @SerializedName("quotation_amount")
    private String mQuotationAmount;
    @SerializedName("quotation_count")
    private String mQuotationCount;
    @SerializedName("receipt_amount")
    private String mReceiptAmount;
    @SerializedName("sales_credit")
    private Long mSalesCredit;
    @SerializedName("total_sales")
    private String mTotalSales;
    @SerializedName("visit_report")
    private String mVisitReport;
    @SerializedName("notification_count")
    private String notificationCount;

    @SerializedName("emp_data")
    private LoginDays loginDays;

    public LoginDays getLoginDays() {
        return loginDays;
    }

    public void setLoginDays(LoginDays loginDays) {
        this.loginDays = loginDays;
    }

    public String getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(String notificationCount) {
        this.notificationCount = notificationCount;
    }

    public String getEnquiryCount() {
        return mEnquiryCount;
    }

    public void setEnquiryCount(String enquiryCount) {
        mEnquiryCount = enquiryCount;
    }

    public Long getLostOpportunityCount() {
        return mLostOpportunityCount;
    }

    public void setLostOpportunityCount(Long lostOpportunityCount) {
        mLostOpportunityCount = lostOpportunityCount;
    }


    public String getPendingDispatchCount() {
        return mPendingDispatchCount;
    }

    public void setPendingDispatchCount(String pendingDispatchCount) {
        mPendingDispatchCount = pendingDispatchCount;
    }

    public String getQuotationAmount() {
        return mQuotationAmount;
    }

    public void setQuotationAmount(String quotationAmount) {
        mQuotationAmount = quotationAmount;
    }

    public String getQuotationCount() {
        return mQuotationCount;
    }

    public void setQuotationCount(String quotationCount) {
        mQuotationCount = quotationCount;
    }

    public String getReceiptAmount() {
        return mReceiptAmount;
    }

    public void setReceiptAmount(String receiptAmount) {
        mReceiptAmount = receiptAmount;
    }

    public Long getSalesCredit() {
        return mSalesCredit;
    }

    public void setSalesCredit(Long salesCredit) {
        mSalesCredit = salesCredit;
    }

    public String getTotalSales() {
        return mTotalSales;
    }

    public void setTotalSales(String totalSales) {
        mTotalSales = totalSales;
    }

    public String getVisitReport() {
        return mVisitReport;
    }

    public void setVisitReport(String visitReport) {
        mVisitReport = visitReport;
    }

}
