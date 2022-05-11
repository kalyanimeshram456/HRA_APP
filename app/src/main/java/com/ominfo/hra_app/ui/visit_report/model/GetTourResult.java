
package com.ominfo.hra_app.ui.visit_report.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetTourResult {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("statuslist")
    private List<GetTourStatuslist> mStatuslist;

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

    public List<GetTourStatuslist> getStatuslist() {
        return mStatuslist;
    }

    public void setStatuslist(List<GetTourStatuslist> statuslist) {
        mStatuslist = statuslist;
    }

}
