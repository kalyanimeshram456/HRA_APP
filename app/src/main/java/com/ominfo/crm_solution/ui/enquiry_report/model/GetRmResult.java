
package com.ominfo.crm_solution.ui.enquiry_report.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetRmResult {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("rmlist")
    private List<GetRmlist> mRmlist;
    @SerializedName("status")
    private String mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<GetRmlist> getRmlist() {
        return mRmlist;
    }

    public void setRmlist(List<GetRmlist> rmlist) {
        mRmlist = rmlist;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
