
package com.ominfo.crm_solution.ui.reminders.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class ReminderListResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("result")
    private List<ReminderResult> mResult;
    @SerializedName("status")
    private Long mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public List<ReminderResult> getResult() {
        return mResult;
    }

    public void setResult(List<ReminderResult> result) {
        mResult = result;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
