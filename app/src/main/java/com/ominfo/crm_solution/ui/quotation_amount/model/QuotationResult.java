
package com.ominfo.crm_solution.ui.quotation_amount.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class QuotationResult {

    @SerializedName("currentpage")
    private String mCurrentpage;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("nextpage")
    private Long mNextpage;
    @SerializedName("prevpage")
    private Long mPrevpage;
    @SerializedName("quot")
    private List<QuotationData> mQuot;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("totalamt")
    private String mTotalamt;
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

    public List<QuotationData> getQuot() {
        return mQuot;
    }

    public void setQuot(List<QuotationData> quot) {
        mQuot = quot;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTotalamt() {
        return mTotalamt;
    }

    public void setTotalamt(String totalamt) {
        mTotalamt = totalamt;
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
