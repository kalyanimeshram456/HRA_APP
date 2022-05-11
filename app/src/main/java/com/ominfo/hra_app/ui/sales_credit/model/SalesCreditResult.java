
package com.ominfo.hra_app.ui.sales_credit.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SalesCreditResult {

    @SerializedName("currentpage")
    private String mCurrentpage;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("nextpage")
    private Long mNextpage;
    @SerializedName("prevpage")
    private Long mPrevpage;
    @SerializedName("report")
    private List<SalesCreditReport> mReport;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("totalenquiries")
    private String mTotalenquiries;
    @SerializedName("totalpages")
    private Long mTotalpages;

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

    public List<SalesCreditReport> getReport() {
        return mReport;
    }

    public void setReport(List<SalesCreditReport> report) {
        mReport = report;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getTotalenquiries() {
        return mTotalenquiries;
    }

    public void setTotalenquiries(String totalenquiries) {
        mTotalenquiries = totalenquiries;
    }

    public Long getTotalpages() {
        return mTotalpages;
    }

    public void setTotalpages(Long totalpages) {
        mTotalpages = totalpages;
    }

}
