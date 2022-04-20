
package com.ominfo.crm_solution.ui.sale.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalesResponse {

    @SerializedName("currentpage")
    private Long mCurrentpage;
    @SerializedName("Invoices")
    private List<ResultInvoice> mInvoices;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("nextpage")
    private Long mNextpage;
    @SerializedName("prevpage")
    private Long mPrevpage;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("totalInvoices")
    private Long mTotalInvoices;
    @SerializedName("totalpages")
    private Long mTotalpages;

    public Long getCurrentpage() {
        return mCurrentpage;
    }

    public void setCurrentpage(Long currentpage) {
        mCurrentpage = currentpage;
    }

    public List<ResultInvoice> getInvoices() {
        return mInvoices;
    }

    public void setInvoices(List<ResultInvoice> invoices) {
        mInvoices = invoices;
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

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public Long getTotalInvoices() {
        return mTotalInvoices;
    }

    public void setTotalInvoices(Long totalenquiries) {
        mTotalInvoices = totalenquiries;
    }

    public Long getTotalpages() {
        return mTotalpages;
    }

    public void setTotalpages(Long totalpages) {
        mTotalpages = totalpages;
    }

}
