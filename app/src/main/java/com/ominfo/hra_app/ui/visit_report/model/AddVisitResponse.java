
package com.ominfo.hra_app.ui.visit_report.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AddVisitResponse {

    @SerializedName("result")
    private AVisitResult mResult;

    public AVisitResult getResult() {
        return mResult;
    }

    public void setResult(AVisitResult result) {
        mResult = result;
    }

}
