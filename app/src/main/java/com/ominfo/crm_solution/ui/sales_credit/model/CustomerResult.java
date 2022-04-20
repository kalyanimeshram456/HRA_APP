
package com.ominfo.crm_solution.ui.sales_credit.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CustomerResult {

    @SerializedName("currentpage")
    private String mCurrentpage;
    @SerializedName("customerData")
    private CustomerData mCustomerData;
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

    public CustomerData getCustomerData() {
        return mCustomerData;
    }

    public void setCustomerData(CustomerData customerData) {
        mCustomerData = customerData;
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
