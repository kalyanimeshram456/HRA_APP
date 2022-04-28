
package com.ominfo.crm_solution.ui.visit_report.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EditVisitResponse {

    @SerializedName("result")
    private EditVisitResult mResult;

    public void setResult(EditVisitResult result) {
        mResult = result;
    }
    public EditVisitResult getResult() {
        return mResult;
    }



}
