
package com.ominfo.crm_solution.ui.enquiry_report.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EnquiryStatusResponse {

    @SerializedName("result")
    private EnquiryStatusResult mResult;

    public EnquiryStatusResult getResult() {
        return mResult;
    }

    public void setResult(EnquiryStatusResult result) {
        mResult = result;
    }

}
