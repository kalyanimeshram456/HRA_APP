
package com.ominfo.hra_app.ui.dashboard.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CalenderHolidayResponse {

    @SerializedName("result")
    private CalenderHolidayResult mResult;

    public CalenderHolidayResult getResult() {
        return mResult;
    }

    public void setResult(CalenderHolidayResult result) {
        mResult = result;
    }

}
