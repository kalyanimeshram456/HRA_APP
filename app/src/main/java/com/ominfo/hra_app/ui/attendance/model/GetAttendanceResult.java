
package com.ominfo.hra_app.ui.attendance.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class GetAttendanceResult {

    @SerializedName("att")
    private List<GetAttendanceAtt> mAtt;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public List<GetAttendanceAtt> getAtt() {
        return mAtt;
    }

    public void setAtt(List<GetAttendanceAtt> att) {
        mAtt = att;
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
