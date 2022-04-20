
package com.ominfo.crm_solution.ui.enquiry_report.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EnquiryStatusResult {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("statuslist")
    private List<EnquiryStatuslist> mStatuslist;

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

    public List<EnquiryStatuslist> getStatuslist() {
        return mStatuslist;
    }

    public void setStatuslist(List<EnquiryStatuslist> statuslist) {
        mStatuslist = statuslist;
    }

}
