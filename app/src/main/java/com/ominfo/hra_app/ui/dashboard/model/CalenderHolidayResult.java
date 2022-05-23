
package com.ominfo.hra_app.ui.dashboard.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class CalenderHolidayResult {

    @SerializedName("leave")
    private List<CalenderHolidayLeave> mLeave;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public List<CalenderHolidayLeave> getLeave() {
        return mLeave;
    }

    public void setLeave(List<CalenderHolidayLeave> leave) {
        mLeave = leave;
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
