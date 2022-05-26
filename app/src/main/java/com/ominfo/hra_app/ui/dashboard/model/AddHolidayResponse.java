
package com.ominfo.hra_app.ui.dashboard.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AddHolidayResponse {

    @SerializedName("result")
    private AddHolidayResult mResult;

    public AddHolidayResult getResult() {
        return mResult;
    }

    public void setResult(AddHolidayResult result) {
        mResult = result;
    }

}
