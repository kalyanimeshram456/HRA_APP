
package com.ominfo.crm_solution.ui.quotation_amount.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class QuotationRequest {

    @SerializedName("companyName")
    private List<String> mCompanyId;
    @SerializedName("EndDate")
    private String mEndDate;
    @SerializedName("pageno")
    private String mPageno;
    @SerializedName("pagesize")
    private String mPagesize;
    @SerializedName("QuotationMaxAmount")
    private String mQuotationMaxAmount;
    @SerializedName("QuotationMinAmount")
    private String mQuotationMinAmount;
    @SerializedName("QuotationNumber")
    private String mQuotationNumber;
    @SerializedName("QuotationStatus")
    private String mQuotationStatus;
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

    public String getQuotationMaxAmount() {
        return mQuotationMaxAmount;
    }

    public void setQuotationMaxAmount(String quotationMaxAmount) {
        mQuotationMaxAmount = quotationMaxAmount;
    }

    public String getQuotationMinAmount() {
        return mQuotationMinAmount;
    }

    public void setQuotationMinAmount(String quotationMinAmount) {
        mQuotationMinAmount = quotationMinAmount;
    }

    public String getQuotationNumber() {
        return mQuotationNumber;
    }

    public void setQuotationNumber(String quotationNumber) {
        mQuotationNumber = quotationNumber;
    }

    public String getQuotationStatus() {
        return mQuotationStatus;
    }

    public void setQuotationStatus(String quotationStatus) {
        mQuotationStatus = quotationStatus;
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
