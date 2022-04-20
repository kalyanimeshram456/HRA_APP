
package com.ominfo.crm_solution.ui.lost_apportunity.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LostApportunityResult {

    @SerializedName("currentpage")
    private String mCurrentpage;
    @SerializedName("lostoppor")
    private List<LostopporData> mLostoppor;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("nextpage")
    private Long mNextpage;
    @SerializedName("prevpage")
    private Long mPrevpage;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("totalenquiries")
    private Long mTotalenquiries;
    @SerializedName("totalpages")
    private Long mTotalpages;

    public String getCurrentpage() {
        return mCurrentpage;
    }

    public void setCurrentpage(String currentpage) {
        mCurrentpage = currentpage;
    }

    public List<LostopporData> getLostoppor() {
        return mLostoppor;
    }

    public void setLostoppor(List<LostopporData> lostoppor) {
        mLostoppor = lostoppor;
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

    public Long getTotalenquiries() {
        return mTotalenquiries;
    }

    public void setTotalenquiries(Long totalenquiries) {
        mTotalenquiries = totalenquiries;
    }

    public Long getTotalpages() {
        return mTotalpages;
    }

    public void setTotalpages(Long totalpages) {
        mTotalpages = totalpages;
    }

}
