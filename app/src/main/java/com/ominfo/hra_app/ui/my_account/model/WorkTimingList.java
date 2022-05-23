
package com.ominfo.hra_app.ui.my_account.model;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class WorkTimingList {

    @SerializedName("mon_day")
    private String mMonDay;
    @SerializedName("mon_end_time")
    private String mMonEndTime;
    @SerializedName("mon_start_time")
    private String mMonStartTime;
    @SerializedName("mon_working")
    private String mMonWorking;
    @SerializedName("toggle")
    private boolean toggle;

    public WorkTimingList(boolean toggle,String mMonDay, String mMonWorking, String mMonStartTime, String mMonEndTime) {
        this.mMonDay = mMonDay;
        this.mMonEndTime = mMonEndTime;
        this.mMonStartTime = mMonStartTime;
        this.mMonWorking = mMonWorking;
        this.toggle = toggle;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public String getMonDay() {
        return mMonDay;
    }

    public void setMonDay(String monDay) {
        mMonDay = monDay;
    }

    public String getMonEndTime() {
        return mMonEndTime;
    }

    public void setMonEndTime(String monEndTime) {
        mMonEndTime = monEndTime;
    }

    public String getMonStartTime() {
        return mMonStartTime;
    }

    public void setMonStartTime(String monStartTime) {
        mMonStartTime = monStartTime;
    }

    public String getMonWorking() {
        return mMonWorking;
    }

    public void setMonWorking(String monWorking) {
        mMonWorking = monWorking;
    }

}
