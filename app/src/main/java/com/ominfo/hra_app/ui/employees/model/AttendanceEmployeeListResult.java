
package com.ominfo.hra_app.ui.employees.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AttendanceEmployeeListResult {

    @SerializedName("data")
    private List<AttendanceEmployeeListData> mData;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("status")
    private String mStatus;

    public List<AttendanceEmployeeListData> getData() {
        return mData;
    }

    public void setData(List<AttendanceEmployeeListData> data) {
        mData = data;
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
