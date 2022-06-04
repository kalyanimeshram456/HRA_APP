
package com.ominfo.hra_app.ui.payment.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class RenewPlanResult {

    @SerializedName("emp_data")
    private List<RenewPlanEmpDatum> mEmpData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public List<RenewPlanEmpDatum> getEmpData() {
        return mEmpData;
    }

    public void setEmpData(List<RenewPlanEmpDatum> empData) {
        mEmpData = empData;
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
