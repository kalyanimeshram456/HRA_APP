
package com.ominfo.crm_solution.ui.visit_report.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EditVisitResponse {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("result")
    private EditVisitResult mResult;
    @SerializedName("status")
    private Long mStatus;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public EditVisitResult getResult() {
        return mResult;
    }

    public void setResult(EditVisitResult result) {
        mResult = result;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}
