
package com.ominfo.crm_solution.ui.reminders.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AddReminderResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private Long mStatus;
    @SerializedName("result")
    private List<AddRemResult> addRemResultList;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

    public List<AddRemResult> getAddRemResultList() {
        return addRemResultList;
    }

    public void setAddRemResultList(List<AddRemResult> addRemResultList) {
        this.addRemResultList = addRemResultList;
    }
}
