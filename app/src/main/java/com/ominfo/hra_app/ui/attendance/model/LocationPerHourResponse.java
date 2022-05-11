
package com.ominfo.hra_app.ui.attendance.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class LocationPerHourResponse {

    @SerializedName("result")
    private LocationPerHourResult mResult;

    public LocationPerHourResult getResult() {
        return mResult;
    }

    public void setResult(LocationPerHourResult result) {
        mResult = result;
    }

}
