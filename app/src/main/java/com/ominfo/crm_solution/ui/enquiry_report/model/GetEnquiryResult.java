
package com.ominfo.crm_solution.ui.enquiry_report.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetEnquiryResult {

    @SerializedName("currentpage")
    private String mCurrentpage;
    @SerializedName("enquiries")
    private List<GetEnquiry> mEnquiries;
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
    @SerializedName("totalenquiries")
    private Long totalEnquiries;


    public Long getTotalEnquiries() {
        return totalEnquiries;
    }

    public void setTotalEnquiries(Long totalEnquiries) {
        this.totalEnquiries = totalEnquiries;
    }

    public String getCurrentpage() {
        return mCurrentpage;
    }

    public void setCurrentpage(String currentpage) {
        mCurrentpage = currentpage;
    }

    public List<GetEnquiry> getEnquiries() {
        return mEnquiries;
    }

    public void setEnquiries(List<GetEnquiry> enquiries) {
        mEnquiries = enquiries;
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

}
