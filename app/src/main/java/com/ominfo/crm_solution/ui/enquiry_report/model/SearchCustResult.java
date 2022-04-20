
package com.ominfo.crm_solution.ui.enquiry_report.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class SearchCustResult {

    @SerializedName("customerlist")
    private List<CustomerList> mCustomerlist;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public List<CustomerList> getCustomerlist() {
        return mCustomerlist;
    }

    public void setCustomerlist(List<CustomerList> customerlist) {
        mCustomerlist = customerlist;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
