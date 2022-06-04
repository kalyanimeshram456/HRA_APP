
package com.ominfo.hra_app.ui.payment.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class MyPlansResponse {

    @SerializedName("result")
    private MyPlansResult mResult;

    public MyPlansResult getResult() {
        return mResult;
    }

    public void setResult(MyPlansResult result) {
        mResult = result;
    }

}
