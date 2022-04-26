
package com.ominfo.crm_solution.ui.notifications.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LeaveSingleResult {

    @SerializedName("leave")
    private List<SingleLeave> mLeave;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public List<SingleLeave> getLeave() {
        return mLeave;
    }

    public void setLeave(List<SingleLeave> leave) {
        mLeave = leave;
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
