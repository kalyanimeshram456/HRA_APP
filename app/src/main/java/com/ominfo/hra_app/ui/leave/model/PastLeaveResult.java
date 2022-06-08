
package com.ominfo.hra_app.ui.leave.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class PastLeaveResult {

    @SerializedName("currentpage")
    private String mCurrentpage;
    @SerializedName("leave")
    private List<PastLeaveList> mLeave;
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
    private Object mTotalrows;

    public String getCurrentpage() {
        return mCurrentpage;
    }

    public void setCurrentpage(String currentpage) {
        mCurrentpage = currentpage;
    }

    public List<PastLeaveList> getLeave() {
        return mLeave;
    }

    public void setLeave(List<PastLeaveList> leave) {
        mLeave = leave;
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

    public Object getTotalrows() {
        return mTotalrows;
    }

    public void setTotalrows(Object totalrows) {
        mTotalrows = totalrows;
    }

}
