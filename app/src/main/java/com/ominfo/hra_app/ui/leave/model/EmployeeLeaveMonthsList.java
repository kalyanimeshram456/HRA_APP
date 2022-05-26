
package com.ominfo.hra_app.ui.leave.model;

import com.google.gson.annotations.SerializedName;
import com.ominfo.hra_app.ui.dashboard.model.BirthDayDobdatum;

import java.util.List;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class EmployeeLeaveMonthsList {

    @SerializedName("name")
    private String name;
    @SerializedName("days")
    private String days;

    public EmployeeLeaveMonthsList(String name, String days) {
        this.name = name;
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
