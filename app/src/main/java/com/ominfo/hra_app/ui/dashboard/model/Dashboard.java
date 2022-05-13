
package com.ominfo.hra_app.ui.dashboard.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
import com.ominfo.hra_app.ui.login.model.DayData;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Dashboard {

    @SerializedName("emp_data")
    private DayData loginDays;
    @SerializedName("enquiry_count")
    private String mEnquiryCount;
    @SerializedName("lost_opportunity_count")
    private Long mLostOpportunityCount;
    @SerializedName("notification_count")
    private String mNotificationCount;
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

    public DayData getLoginDays() {
        return loginDays;
    }

    public void setLoginDays(DayData loginDays) {
        this.loginDays = loginDays;
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

    public String getNotificationCount() {
        return mNotificationCount;
    }

    public void setNotificationCount(String notificationCount) {
        mNotificationCount = notificationCount;
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
