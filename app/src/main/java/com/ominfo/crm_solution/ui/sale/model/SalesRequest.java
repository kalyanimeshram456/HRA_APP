
package com.ominfo.crm_solution.ui.sale.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalesRequest {

    @SerializedName("companyName")
    private List<String> mCompanyId;
    @SerializedName("EndDate")
    private String mEndDate;
    @SerializedName("InvoiceMaxAmount")
    private String mInvoiceMaxAmount;
    @SerializedName("InvoiceMinAmount")
    private String mInvoiceMinAmount;
    @SerializedName("InvoiceNumber")
    private String mInvoiceNumber;
    @SerializedName("pageno")
    private String mPageno;
    @SerializedName("pagesize")
    private String mPagesize;
    @SerializedName("PaymentStatus")
    private String mPaymentStatus;
    @SerializedName("Rm")
    private List<String> mRm;
    @SerializedName("Startdate")
    private String mStartdate;

    public List<String> getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(List<String> companyId) {
        mCompanyId = companyId;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public String getInvoiceMaxAmount() {
        return mInvoiceMaxAmount;
    }

    public void setInvoiceMaxAmount(String invoiceMaxAmount) {
        mInvoiceMaxAmount = invoiceMaxAmount;
    }

    public String getInvoiceMinAmount() {
        return mInvoiceMinAmount;
    }

    public void setInvoiceMinAmount(String invoiceMinAmount) {
        mInvoiceMinAmount = invoiceMinAmount;
    }

    public String getInvoiceNumber() {
        return mInvoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        mInvoiceNumber = invoiceNumber;
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

    public String getPaymentStatus() {
        return mPaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        mPaymentStatus = paymentStatus;
    }

    public List<String> getRm() {
        return mRm;
    }

    public void setRm(List<String> rm) {
        mRm = rm;
    }

    public String getStartdate() {
        return mStartdate;
    }

    public void setStartdate(String startdate) {
        mStartdate = startdate;
    }

}
