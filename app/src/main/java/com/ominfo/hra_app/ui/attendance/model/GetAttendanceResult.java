
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
    @SerializedName("office_address")
    private String office_address;
    @SerializedName("office_longitude")
    private String office_longitude;
    @SerializedName("office_latitude")
    private String office_latitude;

    public List<GetAttendanceAtt> getmAtt() {
        return mAtt;
    }

    public void setmAtt(List<GetAttendanceAtt> mAtt) {
        this.mAtt = mAtt;
    }

    public String getOffice_address() {
        return office_address;
    }

    public void setOffice_address(String office_address) {
        this.office_address = office_address;
    }

    public String getOffice_longitude() {
        return office_longitude;
    }

    public void setOffice_longitude(String office_longitude) {
        this.office_longitude = office_longitude;
    }

    public String getOffice_latitude() {
        return office_latitude;
    }

    public void setOffice_latitude(String office_latitude) {
        this.office_latitude = office_latitude;
    }

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
