
package com.ominfo.crm_solution.ui.quotation_amount.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class QuotationResponse {

    @SerializedName("currentpage")
    private Long mCurrentpage;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("nextpage")
    private Long mNextpage;
    @SerializedName("prevpage")
    private Long mPrevpage;
    @SerializedName("quotations")
    private List<Quotation> mQuotations;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("totalquotations")
    private Long mTotalQuotations;
    @SerializedName("totalpages")
    private Long mTotalpages;
    @SerializedName("Totalamount")
    private List<QuotaionAmount> totalamount;

    public List<QuotaionAmount> getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(List<QuotaionAmount> totalamount) {
        this.totalamount = totalamount;
    }

    public Long getCurrentpage() {
        return mCurrentpage;
    }

    public void setCurrentpage(Long currentpage) {
        mCurrentpage = currentpage;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getNextpage() {
        return mNextpage;
    }

    public void setNextpage(Long nextpage) {
        mNextpage = nextpage;
    }

    public Long getPrevpage() {
        return mPrevpage;
    }

    public void setPrevpage(Long prevpage) {
        mPrevpage = prevpage;
    }

    public List<Quotation> getQuotations() {
        return mQuotations;
    }

    public void setQuotations(List<Quotation> quotations) {
        mQuotations = quotations;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public Long getTotalQuotations() {
        return mTotalQuotations;
    }

    public void setTotalQuotations(Long totalQuotations) {
        mTotalQuotations = totalQuotations;
    }

    public Long getTotalpages() {
        return mTotalpages;
    }

    public void setTotalpages(Long totalpages) {
        mTotalpages = totalpages;
    }

}
