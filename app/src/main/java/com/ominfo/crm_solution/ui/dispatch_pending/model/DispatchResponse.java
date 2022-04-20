
package com.ominfo.crm_solution.ui.dispatch_pending.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DispatchResponse {

    @SerializedName("currentpage")
    private Long mCurrentpage;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("nextpage")
    private Long mNextpage;
    @SerializedName("pendingorders")
    private List<DispatchResult> mPendingorders;
    @SerializedName("prevpage")
    private Long mPrevpage;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("totalpages")
    private Long mTotalpages;
    @SerializedName("totalpendingorders")
    private Long mTotalpendingorders;

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

    public List<DispatchResult> getPendingorders() {
        return mPendingorders;
    }

    public void setPendingorders(List<DispatchResult> pendingorders) {
        mPendingorders = pendingorders;
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

    public Long getTotalpages() {
        return mTotalpages;
    }

    public void setTotalpages(Long totalpages) {
        mTotalpages = totalpages;
    }

    public Long getTotalpendingorders() {
        return mTotalpendingorders;
    }

    public void setTotalpendingorders(Long totalpendingorders) {
        mTotalpendingorders = totalpendingorders;
    }

}
