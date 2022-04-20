
package com.ominfo.crm_solution.ui.enquiry_report.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EnquiryStatuslist {

    @SerializedName("stage_id")
    private String mStageId;
    @SerializedName("status_id")
    private String mStatusId;
    @SerializedName("status_text")
    private String mStatusText;

    public String getStageId() {
        return mStageId;
    }

    public void setStageId(String stageId) {
        mStageId = stageId;
    }

    public String getStatusId() {
        return mStatusId;
    }

    public void setStatusId(String statusId) {
        mStatusId = statusId;
    }

    public String getStatusText() {
        return mStatusText;
    }

    public void setStatusText(String statusText) {
        mStatusText = statusText;
    }

}
