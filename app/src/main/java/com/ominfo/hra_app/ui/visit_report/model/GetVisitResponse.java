
package com.ominfo.hra_app.ui.visit_report.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetVisitResponse {

    @SerializedName("result")
    private GetVisitResult mResult;

    public GetVisitResult getResult() {
        return mResult;
    }

    public void setResult(GetVisitResult result) {
        mResult = result;
    }

}
