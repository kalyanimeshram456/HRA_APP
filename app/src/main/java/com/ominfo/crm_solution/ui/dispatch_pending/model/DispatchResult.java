
package com.ominfo.crm_solution.ui.dispatch_pending.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class DispatchResult {

    @SerializedName("currentpage")
    private String mCurrentpage;
    @SerializedName("dispatch")
    private List<DispatchData> mDispatch;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("nextpage")
    private Long mNextpage;
    @SerializedName("prevpage")
    private Long mPrevpage;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("totalpages")
    private Long mTotalpages;
    @SerializedName("totalrows")
    private String mTotalrows;

    public String getCurrentpage() {
        return mCurrentpage;
    }

    public void setCurrentpage(String currentpage) {
        mCurrentpage = currentpage;
    }

    public List<DispatchData> getDispatch() {
        return mDispatch;
    }

    public void setDispatch(List<DispatchData> dispatch) {
        mDispatch = dispatch;
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

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Long getTotalpages() {
        return mTotalpages;
    }

    public void setTotalpages(Long totalpages) {
        mTotalpages = totalpages;
    }

    public String getTotalrows() {
        return mTotalrows;
    }

    public void setTotalrows(String totalrows) {
        mTotalrows = totalrows;
    }

}
