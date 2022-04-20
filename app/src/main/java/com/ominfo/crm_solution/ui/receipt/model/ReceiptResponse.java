
package com.ominfo.crm_solution.ui.receipt.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ReceiptResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("currentpage")
    private Long mCurrentpage;
    @SerializedName("nextpage")
    private Long mNextpage;
    @SerializedName("prevpage")
    private Long mPrevpage;
    @SerializedName("receipts")
    private List<ReceiptResult> mReceipts;
    @SerializedName("totalpages")
    private Long mTotalpages;
    @SerializedName("totalreceipts")
    private Long mTotalreceipts;
    @SerializedName("TotalAmount")
    private List<ReceiptAmount> totalAmountList;

    public List<ReceiptAmount> getTotalAmountList() {
        return totalAmountList;
    }

    public void setTotalAmountList(List<ReceiptAmount> totalAmountList) {
        this.totalAmountList = totalAmountList;
    }

    public Long getCurrentpage() {
        return mCurrentpage;
    }

    public void setCurrentpage(Long currentpage) {
        mCurrentpage = currentpage;
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

    public List<ReceiptResult> getReceipts() {
        return mReceipts;
    }

    public void setReceipts(List<ReceiptResult> receipts) {
        mReceipts = receipts;
    }

    public Long getTotalpages() {
        return mTotalpages;
    }

    public void setTotalpages(Long totalpages) {
        mTotalpages = totalpages;
    }

    public Long getTotalreceipts() {
        return mTotalreceipts;
    }

    public void setTotalreceipts(Long totalreceipts) {
        mTotalreceipts = totalreceipts;
    }
    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
